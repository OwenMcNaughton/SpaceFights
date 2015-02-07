package celestialbodies;

import java.util.Arrays;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import editmode.Ship;
import Mains.EditMode;
import Mains.PlayMode;

public class StarSystem {

	public boolean focus;
	
	public double locx;
	public double locy;
	
	public int[] seed;
	
	public Star star;
	public Planet[] planets;
	public int terraAmount;
	public int oceanicAmount;
	public int otherTerrestrialAmount;
	public int dwarfAmount;
	public int gasAmount;
	public int[] sortedPop;
	public int[] sortedDist;
	
	
	public Bot[] bots;
	public Bot[] attackBots;
	public int botInfo;
	public Image infoBox;
	public Rectangle infoRect;
	public int infoOn;
	public int mouseX;
	public int mouseY;
	
	public StarSystem(double x, double y) {
		this.locx = x;
		this.locy = y;
		
		this.focus = false;

		Random gen = new Random();
		this.star = new Star(x, y);
		
		int planetNo = 0;
		if(gen.nextInt(3) != 0) {
			planetNo = (int)(Math.abs(gen.nextGaussian())*45);
			if(planetNo > 10) planetNo -= gen.nextInt(10);
			//System.out.print(planetNo + ",");
		}
		
		
		this.planets = new Planet[planetNo];
		for(int i = 0; i != this.planets.length; i++) {
			planets[i] = new Planet(x, y, this.star.name);
		}
		
		for(int i = 0; i != this.planets.length; i++) {
			switch(this.planets[i].type) {
			case Planet.GAS_GIANT: this.gasAmount++; break;
			case Planet.TERRA_WORLD: this.terraAmount++; break;
			case Planet.OCEANIC_WORLD: this.oceanicAmount++; break;
			case Planet.DWARF_PLANET: this.dwarfAmount++; break;
			default: this.otherTerrestrialAmount++; break;
			}
		}

		for(int i = 0; i != this.planets.length; i++) {
			this.planets[i].initPopulation();	
			this.planets[i].spritify();
			
			if(this.planets[i].population > 10000000) {
				this.planets[i].shop = new Shop(this.planets[i].type, this.locx, this.locy);
			}
		}
		
		this.sortByPop();
		this.sortByDist();
		
		this.bots = new Bot[100];
		int botsIter = 0;
		if(this.sortedPop != null) {
			for(int i = this.sortedPop.length-1; i != -1; i--) {
				if(this.planets[sortedPop[i]].population > 10000) {
					if(this.planets[sortedPop[i]].population < 100000) {		//<10000								//0-3
						botBatch(1, botsIter, i);								//3
						botsIter += 1*3;
					} else if(this.planets[sortedPop[i]].population < 1000000) { //<1mil
						int r = 1 + (gen.nextInt(2));							 //3-6
						botBatch(r, botsIter, i);
						botsIter += r*3;
					} else if(this.planets[sortedPop[i]].population < 10000000) { //<10mil
						int r = 2 + (gen.nextInt(3));							  //6-12
						botBatch(r, botsIter, i);
						botsIter += r*3;
					} else if(this.planets[sortedPop[i]].population < 100000000) { //<100mil 
						int r = 4 + gen.nextInt(4);								   //12-21
						botBatch(r, botsIter, i);
						botsIter += r*3;
					} else if(this.planets[sortedPop[i]].population < 1000000000) { //<1bil {
						int r = 7 + (gen.nextInt(6));							    //21-36
						botBatch(r, botsIter, i);
						botsIter += r*3;
					} else { 														//>1bil {
						int r = 12 + (gen.nextInt(9));						   		//36-60
						botBatch(r, botsIter, i);
						botsIter += r*3;
					}
				}
			}
		}
		
		this.attackBots = new Bot[50];
		botsIter = 0;
		if(this.sortedPop != null) {
			for(int i = this.sortedPop.length-1; i != -1; i--) {
				attackBotBatch((this.planets[sortedPop[i]].traders + 
						this.planets[sortedPop[i]].miners)/5, botsIter, i);
				botsIter += (this.planets[sortedPop[i]].traders + this.planets[sortedPop[i]].miners)/2;
			}
		}
		
		for(int i = this.sortedPop.length-1; i != -1; i--) {
			this.planets[sortedPop[i]].freeTitanium = this.planets[sortedPop[i]].traders*100;
			this.planets[sortedPop[i]].freeUranium = this.planets[sortedPop[i]].traders*100;
			this.planets[sortedPop[i]].freeFuel = this.planets[sortedPop[i]].traders*100;
		}
		
		try {
			this.infoBox = new org.newdawn.slick.Image("res//infoBox.png");
		} catch (SlickException e) {}
		this.infoOn = -1;
		this.botInfo = -1;
	}
	
