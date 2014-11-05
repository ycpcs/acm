import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class House {
	private static final int[][] CONNECTIONS = {
		//         1  2  3  4  5
		/* */ { 0, 0, 0, 0, 0, 0 },
		/*1*/ { 0, 0, 1, 1, 0, 1 },
		/*2*/ { 0, 1, 0, 1, 0, 1 },
		/*3*/ { 0, 1, 1, 0, 1, 1 },
		/*4*/ { 0, 0, 0, 1, 0, 1 },
		/*5*/ { 0, 1, 1, 1, 1, 0 },
	};
	
	private static class Edge {
		private int p1, p2;
		
		public Edge(int p1, int p2) {
			if (p1 < p2) {
				this.p1 = p1;
				this.p2 = p2;
			} else {
				this.p1 = p2;
				this.p2 = p1;
			}
		}
		
		@Override
		public boolean equals(Object obj) {
			Edge other = (Edge) obj;
			return p1 == other.p1 && p2 == other.p2;
		}
		
		@Override
		public int hashCode() {
			return p1 * 11 + p2;
		}
	}
	
	private static class Solution {
		private int where;
		private List<Integer> points;
		private Set<Edge> edges;
		
		public Solution(int where) {
			this.where = where;
			points = new ArrayList<Integer>();
			edges = new HashSet<Edge>();
		}
		
		public Solution go(int next) {
			if (CONNECTIONS[where][next] == 0) {
				return null;
			}
			Edge edge = new Edge(where, next);
			if (edges.contains(edge)) {
				return null;
			}
			Solution nextSoln = new Solution(next);
			nextSoln.points.addAll(this.points);
			nextSoln.points.add(next);
			nextSoln.edges.addAll(this.edges);
			nextSoln.edges.add(edge);
			return nextSoln;
		}
		
		public boolean isComplete() {
			return edges.size() == 8;
		}
		
		@Override
		public String toString() {
			StringBuilder buf = new StringBuilder();
			for (Integer i : points) {
				buf.append(i);
			}
			return buf.toString();
		}
	}
	
	public static void main(String[] args) {
		Solution start = new Solution(1);
		start.points.add(1);
		find(start);
	}

	private static void find(Solution solution) {
		if (solution.isComplete()) {
			System.out.println(solution);
		} else {
			for (int i = 1; i <= 5; i++) {
				Solution next = solution.go(i);
				if (next != null) {
					find(next);
				}
			}
		}
	}
}
