package celestialbodies;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


public class Asteroid {

	private double xpos, ypos;
	
	public Rectangle bounds;
	public Image img;
	
	public Asteroid(double x, double y) {
		this.xpos = x;
		this.ypos = y;
		
		try {
			this.img = new org.newdawn.slick.Image("res//asteroid.png");
		} catch (SlickException e) {}
		
		this.bounds = new Rectangle((int)this.xpos, (int)this.ypos, img.getWidth(), img.getHeight());
	}
	
	public void move(double velx, double vely, int delta) {
		this.xpos -= velx*delta; this.ypos -= vely*delta;
		this.bounds = new Rectangle((int)this.xpos, (int)this.ypos, img.getWidth(), img.getHeight());
	}
	
}
