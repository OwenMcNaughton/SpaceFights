package Mains;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import editmode.Ship;

public class SpaceFights extends StateBasedGame {

	final private int editState = 0;
	final private int playState = 1;
	Ship ship;
	
	public static void main(String[] args) {
		AppGameContainer container;
		try {
			container = new AppGameContainer(new SpaceFights("SpaceFights!"));
			//container.setTargetFrameRate(60);
			container.setDisplayMode(1200, 600, false);
			//container.setVSync(true);
			container.start();
		} catch(Exception x) {}
    }
	
	public SpaceFights(String name) {
		super(name);
		
		this.addState(new EditMode(editState, ship));
		this.addState(new PlayMode(playState));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(playState).init(gc, this);
		this.getState(editState).init(gc, this);
		
		this.enterState(editState);
		
	}
}
