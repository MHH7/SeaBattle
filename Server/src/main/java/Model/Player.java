package Model;

public class Player {
    private final String userName;
    private final String password;
    private long authToken;
    private int wins;
    private int losses;
    private int totalScore;
    private int gameId;
    private boolean inGame;
    private boolean ready;
    private int rebuildCnt = 3;
    private boolean online;

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
        wins = 0;
        losses = 0;
        totalScore = 0;
        authToken = 0;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setRebuildCnt(int rebuildCnt) {
        this.rebuildCnt = rebuildCnt;
    }

    public int getRebuildCnt() {
        return rebuildCnt;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setWins(int wins) {
        this.wins = wins;
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

