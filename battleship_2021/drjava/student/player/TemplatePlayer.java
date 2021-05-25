/*
 * Copyright (c) 2014,2017 IBM Corporation
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// If you create any additional classes to help you, ensure they are in this package
package student.player;

import my.battleship.*;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

enum Mode{
    SCOREMAP,
    PATTERN
}

enum coordinateState {
    NORMAL, //no move has been made at this position
    MISS, //we shot here, but there was nothing here
    HIT, //we shot here and there was a ship here but we haven't fully sunk the ship yet.
    SUNK //get owned, get destroyed, literally completely annihilated you fool
}

public class TemplatePlayer implements Player {

    final static String myName = "Kenneth, Harrer";

    /**
     * A new instance of this class is created for each board.
     * You can perform pre-game setup and initialization here if you want to.
     */

    Mode mode;
    Dictionary<Coordinate,coordinateState> currentBoard;
    Coordinate lastShot;
    List<Ship> shipsLeft;

    public TemplatePlayer() {

        this.mode = Mode.SCOREMAP;
        this.currentBoard = new Hashtable<Coordinate,coordinateState>();

    }

    
    
    @Override
    /**
     * This method plays the game.
     * Methods can be called using the platform to shoot and retrieve the results of the shot.
     * 
     * @see Platform
     */
    public void startGame(Platform platform) {


        for(int row = 0; row < platform.getNumberOfRows(); row++) {
            for (int col = 0; col < platform.getNumberOfCols(); col++) {
                currentBoard.put(new Coordinate(row,col),coordinateState.NORMAL);

            }
        }

        this.shipsLeft = platform.listShips();

        boolean playing = true;

        while(playing) {
            // //This is where we shoot
            //

            ScoreMap CurrentScoreMap = new ScoreMap(platform,currentBoard,shipsLeft);
            Coordinate nextShot = CurrentScoreMap.getBestShot();

            ShotReply shotReply = platform.shoot(nextShot.x, nextShot.y);

            ShotStatus status = shotReply.getStatus();

            switch (status) {
                case HIT:
                    currentBoard.put(nextShot,coordinateState.HIT);
                    break;
                case SUNK_SHIP:
                    System.out.println("You sunk my " +
                            platform.listSunkShips().get(0).getName()
                    );
                    for(int i=0;i<shipsLeft.size();i++){
                        if(platform.listSunkShips().get(0).getName().equals(shipsLeft.get(i).getName())){
                            shipsLeft.remove(i);
                        }
                    }
                    currentBoard.put(nextShot,coordinateState.SUNK); //oh shoot we have to find the rest of the sunk coords
                    break;
                case MISS:
                    currentBoard.put(nextShot,coordinateState.MISS);
                    break;
                case SUNK_ALL_YOU_WIN:
                    System.out.println("You won by sinking  my " +
                            platform.listSunkShips().get(0).getName()
                    );
                    playing = false;
                    break;
                case SHOT_AFTER_GAME_OVER:
                case MISSED_BOARD:
                    return;
                default:
                    break;
            }

            lastShot = nextShot;

        }

    }

    @Override
    /**
     * Returns the name of the player to be shown on the screen
     */
    public String getScreenName() { //Dear creators: What the heck
        return myName;  
    }

    @Override
    /**
     * Returns the real name of the player, which may be different
     * than the screen name.
     */
    public String getRealName() {  //Dear creators: What the heck part 2
        return myName;
    }

    @Override
    /**
     * Returns the name of the player's school, or null if this is not
     * available or applicable.
     * 
     * Providing this information currently does not have any effect on
     * the game and is optional. In the future, the platform may take
     * advantage of this information to improve the evaluation and
     * grading process.
     * 
     */
    public String getSchool() {
        return "Mayo";
    }

    /**
     * Invokes the platform to create an instance of the TemplatePlayer class and use it to 
     * play the game.
     * 
     * Note: If you rename or copy this class to a new name, you may have to update this 
     * method in the new/renamed class to use the correct class name.
     * 
     * @param args
     */
    public static void main(String[] args) {

        // The following allows you to run the game from this player class's main 
        // method versus running from the command line.  To work, the battleship.jar
        // must be included in your environment's classpath.
        
        // This QuickStart version lets you select the board using a prompt so you 
        // can choose different boards to test your strategy against
        PlatformImpl.onePlayerQuickStart(
        		// This parameter determines which class will be created by the runtime, and used to play the
        		// game. It should always match the name of the class defined in this file.
                TemplatePlayer.class,
                // true to show the GUI. This optimizes the GUI so you can observe the play of your Player implementation.
                true);

        // This quick start version lets you run against a single board without having
        // to select it using a file prompt each time

         PlatformImpl.onePlayerQuickStart(
                // The name of the battleship game board properties you want to use.  
                // TODO Change as desired. This board has fixed locations for the ships.
                "sampleBoards/Classic-Random.properties",
                // The Class object representing your player class (this class). The battleship
                // game uses this to plug your code into the game.
                TemplatePlayer.class,
                // true to show the GUI. This optimizes the GUI so you can observe the play of your Player implementation.
                true);

        
    }

}