	private void botBatch(int amount, int botsIter, int planetNo) {
		Random gen = new Random();
		
		for(int i = 0; i != amount; i++) {
			if(botsIter > 97) return;
			this.planets[sortedPop[planetNo]].orbitColor = new Color(0, 200, 0);
			int r = gen.nextInt(100);
			if(r < 50) { 		//17% small traders
				this.bots[botsIter++] = new Bot(this.planets[sortedPop[planetNo]].locx, 
						this.planets[sortedPop[planetNo]].locy,
						Bot.SMALL_TRADER, sortedPop[planetNo]);
				this.planets[sortedPop[planetNo]].traders++;
			} else if(r < 85) {	//8% medium traders
				this.bots[botsIter++] = new Bot(this.planets[sortedPop[planetNo]].locx, 
						this.planets[sortedPop[planetNo]].locy,
						Bot.MEDIUM_TRADER, sortedPop[planetNo]);
				this.planets[sortedPop[planetNo]].traders++;
			} else {			//5% large traders
				this.bots[botsIter++] = new Bot(this.planets[sortedPop[planetNo]].locx, 
						this.planets[sortedPop[planetNo]].locy,
						Bot.LARGE_TRADER, sortedPop[planetNo]);
				this.planets[sortedPop[planetNo]].traders++;
			}
			
			for(int j = 0; j != 2; j++) {
				r = gen.nextInt(100);
				if(r < 50) {
					this.bots[botsIter++] = new Bot(this.planets[sortedPop[planetNo]].locx, 
							this.planets[sortedPop[planetNo]].locy,
							Bot.SMALL_MINER, sortedPop[planetNo]);
					this.planets[sortedPop[planetNo]].miners++;
				} else if(r < 85) {
					this.bots[botsIter++] = new Bot(this.planets[sortedPop[planetNo]].locx, 
							this.planets[sortedPop[planetNo]].locy,
							Bot.MEDIUM_MINER, sortedPop[planetNo]);
					this.planets[sortedPop[planetNo]].miners++;
				} else {
					this.bots[botsIter++] = new Bot(this.planets[sortedPop[planetNo]].locx, 
							this.planets[sortedPop[planetNo]].locy,
							Bot.LARGE_MINER, sortedPop[planetNo]);
					this.planets[sortedPop[planetNo]].miners++;
				}
			}
		}
	}
	
	private void attackBotBatch(int amount, int botsIter, int planetNo) {
		Random gen = new Random();
		
		for(int i = 0; i != amount; i++) {
			if(botsIter > 99) return;
			this.attackBots[botsIter++] = new Bot(this.planets[sortedPop[planetNo]].locx+gen.nextInt(100)-200, 
					this.planets[sortedPop[planetNo]].locy+gen.nextInt(100)-200,
					Bot.LARGE_FIGHTER, sortedPop[planetNo]);
		}
	}

	private void sortByPop() {
		this.sortedPop = new int[this.planets.length];
		long[] populations = new long[this.planets.length];

		for(int i = 0; i != this.planets.length; i++) {
			populations[i] = this.planets[i].population;
		}
		
		Arrays.sort(populations);
		
		for(int j = this.planets.length-1; j != -1; j--) {
			for(int i = 0; i != this.planets.length; i++) {
				if(this.planets[i].population == populations[j]) {
					this.sortedPop[j] = i;
					break;
				}
			}		
		}
	}
	
