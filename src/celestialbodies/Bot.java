package celestialbodies;
import java.awt.Point;
import java.util.Random;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import editmode.Tile;


public class Bot {

	public double locx, locy, velx, vely, angle, maxaccel, maxspeed, targx, targy;
	
	public double centerx, centery;
	
	public int width, height, planetTarget;
	
	public Rectangle bounds;
	public Point spriteCoords;
	
	public boolean avoidance;
	public int inOrbit;
	
	int type;
	public static final int SMALL_TRADER = 0;
	public static final int MEDIUM_TRADER = 1;
	public static final int LARGE_TRADER = 2;
	public static final int SMALL_MINER = 3;
	public static final int MEDIUM_MINER = 4;
	public static final int LARGE_MINER = 5;
	
	public static final int SMALL_FIGHTER = 100;
	public static final int MEDIUM_FIGHTER = 101;
	public static final int LARGE_FIGHTER = 102;
	
	public int maxCargo;
	
	public int parent;
	public int relationship;
	
	public double titanium;
	public double uranium;
	public double fuel;
	public double gold;
	
	public Circle vision;
	public Circle attackRadius;
	
	public Projectile[] projectiles;
	public int accuracy;
	public int targetTile;
	
	public int fireTick1, fireTick2, fireTick1Window, fireTick1Max, fireTick2Max;
	
	public boolean shipLocked;
	
	public boolean targeted;
	
	Random gen;
	
	public Bot(double x, double y, int t, int p) {
		gen = new Random();
		
		this.locx = x;
		this.locy = y;
		
		this.angle = 0;
		
		this.parent = p;
		
		this.type = t;
		this.planetTarget = p;
		
		this.targx = 0;
		this.targy = 0;
		
		this.avoidance = false;
		this.inOrbit = -1;
		
		this.titanium = 0;
		this.uranium = 0;
		this.fuel = 0;
		this.gold = 0;
		
		this.shipLocked = false;
		this.targeted = false;
		
		this.initEngines();
		this.initBounds();
		this.spriteCoordify();
		this.initWeapons();
		
	}
	
	private void initWeapons() {
		switch(this.type) {
		case SMALL_FIGHTER: this.projectiles = new Projectile[2];  
							this.fireTick1 = 0; this.fireTick2 = 0; this.fireTick1Window = 1800;
							this.fireTick1Max = 2000; this.fireTick2Max = 100; 
							this.accuracy = 10; this.targetTile = Tile.CORE; break;
		case MEDIUM_FIGHTER:this.projectiles = new Projectile[4];  
							this.fireTick1 = 0; this.fireTick2 = 0; this.fireTick1Window = 1700;
							this.fireTick1Max = 2000; this.fireTick2Max = 75; 
							this.accuracy = 30; this.targetTile = Tile.CORE; break;
		case LARGE_FIGHTER: this.projectiles = new Projectile[1];  
							this.fireTick1 = 0; this.fireTick2 = 0; this.fireTick1Window = 800;
							this.fireTick1Max = 1000; this.fireTick2Max = 200; 
							this.accuracy = 100; this.targetTile = Tile.CORE; break;
		}
		
	}
	
	private void initEngines() {
		switch(this.type) {
		case SMALL_TRADER: this.maxaccel = 0.0002d; this.maxspeed = 0.07d; this.maxCargo = 100; break;
		case MEDIUM_TRADER: this.maxaccel = 0.0002d; this.maxspeed = 0.07d; this.maxCargo = 300; break;
		case LARGE_TRADER: this.maxaccel = 0.0002d; this.maxspeed = 0.07d; this.maxCargo = 1000; break;
		case SMALL_MINER: this.maxaccel = 0.0002d; this.maxspeed = 0.07d; this.maxCargo = 100; break;
		case MEDIUM_MINER: this.maxaccel = 0.0002d; this.maxspeed = 0.07d; this.maxCargo = 300; break;
		case LARGE_MINER: this.maxaccel = 0.0002d; this.maxspeed = 0.07d; this.maxCargo = 1000; break;
		case SMALL_FIGHTER: this.maxaccel = 0.0002d; this.maxspeed = 0.07d; this.maxCargo = 100; break;
		case MEDIUM_FIGHTER: this.maxaccel = 0.0002d; this.maxspeed = 0.07d; this.maxCargo = 100; break;
		case LARGE_FIGHTER: this.maxaccel = 0.0002d; this.maxspeed = 0.07d; this.maxCargo = 100; break;
		}
		
		this.maxaccel /= 3;
		
	}

