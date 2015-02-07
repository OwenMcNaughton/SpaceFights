package celestialbodies;

import java.awt.Point;
import java.util.Random;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import Mains.PlayMode;
import editmode.Ship;

public class Moon {

	public int type;
	public static final int TITANIUM = 0;
	public static final int URANIUM = 1;
	public static final int FUEL = 2;
	public static final int GOLD = 3;
	
	public int size;
	public static final int SMALL = 1;
	public static final int MEDIUM = 3;
	public static final int LARGE = 10;
	
	public int parent;

	public double orbitalRadius;
	public double orbitalAngle;
	
	public double locx, velx, centerx;
	public double locy, vely, centery;
	public double maxvel;
	
	public boolean shipLocked;
	public boolean exists;
	
	public Circle bounds;
	
	public Point spriteCoords;
	public Circle orbit;
	
	public Random gen;
	
	public Moon(int t, int s, int p, double x, double y) {
		this.gen = new Random();
			
		this.type = t;
		if(s != -1) {
			this.size = s;
		} else {
			int a = gen.nextInt(3);
			if(a == 0) {
				this.size = Moon.SMALL;
			} else if(a == 1) {
				this.size = Moon.MEDIUM;
			} else {
				this.size = Moon.LARGE;
			}
		}
		
		this.parent = p;
		
		this.velx = 0;
		this.vely = 0;
		this.maxvel = .1;
		
		this.initOrbitalRadius();

		this.orbitalAngle = gen.nextInt(360);
		
		this.shipLocked = false;
		this.exists = true;
		
		this.initLoc(x, y);
		this.spritify();
	}
	
	private void initOrbitalRadius() {
		Random gen = new Random();
		switch(this.parent) {
		case Planet.GAS_GIANT: this.orbitalRadius = 44 + gen.nextInt(40); break;
		default: this.orbitalRadius = 24 + gen.nextInt(24); break;
		}
		
	}
	
	public void initLoc(double x, double y) {
		
		double xdist = this.orbitalRadius * Math.sin(Math.toRadians(this.orbitalAngle));
		double ydist = this.orbitalRadius * Math.sin(Math.toRadians(180 - 90 - this.orbitalAngle));
		
		this.locx = x + xdist;
		this.locy = y + ydist;
		
		this.centerx = this.locx + 16;
		this.centery = this.locy + 16;
		
		switch(this.size) {
		case SMALL: this.bounds = new Circle((float)this.centerx, (float)this.centery, 2, 2); break;
		case MEDIUM: this.bounds = new Circle((float)this.centerx, (float)this.centery, 4, 4); break;
		case LARGE: this.bounds = new Circle((float)this.centerx, (float)this.centery, 8, 8); break;
		} 
		
		
		
	}

	private void spritify() {
		switch(this.type) {
		case TITANIUM: switch(this.size) {
				case SMALL: this.spriteCoords = new Point(5,4); break;
				case MEDIUM: this.spriteCoords = new Point(6,4); break;
				case LARGE: this.spriteCoords = new Point(7,4); break;
			}  break;
		case URANIUM: switch(this.size) {
				case SMALL: this.spriteCoords = new Point(5,6); break;
				case MEDIUM: this.spriteCoords = new Point(6,6); break;
				case LARGE: this.spriteCoords = new Point(7,6); break;
			}  break;
		case FUEL: switch(this.size) {
				case SMALL: this.spriteCoords = new Point(5,5); break;
				case MEDIUM: this.spriteCoords = new Point(6,5); break;
				case LARGE: this.spriteCoords = new Point(7,5); break;
			}  break;
		case GOLD: switch(this.size) {
				case SMALL: this.spriteCoords = new Point(5,7); break;
				case MEDIUM: this.spriteCoords = new Point(6,7); break;
				case LARGE: this.spriteCoords = new Point(7,7); break;
			}  break;
		
		}
		
	}

	public boolean goToShip(Ship s, int delta) {
		if(Ship.tractorRadius.intersects(this.bounds)) {
			if(Ship.radius.intersects(this.bounds)) {
				switch(this.type) {
				case TITANIUM: switch(this.size) {
								case SMALL: PlayMode.titanium += .3; break;
								case MEDIUM: PlayMode.titanium += .7; break;
								case LARGE: PlayMode.titanium += 2; break;
							} break;
				case URANIUM: switch(this.size) {
								case SMALL: PlayMode.uranium += .3; break;
								case MEDIUM: PlayMode.uranium += .7; break;
								case LARGE: PlayMode.uranium += 2; break;
							} break;
				case FUEL: switch(this.size) { 
								case SMALL: PlayMode.fuel += .3; break;
								case MEDIUM: PlayMode.fuel += .7; break;
								case LARGE: PlayMode.fuel += 2; break;
							} break;
				case GOLD: switch(this.size) {
								case SMALL: PlayMode.gold += .3; break;
								case MEDIUM: PlayMode.gold += .7; break;
								case LARGE: PlayMode.gold += 2; break;
							} break;
				}
				if(PlayMode.moonCapture.isPlaying()) {
					PlayMode.moonCapture.stop();
				}
				PlayMode.moonCapture.playAsSoundEffect((float)((gen.nextInt(10)+10)/100f), .2f, false);
				this.exists = false;	
				return true;
				
			} else {
				if(Ship.locx > this.centerx) {
					if(this.velx < this.maxvel) {
						this.velx += Ship.mineStrength*delta;
					}
				} else {
					if(this.velx > -this.maxvel) {
						this.velx -= Ship.mineStrength*delta;
					}
				}
				
				if(Ship.locy > this.centery) {
					if(this.vely < this.maxvel) {
						this.vely += Ship.mineStrength*delta;
					}
				} else {
					if(this.vely > -this.maxvel) {
						this.vely -= Ship.mineStrength*delta;
					}
				}
				
				
			}
			
		}
		
		this.locx += velx*delta;
		this.locy += vely*delta;
		
		this.centerx = this.locx + 16;
		this.centery = this.locy + 16;
		
		switch(this.size) {
		case SMALL: this.bounds = new Circle((float)this.centerx, (float)this.centery, 2, 2); break;
		case MEDIUM: this.bounds = new Circle((float)this.centerx, (float)this.centery, 4, 4); break;
		case LARGE: this.bounds = new Circle((float)this.centerx, (float)this.centery, 8, 8); break;
		} 
		
		return false;
	}
}