	private void sortByDist() {
		this.sortedDist = new int[this.planets.length];
		int[] distances = new int[this.planets.length];

		for(int i = 0; i != this.planets.length; i++) {
			distances[i] = (int)this.planets[i].orbitalRadius;
		}
		
		Arrays.sort(distances);
		
		for(int j = 0; j != this.planets.length; j++) {
			for(int i = 0; i != this.planets.length; i++) {
				if((int)this.planets[j].orbitalRadius == distances[i]) {
					this.sortedDist[j] = i;
					break;
				}
			}
		}
		
		int a = 1;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		PlayMode.starSheet.startUse();
		PlayMode.starSheet.renderInUse((int)this.star.locx-128, (int)this.star.locy-128, 
								this.star.spriteCoords.x, this.star.spriteCoords.y);
		PlayMode.starSheet.endUse();
		
		/*
		//long start = System.nanoTime();
		g.setColor(new Color(100, 100, 100));
		for(int i = 0; i != this.planets.length; i++) {
			g.setColor(this.planets[i].orbitColor);
			g.draw(new Circle((float)this.star.locx, (float)this.star.locy, 
								(float)this.planets[i].orbitalRadius));
		}
		//System.out.println(System.nanoTime() - start);
		*/
		
		PlayMode.planetSheet.startUse();
		for(int i = 0; i != this.planets.length; i++) {
			if(this.planets[i] != null) {
				if(PlayMode.moonClip.contains((float)this.planets[i].locx, (float)this.planets[i].locy)) {
					PlayMode.planetSheet.renderInUse((int)this.planets[i].locx-16, (int)this.planets[i].locy-16, 
							this.planets[i].spriteCoords.x, this.planets[i].spriteCoords.y);
					if(planets[i].moon) {
						for(int j = 0; j != planets[i].moons.length; j++) {
							if(this.planets[i].moons[j] != null) {
								if(this.planets[i].moons[j].exists) {
									PlayMode.planetSheet.renderInUse((int)this.planets[i].moons[j].locx-16, 
											(int)this.planets[i].moons[j].locy-16, 
											this.planets[i].moons[j].spriteCoords.x, this.planets[i].moons[j].spriteCoords.y);
								}
							}
						}
					}
					
					if(this.planets[i].shop != null) {
						
						Image a = PlayMode.planetSheet.getSprite(this.planets[i].shop.spriteCoords.x, 
								  this.planets[i].shop.spriteCoords.y);

						PlayMode.planetSheet.endUse();
						a.setRotation((float)this.planets[i].shop.rotation);
						g.drawImage(a, (int)this.planets[i].shop.locx, 
								(int)this.planets[i].shop.locy);
						PlayMode.planetSheet.startUse();
						
					}
				}
			}
			
		}
		PlayMode.planetSheet.endUse();
		
		for(int i = 0; i != this.planets.length; i++) {
			if(this.planets[i] != null) {
				if(planets[i].moon) {
					for(int j = 0; j != planets[i].moons.length; j++) {
						if(this.planets[i].moons[j] != null) {
							if(this.planets[i].moons[j].exists) {
								g.draw(this.planets[i].moons[j].bounds);
							}
						}
					}
				}
			}
		}
		
		
		PlayMode.botSheet.startUse();
		for(int i = 0; i != this.bots.length; i++) {
			if(this.bots[i] != null) {
				Image a = PlayMode.botSheet.getSprite(this.bots[i].spriteCoords.x, 
												  this.bots[i].spriteCoords.y);
				
				PlayMode.botSheet.endUse();
				a.setRotation((float)this.bots[i].angle);
				g.drawImage(a, (int)this.bots[i].locx-16, (int)this.bots[i].locy-16);
				if(this.bots[i].targeted) {
					PlayMode.reticule.draw((float)this.bots[i].bounds.getCenterX()-16, 
							(float)this.bots[i].bounds.getCenterY()-16);
				}
				PlayMode.botSheet.startUse();	
			}
		}	
		
		for(int i = 0; i != this.attackBots.length; i++) {
			if(this.attackBots[i] != null) {
				Image a = PlayMode.botSheet.getSprite(this.attackBots[i].spriteCoords.x, 
						  this.attackBots[i].spriteCoords.y);

				PlayMode.botSheet.endUse();
				a.setRotation((float)this.attackBots[i].angle);
				g.drawImage(a, (int)this.attackBots[i].locx-16, (int)this.attackBots[i].locy-16);
				if(this.attackBots[i].targeted) {
					PlayMode.reticule.draw((float)this.attackBots[i].bounds.getCenterX()-16, 
							(float)this.attackBots[i].bounds.getCenterY()-16);
				}
				
				PlayMode.botSheet.startUse();	
			}
		}
		PlayMode.botSheet.endUse();
		
		PlayMode.projectileSheet.startUse();
		for(int i = 0; i != this.attackBots.length; i++) {
			if(this.attackBots[i] != null) {
				for(int j = 0; j != this.attackBots[i].projectiles.length; j++) {
					if(this.attackBots[i].projectiles[j] != null) {
						
						Image a = PlayMode.projectileSheet.getSprite(this.attackBots[i].projectiles[j].spriteCoords.x,
								this.attackBots[i].projectiles[j].spriteCoords.y);

						PlayMode.projectileSheet.endUse();
						a.setRotation((float)this.attackBots[i].projectiles[j].angle);
						g.drawImage(a, (int)this.attackBots[i].projectiles[j].locx-8, (
									int)this.attackBots[i].projectiles[j].locy-8);
						PlayMode.projectileSheet.startUse();	
					}
				}
				
			}
		}
		PlayMode.projectileSheet.endUse();
		
		if(infoOn != -1) {
			g.drawLine((float)this.infoRect.getX(), (float)this.infoRect.getY()+60, 
					(float)this.planets[infoOn].bounds.getCenterX(), (float)this.planets[infoOn].bounds.getCenterY());
			g.drawImage(this.infoBox, infoRect.getMinX(), infoRect.getY());
			g.setFont(EditMode.calibri12);
			EditMode.calibri12.drawString((float)this.infoRect.getX()+5, (float)this.infoRect.getY()+4,
						this.star.name + " " + this.sortedDist[infoOn]);
			EditMode.calibri12.drawString((float)this.infoRect.getX()+5, (float)this.infoRect.getY()+14,
					"Type: " + this.planets[infoOn].typeString);
			EditMode.calibri12.drawString((float)this.infoRect.getX()+5, (float)this.infoRect.getY()+24,
					"Pop: " + this.planets[infoOn].popStr);
			EditMode.calibri12.drawString((float)this.infoRect.getX()+5, (float)this.infoRect.getY()+34,
					"Traders: " + this.planets[infoOn].traders + ", Miners:" + this.planets[infoOn].miners);
			EditMode.calibri12.drawString((float)this.infoRect.getX()+5, (float)this.infoRect.getY()+44,
					"Moons: " + this.planets[infoOn].moonCount);
			
			g.drawImage(PlayMode.resourceBoxSmall, mouseX, mouseY);
			EditMode.calibri12.drawString(mouseX+18, mouseY,
					(int)this.planets[infoOn].titanium + " " + (int)this.planets[infoOn].freeTitanium);
			EditMode.calibri12.drawString(mouseX+18, mouseY+11,
					(int)this.planets[infoOn].uranium + " " + (int)this.planets[infoOn].freeUranium);
			EditMode.calibri12.drawString(mouseX+18, mouseY+22,
					(int)this.planets[infoOn].fuel + " " + (int)this.planets[infoOn].freeFuel);
			EditMode.calibri12.drawString(mouseX+18, mouseY+33,
					(int)this.planets[infoOn].gold + " " + (int)this.planets[infoOn].freeGold);
		}
		
		if(botInfo != -1) {
			g.drawLine((float)mouseX, (float)mouseY, (float)this.bots[botInfo].locx, 
						(float)this.bots[botInfo].locy);
			g.drawImage(PlayMode.resourceBoxSmall, mouseX, mouseY);
			EditMode.calibri12.drawString(mouseX+18, mouseY,
					(int)this.bots[botInfo].titanium + "");
			EditMode.calibri12.drawString(mouseX+18, mouseY+11,
					(int)this.bots[botInfo].uranium + "");
			EditMode.calibri12.drawString(mouseX+18, mouseY+22,
					(int)this.bots[botInfo].fuel + "");
			EditMode.calibri12.drawString(mouseX+18, mouseY+33,
					(int)this.bots[botInfo].gold + "");
			EditMode.calibri12.drawString(mouseX+38, mouseY,
					(int)this.bots[botInfo].parent + "");
		}
		
		
	}

