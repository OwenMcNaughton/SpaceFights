package Mains;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


public class Buffon {

	public int x, y, width, height;
	public Image image;
	
	public String action;
	
	public boolean hovering;
	
	public Buffon(int argx, int argy, int w, int h, Image img, String a) {
		this.x = argx; this.y = argy;
		this.width = w; this.height = h;
		this.image = img;
		
		this.action = a;
		
		this.hovering = false;
		
	}
	
	public String hit(int mousex, int mousey) {
		
		if(mousex > this.x && mousex < this.x + this.width) {
			if(mousey > this.y && mousey < this.y + this.height) {
				return this.action;
			}
		}
		
		return null;
	}
	
	public void hover(int mousex, int mousey) {
		if(mousex > this.x && mousex < this.x + this.width) {
			if(mousey > this.y && mousey < this.y + this.height) {
				this.hovering = true;
				return;
			}
		}
		
		this.hovering = false;
	}
	
	public Graphics draw(Graphics g) {
		
		g.drawImage(this.image, this.x, this.y);
		if(this.hovering) {
			g.setLineWidth(2);
			g.setColor(Color.red);
			g.drawRect(this.x, this.y, this.width, this.height);
		}
		
		
		return g;
	}
	
}
