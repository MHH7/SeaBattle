package Model;

public class Game {
    private Player player1;
    private Player player2;
    private final Board board;
    private boolean gameRunning;
    private int gameTurn;

    public Game(Player player1,Player player2){
        this.player1 = player1;
        this.player2 = player2;
        board = new Board();
        gameRunning = false;
        gameTurn = 1;
    }

    public void changeGameTurn() {
        gameTurn = 3 - gameTurn;
    }

    public int getGameTurn() {
        return gameTurn;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
