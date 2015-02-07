package Mains;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import editmode.Crew;
import editmode.Grid;
import editmode.Ship;
import editmode.Tile;

public class Save {

	public static final String[] directories = {"C://Users//Owen//Desktop//0.txt",
												"C://Users//Owen//Desktop//1.txt",
												"C://Users//Owen//Desktop//2.txt",
												"C://Users//Owen//Desktop//3.txt",
												"C://Users//Owen//Desktop//4.txt",
												"C://Users//Owen//Desktop//5.txt",
												"C://Users//Owen//Desktop//6.txt",
												"C://Users//Owen//Desktop//7.txt",
												"C://Users//Owen//Desktop//8.txt",
												"C://Users//Owen//Desktop//9.txt"};
	
	
	public Save(int file) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(directories[file], "UTF-8");
		writer.println(Grid.columns + "," + Grid.rows);
		writer.println("GLONGO!!");

		for(int i = 0; i != Grid.columns; i++) {
			for(int j = 0; j != Grid.rows; j++) {
				writer.print(Tile.toString(i, j) + ";");
			}
		}

		writer.println("MAXIMUM GLONGAGE");
		writer.println(Ship.locx + "," + Ship.locy);
		
		writer.println("DO YOU EVEN GLONGLE????");
		for(Crew c : Grid.crew) {
			if(c != null) {
				writer.print(Crew.toString(c) + ";");
			}
		}
		
		writer.println("");
		
		writer.println(PlayMode.titanium + "," + PlayMode.uranium + "," + PlayMode.fuel + "," + PlayMode.gold);
		
		writer.close();
	}
	
	public static boolean load(int file) {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(directories[file]));
		} catch (FileNotFoundException e1) {
			return false;
		}

		String size = "a";
		String tiles = "a";
		String pos = "a";
		String crew = "a";
		String resources = "a";
		try {
			size = reader.readLine();
			reader.readLine();
			tiles = reader.readLine();
			pos = reader.readLine();
			reader.readLine();
			crew = reader.readLine();
			resources = reader.readLine();
		} catch (IOException e) {}
		String[] splitTiles = tiles.split(";");
		String[] splitSize = size.split(",");
		String[] splitPos = pos.split(",");
		
		
		Scanner scan = new Scanner(splitPos[0]);
		float x = (float)scan.nextDouble();
		scan.close();
		scan = new Scanner(splitPos[1]);
		float y = (float)scan.nextDouble();
		scan.close();
		scan = new Scanner(splitSize[0]);
		int columns = scan.nextInt(); 
		scan.close();
		scan = new Scanner(splitSize[1]);
		int rows = scan.nextInt();
		scan.close();
		
		try {
			Ship.grid = new Grid(columns, rows, 16, x, y);
		} catch (SlickException e) {}
		
		for(int i = 0; i != columns; i++) {
			for(int j = 0; j != rows; j++) {
				Grid.tiles[i][j].load(splitTiles[i*rows + j]);
			}
		}
		
		if(!crew.equals("")) {
			String[] splitCrew = crew.split(";");
			for(int i = 0; i != splitCrew.length; i++) {
				String[] splitties = splitCrew[i].split(",");
				
				int tilex = 0; int tiley = 0; double lx = 0; double ly = 0; int type = 0;
				scan = new Scanner(splitties[0]);
				tilex = scan.nextInt();
				scan.close();
				scan = new Scanner(splitties[1]);
				tiley = scan.nextInt();
				scan.close();
				scan = new Scanner(splitties[2]);
				lx = scan.nextDouble();
				scan.close();
				scan = new Scanner(splitties[3]);
				ly = scan.nextDouble();
				scan.close();
				scan = new Scanner(splitties[4]);
				type = scan.nextInt();
				scan.close();
				
				try {
					Grid.crew.add(new Crew(tilex, tiley, lx, ly, type));
				} catch (SlickException e) {}
			}
		}
		
		String[] resourceSplit = resources.split(",");
		scan = new Scanner(resourceSplit[0]);
		PlayMode.titanium = scan.nextDouble(); scan.close();
		scan = new Scanner(resourceSplit[1]);
		PlayMode.uranium = scan.nextDouble(); scan.close();
		scan = new Scanner(resourceSplit[2]);
		PlayMode.fuel = scan.nextDouble();  scan.close();
		scan = new Scanner(resourceSplit[3]);
		PlayMode.gold = scan.nextDouble(); scan.close();
		
		PlayMode.firstRun = true;
		
		try {
			reader.close();
		} catch (IOException e) {}
		
		return true;
		
	}
	
	
}
