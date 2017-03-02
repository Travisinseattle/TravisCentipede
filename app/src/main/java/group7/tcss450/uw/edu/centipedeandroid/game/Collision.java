package group7.tcss450.uw.edu.centipedeandroid.game;

import java.util.UUID;

/**
 * Created by nicholas on 3/1/17.
 */

public class Collision {
    public UUID entity;
    public UUID otherEntity;
    public Collision(UUID entity, UUID otherEntity) {
        this.entity = entity;
        this.otherEntity = otherEntity;
    }
}
