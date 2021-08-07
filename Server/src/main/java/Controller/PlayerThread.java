package Controller;

import Model.Requests.Request;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PlayerThread extends Thread{
    private final Socket socket;

    public PlayerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("New Player Thread Started!");
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            Scanner scanner = new Scanner(socket.getInputStream());
            while (true){
                Gson gson = new Gson();
                Request req = gson.fromJson(scanner.nextLine(),Request.class);
                writer.println(gson.toJson(MainServer.getMS().handleRequest(req)));
                writer.flush();
                MainServer.getMS().save();
            }
        } catch (IOException e) {
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
