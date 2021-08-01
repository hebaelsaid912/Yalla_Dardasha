package com.example.android.final_graduation_project.ui.home.fragments.rooms.getRooms;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.socket_connection.ConnectToSocket_IO;
import com.example.android.final_graduation_project.SessionManager;
import com.example.android.final_graduation_project.databinding.FragmentDashboardBinding;
import com.example.android.final_graduation_project.pojo.Rooms.getRooms.GetRooms;
import com.example.android.final_graduation_project.pojo.Rooms.getRooms.RoomsList;
import com.example.android.final_graduation_project.pojo.Rooms.joinRoom.JoinRoom;
import com.example.android.final_graduation_project.pojo.SocketIOResponses.JoinRoomResponse;
import com.example.android.final_graduation_project.ui.home.fragments.rooms.createRooms.CreateRoomFragment;
import com.example.android.final_graduation_project.ui.home.fragments.rooms.get_room_info.ActiveRoomActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Retrofit;


public class DashboardFragment extends Fragment {
    private String ARG_ROOM_ID = "room_id";
    private String TOAST_TAG = "GetRoom";

    private String CHANNEL_ROOM_JOINED = "joined_room";
    private String CHANNEL_REFRESH = "refresh";
    private String CHANNEL_REQUSTE_TO_JOIN_ROOM = "join";
    private String CHANNEL_ROOM_JOINED_ERROR = "error";

    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private String mAccessToken;
    private String accessToken = "";
    private String roomID="";
    private HashMap<String , Object> data;

    CreateRoomFragment createRoomFragment;
    Retrofit retrofit;
    FragmentDashboardBinding dashboardBinding;
    DashboardViewModel dashboardViewModel;
    ShowAllRoomsAdapter showAllRoomsAdapter;
    OnItemClickListener onItemClickListener;

    public static Socket getRoomsSocket;

    public DashboardFragment() {
    }

