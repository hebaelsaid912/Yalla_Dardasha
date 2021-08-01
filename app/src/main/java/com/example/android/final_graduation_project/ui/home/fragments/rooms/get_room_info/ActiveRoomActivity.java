package com.example.android.final_graduation_project.ui.home.fragments.rooms.get_room_info;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.share_sounds.CustomPeerConnectionObserver;
import com.example.android.final_graduation_project.SERVER.share_sounds.CustomSdpObserver;
import com.example.android.final_graduation_project.SERVER.socket_connection.ConnectToSocket_IO;
import com.example.android.final_graduation_project.SessionManager;
import com.example.android.final_graduation_project.StatusBar;
import com.example.android.final_graduation_project.databinding.ActivityActiveRoomBinding;
import com.example.android.final_graduation_project.pojo.Rooms.endRoom.EndRoom;
import com.example.android.final_graduation_project.pojo.Rooms.getRoomInfo.AudienceList;
import com.example.android.final_graduation_project.pojo.Rooms.getRoomInfo.RoomInfo;
import com.example.android.final_graduation_project.pojo.Rooms.getRoomInfo.SpeakersList;
import com.example.android.final_graduation_project.pojo.Rooms.leaveRoom.LeaveRoom;
import com.example.android.final_graduation_project.pojo.SocketIOResponses.LeaveRoomResponse;
import com.example.android.final_graduation_project.pojo.UserInfo.UserInformation;
import com.example.android.final_graduation_project.ui.home.HomeActivity;
import com.example.android.final_graduation_project.ui.home.fragments.profile.ProfileFragment;
import com.example.android.final_graduation_project.ui.home.fragments.rooms.getRooms.DashboardFragment;
import com.example.android.final_graduation_project.ui.home.fragments.rooms.get_room_info.room_member_profile.RoomMemberProfileFragment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.webrtc.SessionDescription;

public class ActiveRoomActivity extends AppCompatActivity {
    ActivityActiveRoomBinding activityActiveRoomBinding;
    ActiveRoomViewModel roomInfoViewModel;

    //leave socket io
    private String CHANNEL_ON_REFRESH = "refresh";
    private String CHANNEL_REQUSTE_TO_LEAVE_ROOM = "leave";
    private String CHANNEL_ROOM_LEAVE_ERROR = "error";
    //want_to_speak socket io
    private String CHANNEL_REQUSTE_TO_WANT_TO_SPEAK = "want_to_speak";
    private String CHANNEL_LISTEN_IF_MEMBER_WANT_TO_SPEAK = "listen_if_member_want_to_speak";
    //allow_member_to_speak socket io
    private String CHANNEL_REQUSTE_TO_ALLOW_MEMBER_TO_SPEAK = "allow_member_to_speak";
    private String CHANNEL_LISTEN_IF_MEMBER_CAN_SPEAK = "if_i_can_speak";
    //dis_allow_member_to_speak socket io
    private String CHANNEL_REQUSTE_TO_DISALLOW_MEMBER_TO_SPEAK = "dis_allow_member_to_speak";
    //move_to_audience socket io
    private String CHANNEL_REQUSTE_TO_MOVE_TO_AUDIENCE = "move_to_audience";
    private String CHANNEL_LISTEN_IF_MODERATOR_CANCEL_MEMBER_REQUEST = "listen_if_moderator_cancel_my_request";
    //open or close mic socket io
    private String CHANNEL_MEMBER_OPEN_MIC = "member_open_mic";
    private String CHANNEL_MEMBER_CLOSE_MIC = "member_closed_mic";
    private String CHANNEL_REFRESH_MIC = "refresh_mic";
    //open or close mic socket io
    private String CHANNEL_END_ROOM = "end_room";
    private String CHANNEL_NOTHING_HAPPEND = "no_thing_happend";
    private String CHANNEL_ROOM_ENDED = "room_ended";
    private String CHANNEL_MY_ROOM_ENDED = "my_room_ended";
    //new webRTC
    private String CHANNEL_ON_START_CALL = "start_call";
    private String CHANNEL_ON_WEBRTC_OFFER = "webrtc_offer";
    private String CHANNEL_ON_WEBRTC_ANSWER = "webrtc_answer";
    private String CHANNEL_ON_WEBRTC_ICE_CANDIDATE = "webrtc_ice_candidate";

