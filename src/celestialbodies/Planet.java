package celestialbodies;

import java.awt.Point;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

public class Planet {;

	public boolean moon;
	
	public int type;
	public static final int GAS_GIANT = 0;
	public static final int DWARF_PLANET = 1;
	public static final int BARREN_PLANET = 2;
	public static final int TITAN = 3;
	public static final int ICE_WORLD = 4;
	public static final int VOLCANIC_WORLD = 5;
	public static final int DESERT_WORLD = 6;
	public static final int OCEANIC_WORLD = 7;
	public static final int JUNGLE_WORLD = 8;
	public static final int TERRA_WORLD = 9;
	
	public String typeString;
	public static final String GAS_GIANTs = "Gas Giant";
	public static final String DWARF_PLANETs = "Dwarf Planet";
	public static final String BARREN_PLANETs = "Barren Planet";
	public static final String TITANs = "Titan";
	public static final String ICE_WORLDs = "Ice World";
	public static final String VOLCANIC_WORLDs = "Volcanic World";
	public static final String DESERT_WORLDs = "Desert World";
	public static final String OCEANIC_WORLDs = "Oceanic World";
	public static final String JUNGLE_WORLDs = "Jungle World";
	public static final String TERRA_WORLDs = "Terra";
	
	public int temperature;
	public long population;
	public String popStr;
	public double radius;
	public double orbitalRadius;
	public double orbitalAngle;
	public int traders;
	public int miners;
	
	public double locx;
	public double locy;
	
	public Point spriteCoords;
	public Circle orbit, bounds;
	public Color orbitColor;
	
	public Moon[] moons;
	public int moonCount;
	public String resourceType;
	
	public String starName;
	
	public double titanium;
	public double uranium;
	public double fuel;
	public double gold;
	
	public double freeTitanium;
	public double freeUranium;
	public double freeFuel;
	public double freeGold;
	
	public Shop shop;
	
	
	public Planet(double x, double y, String n) {
		Random gen = new Random();
		
		this.typify();
		this.spritify();
		
		this.initOrbitalRadius();
		
		this.orbitalAngle = gen.nextInt(360);
		
		this.initLoc(x, y);
		this.orbit = new Circle((float)this.locx, (float)this.locx, (float)this.orbitalRadius);
		
		this.initMoons();
		this.moonCount = 0;
		this.resourceCount();
		
		this.stringify();
		
		this.orbitColor = new Color(200, 200, 200);
		
		this.starName = n;
	}
	
	public void typify() {
		Random gen = new Random();
		
		int r = gen.nextInt(100);
		if(r < 40) {
			this.type = GAS_GIANT;
		} else if(r < 55) {
			this.type = DWARF_PLANET;
		} else if(r < 65) {
			this.type = BARREN_PLANET;
		} else if(r < 77) {
			this.type = TITAN;
		} else if(r < 85) {
			this.type = ICE_WORLD;
		} else if(r < 92) {
			this.type = VOLCANIC_WORLD;
		} else if(r < 94) {
			this.type = JUNGLE_WORLD;
		} else if(r < 96) {
			this.type = OCEANIC_WORLD;
		} else if(r < 98) {
			this.type = DESERT_WORLD;
		} else if(r > 97) {
			this.type = TERRA_WORLD;
		} 
		
	}

	public void resourceCount() {
		this.moonCount = 0;
		this.titanium = 0;
		this.uranium = 0;
		this.fuel = 0;
		this.gold = 0;
		if(this.moon) {
			for(int i = 0; i != this.moons.length; i++) {
				if(this.moons[i] != null) {
					if(this.moons[i].exists) {
						switch(this.moons[i].type) {
							case Moon.TITANIUM: this.titanium += this.moons[i].size; break;
							case Moon.URANIUM: this.uranium += this.moons[i].size; break;
							case Moon.FUEL: this.fuel += this.moons[i].size; break;
							case Moon.GOLD: this.gold += this.moons[i].size; break;
						}
						this.moonCount++;
					}
				}
			}
		}
		
	}

