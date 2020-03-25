package SCIT_OS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {
	static PrintWriter printwriter;
	static FileWriter filewriter;
	static BufferedWriter bufferedwriter;
	static {
		try {

			printwriter = new PrintWriter("SimulationReport.txt");
			filewriter = new FileWriter("SimulationReport.txt", true);
			bufferedwriter = new BufferedWriter(filewriter);
			printwriter = new PrintWriter(bufferedwriter);
		} catch (IOException e) {
			e.getMessage();
		}
	}
}