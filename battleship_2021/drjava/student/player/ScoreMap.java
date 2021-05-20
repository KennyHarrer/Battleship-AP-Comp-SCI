package student.player;

import my.battleship.Platform;
import my.battleship.Ship;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class ScoreMap {

    Dictionary<Coordinate,Integer> scores;
    Platform platform;
    Dictionary<Coordinate,coordinateState> currentBoard;

    public ScoreMap(Platform platform, Dictionary<Coordinate,coordinateState> currentBoard) {
        this.platform = platform;
        this.currentBoard = currentBoard;

        scores = new Hashtable<Coordinate,Integer>();

        for(int x = 0; x < platform.getNumberOfRows(); x++) { //row
            for (int y = 0; y < platform.getNumberOfCols(); y++) { //column

                Coordinate currentCoordinate = new Coordinate(x, y);
                coordinateState currentState = currentBoard.get(currentCoordinate);
                int currentTileScore = 0;

                if(currentState == coordinateState.NORMAL){ //Normal Scoring Method

                    List<Ship> shipsLeft = platform.listShips();

                    for (int ship=0;ship<shipsLeft.size();ship++) {

                        int currentShipLength = shipsLeft.get(ship).getLength();
                        for(int direction=0;direction<4;direction++) {
                            boolean badDirection = false;
                            for (int shipLength = 1; shipLength < currentShipLength; shipLength++) {
                                Coordinate possibleShipPosition = currentCoordinate.offsetInDirection(direction,shipLength);
                                if (possibleShipPosition.getX() < 0 || possibleShipPosition.getY() < 0 || possibleShipPosition.getX() > platform.getNumberOfRows() || possibleShipPosition.getY() > platform.getNumberOfCols()) { continue; } //keep scores in bounds
                                coordinateState checkifbad = currentBoard.get(possibleShipPosition);
                                if(checkifbad != coordinateState.NORMAL){
                                    badDirection = true;
                                }
                            }
                            if(badDirection == false){
                                currentTileScore++;
                            }
                        }
                    }
                }else{

                    if(currentState == coordinateState.HIT){ //Punisher Scoring

                        int ShipDirection = -1;

                        for(int direction=0;direction<4;direction++){

                            Coordinate directionCoordinate = currentCoordinate.offsetInDirection(direction, 1);

                            if(directionCoordinate.getX() < 0 || directionCoordinate.getY() < 0 || directionCoordinate.getX() > platform.getNumberOfRows() || directionCoordinate.getY() > platform.getNumberOfCols()) continue;

                            if(currentBoard.get(directionCoordinate) == coordinateState.HIT){
                                ShipDirection = new Direction().determineDirection(currentCoordinate,directionCoordinate).getDirection();
                                break;
                            }
                        }

                        if(ShipDirection!=-1){

                            for(int i=1;i<6;i++) {

                                Coordinate directionCoordinate = currentCoordinate.offsetInDirection(ShipDirection, i);
                                if (directionCoordinate.getX() < 0 || directionCoordinate.getY() < 0 || directionCoordinate.getX() > platform.getNumberOfRows() || directionCoordinate.getY() > platform.getNumberOfCols())
                                    continue;

                                if (currentBoard.get(directionCoordinate) == coordinateState.MISS) {
                                    break;
                                }
                                if (currentBoard.get(directionCoordinate) == coordinateState.NORMAL) {
                                    scores.put(directionCoordinate, 100);
                                }
                            }

                            for(int i=-1;i>-6;i--) {

                                Coordinate directionCoordinate = currentCoordinate.offsetInDirection(ShipDirection, i);
                                if (directionCoordinate.getX() < 0 || directionCoordinate.getY() < 0 || directionCoordinate.getX() > platform.getNumberOfRows() || directionCoordinate.getY() > platform.getNumberOfCols())
                                    continue;

                                if (currentBoard.get(directionCoordinate) == coordinateState.MISS) {
                                    break;
                                }
                                if (currentBoard.get(directionCoordinate) == coordinateState.NORMAL) {
                                    scores.put(directionCoordinate, 100);
                                }
                            }

                        }else{
                            for(int direction=0;direction<4;direction++){

                                Coordinate directionCoordinate = currentCoordinate.offsetInDirection(direction, 1);
                                if(directionCoordinate.getX() < 0 || directionCoordinate.getY() < 0 || directionCoordinate.getX() > platform.getNumberOfRows() || directionCoordinate.getY() > platform.getNumberOfCols()) continue;

                                if(currentBoard.get(directionCoordinate) == coordinateState.NORMAL){
                                    System.out.println(directionCoordinate.x+" "+directionCoordinate.y);
                                    scores.put(directionCoordinate,100);
                                }
                            }
                        }

                    }
                }
                if(scores.get(currentCoordinate)==null){ scores.put(currentCoordinate,currentTileScore);}
            }
        }
    }

    public ScoreMap analyze() {

        for(int x = 0; x < platform.getNumberOfRows(); x++) { //row
            for (int y = 0; y < platform.getNumberOfCols(); y++) { //column

                Coordinate currentCoordinate = new Coordinate(x,y);
                coordinateState currentCoordinateState = currentBoard.get(currentCoordinate);
                //System.out.println("out: "+currentBoard.get(currentCoordinate));
                if(currentCoordinateState != coordinateState.NORMAL) {

                    if(currentCoordinateState == coordinateState.HIT){

                        Direction ShipDirection = null;

                        for(int direction=0;direction<4;direction++){

                            Coordinate directionCoordinate = currentCoordinate.offsetInDirection(direction, 1);

                            if(currentBoard.get(directionCoordinate) == coordinateState.HIT){
                                ShipDirection = new Direction().determineDirection(currentCoordinate,directionCoordinate);
                                break;
                            }
                        }

                        // TODO: DONT DO RANDOM USE SUNK SHIP LENGTHS TO FIND THE MOST OPTIMAL DIRECTION
                        if(ShipDirection != null){
                            //TODO: Actually find the next shooting pos
                            scores.put(currentCoordinate, 0);
                            continue;
                        }else{
                            for(int direction=0;direction<4;direction++){//We have no idea which direction this ship is going so were going to shoot around the hit until we find the right direction
                                Coordinate directionCoordinate = currentCoordinate.offsetInDirection(direction, 1);
                                System.out.println(directionCoordinate.x + " " + directionCoordinate.y);
                                if(currentBoard.get(directionCoordinate)==coordinateState.NORMAL){
                                    scores.put(directionCoordinate, 100);
                                    System.out.println("100 at "+directionCoordinate.x+" "+directionCoordinate.y+" direction number"+direction);
                                    break;
                                }
                            }


                        }
                    }else{
                        scores.put(currentCoordinate, 0);
                        continue ; //No ship can be on this tile, therefore it cannot be a ship, therefore its value is 0.
                    }

                }

                int currentCoordinateScore = 0;

                List<Ship> shipsLeft = platform.listShips();

                for (int ship=0;ship<shipsLeft.size();ship++) {

                    int currentShipLength = shipsLeft.get(ship).getLength();

                    for (int shipLength = 1;shipLength<currentShipLength;shipLength++) {
                        for (int direction= 0; direction<4; direction++) {

                            Coordinate possibleShipPosition = currentCoordinate.offsetInDirection(direction,shipLength);
                            if (possibleShipPosition.getX() < 0 || possibleShipPosition.getY() < 0 || possibleShipPosition.getX() > platform.getNumberOfRows() || possibleShipPosition.getY() > platform.getNumberOfCols()) { continue; } //keep scores in bounds

                            if(currentBoard.get(possibleShipPosition) == coordinateState.NORMAL){
                                currentCoordinateScore++;
                            }

                        }
                    }
                }

                scores.put(new Coordinate(x,y), currentCoordinateScore);

            }
        }

        return this;

    }

    public Coordinate getBestShot(){
        int currentBest = 0;
        Coordinate bestCoord = null;
        for(int x = 0; x < platform.getNumberOfRows(); x++) { //row
            for (int y = 0; y < platform.getNumberOfCols(); y++) { //column
                Coordinate currentCoordinate = new Coordinate(x,y);

                int currentScore = scores.get(currentCoordinate);

                if(currentScore > currentBest){
                    currentBest = currentScore;
                    bestCoord = currentCoordinate;
                }
            }
        }
        System.out.println("Best score: "+currentBest+"\nBest Score At: "+bestCoord.x+" "+bestCoord.y);
        return bestCoord;
    }

}
