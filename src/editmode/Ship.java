package editmode;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import Mains.EditMode;
import Mains.Intpo;
import Mains.PlayMode;

public class Ship {

	public static Grid grid;
	public static Image img;
	
	public static double locx, locy, slocx, slocy, velx, vely, angle,
				maxaccel, maxorbitaccel, maxspeed, maxorbitspeed;
				  
	public static double targx, targy;
	
	public static Rectangle bounds;
	public static Circle radius, largeRadius, tractorRadius;
	
	public static double mineStrength;
	
	public static int planetLocked;
	
	public static int engines;
	public static int shieldGenerators;
	public static int activeShieldGenerators;
	
	public static double shields;
	public static double shieldsMax;
	public static Circle shieldBubble;
	public static int shieldsPercent;
	public static int shieldsHit;
	public static int shieldsHitRecovered;
	public static int shieldsDown;
	
	static boolean test;
	
	public static double powerTheoretical;
	public static double powerTotal;
	public static double powerRemaining;
	
	public static int[] targets;
	public static double[] targetAngles;
	
	public static int tractors;
	public static int activeTractors;
	
	public Ship(Point offset, int c, int r) throws SlickException {
		Ship.grid = new Grid(c, r, 16, EditMode.buttonsClip.getWidth()+50, 50);
		
		Ship.locx = offset.getX();
		Ship.locy = offset.getY();
		Ship.velx = 0;
		Ship.vely = 0;
		Ship.angle = 0;
		
		engines = 0;
		engineConfig();
		
		tractors = 0;
		activeTractors = 0;
		
		Ship.tractorRadius = null;
		 
		shieldGenerators = 0;
		shieldsMax = 100;
		shields = 0;
		shieldsHitRecovered = 100;
		
		Ship.mineStrength = .01;
		Ship.planetLocked = -1;
		
		Ship.targx = -1;
		Ship.targy = -1;
		
		test = false;
		
		targets = new int[10];
		for(int i = 0; i != targets.length; i++) {
			targets[i] = -1;
		}
		targetAngles = new double[targets.length];
		for(int i = 0; i != targetAngles.length; i++) {
			targetAngles[i] = -1;
		}
	}

	public Graphics editDraw(Graphics g, boolean editDraw, Input input) {
		grid.draw(g, editDraw, input);

		return g;
	}
	
	public Graphics playDraw(Graphics g) {
		
		if(img != null) {
			g.drawImage(img, (float)locx-img.getWidth()/2, (float)locy-img.getHeight()/2);
		}
		
		if(shieldsPercent > 10) {
			g.setColor(new Color(100, 100, 250));
			float shieldWidth = shieldsPercent/20f;
			g.setLineWidth(shieldWidth);
			g.draw(shieldBubble);
		}
		
		return g;
	}
	
	public static void playUpdate(int delta) {
		shieldUpdate(delta);
		tractorUpdate(delta);
		
		for(int i = 0; i != targets.length; i++) {
			if(targets[i] != -1) {
				double bx = 0;
				double by = 0;
				
				double tempa = 0;
				
				if(targets[i] >= 10000) {
					bx = PlayMode.systems[PlayMode.systemFocus].system.attackBots[targets[i]-10000].locx;
					by = PlayMode.systems[PlayMode.systemFocus].system.attackBots[targets[i]-10000].locy;
				} else {
					bx = PlayMode.systems[PlayMode.systemFocus].system.bots[targets[i]].locx;
					by = PlayMode.systems[PlayMode.systemFocus].system.bots[targets[i]].locy;
				}
					
				double targAngle = (float)(180/Math.PI)*(float)Math.asin((Math.abs(Ship.locx-bx))/
							 (Math.sqrt((bx-Ship.locx)*(bx-Ship.locx) + 
							 (by-Ship.locy)*(by-Ship.locy))));
				
				if(Double.isNaN(targAngle)) {
					targAngle = tempa;
				}
			
				if(bx > Ship.locx) {
					if(by > Ship.locy) {
						targAngle = 90 + (90 - targAngle);
					} else if(by > Ship.locy) {
						//
					} 
				} else if(by > Ship.locy) {
					targAngle += 180;
				} else {
					targAngle = 270 + (90 - targAngle);
				}
				
				targAngle -= Ship.angle;
				
				if(targAngle < 0) {
					targAngle = 360 + targAngle;
				}
				
				targetAngles[i] = targAngle;
			}
		}
	}
	
