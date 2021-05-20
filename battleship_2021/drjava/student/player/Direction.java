package student.player;

public class Direction {

    int direction;

    public Direction() { } //simply exist

    public Direction setDirection(int direction) { //TODO: maybe check if the int is a direction?
        this.direction = direction;
        return this;
    }

    public Direction determineDirection(Coordinate pos1, Coordinate pos2) {
        this.direction = directionFromOffset(pos1.substract(pos2));
        return this;
    }

    public int getDirection() {
        return this.direction;
    }

    public int directionFromOffset(Coordinate offset) { //cant think of a better solution right now
        if(offset.getX() == 1) {
            return 1;
        }else if(offset.getX() == -1) {
            return 3;
        }else if(offset.getY() == 1) {
            return 0;
        }else if(offset.getY() == -1) {
            return 2;
        }
        return -1;
    }

    public Coordinate getOffset() {
        int x = (direction%2==0) ? (direction==1?1:-1) : 0;
        int y = (direction%2!=0) ? 0 : (direction==2?1:-1);
        return new Coordinate(x,y);
    }

    @Override
    public String toString() {

        switch (this.direction){
            case 0:
                return "up";
            case 1:
                return "right";
            case 2:
                return "down";
            case 3:
                return "left";
            default:
                return "No Direction";
        }
    }
}
