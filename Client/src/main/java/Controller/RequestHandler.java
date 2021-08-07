package Controller;

import Config.Config;
import Model.Requests.Request;
import Model.Responses.Response;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class RequestHandler extends Thread{
    private ArrayList<Request> requests;
    private static RequestHandler requestHandler;

    @Override
    public void run() {
        Socket socket;
        try {
            socket = new Socket(Config.getConfig("HostFile").getProperty(String.class,"Host"),
                    Config.getConfig("PortFile").getProperty(Integer.class,"Port"));
            System.out.println("Connected!");
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            Scanner scanner = new Scanner(socket.getInputStream());
            synchronized (requests) {
                while (true) {
                    Gson gson = new Gson();
                    if (requests.size() > 0) {
                        String req = gson.toJson(requests.get(0));
                        writer.println(req);
                        writer.flush();
                        requests.remove(0);
                        String res = scanner.nextLine();
                        ResponseHandler.getResponseHandler().handle(gson.fromJson(res, Response.class));
                    }
                }
            }
        } catch (IOException e) {
        }
    }


    public static RequestHandler getRequestHandler() {
        if(requestHandler == null){
            requestHandler = new RequestHandler();
            requestHandler.setRequests(new ArrayList<>());
        }
        return requestHandler;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }
}
