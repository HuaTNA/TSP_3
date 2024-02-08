import java.util.ArrayList;

public class TSPSolver {
	private ArrayList<int[]> solnPath; // ArrayList *or* array of solution paths
	private double[] solnCost; // ArrayList *or* array of solution costs
	private double[] compTime; // ArrayList *or* array of computation times
	private boolean[] solnFound; // ArrayList *or* array of T/F solns found
	private boolean resultsExist = false; // whether or not results exist
	public static ArrayList<Integer> visited = new ArrayList<Integer>(0);
	double sum = 0;

	private String[] name = new String[2];

	// constructors
	public TSPSolver() {
	}

	public TSPSolver(ArrayList<Graph> G) {
		init(G);
	}

	// getters
	public int[] getSolnPath(int i) {
		return this.solnPath.get(i);
	}

	public double getSolnCost(int i) {
		return this.getSolnCost(i);
	}

	public double getCompTime(int i) {
		return this.getCompTime(i);
	}

	public boolean getSolnFound(int i) {
		return this.getSolnFound(i);
	}

	public boolean hasResults() {
		return this.resultsExist;
	}

	public String[] getName() {
		return this.name;
	}

	// setters

	public void setSolnPath(int i, int[] solnPath) {
		this.solnPath.set(i, solnPath);
	}

	public void setSolnCost(int i, double solnCost) {
		this.solnCost[i] = solnCost;
	}

	public void setCompTime(int i, double compTime) {
		this.compTime[i] = compTime;
	}

	public void setSolnFound(int i, boolean solnFound) {
		this.solnFound[i] = solnFound;
	}