	public void move(double velx, double vely, int delta) {
		this.star.locx -= velx*delta; this.star.locy -= vely*delta;
		
		for(int i = 0; i != this.planets.length; i++) {
			this.planets[i].locx -= velx*delta;
			this.planets[i].locy -= vely*delta;
		}
		
		for(int i = 0; i != this.bots.length; i++) {
			if(this.bots[i] != null) {
				this.bots[i].locx -= velx*delta;
				this.bots[i].locy -= vely*delta;
			}
		}	
		
		for(int i = 0; i != this.attackBots.length; i++) {
			if(this.attackBots[i] != null) {
				this.attackBots[i].locx -= velx*delta;
				this.attackBots[i].locy -= vely*delta;
				if(this.attackBots[i].projectiles != null) {
					for(int j = 0; j != this.attackBots[i].projectiles.length; j++) {
						if(this.attackBots[i].projectiles[j] != null) {
							this.attackBots[i].projectiles[j].locx -= velx*delta;
							this.attackBots[i].projectiles[j].locy -= vely*delta;
						}
					}
				}
			}
		}	
		
		for(int i = 0; i != this.planets.length; i++) {
			if(this.planets[i].moons != null) {
				for(int j = 0; j != this.planets[i].moons.length; j++) {
					if(this.planets[i].moons != null) {
						if(this.planets[i].moons[j] != null) {
							if(this.planets[i].moons[j].exists) {
								if(this.planets[i].moons[j].shipLocked) {
									this.planets[i].moons[j].locx -= velx*delta;
									this.planets[i].moons[j].locy -= vely*delta;
								}
							}
						}
					}
				}
			}
			
			if(this.planets[i].shop != null) {
				this.planets[i].shop.locx -= velx*delta;
				this.planets[i].shop.locy -= vely*delta;
			}
		}
	}
	
