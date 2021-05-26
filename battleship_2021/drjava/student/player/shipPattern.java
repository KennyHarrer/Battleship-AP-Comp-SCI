package student.player;

import java.util.Dictionary;

public class shipPattern {

    String pattern;

    public shipPattern(Dictionary<Coordinate, coordinateState> currentBoard, String pattern){
        this.pattern = pattern;
    }

    public Coordinate getNextShot() {
        return new Coordinate(666,666);
    }


}
