package com.example.android.final_graduation_project.ui.home.fragments.rooms.createRooms;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.SERVER.rooms.createRoom.CreateRoom_ApiInterface;
import com.example.android.final_graduation_project.SERVER.socket_connection.ConnectToSocket_IO;
import com.example.android.final_graduation_project.databinding.FragmentCreateRoomBinding;
import com.example.android.final_graduation_project.pojo.Rooms.createRooms.CreateRoom;
import com.example.android.final_graduation_project.pojo.SocketIOResponses.CreateNewRoomResponse;
import com.example.android.final_graduation_project.ui.home.fragments.rooms.getRooms.DashboardFragment;
import com.example.android.final_graduation_project.ui.home.fragments.rooms.get_room_info.ActiveRoomActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Retrofit;

public class CreateRoomFragment extends DialogFragment {
    private String TOAST_TAG = "CreateRoomDialog";
    private static final String SUCCESS_MESSAGE = "Room Created Successfully";
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_ROOM_ID = "room_id";

    private String CHANNEL_ROOM_CREATED = "room_created";
    private String CHANNEL_REQUSTE_TO_CREATE_ROOM = "create";
    private String CHANNEL_ROOM_CREATED_ERROR = "error";

    private String mAccessToken;
    private String accessToken = "";
    private String roomID = "";
    private HashMap<String, Object> data;

    Retrofit retrofit;
    CreateRoom_ApiInterface createRoom_apiInterface;
    FragmentCreateRoomBinding createRoomBinding;
    CreateRoomViewModel createRoomViewModel;

    private Socket createRoomSocket;

    public CreateRoomFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);
        getDialog().setCanceledOnTouchOutside(false);
    }

    public static CreateRoomFragment newInstance(String mAccessToken) {
        CreateRoomFragment fragment = new CreateRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACCESS_TOKEN, mAccessToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createRoomViewModel = new ViewModelProvider(requireActivity()).get(CreateRoomViewModel.class);
        if (getArguments() != null) {
            accessToken = getArguments().getString(ARG_ACCESS_TOKEN);
            // accessToken = SessionManager.getAccessToken();
            Log.i(TOAST_TAG, accessToken + "");
            Log.i(TOAST_TAG, "Socket Connection : " + ConnectToSocket_IO.isConnect() + "");
            createRoomSocket = ConnectToSocket_IO.getServerSocketConnction();
            Log.i(TOAST_TAG, ConnectToSocket_IO.getServerSocketConnction().toString() + "");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createRoomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_room, container, false);
        createRoomBinding.setLifecycleOwner(this);
        createRoomBinding.executePendingBindings();

        createRoomBinding.submitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomName = createRoomBinding.roomName.getEditText().getText().toString().trim();
                if (!roomName.isEmpty()) {
                    if(roomName.length()>3) {
                        data = new HashMap<>();
                        data.put("name", roomName);
                        createRoomViewModel.startCreateRoom(accessToken, data);
                    }else{
                        Toast.makeText(getContext(),
                                "room name must be more than 3 char ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "room name cann't be empty ", Toast.LENGTH_LONG).show();
                }
            }
        });

        createRoomViewModel.createRoomMutableLiveData.observe(this, new Observer<CreateRoom>() {
            @Override
            public void onChanged(CreateRoom createRoom) {
                if (createRoomViewModel.createRoomMutableLiveData.getValue().getCode() != 403) {
                    if (createRoomViewModel.createRoomMutableLiveData.getValue().getMessage().equals(SUCCESS_MESSAGE)) {
                        //Toast.makeText(getContext(), createRoomViewModel.createRoomMutableLiveData.getValue().getMessage() + "", Toast.LENGTH_LONG).show();
                        roomID = createRoomViewModel.createRoomMutableLiveData.getValue().getRoom().get_id() + "";
                        Log.i(TOAST_TAG, roomID + "");
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("roomId" , roomID);
                            createRoomSocket.emit(CHANNEL_REQUSTE_TO_CREATE_ROOM, jsonObject.toString());
                        }catch (JSONException ex){
                            ex.printStackTrace();
                        }

                        createRoomSocket.on(CHANNEL_ROOM_CREATED, onRoomCreated);
                        createRoomSocket.on(CHANNEL_ROOM_CREATED_ERROR, onRoomCreatedError);

                    } else {
                        Toast.makeText(getContext(),
                                createRoomViewModel.createRoomMutableLiveData.getValue().getMessage() + "", Toast.LENGTH_LONG).show();
                    }
                } else {
                    createRoomViewModel.refredh_token(accessToken);
                    Toast.makeText(getContext(), "something went wrong. please retry ", Toast.LENGTH_LONG).show();
                    dismiss();
                }
            }
        });

        createRoomBinding.cancelAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return createRoomBinding.getRoot();
    }

    void toStartRoom(String accessToken, String roomID) {
        Log.i(TOAST_TAG, accessToken + "");
        Intent intent = new Intent(getActivity().getBaseContext(), ActiveRoomActivity.class);
        intent.putExtra(ARG_ACCESS_TOKEN, accessToken);
        intent.putExtra(ARG_ROOM_ID, roomID);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(createRoomBinding.submitName, "roomCreated");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
        startActivity(intent, options.toBundle());
        dismiss();
    }

    private Emitter.Listener onRoomCreated = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, CHANNEL_ROOM_CREATED + " : " + args[0].toString() + "");
                    Gson gson = new Gson();
                    CreateNewRoomResponse response = gson.fromJson(args[0].toString(), CreateNewRoomResponse.class);
                    Log.i(TOAST_TAG, CHANNEL_ROOM_CREATED + " : " + response.getMessage() + "");
                    Toast.makeText(getActivity().getBaseContext(), response.getMessage() + "", Toast.LENGTH_LONG).show();
                    toStartRoom(accessToken, roomID);
                }
            });

        }
    };
    private Emitter.Listener onRoomCreatedError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TOAST_TAG, CHANNEL_ROOM_CREATED_ERROR + " : " + args[0].toString() + "");
                    Gson gson = new Gson();
                    CreateNewRoomResponse response = gson.fromJson(args[0].toString(), CreateNewRoomResponse.class);
                    Log.i(TOAST_TAG, CHANNEL_ROOM_CREATED_ERROR + " : " + response.getMessage() + "");
                    Toast.makeText(getActivity().getBaseContext(), response.getMessage() + "", Toast.LENGTH_LONG).show();
                }
            });

        }
    };
}