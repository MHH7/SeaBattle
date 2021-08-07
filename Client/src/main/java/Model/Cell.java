package Model;

public class Cell {
    private final int X;
    private final int Y;
    private final int cord;
    private boolean isDestroyed;
    private int id;
    private Ship ship;

    public Cell(int X,int Y){
        this.X = X;
        this.Y = Y;
        cord = 10 * X + Y;
        isDestroyed = false;
        ship = null;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getCord() {
        return cord;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
        if(ship != null)ship.checkDestroyed(this);
    }

    public void normalDestroy(){
        isDestroyed = true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
