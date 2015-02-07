package celestialbodies;

import java.awt.Point;
import java.util.Random;


public class Star {

	public int type;
	public static final int ORANGE = 0;
	public static final int RED = 1;
	public static final int BLUE = 2;
	public static final int YELLOW = 3;
	
	public Point spriteCoords;
	
	public String name;
	
	public double locx;
	public double locy;
	
	public double mass;
	public double temperature;
	
	public Star(double x, double y) {
		this.locx = x;
		this.locy = y;
		
		Random gen = new Random();
		this.type = gen.nextInt(4);
		
		this.mass = (this.type*100) + (gen.nextInt(200)-100);
		this.temperature = this.mass/gen.nextInt(10);
		
		spriteCoords = new Point();
		
		this.spritify();
		
		this.nameify();
	}
	
	private void spritify() {
		switch(this.type) {
		case ORANGE: this.spriteCoords.x = 0; this.spriteCoords.y = 0; break;
		case RED: this.spriteCoords.x = 1; this.spriteCoords.y = 0; break;
		case BLUE: this.spriteCoords.x = 0; this.spriteCoords.y = 1; break;
		case YELLOW: this.spriteCoords.x = 1; this.spriteCoords.y = 1; break;
		}
	}
	
	private void nameify() {
		String s = "aaaeeeiioou";
		String[] vowels = s.split("");
		s = "bbcccddffgghjklllmmmmmnnnpppqrrrrrsssssttttttvvvvwwxxyzzz";
		String[] consons = s.split("");
		s = "Mal,Jal,Kal,Pan,Par,Pal,Kar,Kan,Kos,Los,Zal,Zan,Zant,Faas,Raas,Klaas,Yom,Yong,Tash,Pash,"
				+ "Was,Pas,Qar,Apos,Apas,Opras,Uro,Ara,Ero,Lon,Gon,Hon,Nos,Dhal,Xix,Sett,Epres,Ion,"
				+ "Oras,Shil,Kil,Gil,Porus,Ahe,Hoga,Un,Ei,Oes,Fes,Bega,Veinus,Pradus,Konus,Erys,Oh";
		String[] smalls = s.split(",");
		
		String n = "";
		Random gen = new Random();
		int a = gen.nextInt(2)+2;
		for(int i = 0; i != a; i++) {
			String syllable = "";
			int b = gen.nextInt(10);
			if(b < 6) {
				int f = gen.nextInt(4);
				if(f == 0) {
					syllable = vowels[gen.nextInt(vowels.length-1)];
					syllable += consons[gen.nextInt(consons.length-1)];
				} else {
					syllable = consons[gen.nextInt(consons.length-1)];
					syllable += vowels[gen.nextInt(vowels.length-1)];
				}
			}
			else if(b < 8) {
				syllable = consons[gen.nextInt(consons.length-1)];
				syllable += vowels[gen.nextInt(vowels.length-1)];
				syllable += consons[gen.nextInt(consons.length-1)]; 
			} else {	
				syllable = vowels[gen.nextInt(vowels.length-1)];
				syllable += consons[gen.nextInt(consons.length-1)];
				syllable += consons[gen.nextInt(consons.length-1)];
				syllable += vowels[gen.nextInt(vowels.length-1)];
			}
			n += syllable;
		}
		
		this.name = n.substring(0, 1).toUpperCase() + n.substring(1);
		
	}
	
}
