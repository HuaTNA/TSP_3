import java.util.ArrayList;

public class NNFLSolver extends TSPSolver {
	public NNFLSolver() {
		super();
		String[] name = new String[2];
		name[0] = "NN-FL";
		name[1] = "nearest neighbor first-last";
		setName(name);

	}

	@Override
	public int[] nextNode(Graph G, ArrayList<Integer> visited) {
		int nextNode_1[] = new int[2];
		int nextNode_2[] = new int[2];

		nextNode_1[0] = nearestNeighbor(G, visited, 0);
		nextNode_1[1] = 0;
		nextNode_2[0] = nearestNeighbor(G, visited, visited.size() - 1);
		nextNode_2[1] = visited.size();
		if (nextNode_1[0] != 0 && nextNode_2[0] != 0) {
			if (G.getCost(visited.get(0) - 1, nextNode_1[0] - 1) < G.getCost(visited.get(visited.size() - 1) - 1,
					nextNode_2[0] - 1)) {
				return nextNode_1;
			}
			return nextNode_2;
		}
		return nextNode_1;
	}
}
