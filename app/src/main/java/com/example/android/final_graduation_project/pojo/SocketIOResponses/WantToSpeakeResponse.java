package com.example.android.final_graduation_project.pojo.SocketIOResponses;

import io.socket.client.Socket;

public class WantToSpeakeResponse {
    private String username;
    private Socket socketId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocketId() {
        return socketId;
    }

    public void setSocketId(Socket socketId) {
        this.socketId = socketId;
    }
}
