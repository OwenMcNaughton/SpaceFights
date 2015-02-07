package celestialbodies;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class SystemHolder {

	public double locx;
	public double locy;
	
	public Rectangle bounds;
	
	public StarSystem system;
	
	public Rectangle worldClip;
	
	public SystemHolder(double x, double y, Rectangle w) {
		this.locx = x;
		this.locy = y;
		
		this.bounds = new Rectangle((int)this.locx, (int)this.locy, 16, 16);
		
		this.worldClip = w;
	}
	
	public void hit() {
		if(this.system == null) {
			this.system = new StarSystem(this.locx, this.locy);
		}
		
		this.system.focus = true;
	}
	
	public void move(double velx, double vely, int delta) {
		this.locx -= velx*delta; this.locy -= vely*delta;
		this.bounds = new Rectangle((int)this.locx, (int)this.locy, 16, 16);
	}
	
	public void menuMove(double x, double y) {
		this.locx -= x; this.locy -= y;
		this.bounds = new Rectangle((int)this.locx, (int)this.locy, 16, 16);
	}

	public void draw(Graphics g) {
		system.draw(g);
		
	}

	public void moveSystem(double velx, double vely, int delta) {
		this.system.move(velx, vely, delta);
		
	}
	
}