	private void stringify() {
		switch(this.type) {
		case TERRA_WORLD: this.typeString = TERRA_WORLDs; break;
		case ICE_WORLD: this.typeString = ICE_WORLDs; break;
		case TITAN: this.typeString = TITANs; break;
		case DWARF_PLANET: this.typeString = DWARF_PLANETs; break;
		case BARREN_PLANET: this.typeString = BARREN_PLANETs; break;
		case VOLCANIC_WORLD: this.typeString = VOLCANIC_WORLDs; break;
		case OCEANIC_WORLD: this.typeString = OCEANIC_WORLDs; break;
		case DESERT_WORLD: this.typeString = DESERT_WORLDs; break;
		case GAS_GIANT: this.typeString = GAS_GIANTs; break;
		case JUNGLE_WORLD: this.typeString = JUNGLE_WORLDs; break;
		}
		
	}
	
	private void initOrbitalRadius() {
		Random gen = new Random();

		switch(this.type) {
		case TERRA_WORLD: this.orbitalRadius = 330 + Math.abs(gen.nextGaussian())*(gen.nextInt(60) - 30); break;
		case JUNGLE_WORLD: this.orbitalRadius = 310 + Math.abs(gen.nextGaussian())*(gen.nextInt(100) - 50); break;
		case DESERT_WORLD: this.orbitalRadius = 230 + Math.abs(gen.nextGaussian())*(gen.nextInt(300) - 150); break;
		case OCEANIC_WORLD: this.orbitalRadius = 310 + Math.abs(gen.nextGaussian())*(gen.nextInt(150) - 75); break;
		case ICE_WORLD: this.orbitalRadius = 380 + Math.abs(gen.nextGaussian())*gen.nextInt(2150); break;
		case GAS_GIANT: {
			if(gen.nextInt(100) == 0) {
				this.orbitalRadius = 100 + (gen.nextInt(100)); break;
			} else {
				this.orbitalRadius = 350 + (gen.nextInt(2150)); break;
			}
		}
		case VOLCANIC_WORLD: {
			if(gen.nextInt(3) == 0) {
				this.orbitalRadius = 50 + gen.nextInt(200); break;
			} else {
				this.orbitalRadius = 350 + (gen.nextInt(2150)); break;
			}
		}
		case TITAN: this.orbitalRadius = 50 + gen.nextInt(2450); break;
		case DWARF_PLANET: this.orbitalRadius = 50 + gen.nextInt(2450); break;
		case BARREN_PLANET: this.orbitalRadius = 50 + gen.nextInt(2450); break;
		default: this.orbitalRadius = 1000;
		}
		
		this.orbitalRadius *= 2;
		round(this.orbitalRadius, 7);
	}
	
	public void initLoc(double x, double y) {
		
		double xdist = this.orbitalRadius * Math.sin(Math.toRadians(this.orbitalAngle));
		double ydist = this.orbitalRadius * Math.sin(Math.toRadians(180 - 90 - this.orbitalAngle));
		
		this.locx = x + xdist;
		this.locy = y + ydist;
		
		this.bounds = new Circle((float)this.locx+16, (float)this.locy+16, 35);
	}
	
