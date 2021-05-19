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

import my.battleship.Platform;
import my.battleship.Player;
import my.battleship.ShotReply;
import my.battleship.PlatformImpl;
import my.battleship.ShotStatus;

/**
 * Example solution that can be modified as needed. 
 * The main method will invoke the platform to play the game with an instance of 
 * this TemplatePlayer class.
 * 
 */

enum Mode{
    DISCOVERY,
    CHECKERBOARD,
    RANDOMRESOLVE,
    HUNT,
    PUNISHMENT,
}

public class TemplatePlayer implements Player {

    final static String myName = "Kenneth, Harrer";  // TODO: Enter your name as a string
                                    // e.g final static String myName = "lastname, firstname";

    /**
     * A new instance of this class is created for each board.
     * You can perform pre-game setup and initialization here if you want to.
     */
    public TemplatePlayer() {

        Mode mode = Mode.DISCOVERY;

    }

    
    
    @Override
    /**
     * This method plays the game.
     * Methods can be called using the platform to shoot and retrieve the results of the shot.
     * 
     * @see Platform
     */
    public void startGame(Platform platform) {
        
        /*************************************************************************
         * TODO:
         * 
         * YOU SHOULD REPLACE THE CODE IN THIS METHOD WITH YOUR OWN SOLUTION.
         * 
         *************************************************************************/
    	
        /*
         * This is an example algorithm that shoots from top left to bottom right.
         * This is not a great solution, you should make it better!
         */
        for(int row = 0; row < platform.getNumberOfRows(); row++) {
            for (int col = 0; col < platform.getNumberOfCols(); col++) {

                ShotReply shotReply = platform.shoot(row, col); //This is where we shoot

                //DO NOT TOUCH PAST

                ShotStatus status = shotReply.getStatus();

                switch (status) {
                case HIT:
                    break;
                case SUNK_SHIP:
                	/* Get information about the ship just sunk and print its name */
                	System.out.println("You sunk my " + 
                            platform.listSunkShips().get(0).getName()
                            );
                    break;
                case MISS:
                    break;
                case SUNK_ALL_YOU_WIN:
                	/* Get information about the ship just sunk and print its name */
                	System.out.println("You won by sinking  my " + 
                            platform.listSunkShips().get(0).getName()
                            );
                	return;
                
                case SHOT_AFTER_GAME_OVER:
                case MISSED_BOARD:
                	// if you shoot after the game or miss the board, your program has a bug in it
                    return; 
                 default:
                    break;
                }

            }
        }
    }

    @Override
    /**
     * Returns the name of the player to be shown on the screen
     */
    public String getScreenName() {
        return myName;  
    }

    @Override
    /**
     * Returns the real name of the player, which may be different
     * than the screen name.
     */
    public String getRealName() {
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
        return null;
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
        /*
         PlatformImpl.onePlayerQuickStart(
                // The name of the battleship game board properties you want to use.  
                // TODO Change as desired. This board has fixed locations for the ships.
                "sampleBoards/Classic-1.properties",
                // The Class object representing your player class (this class). The battleship
                // game uses this to plug your code into the game.
                TemplatePlayer.class,
                // true to show the GUI. This optimizes the GUI so you can observe the play of your Player implementation.
                true);
        */
        
    }

}