	private void spriteCoordify() {
		switch(this.type) {
		case SMALL_TRADER: this.spriteCoords = new Point(0, 0); break;
		case MEDIUM_TRADER: this.spriteCoords = new Point(1, 0); break;
		case LARGE_TRADER: this.spriteCoords = new Point(2, 0); break;
		case SMALL_MINER: this.spriteCoords = new Point(0, 1); break;
		case MEDIUM_MINER: this.spriteCoords = new Point(1, 1); break;
		case LARGE_MINER: this.spriteCoords = new Point(2, 1); break;
		case SMALL_FIGHTER: this.spriteCoords = new Point(0, 2); break;
		case MEDIUM_FIGHTER: this.spriteCoords = new Point(1, 2); break;
		case LARGE_FIGHTER: this.spriteCoords = new Point(2, 2); break;
		}
		
	}
	
	private void initBounds() {
		this.centerx = this.locx + 16; this.centery = this.locy + 16;
		switch(this.type) {
		case SMALL_TRADER: this.width = 6; this.height = 9; 
				this.bounds = new Rectangle((float)this.locx, (float)this.locy, this.width, this.height); 
				break;
		case MEDIUM_TRADER: this.width = 8; this.height = 12; 
				this.bounds = new Rectangle((float)this.locx, (float)this.locy, this.width, this.height); 
				break;
		case LARGE_TRADER: this.width = 10; this.height = 18; 
				this.bounds = new Rectangle((float)this.locx, (float)this.locy, this.width, this.height); 
				break;
		case SMALL_MINER: this.width = 6; this.height = 9; 
				this.bounds = new Rectangle((float)this.locx, (float)this.locy, this.width, this.height); 
				break;
		case MEDIUM_MINER: this.width = 8; this.height = 12; 
				this.bounds = new Rectangle((float)this.locx, (float)this.locy, this.width, this.height); 
				break;
		case LARGE_MINER: this.width = 14; this.height = 16; 
				this.bounds = new Rectangle((float)this.locx, (float)this.locy, this.width, this.height); 
				break;
		case SMALL_FIGHTER: this.width = 12; this.height = 10; 
				this.bounds = new Rectangle((float)this.locx+9, (float)this.locy+12, this.width, this.height);
				this.vision = new Circle((float)this.centerx, (float)this.centery, 200f);
				this.attackRadius = new Circle((float)this.centerx, (float)this.centery, 50+gen.nextInt(50));
				break;		
		case MEDIUM_FIGHTER: this.width = 16; this.height = 13; 
				this.bounds = new Rectangle((float)this.locx+8, (float)this.locy+9, this.width, this.height);
				this.vision = new Circle((float)this.centerx, (float)this.centery, 200f);
				this.attackRadius = new Circle((float)this.centerx, (float)this.centery, 50+gen.nextInt(40));
				break;		
		case LARGE_FIGHTER: this.width = 20; this.height = 17; 
				this.bounds = new Rectangle((float)this.locx+4, (float)this.locy+6, this.width, this.height);
				this.vision = new Circle((float)this.centerx, (float)this.centery, 200f);
				this.attackRadius = new Circle((float)this.centerx, (float)this.centery, 80+gen.nextInt(20));
				break;		
		}
		
	}
	
	public void rotate() {
		double tempa = this.angle;
		
		this.angle = (float)(180/Math.PI) * (float)Math.asin((Math.abs(this.locx-this.targx))/
					 (Math.sqrt((this.targx-this.locx)*(targx-this.locx) + 
					 (this.targy-this.locy)*(this.targy-this.locy))));
		
		if(Double.isNaN(this.angle)) {
			this.angle = tempa;
		}
	
		if(this.targx > this.locx) {
			if(this.targy > this.locy) {
				this.angle = 90 + (90 - this.angle);
			} else if(targy > this.locy) {
				//
			} 
		} else if(this.targy > this.locy) {
			this.angle += 180;
		} else {
			this.angle = 270 + (90 - this.angle);
		}
	}

