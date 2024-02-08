import java.util.ArrayList;

public class NISolver extends TSPSolver {
	public NISolver() {
		super();
		String[] name = new String[2];
		name[0] = "NI";
		name[1] = "node insertion";
		setName(name);
	}

	@Override
	public int[] nextNode(Graph G, ArrayList<Integer> visited) {
		double cost = Double.POSITIVE_INFINITY;
		int nearestNeighbor = -1;
		int position = -1;
		int neighbor_1 = -1;
		int neighbor_2 = -1;
		int position_1 = -1;
		int position_2 = -1;
		int position_3 = -1;
		int neighbor_3 = -1;

		for (int x = 0; x < visited.size(); x++) {
			if (x == 0) {
				neighbor_1 = nearestNeighbor(G, visited, 0);
				position_1 = 0;
				if (neighbor_1 != -1) {
					if (cost > G.getCost(neighbor_1 - 1, visited.get(0) - 1)) {
						cost = G.getCost(neighbor_1 - 1, visited.get(0) - 1);
						nearestNeighbor = neighbor_1;
						position = position_1;

					}
				}
			} else if (x == visited.size() - 1) {
				neighbor_2 = nearestNeighbor(G, visited, visited.size() - 1);
				position_2 = visited.size();
				if (neighbor_2 != -1) {
					if (cost > G.getCost(neighbor_2 - 1, visited.get(x) - 1)) {
						cost = G.getCost(neighbor_2 - 1, visited.get(x) - 1);
						nearestNeighbor = neighbor_2;
						position = position_2;

					}
				}
			} else {
				neighbor_3 = nearestNeighbor(G, visited, x);
				position_3 = x + 1;
				if (neighbor_3 != -1) {
					if (cost > G.getCost(visited.get(x) - 1, neighbor_3 - 1)) {
						cost = G.getCost(visited.get(x) - 1, neighbor_3 - 1);
						nearestNeighbor = neighbor_3;
						position = position_3;

					}
				}
			}
		}

		return new int[] { nearestNeighbor, position };
	}

	// check if node k can be inserted at position i
	public boolean canBeInserted(Graph G, ArrayList<Integer> visited, int i, int k) {
		if (G.existsArc(visited.get(i) - 1, k)) {

			return true;
		}
		return false;
	}

	@Override
	public int nearestNeighbor(Graph G, ArrayList<Integer> visited, int k) {
		sum = 0.00;
		int start = visited.get(k);
		int nextCity = -1;
		double cost = Double.POSITIVE_INFINITY;
		// the number of city has passed
		// init b

		for (int i = 1; i < G.getN() + 1; i++) {
			if (cost > G.getCost(start - 1, i - 1) && !visited.contains(i) && G.existsArc(start - 1, i - 1)) {
				if (k == 0 || k == visited.size() - 1) {
					cost = G.getCost(start - 1, i - 1);
					nextCity = i;
					b = true; // find the min cost success
				} else {
					if (canBeInserted(G, visited, k + 1, i - 1)) {
						cost = G.getCost(start - 1, i - 1);
						nextCity = i;
						b = true; // find the min cost success
					}
				}

			}
		}
		if (!b) { // out of arc in the current node, cannot be solnpath,
			sum = 0;
			setHasResults(false);
			nextCity = -1; // Exit the while loop if b is still false
		}
		return nextCity;
	}

}