	public void spritify() {
		Random gen = new Random();
		switch(this.type) {
		case GAS_GIANT: {
			int a = gen.nextInt(14);
			switch(a) {
			case 0: spriteCoords = new Point(4, 0); break;
			case 1: spriteCoords = new Point(5, 0); break;
			case 2: spriteCoords = new Point(6, 0); break;
			case 3: spriteCoords = new Point(7, 0); break;
			case 4: spriteCoords = new Point(4, 1); break;
			case 5: spriteCoords = new Point(5, 1); break;
			case 6: spriteCoords = new Point(6, 1); break;
			case 7: spriteCoords = new Point(7, 1); break;
			case 8: spriteCoords = new Point(4, 2); break;
			case 9: spriteCoords = new Point(5, 2); break;
			case 10: spriteCoords = new Point(6, 2); break;
			case 11: spriteCoords = new Point(7, 2); break;
			case 12: spriteCoords = new Point(4, 3); break;
			case 13: spriteCoords = new Point(5, 3); break;
			default: spriteCoords = new Point(0, 0); break;
			}
		} break;
		case TERRA_WORLD: {
			int a = gen.nextInt(4);
			switch(a) {
			case 0: spriteCoords = new Point(0, 2); break;
			case 1: spriteCoords = new Point(1, 2); break;
			case 2: spriteCoords = new Point(2, 2); break;
			case 3: spriteCoords = new Point(3, 2); break;
			}
		} break;
		case ICE_WORLD: {
			int a = gen.nextInt(4);
			switch(a) {
			case 0: spriteCoords = new Point(0, 0); break;
			case 1: spriteCoords = new Point(1, 0); break;
			case 2: spriteCoords = new Point(2, 0); break;
			case 3: spriteCoords = new Point(3, 0); break;
			} 
		} break;
		case TITAN: {
			int a = gen.nextInt(4);
			switch(a) {
			case 0: spriteCoords = new Point(0, 5); break;
			case 1: spriteCoords = new Point(1, 5); break;
			case 2: spriteCoords = new Point(2, 5); break;
			case 3: spriteCoords = new Point(3, 5); break;
			} 
		} break;
		case DWARF_PLANET: {
			int a = gen.nextInt(4);
			switch(a) {
			case 0: spriteCoords = new Point(0, 7); break;
			case 1: spriteCoords = new Point(1, 7); break;
			case 2: spriteCoords = new Point(2, 7); break;
			case 3: spriteCoords = new Point(3, 7); break;
			} 
		} break;
		case BARREN_PLANET: {
			int a = gen.nextInt(4);
			switch(a) {
			case 0: spriteCoords = new Point(0, 6); break;
			case 1: spriteCoords = new Point(1, 6); break;
			case 2: spriteCoords = new Point(2, 6); break;
			case 3: spriteCoords = new Point(3, 6); break;
			} 
		} break;
		case VOLCANIC_WORLD: {
			int a = gen.nextInt(4);
			switch(a) {
			case 0: spriteCoords = new Point(0, 4); break;
			case 1: spriteCoords = new Point(1, 4); break;
			case 2: spriteCoords = new Point(2, 4); break;
			case 3: spriteCoords = new Point(3, 4); break;
			} 
		} break;
		case OCEANIC_WORLD: {
			int a = gen.nextInt(4);
			switch(a) {
			case 0: spriteCoords = new Point(0, 3); break;
			case 1: spriteCoords = new Point(1, 3); break;
			case 2: spriteCoords = new Point(2, 3); break;
			case 3: spriteCoords = new Point(3, 3); break;
			} 
		} break;
		case DESERT_WORLD: {
			int a = gen.nextInt(4);
			switch(a) {
			case 0: spriteCoords = new Point(0, 1); break;
			case 1: spriteCoords = new Point(1, 1); break;
			case 2: spriteCoords = new Point(2, 1); break;
			case 3: spriteCoords = new Point(3, 1); break;
			} 
		} break;
		case JUNGLE_WORLD: {
			int a = gen.nextInt(4);
			switch(a) {
			case 0: spriteCoords = new Point(4, 4); break;
			case 1: spriteCoords = new Point(4, 5); break;
			case 2: spriteCoords = new Point(4, 6); break;
			case 3: spriteCoords = new Point(4, 7); break;
			} 
		} break;
		default: spriteCoords = new Point(0, 0); break;
		}
	}
	
