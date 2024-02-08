import java.util.ArrayList;

public class Graph {
	private int numberOfNodes; // number of nodes
	private int numberOfArcs; // number of arcs
	private ArrayList<Node> node; // ArraList *or* array of nodes
	private boolean[][] adjacencyMatrix; // adjacency matrix
	private double[][] costMatrix; // cost matrix

	// constructors
	public Graph() {

	}

	public Graph(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}

	// setters
	public void setN(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}

	public void setM(int numberOfArcs) {
		this.numberOfArcs = numberOfArcs;
	}

	public void setArc(int i, int j, boolean b) {
		this.adjacencyMatrix[i][j] = b;
		this.adjacencyMatrix[j][i] = b;
	}

	public void setCost(int i, int j, double c) {
		this.costMatrix[i][j] = c;
		this.costMatrix[j][i] = c;
	}

	// getters
	public int getN() {
		return this.numberOfNodes;
	}

	public int getM() {
		return this.numberOfArcs;
	}

	public boolean getArc(int i, int j) {
		return this.adjacencyMatrix[i][j];
	}

	public double getCost(int i, int j) {
		return this.costMatrix[i][j];
	}

	public Node getNode(final int i) {
		return this.node.get(i);
	}

	// initialize values and arrays
	public void init(int n) {
		this.numberOfNodes = n;
		this.numberOfArcs = 0;
		this.node = new ArrayList<Node>();
		this.node.ensureCapacity(n);
		this.adjacencyMatrix = new boolean[this.numberOfNodes][this.numberOfNodes];
		this.costMatrix = new double[this.numberOfNodes][this.numberOfNodes];
	}

	// reset the graph
	public void reset() {
		if (this.numberOfNodes > 0) {
			this.node.clear();
		}
		int n = 0;
		this.numberOfNodes = n;
		this.numberOfArcs = n;
		this.adjacencyMatrix = null;
		this.costMatrix = null;
	}

	// check if arc exists
	public boolean existsArc(int i, int j) {
		if (i < 0 || i > this.numberOfNodes || j < 0 || j > this.numberOfNodes) {
			return false;
		}
		return this.adjacencyMatrix[i][j];
	}

	// check if node exists
	public boolean existsNode(Node t) {
		if (this.node == null) {
			return false;
		}
		for (Node existingNode : this.node) {
			if (existingNode.getName().equals(t.getName())
					|| (existingNode.getLat() == t.getLat() && existingNode.getLon() == t.getLon())) {
				return true;
			}
		}
		return false;
	}

	// add an arc , return T/F success
	public boolean addArc(int i, int j) {
		// Check if the arc already exists
		if (existsArc(i, j)) {
			return false; // Arc already exists, so return false
		}
		// If the arc does not exist, set the entries in the adjacency matrix to true
		setArc(i, j, true);
		double distance = Node.distance(this.node.get(i), this.node.get(j));
		setCost(i, j, distance);
		this.numberOfArcs++;
		return true;
	}

	// add a node
	public boolean addNode(Node t) {
		this.node.add(t);
		return true;
	}

	// print all graph info
	public void print() {
		System.out.println("Number of nodes: " + this.numberOfNodes);
		System.out.println("Number of arcs: " + this.numberOfArcs);
		System.out.println();
		this.printNodes();
		this.printArcs();
	}

	// print node list
	public void printNodes() {
		System.out.println("NODE LIST");
		System.out.println("No.               Name        Coordinates");
		System.out.println("-----------------------------------------");
		for (int i = 0; i < this.numberOfNodes; i++) {
			System.out.format("%3d", i + 1);
			Node n = new Node();
			n = this.node.get(i);
			n.print();
			System.out.println();
		}
		System.out.println();
	}

	// print arc list
	public void printArcs() {
		int x = 1;
		System.out.println("ARC LIST");
		System.out.println("No.    Cities       Distance");
		System.out.println("----------------------------");
		for (int i = 0; i < this.node.size(); i++) {
			for (int j = i; j < this.node.size(); j++) {

				if (this.getArc(i, j) == true) {
					System.out.format("%3d", (x++));
					String string = (i + 1) + "-" + (j + 1);
					System.out.format("%10s%15.2f\n", string, this.getCost(i, j));
				}

			}
		}
		System.out.println();
	}

	public void removeArc(int k) {
		int x = -1;
		for (int i = 0; i < numberOfNodes; i++) {
			for (int j = i + 1; j < numberOfNodes; j++) {
				if (adjacencyMatrix[i][j] == true) {
					x++;
					if (x == k) {
						setArc(i, j, false);
						setCost(i, j, 0);
						this.numberOfArcs = this.numberOfArcs - 1;
						break;
					}
				}
			}
		}
	}

	// check feasibility of path P
	public boolean checkPath(int[] P) throws IllegalArgumentException {
		// Checking if the start and end cities are the same
		if (P[0] != P[this.numberOfNodes]) {
			try {
				if (P[0] != P[this.numberOfNodes]) {
					throw new IllegalArgumentException();
				}
			} catch (IllegalArgumentException e) {
				System.out.println("\nERROR: Start and end cities must be the same!\n");
				return false;
			}
		}

		// Checking if any city is visited more than once
		for (int i = 0; i < this.numberOfNodes; i++) {
			for (int j = i + 1; j < this.numberOfNodes; j++) {
				try {
					if (P[i] == P[j]) {
						throw new IllegalArgumentException();
					}
				} catch (IllegalArgumentException e) {
					System.out.println("\nERROR: Cities cannot be visited more than once!");
					System.out.println("ERROR: Not all cities are visited!\n");
					return false;
				}
			}
		}

		// Checking if all arcs between consecutive cities exist
		for (int k = 0; k < this.numberOfNodes; k++) {
			try {
				if (this.existsArc(P[k], P[k + 1]) == false) {
					throw new IllegalArgumentException();
				}
			} catch (IllegalArgumentException e) {
				System.out.println("\nERROR: Arc " + (P[k] + 1) + "-" + (P[k + 1] + 1) + " does not exist!\n");
				return false;
			}
		}
		return true; // If no issues are found, return true
	}

	// calculate cost of path P
	public double pathCost(int[] P) {
		double k = 0;
		for (int i = 0; i < P.length - 1; i++) {
			k += this.getCost(P[i] - 1, P[i + 1] - 1);
		}
		return k;
	}

}
