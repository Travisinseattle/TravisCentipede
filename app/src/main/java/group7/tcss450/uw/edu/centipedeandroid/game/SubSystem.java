package group7.tcss450.uw.edu.centipedeandroid.game;

/**
 * Standard design: c.f. http://entity-systems.wikidot.com/rdbms-with-code-in-systems
 */

public interface SubSystem
{
	void processOneGameTick( long lastFrameTime );
    
    /**
	 * Mostly used for debugging - check which system is firing, what order
	 * systems are firing in, etc
	 * 
	 * @return the human-readable name of this system
	 */
	String getSimpleName();
}