	public static void tractorUpdate(int delta) {
		Ship.tractorRadius = new Circle((float)Ship.locx, (float)Ship.locy, activeTractors*5);
	}
	
	public static void shieldUpdate(int delta) {
		if(shieldsMax == 0) {
			shields = 0;
		}
		
		if(activeShieldGenerators == 0) {
			shields -= .05*delta;;
		} else {
			if(shieldsHit > shieldsHitRecovered) {
				shields += 0.002*Math.pow(activeShieldGenerators, 1.5)*delta;
			} else {
				shieldsHit += 1*delta;
			}
		}
		
		if(shields > shieldsMax) {
			shields = shieldsMax;
		}
		
		if(shields < 0) {
			shields = 0;
		}
		
		if(shields > (shieldsMax-.02) && shields < (shieldsMax+.02)) {
			shields = shieldsMax;
		}
		
		if(shields > (0-1) && shields < (0+0.0001)) {
			shields = 0;
		}
		
		if(shieldsMax != 0) {
			shieldsPercent = (int)((shields/shieldsMax)*100);
		} else {
			shieldsPercent = 0;
		}
	}
	
	public void path() {
		for(int i = 0; i != Grid.crew.size(); i++) {
			if(Grid.crew.get(i) != null) {
				if(Grid.crew.get(i).calculatingPath) {

					Path p = Grid.crew.get(i).path(Grid.crew.get(i).tilex, Grid.crew.get(i).tiley, 
							Grid.crew.get(i).goalx, Grid.crew.get(i).goaly, 100000, Grid.tilesInt);
					
					
					

					if(p != null) {
						Path c = new Path();
						c.add(new Intpo(Grid.crew.get(i).tilex, Grid.crew.get(i).tiley));
						for(int j = 0; j != p.steps.size(); j++) {
							c.add(p.steps.get(j));
						}
						Grid.crew.get(i).currentPath = c;
						Grid.crew.get(i).pathing = true;
					}
					
					Grid.crew.get(i).calculatingPath = false;
					if(Grid.tiles[Grid.crew.get(i).tilex][Grid.crew.get(i).tiley].node == Tile.STASIS_OCCUPIED) {
						Grid.tiles[Grid.crew.get(i).tilex][Grid.crew.get(i).tiley].node = Tile.STASIS_VACANT;
					}
					Grid.crew.get(i).inStasis = false;
				}
			}
		}
	}
	
	public void rotate(int mx, int my) {
		double centerx = Ship.locx;
		double centery = Ship.locy;
		
		double tempa = Ship.angle;
		
		Ship.angle = (float)(180/Math.PI) * (float)Math.asin((Math.abs(centerx-mx))/
				(Math.sqrt((mx-centerx)*(mx-centerx) + (my-centery)*(my-centery))));
		
		if(Double.isNaN(Ship.angle)) {
			Ship.angle = tempa;
		}
	
		if(mx > centerx) {
			if(my > centery) {
				angle = 90 + (90 - angle);
			} else if(my > centery) {
				//
			} 
		} else if(my > centery) {
			angle += 180;
		} else {
			angle = 270 + (90 - angle);
		}
		
		Ship.img.setRotation((float)angle);
	}
	
	public void rotateToPlanet() {
		double tempa = Ship.angle;
		
		Ship.angle = (float)(180/Math.PI) * (float)Math.asin((Math.abs(Ship.locx-Ship.targx))/
					 (Math.sqrt((Ship.targx-Ship.locx)*(targx-Ship.locx) + 
					 (Ship.targy-Ship.locy)*(Ship.targy-Ship.locy))));
		
		if(Double.isNaN(Ship.angle)) {
			Ship.angle = tempa;
		}
	
		if(Ship.targx > Ship.locx) {
			if(Ship.targy > Ship.locy) {
				Ship.angle = 90 + (90 - Ship.angle);
			} else if(targy > Ship.locy) {
				//
			} 
		} else if(Ship.targy > Ship.locy) {
			Ship.angle += 180;
		} else {
			Ship.angle = 270 + (90 - Ship.angle);
		}
	}
	
