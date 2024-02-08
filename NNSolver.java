import java.util.ArrayList;

public class NNSolver extends TSPSolver {
	public NNSolver() {
		super();
		String[] name = new String[2];
		name[0] = "NN";
		name[1] = "nearest neighbor";
		setName(name);
	}

	@Override
	public int[] nextNode(Graph G, ArrayList<Integer> visited) {
		int[] selectNode = new int[2];
		selectNode[0] = nearestNeighbor(G, visited, visited.size() - 1);
		selectNode[1] = visited.size();
		return selectNode;
	}
}
