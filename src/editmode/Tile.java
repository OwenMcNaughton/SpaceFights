package editmode;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.newdawn.slick.geom.Circle;

import Mains.Intpo;
import celestialbodies.Projectile;

public class Tile {

	public int floor;
	public static final int EMPTY = 0;
	public static final int PATH_N = 1;
	public static final int PATH_E = 2;
	public static final int WALL_0 = 7;
	public static final int WALL_1 = 8;
	public static final int WALL_2 = 9;
	
	public int node;
	public static final int CORE = 11;
	public static final int BROKEN_CORE = 12;
	public static final int WARP = 13;
	public static final int BROKEN_WARP = 14;
	public static final int STASIS_VACANT = 16;
	public static final int STASIS_OCCUPIED = 17;
	public static final int BROKEN_STASIS = 18;
	public static final int ENGINE = 19;
	public static final int BROKEN_ENGINE = 20;
	public static final int SHIELD_GEN = 21;
	public static final int BROKEN_SHIELD_GEN = 22;
	public static final int CHARGER = 23;
	public static final int BROKEN_CHARGER = 24;
	public static final int SOLAR = 25;
	public static final int BROKEN_SOLAR = 26;
	public static final int TRACTOR = 27;
	public static final int BROKEN_TRACTOR = 28;
	public static final int LIFE_SUPPORT = 29;
	public static final int BROKEN_LIFE_SUPPORT = 30;
	
	
	public static final int N_TERMINAL = 50;
	public static final int W_TERMINAL = 51;
	public static final int S_TERMINAL = 52;
	public static final int E_TERMINAL = 53;
	
	public static final int VC_DOOR = 70;
	public static final int VO_DOOR = 71;
	public static final int HC_DOOR = 72;
	public static final int HO_DOOR = 73;
	
	public static final int GIRDER_1_S = 140;
	public static final int GIRDER_1_W = 141;
	public static final int GIRDER_1_N = 142;
	public static final int GIRDER_1_E = 143;
	public static final int GIRDER_2_V = 144;
	public static final int GIRDER_2_H = 145;
	public static final int GIRDER_2_SE = 146;
	public static final int GIRDER_2_SW = 147;
	public static final int GIRDER_2_NE = 148;
	public static final int GIRDER_2_NW = 149;
	public static final int GIRDER_3_E = 150;
	public static final int GIRDER_3_W = 151;
	public static final int GIRDER_3_S = 152;
	public static final int GIRDER_3_N = 153;
	public static final int GIRDER_4 = 154;
	public static final int GIRDER_0 = 155;
	
	
	public static final int TITANIUM_DUMP_0 = 100;
	public static final int TITANIUM_DUMP_1 = 101;
	public static final int TITANIUM_DUMP_2 = 102;
	public static final int TITANIUM_DUMP_3 = 103;
	public static final int URANIUM_DUMP_0 = 110;
	public static final int URANIUM_DUMP_1 = 111;
	public static final int URANIUM_DUMP_2 = 112;
	public static final int URANIUM_DUMP_3 = 113;
	public static final int FUEL_DUMP_0 = 120;
	public static final int FUEL_DUMP_1 = 121;
	public static final int FUEL_DUMP_2 = 122;
	public static final int FUEL_DUMP_3 = 123;
	public static final int GOLD_DUMP_0 = 130;
	public static final int GOLD_DUMP_1 = 131;
	public static final int GOLD_DUMP_2 = 132;
	public static final int GOLD_DUMP_3 = 133;
	
	public Weapon weapon;
	public static final int RAILGUN_0_N = 300;
	public static final int BROKEN_RAILGUN_0_N = 301;
	public static final int RAILGUN_0_E = 302;
	public static final int BROKEN_RAILGUN_0_E = 303;
	public static final int RAILGUN_0_S = 304;
	public static final int BROKEN_RAILGUN_0_S = 305;
	public static final int RAILGUN_0_W = 306;
	public static final int BROKEN_RAILGUN_0_W = 307;
	public static final int LASER_0_N = 308;
	public static final int BROKEN_LASER_0_N = 309;
	public static final int LASER_0_E = 310;
	public static final int BROKEN_LASER_0_E = 311;
	public static final int LASER_0_S = 312;
	public static final int BROKEN_LASER_0_S = 313;
	public static final int LASER_0_W = 314;
	public static final int BROKEN_LASER_0_W = 315;
	
	public static final int CREW = 200;
	public static final int FIRE = 201;
	
	public static final int ERASER = 1337;
	
	public double rads;
	public Circle radEffectRadius;
	public boolean inRad;
	public boolean radsIncreasing;
	
	public int oxy;

	public boolean fire;
	public double fireStrength;
	public static final double fireStrengthMax = 1000;
	public static final double fireStrengthStart = 500;
	public boolean kindling;
	public int fireType;
	
	public Intpo uplink;
	public boolean uplinkFocus;
	public int uplinkStatus;
	public static final int UNLINKED = 0;
	public static final int NOT_IN_USE = 1;
	public static final int IN_USE = 2;
	public static final int WARNING = 3;
	
	public int nodeStatus;
	public static final int OFFLINE = 0;
	public static final int CONNECTED = 1;
	public static final int ACTIVE = 2;
	public static final int NODE_WARNING = 3;
	
