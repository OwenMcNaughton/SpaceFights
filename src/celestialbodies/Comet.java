package celestialbodies;

import java.awt.Point;
import java.util.Random;

public class Comet {

	public double locx, locy, velx, vely;
	
	public Point ballCoords;
	
	public Comet(double x, double y) {
		Random gen = new Random();
		
		this.locx = x + gen.nextInt(2000)-1000;
		this.locy = y + gen.nextInt(2000)-1000;
		
		this.velx = (gen.nextInt(400)-200)/1000d;
		this.vely = (gen.nextInt(400)-200)/1000d;
		
		this.ballCoords = new Point(0, 5);
	}
	
	
	
}