	public void accelerate(char wasd, int delta) {
		if(PlayMode.fuel > 0){   
			
			double tangle = angle;
			while(tangle > 90) {
				tangle = tangle - 90;
			}
			if(wasd == 's') {
				if(velx < .01d && velx > -.01d) velx = 0.000000d;
				if(vely < .01d && vely > -.01d) vely = 0.000000d;
				if(velx > 0.000000d) velx -= maxaccel*delta;
					else if(velx < 0) velx += maxaccel*delta;
					else velx = 0;
				if(vely > 0.000000d) vely -= maxaccel*delta;
					else if(vely < 0) vely += maxaccel*delta;
					else vely = 0;
			} else if(wasd == 't') {
				if(velx > 0.000000d) velx -= (maxaccel/5)*delta;
					else if(velx < 0.00000d) velx += (maxaccel/5)*delta;
					else velx = 0;
				if(vely > 0.00000d) vely -= (maxaccel/5)*delta;
					else if(vely < 0.00000d) vely += (maxaccel/5)*delta;
					else vely = 0;
			} else if(wasd == 'w') {
				if(angle > 0 && angle < 90) {
					vely -= Math.sin((90-tangle)*(Math.PI/180))*maxaccel*delta;
					velx += Math.sin((tangle)*(Math.PI/180))*maxaccel*delta;
				} else if(angle > 90 && angle < 180) {
					vely += Math.sin(tangle*(Math.PI/180))*maxaccel*delta;
					velx += Math.sin((90-tangle)*(Math.PI/180))*maxaccel*delta;
				} else if(angle > 180 && angle < 270) {
					vely += Math.sin((90-tangle)*(Math.PI/180))*maxaccel*delta;
					velx -= Math.sin(tangle*(Math.PI/180))*maxaccel*delta;
				} else {
					vely -= Math.sin(tangle*(Math.PI/180))*maxaccel*delta;
					velx -= Math.sin((90-tangle)*(Math.PI/180))*maxaccel*delta;
				}
			}  else if(wasd == 'o') {
				if(angle > 0 && angle < 90) {
					vely -= Math.sin((90-tangle)*(Math.PI/180))*maxorbitaccel*delta;
					velx += Math.sin((tangle)*(Math.PI/180))*maxorbitaccel*delta;
				} else if(angle > 90 && angle < 180) {
					vely += Math.sin(tangle*(Math.PI/180))*maxorbitaccel*delta;
					velx += Math.sin((90-tangle)*(Math.PI/180))*maxorbitaccel*delta;
				} else if(angle > 180 && angle < 270) {
					vely += Math.sin((90-tangle)*(Math.PI/180))*maxorbitaccel*delta;
					velx -= Math.sin(tangle*(Math.PI/180))*maxorbitaccel*delta;
				} else {
					vely -= Math.sin(tangle*(Math.PI/180))*maxorbitaccel*delta;
					velx -= Math.sin((90-tangle)*(Math.PI/180))*maxorbitaccel*delta;
				}
			}
		}
		
	}
	
	public boolean updateVelocity(int delta) {
		if(Ship.planetLocked == -1) {
			if(velx > maxspeed) velx = maxspeed;
			else if(velx < -maxspeed) velx = -maxspeed;
			if(vely > maxspeed) vely = maxspeed;
			else if(vely < -maxspeed) vely = -maxspeed;
		} else {
			if(velx > maxorbitspeed) velx = maxorbitspeed;
			else if(velx < -maxorbitspeed) velx = -maxorbitspeed;
			if(vely > maxorbitspeed) vely = maxorbitspeed;
			else if(vely < -maxorbitspeed) vely = -maxorbitspeed;
		}
		
		if(PlayMode.systemFocus == -1) {
			if(velx != 0 || vely != 0) {
				if(PlayMode.fuel > 0) {
					PlayMode.fuel -= .03*delta;
				}
			}
		} else {
			if(velx != 0 || vely != 0) {
				if(PlayMode.fuel > 0) {
					PlayMode.fuel -= .001*delta;
				}
			}
		}
		
		Ship.bounds = new Rectangle((float)Ship.locx-Ship.img.getWidth()/2,
				(float)Ship.locy-Ship.img.getHeight()/2, Ship.img.getWidth(), Ship.img.getHeight());
		Ship.radius = new Circle((float)Ship.locx, (float)Ship.locy, Ship.img.getHeight()/2);
		Ship.largeRadius = new Circle((float)Ship.locx, (float)Ship.locy, Ship.img.getHeight()*2);
		
		if(PlayMode.fuel > 0) {
			return false;
		}
		return true;
	}
	
