package Model;

import Controller.GameManager;
import Model.Cell;
import Model.Player;

import java.awt.*;
import java.util.ArrayList;

public class Ship {
    private final ShipType type;
    protected boolean isAlive;
    protected final ArrayList<Integer> cells;
    protected ArrayList<Integer> destCells;
    private final int Id;

    public Ship(ArrayList<Cell> cells,ShipType type,int Id){
        this.Id = Id;
        this.type = type;
        this.cells = new ArrayList<>();
        for(Cell c : cells){
            this.cells.add(10 * c.getX() + c.getY());
        }
        for(Cell c : cells)c.setShip(this);
        isAlive = true;
    }

    public ArrayList<Integer> getCells() {
        return cells;
    }

    public ArrayList<Integer> getDestCells() {
        return destCells;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getId() {
        return Id;
    }

    public ShipType getType() {
        return type;
    }

    public void checkDestroyed(Cell cell){
        int p = 0;
        for(Integer c : destCells){
            if(cell.getCord() == c){
                p = 1;
                break;
            }
        }
        if(p != 1)destCells.add(cell.getCord());
        System.out.println(destCells.get(0));
        if(cells.size() == destCells.size()){
            isAlive = false;
            for(int x : cells){
                Cell c = GameManager.getGameManager().getGame().getBoard().getCell((x / 10) ,(x % 10),3 - Player.getCurrentPlayer().getGameId());
                if(c == null)continue;
                for(int i = 1;i <= 10;i++){
                    for(int j = 1;j <= 10;j++){
                        if(Math.abs(i - c.getX()) <= 1 && Math.abs(j - c.getY()) <= 1) {
                            GameManager.getGameManager().getGame().getBoard().getCell(i, j,3 -  Player.getCurrentPlayer().getGameId()).normalDestroy();
                        }
                    }
                }
            }
        }
    }
}
