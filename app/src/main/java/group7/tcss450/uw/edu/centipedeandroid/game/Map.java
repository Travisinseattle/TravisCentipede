package group7.tcss450.uw.edu.centipedeandroid.game;

import java.util.ArrayList;

import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Method that creates a map on the screen and is used for the correct
 * spawning of mushrooms, player zone and the centipede spawning zone.
 *
 * Created by nicholas on 2/20/17.
 */

public class Map {
    /** the height of the map */
    private int height;

    /** the width of the map */
    private int width;

    /** the tile sizes in the map */
    private float tileSize;

    /** the restricted player zone of the map */
    private int playerZone;

    /** the spawn section of the map for centipede.*/
    private int centipedeSpawnArea;

    /**
     * Main constructor for the map class.
     * Intializes variables.
     * @param width the width of the map
     * @param height the height of the map
     * @param tileSize the tile sizes in the map.
     */
    public Map(int width, int height, int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.playerZone = 3;
        this.centipedeSpawnArea = 1;
    }

    /**
     * Method that gets the positions of all of the mushrooms.
     *
     * @return an ArrayList of component positions.
     */
    public ArrayList<Components.Position> getMushroomPositions() {
        ArrayList<Components.Position> result = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = centipedeSpawnArea; j < height - playerZone; j++) {
                if (Math.random() > 0.7) {
                    result.add(new Components.Position(i * tileSize,j * tileSize));
                }
//                result.add(new Components.Position(i * tileSize,j * tileSize));
            }
        }
        return result;
    }


}
