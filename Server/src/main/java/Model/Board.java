package Model;

import Controller.BoardBuilder;

import java.util.ArrayList;

public class Board {
    private ArrayList<Cell> field1;
    private ArrayList<Cell> field2;

    public Board(){
        field1 = BoardBuilder.getBoardBuilder().getField();
        for(Cell c : field1)c.setId(1);
        field2 = BoardBuilder.getBoardBuilder().getField();
        for(Cell c : field2)c.setId(2);
    }

    public void changeField(int n){
        if(n == 1){
            field1 = BoardBuilder.getBoardBuilder().getField();
            for(Cell c : field1)c.setId(1);
        }
        if(n == 2){
            field2 = BoardBuilder.getBoardBuilder().getField();
            for(Cell c : field2)c.setId(2);
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
