package Model;

import Model.Cell;
import Model.Player;

import java.awt.*;
import java.util.ArrayList;

public class Ship {
    private final ShipType type;
    protected boolean isAlive;
    protected ArrayList<Integer> cells;
    protected ArrayList<Integer> destCells;
    private final int Id;

    public Ship(ArrayList<Cell> cells,ShipType type,int Id){
        this.cells = new ArrayList<Integer>();
        destCells = new ArrayList<Integer>();
        this.Id = Id;
        this.type = type;
        this.cells = new ArrayList<>();
        for(Cell c : cells){
            this.cells.add(10 * c.getX() + c.getY());
        }
        for(Cell c : cells)c.setShip(this);
        isAlive = true;
    }

    public int getId() {
        return Id;
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

    public void checkDestroyed(Cell cell){
        int p = 0;
        for(Integer c : destCells){
            if(cell.getCord() == c)p = 1;
        }
        if(p != 1)destCells.add(cell.getCord());
        if(cells.size() == destCells.size())isAlive = false;
    }
}
