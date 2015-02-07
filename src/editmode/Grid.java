package editmode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import Mains.Intpo;
import Mains.PlayMode;
import celestialbodies.Bot;
import celestialbodies.Projectile;

public class Grid {
	
	public static Input bigInput;
	public static int bigPaint;
	
	
	private SpriteSheet tileSheet;
	private SpriteSheet crewSheet;
	private static SpriteSheet effectSheet;
	public static SpriteSheet weaponSheet;
	
	private Animation walkUp;
	private Animation walkDown;
	private Animation walkRight;
	private Animation walkLeft;
	
	private Animation typeUp;
	private Animation typeDown;
	private Animation typeRight;
	private Animation typeLeft;
	
	private Animation extinguishUp;
	private Animation extinguishDown;
	private Animation extinguishRight;
	private Animation extinguishLeft;
	
	private Animation repairUp;
	private Animation repairDown;
	private Animation repairRight;
	private Animation repairLeft;
	
	private Animation roboWalkUp;
	private Animation roboWalkDown;
	private Animation roboWalkRight;
	private Animation roboWalkLeft;
	
	private Animation roboExtinguishUp;
	private Animation roboExtinguishDown;
	private Animation roboExtinguishRight;
	private Animation roboExtinguishLeft;
	
	private Animation roboRepairUp;
	private Animation roboRepairDown;
	private Animation roboRepairRight;
	private Animation roboRepairLeft;
	
	private Animation fire0;
	private Animation fire1;
	private Animation fire2;
	private Animation fire3;
	private Animation fire4;
	
	private static Animation bigExplosion;
	private static boolean bigExploding;
	private static float bigExplosionx;
	private static float bigExplosiony;
	
	private static ArrayList<Projectile> projectiles;
	
	private Animation charging;
	
	public static float x;
	public static float y;
	
	public static Tile[][] tiles;
	public static int[][] tilesInt;
	public static int columns;
	public static int rows;
	public static float width;
	public static float height;
	
	public static int tileSize;
	
	public static Point highFocus;
	public Point playFocus;
	
	public static ArrayList<Crew> crew;
	public int[] crewSelect;
	
	public int radTick;
	public int oxyTick;
	public int fireTick;
	public int crewTick;
	
	public int hackTick;
	public int powerTick;
	
	public int chargeTick;
	
	boolean oxyTickOn;
	
	public Rectangle selectBox;
	
	public Audio[] selected;
	public Audio[] actioned;
	public Audio[] fireDeath;
	public Audio[] pressureDeath;
	public static Audio[] zap;
	public static Audio[] explosions;
	
	public Audio doorOpen;
	public Audio doorClose;
	
	public boolean pathView;
	public static boolean hackView;
	public double hackGlowIter;
	public int hackSelectX, hackSelectY, hackDestX, hackDestY, hackMouseX, hackMouseY;
	public static Intpo uplinkFocus;
	
	public int warningFlashIter;
	public boolean warningFlash;
	
	public static double power;
	
	public static boolean shaking;
	public static int shakeIter;
	public static double shakeSine;
	
	public static Random gen;
	
	public boolean fillEnd;
	public int fillIter;
	
	public int cursorPaint;
	
	public static double sineThrob;

	public Grid(int w, int h, int t, float argX, float argY) throws SlickException {
		gen = new Random();
		Grid.columns = w;
		Grid.rows = h;
		Grid.tiles = new Tile[w][h];
		Grid.tilesInt = new int[w][h];
		Grid.tileSize = t; 
		this.initialiseTiles();
		
		Grid.width = w*t;
		Grid.height = h*t;
		
		Grid.x = argX;
		Grid.y = argY;

		this.tileSheet = new SpriteSheet(new org.newdawn.slick.Image("res//tileSheet16.png"), t, t);
		this.crewSheet = new SpriteSheet(new org.newdawn.slick.Image("res//crewSheet16.png"), t, t);
		Grid.effectSheet = new SpriteSheet(new org.newdawn.slick.Image("res//effects32.png"), t*2, t*2);
		Grid.weaponSheet = new SpriteSheet(new org.newdawn.slick.Image("res//weapons16.png"), t*4, t*4);
		
		this.loadAnimations();
				
		Grid.projectiles = new ArrayList<Projectile>();
		
		Grid.highFocus = new Point(-1, -1);
		this.playFocus = new Point(-1, -1);
		
		Grid.crew = new ArrayList<Crew>();
		Grid.crew.add(null);
		
		this.crewSelect = new int[10];
		for(int i = 0; i != crewSelect.length; i++) {
			crewSelect[i] = -1;
		}
		
		this.radTick = 0;
		this.oxyTick = 0;
		this.fireTick = 0;
		this.crewTick = 0;
		this.hackTick = 0;
		this.warningFlashIter = 0;
		this.powerTick = 0;
		
		this.oxyTickOn = false;
		
		this.selectBox = null;
		
		this.selected = new Audio[7];
		try {
			selected[0] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//mandartem.wav"));
			selected[1] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//readyForOrders.wav"));
			selected[2] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//whatsUp.wav"));
			selected[3] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//yesCaptain.wav"));
			selected[4] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//yo.wav"));
			selected[5] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//yus.wav"));
			selected[6] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//imReady.wav"));
			
			doorOpen = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//doorOpen.wav"));
			doorClose = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//doorClose.wav"));
		} catch (IOException e) {}
		
		this.actioned = new Audio[7];
		try {
			actioned[0] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//sureThing.wav"));
			actioned[1] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//bennaAdonna.wav"));
			actioned[2] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//youGotIt.wav"));
			actioned[3] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//ahWhatTheHell.wav"));
			actioned[4] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//affirmative.wav"));
			actioned[5] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//letsDoThisThang.wav"));
			actioned[6] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//cios.wav"));
		} catch (IOException e) {}
		
		this.fireDeath = new Audio[3];
		try {
			fireDeath[0] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//fireDeath1.wav"));
			fireDeath[1] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//fireDeath2.wav"));
			fireDeath[2] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//fireDeath3.wav"));
		} catch (IOException e) {}
		
		this.pressureDeath = new Audio[3];
		try {
			pressureDeath[0] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//pressureDeath1.wav"));
			pressureDeath[1] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//pressureDeath2.wav"));
			pressureDeath[2] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//pressureDeath3.wav"));
		} catch (IOException e) {}
		
		Grid.zap = new Audio[3];
		try {
			zap[0] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//zap1.wav"));
			zap[1] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//zap2.wav"));
			zap[2] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//zap3.wav"));
		} catch (IOException e) {}
		
		Grid.explosions = new Audio[3];
		try {
			explosions[0] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//explosion1.wav"));
			explosions[1] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//explosion2.wav"));
			explosions[2] = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res//explosion3.wav"));
		} catch (IOException e) {}
		
		this.pathView = false;
		Grid.hackView = false;
		this.hackSelectX = -1; this.hackSelectY = -1;
		this.hackGlowIter = 0;		
		
		Grid.power = 0;
		
		shaking = false;
		shakeIter = 0;
		shakeSine = 0;
		
		fillEnd = false;
		fillIter = 0;
		
		cursorPaint = -1;
		
		sineThrob = 1;
	}
	
	public Graphics draw(Graphics g, boolean editMode, Input input) {
		bigInput = input;
		
		tileSheet.startUse();
		if(editMode) {
			tileSheet.renderInUse((int)(x + width/2 - tileSize/2), 
					(int)(y - tileSize), 10, 0);
			tileSheet.renderInUse((int)(x + width), 
					(int)(y + height/2 - tileSize/2), 11, 0);
			tileSheet.renderInUse((int)(x + width/2 - tileSize/2), 
					(int)(y + height), 12, 0);
			tileSheet.renderInUse((int)(x - tileSize), 
					(int)(y + height/2 - tileSize/2), 13, 0);
		}
		
		
		for(int i = 0; i != Grid.columns; i++) {
			for(int j = 0; j != Grid.rows; j++) {
				
				int tileX = -1;
				int tileY = -1;
				
				int[] r = Tile.spritifyFloor(tiles[i][j], editMode);
				tileX = r[0]; tileY = r[1];
				
				tileSheet.renderInUse((int)Grid.x + i * Grid.tileSize, 
									  (int)Grid.y + j * Grid.tileSize, tileX, tileY);
				
				r = Tile.spritifyNode(tiles[i][j], editMode);
				boolean isNode = false;
				if(r[2] == 1) {
					isNode = true;
				}
				tileX = r[0]; tileY = r[1];
				
				if(hackView) {
					if(Tile.isTileTerminal((tiles[i][j]))) {
						isNode = true;
						
						if(tiles[i][j].uplink != null) {
							switch(tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y].nodeStatus) {
							case Tile.WARNING: tiles[i][j].uplinkStatus = Tile.WARNING; break;
							}
						}
						
						if(tiles[i][j].uplinkStatus == Tile.NOT_IN_USE) {
							tileX = 8; tileY = 3;
						} else if(tiles[i][j].uplinkStatus == Tile.WARNING) {
							if(warningFlash) {
								tileX = 9; tileY = 3;
							} else {
								tileX = 10; tileY = 3;
							}
						} else if(tiles[i][j].uplinkStatus == Tile.UNLINKED) {
							tileX = 10; tileY = 3;
						} else if(tiles[i][j].uplinkStatus == Tile.IN_USE) {
							tileX = 11; tileY = 3;
						}
					}
					
					if(Tile.isTileRemoteNode((tiles[i][j]))) {
						isNode = true;
						if(tiles[i][j].nodeStatus == Tile.OFFLINE) {
							tileX = 14; tileY = 3;
						} else if(tiles[i][j].nodeStatus == Tile.NODE_WARNING) {
							if(warningFlash) {
								tileX = 13; tileY = 3;
							} else {
								tileX = 14; tileY = 3;
							}
						
						} else if(tiles[i][j].nodeStatus == Tile.ACTIVE || 
								tiles[i][j].nodeStatus == Tile.CONNECTED) {
							tileX = 15; tileY = 3;
						}
					}
				} else {
					if(Tile.isTileTerminal((tiles[i][j]))) {
						isNode = true;
						if(tiles[i][j].uplinkStatus == Tile.NOT_IN_USE) {
							switch(tiles[i][j].node) {
							case Tile.N_TERMINAL: tileX = 0; tileY = 1; break;
							case Tile.S_TERMINAL: tileX = 1; tileY = 1; break;
							case Tile.W_TERMINAL: tileX = 2; tileY = 1; break;
							case Tile.E_TERMINAL: tileX = 3; tileY = 1; break;
							}
						} else if(tiles[i][j].uplinkStatus == Tile.WARNING) {
							if(warningFlash) {
								switch(tiles[i][j].node) {
								case Tile.N_TERMINAL: tileX = 4; tileY = 1; break;
								case Tile.S_TERMINAL: tileX = 5; tileY = 1; break;
								case Tile.W_TERMINAL: tileX = 6; tileY = 1; break;
								case Tile.E_TERMINAL: tileX = 7; tileY = 1; break;
								}
							} else {
								switch(tiles[i][j].node) {
								case Tile.N_TERMINAL: tileX = 8; tileY = 1; break;
								case Tile.S_TERMINAL: tileX = 9; tileY = 1; break;
								case Tile.W_TERMINAL: tileX = 10; tileY = 1; break;
								case Tile.E_TERMINAL: tileX = 11; tileY = 1; break;
								}
							}
						} else if(tiles[i][j].uplinkStatus == Tile.UNLINKED) {
							switch(tiles[i][j].node) {
							case Tile.N_TERMINAL: tileX = 8; tileY = 1; break;
							case Tile.S_TERMINAL: tileX = 9; tileY = 1; break;
							case Tile.W_TERMINAL: tileX = 10; tileY = 1; break;
							case Tile.E_TERMINAL: tileX = 11; tileY = 1; break;
							}
						} else if(tiles[i][j].uplinkStatus == Tile.IN_USE) {
							switch(tiles[i][j].node) {
							case Tile.N_TERMINAL: tileX = 12; tileY = 1; break;
							case Tile.S_TERMINAL: tileX = 13; tileY = 1; break;
							case Tile.W_TERMINAL: tileX = 14; tileY = 1; break;
							case Tile.E_TERMINAL: tileX = 15; tileY = 1; break;
							}
						}
					}
				}
				
				if(isNode) {
					tileSheet.renderInUse((int)Grid.x + i * Grid.tileSize, 
							  (int)Grid.y + j * Grid.tileSize, tileX, tileY);
				}
				
				
				if((int)Grid.tiles[i][j].rads > 0) {
					int radTileX = (int)Grid.tiles[i][j].rads;
					int radTileY = 4;
					tileSheet.renderInUse((int)Grid.x + i * Grid.tileSize, 
							  (int)Grid.y + j * Grid.tileSize, radTileX, radTileY);
				}
				
				if(!oxyTickOn) {
					if((int)Grid.tiles[i][j].oxy < 8) {
						int oxyTileX = (int)Grid.tiles[i][j].oxy;
						int oxyTileY = 5;
						tileSheet.renderInUse((int)Grid.x + i * Grid.tileSize, 
								  (int)Grid.y + j * Grid.tileSize, oxyTileX, oxyTileY);
					}
				}
				
			}
		}
		tileSheet.endUse();
		
