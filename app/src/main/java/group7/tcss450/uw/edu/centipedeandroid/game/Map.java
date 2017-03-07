package group7.tcss450.uw.edu.centipedeandroid.game;

import java.util.ArrayList;

import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 2/20/17.
 */

public class Map {
    private int height;
    private int width;
    private float tileSize;
    private int playerZone;
    private int centipedeSpawnArea;

    public Map(int width, int height, int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.playerZone = 3;
        this.centipedeSpawnArea = 1;
    }

    public ArrayList<Components.Position> getMushroomPositions() {
        ArrayList<Components.Position> result = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = centipedeSpawnArea; j < height - playerZone; j++) {
                if (Math.random() > 0.8) {
                    result.add(new Components.Position(i * tileSize,j * tileSize));
                }
//                result.add(new Components.Position(i * tileSize,j * tileSize));
            }
        }
        return result;
    }


}
