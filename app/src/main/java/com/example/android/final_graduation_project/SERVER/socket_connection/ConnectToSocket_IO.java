package com.example.android.final_graduation_project.SERVER.socket_connection;


import com.example.android.final_graduation_project.ui.home.fragments.rooms.getRooms.DashboardFragment;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class ConnectToSocket_IO {

    private static Socket serverSocket;
    private boolean connect;

    public ConnectToSocket_IO() {
        try {
            serverSocket = IO.socket("https://yalla-dardasha-socket.herokuapp.com/");
            serverSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Socket getServerSocket() {
        return serverSocket;
    }

    public static Socket getServerSocketConnction() {
        return DashboardFragment.getRoomsSocket;
    }

    public void setServerSocket(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static boolean isConnect() {
        return serverSocket.isActive();
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

}
