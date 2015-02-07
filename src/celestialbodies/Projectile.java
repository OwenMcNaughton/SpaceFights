package celestialbodies;

import java.awt.Point;
import java.util.Random;

import org.newdawn.slick.geom.Rectangle;

import Mains.PlayMode;
import editmode.Grid;
import editmode.Ship;

public class Projectile {

	public double locx, locy, velx, vely, maxaccel, maxspeed, angle, targx, targy;
	
	public Point spriteCoords;
	
	public Rectangle bounds;
	
	public int type;
	public static final int BULLET_0 = 0;
	public static final int BULLET_1 = 1;
	public static final int BULLET_2 = 2;
	public static final int BULLET_3 = 3;
	public static final int BULLET_4 = 4;
	
	public static final int MISSILE_0 = 11;
	public static final int MISSILE_1 = 12;
	public static final int MISSILE_2 = 12;
	public static final int MISSILE_3 = 13;
	public static final int MISSILE_4 = 14;
	
	public static final int BULLET_0_GRID = 100;
	
	public static final int MISSILE_0_GRID = 111;
	
	public int parent;
	
	public int lifetime;
	
	public int damage;
	public int nodeDestroyChance;
	public int fireStartChance;
	public int breachChance;
	public int blastRadius;
	
	public Projectile(double lx, double ly, double a, int t, int p) {
		this.locx = lx; 
		this.locy = ly;
		this.angle = a;
		this.type = t;
		this.parent = p;
		
		this.initMovement();
		this.initSpriteCoords();
		this.initBounds();
		
		this.initMisc();
		
	}
	
	public boolean update(int delta) {
		
		switch(this.type) {
		case BULLET_0: this.locx += this.velx*delta; this.locy += this.vely*delta;
				this.bounds = new Rectangle((float)this.locx+3, (float)this.locy+3, 2f, 2f);
				this.lifetime -= 1*delta; 
				if(this.bounds.intersects(Ship.shieldBubble)) {
					if(Grid.hit(this, PlayMode.systems[PlayMode.systemFocus].system.attackBots[this.parent])) {
						return true;
					}
				} break;
		case BULLET_0_GRID: this.locx += this.velx*delta; this.locy += this.vely*delta;
				this.bounds = new Rectangle((float)this.locx+3, (float)this.locy+3, 2f, 2f);
				this.lifetime -= 1*delta; break;
		case MISSILE_0:
				this.locx += this.velx*delta; this.locy += this.vely*delta;
				this.bounds = new Rectangle((float)(this.locx), (float)(this.locy), 8f, 8f); 
				this.lifetime -= 1*delta;
				if(this.bounds.intersects(Ship.shieldBubble)) {
					if(Grid.hit(this, PlayMode.systems[PlayMode.systemFocus].system.attackBots[this.parent])) {
						return true;
					}
				}
				break;
		case MISSILE_0_GRID: this.locx += this.velx*delta; this.locy += this.vely*delta;
				this.bounds = new Rectangle((float)this.locx+3, (float)this.locy+3, 2f, 2f);
				this.lifetime -= 1*delta; break;
		}
		
		return false;
	}


