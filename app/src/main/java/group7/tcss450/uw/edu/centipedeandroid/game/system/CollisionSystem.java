package group7.tcss450.uw.edu.centipedeandroid.game.system;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;

/**
 * Created by nicholas on 3/1/17.
 */

public class CollisionSystem extends SubSystem {
//    The Collision system must
//      * take away health
//      * makes it so objects are impassible
    private HashMap<UUID, Components.Collision> toKill = new HashMap<>();
    public CollisionSystem(GameView theGameView) {
        super(theGameView);
    }
    @Override
    public void processOneGameTick(long lastFrameTime) {
        // This must be a direct reference to collection so removing actually works???
        Set<UUID> allCollisions = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Collision.class);
        Iterator<UUID> iter = allCollisions.iterator();
        while (iter.hasNext()) {
            UUID id = iter.next();
            Components.Collision collision = mGameView.mEntityManager.getComponent(id, Components.Collision.class);
            if (mGameView.mEntityManager.hasComponent(id, Components.Hazard.class)) {
                if (mGameView.mEntityManager.hasComponent(collision.collidedWith, Components.Health.class)) {
//                    // maybe this should be broken into a new system / event?
                    if (!mGameView.mEntityManager.hasComponent(collision.collidedWith, Components.Team.class)) {
                        Components.Hazard hazard = mGameView.mEntityManager.getComponent(id, Components.Hazard.class);
                        Components.Health health = mGameView.mEntityManager.getComponent(collision.collidedWith, Components.Health.class);
                        Components.Health health1 = mGameView.mEntityManager.getComponent(id, Components.Health.class);
                        health.setHitPoints(health.getHitPoints() - hazard.myDamage);
                        health1.setHitPoints(health1.getHitPoints() - 1);
                    }
 }
            }

//            // Special Behaviour for Centipedes?
            if (mGameView.mEntityManager.hasComponent(id, Components.CentipedeID.class)) {
                if (!mGameView.mEntityManager.hasComponent(collision.collidedWith, Components.Movable.class)) {
                    //  Could be used for wall too???
                    // Centipede collided with mushroom do special movement here.

                }
            } else {
                // There was a collision it must be solid?
                // Handle moving back
                Components.Position entityPos = mGameView.mEntityManager.getComponent(id, Components.Position.class);
                Components.Movable entityMov = mGameView.mEntityManager.getComponent(id, Components.Movable.class);
                entityPos.setX(entityPos.x - entityMov.dx);
                entityPos.setY(entityPos.y - entityMov.dy);
                //entityMov.dx = 0;
                //entityMov.dy = 0;
            }
            iter.remove();
        }

    }

    @Override
    public String getSimpleName() {
        return null;
    }
}

//        for (UUID entity : allCollisions) {
//            Components.Collision collision = mGameView.mEntityManager.getComponent(entity, Components.Collision.class);
//
//            // Bullets are hazardous to enemies and mushrooms
//
//            // Our entity collided with something hazardous to it.
//            // Mushroom is not a hazard
//            // Bullet is a hazard
//            // Centipede is a hazard
//            if (mGameView.mEntityManager.hasComponent(entity, Components.Hazard.class)) {
//                if (mGameView.mEntityManager.hasComponent(collision.collidedWith, Components.Health.class)) {
////                    // maybe this should be broken into a new system / event?
//                        Components.Hazard hazard = mGameView.mEntityManager.getComponent(entity, Components.Hazard.class);
//                        Components.Health health = mGameView.mEntityManager.getComponent(collision.collidedWith, Components.Health.class);
//                        health.setHitPoints(health.getHitPoints() - hazard.myDamage);
//                }
//            }
//
////            // Special Behaviour for Centipedes?
//            if (mGameView.mEntityManager.hasComponent(entity, Components.CentipedeID.class)) {
//                if (!mGameView.mEntityManager.hasComponent(collision.collidedWith, Components.Movable.class)) {
//                    //  Could be used for wall too???
//                    // Centipede collided with mushroom do special movement here.
//
//                }
//            } else {
//                // There was a collision it must be solid?
//                // Handle moving back
//                Components.Position entityPos = mGameView.mEntityManager.getComponent(entity, Components.Position.class);
//                Components.Movable entityMov = mGameView.mEntityManager.getComponent(entity, Components.Movable.class);
//                entityPos.setX(entityPos.x - entityMov.dx);
//                entityPos.setY(entityPos.y - entityMov.dy);
//                entityMov.dx = 0;
//                entityMov.dy = 0;
//            }
//            toKill.put(entity, collision);
////            if (mGameView.mEntityManager.hasComponent(entity, Components.Collision.class)) {
////                Log.wtf("fuck","yyyy????");
////            }
//        }
//
//        for (UUID all : toKill.keySet()) {
//            mGameView.mEntityManager.removeComponent(all, toKill.get(all));
//        }
//        toKill.clear();
