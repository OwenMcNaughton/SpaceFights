package Mains;
import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import editmode.Crew;
import editmode.Grid;
import editmode.Ship;
import editmode.Tile;

public class EditMode extends BasicGameState{

	private GameContainer gcBig;
	private StateBasedGame sbgBig;
	
	Buffon[] buffons;
	Intpo a1, a2, a3, a4, a5, a6, a7;
	
	
	public static Ship s;
	public static int zoom;
	
	int width, height;

	public static int paint;
	
	public static int style;
	private final int PENCIL = 0;
	private final int FILL = 1;
	
	public static TrueTypeFont calibri10;
	public static TrueTypeFont calibri12;
	
	public boolean firstRun;
	
	public static Rectangle buttonsClip;
	public static Rectangle windowClip;
	
	public static Image mainButtonBackground, a1bg, a2bg, a3bg, a4bg;
	
	public static int titCost, uraCost, fueCost, golCost;
	
	public EditMode(int editState, Ship ship) {
		EditMode.s = ship;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		firstRun = true;
		zoom = 1;
		
		width = gc.getWidth(); height = gc.getHeight();
		
		buffons = new Buffon[100];
		a1 = new Intpo(10, 50);
		a1bg = new org.newdawn.slick.Image("res//a1bg.png");
		a2 = new Intpo(10, a1.y+100);
		a2bg = new org.newdawn.slick.Image("res//a2bg.png");
		a3 = new Intpo(10, a2.y+150);
		a3bg = new org.newdawn.slick.Image("res//a3bg.png");
		a4 = new Intpo(10, a3.y+50);
		a4bg = new org.newdawn.slick.Image("res//a4bg.png");
		
		//ESSENTIALS
		Image m = new org.newdawn.slick.Image("res//wall.png");
		buffons[0] = new Buffon(a1.x, a1.y, m.getWidth(), m.getHeight(), m, "WALL");
		m = new org.newdawn.slick.Image("res//path.png");
		buffons[1] = new Buffon(a1.x, a1.y+25, m.getWidth(), m.getHeight(), m, "PATH");
		m = new org.newdawn.slick.Image("res//space.png");
		buffons[2] = new Buffon(a1.x, a1.y+50, m.getWidth(), m.getHeight(), m, "EMPTY");
		m = new org.newdawn.slick.Image("res//eraser.png");
		buffons[22] = new Buffon(a1.x+110, a1.y, m.getWidth(), m.getHeight(), m, "ERASER");
		m = new org.newdawn.slick.Image("res//pencil.png");
		buffons[5] = new Buffon(a1.x+60, a1.y, m.getWidth(), m.getHeight(), m, "PENCIL");
		m = new org.newdawn.slick.Image("res//bucket.png");
		buffons[13] = new Buffon(a1.x+85, a1.y, m.getWidth(), m.getHeight(), m, "FILL");
		m = new org.newdawn.slick.Image("res//door.png");
		buffons[12] = new Buffon(a1.x+55, a1.y+25, m.getWidth(), m.getHeight(), m, "DOOR");
		m = new org.newdawn.slick.Image("res//girder.png");
		buffons[25] = new Buffon(a1.x+110, a1.y+25, m.getWidth(), m.getHeight(), m, "GIRDER");
		
		//ESSENTIAL NODES
		m = new org.newdawn.slick.Image("res//core.png");
		buffons[3] = new Buffon(a2.x, a2.y, m.getWidth(), m.getHeight(), m, "CORE");
		m = new org.newdawn.slick.Image("res//stasis.png");
		buffons[4] = new Buffon(a2.x, a2.y+25, m.getWidth(), m.getHeight(), m, "STASIS");
		m = new org.newdawn.slick.Image("res//terminal.png");
		buffons[7] = new Buffon(a2.x, a2.y+50, m.getWidth(), m.getHeight(), m, "TERMINAL");
		m = new org.newdawn.slick.Image("res//engine.png");
		buffons[8] = new Buffon(a2.x, a2.y+75, m.getWidth(), m.getHeight(), m, "ENGINE");
		m = new org.newdawn.slick.Image("res//warp.png");
		buffons[9] = new Buffon(a2.x+55, a2.y, m.getWidth(), m.getHeight(), m, "WARP");
		m = new org.newdawn.slick.Image("res//charger.png");
		buffons[10] = new Buffon(a2.x+55, a2.y+25, m.getWidth(), m.getHeight(), m, "CHARGER");
		m = new org.newdawn.slick.Image("res//shields.png");
		buffons[15] = new Buffon(a2.x+55, a2.y+50, m.getWidth(), m.getHeight(), m, "SHIELDS");
		m = new org.newdawn.slick.Image("res//titanium.png");
		buffons[18] = new Buffon(a2.x+110, a2.y, m.getWidth(), m.getHeight(), m, "TITANIUM");
		m = new org.newdawn.slick.Image("res//uranium.png");
		buffons[19] = new Buffon(a2.x+110, a2.y+25, m.getWidth(), m.getHeight(), m, "URANIUM");
		m = new org.newdawn.slick.Image("res//fuel.png");
		buffons[20] = new Buffon(a2.x+110, a2.y+50, m.getWidth(), m.getHeight(), m, "FUEL");
		m = new org.newdawn.slick.Image("res//gold.png");
		buffons[21] = new Buffon(a2.x+110, a2.y+75, m.getWidth(), m.getHeight(), m, "GOLD");
		m = new org.newdawn.slick.Image("res//solar.png");
		buffons[26] = new Buffon(a2.x+55, a2.y+75, m.getWidth(), m.getHeight(), m, "SOLAR");
		m = new org.newdawn.slick.Image("res//tractor.png");
		buffons[27] = new Buffon(a2.x, a2.y+100, m.getWidth(), m.getHeight(), m, "TRACTOR");
		m = new org.newdawn.slick.Image("res//life.png");
		buffons[28] = new Buffon(a2.x+55, a2.y+100, m.getWidth(), m.getHeight(), m, "LIFE");
		
		//CREW
		m = new org.newdawn.slick.Image("res//human.png");
		buffons[11] = new Buffon(a3.x, a3.y, m.getWidth(), m.getHeight(), m, "HUMAN");
		m = new org.newdawn.slick.Image("res//robot.png");
		buffons[14] = new Buffon(a3.x+55, a3.y, m.getWidth(), m.getHeight(), m, "ROBOT");
		
		//WEAPONS
		m = new org.newdawn.slick.Image("res//railgun.png");
		buffons[16] = new Buffon(a4.x, a4.y, m.getWidth(), m.getHeight(), m, "RAILGUN");
		m = new org.newdawn.slick.Image("res//laser.png");
		buffons[17] = new Buffon(a4.x+55, a4.y, m.getWidth(), m.getHeight(), m, "LASER");


		m = new org.newdawn.slick.Image("res//fire.png");
		buffons[6] = new Buffon(10, 450, m.getWidth(), m.getHeight(), m, "FIRE");
		
		
		mainButtonBackground = new org.newdawn.slick.Image("res//mainButtonBackground.png");
		a7 = new Intpo(0, gc.getHeight()-mainButtonBackground.getHeight());
		
		
		//OTHER
		m = new org.newdawn.slick.Image("res//launch.png");
		buffons[23] = new Buffon(a7.x+132, a7.y+31, m.getWidth(), m.getHeight(), m, "LAUNCH");
		m = new org.newdawn.slick.Image("res//erase.png");
		buffons[24] = new Buffon(a7.x+136, a7.y+52, m.getWidth(), m.getHeight(), m, "ERASE");
		
		
		Font awtFont = new Font("Calibri", Font.PLAIN, 12);
		calibri12 = new TrueTypeFont(awtFont, false);
		awtFont = new Font("Calibri", Font.PLAIN, 10);
		calibri10 = new TrueTypeFont(awtFont, false);
		
		buttonsClip = new Rectangle(0, 0, 180, gc.getHeight());
		windowClip = new Rectangle(0 + buttonsClip.getWidth(), 0, 
					gc.getWidth() - buttonsClip.getWidth(), gc.getHeight());
		
		s = new Ship(new Point(PlayMode.worldClip.getCenterX(), PlayMode.worldClip.getCenterY()), 25, 20);
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_SPACE)) {
			sbg.enterState(1);
			Grid.highFocus = new Point(-1, -1);
		}
		
		Grid.editUpdate(delta, input);
		
		if(firstRun) {
			for(int i = 0; i != Grid.columns; i++) {
				for(int j = 0; j != Grid.rows; j++) {
					Grid.tiles[i][j].uplink = null;
				}
			}
			
			for(int i = 0; i != Grid.columns; i++) {
				for(int j = 0; j != Grid.rows; j++) {
					
				}
			}
			
			
		}
		
		titCost = 0; uraCost = 0;
		for(int i = 0; i != Grid.columns; i++) {
			for(int j = 0; j != Grid.rows; j++) {
				int[] a = Tile.paintPrice(Grid.tiles[i][j]);
				titCost += a[0]; uraCost += a[1];

				Grid.tiles[i][j].uplinkStatus = Tile.UNLINKED;
				Grid.tiles[i][j].nodeStatus = Tile.OFFLINE;
			}
		}
		
		golCost = 0;
		for(Crew c : Grid.crew) {
			if(c != null) {
				golCost += Crew.crewPrice(c);
			}
		}
		
		
		gcBig = gc;
		sbgBig = sbg;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {		
		Input input = gc.getInput();
		g.setFont(calibri12);
		
		//g.draw(buttonsClip);
		//g.draw(windowClip);
		
		g.setWorldClip(windowClip);
		g = s.editDraw(g, true, input);
		
		g.clearWorldClip();
		
		g.setWorldClip(buttonsClip);
		g.drawImage(a1bg, a1.x-5, a1.y-20);
		g.drawImage(a2bg, a2.x-5, a2.y-20);
		g.drawImage(a3bg, a3.x-5, a3.y-20);
		g.drawImage(a4bg, a4.x-5, a4.y-20);
		
		g.drawImage(mainButtonBackground, a7.x, a7.y);
		
		g.setColor(Color.white);
		g.drawImage(PlayMode.resourceBoxSmall, a7.x+5, a7.y+25);
		if(titCost > PlayMode.titanium) {
			g.setColor(new Color(255, 50, 50));
		} else {
			g.setColor(Color.white);
		}
		g.drawString("" + titCost, a7.x+25, a7.y+26);
		if(uraCost > PlayMode.uranium) {
			g.setColor(new Color(255, 50, 50));
		} else {
			g.setColor(Color.white);
		}
		g.drawString("" + uraCost, a7.x+25, a7.y+37);
		if(fueCost > PlayMode.fuel) {
			g.setColor(new Color(255, 50, 50));
		} else {
			g.setColor(Color.white);
		}
		g.drawString("" + fueCost, a7.x+25, a7.y+48);
		if(golCost > PlayMode.gold) {
			g.setColor(new Color(255, 50, 50));
		} else {
			g.setColor(Color.white);
		}
		g.drawString("" + golCost, a7.x+25, a7.y+59);
		
		g.setColor(Color.white);
		g.drawImage(PlayMode.resourceBoxSmall, a7.x+70, a7.y+25);
		g.drawString("" + (int)PlayMode.titanium, a7.x+90, a7.y+26);
		g.drawString("" + (int)PlayMode.uranium, a7.x+90, a7.y+37);
		g.drawString("" + (int)PlayMode.fuel, a7.x+90, a7.y+48);
		g.drawString("" + (int)PlayMode.gold, a7.x+90, a7.y+59);
		
		for(int i = 0; i != buffons.length; i++) {
			if(buffons[i] != null) {
				g = buffons[i].draw(g);
			}
		}
		
		g.clearWorldClip();
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(windowClip.contains((float)newx, (float)newy)) {
			if(Mouse.isButtonDown(2)) {
				Point offset = new Point(Grid.x + (newx-oldx), Grid.y + (newy-oldy));
				Grid.x = offset.getX(); Grid.y = offset.getY();
				for(Crew c : Grid.crew) {
					if(c != null) {
						c.locx = offset.getX(); c.locy = offset.getY(); 
					}
				}
			}
			
			if(Mouse.isButtonDown(0)) {
				if(newx > Grid.x && newx < Grid.x + Grid.width) {
					if(newy > Grid.y && newy < Grid.y + Grid.height) {
						Ship.grid.paint(newx, newy, paint, style);
						Ship.grid.hoverTile(newx, newy, false);
					} else {
						Grid.highFocus = new Point(-1, -1);
					}
				} else {
					Grid.highFocus = new Point(-1, -1);
				}
			}
		}
	}
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if(windowClip.contains((float)newx, (float)newy)) {
			boolean actionTaken = false;
			if(newx > Grid.x && newx < Grid.x + Grid.width) {
				if(newy > Grid.y && newy < Grid.y + Grid.height) {
					Ship.grid.hoverTile(newx, newy, false);
					actionTaken = true;
				}
			}
			
			if(!actionTaken) {
				Grid.highFocus = new Point(-1, -1);
			}
		
		}
		
		if(buttonsClip.contains((float)newx, (float)newy)) {
			for(int i = 0; i != buffons.length; i++) {
				if(buffons[i] != null) {
					buffons[i].hover(newx, newy);
				}
			}
		}
		
		
	}
	
	public void mousePressed(int button, int x, int y) {
		
		if(windowClip.contains((float)x, (float)y)) {
			if(button == 0) {
				if(x > Grid.x && x < Grid.x + Grid.width) {
					if(y > Grid.y && y < Grid.y + Grid.height) {
						Ship.grid.paint(x, y, paint, style);
						Ship.grid.hoverTile(x, y, false);
					}
				}
			}
			
			if(button == 0) {
				if(new Rectangle(Grid.x + Grid.width/2 - Grid.tileSize/2, 
						(int)(Grid.y - Grid.tileSize), Grid.tileSize, Grid.tileSize).contains(x, y)) {
					Ship.resizeGrid(0);
				} else if(new Rectangle(Grid.x + Grid.width, Grid.y + Grid.height/2 - Grid.tileSize/2, 
						Grid.tileSize, Grid.tileSize).contains(x, y)) {
					Ship.resizeGrid(1);
				} else if(new Rectangle(Grid.x + Grid.width/2 - Grid.tileSize/2, 
						Grid.y + Grid.height, Grid.tileSize, Grid.tileSize).contains(x, y)) {
					Ship.resizeGrid(2);
				} else if(new Rectangle(Grid.x - Grid.tileSize, Grid.y + Grid.height/2 - Grid.tileSize/2, 
						Grid.tileSize, Grid.tileSize).contains(x, y)) {
					Ship.resizeGrid(3);
				}
			}
		}
		
		if(buttonsClip.contains(x, y)) {
			if(button == 0) {
				Grid.highFocus = new Point(-1, -1);
				for(int i = 0; i != buffons.length; i++) {
					if(buffons[i] != null) {
						buffonHit(buffons[i].hit(x, y));
					}
				}
			}
		}
		
		if(button == 1) {
			if(paint >= Tile.N_TERMINAL && paint <= Tile.E_TERMINAL) {
				if(paint >= Tile.E_TERMINAL) {
					paint = Tile.N_TERMINAL;
					Grid.bigPaint = Tile.N_TERMINAL;
				} else {
					paint++;
					Grid.bigPaint++;
				}
			} else if(paint == Tile.HC_DOOR) {
				paint = Tile.VC_DOOR;
				Grid.bigPaint = Tile.VC_DOOR;
			} else if(paint == Tile.VC_DOOR) {
				paint = Tile.HC_DOOR;
				Grid.bigPaint = Tile.HC_DOOR;
			} else if(paint >= Tile.RAILGUN_0_N && paint <= Tile.RAILGUN_0_W) {
				if(paint >= Tile.RAILGUN_0_W) {
					paint = Tile.RAILGUN_0_N;
					Grid.bigPaint = Tile.RAILGUN_0_N;
				} else {
					paint += 2;
					Grid.bigPaint += 2;
				}
			} else if(paint >= Tile.LASER_0_N && paint <= Tile.LASER_0_W) {
				if(paint >= Tile.LASER_0_W) {
					paint = Tile.LASER_0_N;
					Grid.bigPaint = Tile.LASER_0_N;
				} else {
					paint += 2;
					Grid.bigPaint += 2;
				}
			}
		}
	}
	
	public void mouseReleased(int button, int x, int y) {
		
	}
	
	public void mouseWheelMoved(int change) {
		Input input = gcBig.getInput();
		
		if(windowClip.contains(input.getMouseX(), input.getMouseY())) {
			int x = input.getMouseX();
			int y = input.getMouseY();
			
			change /= 120;
			if(zoom + change >= 1 && zoom + change < 4) {
				zoom += change;
				Ship.grid.zoomChange(zoom, x , y);
			}
		}
	}
	
	public boolean buffonHit(String a) {
		if(a != null) {
			if(a.equals("WALL")) {
				paint = Tile.WALL_0;
				Grid.bigPaint = Tile.WALL_0;
			} else if(a.equals("PATH")) {
				paint = Tile.PATH_N;
				Grid.bigPaint = Tile.PATH_N;
			} else if(a.equals("EMPTY")) {
				paint = Tile.EMPTY;
				Grid.bigPaint = Tile.WALL_0;
			} else if(a.equals("STASIS")) {
				paint = Tile.STASIS_VACANT;
				Grid.bigPaint = Tile.STASIS_VACANT;
			} else if(a.equals("PENCIL")) {
				style = PENCIL;
			} else if(a.equals("FILL")) {
				if(paint <= 10) {
					style = FILL;
				}
			} else if(a.equals("CORE")) {
				paint = Tile.CORE;
				Grid.bigPaint = Tile.CORE;
				style = PENCIL;
			} else if(a.equals("FIRE")) {
				paint = Tile.FIRE;
				Grid.bigPaint = Tile.FIRE;
				style = PENCIL;
			} else if(a.equals("TERMINAL")) {
				paint = Tile.N_TERMINAL;
				Grid.bigPaint = Tile.N_TERMINAL;
			} else if(a.equals("ENGINE")) {
				paint = Tile.ENGINE;
				Grid.bigPaint = Tile.ENGINE;
				style = PENCIL;
			} else if(a.equals("WARP")) {
				paint = Tile.WARP;
				Grid.bigPaint = Tile.WARP;
				style = PENCIL;
			} else if(a.equals("CHARGER")) {
				paint = Tile.CHARGER;
				Grid.bigPaint = Tile.CHARGER;
				style = PENCIL;
			} else if(a.equals("HUMAN")) {
				paint = Crew.HUMAN;
				Grid.bigPaint = Crew.HUMAN;
				style = PENCIL;
			} else if(a.equals("ROBOT")) {
				paint = Crew.ROBOT;
				Grid.bigPaint = Crew.ROBOT;
				style = PENCIL;
			} else if(a.equals("DOOR")) {
				paint = Tile.HC_DOOR;
				style = PENCIL;
				Grid.bigPaint = Tile.HC_DOOR;
			} else if(a.equals("SHIELDS")) {
				paint = Tile.SHIELD_GEN;
				style = PENCIL;
				Grid.bigPaint = Tile.SHIELD_GEN;
			} else if(a.equals("RAILGUN")) {
				paint = Tile.RAILGUN_0_N;
				style = PENCIL;
				Grid.bigPaint = Tile.RAILGUN_0_N;
			} else if(a.equals("LASER")) {
				paint = Tile.LASER_0_N;
				Grid.bigPaint = Tile.LASER_0_N;
				style = PENCIL;
			} else if(a.equals("TITANIUM")) {
				paint = Tile.TITANIUM_DUMP_0;
				Grid.bigPaint = Tile.TITANIUM_DUMP_0;
				style = PENCIL;
			} else if(a.equals("URANIUM")) {
				Grid.bigPaint = Tile.URANIUM_DUMP_0;
				style = PENCIL;
				paint = Tile.URANIUM_DUMP_0;
			} else if(a.equals("FUEL")) {
				Grid.bigPaint = Tile.FUEL_DUMP_0;
				style = PENCIL;
				paint = Tile.FUEL_DUMP_0;
			} else if(a.equals("GOLD")) {
				paint = Tile.GOLD_DUMP_0;
				Grid.bigPaint = Tile.GOLD_DUMP_0;
				style = PENCIL;
			} else if(a.equals("ERASER")) {
				paint = Tile.ERASER;
			} else if(a.equals("GIRDER")) {
				paint = Tile.GIRDER_0;
				style = PENCIL;
				Grid.bigPaint = Tile.GIRDER_0;
			} else if(a.equals("SOLAR")) {
				paint = Tile.SOLAR;
				Grid.bigPaint = Tile.SOLAR;
				style = PENCIL;
			} else if(a.equals("TRACTOR")) {
				paint = Tile.TRACTOR;
				Grid.bigPaint = Tile.TRACTOR;
				style = PENCIL;
			} else if(a.equals("LIFE")) {
				paint = Tile.LIFE_SUPPORT;
				Grid.bigPaint = Tile.LIFE_SUPPORT;
				style = PENCIL;
			} else if(a.equals("LAUNCH")) {
				if(titCost <= PlayMode.titanium && uraCost <= PlayMode.uranium &&
					fueCost <= PlayMode.fuel && golCost <= PlayMode.gold) {
					sbgBig.enterState(1);
				}
			} else if(a.equals("ERASE")) {
				for(int i = 0; i != Grid.columns; i++) {
					for(int j = 0; j != Grid.rows; j++) {
						Grid.tiles[i][j].floor = Tile.EMPTY;
						Grid.tiles[i][j].node = -1;
						Grid.tiles[i][j].weapon = null;
					}
				}
			}
			
			return true;
		} else return false;
	}
 	
	public void keyPressed(int key, char c) {
		
	}

	public int getID() {
		return 0;
	}


}