	private void initMovement() {
		double tangle = this.angle;
		int sector = 0;
		while(tangle > 90) {
			tangle -= 90;
			sector++;
		}
		
		tangle = Math.toRadians(tangle);
		
		switch(this.type) {
		case BULLET_0: this.maxspeed = .3;
						switch(sector) {
						case 0: this.velx = this.maxspeed*Math.sin(tangle);
								this.vely = -(Math.sqrt(this.maxspeed*this.maxspeed - velx*velx));
								break;
						case 1: this.velx = this.maxspeed*Math.sin(1.57-tangle);
								this.vely = Math.sqrt(this.maxspeed*this.maxspeed - velx*velx);
								break;
						case 2: this.velx = -(this.maxspeed*Math.sin(tangle));
								this.vely = Math.sqrt(this.maxspeed*this.maxspeed - velx*velx);
								break;
						case 3: this.velx = -(this.maxspeed*Math.sin(1.57-tangle));
								this.vely = -(Math.sqrt(this.maxspeed*this.maxspeed - velx*velx));
								break;
						} break;
		case BULLET_0_GRID: this.maxspeed = .7;
						switch(sector) {
						case 0: this.velx = this.maxspeed*Math.sin(tangle);
								this.vely = -(Math.sqrt(this.maxspeed*this.maxspeed - velx*velx));
								break;
						case 1: this.velx = this.maxspeed*Math.sin(1.57-tangle);
								this.vely = Math.sqrt(this.maxspeed*this.maxspeed - velx*velx);
								break;
						case 2: this.velx = -(this.maxspeed*Math.sin(tangle));
								this.vely = Math.sqrt(this.maxspeed*this.maxspeed - velx*velx);
								break;
						case 3: this.velx = -(this.maxspeed*Math.sin(1.57-tangle));
								this.vely = -(Math.sqrt(this.maxspeed*this.maxspeed - velx*velx));
								break;
						} break;
						
		case MISSILE_0: this.maxspeed = .2; this.maxaccel = .01;
						switch(sector) {
						case 0: this.velx = (this.maxspeed*Math.sin(tangle));
								this.vely = -(Math.sqrt(this.maxspeed*this.maxspeed - velx*velx));
								break;
						case 1: this.velx = (this.maxspeed*Math.sin(1.57-tangle));
								this.vely = (Math.sqrt(this.maxspeed*this.maxspeed - velx*velx));
								break;
						case 2: this.velx = -((this.maxspeed*Math.sin(tangle)));
								this.vely = (Math.sqrt(this.maxspeed*this.maxspeed - velx*velx));
								break;
						case 3: this.velx = -((this.maxspeed*Math.sin(1.57-tangle)));
								this.vely = -((Math.sqrt(this.maxspeed*this.maxspeed - velx*velx)));
								break;
						} this.targx = Ship.locx; this.targy = Ship.locy;
						break;
		case MISSILE_0_GRID: this.maxspeed = .7;
						switch(sector) {
						case 0: this.velx = this.maxspeed*Math.sin(tangle);
								this.vely = -(Math.sqrt(this.maxspeed*this.maxspeed - velx*velx));
								break;
						case 1: this.velx = this.maxspeed*Math.sin(1.57-tangle);
								this.vely = Math.sqrt(this.maxspeed*this.maxspeed - velx*velx);
								break;
						case 2: this.velx = -(this.maxspeed*Math.sin(tangle));
								this.vely = Math.sqrt(this.maxspeed*this.maxspeed - velx*velx);
								break;
						case 3: this.velx = -(this.maxspeed*Math.sin(1.57-tangle));
								this.vely = -(Math.sqrt(this.maxspeed*this.maxspeed - velx*velx));
								break;
						} break;
		}
	}
	
	private void initSpriteCoords() {
		switch(this.type) {
		case BULLET_0: this.spriteCoords = new Point(0, 0); break;
		case BULLET_0_GRID: this.spriteCoords = new Point(1, 0); break;
		case MISSILE_0: this.spriteCoords = new Point(0, 1); break;
		case MISSILE_0_GRID: this.spriteCoords = new Point(1, 1); break;
		}
	}
	
	private void initBounds() {
		switch(this.type) {
		case BULLET_0: this.bounds = new Rectangle((float)this.locx+4, (float)this.locy+3, 2f, 2f); break;
		case BULLET_0_GRID: this.bounds = new Rectangle((float)this.locx+3, (float)this.locy+2, 2f, 4f); break;
		case MISSILE_0: this.bounds = new Rectangle((float)(this.locx+2), (float)(this.locy+2), 3f, 6f); break;
		case MISSILE_0_GRID: this.bounds = new Rectangle((float)(this.locx), (float)(this.locy), 8f, 8f); break;
		}
	}
	
	private void initMisc() {
		switch(this.type) {
		case BULLET_0:	this.lifetime = 1000; this.damage = 5; this.nodeDestroyChance = 75;
						this.fireStartChance = 0; this.breachChance = 0; this.blastRadius = 0;
						break;
		case BULLET_0_GRID: this.lifetime = 500;
		case MISSILE_0:	this.lifetime = 5000; this.damage = 20; this.nodeDestroyChance = 100;
						this.fireStartChance = 80; this.breachChance = 0; this.blastRadius = 1;
						break;
		case MISSILE_0_GRID: this.lifetime = 500;
						
		}
	}
	
}
