package celestialbodies;

import java.util.Random;

import org.newdawn.slick.geom.Rectangle;

import Mains.Intpo;

public class Shop {

	public double orbitalRadius;
	public double orbitalAngle;
	public double rotation;
	
	public double locx, velx, locy, vely;
	public double maxvel;
	
	public int parent;
	
	public int humans, robots, humanCost, robotCost;
	public int fuel, fuelCost;
	
	public int flashIter;
	
	public Intpo spriteCoords;
							
	public Rectangle bounds;
	
	public Shop(int p, double x, double y) {
		Random gen = new Random();
		
		this.parent = p;
		
		this.orbitalRadius = 40;
		this.orbitalAngle = gen.nextInt(360);
		
		this.flashIter = 0;
		
		this.spriteCoords = new Intpo(6, 3);
		
		this.initLoc(x, y);
		
		this.rotation = 0;
	}
	
	public void update(int delta) {
		this.flashIter += 1*delta;
		
		if(this.flashIter < 1000) {
			this.spriteCoords = new Intpo(6, 3);
		} else {
			this.spriteCoords = new Intpo(7, 3);
		}
		
		if(this.flashIter > 2000) {
			this.flashIter = 0;
		}
	}
	
	public void initLoc(double x, double y) {
		double xdist = this.orbitalRadius * Math.sin(Math.toRadians(this.orbitalAngle));
		double ydist = this.orbitalRadius * Math.sin(Math.toRadians(180 - 90 - this.orbitalAngle));
		
		this.locx = x + xdist;
		this.locy = y + ydist;
		
		this.bounds = new Rectangle((float)this.locx, (float)this.locy, 64f, 64f);
	}
}