	public void menuMove(double x, double y) {
		this.star.locx -= x; this.star.locy -= y;
		
		for(int i = 0; i != this.planets.length; i++) {
			this.planets[i].locx -= x;
			this.planets[i].locy -= y;
		}
		
		for(int i = 0; i != this.bots.length; i++) {
			if(this.bots[i] != null) {
				this.bots[i].locx -= x;
				this.bots[i].locy -= y;
			}
		}	
		
		for(int i = 0; i != this.attackBots.length; i++) {
			if(this.attackBots[i] != null) {
				this.attackBots[i].locx -= x;
				this.attackBots[i].locy -= y;
				if(this.attackBots[i].projectiles != null) {
					for(int j = 0; j != this.attackBots[i].projectiles.length; j++) {
						if(this.attackBots[i].projectiles[j] != null) {
							this.attackBots[i].projectiles[j].locx -= x;
							this.attackBots[i].projectiles[j].locy -= y;
						}
					}
				}
			}
		}	
		
		for(int i = 0; i != this.planets.length; i++) {
			if(this.planets[i].moons != null) {
				for(int j = 0; j != this.planets[i].moons.length; j++) {
					if(this.planets[i].moons != null) {
						if(this.planets[i].moons[j].exists) {
							if(this.planets[i].moons[j].shipLocked) {
								this.planets[i].moons[j].locx -= x;
								this.planets[i].moons[j].locy -= y;
							}
						}
					}
				}
			}
			
			if(this.planets[i].shop != null) {
				this.planets[i].shop.locx -= x;
				this.planets[i].shop.locy -= y;
			}
		}
	}