	public int damage;
	public static final int BROKEN_CORE_DAMAGE = 10000;
	public static final int BROKEN_WARP_DAMAGE = 10000;
	public static final int BROKEN_ENGINE_DAMAGE = 10000;
	public static final int BROKEN_SHIELD_GEN_DAMAGE = 10000;
	public static final int BROKEN_STASIS_DAMAGE = 10000;
	public static final int BROKEN_CHARGER_DAMAGE = 10000;
	public static final int BROKEN_TRACTOR_DAMAGE = 10000;
	public static final int BROKEN_SOLAR_DAMAGE = 10000;
	public static final int BROKEN_LIFE_SUPPORT_DAMAGE = 10000;
	
	
	public int hackType;
	
	public double power;
	
	public boolean chargerWithRobot;
	
	public boolean imminentExplosion;
	public int imexIter;
	public Projectile imminentProjectile;
	
	public Circle doubleCircle;
	
	public Tile(int f, int n, double r, int o) {
		Random gen = new Random();
		
		this.floor = f;
		this.node = n;
		this.rads = r;
		this.oxy = o;
		this.fire = false;
		this.fireStrength = Tile.fireStrengthStart;
		this.kindling = false;
		
		this.fireType = gen.nextInt(5);
		this.hackType = gen.nextInt(3);
		
		this.radsIncreasing = false;
		
		this.radEffectRadius = null;
		
		this.inRad = false;
		
		this.uplink = null;
		
		this.uplinkStatus = Tile.UNLINKED;
		this.nodeStatus = Tile.OFFLINE;
		
		this.damage = 0;
		this.power = 0;
		
		this.chargerWithRobot = false;
		this.imminentExplosion = false;
		this.imexIter = -1;
	}
	
	public static boolean isTilePathable(Tile t, int i, int j) {
		switch(t.floor) {
		case Tile.WALL_0: return false;
		case Tile.WALL_1: return false;
		case Tile.WALL_2: return false;
		case Tile.EMPTY: return false;
		}
		
		boolean isNode = true;
		switch(t.node) {
		case -1: isNode = false; break;
		case Tile.N_TERMINAL:
		case Tile.W_TERMINAL: 
		case Tile.S_TERMINAL: 
		case Tile.E_TERMINAL: 	for(Crew c : Grid.crew) {
									if(c != null) {
										if(c.tilex == i && c.tiley == j && c.typing) {
											return false;
										}
									}
								} return true;
		case Tile.STASIS_VACANT: return true;
		case Tile.VO_DOOR: return true;
		case Tile.HO_DOOR: return true;
		case Tile.VC_DOOR: return true;
		case Tile.HC_DOOR: return true;
		case Tile.CHARGER: return true;
		}
		
		if(!isNode) {
			switch(t.floor) {
			case Tile.PATH_N: return true;
			case Tile.PATH_E: return true;
			}
		}
		
		return false;
	}
	
	public static boolean isPaintPathable(int p) {
		switch(p) {
		case Tile.N_TERMINAL: return true;
		case Tile.W_TERMINAL: return true;
		case Tile.S_TERMINAL: return true;
		case Tile.E_TERMINAL: return true;
		case Tile.STASIS_VACANT: return true;
		case Tile.VO_DOOR: return true;
		case Tile.HO_DOOR: return true;
		case Tile.VC_DOOR: return true;
		case Tile.HC_DOOR: return true;
		case Tile.PATH_N: return true;
		case Tile.PATH_E: return true;
		case Tile.CHARGER: return true;
		}
		
		return false;
	}
	
	public static boolean isTileRaddable(Tile t) {
		
		switch(t.floor) {
		case Tile.WALL_0: return false;
		case Tile.WALL_1: return false;
		case Tile.WALL_2: return false;
		case Tile.EMPTY: return false;
		}
		
		switch(t.node) {
		case Tile.CORE: return false;
		case Tile.BROKEN_CORE: return false;
		}

		return true;
	}
	
	public static boolean isTileTerminal(Tile t) {
		switch(t.node) {
			case Tile.N_TERMINAL: return true;
			case Tile.W_TERMINAL: return true;
			case Tile.S_TERMINAL: return true;
			case Tile.E_TERMINAL: return true;
		}
		
		return false;
	}
	
	public static boolean isTileRemoteNode(Tile t) {
		switch(t.node) {
		case Tile.CORE: return true;
		case Tile.BROKEN_CORE: return true;
		case Tile.WARP: return true;
		case Tile.BROKEN_WARP: return true;
		case Tile.ENGINE: return true;
		case Tile.BROKEN_ENGINE: return true;
		case Tile.SHIELD_GEN: return true;
		case Tile.BROKEN_SHIELD_GEN: return true;
		case Tile.TRACTOR: return true;
		case Tile.BROKEN_TRACTOR: return true;
		case Tile.LIFE_SUPPORT: return true;
		case Tile.BROKEN_LIFE_SUPPORT: return true;
		case Tile.RAILGUN_0_N: return true;
		case Tile.BROKEN_RAILGUN_0_N: return true;
		case Tile.RAILGUN_0_E: return true;
		case Tile.BROKEN_RAILGUN_0_E: return true;
		case Tile.RAILGUN_0_S: return true;
		case Tile.BROKEN_RAILGUN_0_S: return true;
		case Tile.RAILGUN_0_W: return true;
		case Tile.BROKEN_RAILGUN_0_W: return true;
		case Tile.LASER_0_N: return true;
		case Tile.BROKEN_LASER_0_N: return true;
		case Tile.LASER_0_E: return true;
		case Tile.BROKEN_LASER_0_E: return true;
		case Tile.LASER_0_S: return true;
		case Tile.BROKEN_LASER_0_S: return true;
		case Tile.LASER_0_W: return true;
		case Tile.BROKEN_LASER_0_W: return true;
		}
		
		return false;
	}
	