	public static boolean picify() {
		int startx = -1; int starty = -1; int endx = -1; int endy = -1;
		
		for(int i = 0; i != Grid.tiles.length; i++) {
			if(startx != -1) break;
			for(int j = 0; j != Grid.tiles[0].length; j++) {
				if(Grid.tiles[i][j].node != -1 || Grid.tiles[i][j].floor != Tile.EMPTY) {
					startx = i;
					break;
				}
			}
		}
		
		if(startx == -1) return false;
		
		for(int i = 0; i != Grid.tiles[0].length; i++) {
			if(starty != -1) break;
			for(int j = 0; j != Grid.tiles.length; j++) {
				if(Grid.tiles[j][i].node != -1 || Grid.tiles[j][i].floor != Tile.EMPTY) {
					starty = i;
					break;
				}
			}
		}
		boolean setend = false;
		for(int i = startx; i != Grid.tiles.length; i++) {
			setend = false;
			for(int j = 0; j != Grid.tiles[0].length; j++) {
				if(Grid.tiles[i][j].floor != Tile.EMPTY || Grid.tiles[i][j].node != -1) {
					setend = true;
				}
			}
			
			if(!setend) {
				endx = i; break;
			}
		}
		setend = false;
		for(int i = starty; i != Grid.tiles[0].length; i++) {
			setend = false;
			for(int j = 0; j != Grid.tiles.length; j++) {
				if(Grid.tiles[j][i].floor != Tile.EMPTY || Grid.tiles[j][i].node != -1) {
					setend = true;
				}
			}
			if(!setend) {
				endy = i; break;
			}
		}
		
		if(endx == -1) {
			endx = Grid.tiles.length;
		}
		if(endy == -1) {
			endy = Grid.tiles[0].length;
		}
		
		Tile[][] t = new Tile[endx-startx][endy-starty];
		
		int a = 0; int b = 0;
		for(int i = startx; i != endx; i++) {
			b = 0;
			for(int j = starty; j != endy; j++) {
				t[a][b] = Grid.tiles[i][j];
				b++;
			}
			a++;
		}
		
		Grid.tiles = t;
		Grid.columns = Grid.tiles.length;
		Grid.rows = Grid.tiles[0].length;
		
		Grid.width = Grid.columns*16;
		Grid.height = Grid.rows*16;
		
		Grid.pathify();
		
		for(Crew c : Grid.crew) {
			if(c != null) {
				c.tilex -= startx;
				c.tiley -= starty;
			}
		}
		
		ImageBuffer pixels = new ImageBuffer(Grid.tiles.length, Grid.tiles[0].length);
		
		for(int i = 0; i != Grid.tiles.length; i++) {
			for(int j = 0; j != Grid.tiles[0].length; j++) {
				if(Grid.tiles[i][j].floor == Tile.PATH_N) {
					pixels.setRGBA(i, j, 170, 170, 170, 255);
					
					if(Grid.tiles[i][j].node == Tile.CORE) {
						pixels.setRGBA(i, j, 102, 160, 253, 255);
					} else if(Grid.tiles[i][j].node == Tile.SHIELD_GEN) {
						pixels.setRGBA(i, j, 205, 201, 60, 255);
					} else if(Grid.tiles[i][j].node == Tile.ENGINE) {
						pixels.setRGBA(i, j, 255, 93, 123, 255);
					} else if(Grid.tiles[i][j].node == Tile.WARP) {
						pixels.setRGBA(i, j, 0, 213, 31, 255);
					} else if(Grid.tiles[i][j].node == Tile.WARP) {
						pixels.setRGBA(i, j, 102, 160, 253, 255);
					}
				} else if(Grid.tiles[i][j].floor == Tile.WALL_0) {
					pixels.setRGBA(i, j, 90, 90, 90, 255);
				} else if(Grid.tiles[i][j].floor == Tile.EMPTY) {
					pixels.setRGBA(i, j, 0, 0, 0, 0);
				}
			}
		}

		img = new Image(pixels);
		img = img.getScaledCopy((float)Math.log(Grid.columns*Grid.rows)/10);
		
		
		Ship.bounds = new Rectangle((float)Ship.locx, (float)Ship.locy, 
									Ship.img.getWidth(), Ship.img.getHeight());
		angle = 90;
		
		int longerDimension = (int)Math.sqrt(Math.pow(Grid.columns, 2) + Math.pow(Grid.rows, 2))/2;
		shieldBubble = new Circle((float)Ship.locx, (float)Ship.locy, longerDimension);
		
		return true;
	}
	