    public static DashboardFragment newInstance(String mAccessToken) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACCESS_TOKEN, mAccessToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // accessToken = "Bearer " + SessionManager.getAccessToken();
        accessToken = "Bearer " + getArguments().getString(ARG_ACCESS_TOKEN);
        Log.i(TOAST_TAG, accessToken + "");
        ConnectToSocket_IO connectToSocket_io = new ConnectToSocket_IO();
        getRoomsSocket = connectToSocket_io.getServerSocket();
        getRoomsSocket.on(Socket.EVENT_CONNECT,onConnect);
        getRoomsSocket.connect();
        getRoomsSocket.on(CHANNEL_ROOM_JOINED , onJoinedRoom);
        getRoomsSocket.on(CHANNEL_ROOM_JOINED_ERROR , onJoinedRoomError);
        //getRoomsSocket.on(CHANNEL_REFRESH, onRefresh);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        dashboardBinding.setLifecycleOwner(this);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        dashboardViewModel.getRoom(accessToken);
        dashboardBinding.executePendingBindings();
        dashboardBinding.emptyViewRooms.setVisibility(View.GONE);
        //dashboardBinding.getRoomsRv.setVisibility(View.GONE);
        dashboardBinding.shimmerEffectRooms.setVisibility(View.VISIBLE);
        dashboardBinding.refreshGetRooms.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashboardViewModel.getRoom(accessToken);
                dashboardBinding.emptyViewRooms.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dashboardBinding.refreshGetRooms.setRefreshing(false);
                    }
                },1500);
            }
        });

        dashboardBinding.createNewRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRoomFragment = CreateRoomFragment.newInstance(accessToken);
                createRoomFragment.show(requireActivity().getSupportFragmentManager(), null);
              //  dashboardViewModel.getRoom(accessToken);
            }
        });

        dashboardViewModel.getRoomsMutableLiveData.observe(this, new Observer<GetRooms>() {
            @Override
            public void onChanged(GetRooms getRooms) {
                if(dashboardViewModel.getRoomsMutableLiveData.getValue().getCount() >0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dashboardBinding.shimmerEffectRooms.stopShimmer();
                            dashboardBinding.shimmerEffectRooms.setVisibility(View.GONE);
                            dashboardBinding.getRoomsRv.setVisibility(View.VISIBLE);
                            showAllRoomsAdapter = new ShowAllRoomsAdapter(
                                    dashboardViewModel.getRoomsMutableLiveData.getValue().getRooms()
                                    , getContext()
                                    , accessToken
                                    , new OnItemClickListener() {
                                @Override
                                public void onItemClick(RoomsList getRooms) {
                                    roomID = getRooms.getRoom_id();
                                    data = new HashMap<>();
                                    data.put(ARG_ROOM_ID , roomID);
                                    Log.i(TOAST_TAG , accessToken + "");
                                    Log.i(TOAST_TAG , roomID + "");
                                    dashboardViewModel.join_Room(accessToken,data);

                                }
                            });

                            showAllRoomsAdapter.notifyDataSetChanged();
                            Log.i(TOAST_TAG , "rooms size  : "+dashboardViewModel.getRoomsMutableLiveData.getValue().getRooms().size() + "" );
                            dashboardBinding.getRoomsRv.setAdapter(showAllRoomsAdapter);
                            LinearLayoutManager layoutManager
                                    = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            dashboardBinding.getRoomsRv.setLayoutManager(layoutManager);


                        }
                    }, 1500);
               }else{
                    dashboardBinding.shimmerEffectRooms.setVisibility(View.VISIBLE);
                    dashboardBinding.shimmerEffectRooms.startShimmer();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dashboardBinding.shimmerEffectRooms.stopShimmer();
                            dashboardBinding.shimmerEffectRooms.setVisibility(View.GONE);
                            dashboardBinding.emptyViewRooms.setVisibility(View.VISIBLE);
                        }
                    },1500);
                }

            }
        });

        dashboardViewModel.joinRoomMutableLiveData.observe(this, new Observer<JoinRoom>() {
            @Override
            public void onChanged(JoinRoom joinRoom) {
                if(dashboardViewModel.joinRoomMutableLiveData.getValue().getCode() != 403 ) {
                    Log.i(TOAST_TAG, "Joined : " + dashboardViewModel.joinRoomMutableLiveData.getValue().isJoined() + "");
                    Log.i(TOAST_TAG, "Message : " + dashboardViewModel.joinRoomMutableLiveData.getValue().getMessage() + "");
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("roomId",roomID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getRoomsSocket.emit(CHANNEL_REQUSTE_TO_JOIN_ROOM , jsonObject.toString());
                   // Toast.makeText(getContext(), dashboardViewModel.joinRoomMutableLiveData.getValue().getMessage() + "", Toast.LENGTH_LONG).show();
                    //  dashboardBinding.getRoomsRv.notifyAll();
                }else{
                    dashboardViewModel.refredh_token(accessToken);
                    Toast.makeText(getContext() , "something went wrong. please retry ",Toast.LENGTH_LONG).show();

                }
            }
        });
        dashboardViewModel.refreshMutableLiveData.observe(this, new Observer<Refresh>() {
            @Override
            public void onChanged(Refresh refresh) {
                Log.i(TOAST_TAG , dashboardViewModel.refreshMutableLiveData.getValue().getToken()+"");
                SessionManager.setAccessToken(dashboardViewModel.refreshMutableLiveData.getValue().getToken());
            }
        });

        return dashboardBinding.getRoot();
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG , "Connect to socket.io from getRooms");
                }
            });
        }
    };
    private Emitter.Listener onJoinedRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG , CHANNEL_ROOM_JOINED + " : "+args[0].toString()+"");
                    Gson gson = new Gson();
                    JoinRoomResponse response = gson.fromJson(args[0].toString() , JoinRoomResponse.class);
                    Log.i(TOAST_TAG , CHANNEL_ROOM_JOINED + " : "+response.getMessage()+"");
                    Toast.makeText(getContext() , response.getMessage() + "" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext() , ActiveRoomActivity.class);
                    intent.putExtra(ARG_ACCESS_TOKEN , accessToken);
                    intent.putExtra(ARG_ROOM_ID , roomID);
                    startActivity(intent);
                    //doCall();
                }
            });
        }
    };
    private Emitter.Listener onJoinedRoomError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG , CHANNEL_ROOM_JOINED_ERROR + " : "+args[0].toString()+"");
                    Gson gson = new Gson();
                    JoinRoomResponse response = gson.fromJson(args[0].toString() , JoinRoomResponse.class);
                    Log.i(TOAST_TAG , CHANNEL_ROOM_JOINED_ERROR + " : "+response.getMessage()+"");
                    Toast.makeText(getContext() , response.getMessage() + "" , Toast.LENGTH_LONG).show();
                }
            });
        }
    };
    private Emitter.Listener onRefresh = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG , CHANNEL_REFRESH + " mmmmmmmmmmm: "+args[0].toString()+"");
                   JSONObject jsonObject = (JSONObject) args[0];
                   try {
                       Log.i(TOAST_TAG, CHANNEL_REFRESH + " : " + jsonObject.getString("message") + "");
                   }catch (JSONException ex){
                       ex.printStackTrace();
                   }

                    //Toast.makeText(getContext() , response.getMessage() + "" , Toast.LENGTH_LONG).show();

                }
            });
        }
    };

    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        dashboardViewModel.getRoom(accessToken);
    }
}