	public void convertNodeToBroken() {
		switch(this.node) {
		case Tile.CORE: this.node = Tile.BROKEN_CORE; this.damage = BROKEN_CORE_DAMAGE; break;
		case Tile.WARP: this.node = Tile.BROKEN_WARP; this.damage = BROKEN_WARP_DAMAGE; break;
		case Tile.ENGINE: this.node = Tile.BROKEN_ENGINE; this.damage = BROKEN_ENGINE_DAMAGE; break;
		case Tile.SHIELD_GEN: this.node = Tile.BROKEN_SHIELD_GEN; this.damage = BROKEN_SHIELD_GEN_DAMAGE; break;
		case Tile.STASIS_VACANT: this.node = Tile.BROKEN_STASIS; this.damage = BROKEN_STASIS_DAMAGE; break;
		case Tile.STASIS_OCCUPIED: this.node = Tile.BROKEN_STASIS; this.damage = BROKEN_STASIS_DAMAGE; break;
		case Tile.CHARGER: this.node = Tile.BROKEN_CHARGER; this.damage = BROKEN_CHARGER_DAMAGE; break;
		case Tile.TRACTOR: this.node = Tile.BROKEN_TRACTOR; this.damage = BROKEN_TRACTOR_DAMAGE; break;
		case Tile.SOLAR: this.node = Tile.BROKEN_SOLAR; this.damage = BROKEN_SOLAR_DAMAGE; break;
		case Tile.LIFE_SUPPORT: this.node = Tile.BROKEN_LIFE_SUPPORT; this.damage = BROKEN_LIFE_SUPPORT_DAMAGE; break;
		}
	}
	
	public void convertNodeToFixed() {
		this.damage = 0;
		switch(this.node) {
		case Tile.BROKEN_CORE: this.node = Tile.CORE; this.nodeStatus = Tile.OFFLINE; break;
		case Tile.BROKEN_WARP: this.node = Tile.WARP; this.nodeStatus = Tile.OFFLINE; break;
		case Tile.BROKEN_ENGINE: this.node = Tile.ENGINE; this.nodeStatus = Tile.OFFLINE; break;
		case Tile.BROKEN_SHIELD_GEN: this.node = Tile.SHIELD_GEN; this.nodeStatus = Tile.OFFLINE; break;
		case Tile.BROKEN_STASIS: this.node = Tile.STASIS_VACANT; this.nodeStatus = Tile.OFFLINE; break;
		case Tile.BROKEN_CHARGER: this.node = Tile.CHARGER; this.nodeStatus = Tile.OFFLINE; break;
		case Tile.BROKEN_SOLAR: this.node = Tile.SOLAR; this.nodeStatus = Tile.OFFLINE; break;
		case Tile.BROKEN_TRACTOR: this.node = Tile.TRACTOR; this.nodeStatus = Tile.OFFLINE; break;
		case Tile.BROKEN_LIFE_SUPPORT: this.node = Tile.LIFE_SUPPORT; this.nodeStatus = Tile.OFFLINE; break;
		}
	}
	
	public boolean isNodeBroken() {
		
		switch(this.node) {
		case Tile.BROKEN_CORE: return true;
		case Tile.BROKEN_ENGINE: return true;
		case Tile.BROKEN_WARP: return true;
		case Tile.BROKEN_SHIELD_GEN: return true;
		case Tile.BROKEN_STASIS: return true;
		case Tile.BROKEN_CHARGER: return true;
		case Tile.BROKEN_SOLAR: return true;
		case Tile.BROKEN_TRACTOR: return true;
		case Tile.BROKEN_LIFE_SUPPORT: return true;
		}
		
		return false;
	}
	
