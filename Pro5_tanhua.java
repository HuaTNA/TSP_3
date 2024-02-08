import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Pro5_tanhua {

	public static Node node = new Node();

	public static exInfo exInfo = new exInfo();
	public static ArrayList<Graph> G = new ArrayList<Graph>();
	public static boolean loadFile = false;
	public static String filePath;

	public static NNFLSolver nnflSolver = new NNFLSolver();
	public static NNSolver nnSolver = new NNSolver();
	public static NISolver niSolver = new NISolver();
	public static TSPSolver ts = new TSPSolver();

	public static void main(String arg[]) throws IOException {

		String input = "Q";
		do {
			displayMenu1();

			input = getInput.getString("\nEnter choice: ");
			if (input.equalsIgnoreCase("L")) {
				System.out.print("\nEnter file name (0 to cancel): ");
				filePath = getInput.getString("");
				if (filePath.equals("0")) {
					System.out.println("\nFile loading process canceled.\n");
				} else {
					loadFile = getInput.readFile(G);
				}

			} else if (input.equalsIgnoreCase("I")) {
				if (!loadFile) {
					System.out.println("\nERROR: No graphs have been loaded!\n");
				} else {
					if (G.isEmpty()) {
						System.out.println("\nERROR: No graphs have been loaded!\n");
					} else {
						exInfo.displayGraphs(G);
					}
				}
			} else if (input.equalsIgnoreCase("C")) {
				if (!loadFile) {
					System.out.println("\nERROR: No graphs have been loaded!\n");
				} else {
					resetAll(nnSolver, nnflSolver, niSolver);
					System.out.println("\nAll graphs cleared.\n");
				}
			}

			else if (input.equalsIgnoreCase("R")) {
				if (G.size() == 0) {
					System.out.println("\nERROR: No graphs have been loaded!\n");
				} else {
					runAll(G, nnSolver, nnflSolver, niSolver);

				}

			} else if (input.equalsIgnoreCase("D")) {

				if (loadFile == false || nnSolver.hasResults() == false
						|| nnflSolver.hasResults() == false) {
					System.out.println("\nERROR: Results do not exist for all algorithms!\n");
				} else {
					printAll(nnSolver, nnflSolver, niSolver);
				}
			}

			else if (input.equalsIgnoreCase("X")) {
				if (loadFile == true && nnSolver.hasResults() == true
						&& nnflSolver.hasResults() == true) {
					printX();
				} else {

					System.out.println("\nERROR: Results do not exist for all algorithms!\n");
				}

			} else if (input.equalsIgnoreCase("Q")) {
				System.out.println("\nCiao!");
			} else {
				System.out.println("\nERROR: Invalid menu choice!\n");
			}

		} while (!input.equalsIgnoreCase("Q"));
	}

	public static void displayMenu1() {
		System.out.println("   JAVA TRAVELING SALESMAN PROBLEM V3");
		System.out.println("L - Load graphs from file");
		System.out.println("I - Display graph info");
		System.out.println("C - Clear all graphs");
		System.out.println("R - Run all algorithms");
		System.out.println("D - Display algorithm performance");
		System.out.println("X - Compare average algorithm performance");
		System.out.println("Q - Quit");
	}

	public static String[] n = new String[4];

	public static void compare(NNSolver NN, NNFLSolver FL, NISolver NI) {
		double[] array = { NN.avgCost(), FL.avgCost(), NI.avgCost() };
		double[] arrayComeptime = { NN.avgTime(), FL.avgTime(), NI.avgTime() };
		double[] arraySuccessRate = { NN.successRate(), FL.successRate(), NI.successRate() };
		Arrays.sort(array);
		Arrays.sort(arrayComeptime);
		Arrays.sort(arraySuccessRate);
		String minCost = (array[0] == NN.avgCost()) ? NN.getName()[0]
				: (array[0] == FL.avgCost()) ? FL.getName()[0] : NI.getName()[0];
		String minTime = (arrayComeptime[0] == NN.avgTime()) ? NN.getName()[0]
				: (arrayComeptime[0] == FL.avgTime()) ? FL.getName()[0] : NI.getName()[0];
		String maxSuccessRate = (arraySuccessRate[2] == NN.successRate()) ? NN.getName()[0]
				: (arraySuccessRate[2] == FL.successRate()) ? FL.getName()[0] : NI.getName()[0];
		String winner;

		if (minCost == minTime && minTime == maxSuccessRate && minCost == maxSuccessRate) {
			winner = minCost;
		} else {
			winner = "Unclear";
		}
		n = new String[] { minCost, minTime, maxSuccessRate, winner };
	}

	public static void printX() {
		compare(nnSolver, nnflSolver, niSolver);
		System.out.println();
		System.out.println("------------------------------------------------------------");
		System.out.println("           Cost (km)     Comp time (ms)     Success rate (%)");
		System.out.println("------------------------------------------------------------");
		System.out.format("%s%18.2f%19.2f%21.1f\n", nnSolver.getName()[0], nnSolver.avgCost(), nnSolver.avgTime(),
				nnSolver.successRate());
		System.out.format("%s%15.2f%19.2f%21.1f\n", nnflSolver.getName()[0], nnflSolver.avgCost(), nnflSolver.avgTime(),
				nnflSolver.successRate());
		System.out.format("%s%18.2f%19.2f%21.1f\n", niSolver.getName()[0], niSolver.avgCost(), niSolver.avgTime(),
				niSolver.successRate());
		System.out.println("------------------------------------------------------------");
		System.out.format("Winner%14s%19s%21s\n", n[0], n[1], n[2]);
		System.out.println("------------------------------------------------------------");
		System.out.println("Overall winner: " + n[3]);
		System.out.println("");

	}

	public static void resetAll(NNSolver NN, NNFLSolver FL, NISolver NI) {
		G.clear();
		NN.reset();
		FL.reset();
		NI.reset();
	}

	public static void runAll(ArrayList<Graph> G, NNSolver NN, NNFLSolver FL, NISolver NI) {
		System.out.println();
		NN.init(G);
		for (int i = 0; i < G.size(); i++) {
			NN.run(G, i, loadFile);
		}
		NN.stats();
		System.out.println("Nearest neighbor algorithm done.\n");
		FL.init(G);
		for (int j = 0; j < G.size(); j++) {
			FL.run(G, j, loadFile);
		}
		FL.stats();
		System.out.println("Nearest neighbor first-last algorithm done.\n");
		NI.init(G);
		for (int g = 0; g < G.size(); g++) {
			NI.run(G, g, loadFile);
		}

		NI.stats();
		System.out.println("Node insertion algorithm done.\n");
	}

	public static void printAll(NNSolver NN, NNFLSolver FL, NISolver NI) {
		NN.printAll();
		FL.printAll();
		NI.printAll();
	}

}