	public void setHasResults(boolean resultsExist) {
		this.resultsExist = resultsExist;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public void init(ArrayList<Graph> G) {
		int size = G.size();
		this.solnPath = new ArrayList<int[]>(size);
		for (int i = 0; i < size; i++) {
			int[] e = new int[0];
			solnPath.add(e);
		}
		this.solnCost = new double[size];
		this.compTime = new double[size];
		this.solnFound = new boolean[size];
		this.resultsExist = false;
	}

	public void reset() {
		resultsExist = false;
		exInfo exInfo = new exInfo();
		exInfo.reset();
	}

	public static boolean b = false;

	public void run(ArrayList<Graph> G, int i, boolean suppressOutput) {

		Graph current_graph = G.get(i);
		long start_time = System.currentTimeMillis();
		visited.clear();
		visited.add(1);

		for (int c = 0; c < current_graph.getN() - 1; c++) {
			int[] next_node = nextNode(current_graph, visited);
			if (next_node[0] == 0 || next_node[0] == -1) {
				// this.name[0], i + 1);
				// since "D" need to display, so still need to add as 0
				setSolnFound(i, false);
				int[] emptyPath = new int[0];
				solnPath.set(c, emptyPath);
				System.out.format("ERROR: %s did not find a TSP rout for Graph %d!\n", this.name[0], i + 1);
			} else {
				visited.add(next_node[1], next_node[0]);
			}
		}

		if (b == true) {
			if (current_graph.existsArc(visited.get(0) - 1, visited.get(visited.size() - 1) - 1)) {
				visited.add(visited.get(0));
				setSolnFound(i, b);
				setHasResults(b);
				int[] path = new int[visited.size()]; // add path
				for (int j = 0; j < visited.size(); j++) {
					path[j] = visited.get(j);
				}
				sum = current_graph.pathCost(path);
				solnPath.set(i, path);
				solnCost[i] = sum;
				long elapsedTime = System.currentTimeMillis() - start_time;
				compTime[i] = elapsedTime;
				for (int u = 0; u < G.size(); u++) {
					if (solnFound[u] == true) {
						setHasResults(true);
						break;
					} else {
						setHasResults(false);
					}
				}

			}
		} else {
			System.out.format("ERROR: %s did not find a TSP rout for Graph %d!\n", this.name[0], i + 1);
			int[] emptyPath = new int[0];
			solnPath.set(i, emptyPath);
			setSolnFound(i, false);
			setHasResults(false);
			setSolnCost(i, 0);
			setCompTime(i, 0);
		}

	}

	public int nearestNeighbor(Graph G, ArrayList<Integer> visited, int k) {
		sum = 0.00;
		int start = visited.get(k);
		int nextCity = -1;

		double cost = Double.POSITIVE_INFINITY;

		// the number of city has passed
		// init b
		b = false;
		for (int i = 1; i < G.getN() + 1; i++) {
			if (G.getArc(start - 1, i - 1)) {
				if (cost > G.getCost(start - 1, i - 1) && !visited.contains(i) && G.getCost(start - 1, i - 1) != 0) {
					cost = G.getCost(start - 1, i - 1);
					nextCity = i;
					b = true; // find the min cost success
				}
			}
		}
		if (!b) { // out of arc in the current node, cannot be solnpath,
			sum = 0;
			setHasResults(false);
			nextCity = 0; // Exit the while loop if b is still false
		}
		return nextCity;
	}

	// find next node(return 2D array with [0]= next node, [1]= insertion position)
	public int[] nextNode(Graph G, ArrayList<Integer> visited) {
		int[] array = new int[2];
		array[0] = -1;
		array[1] = -1;
		return array;
	}

	public void printAll() {
		if (this.solnFound.length != Pro5_tanhua.G.size()) { // num of "R" not same with "I"， out of input
			System.out.println("\nERROR: No results exist!\n");
			return;
		}
		System.out.println("\nDetailed results for " + getName()[1] + ":");
		System.out.println("-----------------------------------------------");
		System.out.println("No.        Cost (km)     Comp time (ms)   Route");
		System.out.println("-----------------------------------------------");
		for (int i = 0; i < Pro5_tanhua.G.size(); i++) {
			printSingleResult(i, solnFound[i]);
		}

		// System.out.println("");
		printStats();
	}

	public void printSingleResult(int i, boolean rowOnly) {
		if (rowOnly) {
			System.out.format("%3d", i + 1);
			System.out.format("%17.2f", solnCost[i]);
			System.out.format("%19.3f", (double) compTime[i]);

			int[] string = solnPath.get(i);
			System.out.print("   ");
			for (int i1 = 0; i1 < string.length - 1; i1++) {
				System.out.format("%s", string[i1]);
				System.out.print("-");
			}
			System.out.format("%s", string[string.length - 1]);
		} else {
			System.out.format("%3d", i + 1);
			System.out.print("                -                  -   -");
		}
		System.out.println();
	}

	private double Average = 0;
	private double Average_comtime = 0;
	private double standardDeviation = 0;
	private double standardDeviation_comtime = 0;
	private double Rate1 = 0;

	double Max = 0;
	double Min = 0;
	double max_comtime = 0;
	double min_comtime = 0;
	double successRate = 0;

	public void stats() {
		double average = 0;
		double average_comtime = 0;
		double exit;
		int t = 0;

		for (double value : solnCost) { // num of solncost without 0
			if (value != 0) {
				t++;
			}
		}
		double[] newsolnCost = new double[t];
		double[] newcomTime = new double[t];
		int index = 0;
		int index_2 = 0;
		for (double value : solnCost) {
			if (value != 0) {
				newsolnCost[index++] = value; // all solncost != 0
			}
		}

		for (int i = 0; i < t; i++) {
			if (compTime[i] != 0) {
				newcomTime[index_2] = compTime[i];
				index_2++;
			}
		}
		double h = newsolnCost.length;
		double y = solnCost.length;
		exit = h / y;
		Max = newsolnCost[0];
		Min = newsolnCost[0];
		max_comtime = newcomTime[0];
		min_comtime = newcomTime[0];
		double total = 0;
		double total_comtime = 0;
		for (int i = 0; i < newsolnCost.length; i++) {
			Min = Min > newsolnCost[i] ? newsolnCost[i] : Min;
			Max = Max < newsolnCost[i] ? newsolnCost[i] : Max;
			min_comtime = min_comtime > newcomTime[i] ? newcomTime[i] : min_comtime;
			max_comtime = max_comtime < newcomTime[i] ? newcomTime[i] : max_comtime;
			average += newsolnCost[i];
			average_comtime += newcomTime[i];
		}

		average = average / newsolnCost.length;
		Average = average;
		average_comtime = average_comtime / newcomTime.length;
		Average_comtime = average_comtime;

		for (int i = 0; i < newsolnCost.length; i++) {
			total += (newsolnCost[i] - average) * (newsolnCost[i] - average);
			total_comtime += (newcomTime[i] - average_comtime) * (newcomTime[i] - average_comtime);
		}
		standardDeviation = Math.sqrt(total / (newsolnCost.length - 1));
		standardDeviation_comtime = Math.sqrt(total_comtime / (newcomTime.length - 1));
		successRate = exit * 100;
		Rate1 = successRate;

	}

	public void printStats() {
		System.out.println();
		System.out.println("Statistical summary for " + getName()[1] + ":");
		System.out.println("---------------------------------------");
		System.out.println("           Cost (km)     Comp time (ms)");
		System.out.println("---------------------------------------");
		System.out.format("Average%13.2f%19.3f\n", Average, Average_comtime);
		if (!Double.isNaN(standardDeviation)) {
			System.out.format("St Dev%14.2f%19.3f\n", standardDeviation, standardDeviation_comtime);
		} else {
			System.out.format("St Dev           NaN                NaN\n");
		}
		System.out.format("Min%17.2f%19.3f\n", Min, min_comtime);
		System.out.format("Max%17.2f%19.3f\n", Max, max_comtime);
		System.out.format("\nSuccess rate: %.1f%%\n", successRate());
		System.out.println();
	}

	static boolean success = false;

	public double successRate() {
		double rabnte = Rate1;
		return rabnte;
	}

	public double avgCost() {
		double averagecost = Average;
		return averagecost;
	}

	public double avgTime() {
		double averagecome = Average_comtime;
		return averagecome;
	}
}
