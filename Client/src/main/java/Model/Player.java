package Model;

public class Player {
    private final String userName;
    private final String password;
    private long authToken;
    private int wins;
    private int losses;
    private int totalScore;
    private boolean inGame;
    private int gameId;
    private boolean ready;
    private static Player currentPlayer;
    private int rebuildCnt = 3;
    private boolean online;

    public Player(String userName, String password) {
        online = true;
        this.userName = userName;
        this.password = password;
        wins = 0;
        losses = 0;
        totalScore = 0;
        inGame = false;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getRebuildCnt() {
        return rebuildCnt;
    }

    public void setRebuildCnt(int rebuildCnt) {
        this.rebuildCnt = rebuildCnt;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Player.currentPlayer = currentPlayer;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }

    public void setAuthToken(long authToken) {
        this.authToken = authToken;
    }

    public void addWins() {
        wins++;
    }

    public void addLosses() {
        losses++;
    }

    public void addScore(int x) {
        totalScore += x;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public long getAuthToken() {
        return authToken;
    }

    public int getLosses() {
        return losses;
    }

    public int getWins() {
        return wins;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}

