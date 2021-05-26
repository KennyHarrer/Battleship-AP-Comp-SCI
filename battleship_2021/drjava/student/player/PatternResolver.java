package student.player;

import java.util.Dictionary;

public class PatternResolver {

    shipPattern resolvedPattern;
    Dictionary<Coordinate, coordinateState> currentBoard;

    PatternResolver(Dictionary<Coordinate, coordinateState> currentBoard){

        this.currentBoard = currentBoard;

        if(currentBoard.get(new Coordinate(6,6)) == coordinateState.HIT){

        }

    }

    public shipPattern getResolved() {

        return new shipPattern(currentBoard,"i didnt finish this");

    }
}
