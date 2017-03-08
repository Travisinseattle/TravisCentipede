package group7.tcss450.uw.edu.centipedeandroid.game.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import group7.tcss450.uw.edu.centipedeandroid.R;
import group7.tcss450.uw.edu.centipedeandroid.game.Component;
import group7.tcss450.uw.edu.centipedeandroid.game.EntityFactory;
import group7.tcss450.uw.edu.centipedeandroid.game.GameView;
import group7.tcss450.uw.edu.centipedeandroid.game.MetaEntity;
import group7.tcss450.uw.edu.centipedeandroid.game.SubSystem;
import group7.tcss450.uw.edu.centipedeandroid.game.component.Components;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.EntityManager;

/**
 * Created by nicholas on 3/2/17.
 */

public class DestroySystem extends SubSystem {

    public DestroySystem(GameView theGameView) {
        super(theGameView);
    }
    @Override
    public void processOneGameTick(long lastFrameTime) {
        Set<UUID> allAlive = mGameView.mEntityManager.getAllEntitiesPossessingComponent(Components.Health.class);
        Iterator<UUID> alive = allAlive.iterator();
        while (alive.hasNext()) {
            UUID entity = alive.next();
            Components.Health health = mGameView.mEntityManager.getComponent(entity, Components.Health.class);
            if (health.getHitPoints() <= 0) {
                if (mGameView.mEntityManager.hasComponent(entity, Components.SegmentComponent.class)) {
                    Components.SegmentComponent segmentComponent = mGameView.mEntityManager.getComponent(entity,Components.SegmentComponent.class);

                    UUID[] ids = mGameView.mEntityManager.getComponent(segmentComponent.myParentEntity, Components.CentipedeID.class).myIDs;
//                    EntityFactory.createMushroom(mGameView.mEntityManager.getComponent(entity, Components.Position.class));
//                    if (ids.length > 0) {
                        Components.Movable previousMov = mGameView.mEntityManager.getComponent(entity, Components.Movable.class);
                        Components.Direction previousDir = mGameView.mEntityManager.getComponent(entity, Components.Direction.class);


                        int newEntity1 = -1;
                        int newEntity2 = -1;

                        for (int i = 0; i < ids.length; ++i) {
                            if (ids[i] == entity) {
                                newEntity1 = i - 1;
                                newEntity2 = i + 1;
                                break;
                            }
                        }

                        if (newEntity1 != -1) {
                            UUID[] newValues = new UUID[newEntity2 - 1];
                            MetaEntity centipede = EntityFactory.createCentipede(newValues);
                            for (int i = 0, j = 0; i < newEntity1 + 1; ++i, ++j) {
                                newValues[j] = ids[i];
                                mGameView.mEntityManager.getComponent(newValues[j], Components.SegmentComponent.class).myParentEntity = centipede.entity;
                            }
                            // Create a new centipede
                            UUID head = newValues[0];
                            if(mGameView.mEntityManager.hasComponent(head,Components.SegmentMovable.class)) {
                                Components.SegmentMovable segmentMovable = mGameView.mEntityManager.getComponent(head,Components.SegmentMovable.class);
                                Components.Movable movable = mGameView.mEntityManager.getComponent(head, Components.Movable.class);
                                Components.CAndroidDrawable draw = mGameView.mEntityManager.getComponent(head, Components.CAndroidDrawable.class);
                                draw.setDrawable(R.drawable.centipedehead);
                                movable.dx = segmentMovable.dx;
                                movable.dy = segmentMovable.dy;
//                                Components.Direction direction = mGameView
                                mGameView.mEntityManager.removeComponent(head, segmentMovable);

                            }
                            centipede.get(Components.CentipedeID.class).myIDs = newValues;
                        } else {
                            newEntity1 = 1;
                        }

                        if (newEntity2 != ids.length) {

                            UUID[] newValues2 =  new UUID[ids.length - newEntity2];
                            MetaEntity centipede = EntityFactory.createCentipede(newValues2);
                            for (int i = newEntity2, j= 0; i < ids.length; ++i, ++j) {
                                newValues2[j] = ids[i];
                                mGameView.mEntityManager.getComponent(newValues2[j], Components.SegmentComponent.class).myParentEntity = centipede.entity;

                            }
                            // Create a new Centipede
                            UUID head = newValues2[0];
                            if(mGameView.mEntityManager.hasComponent(head,Components.SegmentMovable.class)) {
                                Components.SegmentMovable segmentMovable = mGameView.mEntityManager.getComponent(head,Components.SegmentMovable.class);
                                Components.Movable movable = mGameView.mEntityManager.getComponent(head, Components.Movable.class);
                                Components.CAndroidDrawable draw = mGameView.mEntityManager.getComponent(head, Components.CAndroidDrawable.class);
                                draw.setDrawable(R.drawable.centipedehead);
                                movable.dx = segmentMovable.dx;
                                movable.dy = segmentMovable.dy;
//                                Components.Direction direction = mGameView
                                mGameView.mEntityManager.removeComponent(head, segmentMovable);

                            }
                            centipede.get(Components.CentipedeID.class).myIDs = newValues2;

                        }

//                        }
                    mGameView.mEntityManager.killEntity(segmentComponent.myParentEntity);
                }
                if (mGameView.mEntityManager.hasComponent(entity, Components.Score.class)) {
                    Components.Score score = mGameView.mEntityManager.getComponent(entity, Components.Score.class);
                    mGameView.mScore += score.myScore;
                }
                alive.remove();
                mGameView.mEntityManager.killEntity(entity);
            }

        }
//        for (UUID alive : allAlive) {
//            Components.Health health = mGameView.mEntityManager.getComponent(alive, Components.Health.class);
//            if (health.getHitPoints() == 0) {
//                mGameView.mEntityManager.killEntity(alive);
//            }
//        }

    }

    @Override
    public String getSimpleName() {
        return null;
    }
}
