package editmode;

public class DataPacket {

	double locx, locy, goalx, goaly, distance, angle, velx, vely;
	
	boolean right, down;
	
	public DataPacket(double lx, double ly, double gx, double gy) {
		this.locx = lx;
		this.locy = ly;
		this.goalx = gx;
		this.goaly = gy;
		this.velx = 0;
		this.vely = 0;
		
		if(gx > lx) {
			this.right = true;
		} else {
			this.right = false;
		}
		if(gy > ly) {
			this.down = true;
		} else {
			this.down = false;
		}
		
		double tempa = this.angle;
		
		this.angle = (float)(180/Math.PI) * (float)Math.asin((Math.abs(lx-gx))/
					 (Math.sqrt((gx-lx)*(gx-lx) + (gy-ly)*(gy-ly))));
		
		if(Double.isNaN(this.angle)) {
			this.angle = tempa;
		}
	
		if(gx > lx) {
			if(gy > ly) {
				this.angle = 90 + (90 - this.angle);
			} else if(gy > ly) {
				//
			} 
		} else if(gy > ly) {
			this.angle += 180;
		} else {
			this.angle = 270 + (90 - this.angle);
		}
	}
	
	public boolean update(int delta) {
		double tangle = this.angle;
		while(tangle > 90) {
			tangle = tangle - 90;
		}
		
		if(this.angle > 0 && this.angle < 90) {
			this.vely -= (Math.sin((90-tangle)*(Math.PI/180))*delta)/20;
			this.velx += (Math.sin((tangle)*(Math.PI/180))*delta)/20;
		} else if(angle > 90 && this.angle < 180) {
			this.vely += (Math.sin(tangle*(Math.PI/180))*delta)/20;
			this.velx += (Math.sin((90-tangle)*(Math.PI/180))*delta)/20;
		} else if(angle > 180 && this.angle < 270) {
			this.vely += (Math.sin((90-tangle)*(Math.PI/180))*delta)/20;
			this.velx -= (Math.sin(tangle*(Math.PI/180))*delta)/20;
		} else {
			this.vely -= (Math.sin(tangle*(Math.PI/180))*delta)/10;
			this.velx -= (Math.sin((90-tangle)*(Math.PI/180))*delta)/20;
		}
		
		this.locx += velx; this.locy += vely;
		
		return false;
	}
	
}
