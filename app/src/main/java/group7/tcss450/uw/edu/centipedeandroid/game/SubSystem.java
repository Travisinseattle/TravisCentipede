package group7.tcss450.uw.edu.centipedeandroid.game;

import android.view.SurfaceView;

import group7.tcss450.uw.edu.centipedeandroid.game.manager.EntityManager;
import group7.tcss450.uw.edu.centipedeandroid.game.manager.GameManager;

/**
 * Standard design: c.f. http://entity-systems.wikidot.com/rdbms-with-code-in-systems
 */

public abstract class SubSystem {
	/** View for the game engine */
	protected GameView mGameView;

	/**
	 * Main Constructor for the class.
	 *
	 * @param theGameView
	 */
	public SubSystem(GameView theGameView) {
		mGameView = theGameView;
	}

	/**
	 * Method that process one game tick.
	 */
	public abstract void processOneGameTick( long lastFrameTime );
    
    /**
	 * Mostly used for debugging - check which system is firing, what order
	 * systems are firing in, etc
	 * 
	 * @return the human-readable name of this system
	 */
	 public abstract String getSimpleName();
}