		//WeaponsRender
		g.setLineWidth(5);
		if(!hackView) {
			for(int i = 0; i != Grid.columns; i++) {
				for(int j = 0; j != Grid.rows; j++) {
					if(Grid.tiles[i][j].weapon != null) {
						Rectangle r = new Rectangle(Grid.x + i*tileSize, Grid.y + j*tileSize, tileSize, tileSize);
						Grid.tiles[i][j].weapon.drawNonSheet(g, r);
					}
				}
			}
			
			Grid.weaponSheet.startUse();
			for(int i = 0; i != Grid.columns; i++) {
				for(int j = 0; j != Grid.rows; j++) {
					if(Grid.tiles[i][j].weapon != null) {
						Rectangle r = new Rectangle(Grid.x + i*tileSize, Grid.y + j*tileSize, tileSize, tileSize);
						Grid.tiles[i][j].weapon.draw(g, r);
					}
				}
			}
			Grid.weaponSheet.endUse();			
		}
		
		//FireRender
		for(int i = 0; i != Grid.columns; i++) {
			for(int j = 0; j != Grid.rows; j++) {
				if(tiles[i][j].fire) {
					switch(tiles[i][j].fireType) {
					case 0: this.fire0.draw(Grid.x + i * Grid.tileSize, Grid.y + j * Grid.tileSize);
							break;
					case 1: this.fire1.draw(Grid.x + i * Grid.tileSize, Grid.y + j * Grid.tileSize);
							break;
					case 2: this.fire2.draw(Grid.x + i * Grid.tileSize, Grid.y + j * Grid.tileSize);
							break;
					case 3: this.fire3.draw(Grid.x + i * Grid.tileSize, Grid.y + j * Grid.tileSize);
							break;
					case 4: this.fire4.draw(Grid.x + i * Grid.tileSize, Grid.y + j * Grid.tileSize);
							break;
					}
					
				}
			}
		}
		
		//PathViewRender
		if(this.pathView) {
			for(int i = 0; i != columns; i++) {
				for(int j = 0; j != rows; j++) {
					if(tilesInt[i][j] == 0) {
						g.setColor(new Color(0, 200, 0));
						g.fillRect(Grid.x + i*tileSize + tileSize/4, Grid.y + j*tileSize + tileSize/4, tileSize*(1/2f), tileSize*(1/2f));
					} else {
						g.setColor(new Color(200, 0, 0));
						g.fillRect(Grid.x + i*tileSize + tileSize/4, Grid.y + j*tileSize + tileSize/4, tileSize*(1/2f), tileSize*(1/2f));
					}
				}
			}
		}
		
		//CrewStills
		crewSheet.startUse();
		for(Crew c : crew) {
			if(c != null && !c.inStasis) {
				if(c.type == Crew.HUMAN) {
		 			if(!c.pathing && !c.typing && c.pressureDead == -1 && !c.repairing && !c.extinguishing
		 					&& !c.fireDying && !c.radDying) {
						crewSheet.renderInUse((int)(Grid.x + c.tilex * Grid.tileSize), 
										      (int)(Grid.y + c.tiley * Grid.tileSize),
											  c.orientation, 0); 
					} else if(c.pressureDead > -1) {
						crewSheet.renderInUse((int)(Grid.x + c.tilex * Grid.tileSize), 
							      (int)(Grid.y + c.tiley * Grid.tileSize),
								  4, 0); 
					}
		 			
		 			if(c.extinguishing) {
		 				for(int i = 0; i != c.co2.length; i++) {
		 					if(c.co2[i] != null) {
		 						crewSheet.renderInUse((int)(c.co2[i].getX()), 
		 								(int)(c.co2[i].getY()), 0, 10); 
		 					}
		 				}
					}
				} else if(c.type == Crew.ROBOT) {
					if(!c.pathing && c.robotDead == -1 && !c.repairing && !c.extinguishing) {
						crewSheet.renderInUse((int)(Grid.x + c.tilex * Grid.tileSize), 
							      (int)(Grid.y + c.tiley * Grid.tileSize),
								  8 + c.orientation, 0); 
					} else if(c.pressureDead > -1) {
						crewSheet.renderInUse((int)(Grid.x + c.tilex * Grid.tileSize), 
							      (int)(Grid.y + c.tiley * Grid.tileSize),
								  12, 0); 
					}
					
					if(c.extinguishing) {
		 				for(int i = 0; i != c.co2.length; i++) {
		 					if(c.co2[i] != null) {
		 						crewSheet.renderInUse((int)(c.co2[i].getX()), 
		 								(int)(c.co2[i].getY()), 0, 10); 
		 					}
		 				}
					}
					
				}
			}
		}	
		crewSheet.endUse();
		
		//CrewAnimations
		for(Crew c : crew) {
			if(c != null) {
	 			if(c.pathing && !c.inStasis) {
	 				if(c.type == Crew.HUMAN) {
		 				switch(c.orientation) {
						case 0: this.walkUp.draw((float)c.locx, (float)c.locy); break;
						case 1: this.walkRight.draw((float)c.locx, (float)c.locy); break;
						case 2: this.walkDown.draw((float)c.locx, (float)c.locy); break;
						case 3: this.walkLeft.draw((float)c.locx, (float)c.locy); break;
						}
	 				} else if(c.type == Crew.ROBOT) {
	 					switch(c.orientation) {
						case 0: this.roboWalkUp.draw((float)c.locx, (float)c.locy); break;
						case 1: this.roboWalkRight.draw((float)c.locx, (float)c.locy); break;
						case 2: this.roboWalkDown.draw((float)c.locx, (float)c.locy); break;
						case 3: this.roboWalkLeft.draw((float)c.locx, (float)c.locy); break;
						}
	 				}
	 			} else if(c.typing) {
	 				switch(c.orientation) {
					case 0: this.typeUp.draw((float)c.locx, (float)c.locy); break;
					case 1: this.typeRight.draw((float)c.locx, (float)c.locy); break;
					case 2: this.typeDown.draw((float)c.locx, (float)c.locy); break;
					case 3: this.typeLeft.draw((float)c.locx, (float)c.locy); break;
	 				}
	 			} else if(c.extinguishing) {
	 				if(c.type == Crew.HUMAN) {
		 				switch(c.orientation) {
						case 0: this.extinguishUp.draw((float)c.locx, (float)c.locy); break;
						case 1: this.extinguishRight.draw((float)c.locx, (float)c.locy); break;
						case 2: this.extinguishDown.draw((float)c.locx, (float)c.locy); break;
						case 3: this.extinguishLeft.draw((float)c.locx, (float)c.locy); break;
		 				}
	 				} else if(c.type == Crew.ROBOT) {
	 					switch(c.orientation) {
		 				case 0: this.roboExtinguishUp.draw((float)c.locx, (float)c.locy); break;
						case 1: this.roboExtinguishRight.draw((float)c.locx, (float)c.locy); break;
						case 2: this.roboExtinguishDown.draw((float)c.locx, (float)c.locy); break;
						case 3: this.roboExtinguishLeft.draw((float)c.locx, (float)c.locy); break;
	 					}
	 				}
	 			}  else if(c.repairing) {
	 				if(c.type == Crew.HUMAN) {
		 				switch(c.orientation) {
						case 0: this.repairUp.draw((float)c.locx, (float)c.locy); break;
						case 1: this.repairRight.draw((float)c.locx, (float)c.locy); break;
						case 2: this.repairDown.draw((float)c.locx, (float)c.locy); break;
						case 3: this.repairLeft.draw((float)c.locx, (float)c.locy); break;
		 				}
	 				} else if(c.type == Crew.ROBOT) {
	 					switch(c.orientation) {
						case 0: this.roboRepairUp.draw((float)c.locx, (float)c.locy); break;
						case 1: this.roboRepairRight.draw((float)c.locx, (float)c.locy); break;
						case 2: this.roboRepairDown.draw((float)c.locx, (float)c.locy); break;
						case 3: this.roboRepairLeft.draw((float)c.locx, (float)c.locy); break;
		 				}
	 				}
	 			} 
	 			
	 			if(c.fireDying) {
	 				c.fireDeath.draw((float)c.locx, (float)c.locy);
	 			}
	 			
	 			if(c.radDying) {
	 				c.radDeath.draw((float)c.locx, (float)c.locy);
	 			}
	 			
	 			
	 		}
	 	}
		
		//CrewStatusBars
		for(int i = 0; i != crewSelect.length; i++) {
			if(crewSelect[i] != -1) {
				if(crew.get(crewSelect[i]).type == Crew.HUMAN) {
					if(!crew.get(crewSelect[i]).fireDying && crew.get(crewSelect[i]).pressureDead == -1) {
						g.setLineWidth(1);
						g.setColor(new Color(200, 0, 0));
						g.fillRect((float)crew.get(crewSelect[i]).locx, (float)crew.get(crewSelect[i]).locy-tileSize/6,
									tileSize, tileSize/4);
		
						g.setColor(new Color(0, 200, 0));
						g.fillRect((float)crew.get(crewSelect[i]).locx, (float)crew.get(crewSelect[i]).locy-tileSize/6, 
								(float)(Grid.tileSize*(crew.get(crewSelect[i]).health)/1000d), tileSize/4);
						
						g.setColor(new Color(250, 250, 250));
						g.drawRect((float)crew.get(crewSelect[i]).locx-1, ((float)crew.get(crewSelect[i]).locy-tileSize/6)-1, 
								(float)Grid.tileSize+1, (tileSize/4)+1);
					}
				} else if(crew.get(crewSelect[i]).type == Crew.ROBOT) {
					if(crew.get(crewSelect[i]).type == Crew.ROBOT) {
						g.setLineWidth(1);
						g.setColor(new Color(250, 0, 0));
						g.fillRect((float)crew.get(crewSelect[i]).locx, (float)crew.get(crewSelect[i]).locy-tileSize/6,
									tileSize, tileSize/4);
		
						g.setColor(new Color(100, 100, 255));
						g.fillRect((float)crew.get(crewSelect[i]).locx, (float)crew.get(crewSelect[i]).locy-tileSize/6, 
								(float)(Grid.tileSize*(crew.get(crewSelect[i]).stepsLeft)/(double)crew.get(crewSelect[i]).maxSteps),
								tileSize/4);
						
						g.setColor(new Color(250, 250, 250));
						g.drawRect((float)crew.get(crewSelect[i]).locx-1, ((float)crew.get(crewSelect[i]).locy-tileSize/6)-1, 
								((float)Grid.tileSize)+1, (tileSize/4)+1);
					}
				}
			}
		}
		
		//HackHaze
		if(Grid.hackView) {
			tileSheet.startUse();
			g.setColor(new Color(0, 200, 0, .7f + (float)(Math.sin(this.hackGlowIter))/5f));
			for(int i = 0; i != columns; i++) {
				for(int j = 0; j != rows; j++) {
					if(!Tile.isTileTerminal(tiles[i][j]) && !Tile.isTileRemoteNode(tiles[i][j]) &&
							tiles[i][j].floor != Tile.EMPTY) {
						tileSheet.renderInUse((int)Grid.x + i * Grid.tileSize, 
						  (int)Grid.y + j * Grid.tileSize, 4 + Grid.tiles[i][j].hackType, 3);
					}
				}
			}
			tileSheet.endUse();
		}
		
		//HackNodes
		float alpha = 1;
		if(Grid.hackView) {
			g.setAntiAlias(true);
			if(uplinkFocus != null) {
				alpha = .4f;
			} else {
				alpha = .8f;
			}
				
			for(int i = 0; i != columns; i++) {
				for(int j = 0; j != rows; j++) {
					if(tiles[i][j].uplink != null) {
						g.setLineWidth(3);
						g.setColor(new Color(.98f, .98f, .98f, alpha));
						g.drawLine(Grid.x + i*tileSize + tileSize/2, Grid.y + j*tileSize + tileSize/2,
									Grid.x + tiles[i][j].uplink.x*tileSize + tileSize/2,
									Grid.y + tiles[i][j].uplink.y*tileSize + tileSize/2);
						
						switch(tiles[i][j].uplinkStatus) {
						case Tile.IN_USE: g.setColor(new Color(.41f, .81f, .41f, alpha)); break;
						case Tile.NOT_IN_USE: g.setColor(new Color(.28f, .48f, .64f, alpha)); break;
						case Tile.WARNING:	if(warningFlash) {
												g.setColor(new Color(.99f, .37f, .37f, alpha)); break;
											} else {
												g.setColor(new Color(0, 0, 0, alpha)); break;
											}
						case Tile.UNLINKED: g.setColor(new Color(0, 0, 0, alpha)); break;
						}

						g.setLineWidth(1);
						g.drawLine(Grid.x + i*tileSize + tileSize/2, Grid.y + j*tileSize + tileSize/2,
									Grid.x + tiles[i][j].uplink.x*tileSize + tileSize/2,
									Grid.y + tiles[i][j].uplink.y*tileSize + tileSize/2);
						
					}
				}
				
			}
			g.setAntiAlias(false);
			if(hackSelectX != -1) {
				g.setAntiAlias(true);
				switch(tiles[hackSelectX][hackSelectY].uplinkStatus) {
				case Tile.IN_USE: g.setColor(new Color(105, 207, 105)); break;
				case Tile.NOT_IN_USE: g.setColor(new Color(72, 122, 164)); break;
				case Tile.WARNING:	 if(warningFlash) {
										g.setColor(new Color(252, 94, 94)); break;
									} else {
										g.setColor(new Color(0, 0, 0)); break;
									}
				case Tile.UNLINKED: g.setColor(new Color(0, 0, 0)); break;
				}
				g.setLineWidth(10);
				g.drawLine(Grid.x + hackSelectX*tileSize + tileSize/2, Grid.y + hackSelectY*tileSize + tileSize/2,
								hackMouseX, hackMouseY);
				
				g.setColor(new Color(250, 250, 250));
				g.setLineWidth(3);
				g.drawLine(Grid.x + hackSelectX*tileSize + tileSize/2, Grid.y + hackSelectY*tileSize + tileSize/2,
								hackMouseX, hackMouseY);
				g.setAntiAlias(false);
			}
		}
		
