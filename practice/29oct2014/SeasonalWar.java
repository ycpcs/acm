import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SeasonalWar {
	private static class Shape {
		private int id;
		private Shape(int id) {
			this.id = id;
		}
		
		@Override
		public boolean equals(Object obj) {
			Shape other = (Shape) obj;
			return this.id == other.id;
		}
		
		@Override
		public int hashCode() {
			return id;
		}
	}
	
	private static class Location {
		private int x,y;
		private Location(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object obj) {
			Location other = (Location) obj;
			return this.x == other.x && this.y == other.y;
		}
		
		@Override
		public int hashCode() {
			return x*23 + y;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader("Bumble.in"));
		
		int imageNumber = 1;
		
		while (in.hasNextLine()) {
			int nextId = 0;

			// read picture dimension
			int dim = Integer.parseInt(in.nextLine());
			
			// read picture data
			int[][] pic = new int[dim][dim];
			for (int y = 0; y < dim; y++) {
				String line = in.nextLine();
				for (int x = 0; x < dim; x++) {
					pic[x][y] = line.charAt(x) - '0';
				}
			}
			
			Set<Shape> set = new HashSet<Shape>();
			Shape[][] shapes = new Shape[dim][dim];
			for (int x = 0; x < dim; x++) {
				for (int y = 0; y < dim; y++) {
					if (pic[x][y] != 0) {
						Shape s = shapes[x][y];
						if (s == null) {
							s = new Shape(nextId++);
							set.add(s);
							analyzeShape(s, x, y, pic, shapes);
						}
					}
				}
			}

			int result = set.size();
			System.out.printf("Image number %d contains %d war eagles.\n", imageNumber, result);
			
			/*
			for (int y = 0; y < dim; y++) {
				for (int x = 0; x < dim; x++) {
					System.out.printf("%d", pic[x][y]);
				}			
				System.out.println();
			}
			
			for (int y = 0; y < dim; y++) {
				for (int x = 0; x < dim; x++) {
					int c = shapes[x][y] != null ? shapes[x][y].id + 'a' : '.'; 
					System.out.printf("%c", c);
				}
				System.out.println();
			}
			*/
			
			imageNumber++;
		}
	}

	private static void analyzeShape(Shape s, int x, int y, int[][] pic, Shape[][] shapes) {
		int dim = pic.length;
		if (x < 0 || x >= dim || y < 0 || y >= dim) {
			return;
		}
		if (pic[x][y] == 0) {
			return;
		}
		if (shapes[x][y] != null) {
			return;
		}
		shapes[x][y] = s;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (dx != 0 || dy != 0) {
					analyzeShape(s, x+dx, y+dy, pic, shapes);
				}
			}
		}
	}
}