	public void update(int delta, Input input, Ship s) {
		Random gen = new Random();
		int mx = input.getMouseX(); int my = input.getMouseY();
		
		for(int i = 0; i != this.planets.length; i++) {
			if(this.planets[i].orbitalAngle >= 360) {
				this.planets[i].orbitalAngle = 0;
			} else {
				this.planets[i].orbitalAngle += ((1.5*delta)/(this.planets[i].orbitalRadius));
			}
			this.planets[i].initLoc(this.star.locx, this.star.locy);
			
			//long start = System.nanoTime();
			if(PlayMode.moonClip.contains((float)this.planets[i].locx, (float)this.planets[i].locy)) {
				if(this.planets[i].moon) {
					for(int j = 0; j != this.planets[i].moons.length; j++) {
						if(this.planets[i].moons[j] != null) {
							if(this.planets[i].moons[j].exists) {
								if(this.planets[i].moons[j].shipLocked) {
									if(this.planets[i].moons[j].goToShip(s, delta)) {
										this.planets[i].resourceCount();
										this.planets[i].moons[j] = null;
									}
								} else {
									if(this.planets[i].moons[j].orbitalAngle >= 360) {
										this.planets[i].moons[j].orbitalAngle = 0;
									} else {
										this.planets[i].moons[j].orbitalAngle += (5*delta/this.planets[i].moons[j].orbitalRadius);
									}
									this.planets[i].moons[j].initLoc(this.planets[i].locx, this.planets[i].locy);
								}
							}
						}
					}
				}
				
				if(this.planets[i].shop != null) {
					if(this.planets[i].shop.orbitalAngle >= 360) {
						this.planets[i].shop.orbitalAngle = 0;
					} else {
						this.planets[i].shop.orbitalAngle += (5*delta/this.planets[i].shop.orbitalRadius);
					}
					this.planets[i].shop.initLoc(this.planets[i].locx-16, this.planets[i].locy-16);
					if(this.planets[i].shop.rotation < 0) {
						this.planets[i].shop.rotation = 360;
					} else {
						this.planets[i].shop.rotation += (.2*delta);
					}
					
					this.planets[i].shop.update(delta);
				
				}
			}
			//System.out.println(System.nanoTime() - start);
			
			if(input.isMouseButtonDown(2)) {
				if(PlayMode.worldClip.contains((float)mx, (float)my)) {
					if(this.planets[i].bounds.contains((float)mx, (float)my)) {
						try {
							this.infoBox = new org.newdawn.slick.Image("res//infoBox.png");
						} catch (SlickException e) {}
						infoOn = i;
						botInfo = -1;
					}
					if(infoOn != -1) {
						mouseX = mx;
						mouseY = my;
						infoRect = new Rectangle(mx, my-60, 130, 60);
					}
				}
			} else {
				this.infoBox = null;
				infoOn = -1;
			}
			
			if(input.isMouseButtonDown(1)) {
				if(PlayMode.worldClip.contains((float)mx, (float)my)) {
					if(this.planets[i].bounds.contains((float)mx, (float)my)) {
						Ship.planetLocked = i;
					}
				}
			}
		}
		
		for(Planet p : this.planets) {
			if(p.moon) {
				for(Moon m : p.moons) {
					if(m != null) {
						if(Ship.tractorRadius.contains((float)m.locx, (float)m.locy)) {
							m.shipLocked = true;
						}
					}
				}
			}
		}
		
		
		this.updateBots(input, delta);
		this.updateAttackBots(input, delta);
	}
	
