package Mains;

import java.util.ArrayList;

import editmode.Grid;
import editmode.Tile;

public class MemPoint {

	public Intpo here;
	public MemPoint from;
	public int cost;
	
	public MemPoint(Intpo i) {
		this.here = i;
		this.cost = Integer.MAX_VALUE;
	}
	
	public ArrayList<MemPoint> adjacents(ArrayList<MemPoint> closed) {
		ArrayList<MemPoint> r = new ArrayList<>();
		
		if(this.here.x > 0) {
			if(Tile.isTilePathable(Grid.tiles[this.here.x-1][this.here.y], this.here.x-1, this.here.y)) {
				r.add(new MemPoint(new Intpo(this.here.x-1, this.here.y)));
			}
		}
		
		if(this.here.x < Grid.columns-1) {
			if(Tile.isTilePathable(Grid.tiles[this.here.x+1][this.here.y], this.here.x+1, this.here.y)) {
				r.add(new MemPoint(new Intpo(this.here.x+1, this.here.y)));
			}
		}
		
		if(this.here.y > 0) {
			if(Tile.isTilePathable(Grid.tiles[this.here.x][this.here.y-1], this.here.x, this.here.y-1)) {
				r.add(new MemPoint(new Intpo(this.here.x, this.here.y-1)));
				
			}
		}
		
		if(this.here.y < Grid.rows-1) {
			if(Tile.isTilePathable(Grid.tiles[this.here.x][this.here.y+1], this.here.x, this.here.y+1)) {
				r.add(new MemPoint(new Intpo(this.here.x, this.here.y+1)));
			}
		}
		
		for(MemPoint c : closed) {
			int i = 0;
			if(i+0 < r.size()) {
				if(r.get(0).here.x == c.here.x && r.get(0).here.y == c.here.y) {
					r.remove(i);
					i--;
				}
			}
			if(i+1 < r.size()) {
				if(r.get(i+1).here.x == c.here.x && r.get(i+1).here.y == c.here.y) {
					r.remove(i+1);
					i--;
				}
			}
			if(i+2 < r.size()) {
				if(r.get(i+2).here.x == c.here.x && r.get(i+2).here.y == c.here.y) {
					r.remove(i+2);
					i--;
				}
			}
			if(i+3 < r.size()) {
				if(r.get(i+3).here.x == c.here.x && r.get(i+3).here.y == c.here.y) {
					r.remove(i+3);
					i--;
				}
			}
		}
		
		return r;
	}
	
}
