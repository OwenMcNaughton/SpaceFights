package Mains;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

public class PlayMode extends BasicGameState{ 
	
	public static boolean firstRun;
	static Ship s;
	public static Rectangle worldClip;
	Point worldCenter;
	Point deltaPos;
	Rectangle editClip;
	
	String deltaStr;
	public static Image resourceBox;
	public PlayMode(int playState) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		firstRun = true;
		worldClip = new Rectangle(0, 0, menuClip.getX(), gc.getHeight());
		
		worldCenter = new Point(100, 100);
		
		deltaStr = "0";
	
		Random gen = new Random();
		systems = new SystemHolder[10000];
		for(int i = 0; i != systems.length; i++) {
			systems[i] = new SystemHolder(gen.nextInt(5000), gen.nextInt(5000), PlayMode.worldClip);
		}
		systemSheet = new SpriteSheet(new org.newdawn.slick.Image("res//SystemSheet.png"), 16, 16);
		systemFocus = -1;
			
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setWorldClip(editClip);
		s.editDraw(g, false, input);
		g.clearWorldClip();
		
		g.setWorldClip(worldClip);
		if(systemFocus == -1) {
			systemSheet.startUse();
		g.clearWorldClip();
		
		g.drawString(deltaStr, 100, 10);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(input.isKeyDown(Input.KEY_RALT)) {
			firstRun = true;
			sbg.enterState(0);
		} else if(firstRun) {
			s = EditMode.s;
				firstRun = false;
		} 
		
		deltaStr = "" + delta;
		
		if(input.isMouseButtonDown(1)) {
			int newx = input.getMouseX(); 
			int newy = input.getMouseY();
			if(newx > worldClip.getX() && newx < worldClip.getY() + worldClip.getWidth()) {
				if(newy > worldClip.getY() && newy < worldClip.getY() + worldClip.getHeight()) {
					s.rotate(newx, newy);
				}
			}
		}
			if(input.isKeyDown(Input.KEY_W)) {
				s.accelerate('w', delta);
			} else {
				//s.accelerate('t', delta);
			}
			if(input.isKeyDown(Input.KEY_D)) {
				if(Ship.img.getRotation() +.3f > 360) {
					Ship.img.setRotation(.2f);
				} else {
					Ship.img.rotate(+.3f*delta);
				}
				Ship.angle = Ship.img.getRotation();
			}
			if(input.isKeyDown(Input.KEY_S)) {
				s.accelerate('s', delta);
			}
			if(input.isKeyDown(Input.KEY_A)) {
				if(Ship.img.getRotation() -.3f < 0) {
					Ship.img.setRotation(359.8f);
				} else {
					Ship.img.rotate(-.3f*delta);
				}
				Ship.angle = Ship.img.getRotation();
			}
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
	public int getID() {
		return 1;
	}
}