	public void accelerate(char wasd, int delta) {
		double tangle = this.angle;
		while(tangle > 90) {
			tangle = tangle - 90;
		}
		if(wasd == 's') {
			if(velx < .01d && velx > -.01d) velx = 0.000000d;
			if(vely < .01d && vely > -.01d) vely = 0.000000d;
			if(velx > 0.000000d) velx -= maxaccel*delta;
				else if(velx < 0) velx += maxaccel*delta;
				else velx = 0;
			if(vely > 0.000000d) vely -= maxaccel*delta;
				else if(vely < 0) vely += maxaccel*delta;
				else vely = 0;
		} else if(wasd == 't') {
			if(this.velx > 0.000000d) this.velx -= (this.maxaccel/5)*delta;
				else if(this.velx < 0.00000d) this.velx += (this.maxaccel/5)*delta;
				else this.velx = 0;
			if(this.vely > 0.00000d) this.vely -= (this.maxaccel/5)*delta;
				else if(this.vely < 0.00000d) vely += (this.maxaccel/5)*delta;
				else this.vely = 0;
		} else if(wasd == 'w') {
			if(this.angle > 0 && this.angle < 90) {
				this.vely -= Math.sin((90-tangle)*(Math.PI/180))*this.maxaccel*delta;
				this.velx += Math.sin((tangle)*(Math.PI/180))*this.maxaccel*delta;
			} else if(this.angle > 90 && this.angle < 180) {
				this.vely += Math.sin(tangle*(Math.PI/180))*this.maxaccel*delta;
				this.velx += Math.sin((90-tangle)*(Math.PI/180))*this.maxaccel*delta;
			} else if(this.angle > 180 && this.angle < 270) {
				this.vely += Math.sin((90-tangle)*(Math.PI/180))*this.maxaccel*delta;
				this.velx -= Math.sin(tangle*(Math.PI/180))*this.maxaccel*delta;
			} else {
				this.vely -= Math.sin(tangle*(Math.PI/180))*this.maxaccel*delta;
				this.velx -= Math.sin((90-tangle)*(Math.PI/180))*this.maxaccel*delta;
			}
		} else if(wasd == 'a') {
			if(this.angle > 0 && this.angle < 90) {
				this.vely += Math.sin((tangle)*(Math.PI/180))*this.maxaccel*delta;
				this.velx -= Math.sin((90-tangle)*(Math.PI/180))*this.maxaccel*delta;
			} else if(this.angle > 90 && this.angle < 180) {
				this.vely -= Math.sin(90-tangle*(Math.PI/180))*this.maxaccel*delta;
				this.velx -= Math.sin((tangle)*(Math.PI/180))*this.maxaccel*delta;
			} else if(this.angle > 180 && this.angle < 270) {
				this.vely -= Math.sin((tangle)*(Math.PI/180))*this.maxaccel*delta;
				this.velx += Math.sin(90-tangle*(Math.PI/180))*this.maxaccel*delta;
			} else {
				this.vely += Math.sin(90-tangle*(Math.PI/180))*this.maxaccel*delta;
				this.velx += Math.sin((tangle)*(Math.PI/180))*this.maxaccel*delta;
			}
		}
		
	}
	
	public void updateVelocity(int delta) {
		if(this.velx > this.maxspeed) this.velx = this.maxspeed;
		else if(this.velx < -this.maxspeed) this.velx = -this.maxspeed;
		if(this.vely > this.maxspeed) this.vely = this.maxspeed;
		else if(this.vely < -this.maxspeed) this.vely = -this.maxspeed;

		this.locx += this.velx*delta;
		this.locy += this.vely*delta;
		
		this.centerx = this.locx+16;
		this.centery = this.locy+16;
		
		this.bounds.setCenterX((float)this.locx);
		this.bounds.setCenterY((float)this.locy);
		
		if(this.vision != null) {
			this.vision.setCenterX((float)this.locx);
			this.vision.setCenterY((float)this.locy);
		}
		
		if(this.attackRadius != null) {
			this.attackRadius.setCenterX((float)this.locx);
			this.attackRadius.setCenterY((float)this.locy);
		}
	}
	
	public void fire(int botIndex) {
		switch(this.type) {
		case SMALL_FIGHTER:
			for(int j = 0; j != this.projectiles.length; j++) {
				if(this.projectiles[j] == null) {
					this.projectiles[j] = new Projectile(this.locx, this.locy, 
							this.angle, Projectile.BULLET_0, botIndex); break;
				}
			} 
			break;
		case MEDIUM_FIGHTER:
			for(int j = 0; j != this.projectiles.length; j++) {
				if(this.projectiles[j] == null) {
					this.projectiles[j] = new Projectile(this.locx, this.locy, 
							this.angle, Projectile.BULLET_0, botIndex); break;
				}
			} 
			break;
		case LARGE_FIGHTER:
			for(int j = 0; j != this.projectiles.length; j++) {
				if(this.projectiles[j] == null) {
					this.projectiles[j] = new Projectile(this.locx, this.locy, 
							this.angle, Projectile.MISSILE_0, botIndex); break;
				}
			} 
			break;
		}
	}
}