	public static void resizeGrid(int direction) {
		Tile[][] t = null;
		switch(direction) {
		case 0: t = new Tile[Grid.columns][Grid.rows+1];
				int a = 0; int b = 0;
				for(int i = 0; i != Grid.columns; i++) {
					b = 0;
					for(int j = 1; j != Grid.rows+1; j++) {
						t[i][j] = Grid.tiles[a][b];
						b++;
					}
					a++;
				}
				
				for(int i = 0; i != Grid.columns; i++) {
					t[i][0] = new Tile(Tile.EMPTY, -1, -1, 8);
				}
				
				for(Crew c : Grid.crew) {
					if(c != null) {
						c.tiley += 1;
					}
				}
				
				break;
		case 1: t = new Tile[Grid.columns+1][Grid.rows];
				a = 0; b = 0;
				for(int i = 0; i != Grid.columns; i++) {
					b = 0;
					for(int j = 0; j != Grid.rows; j++) {
						t[i][j] = Grid.tiles[a][b];
						b++;
					}
					a++;
				}
				
				for(int i = 0; i != Grid.rows; i++) {
					t[Grid.columns][i] = new Tile(Tile.EMPTY, -1, -1, 8);
				}
			
				Grid.x -= Grid.tileSize;
				break;
		case 2: t = new Tile[Grid.columns][Grid.rows+1];
				a = 0; b = 0;
				for(int i = 0; i != Grid.columns; i++) {
					b = 0;
					for(int j = 0; j != Grid.rows; j++) {
						t[i][j] = Grid.tiles[a][b];
						b++;
					}
					a++;
				}
				
				for(int i = 0; i != Grid.columns; i++) {
					t[i][Grid.rows] = new Tile(Tile.EMPTY, -1, -1, 8);
				}
				
				Grid.y -= Grid.tileSize;
				break;
		case 3: t = new Tile[Grid.columns+1][Grid.rows];
				a = 0; b = 0;
				for(int i = 1; i != Grid.columns+1; i++) {
					b = 0;
					for(int j = 0; j != Grid.rows; j++) {
						t[i][j] = Grid.tiles[a][b];
						b++;
					}
					a++;
				}
				
				for(int i = 0; i != Grid.rows; i++) {
					t[0][i] = new Tile(Tile.EMPTY, -1, -1, 8);
				}
				
				for(Crew c : Grid.crew) {
					if(c != null) {
						c.tilex += 1;
					}
				}
				break;
		}
		

		Grid.tiles = t;
		int a  = Grid.columns;
		Grid.columns = Grid.tiles.length;
		a  = Grid.columns;
		Grid.rows = Grid.tiles[0].length;
		
		Grid.width = Grid.columns*Grid.tileSize;
		Grid.height = Grid.rows*Grid.tileSize;
		
		Grid.pathify();
	}
	
	public static void engineConfig() {
		engines = 10;
		switch(engines) {
		case 0: Ship.maxspeed = 0.0d; break;
		case 1: Ship.maxspeed = 1/25d; break;
		case 2: Ship.maxspeed = 2/25d; break;
		case 3: Ship.maxspeed = 3/25d; break;
		case 4: Ship.maxspeed = 4/25d; break;
		case 5: Ship.maxspeed = 5/25d; break;
		case 6: Ship.maxspeed = 6/25d; break;
		case 7: Ship.maxspeed = 7/25d; break;
		case 8: Ship.maxspeed = 8/25d; break;
		case 9: Ship.maxspeed = 9/25d; break;
		case 10:Ship.maxspeed = 10/25d; break;
		case 11:Ship.maxspeed = 11/25d; break;
		case 12:Ship.maxspeed = 12/25d; break;
		case 13:Ship.maxspeed = 13/25d; break;
		case 14:Ship.maxspeed = 14/25d; break;
		case 15:Ship.maxspeed = 15/25d; break;
		}
		
		Ship.maxorbitspeed = Ship.maxspeed;
		if(maxorbitspeed > .2) {
			maxorbitspeed = .2;
		}
		Ship.maxaccel = Ship.maxspeed/250d; 
		Ship.maxorbitaccel = Ship.maxorbitspeed/200d;
	}
	
	public static void shieldConfig() {
		shieldsMax = 100*activeShieldGenerators;
		
		
	}
}