		if(uplinkFocus != null && hackView) {
			g.setAntiAlias(true);
			g.setLineWidth(10);
			g.setColor(new Color(250, 250, 250));
			g.drawLine(Grid.x + uplinkFocus.x*tileSize + tileSize/2, 
						Grid.y + uplinkFocus.y*tileSize + tileSize/2,
						Grid.x + tiles[uplinkFocus.x][uplinkFocus.y].uplink.x*tileSize + tileSize/2,
						Grid.y + tiles[uplinkFocus.x][uplinkFocus.y].uplink.y*tileSize + tileSize/2);
			
			switch(tiles[uplinkFocus.x][uplinkFocus.y].uplinkStatus) {
			case Tile.IN_USE: g.setColor(new Color(105, 207, 105)); break;
			case Tile.NOT_IN_USE: g.setColor(new Color(72, 122, 164)); break;
			case Tile.WARNING:	if(warningFlash) {
									g.setColor(new Color(252, 94, 94)); break;
								} else {
									g.setColor(new Color(0, 0, 0)); break;
								}
			case Tile.UNLINKED: g.setColor(new Color(0, 0, 0)); break;
			}

			g.setLineWidth(3);
			g.drawLine(Grid.x + uplinkFocus.x*tileSize + tileSize/2, 
						Grid.y + uplinkFocus.y*tileSize + tileSize/2,
						Grid.x + tiles[uplinkFocus.x][uplinkFocus.y].uplink.x*tileSize + tileSize/2,
						Grid.y + tiles[uplinkFocus.x][uplinkFocus.y].uplink.y*tileSize + tileSize/2);
			g.setAntiAlias(false);
		}
		
		for(int i = 0; i != columns; i++) {
			for(int j = 0; j != rows; j++) {
				if(tiles[i][j].chargerWithRobot) {
					this.charging.draw(Grid.x + i * Grid.tileSize, Grid.y + j * Grid.tileSize);
				}
			}
		}
		
		g.setLineWidth(2);
			
		if(Grid.highFocus.getX() != -1) {
			Grid.highlight(g);
		}
		
		if(this.playFocus.getX() != -1) {
			this.playlight(g);
		}
		
		if(selectBox != null) {
			g.draw(selectBox);
		}
		
		PlayMode.projectileSheet.startUse();
		for(int i = 0; i != projectiles.size(); i++) {
			if(projectiles.get(i) != null) {
				PlayMode.projectileSheet.renderInUse((int)projectiles.get(i).locx,
						(int)projectiles.get(i).locy, projectiles.get(i).spriteCoords.x,
						projectiles.get(i).spriteCoords.y);
			}
		}
		PlayMode.projectileSheet.endUse();
		
		if(Grid.bigExploding) {
			Grid.bigExplosion.draw(Grid.bigExplosionx, Grid.bigExplosiony);
		}
		
		if(Grid.bigExplosion.isStopped()) {
			Grid.bigExploding = false;
		}
		
		if(editMode) {
			int tileX = -1; int tileY = -1;
			
			int[] r = Tile.spritifyFloor(new Tile(bigPaint, bigPaint, 0, 0), editMode);
			if(r[0] == -1) {
				r = Tile.spritifyNode(new Tile(bigPaint, bigPaint, 0, 0), editMode);
			}
			
			tileX = r[0]; tileY = r[1];
			float lx = bigInput.getMouseX(); float ly = bigInput.getMouseY();
			
			int adjustX = (int)(lx); adjustX = (int)(adjustX-Grid.x);
			int adjustY = (int)(ly); adjustY = (int)(adjustY-Grid.y);
			adjustX /= Grid.tileSize; adjustY /= Grid.tileSize;
			
			adjustX = (int)(adjustX*tileSize + Grid.x); 
			adjustY = (int)(adjustY*tileSize + Grid.y);
			
			if(Tile.isTileWeapon(new Tile(bigPaint, bigPaint, 0, 0))) {
				Rectangle rect = new Rectangle(adjustX, adjustY, tileSize, tileSize);
				int type = -1;
				int ori = -1;
				if(bigPaint >= Tile.RAILGUN_0_N && bigPaint <= Tile.RAILGUN_0_W) {
					type = Weapon.RAILGUN_0;
					switch(bigPaint % 100) {
					case 0: ori = 0; break;
					case 2: ori = 1; break;
					case 4: ori = 2; break;
					case 6: ori = 3; break;
					}
				} else if(bigPaint >= Tile.LASER_0_N && bigPaint <= Tile.LASER_0_W) {
					type = Weapon.LASER_0;
					switch(bigPaint % 100) {
					case 8: ori = 0; break;
					case 10: ori = 1; break;
					case 12: ori = 2; break;
					case 14: ori = 3; break;
					}
				}
				weaponSheet.startUse();
				new Weapon(adjustX, adjustY, type, ori).draw(g, rect);
				weaponSheet.endUse();
			}
			
			if(bigPaint == Crew.HUMAN || bigPaint == Crew.ROBOT) {
				if(bigPaint == Crew.HUMAN) {
					this.walkDown.update(1);
					this.walkDown.draw(adjustX, adjustY);
				} else {
					this.roboWalkDown.update(1);
					this.roboWalkDown.draw(adjustX, adjustY);
				}
			} else if(bigPaint == Tile.FIRE) {
				this.fire0.update(1);
				this.fire0.draw(adjustX, adjustY);
			} else {
				tileSheet.startUse();
				tileSheet.renderInUse(adjustX, adjustY, tileX, tileY);
				tileSheet.endUse();
			}
		}		
		
		for(int i = 0; i != columns; i++) {
			for(int j = 0; j != rows; j++) {
				if(tiles[i][j].radEffectRadius != null) {
					g.draw(tiles[i][j].radEffectRadius);
				}
			}
		}
		
