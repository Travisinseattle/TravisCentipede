package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.graphics.RectF;
import android.util.Log;

import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameActivity;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 2/28/17.
 */

public class PhysicsSystem extends SubSystem {
    public PhysicsSystem(GameView theGameView) {
        super(theGameView);
    }
    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allHitBoxes = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.HitBox.class);
        for (UUID id: allHitBoxes) {
            if (!mGameView.mEntityManager.hasComponent(id, Components.Collision.class)) {
                if (mGameView.mEntityManager.hasComponent(id, Components.Movable.class)) {

                    Components.HitBox box = mGameView.mEntityManager.getComponent(id, Components.HitBox.class);
                    for (UUID otherID : allHitBoxes) {
                        if (otherID.equals(id))
                            continue;
                        Components.HitBox otherBox = mGameView.mEntityManager.getComponent(otherID, Components.HitBox.class);
                        if (RectF.intersects(box.getHitBox(), otherBox.getHitBox())) {
                            mGameView.mEntityManager.addComponent(id, new Components.Collision(otherID));
                        }

                    }

                }
            }
        }
    }

    @Override
    public String getSimpleName() {
        return null;
    }
}
