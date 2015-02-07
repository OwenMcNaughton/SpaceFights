package editmode;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Weapon {

	public int tilex, tiley, type;
	
	public Point[] spriteCoords;
	
	public static final int RAILGUN_0 = 0;
	
	public static final int LASER_0 = 10;
	
	public int orientation;
	
	public double angle;
	public boolean angleDirection;
	public double maxangle, minangle;
	
	public boolean active;
	public boolean broken;
	
	public boolean firing;
	public int fireFrame;
	public int fireFrameIter;
	public int fireFrameIterMax;
	
	
	public Weapon(int x, int y, int t, int o) {
		
		this.tilex = x;
		this.tiley = y;
		this.type = t;
		this.orientation = o;
		
		this.spritify();
		
		this.initRotation();
		
		this.active = false;
		
		this.broken = false;
		this.firing = false;
		
		this.fireFrame = -1;
		this.fireFrameIter = -1;
		
	}
	
	public Graphics draw(Graphics g, Rectangle r) {
		
		if(!this.firing && !this.broken) {
			Image a = Grid.weaponSheet.getSprite(this.spriteCoords[0].x, this.spriteCoords[0].y);
	
			Grid.weaponSheet.endUse();
			a.setRotation((float)this.angle);
			g.drawImage(a, (int)r.getCenterX()-32, (int)r.getCenterY()-32);
			Grid.weaponSheet.startUse();
		}
		
		if(this.firing) {
			Image a = Grid.weaponSheet.getSprite(this.spriteCoords[fireFrame].x, this.spriteCoords[fireFrame].y);
			
			Grid.weaponSheet.endUse();
			a.setRotation((float)this.angle);
			g.drawImage(a, (int)r.getCenterX()-32, (int)r.getCenterY()-32);
			Grid.weaponSheet.startUse();
		}
		
		return g;
	}
	
	public Graphics drawNonSheet(Graphics g, Rectangle r) {
		if(this.firing && this.type == LASER_0) {
			float linendx = 0; float linendy = 0;
			
			double tangle = this.angle;
			int sector = 0;
			while(tangle > 90) {
				tangle -= 90;
				sector++;
			}
			
			tangle = Math.toRadians(tangle);
			float laserLength = 2000;
			switch(sector) {
			case 0: linendx = (float) (laserLength*Math.sin(tangle));
					linendy = (float) -(Math.sqrt(laserLength*laserLength - linendx*linendx));
					break;
			case 1: linendx = (float) (laserLength*Math.sin(1.57-tangle));
					linendy = (float) Math.sqrt(laserLength*laserLength - linendx*linendx);
					break;
			case 2: linendx = (float) -(laserLength*Math.sin(tangle));
					linendy = (float) Math.sqrt(laserLength*laserLength - linendx*linendx);
					break;
			case 3: linendx = (float) -(laserLength*Math.sin(1.57-tangle));
					linendy = (float) -(Math.sqrt(laserLength*laserLength - linendx*linendx));
					break;
			}
			
			switch(this.fireFrame) {
			case 2: g.setColor(new Color(128, 194, 225)); break;
			case 3: g.setColor(new Color(138, 204, 235)); break;
			case 4: g.setColor(new Color(148, 214, 245)); break;
			case 5: g.setColor(new Color(158, 224, 255)); break;
			case 6: g.setColor(new Color(148, 214, 245)); break;
			case 7: g.setColor(new Color(138, 204, 235)); break;
			}
			g.drawLine(r.getCenterX(), r.getCenterY(), 
					r.getCenterX()+linendx, r.getCenterY()+linendy);
		}
		
		return g;
	}
	
	public void update(int delta) {
		if(this.active && !this.firing) {
			if(this.angle > maxangle) {
				this.angleDirection = false;
			} else if(this.angle < this.minangle) {
				this.angleDirection = true;
			}
			
			if(this.angleDirection) {
				switch(this.type) {
				case RAILGUN_0: this.angle += .01*delta;
				case LASER_0: this.angle += .01*delta;
				}
			} else {
				switch(this.type) {
				case RAILGUN_0: this.angle -= .01*delta;
				case LASER_0: this.angle -= .01*delta;
				}
			}
			
		}
		
		if(this.firing) {
			if(this.fireFrame == -1) {
				this.fireFrame = 2;
			}
			
			if(this.fireFrameIter > this.fireFrameIterMax) {
				this.fireFrameIter = 0; 
				this.fireFrame++;
				if(this.fireFrame > 7) {
					this.fireFrame = 2;
				}
			} else {
				this.fireFrameIter += 1*delta;
			}
		}
	}
	
	public void initRotation() {
		switch(this.type) {
		case RAILGUN_0:	switch(this.orientation) {
						case 0: this.angle = 0; this.maxangle = 45; this.minangle = -45; break;
						case 1: this.angle = 90; this.maxangle = 135; this.minangle = 45; break;
						case 2: this.angle = 180; this.maxangle = 225; this.minangle = 135; break;
						case 3: this.angle = 270; this.maxangle = 315; this.minangle = 225; break;
						} break;
		case LASER_0:	switch(this.orientation) {
						case 0: this.angle = 45; this.maxangle = 90; this.minangle = 0; break;
						case 1: this.angle = 135; this.maxangle = 180; this.minangle = 90; break;
						case 2: this.angle = 225; this.maxangle = 270; this.minangle = 180; break;
						case 3: this.angle = 315; this.maxangle = 360; this.minangle = 270; break;
						} break;			
		}
	}
	
	public void spritify() {
		switch(this.type) {
		case RAILGUN_0: this.spriteCoords = new Point[8];
						for(int i = 0; i != this.spriteCoords.length; i++) {
							this.spriteCoords[i] = new Point(i, 0);
						} this.fireFrameIterMax = 70; break;
		case LASER_0: 	this.spriteCoords = new Point[8];
						for(int i = 0; i != this.spriteCoords.length; i++) {
							this.spriteCoords[i] = new Point(i, 1);
						} this.fireFrameIterMax = 120; break;
		}
	}

	public boolean inRange(double targAngle) {
		if(this.type == RAILGUN_0 && this.orientation == 0) {
			if((targAngle > 0 && targAngle < 45) || (targAngle > 315 && targAngle < 360)) {
				return true; 
			}
		} else {
			if(targAngle > this.minangle && targAngle < this.maxangle) {
				return true;
			}
		}
		
		return false;
	}
		
	
	
}