	private void initMoons() {
		Random gen = new Random();
		
		switch(this.type) {
		case GAS_GIANT: this.moon = true; break;
		case DWARF_PLANET: int a = gen.nextInt(4); if(a == 3) {this.moon = true;}; break;
		default: a = gen.nextInt(2); if(a == 1) {this.moon = true;}; break;
		}
		
		if(this.moon) {
			if(this.type == GAS_GIANT) {
				int c = (int)((Math.abs(gen.nextGaussian()))*80d);
				if(c>10 && gen.nextInt(3) == 0) {

				}
				//System.out.print(c + ",");
				this.moons = new Moon[c];
			} else {
				int b = gen.nextInt(20);
				if(b > 0 && b < 15) {
					this.moons = new Moon[1];
				} else if(b > 15 && b < 19) {
					this.moons = new Moon[2];
				} else {
					this.moons = new Moon[3];
				}
			}
			
			int r = gen.nextInt(100);
			if(r < 50) { //50%
				this.resourceType = "Hybrid"; //45% T, 35% F, 15% U, 5% G
				for(int i = 0; i != this.moons.length; i++) {
					if(gen.nextInt(100) < 45) {
						this.moons[i] = new Moon(Moon.TITANIUM, -1, this.type, this.locx, this.locy);
					} else if(gen.nextInt(100) < 85) {
						this.moons[i] = new Moon(Moon.FUEL, -1, this.type, this.locx, this.locy);
					} else if(gen.nextInt(100) < 95) {
						this.moons[i] = new Moon(Moon.URANIUM, -1, this.type, this.locx, this.locy);
					} else {
						this.moons[i] = new Moon(Moon.GOLD, 10, this.type, this.locx, this.locy);
					}
				}
			} else if(r < 75) { //25%
				this.resourceType = "Pure Titanium";
				for(int i = 0; i != this.moons.length; i++) {
						this.moons[i] = new Moon(Moon.TITANIUM, -1, this.type, this.locx, this.locy);
				}
			} else if(r < 95) { //20%
				this.resourceType = "Pure Fuel";
				for(int i = 0; i != this.moons.length; i++) {
						this.moons[i] = new Moon(Moon.FUEL, -1, this.type, this.locx, this.locy);
				}
			} else if(r < 98) { //3%
				this.resourceType = "Pure Uranium"; 
				for(int i = 0; i != this.moons.length; i++) {
					this.moons[i] = new Moon(Moon.URANIUM, -1, this.type, this.locx, this.locy);
				}
			} else if(r > 97) { //1%
				this.resourceType = "Pure Gold";
				for(int i = 0; i != this.moons.length; i++) {
					this.moons[i] = new Moon(Moon.GOLD, -1, this.type, this.locx, this.locy);
				}
			}
			int a = 1;
		}
		
	}
	
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public void initPopulation() {
		Random gen = new Random();
		if(this.type == TERRA_WORLD) {
			int r = gen.nextInt(100);
			if(r < 1) { 	   //1% 0
				this.population = 0;
			} else if(r < 3) { //2% ~100,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(100000)*mul);
			} else if(r < 10) { //7% ~1,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(1000000)*mul);
			} else if(r < 25) { //15% ~10,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(10000000)*mul);
			} else if(r < 45) { //20% ~100,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(100000000)*mul);
			} else if(r < 75) { //30% ~1,000,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)((long)gen.nextInt(1000000000)*mul);
			} else if(r < 95) { //20% ~10,000,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)((long)gen.nextInt(1000000000)*10*mul);
			} else { 			//5% ~100,000,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)((long)gen.nextInt(1000000000)*100*mul);
			}
			//System.out.print(this.population + ",");
		} else if(this.type == DESERT_WORLD) {
			int r = gen.nextInt(100);
			if(r < 20) { //20% 0
				this.population = 0;
			} else if(r < 25) { //5% 100,000,
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(100000)*mul);
			} else if(r < 60) { //35% 1,000,000,
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(1000000)*mul);
			} else if(r < 90) { //30% 10,000,000,
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(10000000)*mul);
			} else if(r < 99) { //9% 100,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(100000000)*mul);
			} else {			//1% 1,000,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)((long)gen.nextInt(1000000000)*mul);
			}
		} else if(this.type == JUNGLE_WORLD) {
			int r = gen.nextInt(100);
			if(r < 15) { //15% 0
				this.population = 0;
			} else if(r < 30) { //15% 100,000,
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(100000)*mul);
			} else if(r < 55) { //25% 1,000,000,
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(1000000)*mul);
			} else if(r < 90) { //35% 10,000,000,
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(10000000)*mul);
			} else if(r < 99) { //9% 100,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(100000000)*mul);
			} else { 			//1% 1,000,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)((long)gen.nextInt(1000000000)*mul);
			}
		} else if(this.type == OCEANIC_WORLD) {
			int r = gen.nextInt(100);
			if(r < 10) { //10% 0
				this.population = 0;
			} else if(r < 30) { //20% 100,000,
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(100000)*mul);
			} else if(r < 60) { //30% 1,000,000,
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(1000000)*mul);
			} else if(r < 90) { //30% 10,000,000,
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(10000000)*mul);
			} else if(r < 97) { //7% 100,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(100000000)*mul);
			} else if(r < 99) { //2% 1,000,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)((long)gen.nextInt(1000000000)*mul);
			} else {			//1% 10,000,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)((long)gen.nextInt(1000000000)*10*mul);
			}
		} else if(this.type == GAS_GIANT) {
			int s = gen.nextInt(100);
			if(s < 90) { 		//90% 0
				this.population = 0;
			} else if(s < 97) { //7% 100,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(100000)*mul);
			} else if(s < 99) { //2% 1,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)(gen.nextInt(1000000)*mul);
			} else { //1% 10,000,000
				double mul = Math.abs(gen.nextGaussian());
				this.population = (long)((long)gen.nextInt(10000000)*mul);
			}
		} else {
			int r = gen.nextInt(3);
			if(r == 2) {
				this.population = (long)((100000/this.orbitalRadius)*Math.abs(gen.nextGaussian()));
			}
		}
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		this.popStr = formatter.format(this.population);
		
	}
}
