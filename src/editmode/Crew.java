package editmode;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;



import Mains.Intpo;
import Mains.MemPoint;

public class Crew {
	
	public int type;
	public static final int HUMAN = 1000;
	public static final int ROBOT = 1001;
	
	public int tilex;
	public int tiley;
	public double locx;
	public double locy;
	
	public int goalx;
	public int goaly;
	
	public int repairx;
	public int repairy;
	
	public int orientation;
	
	public double health;
	public static double maxHealth;
	public int sleep;
	public int hunger;

	public Path currentPath;
	public boolean calculatingPath;
	public boolean pathing;
	public Point delayedCalcPath;
	public int pathIter;
	public int stepGap;
	public int stepLength;
	public int stepsLeft;
	public int maxSteps;
	
	public boolean typing;
	
	public boolean repairing;
	
	public boolean inStasis;
	
	public boolean extinguishing;
	public Point extinguishTarget;
	public Point[] co2;
	public int nextCo2;
	public int co2Iter;
	public boolean fireDying;
	public Animation fireDeath;
	
	public boolean radDying;
	public Animation radDeath;
	
	public int pressureDead;
	public int robotDead;
	
	public boolean dead;

	public Crew(int argX, int argY, double lx, double ly, int t) throws SlickException {
		this.type = t;
		
		if(t == Crew.HUMAN) {
			this.stepLength = 200;
		} else if(t == Crew.ROBOT) {
			this.robotDead = -1;
			this.stepLength = 400;
			this.maxSteps = 50;
			this.stepsLeft = this.maxSteps;
		}
		
		this.tilex = argX; 
		this.locx = lx;
		this.locy = ly;
		
		this.goalx = argX;
		this.tiley = argY; this.goaly = argY;
		
		this.orientation = 2;
		
		this.health = 1000;
		Crew.maxHealth = 1000;
		this.hunger = 1000;
		this.sleep = 1000;
		
		this.currentPath = null;
		this.calculatingPath = false;
		this.delayedCalcPath = new Point(-1, -1);
		this.pathing = false;
		this.stepGap = this.stepLength;;
		
		this.typing = false;
		this.extinguishing = false;
		this.co2 = new Point[5];
		this.nextCo2 = 1000;
		this.co2Iter = 0;
		this.extinguishTarget = new Point(-1, -1);
		this.fireDying = false;
		this.radDying = false;
		
		this.pressureDead = -1;
		this.dead = false;
		
		this.repairx = -1; this.repairy = -1;
		this.repairing = false;
		
		this.inStasis = false;
		
	}
	
	public Path path(int startx, int starty, int goalx, int goaly, int maxpath, int[][] tiles) {	
	
		this.pathIter = 0;
	    
		Path p = aStar(new Intpo(startx, starty), new Intpo(goalx, goaly));
		
	    if(p != null) {
		    if(this.pathIter < p.getLength()) {
				if(p.getX(this.pathIter)<this.tilex) this.orientation = 2;
				else if(p.getX(this.pathIter)>this.tilex) this.orientation = 3;
				else if(p.getY(this.pathIter)<this.tiley) this.orientation = 0;
				else if(p.getY(this.pathIter)>this.tiley) this.orientation = 1;
			}
	    }
	    
	    return p;
	}
	
	public static String toString(Crew c) {
		String s = "";
		
		s = c.tilex + "," + c.tiley + "," + c.locx + "," + c.locy + "," + c.type;

		return s;
	}
	
	public static int crewPrice(Crew c) {
		int g = 0;
		
		switch(c.type) {
		case HUMAN: g += 20; break;
		case ROBOT: g += 100; break;
		}
	
		return g;
	}
	
	public Path aStar(Intpo start, Intpo end) {
		ArrayList<MemPoint> reachable = new ArrayList<MemPoint>();
		MemPoint m = new MemPoint(start); 
		m.from = m;
		m.cost = 0;
		reachable.add(m);
		ArrayList<MemPoint> closed = new ArrayList<MemPoint>();
		
		while(reachable.size() != 0) {
			MemPoint i = bestDirection(reachable, end, closed);
			
			if(i == null) break;
				
			if(i.here.x == end.x && i.here.y == end.y) {
				return buildPath(i);
			}
			
			reachable.remove(i);
			if(!closed.contains(i)) {
				closed.add(i);
			}
			
			ArrayList<MemPoint> reachable2 = i.adjacents(closed);
			
			for(MemPoint j : reachable2) {
				if(!reachable.contains(j.here)) {
					
					reachable.add(j);
				}
				
				if(i.cost + 1 < j.cost) {
					j.from = i;
					j.cost = i.cost + 1;
				}
			}
		}
		
		return null;
		
	}
	
	public MemPoint bestDirection(ArrayList<MemPoint> reachable, Intpo goal, ArrayList<MemPoint> closed) {
		int min = Integer.MAX_VALUE;
		MemPoint best = null;
		
		for(MemPoint m : reachable) {
			int costToHere = m.cost;
			int costToGoal = manhattanDistance(m.here, goal);
			int totalCost = costToHere + costToGoal;
			
			if(totalCost < min) {
				boolean inClosed = false;
				for(MemPoint c : closed) {
					if(m.here.x == c.here.x && m.here.y == c.here.y) {
						inClosed = true;
						break;
					}
				}
				if(!inClosed) {
					min = totalCost;
					best = m;
				}
				
				
			}
		}
		
		return best;
	}

	public int manhattanDistance(Intpo here, Intpo goal) {
		
		int r = Math.abs(here.x - goal.x) + Math.abs(here.y - goal.y);
		
		return r;
	}

	public Path buildPath(MemPoint m) {
		Path p = new Path();
		
		while(m != null && m.from.here != m.here) {
			p.add(m.here);
			m = m.from;
		}
		
		Path copy = new Path();
		
		for(int i = p.steps.size()-1; i != -1; i--) {
			copy.add(p.steps.get(i));
		}
		
		return copy;
	}
}
	
	

