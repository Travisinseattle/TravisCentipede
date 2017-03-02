package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.util.Log;

import java.util.ArrayList;

import group7.tcss450.uw.edu.centipedeandroid.game.Collision;
import group7.tcss450.uw.edu.centipedeandroid.game.Component;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 3/1/17.
 */

public class CollisionSystem extends SubSystem {

    public CollisionSystem(GameView theGameView) {
        super(theGameView);
    }
    @Override
    public void processOneGameTick(long lastFrameTime) {
        //Log.d("Here 0", String.valueOf(mGameView.myCollisions.size()));

        for (Collision c : mGameView.myCollisions) {

            Components.Position entityPos = mGameView.mEntityManager.getComponent(c.entity, Components.Position.class);
            Components.Position otherPos = mGameView.mEntityManager.getComponent(c.otherEntity, Components.Position.class);
            if (mGameView.mEntityManager.hasComponent(c.entity, Components.Movable.class)) {
                Components.Movable entityMov = mGameView.mEntityManager.getComponent(c.entity, Components.Movable.class);
//                    Log.d("Here 1", "PLEASE");
                entityPos.setX(entityPos.x - entityMov.dx);
                entityPos.setY(entityPos.y - entityMov.dy);
                    entityMov.dx = 0;
                    entityMov.dy = 0;
                }
                if (mGameView.mEntityManager.hasComponent(c.otherEntity, Components.Movable.class)) {
                    Components.Movable otherMov = mGameView.mEntityManager.getComponent(c.otherEntity, Components.Movable.class);
//                    Log.d("Here 2", "PLEASE");
                    otherPos.setX(otherPos.x - otherMov.dx);
                    otherPos.setY(otherPos.y - otherMov.dy);
                    otherMov.dx = 0;
                    otherMov.dy = 0;
                }
        }
        mGameView.myCollisions.clear();
    }

    @Override
    public String getSimpleName() {
        return null;
    }
}