	public void updateBots(Input input, int delta) {
		int mx = input.getMouseX(); int my = input.getMouseY();
		Random gen = new Random();
		for(int i = 0; i != this.bots.length; i++) {
			if(this.bots[i] != null) {
				
				if(input.isMouseButtonDown(2)) {
					if(this.bots[i].bounds.contains((float)mx, (float)my)) {
						if(infoOn == -1) {
							botInfo = i;
							infoOn = -1;
						}
					}
					mouseX = mx;
					mouseY = my;
				} else {
					botInfo = -1;
				}
				
				this.bots[i].targx = this.planets[this.bots[i].planetTarget].bounds.getCenterX();
				this.bots[i].targy = this.planets[this.bots[i].planetTarget].bounds.getCenterY();
				char wasd = 'w';
		
				Circle rad2 = new Circle((float)this.bots[i].locx, (float)this.bots[i].locy, 30);
				if(rad2.contains((float)this.planets[this.bots[i].planetTarget].bounds.getCenterX(), 
								 (float)this.planets[this.bots[i].planetTarget].bounds.getCenterY())) {
					this.bots[i].inOrbit += 1*delta;
					if(this.bots[i].type == Bot.SMALL_MINER || this.bots[i].type == Bot.MEDIUM_MINER ||
							this.bots[i].type == Bot.LARGE_MINER) {
						if(this.bots[i].parent != this.bots[i].planetTarget) {
							
							if(this.bots[i].titanium + this.bots[i].uranium + this.bots[i].fuel + this.bots[i].gold < this.bots[i].maxCargo) {
								this.bots[i].titanium += this.planets[this.bots[i].planetTarget].titanium/20d;
								this.bots[i].uranium += this.planets[this.bots[i].planetTarget].uranium/20d;
								this.bots[i].fuel += this.planets[this.bots[i].planetTarget].fuel/20d;
								this.bots[i].gold += this.planets[this.bots[i].planetTarget].gold/20d;
							}
						} else {
							this.planets[this.bots[i].parent].freeTitanium += this.bots[i].titanium; 
							this.bots[i].titanium = 0;
							this.planets[this.bots[i].parent].freeUranium += this.bots[i].uranium; 
							this.bots[i].uranium = 0;
							this.planets[this.bots[i].parent].freeFuel += this.bots[i].fuel; 
							this.bots[i].fuel = 0;
							this.planets[this.bots[i].parent].freeGold += this.bots[i].gold;
							this.bots[i].gold = 0;
						}
					} else {
						if(this.bots[i].parent == this.bots[i].planetTarget) {
							if(this.bots[i].titanium + this.bots[i].uranium + this.bots[i].fuel < this.bots[i].maxCargo) {
								double t = this.planets[this.bots[i].planetTarget].freeTitanium/20d;
								this.bots[i].titanium += t; this.planets[this.bots[i].planetTarget].freeTitanium -= t;
								t = this.planets[this.bots[i].planetTarget].freeUranium/20d;
								this.bots[i].uranium += t; this.planets[this.bots[i].planetTarget].freeUranium -= t;
								t = this.planets[this.bots[i].planetTarget].freeFuel/20d;
								this.bots[i].fuel += t;this.planets[this.bots[i].planetTarget].freeFuel -= t;
							}
							this.planets[this.bots[i].planetTarget].freeGold += this.bots[i].gold; 
							this.bots[i].gold = 0;
						} else {
							this.planets[this.bots[i].planetTarget].freeGold -= (this.bots[i].titanium + this.bots[i].uranium + this.bots[i].fuel)/2;
							if(this.planets[this.bots[i].planetTarget].freeGold < 0) this.planets[this.bots[i].planetTarget].freeGold = 0;
							this.bots[i].gold += (this.bots[i].titanium + this.bots[i].uranium + this.bots[i].fuel)/2;
							this.planets[this.bots[i].planetTarget].freeTitanium += this.bots[i].titanium; 
							this.bots[i].titanium = 0;
							this.planets[this.bots[i].planetTarget].freeUranium += this.bots[i].uranium; 
							this.bots[i].uranium = 0;
							this.planets[this.bots[i].planetTarget].freeFuel += this.bots[i].fuel; 
							this.bots[i].fuel = 0;
						}
					}
				}
				
				if(this.bots[i].inOrbit != -1) {
					if(this.bots[i].inOrbit > 3000) {
						if(this.bots[i].type == Bot.LARGE_MINER || this.bots[i].type == Bot.MEDIUM_MINER ||
								this.bots[i].type == Bot.SMALL_MINER) {
							if(this.bots[i].planetTarget != this.bots[i].parent) {
								this.bots[i].planetTarget = this.bots[i].parent;
								this.bots[i].inOrbit = -1;
							} else {
								boolean b = true;
								int a = gen.nextInt(this.planets.length);
								while(b) {
									a = gen.nextInt(this.planets.length);
									if(this.planets[a].moons != null) {
										b = false;
									}
								}
								this.bots[i].planetTarget = a;
								this.bots[i].inOrbit = -1;
							}
						} else {
							if(this.bots[i].planetTarget != this.bots[i].parent) {
								this.bots[i].planetTarget = this.bots[i].parent;
								this.bots[i].inOrbit = -1;
							} else {
								for(int j = this.sortedPop.length-1; j != -1; j--) {
									if(this.planets[this.sortedPop[j]].population != 0) {
										if(this.sortedPop[j] != this.bots[i].planetTarget) {
											if(gen.nextInt(4) == 0) {
												this.bots[i].planetTarget = this.sortedPop[j]; 
												this.bots[i].inOrbit = -1; break;
											}
										}
									}
								}
							}
						}
					}
				}

				this.bots[i].rotate();
				this.bots[i].accelerate(wasd, delta);
				this.bots[i].updateVelocity(delta);
			}
		}
	}
	