	public static int isTileCrewed(int x, int y, ArrayList<Crew> crew) {
		for(int i = 0; i != crew.size(); i ++) {
			if(crew.get(i) != null) {
				if(crew.get(i).tilex == x && crew.get(i).tiley == y) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public static double theoreticalPower(Tile t) {
		switch(t.node) {
		case Tile.CORE: return 100;
		case Tile.SOLAR: return 10;
		}
		
		return 0;
	}
	
	public static double grossPower(Tile t) {
		switch(t.node) {
		case Tile.CORE: 	if(t.nodeStatus == Tile.ACTIVE) {
								return 100;
							} break;
		case Tile.SOLAR: return 10;
		}
		
		return 0;
	}
	
	public static double netPower(Tile t) {
		switch(t.node) {
		case Tile.ENGINE: return -30;
		case Tile.WARP: return -50;			
		case Tile.STASIS_OCCUPIED: return -20;
		case Tile.SHIELD_GEN: return -100;		
		case Tile.CHARGER: return -20;
		case Tile.TRACTOR: return -40;
		case Tile.LIFE_SUPPORT: return -80;
		}
		
		return 0;
	}

	public static boolean isTileWeapon(Tile tile) {
		switch(tile.node) {
		case Tile.RAILGUN_0_N: return true;
		case Tile.BROKEN_RAILGUN_0_N: return true;
		case Tile.RAILGUN_0_E: return true;
		case Tile.BROKEN_RAILGUN_0_E: return true;
		case Tile.RAILGUN_0_S: return true;
		case Tile.BROKEN_RAILGUN_0_S: return true;
		case Tile.RAILGUN_0_W: return true;
		case Tile.BROKEN_RAILGUN_0_W: return true;
		case Tile.LASER_0_N: return true;
		case Tile.BROKEN_LASER_0_N: return true;
		case Tile.LASER_0_E: return true;
		case Tile.BROKEN_LASER_0_E: return true;
		case Tile.LASER_0_S: return true;
		case Tile.BROKEN_LASER_0_S: return true;
		case Tile.LASER_0_W: return true;
		case Tile.BROKEN_LASER_0_W: return true;
		}
		return false;
	}
	
	public static boolean isTileRailgun(Tile tile) {
		switch(tile.node) {
		case Tile.RAILGUN_0_N: return true;
		case Tile.BROKEN_RAILGUN_0_N: return true;
		case Tile.RAILGUN_0_E: return true;
		case Tile.BROKEN_RAILGUN_0_E: return true;
		case Tile.RAILGUN_0_S: return true;
		case Tile.BROKEN_RAILGUN_0_S: return true;
		case Tile.RAILGUN_0_W: return true;
		case Tile.BROKEN_RAILGUN_0_W: return true;
		}
		return false;
	}
	
	public static boolean isTileLaser(Tile tile) {
		switch(tile.node) {
		case Tile.LASER_0_N: return true;
		case Tile.BROKEN_LASER_0_N: return true;
		case Tile.LASER_0_E: return true;
		case Tile.BROKEN_LASER_0_E: return true;
		case Tile.LASER_0_S: return true;
		case Tile.BROKEN_LASER_0_S: return true;
		case Tile.LASER_0_W: return true;
		case Tile.BROKEN_LASER_0_W: return true;
		}
		return false;
	}
	
	public static boolean isRailgunValid(Tile t, int i, int j) {
		boolean valid = true;
		
		switch(t.weapon.orientation) {
		case 0: if(i + 1 < Grid.columns) {
					if(Grid.tiles[i+1][j].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(i - 1 >= 0) {
					if(Grid.tiles[i-1][j].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(j + 1 < Grid.rows) {
					if(Grid.tiles[i][j+1].floor != Tile.WALL_0) {
						valid = false;
					}
				}
				if(j - 1 >= 0) {
					if(Grid.tiles[i][j-1].floor != Tile.EMPTY) {
						valid = false;
					}
				} break;
		case 1: if(i + 1 < Grid.columns) {
					if(Grid.tiles[i+1][j].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(i - 1 >= 0) {
					if(Grid.tiles[i-1][j].floor != Tile.WALL_0) {
						valid = false;
					}
				}
				if(j + 1 < Grid.rows) {
					if(Grid.tiles[i][j+1].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(j - 1 >= 0) {
					if(Grid.tiles[i][j-1].floor != Tile.EMPTY) {
						valid = false;
					}
				} break;
		case 2: if(i + 1 < Grid.columns) {
					if(Grid.tiles[i+1][j].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(i - 1 >= 0) {
					if(Grid.tiles[i-1][j].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(j + 1 < Grid.rows) {
					if(Grid.tiles[i][j+1].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(j - 1 >= 0) {
					if(Grid.tiles[i][j-1].floor != Tile.WALL_0) {
						valid = false;
					}
				} break;
		case 3: if(i + 1 < Grid.columns) {
					if(Grid.tiles[i+1][j].floor != Tile.WALL_0) {
						valid = false;
					}
				}
				if(i - 1 >= 0) {
					if(Grid.tiles[i-1][j].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(j + 1 < Grid.rows) {
					if(Grid.tiles[i][j+1].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(j - 1 >= 0) {
					if(Grid.tiles[i][j-1].floor != Tile.EMPTY) {
						valid = false;
					}
				} break;
		}
		return valid;
	}

	public static boolean isLaserValid(Tile t, int i, int j) {
		boolean valid = true;
		
		switch(t.weapon.orientation) {
		case 0: if(i + 1 < Grid.columns) {
					if(Grid.tiles[i+1][j].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(i - 1 >= 0) {
					if(Grid.tiles[i-1][j].floor != Tile.WALL_0) {
						valid = false;
					}
				}
				if(j + 1 < Grid.rows) {
					if(Grid.tiles[i][j+1].floor != Tile.WALL_0) {
						valid = false;
					}
				}
				if(j - 1 >= 0) {
					if(Grid.tiles[i][j-1].floor != Tile.EMPTY) {
						valid = false;
					}
				} break;
		case 1: if(i + 1 < Grid.columns) {
					if(Grid.tiles[i+1][j].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(i - 1 >= 0) {
					if(Grid.tiles[i-1][j].floor != Tile.WALL_0) {
						valid = false;
					}
				}
				if(j + 1 < Grid.rows) {
					if(Grid.tiles[i][j+1].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(j - 1 >= 0) {
					if(Grid.tiles[i][j-1].floor != Tile.WALL_0) {
						valid = false;
					}
				} break;
		case 2: if(i + 1 < Grid.columns) {
					if(Grid.tiles[i+1][j].floor != Tile.WALL_0) {
						valid = false;
					}
				}
				if(i - 1 >= 0) {
					if(Grid.tiles[i-1][j].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(j + 1 < Grid.rows) {
					if(Grid.tiles[i][j+1].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(j - 1 >= 0) {
					if(Grid.tiles[i][j-1].floor != Tile.WALL_0) {
						valid = false;
					}
				} break;
		case 3: if(i + 1 < Grid.columns) {
					if(Grid.tiles[i+1][j].floor != Tile.WALL_0) {
						valid = false;
					}
				}
				if(i - 1 >= 0) {
					if(Grid.tiles[i-1][j].floor != Tile.EMPTY) {
						valid = false;
					}
				}
				if(j + 1 < Grid.rows) {
					if(Grid.tiles[i][j+1].floor != Tile.WALL_0) {
						valid = false;
					}
				}
				if(j - 1 >= 0) {
					if(Grid.tiles[i][j-1].floor != Tile.EMPTY) {
						valid = false;
					}
				} break;
		}
		
		
		
		return valid;
	}
	
	public static String toString(int i, int j) {
		int a = Grid.tiles[i][j].floor;
		
		String uplinkx = "null";
		String uplinky = "null";
		if(Grid.tiles[i][j].uplink != null) {
			uplinkx = "" + Grid.tiles[i][j].uplink.x;
			uplinky = "" + Grid.tiles[i][j].uplink.y;
		}
		
		String weapon = "null";
		if(Grid.tiles[i][j].weapon != null) {
			weapon = Grid.tiles[i][j].weapon.tilex + "'" + Grid.tiles[i][j].weapon.tiley + "'" +
					Grid.tiles[i][j].weapon.type + "'" + Grid.tiles[i][j].weapon.orientation;
		}
		
		
		String s = "" + Grid.tiles[i][j].floor + "," + Grid.tiles[i][j].node + "," + uplinkx + "," + 
				uplinky + "," + Grid.tiles[i][j].uplinkStatus + "," + Grid.tiles[i][j].nodeStatus + "," +
				weapon + ",";
				
		
		return s;
	}
	
	public void load(String arg) {
		String[] splits = arg.split(",");
		
		Scanner scan = new Scanner(splits[0]);
		this.floor = scan.nextInt();
		scan.close();
		
		scan = new Scanner(splits[1]);
		this.node = scan.nextInt();
		scan.close();
		
		if(!splits[2].equals("null")) {
			scan = new Scanner(splits[2]);
			int d = scan.nextInt();
			scan.close();
			scan = new Scanner(splits[3]);
			int e = scan.nextInt();
			this.uplink = new Intpo(d, e);
			scan.close();
		}
		
		scan = new Scanner(splits[4]);
		this.uplinkStatus = scan.nextInt();
		scan.close();
		
		scan = new Scanner(splits[5]);
		this.nodeStatus = scan.nextInt();
		scan.close();
		
		if(!splits[6].equals("null")) {
			int tilex = 0; int tiley = 0; int type = 0; int o = 0;
			String[] splitties = splits[6].split("'");
			scan = new Scanner(splitties[0]);
			tilex = scan.nextInt();
			scan.close();
			scan = new Scanner(splitties[1]);
			tiley = scan.nextInt();
			scan.close();
			scan = new Scanner(splitties[2]);
			type = scan.nextInt();
			scan.close();
			scan = new Scanner(splitties[3]);
			o = scan.nextInt();
			scan.close();
			this.weapon = new Weapon(tilex, tiley, type, o);
		}
		
		
	}
	
	public static int[] paintPrice(Tile t) {
		int tit = 0;
		int ura = 0;
		
		switch(t.floor) {
		case EMPTY:	 tit = 0; ura = 0; break;
		case PATH_N: tit = 5; ura = 0; break;
		case PATH_E: tit = 10; ura = 0; break;
		case WALL_0: tit = 5; ura = 0; break;
		case WALL_1: tit = 10; ura = 0; break;
		case WALL_2: tit = 20; ura = 0; break;
		}
		
		switch(t.node) {
		case CORE: 				tit += 10; ura += 30; break;
		case BROKEN_CORE:		tit += 5; ura += 15; break;
		case WARP:				tit += 15; ura += 20; break;
		case BROKEN_WARP:		tit += 7; ura += 10; break;
		case STASIS_VACANT:		tit += 20; ura += 0; break;
		case STASIS_OCCUPIED:	tit += 20; ura += 0; break;
		case BROKEN_STASIS:		tit += 10; ura += 0; break;
		case ENGINE:			tit += 50; ura += 0; break;
		case BROKEN_ENGINE:		tit += 25; ura += 0; break;
		case SHIELD_GEN:		tit += 50; ura += 10; break;
		case BROKEN_SHIELD_GEN: tit += 25; ura += 5; break;
		case CHARGER:			tit += 30; ura += 0; break;
		case BROKEN_CHARGER:	tit += 15; ura += 0; break;
		case SOLAR:				tit += 20; ura += 0; break;
		case BROKEN_SOLAR:		tit += 20; ura += 0; break;
		case TRACTOR:			tit += 10; ura += 20; break;
		case BROKEN_TRACTOR:	tit += 5; ura += 10; break;
		case LIFE_SUPPORT:		tit += 30; ura += 10; break;
		case BROKEN_LIFE_SUPPORT:tit += 30; ura += 10; break;
		
		
		
		case N_TERMINAL:		tit += 25; ura += 0; break;
		case W_TERMINAL:		tit += 25; ura += 0; break;
		case S_TERMINAL:		tit += 25; ura += 0; break;
		case E_TERMINAL:		tit += 25; ura += 0; break;
		
		case VC_DOOR:			tit += 50; ura += 0; break;
		case VO_DOOR:			tit += 50; ura += 0; break;
		case HC_DOOR:			tit += 50; ura += 0; break;
		case HO_DOOR:			tit += 50; ura += 0; break;
		
		case TITANIUM_DUMP_0:	tit += 20; ura += 0; break;
		case TITANIUM_DUMP_1:	tit += 20; ura += 0; break;
		case TITANIUM_DUMP_2:	tit += 20; ura += 0; break;
		case TITANIUM_DUMP_3:	tit += 20; ura += 0; break;
		case URANIUM_DUMP_0:	tit += 20; ura += 0; break;
		case URANIUM_DUMP_1:	tit += 20; ura += 0; break;
		case URANIUM_DUMP_2:	tit += 20; ura += 0; break;
		case URANIUM_DUMP_3:	tit += 20; ura += 0; break;
		case FUEL_DUMP_0:		tit += 20; ura += 0; break;
		case FUEL_DUMP_1:		tit += 20; ura += 0; break;
		case FUEL_DUMP_2:		tit += 20; ura += 0; break;
		case FUEL_DUMP_3:		tit += 20; ura += 0; break;
		case GOLD_DUMP_0:		tit += 20; ura += 0; break;
		case GOLD_DUMP_1:		tit += 20; ura += 0; break;
		case GOLD_DUMP_2:		tit += 20; ura += 0; break;
		case GOLD_DUMP_3:		tit += 20; ura += 0; break;
		
		case RAILGUN_0_N:		tit += 100; ura += 0; break;
		case BROKEN_RAILGUN_0_N:tit += 50; ura += 0; break;
		case RAILGUN_0_E:		tit += 100; ura += 0; break;
		case BROKEN_RAILGUN_0_E:tit += 50; ura += 0; break;
		case RAILGUN_0_S:		tit += 100; ura += 0; break;
		case BROKEN_RAILGUN_0_S:tit += 50; ura += 0; break;
		case RAILGUN_0_W:		tit += 100; ura += 0; break;
		case BROKEN_RAILGUN_0_W:tit += 50; ura += 0; break;
		case LASER_0_N:			tit += 20; ura += 50; break;
		case BROKEN_LASER_0_N:	tit += 10; ura += 25; break;
		case LASER_0_E:			tit += 20; ura += 50; break;
		case BROKEN_LASER_0_E:	tit += 10; ura += 25; break;
		case LASER_0_S:			tit += 20; ura += 50; break;
		case BROKEN_LASER_0_S:	tit += 10; ura += 25; break;
		case LASER_0_W:			tit += 20; ura += 50; break;
		case BROKEN_LASER_0_W:	tit += 10; ura += 25; break;
		
		
		}
		
		if(Tile.isTileGirder(t)) {
			tit += 1; ura += 0;
		}
		
		return new int[] {tit, ura};
	}
	
	public static boolean isTileGirder(Tile t) {
		if(t.floor >= Tile.GIRDER_1_S && t.floor <= Tile.GIRDER_0) {
			return true;
		}
		
		return false;
	}
	
	public static void gridify() {
		for(int i = 0; i != Grid.columns; i++) {
			for(int j = 0; j != Grid.rows; j++) {
				if(Tile.isTileGirder(Grid.tiles[i][j])) {
					boolean n = false;
					boolean e = false;
					boolean w = false;
					boolean s = false;
					
					if(i + 1 < Grid.columns) {
						if(Grid.tiles[i+1][j].floor != Tile.EMPTY) {
							e = true;
						}
					}
					
					if(i - 1 >= 0) {
						if(Grid.tiles[i-1][j].floor != Tile.EMPTY) {
							w = true;
						}
					}
					
					if(j - 1 >= 0) {
						if(Grid.tiles[i][j-1].floor != Tile.EMPTY) {
							n = true;
						}
					}
					
					if(j + 1 < Grid.rows) {
						if(Grid.tiles[i][j+1].floor != Tile.EMPTY) {
							s = true;
						}
					}
					
					if(n && e && w && s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_4;
					}
					if(n && e && w && !s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_3_N;
					}
					if(n && e && !w && s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_3_E;
					}
					if(n && !e && w && s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_3_W;
					}
					if(!n && e && w && s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_3_S;
					}
					if(n && e && !w && !s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_2_NE;
					}
					if(n && !e && w && !s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_2_NW;
					}
					if(!n && e && w && !s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_2_H;
					}
					if(n && !e && !w && s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_2_V;
					}
					if(!n && e && !w && s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_2_SE;
					}
					if(!n && !e && w && s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_2_SW;
					}
					if(n && e && w && s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_4;
					}
					if(n && !e && !w && !s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_1_N;
					}
					if(!n && e && !w && !s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_1_E;
					}
					if(!n && !e && w && !s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_1_W;
					}
					if(!n && !e && !w && s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_1_S;
					}
					if(!n && !e && !w && !s) {
						Grid.tiles[i][j].floor = Tile.GIRDER_0;
					}
				}
			}
		}
	}
	
	public static boolean isPaintFloor(int p) {
		if(p <= 10 || p >= GIRDER_1_S && p <= GIRDER_0) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean isTilePressurized(Tile t) {
		switch(t.floor) {
		case WALL_0: return false;
		case EMPTY: return false;
		}
		
		if(isTileGirder(t)) {
			return false;
		}
		
		switch(t.node) {
		case HC_DOOR: return false;
		case VC_DOOR: return false;
		}
		
		return true;
	}
	
	public static boolean isTilePoweredNode(Tile t) {
		switch(t.node) {
		case CORE: return true;
		case BROKEN_CORE: return true;
		case WARP: return true;
		case BROKEN_WARP: return true;
		case STASIS_OCCUPIED: return true;
		case BROKEN_STASIS: return true;
		case ENGINE: return true;
		case BROKEN_ENGINE: return true;
		case SHIELD_GEN: return true;
		case BROKEN_SHIELD_GEN: return true;
		case CHARGER: return true;
		case BROKEN_CHARGER: return true;
		case SOLAR: return true;
		case BROKEN_SOLAR: return true;
		case TRACTOR: return true;
		case BROKEN_TRACTOR: return true;
		case LIFE_SUPPORT: return true;
		case BROKEN_LIFE_SUPPORT: return true;

		case VC_DOOR: return true;
		case VO_DOOR: return true;
		case HC_DOOR: return true;
		case HO_DOOR: return true;
		
		case RAILGUN_0_N: return true;
		case BROKEN_RAILGUN_0_N: return true;
		case RAILGUN_0_E: return true;
		case BROKEN_RAILGUN_0_E: return true;
		case RAILGUN_0_S: return true;
		case BROKEN_RAILGUN_0_S: return true;
		case RAILGUN_0_W: return true;
		case BROKEN_RAILGUN_0_W: return true;
		case LASER_0_N: return true;
		case BROKEN_LASER_0_N: return true;
		case LASER_0_E: return true;
		case BROKEN_LASER_0_E: return true;
		case LASER_0_S: return true;
		case BROKEN_LASER_0_S: return true;
		case LASER_0_W: return true;
		case BROKEN_LASER_0_W: return true;
		}
		
		return false;
		
	}
	
	public static int[] spritifyFloor(Tile t, boolean editMode) {
		int tileX = -1;
		int tileY = -1;
		
		switch(t.floor) {
		case Tile.EMPTY:if(editMode) {
							tileX = 9; tileY = 0; break;
						} else {
							tileX = 15; tileY = 15; break;
						}
		case Tile.PATH_N: tileX = 3; tileY = 0; break;
		case Tile.PATH_E: tileX = 4; tileY = 0; break;
		case Tile.WALL_0: tileX = 0; tileY = 0; break;
		case Tile.WALL_1: tileX = 1; tileY = 0; break;
		case Tile.WALL_2: tileX = 2; tileY = 0; break;
		
		case Tile.GIRDER_1_S:		tileX = 0; tileY = 9; break;
		case Tile.GIRDER_1_W:		tileX = 1; tileY = 9; break;
		case Tile.GIRDER_1_N:		tileX = 2; tileY = 9; break;
		case Tile.GIRDER_1_E:		tileX = 3; tileY = 9; break;
		case Tile.GIRDER_2_V:		tileX = 4; tileY = 9; break;
		case Tile.GIRDER_2_H:		tileX = 5; tileY = 9; break;
		case Tile.GIRDER_2_SE:		tileX = 6; tileY = 9; break;
		case Tile.GIRDER_2_SW:		tileX = 7; tileY = 9; break;
		case Tile.GIRDER_2_NE:		tileX = 8; tileY = 9; break;
		case Tile.GIRDER_2_NW:		tileX = 9; tileY = 9; break;
		case Tile.GIRDER_3_E:		tileX = 10; tileY = 9; break;
		case Tile.GIRDER_3_W:		tileX = 11; tileY = 9; break;
		case Tile.GIRDER_3_S:		tileX = 12; tileY = 9; break;
		case Tile.GIRDER_3_N:		tileX = 13; tileY = 9; break;
		case Tile.GIRDER_4:			tileX = 14; tileY = 9; break;
		case Tile.GIRDER_0:			tileX = 15; tileY = 9; break;
		}
		
		int[] r = {tileX, tileY};
		
		return r;
		
	}
	
	public static int[] spritifyNode(Tile t, boolean editMode) {
		int tileX = -1;
		int tileY = -1;
		
		boolean isNode = true;
		switch(t.node) {
		case Tile.CORE: 			tileX = 0; tileY = 2; break;
		case Tile.BROKEN_CORE:		tileX = 1; tileY = 2; break;
		case Tile.ENGINE: 			tileX = 2; tileY = 2; break;
		case Tile.BROKEN_ENGINE: 	tileX = 3; tileY = 2; break;
		case Tile.WARP: 			tileX = 4; tileY = 2; break;
		case Tile.BROKEN_WARP: 		tileX = 5; tileY = 2; break;
		case Tile.STASIS_OCCUPIED: 	tileX = 6; tileY = 2; break;
		case Tile.STASIS_VACANT: 	tileX = 7; tileY = 2; break;
		case Tile.BROKEN_STASIS: 	tileX = 8; tileY = 2; break;
		case Tile.SHIELD_GEN: 		tileX = 9; tileY = 2; break;
		case Tile.BROKEN_SHIELD_GEN:tileX = 10; tileY = 2; break;
		case Tile.CHARGER: 			tileX = 11; tileY = 2; break;
		case Tile.BROKEN_CHARGER: 	tileX = 15; tileY = 2; break;
		case Tile.SOLAR:			tileX = 0; tileY = 3; break;
		case Tile.BROKEN_SOLAR:		tileX = 1; tileY = 3; break;
		case Tile.TRACTOR:			tileX = 2; tileY = 3; break;
		case Tile.BROKEN_TRACTOR:	tileX = 3; tileY = 3; break;
		case Tile.LIFE_SUPPORT:		tileX = 0; tileY = 10; break;
		case Tile.BROKEN_LIFE_SUPPORT:tileX = 1; tileY = 10; break;
		
		case Tile.N_TERMINAL:	tileX = 0; tileY = 1; break;
		case Tile.W_TERMINAL:	tileX = 2; tileY = 1; break;
		case Tile.S_TERMINAL:	tileX = 1; tileY = 1; break;
		case Tile.E_TERMINAL:	tileX = 3; tileY = 1; break;
		
		case Tile.VC_DOOR:			tileX = 6; tileY = 0; break;
		case Tile.VO_DOOR:			tileX = 8; tileY = 0; break;
		case Tile.HC_DOOR:			tileX = 5; tileY = 0; break;
		case Tile.HO_DOOR:			tileX = 7; tileY = 0; break;
		
		case Tile.TITANIUM_DUMP_0:  tileX = 0; tileY = 8; break;
		case Tile.TITANIUM_DUMP_1:  tileX = 1; tileY = 8; break;
		case Tile.TITANIUM_DUMP_2:  tileX = 2; tileY = 8; break;
		case Tile.TITANIUM_DUMP_3:  tileX = 3; tileY = 8; break;
		case Tile.URANIUM_DUMP_0:  tileX = 4; tileY = 8; break;
		case Tile.URANIUM_DUMP_1:  tileX = 5; tileY = 8; break;
		case Tile.URANIUM_DUMP_2:  tileX = 6; tileY = 8; break;
		case Tile.URANIUM_DUMP_3:  tileX = 7; tileY = 8; break;
		case Tile.FUEL_DUMP_0:  tileX = 8; tileY = 8; break;
		case Tile.FUEL_DUMP_1:  tileX = 9; tileY = 8; break;
		case Tile.FUEL_DUMP_2:  tileX = 10; tileY = 8; break;
		case Tile.FUEL_DUMP_3:  tileX = 11; tileY = 8; break;
		case Tile.GOLD_DUMP_0:  tileX = 12; tileY = 8; break;
		case Tile.GOLD_DUMP_1:  tileX = 13; tileY = 8; break;
		case Tile.GOLD_DUMP_2:  tileX = 14; tileY = 8; break;
		case Tile.GOLD_DUMP_3:  tileX = 15; tileY = 8; break;
		
		case Tile.RAILGUN_0_N:		tileX = 0; tileY = 7; break;
		case Tile.RAILGUN_0_E:		tileX = 1; tileY = 7; break;
		case Tile.RAILGUN_0_S:		tileX = 2; tileY = 7; break;
		case Tile.RAILGUN_0_W:		tileX = 3; tileY = 7; break;
		
		case Tile.LASER_0_N:		tileX = 4; tileY = 7; break;
		case Tile.LASER_0_E:		tileX = 5; tileY = 7; break;
		case Tile.LASER_0_S:		tileX = 6; tileY = 7; break;
		case Tile.LASER_0_W:		tileX = 7; tileY = 7; break;
	
		default: isNode = false; break;
		}
		
		int[] r = {tileX, tileY, -1};
		if(isNode) {
			r[2] = 1;
		} else {
			r[2] = 0;
		}
		
		return r;
	}
	
}