		return g;
	}

	public void playUpdate(int delta, Input input) {
		if(input.isKeyDown(Input.KEY_DOWN)) {
			hackView = true;
		} else {
			hackView = false;
		}
		if(input.isKeyDown(Input.KEY_P)) {
			pathView = true;
		} else {
			pathView = false;
		}
		this.hackGlowIter += .001*delta;
		
		if(this.warningFlashIter > 750) {
			this.warningFlash = true;
		} else {
			this.warningFlash = false;
		}
		if(this.warningFlashIter > 1500) {
			this.warningFlashIter = 0;
		} else {
			this.warningFlashIter += 1*delta;
		}

		this.crewUpdate(delta);
		
		this.radUpdate(delta);
		this.oxyUpdate(delta);
		this.fireUpdate(delta);
		
		if(hackTick > 100) {
			hackTick = 0;
			this.powerUpdate();
			Grid.hackUpdate();
		} else {
			hackTick += 1*delta;
		}
		
		this.walkUp.update(delta);
		this.walkDown.update(delta);
		this.walkLeft.update(delta);
		this.walkRight.update(delta);
		
		this.typeUp.update(delta);
		this.typeDown.update(delta);
		this.typeLeft.update(delta);
		this.typeRight.update(delta);
		
		this.extinguishUp.update(delta);
		this.extinguishDown.update(delta);
		this.extinguishLeft.update(delta);
		this.extinguishRight.update(delta);
		
		this.repairUp.update(delta);
		this.repairDown.update(delta);
		this.repairLeft.update(delta);
		this.repairRight.update(delta);
		
		this.roboWalkUp.update(delta);
		this.roboWalkDown.update(delta);
		this.roboWalkLeft.update(delta);
		this.roboWalkRight.update(delta);
		
		this.roboExtinguishUp.update(delta);
		this.roboExtinguishDown.update(delta);
		this.roboExtinguishLeft.update(delta);
		this.roboExtinguishRight.update(delta);
		
		this.roboRepairUp.update(delta);
		this.roboRepairDown.update(delta);
		this.roboRepairLeft.update(delta);
		this.roboRepairRight.update(delta);
		
		this.fire0.update(delta);
		this.fire1.update(delta);
		this.fire2.update(delta);
		this.fire3.update(delta);
		this.fire4.update(delta);
		
		this.charging.update(delta);
		
		for(int i = 0; i != Grid.projectiles.size(); i++) {
			if(Grid.projectiles.get(i) != null) {
				Grid.projectiles.get(i).update(delta);
				if(Grid.showHit(projectiles.get(i).locx, projectiles.get(i).locy, projectiles.get(i).parent)) {
					Grid.projectiles.remove(i); break;
				} else if(projectiles.get(i).lifetime < 0) {
					defoShowHit(projectiles.get(i).parent);
					Grid.projectiles.remove(i); break;
				}
			}
		}
		
		if(bigExploding) {
			shaking = true;
			shakeIter = 0;
			Grid.bigExplosion.update(delta);
		}
		
		if(shaking) {
			if(shakeIter < 15) {
				shakeIter++;
				shakeSine += .05*delta;
				double offset = Math.sin(shakeSine);
				Grid.x += offset;
				for(Crew c : crew) {
					if(c != null) {
						c.locx += offset;
					}
				}
				relocateProjectiles((int)Grid.x, 0, (int)Math.sin(shakeSine)*100, 0);
				relocateExplosions();
			} else {
				shaking = false;
			}
		}
		
		weaponUpdate(delta);
		
	}
	
	private void weaponUpdate(int delta) {
		for(int i = 0; i != Grid.columns; i++) {
			for(int j = 0; j != Grid.rows; j++) {
				if(Grid.tiles[i][j].weapon != null) {
					if(Grid.tiles[i][j].nodeStatus == Tile.ACTIVE) {
						Grid.tiles[i][j].weapon.active = true;
						
						boolean foundTarget = false;
						for(int k = 0; k != Ship.targetAngles.length; k++) {
							if(Ship.targetAngles[k] != -1) {
								if(Grid.tiles[i][j].weapon.inRange(Ship.targetAngles[k])) {
									Grid.tiles[i][j].weapon.angle = Ship.targetAngles[k];
									Grid.tiles[i][j].weapon.firing = true;
									foundTarget = true;
									break;
								}
							}
						}
						
						if(!foundTarget) {
							Grid.tiles[i][j].weapon.firing = false;
						}
						
						Grid.tiles[i][j].weapon.update(delta);
					} else {
						Grid.tiles[i][j].weapon.active = false;
					}
					
				}
			}
		}
	}

	private void crewUpdate(int delta) {
		for(Crew c : crew) {
			if(c != null) {
				if(c.type == Crew.HUMAN)  {
					if(c.inStasis) {
						c.health += .1*delta;
						if(c.health > Crew.maxHealth) {
							c.health = Crew.maxHealth;
						}
					}
					
					if(tiles[c.tilex][c.tiley].fire && !c.inStasis) {
						c.health -= .3*delta;
						if(c.health <= 0 && !c.fireDying) {
							fireDeath[gen.nextInt(fireDeath.length)].playAsSoundEffect(1f, 1f, false);
							c.fireDying = true;
							c.fireDeath = new Animation(crewSheet, 0, 7, 7, 7, true, 100, false);
							c.fireDeath.setLooping(false);
						}
					}
					if(c.fireDying) {
						c.fireDeath.update(delta);
						if(c.fireDeath.isStopped()) {
							c.dead = true;
						}
					}
					
					if(tiles[c.tilex][c.tiley].oxy == 0  && !c.inStasis) {
						if(c.pressureDead == -1) {
							c.health = 0;
							pressureDeath[gen.nextInt(pressureDeath.length)].playAsSoundEffect(1f, 1f, false);
							c.pressureDead = 0;
						}
						
					}
					if(c.pressureDead != -1) {
						c.pressureDead += 1*delta+gen.nextInt(100);
					}
					if(c.pressureDead > 10000) {
						c.dead = true;
					}
				
				}
				
				if(!c.pathing && !c.inStasis) {
					boolean foundFire = false;
					if(tiles[c.tilex][c.tiley].fire) {
						c.extinguishing = true;
						c.typing = false;
						foundFire = true;
						c.extinguishTarget = new Point(c.tilex, c.tiley);
					} else {
						if(c.tilex-1 >= 0) {
							if(tiles[c.tilex-1][c.tiley].fire) {
								c.extinguishing = true;
								c.typing = false;
								c.orientation = 3;
								foundFire = true;
								c.extinguishTarget = new Point(c.tilex-1, c.tiley);
							}
						}
						if(c.tilex+1 < columns) {
							if(tiles[c.tilex+1][c.tiley].fire) {
								c.extinguishing = true;
								c.typing = false;
								c.orientation = 1;
								foundFire = true;
								c.extinguishTarget = new Point(c.tilex+1, c.tiley);
							}
						}
						if(c.tiley-1 >= 0) {
							if(tiles[c.tilex][c.tiley-1].fire) {
								c.extinguishing = true;
								c.typing = false;
								c.orientation = 0;
								foundFire = true;
								c.extinguishTarget = new Point(c.tilex, c.tiley-1);
							}
						}
						if(c.tiley+1 < rows) {
							if(tiles[c.tilex][c.tiley+1].fire) {
								c.extinguishing = true;
								c.typing = false;
								c.orientation = 2;
								foundFire = true;
								c.extinguishTarget = new Point(c.tilex, c.tiley+1);
							}
						}
					}
					if(!foundFire) {
						if(c.extinguishing) {
							if(!this.continueExtinguishing(c, delta)) {
								c.extinguishing = false;
								for(int i = 0; i != c.co2.length; i++) {
									c.co2[i] = null;
								}
							}
						}
					}
				}
				
				if(c.extinguishing && !c.pathing) {
					if(c.type == Crew.HUMAN) {
						tiles[(int)c.extinguishTarget.getX()][(int)c.extinguishTarget.getY()].fireStrength -= 1*delta;
					} else if(c.type == Crew.ROBOT) {
						tiles[(int)c.extinguishTarget.getX()][(int)c.extinguishTarget.getY()].fireStrength -= 3*delta;
					}
					
					if(c.nextCo2 > 30) {
						c.nextCo2 = 0;
						if(c.co2Iter == c.co2.length) {
							c.co2Iter = 0;
						}
						switch(c.orientation) {
						case 0: c.co2[c.co2Iter++] = new Point((float)(c.locx), (float)(c.locy)); break;
						case 1: c.co2[c.co2Iter++] = new Point((float)(c.locx), (float)(c.locy)); break;
						case 2: c.co2[c.co2Iter++] = new Point((float)(c.locx), (float)(c.locy)); break;
						case 3: c.co2[c.co2Iter++] = new Point((float)(c.locx), (float)(c.locy)); break;
						}
					} else {
						c.nextCo2 += 1*delta;
						for(int i = 0; i != c.co2.length; i++) {
							if(c.co2[i] != null) {
								switch(c.orientation) {
								case 0: c.co2[i].setY(c.co2[i].getY()-(.08f)*delta);
										c.co2[i].setX(c.co2[i].getX()+(((gen.nextInt(21)-10)/50f)*delta)); break;
								case 1: c.co2[i].setX(c.co2[i].getX()+(.08f)*delta); 
										c.co2[i].setY(c.co2[i].getY()+(((gen.nextInt(21)-10)/50f)*delta)); break;
								case 2: c.co2[i].setY(c.co2[i].getY()+(.08f)*delta); 
										c.co2[i].setX(c.co2[i].getX()+(((gen.nextInt(21)-10)/50f)*delta)); break;
								case 3: c.co2[i].setX(c.co2[i].getX()-(.08f)*delta);
										c.co2[i].setY(c.co2[i].getY()+(((gen.nextInt(21)-10)/50f)*delta)); break;
								}
							}
						}
					}
				}
				
				if(!c.pathing && !c.extinguishing) {
					if(c.repairx != -1) {
						if(c.tilex > c.repairx) c.orientation = 3;
						if(c.tilex < c.repairx) c.orientation = 1;
						if(c.tiley > c.repairy) c.orientation = 0;
						if(c.tiley < c.repairy) c.orientation = 2;
						
						switch(c.orientation) {
						case 0: if(c.repairx == c.tilex && c.repairy+1 == c.tiley) {
									c.repairing = true;
								} break;
						case 1: if(c.repairy == c.tiley && c.repairx-1 == c.tilex) {
									c.repairing = true;
								} break;
						case 2: if(c.repairx == c.tilex && c.repairy-1 == c.tiley) {
									c.repairing = true;
								} break;
						case 3: if(c.repairy == c.tiley && c.repairx+1 == c.tilex) {
									c.repairing = true;
								} break;
						}
						
						if(c.repairing) {
							if(tiles[c.repairx][c.repairy].damage < 0) {
								tiles[c.repairx][c.repairy].convertNodeToFixed();
								c.repairing = false; c.repairx = -1; c.repairy = -1;
							} else {
								if(c.type == Crew.HUMAN) {
									tiles[c.repairx][c.repairy].damage -= 1*delta;
								} else if(c.type == Crew.ROBOT) {
									tiles[c.repairx][c.repairy].damage -= 4*delta;
								}
							}
						}
					}
				}
				
				if(c.type == Crew.HUMAN) {
					if(tiles[c.tilex][c.tiley].rads > 0) {
						c.health -= .01*tiles[c.tilex][c.tiley].rads*delta;
						
						if(c.health < 0 && !c.radDying) {
							for(int i = 0; i != fireDeath.length; i++) {
								fireDeath[i].stop();
							}
							fireDeath[gen.nextInt(fireDeath.length)].playAsSoundEffect(1f, 1f, false);
							c.radDying = true;
							c.radDeath = new Animation(crewSheet, 0, 13, 7, 13, true, 100, false);
							c.radDeath.setLooping(false);
						}
	 				}
					
				}
				if(c.radDying) {
					c.radDeath.update(delta);
					if(c.radDeath.isStopped()) {
						c.dead = true;
						
					}
					
				}
				
				if(chargeTick > 400) {
					chargeTick = 0;
					if(c.type == Crew.ROBOT) {
						if(tiles[c.tilex][c.tiley].chargerWithRobot) {
							c.stepsLeft += 1*delta;
							if(c.stepsLeft > c.maxSteps) {
								c.stepsLeft = c.maxSteps;
							}
						}
					}
				} else {
					chargeTick += 1*delta;
				}
				
				if(c.pathing) {
					if(c.type == Crew.ROBOT && c.stepsLeft > 0 || c.type != Crew.ROBOT) {
						for(int i = 0; i != c.co2.length; i++) {
							c.co2[i] = null;
						}
						if(c.stepGap > c.stepLength) {
							if(Tile.isTilePathable(tiles[c.currentPath.getX(c.pathIter)][c.currentPath.getY(c.pathIter)],
								c.currentPath.getX(c.pathIter), c.currentPath.getY(c.pathIter))) {
								
								if(c.type == Crew.ROBOT) {
									c.stepsLeft--;
								}
								switch(tiles[c.tilex][c.tiley].node) {
								case Tile.VO_DOOR: tiles[c.tilex][c.tiley].node = Tile.VC_DOOR;
													doorOpen.stop(); doorClose.stop();
													doorClose.playAsSoundEffect(1.7f, 1.5f, false); break;
								case Tile.HO_DOOR: tiles[c.tilex][c.tiley].node = Tile.HC_DOOR;
													doorOpen.stop(); doorClose.stop();
													doorClose.playAsSoundEffect(1.7f, 1.5f, false); break;
								}
								
								c.tilex = c.currentPath.getX(c.pathIter);
								c.tiley = c.currentPath.getY(c.pathIter++);
								
								switch(tiles[c.tilex][c.tiley].node) {
								case Tile.VC_DOOR: tiles[c.tilex][c.tiley].node = Tile.VO_DOOR;
													doorOpen.stop(); doorClose.stop();
													doorOpen.playAsSoundEffect(1.7f, 1.5f, false); break;
								case Tile.HC_DOOR: tiles[c.tilex][c.tiley].node = Tile.HO_DOOR;
													doorOpen.stop(); doorClose.stop();
													doorOpen.playAsSoundEffect(1.7f, 1.5f, false); break;
								}
								
								if(c.pathIter < c.currentPath.getLength()) {
									if(c.currentPath.getX(c.pathIter)<c.tilex) c.orientation = 3;
									else if(c.currentPath.getX(c.pathIter)>c.tilex) c.orientation = 1;
									else if(c.currentPath.getY(c.pathIter)<c.tiley) c.orientation = 0;
									else if(c.currentPath.getY(c.pathIter)>c.tiley) c.orientation = 2;
								}
								
								c.stepGap = 0;
								c.locx = Grid.x + c.tilex*Grid.tileSize;
								c.locy = Grid.y + c.tiley*Grid.tileSize;
								if(c.tilex == c.goalx && c.goaly == c.tiley) {
									c.pathing = false;
									c.pathIter = 0;
									c.locx = Grid.x + c.tilex*Grid.tileSize;
									c.locy = Grid.y + c.tiley*Grid.tileSize;
									c.stepGap = c.stepLength;
									
									crewStackAvoidance(c);
								}
							} else {
								
								if(Tile.isTilePathable(tiles[c.goalx][c.goaly], c.goalx, c.goaly)) {
									c.calculatingPath = true;
								} else {
									c.pathing = false;
									c.pathIter = 0;
									c.locx = Grid.x + c.tilex*Grid.tileSize;
									c.locy = Grid.y + c.tiley*Grid.tileSize;
									c.stepGap = c.stepLength;
									
									crewStackAvoidance(c);
								}
								
								
							}
						}
						c.stepGap += 1*delta; 
						if(c.pathIter < c.currentPath.getLength()) {
							switch(c.orientation) {
							case 0: c.locy -= (Grid.tileSize/(double)c.stepLength)*delta; break;
							case 1: c.locx += (Grid.tileSize/(double)c.stepLength)*delta; break;
							case 2: c.locy += (Grid.tileSize/(double)c.stepLength)*delta; break;
							case 3: c.locx -= (Grid.tileSize/(double)c.stepLength)*delta; break;
							}
						}
					} else if(c.type == Crew.ROBOT && c.stepsLeft == 0) {
						c.pathing = false;
					}
					
				} 
				
				if(c.type == Crew.HUMAN) {
					if(c.goalx != -1) {
						if(tiles[c.tilex][c.tiley].uplinkStatus == Tile.IN_USE &&
								c.tilex == c.goalx && c.tiley == c.goaly) {
							c.typing = true;
						} else {
							c.typing = false;
						}
					}
				}
				
				if(!c.pathing && !c.extinguishing && c.type == Crew.HUMAN) {
					if(tiles[c.tilex][c.tiley].node == Tile.STASIS_VACANT) {
						c.inStasis = true;
						tiles[c.tilex][c.tiley].node = Tile.STASIS_OCCUPIED;
					} 
				}
			}
			
			
				
		} 
		
		
		
		for(int i = 0; i != crew.size(); i++) {
			if(crew.get(i) != null) {
				if(crew.get(i).dead) {
					crew.remove(i);
					
					for(int j = 0; j != crewSelect.length; j++) {
						if(crewSelect[j] == i) {
							crewSelect[j] = -1;
						} else if(crewSelect[j] != -1 && crewSelect[j] > i) {
							crewSelect[j]--;
						}
					}
					break;
				}
			}
		}
	}
	
	private boolean continueExtinguishing(Crew c, int delta) {
		if(c.type == Crew.ROBOT && c.stepsLeft == 0) {
			return false;
		}
		
		boolean foundFire = false;
		if(c.tilex-1 >= 0 && c.tiley-1 >= 0) {
			if(tiles[c.tilex-1][c.tiley-1].fire) {
				if(Tile.isTilePathable(tiles[c.tilex][c.tiley-1], c.tilex, c.tiley-1)) {
					foundFire = true;
					c.goalx = c.tilex;
					c.goaly = c.tiley-1;
					c.calculatingPath = true;
				}
			}
		}
		if(c.tilex+1 < columns  && c.tiley-1 >= 0) {
			if(tiles[c.tilex+1][c.tiley-1].fire) {
				if(Tile.isTilePathable(tiles[c.tilex][c.tiley-1], c.tilex, c.tiley-1)) {
					c.goalx = c.tilex+1;
					c.goaly = c.tiley;
					c.calculatingPath = true;
				}
			}
		}
		if(c.tiley+1 < rows && c.tilex-1 >= 0) {
			if(tiles[c.tilex-1][c.tiley+1].fire) {
				if(Tile.isTilePathable(tiles[c.tilex-1][c.tiley], c.tilex-1, c.tiley)) {
					foundFire = true;
					c.goalx = c.tilex-1;
					c.goaly = c.tiley;
					c.calculatingPath = true;
				}
			}
		}
		if(c.tiley+1 < rows && c.tilex+1 < columns) {
			if(tiles[c.tilex+1][c.tiley+1].fire) {
				if(Tile.isTilePathable(tiles[c.tilex][c.tiley+1], c.tilex, c.tiley+1)) {
					foundFire = true;
					c.goalx = c.tilex;
					c.goaly = c.tiley+1;
					c.calculatingPath = true;
				}
			}
		}
		
		
		return foundFire;
	}
	
	private void crewStackAvoidance(Crew c) {
		for(Crew cc : crew) {
			if(cc != null && cc != c) {
				if(cc.tilex == c.tilex && cc.tiley == c.tiley) {
					switch(gen.nextInt(4)) {
					case 0: if(c.tilex+1 < columns) {
								if(Tile.isTilePathable(tiles[c.tilex+1][c.tiley], c.tilex, c.tiley)) {
									c.goalx = c.tilex + 1; c.goaly = c.tiley; c.calculatingPath = true;
									break;
								}
							}
					case 1: if(c.tilex-1 >= 0) {
								if(Tile.isTilePathable(tiles[c.tilex-1][c.tiley], c.tilex-1, c.tiley)) {
									c.goalx = c.tilex - 1; c.goaly = c.tiley; c.calculatingPath = true;
									break;
								}
							}	
					case 2: if(c.tiley+1 < rows) {
								if(Tile.isTilePathable(tiles[c.tilex][c.tiley+1], c.tilex, c.tiley+1)) {
									c.goalx = c.tilex; c.goaly = c.tiley + 1; c.calculatingPath = true;
									break;
								}
							}
					case 3: if(c.tiley-1 >= 0) {
								if(Tile.isTilePathable(tiles[c.tilex][c.tiley-1], c.tilex, c.tiley-1)) {
									c.goalx = c.tilex; c.goaly = c.tiley - 1; c.calculatingPath = true;
									break;
								}
							}
					}
				}
			}
		}
	}
	
	private void radUpdate(int delta) {
		if(this.radTick > 500) {
			for(int i = 0; i != tiles.length; i++) {
				for(int j = 0; j != tiles[0].length; j++) {
					boolean radSource = false;
					if(tiles[i][j].node == Tile.BROKEN_CORE) {
						if(tiles[i][j].rads == -1) {
							tiles[i][j].rads = 0;
						}
						tiles[i][j].rads += .1*delta;
						if(tiles[i][j].rads > 15) {
							tiles[i][j].rads = 15;
						}
						radSource = true;
					} else if(tiles[i][j].node == Tile.CORE) {
						if(tiles[i][j].rads > 4) {
							tiles[i][j].radsIncreasing = false;
						} 
						
						if(tiles[i][j].rads < -2) {
							tiles[i][j].radsIncreasing = true;
						} 
						
						if(tiles[i][j].nodeStatus != Tile.ACTIVE) {
							tiles[i][j].rads = 0;
						}
						
						if(tiles[i][j].radsIncreasing) {
							tiles[i][j].rads += .1*delta;
						} else {
							tiles[i][j].rads -= .1*delta;
						}
						
						radSource = true;
					}
					
					if(radSource) {
						tiles[i][j].radEffectRadius = new Circle((float)(Grid.x + i*tileSize + tileSize/2),
																(float)(Grid.y + j*tileSize + tileSize/2),
																(float)(((float)tileSize*tiles[i][j].rads)/2));
						
						for(int k = 0; k != tiles.length; k++) {
							for(int l = 0; l != tiles[0].length; l++) {
								if(!(i == k && j == l)) {
									if(tiles[i][j].radEffectRadius.contains(Grid.x + k*tileSize + tileSize/2,
																			Grid.y + l*tileSize + tileSize/2) &&
										Tile.isTileRaddable(tiles[k][l])) {
										tiles[k][l].inRad = true;
									}
								}
							}
						}
					}
					
					if(!tiles[i][j].inRad && !radSource) {
						tiles[i][j].rads -= .15*delta;
						if(tiles[i][j].rads < 0) {
							tiles[i][j].rads = -1;
						}
					}
					
					if(tiles[i][j].inRad && !radSource) {
							tiles[i][j].rads += .25*delta;
						if(tiles[i][j].rads > 15) {
							tiles[i][j].rads = 15;
						}
					}
						
					tiles[i][j].inRad = false;
					radTick = 0;
				}
			}
		} else {
			this.radTick += 1*delta;
		}
	}
	
	private void oxyUpdate(int delta) {
		if(this.oxyTick > 1000) {
			this.oxyTick = 0;
			for(int i = 0; i != tiles.length; i++) {
				for(int j = 0; j != tiles[0].length; j++) {
					if(tiles[i][j].oxy < 8) {
						tiles[i][j].oxy = 8;
					}
				}
			}
		} else {
			this.oxyTick += 1*delta;
			this.oxyTickOn = false;
		}
		
		if(this.oxyTick < 500) {
			this.oxyTickOn = true;
		}
		
		for(int k = 0; k != 1; k++) {
			for(int i = 0; i != tiles.length; i++) {
				for(int j = 0; j != tiles[0].length; j++) {
					if(tiles[i][j].floor == Tile.EMPTY || Tile.isTileGirder(tiles[i][j])) {
						if(i - 1 >= 0) {
							if(Tile.isTilePressurized(tiles[i-1][j])) {
								tiles[i-1][j].oxy = 0;
								tiles[i-1][j].fire = false;
							}
						}
						if(i + 1 < columns) {
							if(Tile.isTilePressurized(tiles[i+1][j])) {
								tiles[i+1][j].oxy = 0;
								tiles[i+1][j].fire = false;
							}
						}
						if(j - 1 >= 0) {
							if(Tile.isTilePressurized(tiles[i][j-1])) {
								tiles[i][j-1].oxy = 0;
								tiles[i][j-1].fire = false;
							}
						}
						if(j + 1 < rows) {
							if(Tile.isTilePressurized(tiles[i][j+1])) {
								tiles[i][j+1].oxy = 0;
								tiles[i][j+1].fire = false;
							}
						}
					} else if(tiles[i][j].floor != Tile.WALL_0 &&
							  tiles[i][j].node != Tile.VC_DOOR && 
							  tiles[i][j].node != Tile.HC_DOOR) {
						if(i - 1 >= 0) {
							if(tiles[i-1][j].oxy == 0) {
								tiles[i][j].oxy = 0;
								tiles[i][j].fire = false;
							}
						} if(i + 1 < columns) {
							if(tiles[i+1][j].oxy == 0) {
								tiles[i][j].oxy = 0;
								tiles[i][j].fire = false;
							}
						} if(j - 1 >= 0) {
							if(tiles[i][j-1].oxy == 0) {
								tiles[i][j].oxy = 0;
								tiles[i][j].fire = false;
							}
						} if(j + 1 < rows) {
							if(tiles[i][j+1].oxy == 0) {
								tiles[i][j].oxy = 0;
								tiles[i][j].fire = false;
							}
						}
					}
				}
			}
		}
	}
	
	private void fireUpdate(int delta) {
		Grid.pathify();
		if(this.fireTick > 200) {
			this.fireTick = 0;
			for(int i = 0; i != tiles.length; i++) {
				for(int j = 0; j != tiles[0].length; j++) {
					if(tiles[i][j].oxy == 8) {
						if(tiles[i][j].fire && !tiles[i][j].kindling) {
							if(tiles[i][j].fireStrength > 550) {
								if(!tiles[i][j].isNodeBroken()) {
									Grid.nodeDamaged(i, j);
								}
								
							}
							
							switch(gen.nextInt(4)) {
							case 0: if(i - 1 >= 0 && gen.nextInt(10) == 0) {
										if(tiles[i-1][j].floor != Tile.WALL_0) {
											if(!tiles[i-1][j].fire && Tile.isTilePressurized(tiles[i-1][j])) {
												tiles[i-1][j].fire = true;
												tiles[i-1][j].fireStrength = Tile.fireStrengthStart;
												tiles[i-1][j].kindling = true;
											}
										}
									} break;
							case 1: if(i + 1 < columns && gen.nextInt(10) == 0) {
										if(tiles[i+1][j].floor != Tile.WALL_0) {
											if(!tiles[i+1][j].fire && Tile.isTilePressurized(tiles[i+1][j])) {
												tiles[i+1][j].fire = true;
												tiles[i+1][j].fireStrength = Tile.fireStrengthStart;
												tiles[i+1][j].kindling = true;
											}
										}
									} break;
							case 2: if(j - 1 >= 0 && gen.nextInt(10) == 0) {
										if(tiles[i][j-1].floor != Tile.WALL_0) {
											if(!tiles[i][j-1].fire && Tile.isTilePressurized(tiles[i][j-1])) {
												tiles[i][j-1].fire = true;
												tiles[i][j-1].fireStrength = Tile.fireStrengthStart;
												tiles[i][j-1].kindling = true;
											}
										}
									} break;
							case 3: if(j + 1 < rows && gen.nextInt(10) == 0) {
										if(tiles[i][j+1].floor != Tile.WALL_0) {
											if(!tiles[i][j+1].fire && Tile.isTilePressurized(tiles[i][j+1])) {
												tiles[i][j+1].fire = true;
												tiles[i][j+1].fireStrength = Tile.fireStrengthStart;
												tiles[i][j+1].kindling = true;
											}
										}
									} break;
							}
						}
						tiles[i][j].kindling = false;
					} else {
						tiles[i][j].fire = false;
						tiles[i][j].kindling = false;
					}
				}
			}
		} else {
			fireTick += 1*delta;
			for(int i = 0; i != tiles.length; i++) {
				for(int j = 0; j != tiles[0].length; j++) {
					if(tiles[i][j].fire) {
						if(tiles[i][j].fireStrength < 0) {
							tiles[i][j].fire = false;
							tiles[i][j].fireStrength = Tile.fireStrengthStart;
						} else {
							if(tiles[i][j].fireStrength < Tile.fireStrengthMax) {
								tiles[i][j].fireStrength += .02*delta;
							}
						}
					}
				}
			}
		}
	}
	
	private static void hackUpdate() {
		for(int i = 0; i != tiles.length; i++) {
			for(int j = 0; j != tiles[0].length; j++) {
				if(Tile.isTileRemoteNode(tiles[i][j])) {
					if(tiles[i][j].nodeStatus == Tile.ACTIVE || tiles[i][j].nodeStatus == Tile.CONNECTED) {
						tiles[i][j].nodeStatus = Tile.OFFLINE;
					}
				}
			}
		}
	
		for(int i = 0; i != tiles.length; i++) {
			for(int j = 0; j != tiles[0].length; j++) {
				if(Tile.isTileTerminal(tiles[i][j])) {
					if(tiles[i][j].uplink != null) {
						if(tiles[i][j].uplinkStatus == Tile.UNLINKED) {
							tiles[i][j].uplinkStatus = Tile.NOT_IN_USE;
						}
						
						if(tiles[i][j].uplinkStatus == Tile.WARNING) {
							tiles[i][j].uplinkStatus = Tile.NOT_IN_USE;
						}
						
						if(tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y].isNodeBroken()) {
							tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y].nodeStatus = Tile.WARNING;
						}
						
						if(tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y].nodeStatus != Tile.WARNING) {
							tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y].nodeStatus = Tile.CONNECTED;
						}
						
						
						
						switch(tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y].nodeStatus) {
						case Tile.WARNING: tiles[i][j].uplinkStatus = Tile.WARNING; break;
						}
						
						boolean activated = false;
						int crewIndex = Tile.isTileCrewed(i, j, crew);
						if(tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y].nodeStatus == Tile.CONNECTED ||
							tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y].nodeStatus == Tile.OFFLINE) {
							if(crewIndex != -1) 
							if(crew.get(crewIndex).type == Crew.HUMAN) 
							if(crew.get(crewIndex).tilex == crew.get(crewIndex).goalx && crew.get(crewIndex).tiley == crew.get(crewIndex).goaly)
							if(power + Tile.netPower(tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y]) >= 0) {
								tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y].nodeStatus = Tile.ACTIVE;
								power += Tile.netPower(tiles[(int)tiles[i][j].uplink.x][(int)tiles[i][j].uplink.y]);
								crew.get(crewIndex).typing = true;
								crew.get(crewIndex).orientation = tiles[crew.get(crewIndex).tilex][crew.get(crewIndex).tiley].node % 10;
								tiles[i][j].uplinkStatus = Tile.IN_USE;
								activated = true;
								
							}	
						}
						
						if(!activated) {
							if(crewIndex != -1) {
								if(crew.get(crewIndex).type == Crew.HUMAN) {
									crew.get(crewIndex).typing = false;
								}
							}
							if(tiles[i][j].uplinkStatus == Tile.IN_USE) {
								tiles[i][j].uplinkStatus = Tile.NOT_IN_USE;
							}
						
							
						}
						
						/*
						boolean toggled = false;
						for(Crew c : crew) {
							if(c != null) {
								if(c.type == Crew.HUMAN) {
									if(c.tilex == i && c.tiley == j && c.goalx == i && c.goaly == j) {
										if(tiles[i][j].uplinkStatus == Tile.NOT_IN_USE) {
											tiles[i][j].uplinkStatus = Tile.IN_USE;
											
										}
										toggled = true;
									}
								}
							}
						}
						
						if(!toggled) {
							if(tiles[i][j].uplinkStatus == Tile.IN_USE) {
								tiles[i][j].uplinkStatus = Tile.NOT_IN_USE;
							}
						}
						*/
					} else {
						//tiles[i][j].uplinkStatus = Tile.UNLINKED;
					}
				}
			}
		}
		
		Ship.powerRemaining = power;
	}
	
	private void powerUpdate() {
		power = 0;
		double theoPower = 0;
		
		Ship.engines = 0;
		Ship.shieldGenerators = 0;
		Ship.activeShieldGenerators = 0;
		Ship.tractors = 0;
		Ship.activeTractors = 0;
		
		PlayMode.maxtit = 0;
		PlayMode.maxura = 0;
		PlayMode.maxfue = 0;
		PlayMode.maxgol = 0;
		
		for(int i = 0; i != columns; i++) {
			for(int j = 0; j != rows; j++) {
				power += Tile.grossPower(tiles[i][j]);
				theoPower += Tile.theoreticalPower(tiles[i][j]);
				
				if(tiles[i][j].node == Tile.ENGINE && tiles[i][j].nodeStatus == Tile.ACTIVE) {
					Ship.engines++; 
				}
				
				if(tiles[i][j].node == Tile.TRACTOR) {
					if(tiles[i][j].nodeStatus == Tile.ACTIVE) {
						Ship.activeTractors++;
					} else {
						Ship.tractors++;
					}
				}
				
				if(tiles[i][j].node == Tile.SHIELD_GEN) {				
					Ship.shieldGenerators++; 
					if(tiles[i][j].nodeStatus == Tile.ACTIVE) {
						Ship.activeShieldGenerators++; 
					}
				}
				
				if(tiles[i][j].node >= Tile.TITANIUM_DUMP_0 && tiles[i][j].node <= Tile.TITANIUM_DUMP_3) {
					PlayMode.maxtit += 500;
				}
				if(tiles[i][j].node >= Tile.URANIUM_DUMP_0 && tiles[i][j].node <= Tile.URANIUM_DUMP_3) {
					PlayMode.maxura += 100;
				}
				if(tiles[i][j].node >= Tile.FUEL_DUMP_0 && tiles[i][j].node <= Tile.FUEL_DUMP_3) {
					PlayMode.maxfue += 500;
				}
				if(tiles[i][j].node >= Tile.GOLD_DUMP_0 && tiles[i][j].node <= Tile.GOLD_DUMP_3) {
					PlayMode.maxgol += 40;
				}
				
				if(tiles[i][j].node == Tile.CHARGER) {
					for(Crew c : crew) {
						if(c != null) {
							if(c.type == Crew.ROBOT && c.tilex == i && c.tiley == j) {
								tiles[i][j].chargerWithRobot = true; break;
							} else {
								tiles[i][j].chargerWithRobot = false;
							}
						}
					}
				}
			}
		}

		
		Ship.powerTheoretical = theoPower;
		Ship.powerTotal = power;
		Ship.engineConfig();
		Ship.shieldConfig();
		if(PlayMode.titanium > PlayMode.maxtit) PlayMode.titanium = PlayMode.maxtit;
		if(PlayMode.uranium > PlayMode.maxura) PlayMode.uranium = PlayMode.maxura;
		if(PlayMode.fuel > PlayMode.maxfue) PlayMode.fuel = PlayMode.maxfue;
		if(PlayMode.gold > PlayMode.maxgol) PlayMode.gold = PlayMode.maxgol;
		
		double temptit = PlayMode.titanium;
		double tempura = PlayMode.uranium;
		double tempfue = PlayMode.fuel;
		double tempgol = PlayMode.gold;
		
		for(int i = 0; i != columns; i++) {
			for(int j = 0; j != rows; j++) {
				if(tiles[i][j].node >= Tile.TITANIUM_DUMP_0 && tiles[i][j].node <= Tile.TITANIUM_DUMP_3) {
					if(temptit >= 500) {tiles[i][j].node = Tile.TITANIUM_DUMP_3; temptit -= 500;}
					else if(temptit >= 375) {tiles[i][j].node = Tile.TITANIUM_DUMP_2; temptit -= 375;}
					else if(temptit >= 250) {tiles[i][j].node = Tile.TITANIUM_DUMP_1; temptit -= 250;}
					else if(temptit >= 125) {tiles[i][j].node = Tile.TITANIUM_DUMP_0; temptit -= 125;}
				}
				if(tiles[i][j].node >= Tile.URANIUM_DUMP_0 && tiles[i][j].node <= Tile.URANIUM_DUMP_3) {
					if(tempura >= 100) {tiles[i][j].node = Tile.URANIUM_DUMP_3; tempura -= 100;}
					else if(tempura >= 75) {tiles[i][j].node = Tile.URANIUM_DUMP_2; tempura -= 75;}
					else if(tempura >= 50) {tiles[i][j].node = Tile.URANIUM_DUMP_1; tempura -= 50;}
					else if(tempura >= 25) {tiles[i][j].node = Tile.URANIUM_DUMP_0; tempura -= 25;}
				}
				if(tiles[i][j].node >= Tile.FUEL_DUMP_0 && tiles[i][j].node <= Tile.FUEL_DUMP_3) {
					if(tempfue >= 500) {tiles[i][j].node = Tile.FUEL_DUMP_3; tempfue -= 500;}
					else if(tempfue >= 375) {tiles[i][j].node = Tile.FUEL_DUMP_2; tempfue -= 375;}
					else if(tempfue >= 250) {tiles[i][j].node = Tile.FUEL_DUMP_1; tempfue -= 250;}
					else if(tempfue >= 125) {tiles[i][j].node = Tile.FUEL_DUMP_0; tempfue -= 125;}
				}
				if(tiles[i][j].node >= Tile.GOLD_DUMP_0 && tiles[i][j].node <= Tile.GOLD_DUMP_3) {
					if(tempgol >= 40) {tiles[i][j].node = Tile.GOLD_DUMP_3; tempgol -= 40;}
					else if(tempgol >= 30) {tiles[i][j].node = Tile.GOLD_DUMP_2; tempgol -= 30;}
					else if(tempgol >= 20) {tiles[i][j].node = Tile.GOLD_DUMP_1; tempgol -= 20;}
					else if(tempgol >= 10) {tiles[i][j].node = Tile.GOLD_DUMP_0; tempgol -= 10;}
				}
			}
		}
	}
	
	public static boolean hit(Projectile bullet, Bot bot) {
		if(Ship.shields > 1) {
			Ship.shields -= bullet.damage;
			Ship.shieldsHit = 0;
			if(Ship.shields > 1) {
				PlayMode.shieldHit.playAsSoundEffect(1f, 1f, false);
			}
			return true;
		} else if(Ship.radius.contains((float)bullet.locx, (float)bullet.locy)) {
			
			ArrayList<Point> candidates = new ArrayList<Point>();
			for(int i = 0; i != Grid.columns; i++) {
				for(int j = 0; j != Grid.rows; j++) {
					if(tiles[i][j].node == bot.targetTile) {
						candidates.add(new Point(i, j));
					}
				}
			}
			
			if(gen.nextInt(100) < bot.accuracy && candidates.size() > 0) {
				int i = gen.nextInt(candidates.size());
				int id = gen.nextInt(1000000000);
				Grid.tiles[(int)candidates.get(i).getX()][(int)candidates.get(i).getY()].imminentExplosion = true;
				Grid.tiles[(int)candidates.get(i).getX()][(int)candidates.get(i).getY()].imexIter = id;
				Grid.tiles[(int)candidates.get(i).getX()][(int)candidates.get(i).getY()].imminentProjectile = bullet;
				Projectile b = null;
				switch(gen.nextInt(4)) {
				case 0: b = new Projectile(Grid.x + candidates.get(i).getX()*Grid.tileSize-100, 
						Grid.y + candidates.get(i).getY()*Grid.tileSize-100, 135, bullet.type+100, id); break;
				case 1: b = new Projectile(Grid.x + candidates.get(i).getX()*Grid.tileSize-100, 
						Grid.y + candidates.get(i).getY()*Grid.tileSize+100, 45, bullet.type+100, id); break;
				case 2: b = new Projectile(Grid.x + candidates.get(i).getX()*Grid.tileSize+100, 
						Grid.y + candidates.get(i).getY()*Grid.tileSize-100, 225, bullet.type+100, id); break;
				case 3: b = new Projectile(Grid.x + candidates.get(i).getX()*Grid.tileSize+100, 
						Grid.y + candidates.get(i).getY()*Grid.tileSize+100, 315, bullet.type+100, id); break;
				}
				
				projectiles.add(b);
			}
			
			return true;
		}	
		return false;
	}
	
	public static boolean showHit(double lx, double ly, int id) {
		int adjustX = (int)(lx); adjustX = (int)(adjustX-Grid.x);
		int adjustY = (int)(ly); adjustY = (int)(adjustY-Grid.y);
		adjustX /= Grid.tileSize; adjustY /= Grid.tileSize;
		for(int i = 0; i != columns; i++) {
			for(int j = 0; j != rows; j++) {

				if(tiles[i][j].imexIter == id) {
					Circle test = new Circle(Grid.x + i*tileSize + tileSize/2, Grid.y + j*tileSize + tileSize/2, tileSize*2);
					if(test.contains((float)lx, (float)ly)) {
						if(gen.nextInt(100) < tiles[i][j].imminentProjectile.nodeDestroyChance) {
							nodeDamaged(i, j);
						}
						
						if(gen.nextInt(100) < tiles[i][j].imminentProjectile.fireStartChance) {
							for(int k = i-tiles[i][j].imminentProjectile.blastRadius; k != i+tiles[i][j].imminentProjectile.blastRadius; k++) {
								for(int l = j-tiles[i][j].imminentProjectile.blastRadius; l != j+tiles[i][j].imminentProjectile.blastRadius; l++) {
									if(k >= 0 && k < columns && l >= 0 && l < rows) {
										if(tiles[k][l].floor == Tile.PATH_N) {
											tiles[k][l].fire = true;
											tiles[k][l].fireStrength = Tile.fireStrengthStart;
											tiles[k][l].kindling = true;
										}
									}
								}
							}
						}
						
						if(gen.nextInt(100) < tiles[i][j].imminentProjectile.breachChance) {
							tiles[i][j].node = -1;
							tiles[i][j].floor = Tile.EMPTY;
						}
						
						Grid.bigExplosion = new Animation(effectSheet, 0, 0, 7, 0, true, 20, false);
						Grid.bigExplosion.stopAt(7);
						Grid.bigExploding = true; 
						Grid.bigExplosionx = x + i*Grid.tileSize + Grid.tileSize/2 - Grid.tileSize;
						Grid.bigExplosiony = y + j*Grid.tileSize + Grid.tileSize/2 - Grid.tileSize;
						Grid.tiles[i][j].imminentExplosion = false;
						for(int k = 0; k != explosions.length; k++) {
							explosions[k].stop();
						}
						explosions[gen.nextInt(3)].playAsSoundEffect(.8f, 1f, false);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static void defoShowHit(int id) {
		for(int i = 0; i != columns; i++) {
			for(int j = 0; j != rows; j++) {
				if(tiles[i][j].imexIter == id) {
					
					if(gen.nextInt(100) < tiles[i][j].imminentProjectile.nodeDestroyChance) {
						nodeDamaged(i, j);
					}
					
					if(gen.nextInt(100) < tiles[i][j].imminentProjectile.fireStartChance) {
						for(int k = i-tiles[i][j].imminentProjectile.blastRadius; k != i+tiles[i][j].imminentProjectile.blastRadius+1; k++) {
							for(int l = j-tiles[i][j].imminentProjectile.blastRadius; l != j+tiles[i][j].imminentProjectile.blastRadius+1; l++) {
								if(k >= 0 && k < columns && l >= 0 && l < rows) {
									if(tiles[k][l].floor == Tile.PATH_N) {
										tiles[k][l].fire = true;
										tiles[k][l].fireStrength = Tile.fireStrengthStart;
										tiles[k][l].kindling = true;
									}
								}
							}
						}
					}
					
					if(gen.nextInt(100) < tiles[i][j].imminentProjectile.breachChance) {
						tiles[i][j].node = -1;
						tiles[i][j].floor = Tile.EMPTY;
					}
					
					Grid.bigExplosion = new Animation(effectSheet, 0, 0, 7, 0, true, 20, false);
					Grid.bigExplosion.stopAt(7);
					Grid.bigExploding = true; 
					Grid.bigExplosionx = x + i*Grid.tileSize + Grid.tileSize/2 - Grid.tileSize;
					Grid.bigExplosiony = y + j*Grid.tileSize + Grid.tileSize/2 - Grid.tileSize;
					Grid.tiles[i][j].imminentExplosion = false;
					for(int k = 0; k != explosions.length; k++) {
						explosions[k].stop();
					}
					explosions[gen.nextInt(3)].playAsSoundEffect(.8f, 1f, false);
					return;
				}
			}
		}
	}
	
	private static void nodeDamaged(int x, int y) {
		if(tiles[x][y].node == Tile.STASIS_OCCUPIED) {
			for(Crew c : crew) {
				if(c != null) {
					if(c.tilex == x && c.tiley == y) {
						c.dead = true;
					}
				}
			}
		}
		
		tiles[x][y].convertNodeToBroken();
		tiles[x][y].nodeStatus = Tile.WARNING;
		
		
		//Grid.hackUpdate();
	}
	
	private void initialiseTiles() {
		for(int i = 0; i != Grid.columns; i++) {
			for(int j = 0; j != Grid.rows; j++) {
				Grid.tiles[i][j] = new Tile(Tile.EMPTY, -1, -1, 8);
				Grid.tilesInt[i][j] = 0;
			}
		}
	}
	
	public void hoverTile(int newx, int newy, boolean playMode) {
		int adjustX = (int)(newx); adjustX = (int)(adjustX-Grid.x);
		int adjustY = (int)(newy); adjustY = (int)(adjustY-Grid.y);
		adjustX /= Grid.tileSize; adjustY /= Grid.tileSize;
		if(adjustX >= 0 && adjustX < columns && adjustY >= 0 && adjustY < rows) {
			if(playMode) {
				this.playFocus = new Point(adjustX, adjustY);
			} else {
				Grid.highFocus = new Point(adjustX, adjustY);
			}
		}
		
	}
	
	public static Graphics highlight(Graphics g) {
		g.setLineWidth(2);
		
		int radius = 7;
		for(int i = -radius; i != radius+1; i++) {
			for(int j = -radius; j != radius+1; j++) {
				if((highFocus.getX() + i) >= 0 && (highFocus.getX() + i) < Grid.columns) {
					if((highFocus.getY() + j) >= 0 && (highFocus.getY() + j) < Grid.rows) {
						Point homeTile = new Point(Grid.x + highFocus.getX()*Grid.tileSize + Grid.tileSize/2,
											(Grid.y + highFocus.getY()*Grid.tileSize + Grid.tileSize/2));
						Point targetTile = new Point(Grid.x + (highFocus.getX()+i)*Grid.tileSize + Grid.tileSize/2,
								(Grid.y + (highFocus.getY()+j)*Grid.tileSize + Grid.tileSize/2));
						
						float distance = (float)Math.sqrt(Math.pow(homeTile.getX() - targetTile.getX(), 2) + 
											Math.pow(homeTile.getY() - targetTile.getY(), 2))/tileSize;
						
						float alpha = (float)((-(distance)/5 + 1)*Math.abs(Math.sin(sineThrob)));
						
						if(Grid.tiles[(int)highFocus.getX() + i][(int)highFocus.getY() + j].floor == Tile.WALL_0 ||
							Grid.tiles[(int)highFocus.getX() + i][(int)highFocus.getY() + j].floor == Tile.EMPTY) {
							g.setColor(new Color(.9f, .9f, .9f, alpha));
							
						} else {
							g.setColor(new Color(.1f, .1f, .1f, alpha));
						}
						
						if(Grid.tiles[(int)highFocus.getX() + i][(int)highFocus.getY() + j].node != -1) {
							g.setColor(new Color(1f, .6f, .6f, alpha));
						}
						
						g.drawRect((int)((Grid.x + (i+highFocus.getX()) * Grid.tileSize+1)), 
							       (int)((Grid.y + (j+highFocus.getY()) * Grid.tileSize+1)), 
							       (int)(Grid.tileSize)-2, (int)(tileSize)-2);
					}
				}
			}
		}
		return g;
	}
	
	private void playlight(Graphics g) {
		Color darkHighlight;
		Color brightHighlight;
		Color specHighlight;
		if(Grid.hackView) {
			darkHighlight = new Color(0, 50, 0);
			brightHighlight = new Color(100, 250, 100);
			specHighlight = new Color(200, 200, 100);
		} else {
			darkHighlight = new Color(70, 70, 70);
			brightHighlight = new Color(250, 250, 250);
			specHighlight = new Color(250, 150, 150);
		}
		
		if(Grid.tiles[(int)playFocus.getX()][(int)playFocus.getY()].floor == Tile.WALL_0 ||
				Grid.tiles[(int)playFocus.getX()][(int)playFocus.getY()].floor == Tile.EMPTY) {
			g.setColor(brightHighlight);
		} else {
			g.setColor(darkHighlight);
		}
		
		if(Grid.tiles[(int)playFocus.getX()][(int)playFocus.getY()].node != -1) {
			g.setColor(specHighlight);
		}
		
		g.setLineWidth(2);
		g.drawRect((int)(playFocus.getX()*Grid.tileSize + Grid.x), 
			       (int)(playFocus.getY()*Grid.tileSize + Grid.y), 
			       (int)(Grid.tileSize), (int)(Grid.tileSize));
	}

	public void paint(int newx, int newy, int paint, int style) {
		int adjustX = (int)((newx - Grid.x)/Grid.tileSize);
		int adjustY = (int)((newy - Grid.y)/Grid.tileSize);
		
		if(paint == Tile.FIRE) {
			Grid.tiles[adjustX][adjustY].fire = !Grid.tiles[adjustX][adjustY].fire;
		} else if(paint == Tile.ERASER) {
			Grid.tiles[adjustX][adjustY].node = -1;
			Grid.tiles[adjustX][adjustY].floor = Tile.EMPTY;
			Grid.tiles[adjustX][adjustY].weapon = null;
		} else if(paint != Crew.HUMAN && paint != Crew.ROBOT) {
			if(paint >= Tile.RAILGUN_0_N && paint <= Tile.LASER_0_W) {
				int type = -1;
				int ori = -1;
				if(paint >= Tile.RAILGUN_0_N && paint <= Tile.RAILGUN_0_W) {
					type = Weapon.RAILGUN_0;
					switch(paint % 100) {
					case 0: ori = 0; break;
					case 2: ori = 1; break;
					case 4: ori = 2; break;
					case 6: ori = 3; break;
					}
				} else if(paint >= Tile.LASER_0_N && paint <= Tile.LASER_0_W) {
					type = Weapon.LASER_0;
					switch(paint % 100) {
					case 8: ori = 0; break;
					case 10: ori = 1; break;
					case 12: ori = 2; break;
					case 14: ori = 3; break;
					}
				}

				Grid.tiles[adjustX][adjustY].weapon = new Weapon(adjustX, adjustY, type, ori);
				
			} else {
				Grid.tiles[adjustX][adjustY].weapon = null;
			}
				
			if(style == 0) {
				if(Tile.isPaintFloor(paint)) {
					Grid.tiles[adjustX][adjustY].floor = paint;
				} else {
					Grid.tiles[adjustX][adjustY].node = paint;
				}
				this.fillInt();
			} else if(style == 1) {
				if(Tile.isPaintFloor(paint)) {
					fillIter = 0;
					this.fillFloor(new Point(adjustX, adjustY), Grid.tiles[adjustX][adjustY].floor, paint);
					this.fillInt();
				}
			}
		} else if(paint == Crew.HUMAN || paint == Crew.ROBOT) { 
			for(Crew c: crew) {
				if(c == null) {
					if(Tile.isTilePathable(Grid.tiles[adjustX][adjustY], adjustX, adjustY)) {
						boolean unoccupied = true;
						for(Crew cc : crew) {
							if(cc != null) {
								if(cc.tilex == adjustX && cc.tiley == adjustY) {
									unoccupied = false;
								}
							}
						}
						if(unoccupied) {
							double ax = Grid.x + adjustX*Grid.tileSize;
							double ay = Grid.x + adjustY*Grid.tileSize;
							try {
								crew.add(new Crew(adjustX, adjustY, ax, ay, paint));
							} catch (SlickException e) {}
						}
						break;
				
					}
				}
			}
		}
		
		Tile.gridify();
	}
	
	public static void pathify() {
		Grid.tilesInt = new int[Grid.columns][Grid.rows];
		
		for(int i = 0; i != Grid.columns; i++) {
			for(int j = 0; j != Grid.rows; j++) {
				if(Tile.isTilePathable(Grid.tiles[i][j], i, j)) {
					Grid.tilesInt[i][j] = 0;
				} else {
					Grid.tilesInt[i][j] = 1;
				}
			}
		}
		
	}
	
	public void fillFloor(Point node, int target, int replacement) {
		if(fillIter > 10000) {
			return;
		} else {
			fillIter++;
		}
		
		if(target == replacement) {
		  return;
		}
		if(Grid.tiles[(int)node.getX()][(int)node.getY()].floor != target) {
		  return;
		}
		Grid.tiles[(int)node.getX()][(int)node.getY()].floor = replacement;
		if(Grid.tiles.length > node.getX() + 1) {
			fillFloor(new Point(node.getX() + 1, node.getY()), target, replacement);
		}
		if(node.getX() - 1 >= 0) {
			fillFloor(new Point(node.getX() - 1, node.getY()), target, replacement);
		}
		if(Grid.tiles[0].length > node.getY() + 1) {
			fillFloor(new Point(node.getX(), node.getY() + 1), target, replacement);
		}
		if(node.getY() - 1 >= 0) {
			fillFloor(new Point(node.getX(), node.getY() - 1), target, replacement);
		}
	}
	
	public void fillInt() {
		for(int i = 0; i != columns; i++) {
			for(int j = 0; j != rows; j++) {
				if(Tile.isTilePathable(Grid.tiles[i][j], i, j)) {
					Grid.tilesInt[i][j] = 0;
				} else {
					Grid.tilesInt[i][j] = 1;
				}
			}
		}
	}
	
	public void select(int newx, int newy, Input input) {
		if(!hackView) {
			int adjustX = (int)((newx - Grid.x)/Grid.tileSize);
			int adjustY = (int)((newy - Grid.y)/Grid.tileSize);
			
			if(!input.isKeyDown(Input.KEY_LCONTROL)) {
				for(int i = 0; i != crewSelect.length; i++) {
					crewSelect[i] = -1;
				}
			}
			for(int i = 0; i != crew.size(); i++) {
				if(crew.get(i) != null) {
					if(crew.get(i).tilex == adjustX && crew.get(i).tiley == adjustY) {
						for(int j = 0; j != crewSelect.length; j++) {
							if(crewSelect[j] == -1) {
								crewSelect[j] = i;
								for(int k = 0; k != actioned.length; k++) {
									actioned[k].stop();
								}
	
								for(int k = 0; k != selected.length; k++) {
									if(selected[k].isPlaying())
									selected[k].stop();
								}
								
								selected[gen.nextInt(selected.length)].playAsSoundEffect(1f, 1f, false);
								break;
							}
						}
					}
				}
			}
			switch(tiles[adjustX][adjustY].node) {
			case Tile.VC_DOOR: tiles[adjustX][adjustY].node = Tile.VO_DOOR; 
								doorClose.stop(); doorOpen.stop();
							   doorOpen.playAsSoundEffect(1.7f, 1.5f, false); break;
			case Tile.VO_DOOR: tiles[adjustX][adjustY].node = Tile.VC_DOOR;
								doorClose.stop(); doorOpen.stop();
							   doorClose.playAsSoundEffect(1.7f, 1.5f, false); break;
			case Tile.HC_DOOR: tiles[adjustX][adjustY].node = Tile.HO_DOOR;
								doorClose.stop(); doorOpen.stop();
							   doorOpen.playAsSoundEffect(1.7f, 1.5f, false); break;
			case Tile.HO_DOOR: tiles[adjustX][adjustY].node = Tile.HC_DOOR;
								doorClose.stop(); doorOpen.stop();
							   doorClose.playAsSoundEffect(1.7f, 1.5f, false); break;
			}
		}
	}
	
	public void boxCreate(int startx, int starty, int newx, int newy, int oldx, int oldy) {
		int width = 0;
		int height = 0;
		
		if(newx > startx) width = newx-startx;
		else width = startx - newx;
		
		if(newy > starty) height = newy-starty;
		else height = starty - newy;
		
		if(newx > startx && newy > starty) {
			this.selectBox = new Rectangle(startx, starty, width, height);
		} else if(newx > startx && newy < starty) {
			this.selectBox = new Rectangle(startx, starty-height, width, height);
		} else if(newx < startx && newy > starty) {
			this.selectBox = new Rectangle(startx-width, starty, width, height);
		} else if(newx < startx && newy < starty) {
			this.selectBox = new Rectangle(startx-width, starty-height, width, height);
		}
	}
	
	public void fireBoxSelect() {
		if(selectBox != null) {
			for(int i = 0; i != crew.size(); i++) {
				if(crew.get(i) != null) {
					if(selectBox.contains((float)crew.get(i).locx, (float)crew.get(i).locy)) {
						for(int j = 0; j != crewSelect.length; j++) {
							if(crewSelect[j] == -1) {
								crewSelect[j] = i;
								for(int k = 0; k != actioned.length; k++) {
									actioned[k].stop();
								}

								for(int k = 0; k != selected.length; k++) {
									if(selected[k].isPlaying())
									selected[k].stop();
								}
								
								selected[gen.nextInt(selected.length)].playAsSoundEffect(1f, 1f, false);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	public void destinate(int newx, int newy, Input input) {
		int adjustX = (int)((newx - Grid.x)/Grid.tileSize);
		int adjustY = (int)((newy - Grid.y)/Grid.tileSize);
		
		boolean crewSelected = false;
		if(Tile.isTilePathable(tiles[adjustX][adjustY], adjustX, adjustY)) {
			for(int i = 0; i != crewSelect.length; i++) {
				if(crewSelect[i] != -1) {
					crewSelected = true;
					crew.get(crewSelect[i]).goalx = adjustX;
					crew.get(crewSelect[i]).goaly = adjustY;
					crew.get(crewSelect[i]).calculatingPath = true;
					for(int j = 0; j != crew.get(crewSelect[i]).co2.length; j++) {
						crew.get(crewSelect[i]).co2[j] = null;
					}
					
					for(int j = 0; j != actioned.length; j++) {
						actioned[j].stop();
					}

					for(int j = 0; j != selected.length; j++) {
						if(selected[j].isPlaying())
						selected[j].stop();
					}
					
					actioned[gen.nextInt(actioned.length)].playAsSoundEffect(1f, 1f, false);
					
					crew.get(crewSelect[i]).repairx = -1; crew.get(crewSelect[i]).repairy = -1;
					crew.get(crewSelect[i]).repairing = false;
					
				}
			}
		} else if(tiles[adjustX][adjustY].isNodeBroken()) {
			this.destinateToRepair(adjustX, adjustY);
		}
		
		if(!crewSelected && hackView) {
			autoHack(adjustX, adjustY);
		}
	}
	
	public static void autoHack(int x, int y) {
		if(Tile.isTileRemoteNode(Grid.tiles[x][y])) {
			if(Grid.tiles[x][y].nodeStatus == Tile.OFFLINE) {
				double shortestDistance = 10000000;
				int savex = -1; int savey = -1;
				for(int i = 0; i != columns; i++) {
					for(int j = 0; j != rows; j++) {
						if(Tile.isTileTerminal(Grid.tiles[i][j])) {
							if(Grid.tiles[i][j].uplink == null) {
								if(Math.sqrt(Math.pow((Grid.x + x*tileSize)-(Grid.x + i*tileSize), 2) +
										Math.pow((Grid.y + y*tileSize)-(Grid.y + j*tileSize), 2)) < shortestDistance) {
									savex = i; savey = j;
									shortestDistance = Math.sqrt(Math.pow((Grid.x + x*tileSize)-(Grid.x + i*tileSize), 2) +
											Math.pow((Grid.y + y*tileSize)-(Grid.y + j*tileSize), 2));
								}
							}
						}
					}
				}
				
				if(savex != -1) {
					tiles[savex][savey].uplink = new Intpo(x, y);
					tiles[x][y].nodeStatus = Tile.CONNECTED;
					zap[gen.nextInt(zap.length)].playAsSoundEffect(1f, .1f, false);
					
				}
			}
		}
	}
	
	public static void fullAutoHack() {
		for(int i = 0; i != columns; i++) {
			for(int j = 0; j != rows; j++) {
				autoHack(i, j);
			}
		}
	}
	
	public void destinateToRepair(int adjustX, int adjustY) {
		Point[] repairSpaces = new Point[4];
		if(adjustX - 1 >= 0) {
			if(Tile.isTilePathable(tiles[adjustX-1][adjustY], adjustX-1, adjustY) 
					&& Tile.isTileCrewed(adjustX-1, adjustY, crew) != 1) {
				repairSpaces[0] = new Point(adjustX-1, adjustY);
			}
		}
		if(adjustX + 1 < columns) {
			if(Tile.isTilePathable(tiles[adjustX+1][adjustY], adjustX+1, adjustY) 
					&& Tile.isTileCrewed(adjustX+1, adjustY, crew) != 1) {
				repairSpaces[1] = new Point(adjustX+1, adjustY);
			}
		}
		if(adjustY - 1 >= 0) {
			if(Tile.isTilePathable(tiles[adjustX][adjustY-1], adjustX, adjustY-1) 
					&& Tile.isTileCrewed(adjustX, adjustY-1, crew) != 1) {
				repairSpaces[2] = new Point(adjustX, adjustY-1);
			}
		}
		if(adjustY + 1 < rows) {
			if(Tile.isTilePathable(tiles[adjustX][adjustY+1], adjustX, adjustY+1) 
					&& Tile.isTileCrewed(adjustX, adjustY+1, crew) != 1) {
				repairSpaces[3] = new Point(adjustX, adjustY+1);
			}
		}
				
		for(int i = 0; i != crewSelect.length; i++) {
			if(crewSelect[i] != -1) {
				for(int j = 0; j != repairSpaces.length; j++) {
					if(repairSpaces[j] != null) {
						crew.get(crewSelect[i]).goalx = (int)repairSpaces[j].getX();
						crew.get(crewSelect[i]).goaly = (int)repairSpaces[j].getY();
						crew.get(crewSelect[i]).calculatingPath = true;
						
						for(int k = 0; k != crew.get(crewSelect[i]).co2.length; k++) {
							crew.get(crewSelect[i]).co2[k] = null;
						}
						
						for(int k = 0; k != actioned.length; k++) {
							actioned[k].stop();
						}
	
						for(int k = 0; k != selected.length; k++) {
							if(selected[k].isPlaying())
							selected[k].stop();
						}
						
						actioned[gen.nextInt(actioned.length)].playAsSoundEffect(1f, 1f, false);
						
						crew.get(crewSelect[i]).repairx = adjustX; crew.get(crewSelect[i]).repairy = adjustY;
						
						repairSpaces[j] = null;
						break;
					}
				}
			}
		}
	}
	
	public void hackInteract(Input input) {
		
		if(input.isMouseButtonDown(0)) {
			if(hackSelectX == -1) {
				int adjustX = (int)((input.getMouseX() - Grid.x)/Grid.tileSize);
				int adjustY = (int)((input.getMouseY() - Grid.y)/Grid.tileSize);
				if(Tile.isTileTerminal(tiles[adjustX][adjustY])) {
					this.hackSelectX = adjustX;
					this.hackSelectY = adjustY;
				}
			}
			
			this.hackMouseX = input.getMouseX();
			this.hackMouseY = input.getMouseY();
			
			this.hackDestX = (int)((input.getMouseX() - Grid.x)/Grid.tileSize);
			this.hackDestY = (int)((input.getMouseY() - Grid.y)/Grid.tileSize);
			
		} else {
			if(this.hackSelectX != -1) {
				if(hackSelectX < columns && hackSelectY < rows) {
					if(Tile.isTileRemoteNode(tiles[this.hackDestX][this.hackDestY])) {
						if(tiles[this.hackDestX][this.hackDestY].nodeStatus == Tile.OFFLINE) {
							tiles[this.hackSelectX][this.hackSelectY].uplink = new Intpo(this.hackDestX, this.hackDestY);
							tiles[this.hackDestX][this.hackDestY].nodeStatus = Tile.CONNECTED;
							zap[gen.nextInt(zap.length)].playAsSoundEffect(1f, .1f, false);
						}
					}
				}
			}
			
			this.hackSelectX = -1; this.hackSelectY = -1;
		}
	}
	
	public void zoomChange(int zoom, int mx, int my){
		int adjustX = (int)((mx - Grid.x)/Grid.tileSize);
		int adjustY = (int)((my - Grid.y)/Grid.tileSize);
		
		try {
		switch(zoom) {
			case 1: Grid.tileSize = 4;
					this.tileSheet = new SpriteSheet(new org.newdawn.slick.Image("res//tileSheet4.png"),
							Grid.tileSize, Grid.tileSize);
					this.crewSheet = new SpriteSheet(new org.newdawn.slick.Image("res//crewSheet4.png"),
							Grid.tileSize, Grid.tileSize); 
					this.crewSheet = new SpriteSheet(new org.newdawn.slick.Image("res//effectSheet8.png"),
							Grid.tileSize*2, Grid.tileSize*2); 
					break;
			case 2: Grid.tileSize = 8;
					this.tileSheet = new SpriteSheet(new org.newdawn.slick.Image("res//tilesheet8.png"),
							Grid.tileSize, Grid.tileSize);
					this.crewSheet = new SpriteSheet(new org.newdawn.slick.Image("res//crewSheet8.png"),
							Grid.tileSize, Grid.tileSize); 
					this.crewSheet = new SpriteSheet(new org.newdawn.slick.Image("res//effectSheet16.png"),
							Grid.tileSize*2, Grid.tileSize*2); 
					break;
			case 3: Grid.tileSize = 16;
					this.tileSheet = new SpriteSheet(new org.newdawn.slick.Image("res//tileSheet16.png"),
							Grid.tileSize, Grid.tileSize);
					this.crewSheet = new SpriteSheet(new org.newdawn.slick.Image("res//crewSheet16.png"),
							Grid.tileSize, Grid.tileSize);
					this.crewSheet = new SpriteSheet(new org.newdawn.slick.Image("res//effectSheet32.png"),
							Grid.tileSize*2, Grid.tileSize*2); 
					break;
		}
		} catch(Exception ex) {}

		this.loadAnimations();
		
		Grid.width = Grid.columns*Grid.tileSize; Grid.height = Grid.rows*Grid.tileSize;
		
		int tileXpos = (int)(Grid.x + adjustX*Grid.tileSize);
		int tileYpos = (int)(Grid.y + adjustY*Grid.tileSize);
		int xshifter = mx - tileXpos; int yshifter = my - tileYpos;
		Grid.x += xshifter; Grid.x -= Grid.tileSize/2;
		Grid.y += yshifter; Grid.y -= Grid.tileSize/2;
		this.relocateCrew();
	}
	
	public void relocateCrew() {
		for(Crew c : Grid.crew) {
			if(c != null) {
				c.locx = Grid.x + c.tilex*Grid.tileSize;
				c.locy = Grid.y + c.tiley*Grid.tileSize;
			}
		}
	}
	
	public void loadAnimations() {
		this.walkUp = new Animation(crewSheet, 0, 1, 7, 1, true, 50, false);
		this.walkDown = new Animation(crewSheet, 0, 2, 7, 2, true, 50, false);
		this.walkLeft = new Animation(crewSheet, 0, 3, 7, 3, true, 50, false);
		this.walkRight = new Animation(crewSheet, 0, 4, 7, 4, true, 50, false);
		
		this.typeUp = new Animation(crewSheet, 0, 5, 3, 5, true, 100, false);
		this.typeDown = new Animation(crewSheet, 4, 5, 7, 5, true, 100, false);
		this.typeLeft = new Animation(crewSheet, 0, 6, 3, 6, true, 100, false);
		this.typeRight = new Animation(crewSheet, 4, 6, 7, 6, true, 100, false);
		
		this.extinguishUp = new Animation(crewSheet, 0, 8, 3, 8, true, 100, false);
		this.extinguishDown = new Animation(crewSheet, 4, 8, 7, 8, true, 100, false);
		this.extinguishLeft = new Animation(crewSheet, 0, 9, 3, 9, true, 100, false);
		this.extinguishRight = new Animation(crewSheet, 4, 9, 7, 9, true, 100, false);
		
		this.repairUp = new Animation(crewSheet, 0, 11, 3, 11, true, 100, false);
		this.repairDown = new Animation(crewSheet, 4, 11, 7, 11, true, 100, false);
		this.repairLeft = new Animation(crewSheet, 0, 12, 3, 12, true, 100, false);
		this.repairRight = new Animation(crewSheet, 4, 12, 7, 12, true, 100, false);
		
		this.roboWalkUp = new Animation(crewSheet, 8, 1, 15, 1, true, 100, false);
		this.roboWalkDown = new Animation(crewSheet, 8, 2, 15, 2, true, 100, false);
		this.roboWalkLeft = new Animation(crewSheet, 8, 3, 15, 3, true, 100, false);
		this.roboWalkRight = new Animation(crewSheet, 8, 4, 15, 4, true, 100, false);
		
		this.roboExtinguishUp = new Animation(crewSheet, 8, 5, 11, 5, true, 80, false);
		this.roboExtinguishDown = new Animation(crewSheet, 12, 5, 15, 5, true, 80, false);
		this.roboExtinguishLeft = new Animation(crewSheet, 8, 6, 11, 6, true, 80, false);
		this.roboExtinguishRight = new Animation(crewSheet, 12, 6, 15, 6, true, 80, false);
		
		this.roboRepairUp = new Animation(crewSheet, 8, 7, 11, 7, true, 50, false);
		this.roboRepairDown = new Animation(crewSheet, 12, 7, 15, 7, true, 50, false);
		this.roboRepairLeft = new Animation(crewSheet, 8, 8, 11, 8, true, 50, false);
		this.roboRepairRight = new Animation(crewSheet, 12, 8, 15, 8, true, 50, false);
		
		this.fire0 = new Animation(tileSheet, 0, 6, 15, 6, true, 60, false);
		this.fire1 = new Animation(tileSheet, 0, 6, 15, 6, true, 61, false);
		this.fire2 = new Animation(tileSheet, 0, 6, 15, 6, true, 62, false);
		this.fire3 = new Animation(tileSheet, 0, 6, 15, 6, true, 63, false);
		this.fire4 = new Animation(tileSheet, 0, 6, 15, 6, true, 64, false);
		
		this.charging = new Animation(tileSheet, 11, 2, 14, 2, true, 150, false);
		
		Grid.bigExplosion = new Animation(effectSheet, 0, 0, 7, 0, true, 20, false);
		Grid.bigExplosion.stopAt(7);
	}

	public void relocateProjectiles(int oldx, int oldy, int newx, int newy) {
		for(Projectile p : Grid.projectiles) {
			if(p != null) {
				p.locx = p.locx + newx-oldx;
				p.locy = p.locy + newy-oldy;
			}
		}
	}

	public void relocateExplosions() {
		
	}

	public static void editUpdate(int delta, Input input) {
		bigInput = input;
		
		Grid.sineThrob += .001*delta;
		
		for(int i = 0; i != columns; i++) {
			for(int j = 0; j != rows; j++) {
				if(tiles[i][j].node == Tile.SOLAR && !Tile.isTileGirder(tiles[i][j])) {
					tiles[i][j].node = -1;
				}
				
				if(tiles[i][j].node != -1 && tiles[i][j].floor != Tile.PATH_N) {
					if(!Tile.isTileWeapon(tiles[i][j]) && !Tile.isTileGirder(tiles[i][j])) {
						tiles[i][j].node = -1;
					}
				}
				
				if(Tile.isTileWeapon(tiles[i][j])) {
					if(Tile.isTileRailgun(tiles[i][j])) {
						if(!Tile.isRailgunValid(tiles[i][j], i, j)) {
							tiles[i][j].node = -1;
							tiles[i][j].weapon = null;
						}
					} else if(Tile.isTileLaser(tiles[i][j])) {
						if(!Tile.isLaserValid(tiles[i][j], i, j)) {
							tiles[i][j].node = -1;
							tiles[i][j].weapon = null;
						}
					}
				}
				
				
			}
		}
	}

	public static void hackHover(int newx, int newy) {
		if(hackView) {
			int adjustX = (int)((newx - Grid.x)/Grid.tileSize);
			int adjustY = (int)((newy - Grid.y)/Grid.tileSize);
			
			if(tiles[adjustX][adjustY].uplink != null) {
				uplinkFocus = new Intpo(adjustX, adjustY);
			} else if(tiles[adjustX][adjustY].nodeStatus != Tile.OFFLINE) {
				for(int i = 0; i != columns; i++) {
					for(int j = 0; j != rows; j++) {
						if(tiles[i][j].uplink != null) {
							if(tiles[i][j].uplink.x == adjustX && tiles[i][j].uplink.y == adjustY) {
								uplinkFocus = new Intpo(i, j);
							}
						}
					}
				}
			} else {
				uplinkFocus = null;
			}
		} else {
			uplinkFocus = null;
		}
		
	}
}
