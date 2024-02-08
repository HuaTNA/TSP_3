
import java.io.IOException;
import java.util.ArrayList;

public class exInfo extends Graph {

	public void printGsum(ArrayList<Graph> G) {
		System.out.println("\nGRAPH SUMMARY");
		System.out.println("No.    # nodes    # arcs");
		System.out.println("------------------------");
		for (int i = 0; i < G.size(); i++) {
			System.out.format("%3d", i + 1);
			System.out.format("%11d", G.get(i).getN());
			System.out.format("%10d\n", G.get(i).getM());

		}
	}

	public void displayGraphs(ArrayList<Graph> G) throws IOException {
		boolean exit = false;
		while (!exit) {
			printGsum(G);
			int enter = 0;
			enter = getInput.getInteger("\nEnter graph to see details (0 to quit): ", 0, G.size());
			System.out.println();
			if (enter == 0) {
				exit = true;
			} else {
				G.get(enter - 1);
				Graph Graph = new Graph();
				Graph = G.get(enter - 1);
				Graph.print();
			}
		}
	}

	@Override
	public void reset() {
		if (Pro5_tanhua.G.size() > 0) {
			Pro5_tanhua.G.clear();
		}
		Graph Graph = new Graph();
		super.reset();
		getInput.a = false;

	}

}
