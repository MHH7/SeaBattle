package Controller;

import Model.Board;
import Model.Cell;
import Model.Ship;
import Model.ShipType;

import java.util.ArrayList;
import java.util.Random;

public class BoardBuilder {
    private String[] board = new String[5];
    private static BoardBuilder boardBuilder;

    private BoardBuilder(){
        board[0] = " ~ ~ ~ ~ ~ G ~ ~ ~ ~" +
                " ~ ~ ~ ~ ~ ~ ~ ~ ~ ~" +
                " ~ ~ ~ ~ ~ S S S ~ ~" +
                " ~ c ~ ~ ~ ~ ~ ~ ~ ~" +
                " ~ c ~ ~ ~ ~ ~ C C C" +
                " ~ c ~ ~ ~ f ~ ~ ~ ~" +
                " ~ ~ ~ ~ ~ ~ ~ t t ~" +
                " ~ g ~ ~ d d ~ ~ ~ ~" +
                " ~ ~ ~ ~ ~ ~ ~ ~ D ~" +
                " F ~ ~ B B B B ~ D ~";
        board[1] = " ~ ~ ~ ~ ~ c c c ~ ~" +
                " ~ ~ ~ B ~ ~ ~ ~ ~ ~" +
                " ~ ~ ~ B ~ ~ G ~ ~ t" +
                " ~ ~ ~ B ~ ~ ~ ~ ~ t" +
                " ~ ~ ~ B ~ f ~ ~ ~ ~" +
                " ~ ~ ~ ~ ~ ~ ~ S S S" +
                " ~ ~ C ~ D D ~ ~ ~ ~" +
                " ~ ~ C ~ ~ ~ ~ ~ d ~" +
                " ~ ~ C ~ ~ g ~ ~ d ~" +
                " F ~ ~ ~ ~ ~ ~ ~ ~ ~";
        board[2] = " ~ ~ ~ ~ G ~ ~ d ~ ~" +
                " ~ t t ~ ~ ~ ~ d ~ S" +
                " ~ ~ ~ ~ ~ ~ ~ ~ ~ S" +
                " c c c ~ ~ ~ g ~ ~ S" +
                " ~ ~ ~ ~ ~ ~ ~ ~ ~ ~" +
                " ~ ~ ~ ~ f ~ ~ B ~ F" +
                " ~ D ~ ~ ~ ~ ~ B ~ ~" +
                " ~ D ~ ~ ~ ~ ~ B ~ ~" +
                " ~ ~ ~ ~ ~ ~ ~ B ~ ~" +
                " ~ ~ ~ C C C ~ ~ ~ ~";
        board[3] = " ~ f ~ ~ ~ ~ d ~ ~ C" +
                " ~ ~ ~ ~ ~ ~ d ~ ~ C" +
                " ~ g ~ t t ~ ~ ~ ~ C" +
                " ~ ~ ~ ~ ~ ~ ~ D ~ ~" +
                " ~ ~ S ~ ~ ~ ~ D ~ ~" +
                " ~ ~ S ~ ~ ~ ~ ~ ~ ~" +
                " G ~ S ~ ~ ~ B B B B" +
                " ~ ~ ~ ~ ~ ~ ~ ~ ~ ~" +
                " ~ ~ ~ ~ ~ ~ ~ ~ ~ ~" +
                " ~ ~ ~ ~ ~ F ~ ~ ~ ~";
        board[4] = " ~ ~ ~ ~ ~ ~ ~ ~ ~ ~" +
                " ~ ~ S ~ D D ~ ~ ~ ~" +
                " ~ ~ S ~ ~ ~ ~ ~ C ~" +
                " ~ ~ S ~ ~ G ~ ~ C ~" +
                " ~ ~ ~ ~ ~ ~ ~ ~ C ~" +
                " B B B B ~ ~ ~ ~ ~ ~" +
                " ~ ~ ~ ~ ~ ~ c c c ~" +
                " ~ ~ ~ ~ ~ ~ ~ ~ ~ ~" +
                " ~ ~ ~ ~ ~ ~ ~ f ~ d" +
                " g ~ ~ ~ t t ~ ~ ~ d";
    }

    public static BoardBuilder getBoardBuilder() {
        if(boardBuilder == null)boardBuilder = new BoardBuilder();
        return boardBuilder;
    }

    public ArrayList<Cell> getField(){
        Random random = new Random();
        int x = random.nextInt(5);
        String s = board[x];
        ArrayList<Cell> field = new ArrayList<>();
        ArrayList<Cell> battleShips = new ArrayList<>();
        ArrayList<Cell> cruisers1 = new ArrayList<>();
        ArrayList<Cell> cruisers2 = new ArrayList<>();
        ArrayList<Cell> destroyers1 = new ArrayList<>();
        ArrayList<Cell> destroyers2 = new ArrayList<>();
        ArrayList<Cell> destroyers3 = new ArrayList<>();
        ArrayList<Cell> frigates1 = new ArrayList<>();
        ArrayList<Cell> frigates2 = new ArrayList<>();
        ArrayList<Cell> frigates3 = new ArrayList<>();
        ArrayList<Cell> frigates4 = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            for(int j = 0;j < 10;j++){
                Cell c = new Cell(i + 1,j + 1);
                field.add(c);
                int t = (i * 10 + j) * 2 + 1;
                if(s.charAt(t) == 'B')battleShips.add(c);
                if(s.charAt(t) == 'c')cruisers1.add(c);
                if(s.charAt((t)) == 'C')cruisers2.add(c);
                if(s.charAt((t)) == 'd')destroyers1.add(c);
                if(s.charAt((t)) == 'D')destroyers2.add(c);
                if(s.charAt((t)) == 't')destroyers3.add(c);
                if(s.charAt((t)) == 'f')frigates1.add(c);
                if(s.charAt((t)) == 'F')frigates2.add(c);
                if(s.charAt((t)) == 'g')frigates3.add(c);
                if(s.charAt((t)) == 'G')frigates4.add(c);
            }
        }
        Ship battleShip = new Ship(battleShips, ShipType.BattleShip,1);
        Ship cruiser1 = new Ship(cruisers1,ShipType.Cruiser,1);
        Ship cruiser2 = new Ship(cruisers2,ShipType.Cruiser,2);
        Ship destroyer1 = new Ship(destroyers1,ShipType.Destroyer,1);
        Ship destroyer2 = new Ship(destroyers2,ShipType.Destroyer,2);
        Ship destroyer3 = new Ship(destroyers3,ShipType.Destroyer,3);
        Ship frigate1 = new Ship(frigates1,ShipType.Frigate,1);
        Ship frigate2 = new Ship(frigates2,ShipType.Frigate,2);
        Ship frigate3 = new Ship(frigates3,ShipType.Frigate,3);
        Ship frigate4 = new Ship(frigates4,ShipType.Frigate,4);
        return field;
    }
}
