package editmode;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.util.pathfinding.Path;

public class Passenger {

	public int ageBracket;
	public static final int CHILD = 0;
	public static final int TEENAGER = 1;
	public static final int ADULT = 2;
	public static final int OAP = 4;
	public int age;
	
	public int reasonForTravel;
	public static final int HOLIDAY = 0;
	public static final int BUSINESS = 1;
	public static final int ONE_WAY = 2;
	
	public int tilex;
	public int tiley;
	public double locx;
	public double locy;
	public int goalx;
	public int goaly;
	
	public int orientation;
	
	public double health;
	public static double maxHealth;
	public int sleep;
	public int hunger;
	public int happiness;

	public Path currentPath;
	public boolean calculatingPath;
	public boolean pathing;
	public Point delayedCalcPath;
	public int pathIter;
	public int stepGap;
	public int stepLength;
	public int stepsLeft;
	public int maxSteps;
	
	public boolean inStasis;
	public boolean asleep;
	
	public boolean eating;

	public boolean fireDying;
	public Animation fireDeath;
	
	public boolean radDying;
	public Animation radDeath;
	
	public int pressureDead;
	
	public boolean dead;
	
	
}
