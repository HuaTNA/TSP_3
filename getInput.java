import java.io.*;
import java.util.ArrayList;

public class getInput {

	private static final BufferedReader num = new BufferedReader(new InputStreamReader(System.in));

	public static int[] count;
	public static boolean a = false;

	public static int getInteger(String prompt, int LB, int UB) throws IOException {
		int number = 0;
		do {
			System.out.print(prompt);
			try {
				number = Integer.parseInt(num.readLine());
				if (number < LB || number > UB) {
					throw new NumberFormatException();
				}
				break;
			} catch (Exception e) {
				if (LB == Integer.MIN_VALUE && UB == Integer.MAX_VALUE) {
					System.out.print("ERROR: Input must be an integer in [-infinity, infinity]!\n");
				} else if (UB == Integer.MAX_VALUE) {
					System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n", LB);
				} else if (LB == Integer.MIN_VALUE) {
					System.out.format("ERROR: Input must be an integer in [-infinity, %d]!\n", UB);
				} else {
					System.out.format("ERROR: Input must be an integer in [%d, %d]!\n", LB, UB);
				}
			}
		} while (true);
		return number;
	}

	public static double getDouble(String prompt, double LB, double UB) throws IOException {
		double number = 0.00;

		do {
			System.out.print(prompt);
			try {
				number = Double.parseDouble(num.readLine());
				if (number < LB || number > UB) {
					throw new NumberFormatException();
				}
				break;
			} catch (NumberFormatException e) {
				if (LB == Double.MIN_VALUE && UB == Double.MAX_VALUE) {
					System.out.print("ERROR: Input must be a real number in [-infinity, infinity]!\n");
				} else if (UB == Double.MAX_VALUE) {
					System.out.format("ERROR: Input must be a real number in [%.2f, infinity]!\n", LB);
				} else if (LB == Double.MIN_VALUE) {
					System.out.format("ERROR: Input must be a real number in [-infinity, %.2f]!\n", UB);
				} else {
					System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n", LB, UB);
				}
			}
		} while (true);
		return number;
	}

	public static String getString(String prompt) throws IOException {
		System.out.print(prompt);
		prompt = num.readLine();
		return prompt;
	}

	public static boolean readFile(ArrayList<Graph> G) throws IOException {
		boolean exist = false;
		String filePath = Pro5_tanhua.filePath;

		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			BufferedReader fin = new BufferedReader(new FileReader(filePath));
			String lintxt = null;
			int status = 0;
			int numberOfGraph = 0;
			// once lintex == null, means there's empty, so one graph finished load
			while ((lintxt = fin.readLine()) != null) {
				int number = Integer.parseInt(lintxt);
				Graph graph = new Graph(number);
				graph.setN(number);
				graph.init(number);
				status++;
				numberOfGraph++;
				for (int i = 0; i < number; i++) {
					lintxt = fin.readLine();
					String[] array;
					array = lintxt.split(",");
					double lon = Double.parseDouble(array[1]);
					double lat = Double.parseDouble(array[2]);
					Node node = new Node(array[0], lon, lat);
					graph.addNode(node);
					if (!graph.existsNode(node) && lon < 180 && lon > -180 && lat < 90 && lat > -90) {
						exist = true;
						status -= 1; // if the graph exist, status--, means this graph will not be count
					}
				}

				for (int j = 0; j < number - 1; j++) { // add arc
					if (!exist) {
						lintxt = fin.readLine();
						String[] a = lintxt.split(",");
						int[] arc = new int[a.length];
						for (int k = 0; k < a.length; k++) {
							arc[k] = Integer.parseInt(a[k]);
							graph.addArc(j, arc[k] - 1);
						}
					}
				}

				if (!exist) { // the graph that added node added in the G list
					G.add(graph);

				}
				lintxt = fin.readLine(); // read the empty line
			}
			fin.flush();
			fin.close();
			System.out.format("\n%d of %d graphs loaded!\n", status, numberOfGraph);
			System.out.println();
			a = true;
			return true;
		} else {
			System.out.println("\nERROR: File not found!\n");

			a = false;
			return false;
		}

	}

}
