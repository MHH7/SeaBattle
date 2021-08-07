package Model;

import java.util.ArrayList;

public class Board {
    private final ArrayList<Cell> field1;
    private final ArrayList<Cell> field2;

    public Board(){
        field1 = new ArrayList<>();
        field2 = new ArrayList<>();
        for(int i = 1;i <= 10;i++){
            for(int j = 1;j <= 10;j++){
                field1.add(new Cell(i,j));
                field2.add(new Cell(i,j));
            }
        }
    }

    public ArrayList<Cell> getField1() {
        return field1;
    }

    public ArrayList<Cell> getField2() {
        return field2;
    }

    public Cell getCell(int x, int y, int n){
        if(n == 1){
            for(Cell c : field1){
                if(c.getX() == x && c.getY() == y)return c;
            }
        }
        else if(n == 2){
            for(Cell c : field2){
                if(c.getX() == x && c.getY() == y)return c;
            }
        }
        return null;
    }
}
