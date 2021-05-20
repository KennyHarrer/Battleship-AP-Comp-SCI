package student.player;

import static java.lang.Math.abs;

public class Coordinate {

    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Coordinate add(Coordinate otherCord) { //override?
        int x = this.x+otherCord.getX();
        int y = this.y+otherCord.getY();
        return new Coordinate(x,y);
    }

    public Coordinate substract(Coordinate otherCord){//override?
        int x = abs(this.x-otherCord.x);
        int y = abs(this.y-otherCord.y);
        return new Coordinate(x,y);
    }

    public Coordinate reverse() {
        this.x = x*-1;
        this.y = x*-1;
        return new Coordinate(x,y);
    }

    public Coordinate offsetInDirection(int direction,int distance){
        //oh my god im losing my mind Im hard coding this
        int xx = 0;
        int yy = 0;
        if(direction == 0) yy=(1*distance);
        if(direction == 1) xx=(1*distance);
        if(direction == 2) yy=(-1*distance);
        if(direction == 3) xx=(-1*distance);

        return new Coordinate(this.x+xx,this.y+yy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate otherCoord = (Coordinate) o;

        return otherCoord.x == this.x && otherCoord.y == this.y;

    }

    @Override
    public int hashCode() { //what the heck java
        int result = (int) (x ^ (x >>> 32));
        result = 31 * result + y;
        return result;
    }

}
