package Controller;

import Model.Cell;
import Model.Game;
import Model.Player;
import Model.Requests.Request;
import Model.Requests.UserPassData;
import Model.Responses.GamesData;
import Model.Responses.PlayersData;
import Model.Responses.Response;
import Model.Responses.UpdateData;
import Model.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainServer {
    private ArrayList<Player> allPlayers;
    private Map<Long,Player> authPlayers;
    private final ArrayList<PlayerThread> threads;
    private final ArrayList<Player> waiting;
    private final ArrayList<Game> games;
    private static MainServer ms;

    private MainServer(){
        allPlayers = new ArrayList<>();
        authPlayers = new HashMap<>();
        waiting = new ArrayList<>();
        threads = new ArrayList<>();
        games = new ArrayList<>();
    }

    public static MainServer getMS() {
        if(ms == null)ms = new MainServer();
        return ms;
    }

    public void addPlayer(Player player){
        allPlayers.add(player);
    }

    public void addThread(PlayerThread thread){
        threads.add(thread);
    }

    public ArrayList<PlayerThread> getThreads() {
        return threads;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public boolean isUserNameUsed(String userName){
        for(Player p : allPlayers){
            if(p.getUserName().equals(userName))return true;
        }
        return false;
    }

    public void updatePlayerAuthToken(Player player){
        if(player.getAuthToken() != 0){
            authPlayers.put(player.getAuthToken(),null);
        }
        SecureRandom random = new SecureRandom();
        long bytes = 0;
        do {
            bytes = random.nextLong();
        }while(authPlayers.get(bytes) != null || bytes == 0);
        player.setAuthToken(bytes);
        authPlayers.put(bytes,player);
    }

    public Player getPlayer(String name){
        for(Player p : allPlayers){
            if(p.getUserName().equals(name))return p;
        }
        return null;
    }

    public Player getPlayerByAuthToken(long authToken){
        return authPlayers.get(authToken);
    }

    public Game getGameByPlayer(Player p){
        for(Game g : games){
            if(g.getPlayer1().getUserName().equals(p.getUserName())
            || g.getPlayer2().getUserName().equals(p.getUserName()))return g;
        }
        return null;
    }

    public Response handleRequest(Request request){
        Gson gson = new Gson();
        Type type = request.getType();
        if(type == Type.SignIn){
            UserPassData data = gson.fromJson(request.getData(),UserPassData.class);
            if(data.getUserName().equals("") || data.getPassword().equals(""))
                return(new Response(Type.SignIn,"Null"));
            if(getPlayer(data.getUserName()) == null)
                return(new Response(Type.SignIn,"DExist"));
            if(!getPlayer(data.getUserName()).getPassword().equals(data.getPassword()))
                return(new Response(Type.SignIn,"Wrong"));
            Player player = getPlayer(data.getUserName());
            player.setInGame(false);
            player.setReady(false);
            player.setOnline(true);
            updatePlayerAuthToken(player);
            return (new Response(Type.SignIn,gson.toJson(player)));
        }
        else if(type == Type.SignUp){
            UserPassData data = gson.fromJson(request.getData(),UserPassData.class);
            if(data.getUserName().equals("") || data.getPassword().equals(""))
                return(new Response(Type.SignUp,"Null"));
            if(isUserNameUsed(data.getUserName()))
                return(new Response(Type.SignUp,"Duplicate"));
            Player player = new Player(data.getUserName(), data.getPassword());
            updatePlayerAuthToken(player);
            addPlayer(player);
            player.setOnline(true);
            return (new Response(Type.SignUp,gson.toJson(player)));
        }
        else if(type == Type.NewGame){
            Player player = getPlayerByAuthToken(request.getAuthToken());
            if(player == null)return null;
            waiting.add(player);
            return (new Response(Type.NewGame,""));
        }
        else if(type == Type.StartGame){
            Player player = getPlayerByAuthToken(request.getAuthToken());
            if(player == null)return null;
            if(player.isInGame()){
                Game game = null;
                for(Game g : games){
                    if(g.getPlayer1().getUserName().equals(player.getUserName())
                            || g.getPlayer2().getUserName().equals(player.getUserName()))game = g;
                }
                UpdateData updateData = null;
                if(player.getUserName().equals(game.getPlayer1().getUserName())) updateData = new UpdateData(game,game.getPlayer1());
                if(player.getUserName().equals(game.getPlayer2().getUserName())) updateData = new UpdateData(game,game.getPlayer2());
                return new Response(Type.StartGame,gson.toJson(updateData));
            }
            synchronized (waiting){
                if(waiting.size() > 1){
                    for(int i = 0;i < waiting.size();i++){
                        Player p = waiting.get(i);
                        if(p.getUserName().equals(player.getUserName())){
                            waiting.remove(i);
                            break;
                        }
                    }
                    Player player2 = waiting.get(0);
                    waiting.remove(0);
                    player.setInGame(true);
                    player.setGameId(1);
                    player.setRebuildCnt(3);
                    player.setReady(false);
                    player2.setInGame(true);
                    player2.setGameId(2);
                    player2.setRebuildCnt(3);
                    player2.setReady(false);
                    Game game = new Game(player,player2);
                    games.add(game);
                    UpdateData updateData = new UpdateData(game,player);
                    return (new Response(Type.StartGame,gson.toJson(updateData)));
                }
            }
        }
        else if(type == Type.Rebuild){
            Player player = getPlayerByAuthToken(request.getAuthToken());
            if(player == null)return null;
            getGameByPlayer(player).getBoard().changeField(player.getGameId());
            player.setRebuildCnt(player.getRebuildCnt() - 1);
            for(int i = 0;i < allPlayers.size();i++){
                if(allPlayers.get(i).getUserName().equals(player.getUserName()))allPlayers.set(i,player);
            }
            return (new Response(Type.Rebuild,gson.toJson(getGameByPlayer(player))));
        }
        else if(type == Type.ReadyPlayer){
            Player player = getPlayerByAuthToken(request.getAuthToken());
            if(player == null)return null;
            player = gson.fromJson(request.getData(),Player.class);
            Game game = getGameByPlayer(player);
            if(game.getPlayer1().getUserName().equals(player.getUserName()))game.setPlayer1(player);
            if(game.getPlayer2().getUserName().equals(player.getUserName()))game.setPlayer2(player);
            for(int i = 0;i < allPlayers.size();i++){
                if(allPlayers.get(i).getUserName().equals(player.getUserName()))allPlayers.set(i,player);
            }
            if(game.getPlayer1().isReady() && game.getPlayer2().isReady()){
                game.setGameRunning(true);
            }
        }
        else if(type == Type.UpdateGame){
            Player player = getPlayerByAuthToken(request.getAuthToken());
            if(player == null)return null;
            UpdateData updateData = gson.fromJson(request.getData(),UpdateData.class);
            Game game = updateData.getGame();
            game.changeGameTurn();
            for(Cell c : game.getBoard().getField1())if(c.isDestroyed())c.setDestroyed(true);
            for(Cell c : game.getBoard().getField2())if(c.isDestroyed())c.setDestroyed(true);
            for(int i = 0;i < games.size();i++){
                Game temp = games.get(i);
                if(temp.getPlayer1().getUserName().equals(game.getPlayer1().getUserName())
                ||temp.getPlayer2().getUserName().equals(game.getPlayer2().getUserName())
                ||temp.getPlayer1().getUserName().equals(game.getPlayer2().getUserName())
                ||temp.getPlayer2().getUserName().equals(game.getPlayer1().getUserName())){
                    games.set(i,game);
                }
            }
            Player player1 = game.getPlayer1();
            Player player2 = game.getPlayer2();
            for(int i = 0;i < allPlayers.size();i++){
                if(allPlayers.get(i).getUserName().equals(player1.getUserName()))allPlayers.set(i,player1);
                if(allPlayers.get(i).getUserName().equals(player2.getUserName()))allPlayers.set(i,player2);
            }
            return (new Response(Type.UpdateGame,null));
        }
        else if(type == Type.GetGames){
            Player player = getPlayerByAuthToken(request.getAuthToken());
            if(player == null)return null;
            GamesData gamesData = new GamesData(games);
            return (new Response(Type.GetGames,gson.toJson(gamesData)));
        }
        else if(type == Type.GetPlayers){
            Player player = getPlayerByAuthToken(request.getAuthToken());
            if(player == null)return null;
            PlayersData playersData = new PlayersData(allPlayers);
            return (new Response(Type.GetPlayers,gson.toJson(playersData)));
        }
        return null;
    }

    public void save(){
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            PrintStream writer = new PrintStream("./src/main/resources/Players.json");
            writer.print(gson.toJson(allPlayers));
            writer.close();
        } catch (FileNotFoundException e) {
        }
    }

    public void load(){
        try
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BufferedReader scanner = new BufferedReader(new FileReader("./src/main/resources/Players.json"));
            allPlayers = gson.fromJson(scanner,new TypeToken<ArrayList<Player>>(){}.getType());
            if(allPlayers == null)allPlayers = new ArrayList<>();
            for(Player player : allPlayers)player.setOnline(false);
            authPlayers = new HashMap<>();
            for(Player p : allPlayers){
                authPlayers.put(p.getAuthToken(),p);
            }
        } catch (FileNotFoundException e){
        }
    }
}