	public void updateAttackBots(Input input, int delta) {
		Random gen = new Random();
		for(int i = 0; i != this.attackBots.length; i++) {
			if(this.attackBots[i] != null) {
				this.attackBots[i].fireTick1 += delta;
				
				if(this.attackBots[i].vision.contains((float)Ship.locx, (float)Ship.locy)) {
					this.attackBots[i].targx = Ship.locx;
					this.attackBots[i].targy = Ship.locy;
					this.attackBots[i].shipLocked = true;
					
					if(this.attackBots[i].fireTick1 > this.attackBots[i].fireTick1Window) {
						this.attackBots[i].fireTick2 += delta;
						if(this.attackBots[i].fireTick2 > this.attackBots[i].fireTick2Max) {
							this.attackBots[i].fireTick2 = 0;
							this.attackBots[i].fire(i);
						}
					}
					
					if(this.attackBots[i].fireTick1 > this.attackBots[i].fireTick1Max) {
						this.attackBots[i].fireTick1 = 0;
					}
	
				} else if(this.attackBots[i].shipLocked) {
					this.attackBots[i].shipLocked = false;
					for(int j = this.sortedPop.length-1; j != -1; j--) {
						if(this.planets[this.sortedPop[j]].population != 0) {
							if(this.sortedPop[j] != this.bots[i].planetTarget) {
								if(gen.nextInt(4) == 0) {
									this.attackBots[i].planetTarget = this.sortedPop[j];
								}
							}
						}
					}
				}
				
				if(!this.attackBots[i].shipLocked) {
					this.attackBots[i].targx = this.planets[this.bots[i].planetTarget].bounds.getCenterX();
					this.attackBots[i].targy = this.planets[this.bots[i].planetTarget].bounds.getCenterY();
				}
				
				for(int j = 0; j != this.attackBots[i].projectiles.length; j++) {
					if(this.attackBots[i].projectiles[j] != null) {
						if(this.attackBots[i].projectiles[j].update(delta)) {
							this.attackBots[i].projectiles[j] = null;
						} else {
							if(this.attackBots[i].projectiles[j].lifetime < 0) {
								this.attackBots[i].projectiles[j] = null;
							}
						}
					}
				}
				
				char wasd = 'w';
				if(this.attackBots[i].attackRadius.intersects(Ship.shieldBubble)){
					wasd = 's';
				}
				
				this.attackBots[i].rotate();
				this.attackBots[i].accelerate(wasd, delta);
				this.attackBots[i].updateVelocity(delta);
			}
		}
	}
		
		
		
		
		
		
		
		
		
}