    //webRtc
    private MediaConstraints sdpConstraints;
    private MediaConstraints audioConstraints;
    private PeerConnection localPeer;
    private PeerConnectionFactory peerConnectionFactory;
    private AudioTrack localAudioTrack;
    private AudioSource audioSource;
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int permissionCode = 21;
    private List<PeerConnection.IceServer> iceServers;
    List<PeerConnection.IceServer> peerIceServers = new ArrayList<>();
     AudioManager manager;
    // MediaPlayer mediaPlayer;

    //variables
    private String TOAST_TAG = "GetRoomInfo";
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_ROOM_ID = "room_id";
    private static String USER_CREATOR_ID = "";
    String accessToken = "";
    String roomId = "";
    HashMap<String, Object> data;
    private List<HashMap<String, Object>> memberswantTospeakList;
    private Socket getRoomInfoSocket;
    private Socket socketIdFromRoomSocket;
    PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityActiveRoomBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_active_room);
        activityActiveRoomBinding.setLifecycleOwner(this);
        roomInfoViewModel = new ViewModelProvider(this).get(ActiveRoomViewModel.class);
        new StatusBar(this, R.color.browser_actions_bg_grey);

        //Socket Connection
        Log.i(TOAST_TAG, "Socket Connection : " + ConnectToSocket_IO.isConnect() + "");
        getRoomInfoSocket = ConnectToSocket_IO.getServerSocketConnction();
        Log.i(TOAST_TAG, ConnectToSocket_IO.getServerSocketConnction().toString() + "");
        getRoomInfoSocket.on(CHANNEL_ROOM_LEAVE_ERROR, onRoomLeaveError);
        getRoomInfoSocket.on(CHANNEL_ON_REFRESH, onRefresh);
        getRoomInfoSocket.on(CHANNEL_REFRESH_MIC, onRefreshMic);
        getRoomInfoSocket.on(CHANNEL_ROOM_ENDED, onRoomEnded);
        getRoomInfoSocket.on(CHANNEL_MY_ROOM_ENDED, onMyRoomEnded);
        getRoomInfoSocket.on(CHANNEL_NOTHING_HAPPEND, onNoThingHappend);
        getRoomInfoSocket.on(CHANNEL_LISTEN_IF_MEMBER_CAN_SPEAK, onIfCanSpeak);
        getRoomInfoSocket.on(CHANNEL_LISTEN_IF_MODERATOR_CANCEL_MEMBER_REQUEST, onIfModeratorCancelMemberRequst);
        //webRTC
        getRoomInfoSocket.on(CHANNEL_ON_START_CALL, onStartCall);
        getRoomInfoSocket.on(CHANNEL_ON_WEBRTC_OFFER, onWebRtcOffer);
        getRoomInfoSocket.on(CHANNEL_ON_WEBRTC_ANSWER, onWebRtcAnswer);
        getRoomInfoSocket.on(CHANNEL_ON_WEBRTC_ICE_CANDIDATE, onWebRtcIceCandidate);

        accessToken = getIntent().getStringExtra(ARG_ACCESS_TOKEN);
        roomId = getIntent().getStringExtra(ARG_ROOM_ID);
        Log.i("getRoomInfo", ARG_ACCESS_TOKEN + accessToken);
        Log.i("getRoomInfo", ARG_ROOM_ID + roomId + "");
        data = new HashMap<>();
        data.put(ARG_ROOM_ID, roomId);
        memberswantTospeakList = new ArrayList<>();
        memberswantTospeakList.add(new HashMap<>());

        activityActiveRoomBinding.askToSpeakBtn.setVisibility(View.GONE);
        activityActiveRoomBinding.acceptMemberAsk.setVisibility(View.GONE);
        activityActiveRoomBinding.micOff.setVisibility(View.GONE);
        activityActiveRoomBinding.micOn.setVisibility(View.GONE);
        activityActiveRoomBinding.LeaveRoom.setVisibility(View.GONE);
        activityActiveRoomBinding.EndRoom.setVisibility(View.GONE);

        roomInfoViewModel.setRoomInfoData(accessToken, data);
        roomInfoViewModel.getUserInfo(accessToken);
        manager = (AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);


        PopupMenu popupMenu = new PopupMenu(getBaseContext(), activityActiveRoomBinding.acceptMemberAsk);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        roomInfoViewModel.roomInfoListMutableLiveData.observe(this, new Observer<RoomInfo>() {
            @Override
            public void onChanged(RoomInfo roomInfo) {
                if (roomInfoViewModel.roomInfoListMutableLiveData.getValue().getCode() != 403) {
                    if (roomInfoViewModel.roomInfoListMutableLiveData.getValue() != null) {

                        int length = roomInfoViewModel.roomInfoListMutableLiveData.getValue().getRoom_info().getSpeakers().size();
                        Log.i(TOAST_TAG, "length : " + length + "");
                        roomInfoViewModel.userInformationMutableLiveData.observe(activityActiveRoomBinding.getLifecycleOwner(), new Observer<UserInformation>() {
                            @Override
                            public void onChanged(UserInformation userInformation) {
                                for (int i = 0; i < length; i++) {

                                    if (roomInfoViewModel.userInformationMutableLiveData.getValue().getUser().get_id()
                                            .equals(roomInfoViewModel.roomInfoListMutableLiveData.getValue().getRoom_info().getSpeakers().get(i).getUser_id())) {
                                        //if member is creator
                                        if (roomInfoViewModel.userInformationMutableLiveData.getValue().getUser().get_id()
                                                .equals(roomInfoViewModel.roomInfoListMutableLiveData.getValue().getRoom_info().getCreated_by())) {
                                            USER_CREATOR_ID = roomInfoViewModel.roomInfoListMutableLiveData.getValue().getRoom_info().getSpeakers().get(i).getUser_id() + "";
                                            Log.i(TOAST_TAG, "Created By  : " + roomInfoViewModel.roomInfoListMutableLiveData.getValue().getRoom_info().getCreated_by());
                                            String userId = roomInfoViewModel.roomInfoListMutableLiveData.getValue().getRoom_info().getSpeakers().get(i).getUser_id();
                                            Log.i(TOAST_TAG, "User Created this room   : " + roomInfoViewModel.roomInfoListMutableLiveData.getValue().getRoom_info().getSpeakers().get(i).getUser_id());
                                            getRoomInfoSocket.on(CHANNEL_LISTEN_IF_MEMBER_WANT_TO_SPEAK, onSpeakerListenIfMemberWantToSpeak);
                                            registerForContextMenu(activityActiveRoomBinding.acceptMemberAsk);
                                            activityActiveRoomBinding.EndRoom.setVisibility(View.VISIBLE);
                                            activityActiveRoomBinding.micOff.setVisibility(View.VISIBLE);
                                            activityActiveRoomBinding.acceptMemberAsk.setVisibility(View.VISIBLE);

                                            activityActiveRoomBinding.EndRoom.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    roomInfoViewModel.setEndRoom(accessToken , data);
                                                }
                                            });
                                            activityActiveRoomBinding.acceptMemberAsk.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                        @Override
                                                        public boolean onMenuItemClick(MenuItem item) {
                                                            JSONObject jsonObject = new JSONObject();
                                                            try {
                                                                jsonObject.put("memberSocketId", memberswantTospeakList.get(item.getItemId()).get("memberSocketId"));
                                                                Log.i(TOAST_TAG, memberswantTospeakList.get(item.getItemId()).get("memberSocketId") + "");
                                                                new AlertDialog.Builder(ActiveRoomActivity.this)
                                                                        .setTitle("request")
                                                                        .setMessage(memberswantTospeakList.get(item.getItemId()).get("userName").toString() + " want to speak ")
                                                                        .setCancelable(false)
                                                                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                getRoomInfoSocket.emit(CHANNEL_REQUSTE_TO_ALLOW_MEMBER_TO_SPEAK, jsonObject.toString());
                                                                                JSONObject json = new JSONObject();
                                                                                try {
                                                                                    json.put("roomId", roomId);
                                                                                    getRoomInfoSocket.emit(CHANNEL_REQUSTE_TO_MOVE_TO_AUDIENCE, json.toString());
                                                                                } catch (JSONException e) {
                                                                                    e.printStackTrace();
                                                                                }

                                                                            }
                                                                        })
                                                                        .setNegativeButton("Dis Allow", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                getRoomInfoSocket.emit(CHANNEL_REQUSTE_TO_DISALLOW_MEMBER_TO_SPEAK, jsonObject.toString());

                                                                            }
                                                                        })
                                                                        .show();
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                            return true;
                                                        }
                                                    });
                                                    popupMenu.show();
                                                }
                                            });
                                            activityActiveRoomBinding.micOff.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    activityActiveRoomBinding.micOff.setVisibility(View.GONE);
                                                    activityActiveRoomBinding.micOn.setVisibility(View.VISIBLE);
                                                    //activityActiveRoomBinding.micOff.setImageResource(R.id.mic_on);
                                                    try {
                                                        JSONObject jsonObject = new JSONObject();
                                                        jsonObject.put("roomId", roomId);
                                                        jsonObject.put("userId", userId);
                                                        getRoomInfoSocket.emit(CHANNEL_MEMBER_OPEN_MIC, jsonObject.toString());
                                                    } catch (JSONException ex) {
                                                        ex.printStackTrace();
                                                    }

                                                }
                                            });
                                            activityActiveRoomBinding.micOn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    activityActiveRoomBinding.micOn.setVisibility(View.GONE);
                                                    activityActiveRoomBinding.micOff.setVisibility(View.VISIBLE);
                                                    try {
                                                        JSONObject jsonObject = new JSONObject();
                                                        jsonObject.put("roomId", roomId);
                                                        jsonObject.put("userId", userId);
                                                        getRoomInfoSocket.emit(CHANNEL_MEMBER_CLOSE_MIC, jsonObject.toString());
                                                    } catch (JSONException ex) {
                                                        ex.printStackTrace();
                                                    }

                                                }
                                            });
                                        }

                                    } else { // if audiance
                                        activityActiveRoomBinding.LeaveRoom.setVisibility(View.VISIBLE);
                                        activityActiveRoomBinding.askToSpeakBtn.setVisibility(View.VISIBLE);
                                        activityActiveRoomBinding.askToSpeakBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(getBaseContext(), "wait until speakers accept your request !", Toast.LENGTH_LONG).show();
                                                JSONObject jsonObject = new JSONObject();
                                                try {
                                                    jsonObject.put("roomId", roomId);
                                                    jsonObject.put("username", userInformation.getUser().getUsername() + "");
                                                    getRoomInfoSocket.emit(CHANNEL_REQUSTE_TO_WANT_TO_SPEAK, jsonObject.toString());
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        });
                                        activityActiveRoomBinding.LeaveRoom.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                roomInfoViewModel.setLeaveRoom(accessToken, data);
                                            }
                                        });
                                    }
                                }

                            }
                        });


                        //Speakers
                        RoomSpeakersAdabter roomSpeakersAdabter = new RoomSpeakersAdabter(
                                roomInfoViewModel.roomInfoListMutableLiveData.getValue().getRoom_info().getSpeakers(),
                                getBaseContext(),
                                new OnItemClickListenerSpeakers() {
                            @Override
                            public void onItemClickSpeakers(SpeakersList getSpeakers) {
                                Log.i(TOAST_TAG,"onItemClickAudience room member" + getSpeakers.getUser_id());
                                Log.i(TOAST_TAG,"onItemClickAudience user " +
                                        roomInfoViewModel.userInformationMutableLiveData.getValue().getUser().get_id());
                                Log.i(TOAST_TAG,"onItemClickSpeakers "+ accessToken);
                                RoomMemberProfileFragment roomMemberProfileFragment =
                                        RoomMemberProfileFragment.newInstance(accessToken,getSpeakers.getUser_id().toString(),
                                                roomInfoViewModel.userInformationMutableLiveData.getValue().getUser().get_id());
                                roomMemberProfileFragment.show(getSupportFragmentManager(), null);
                            }
                        });
                        activityActiveRoomBinding.roomSpeakersRv.setVisibility(View.VISIBLE);
                        activityActiveRoomBinding.roomSpeakersRv.setAdapter(roomSpeakersAdabter);
                        LinearLayoutManager layoutManager
                                = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
                        activityActiveRoomBinding.roomSpeakersRv.setLayoutManager(layoutManager);
                        //Audience
                        RoomAudienceAdabter roomAudienceAdabter = new RoomAudienceAdabter(
                                roomInfoViewModel.roomInfoListMutableLiveData.getValue().getRoom_info().getAudience(),
                                getBaseContext(), new OnItemClickListenerAudience() {
                            @Override
                            public void onItemClickAudience(AudienceList getAudience) {
                                Log.i(TOAST_TAG,"onItemClickAudience room member" + getAudience.getUser_id());
                                Log.i(TOAST_TAG,"onItemClickAudience user " +
                                        roomInfoViewModel.userInformationMutableLiveData.getValue().getUser().get_id());
                                Log.i(TOAST_TAG,"onItemClickAudience " + accessToken);
                                RoomMemberProfileFragment roomMemberProfileFragment =
                                        RoomMemberProfileFragment.newInstance(accessToken,getAudience.getUser_id().toString(),roomInfoViewModel.userInformationMutableLiveData.getValue().getUser().get_id());
                                roomMemberProfileFragment.show(getSupportFragmentManager(), null);
                            }
                        });
                        LinearLayoutManager layoutManager22 = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
                        activityActiveRoomBinding.roomMemberRv.setVisibility(View.VISIBLE);
                        activityActiveRoomBinding.roomMemberRv.setAdapter(roomAudienceAdabter);
                        activityActiveRoomBinding.roomMemberRv.setLayoutManager(layoutManager22);
                    }
                } else {
                    roomInfoViewModel.refredh_token(accessToken);
                    Toast.makeText(getBaseContext(), "something went wrong. please retry ", Toast.LENGTH_LONG).show();
                }

            }
        });

        roomInfoViewModel.leaveRoomMutableLiveData.observe(this, new Observer<LeaveRoom>() {
            @Override
            public void onChanged(LeaveRoom leaveRoom) {
                if (roomInfoViewModel.leaveRoomMutableLiveData.getValue().getCode() != 403) {
                    Log.i(TOAST_TAG, roomInfoViewModel.leaveRoomMutableLiveData.getValue().getMessage() + "");
                    Log.i(TOAST_TAG, roomInfoViewModel.leaveRoomMutableLiveData.getValue().isLeft() + "");
                    Toast.makeText(getBaseContext(), roomInfoViewModel.leaveRoomMutableLiveData.getValue().getMessage() + "", Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("roomId", roomId);
                        Log.i(TOAST_TAG, roomId + " to leave");
                        getRoomInfoSocket.emit(CHANNEL_REQUSTE_TO_LEAVE_ROOM, jsonObject.toString());
                        hangup();
                        Intent intent = new Intent(getBaseContext() , HomeActivity.class);
                        intent.putExtra("accessToken" , accessToken);
                        startActivity(intent);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    roomInfoViewModel.refredh_token(accessToken);
                    Toast.makeText(getBaseContext(), "something went wrong. please retry ", Toast.LENGTH_LONG).show();
                }
            }
        });
        roomInfoViewModel.endRoomMutableLiveData.observe(this, new Observer<EndRoom>() {
            @Override
            public void onChanged(EndRoom endRoom) {
                if (roomInfoViewModel.endRoomMutableLiveData.getValue().getCode() != 403) {
                    Log.i(TOAST_TAG, roomInfoViewModel.endRoomMutableLiveData.getValue().getMessage() + "");
                    Toast.makeText(getBaseContext(), roomInfoViewModel.endRoomMutableLiveData.getValue().getMessage() + "", Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("roomId", roomId);
                        getRoomInfoSocket.emit(CHANNEL_END_ROOM, jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    roomInfoViewModel.refredh_token(accessToken);
                    Toast.makeText(getBaseContext(), "something went wrong. please retry ", Toast.LENGTH_LONG).show();
                }
            }
        });
        roomInfoViewModel.userInformationMutableLiveData.observe(this, new Observer<UserInformation>() {
            @Override
            public void onChanged(UserInformation userInformation) {
                if (roomInfoViewModel.userInformationMutableLiveData.getValue().getCode() != 403) {
                    Log.i(TOAST_TAG, roomInfoViewModel.userInformationMutableLiveData.getValue().getUser().get_id() + "");
                } else {
                    roomInfoViewModel.refredh_token(accessToken);
                    Toast.makeText(getBaseContext(), "something went wrong. please retry ", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        roomInfoViewModel.refreshMutableLiveData.observe(this, new Observer<Refresh>() {
            @Override
            public void onChanged(Refresh refresh) {
                Log.i(TOAST_TAG, roomInfoViewModel.refreshMutableLiveData.getValue().getToken() + "");
                SessionManager.setAccessToken(roomInfoViewModel.refreshMutableLiveData.getValue().getToken());
            }
        });

        start();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roomId", roomId);
            getRoomInfoSocket.emit(CHANNEL_ON_START_CALL, jsonObject.toString());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        if (activityActiveRoomBinding.EndRoom.getVisibility() == View.VISIBLE) {
            roomInfoViewModel.setEndRoom(accessToken , data);
        } else if (activityActiveRoomBinding.LeaveRoom.getVisibility() == View.VISIBLE) {
            roomInfoViewModel.setLeaveRoom(accessToken,data);
        }
    }

    private void start() {
        initializePeerConnectionFactory();
        createPeerConnection();
    }

    private void initializePeerConnectionFactory() {
        getIceServer();
        PeerConnectionFactory.InitializationOptions initializationOptions =
                PeerConnectionFactory.InitializationOptions.builder(this)
                        .createInitializationOptions();
        PeerConnectionFactory.initialize(initializationOptions);
        //Create a new PeerConnectionFactory instance.
        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
        peerConnectionFactory = PeerConnectionFactory.builder()
                .setOptions(options)
                .createPeerConnectionFactory();
        //Create MediaConstraints - Will be useful for specifying video and audio constraints. More on this later!
        audioConstraints = new MediaConstraints();
        //create an AudioSource instance
        audioSource = peerConnectionFactory.createAudioSource(audioConstraints);
        localAudioTrack = peerConnectionFactory.createAudioTrack("101", audioSource);

    }

    private void createPeerConnection() {
        PeerConnection.RTCConfiguration rtcConfig = new PeerConnection.RTCConfiguration(peerIceServers);
        Log.i(TOAST_TAG, CHANNEL_ON_START_CALL + " : " + "on createPeerConnection ");
        //creating localPeer
        localPeer = peerConnectionFactory.createPeerConnection(rtcConfig, sdpConstraints,
                new CustomPeerConnectionObserver("localPeerCreation") {
            @Override
            public void onIceCandidate(IceCandidate iceCandidate) {
                super.onIceCandidate(iceCandidate);
                Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_ICE_CANDIDATE + " onIceCandidate: Sender :  " + iceCandidate);
                emitIceCandidate(iceCandidate);
            }

            @Override
            public void onAddStream(MediaStream mediaStream) {
                super.onAddStream(mediaStream);
                Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_ICE_CANDIDATE + " onAddStream: " + mediaStream);
                manager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                manager.setMode(AudioManager.MODE_NORMAL);
                manager.setSpeakerphoneOn(true);
                manager.setMicrophoneMute(false);
                manager.setStreamVolume(AudioManager.STREAM_MUSIC,10 , 1);
                AudioTrack audioTrack = mediaStream.audioTracks.get(0);

            }
        });
        Log.i(TOAST_TAG, "on createPeerConnection " + localPeer);
        addStreamToLocalPeer();
    }

    //Adding the stream to the localpeer
    private void addStreamToLocalPeer() {
        //creating local mediastream
        Log.i(TOAST_TAG,"on createPeerConnection " + " addStreamToLocalPeer");
        MediaStream stream = peerConnectionFactory.createLocalMediaStream("102");
        stream.addTrack(localAudioTrack);
        localPeer.addStream(stream);
    }

    public void emitIceCandidate(IceCandidate iceCandidate) {
        try {
            JSONObject object = new JSONObject();
            object.put("type", "candidate");
            object.put("label", iceCandidate.sdpMLineIndex);
            object.put("id", iceCandidate.sdpMid);
            object.put("candidate", iceCandidate.sdp);
            object.put("roomId", roomId);
            getRoomInfoSocket.emit(CHANNEL_ON_WEBRTC_ICE_CANDIDATE, object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doCall() {
        sdpConstraints = new MediaConstraints();
        sdpConstraints.mandatory.add(
                new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        localPeer.createOffer(new CustomSdpObserver("localCreateOffer") {
            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {
                super.onCreateSuccess(sessionDescription);
                localPeer.setLocalDescription(new CustomSdpObserver("localSetLocalDesc"), sessionDescription);
                Log.d("onCreateSuccess", "SignallingClient emit ");
                JSONObject obj = new JSONObject();
                try {
                    obj.put("type", sessionDescription.type.canonicalForm());
                    obj.put("sdp", sessionDescription.description);
                    obj.put("roomId", roomId);
                    getRoomInfoSocket.emit(CHANNEL_ON_WEBRTC_OFFER, obj.toString());
                    Log.d("emitMessage", obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, sdpConstraints);
    }

    private void doAnswer() {
        localPeer.createAnswer(new CustomSdpObserver("localCreateAns") {
            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {
                super.onCreateSuccess(sessionDescription);
                localPeer.setLocalDescription(new CustomSdpObserver("localSetLocal"), sessionDescription);
                JSONObject obj = new JSONObject();
                try {
                    obj.put("type", sessionDescription.type.canonicalForm());
                    obj.put("sdp", sessionDescription.description);
                    obj.put("roomId", roomId);
                    Log.d(TOAST_TAG, CHANNEL_ON_WEBRTC_ANSWER + "doAnswer" + obj.toString());
                    getRoomInfoSocket.emit(CHANNEL_ON_WEBRTC_ANSWER, obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new MediaConstraints());
    }


    // Remote IceCandidate received
    public void onIceCandidateReceived(JSONObject data) {
        try {
            Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_ICE_CANDIDATE + " onaddIceCandidate : ");
            localPeer.addIceCandidate(new IceCandidate(data.getString("id"), data.getInt("label"), data.getString("candidate")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void getIceServer() {
        PeerConnection.IceServer peerIceServer = PeerConnection.IceServer.builder("stun:stun.l.google.com:19302").createIceServer();
        peerIceServers.add(peerIceServer);
    }

    //endRoom
    private void hangup() {
        try {
            if (localPeer != null) {
                localPeer.close();
                //getRoomInfoSocket.emit("", roomId);
            }
            localPeer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Emitter.Listener
    private Emitter.Listener onRefresh = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " : " + args[0].toString() + "");
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        if (jsonObject.get("message").equals("there is member left the room so try to refresh the view")) {
                            Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " : " + jsonObject.get("message") + "");
                            //Toast.makeText(getBaseContext(), response.getMessage() + "", Toast.LENGTH_LONG).show();
                            Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " : " + jsonObject.get("message") + "");
                            roomInfoViewModel.setRoomInfoData(accessToken, data);
                        }
                        if (jsonObject.get("message").equals("new member joined so try to refresh the view")) {
                            Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " : " + jsonObject.get("message") + "");
                            roomInfoViewModel.setRoomInfoData(accessToken, data);
                        }
                        if (jsonObject.get("message").equals("there is a member moved to audience")) {
                            roomInfoViewModel.setRoomInfoData(accessToken, data);
                            Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " : " + jsonObject.get("message") + "");
                        }
                        if (jsonObject.get("message").equals("there is member changed his state from audience to speaker so try to refresh the view")) {
                            roomInfoViewModel.setRoomInfoData(accessToken, data);
                            Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " : " + jsonObject.get("message") + "");
                        }

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

                }
            });
        }
    };
    private Emitter.Listener onRefreshMic = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " : " + args[0].toString() + "");
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        if (jsonObject.get("message").equals("there is a member opened his mic")) {

                            Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " message : " + jsonObject.get("message") + "");
                            Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " user : " + jsonObject.get("userId") + "");
                        }
                        if (jsonObject.get("message").equals("there is a member closed his mic")) {

                            Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " message : " + jsonObject.get("message") + "");
                            Log.i(TOAST_TAG, CHANNEL_ON_REFRESH + " user : " + jsonObject.get("userId") + "");
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onRoomLeaveError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, CHANNEL_ROOM_LEAVE_ERROR + " : " + args[0].toString() + "");
                    Gson gson = new Gson();
                    LeaveRoomResponse response = gson.fromJson(args[0].toString(), LeaveRoomResponse.class);
                    Log.i(TOAST_TAG, CHANNEL_ROOM_LEAVE_ERROR + " : " + response.getMessage() + "");
                    Toast.makeText(getBaseContext(), response.getMessage() + "", Toast.LENGTH_LONG).show();

                }
            });
        }
    };
    private Emitter.Listener onSpeakerListenIfMemberWantToSpeak = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, CHANNEL_LISTEN_IF_MEMBER_WANT_TO_SPEAK + " : " + args[0].toString() + "");
                    try {
                        JSONObject json = (JSONObject) args[0];
                        Log.i(TOAST_TAG, CHANNEL_LISTEN_IF_MEMBER_WANT_TO_SPEAK + " : " + json.getString("username") + "");
                        Log.i(TOAST_TAG, CHANNEL_LISTEN_IF_MEMBER_WANT_TO_SPEAK + " : " + json.getString("socketId").toString() + "");
                        memberswantTospeakList.get(0).put("userName", json.getString("username") + "");
                        memberswantTospeakList.get(0).put("memberSocketId", json.getString("socketId") + "");
                        popupMenu = new PopupMenu(getBaseContext(), activityActiveRoomBinding.acceptMemberAsk);
                        //popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                        popupMenu.getMenu().add(json.getString("username"));
                        Toast.makeText(getBaseContext(), json.getString("username") + " can i speak? ", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    private Emitter.Listener onRoomEnded = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, CHANNEL_ROOM_ENDED + " : " + args[0].toString() + "");
                    new AlertDialog.Builder(ActiveRoomActivity.this)
                            .setTitle("alert")
                            .setMessage("Room ended by it's creator !")
                            .setCancelable(false)
                            .setNegativeButton("Okey", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    hangup();
                                    Intent intent = new Intent(getBaseContext() , HomeActivity.class);
                                    intent.putExtra("accessToken" , accessToken);
                                    startActivity(intent);
                                    finish();

                                }
                            }).show();
                }
            });
        }
    };
    private Emitter.Listener onNoThingHappend = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, args[0].toString() + "");
                }
            });
        }
    };
    private Emitter.Listener onMyRoomEnded = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, args[0].toString() + "");
                    hangup();
                    Intent intent = new Intent(getBaseContext() , HomeActivity.class);
                    intent.putExtra("accessToken" , accessToken);
                    startActivity(intent);
                    finish();
                }
            });
        }
    };
    private Emitter.Listener onIfCanSpeak = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, CHANNEL_LISTEN_IF_MEMBER_CAN_SPEAK + " : " + args[0].toString() + "");
                    try {
                        JSONObject jsonObject = (JSONObject) args[0];
                        Toast.makeText(getBaseContext(), jsonObject.getString("message") + "", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    private Emitter.Listener onIfModeratorCancelMemberRequst = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(TOAST_TAG, CHANNEL_LISTEN_IF_MODERATOR_CANCEL_MEMBER_REQUEST + " : " + args[0].toString() + "");
            try {
                JSONObject jsonObject = (JSONObject) args[0];
                Toast.makeText(getBaseContext(), jsonObject.getString("message") + "", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onStartCall = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, CHANNEL_ON_START_CALL + " : " + args[0] + "");
                    JSONObject jsonObject = (JSONObject) args[0];
                    // createPeerConnection();
                    if (!USER_CREATOR_ID.isEmpty()) {
                        doCall();
                    } else {

                    }
                }
            });

        }
    };
    private Emitter.Listener onWebRtcOffer = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_OFFER + " : " + args[0] + "");
                        JSONObject jsonObject = (JSONObject) args[0];
                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_OFFER + " type 1 : " + jsonObject.getString("type") + "");
                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_OFFER + " type 2 : " + SessionDescription.Type.fromCanonicalForm(jsonObject.getString("type").toLowerCase()).toString() + "");
                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_OFFER + " sdp : " + jsonObject.getString("sdp") + "");
                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_OFFER + " localPeer : " + localPeer + "");

                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_OFFER + " isNotCreator : ");

                        localPeer.setRemoteDescription(new CustomSdpObserver("localSetRemote"),
                                new SessionDescription(
                                        SessionDescription.Type.fromCanonicalForm(jsonObject.getString("type").toLowerCase()),
                                        jsonObject.getString("sdp")));
                        doAnswer();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    };
    private Emitter.Listener onWebRtcAnswer = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_ANSWER + " : " + args[0] + "");
                        JSONObject jsonObject = (JSONObject) args[0];
                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_ANSWER + " type : " + jsonObject.getString("type") + "");
                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_ANSWER + " sdp: " + jsonObject.getString("sdp") + "");

                        localPeer.setRemoteDescription(new CustomSdpObserver("localSetRemote"),
                                new SessionDescription(
                                        SessionDescription.Type.fromCanonicalForm(jsonObject.getString("type").toLowerCase()),
                                        jsonObject.getString("sdp")));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    };
    private Emitter.Listener onWebRtcIceCandidate = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_ICE_CANDIDATE + " : " + args[0] + "");
                        Log.i(TOAST_TAG, CHANNEL_ON_WEBRTC_ICE_CANDIDATE + " onIceCandidate try: " + args[0] + "");
                        JSONObject jsonObject = (JSONObject) args[0];
                        onIceCandidateReceived(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    };


}