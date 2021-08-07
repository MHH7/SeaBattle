import Config.Config;
import Controller.MainServer;
import Controller.PlayerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args){
        MainServer.getMS().load();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Config.getConfig("PortFile").getProperty(Integer.class,"Port"));
            System.out.println("Server Started!");
            while (true){
                Socket socket = serverSocket.accept();
                PlayerThread thread = new PlayerThread(socket);
                thread.start();
            }
        }catch (IOException e){

        }
    }
}
