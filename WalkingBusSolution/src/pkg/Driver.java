package pkg;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;

public class Driver {
	private static int numberNodes;
	private static double alpha;
	private static int[][] coordXY;
	private static double[][] danger;
	
	public static void main(String[] args){
		ImmutableList<String> lines = null;
		CharSource in = Files.asCharSource(new File(args[0]), Charsets.UTF_8);
		try {
			lines = in.readLines();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean readX = false;
		boolean readY = false;
		boolean readD = false;
		
		for(String line : lines){
			String[] s = line.trim().split("\\s+");
			
			if(s.length > 1 && s[0].equals("param") && s[1].equals("n")){
				numberNodes = Ints.tryParse(s[s.length-1]);
				coordXY = new int[numberNodes+1][2];
				danger = new double[numberNodes+1][numberNodes+1];
			}
			else if(s.length > 1 && s[0].equals("param") && s[1].equals("alpha"))
				alpha = Doubles.tryParse(s[s.length-1]);
			else if(s.length > 1 && s[0].equals("param") && s[1].equals("coordX")){
				readX = true;
				readY = false;
				readD = false;
			}
			else if(s.length > 1 && s[0].equals("param") && s[1].equals("coordY")){
				readY = true;
				readX = false;
				readD = false;
			}
			else if(s.length > 1 && s[0].equals("param") && s[1].equals("d")){
				readX = false;
				readY = false;
				readD = true;
			}
			else if(s.length > 0 && s[0].equals(";") || s.length <= 0){
				readX = false;
				readY = false;
				readD = false;
				continue;
			}
			else{
				if(readX){
					for(int i = 0; i < s.length; i+=2)
						coordXY[Ints.tryParse(s[i])][0] = Ints.tryParse(s[i+1]); 
				}
				else if(readY){
					for(int i = 0; i < s.length; i+=2)
						coordXY[Ints.tryParse(s[i])][1] = Ints.tryParse(s[i+1]); 
				}
				else if(readD){
					if(!s[s.length-1].equals(":=")){
						int row = Ints.tryParse(s[0]);
						for(int i = 1; i <= numberNodes + 1; i++)
							danger[row][i-1] = Doubles.tryParse(s[i]);
					}
				}
			}
		}
		
		Solver s = new Solver(numberNodes, alpha, coordXY, danger);
		ArrayList<Arc> solution = s.solve();
		
		CharSink out;
		String inputFilename;
//		String jarPath = ClassLoader.getSystemClassLoader().getResource(".").getPath();
		
		// If the provided argument ends with a trailing / then remove it 
		if(args[0].charAt(args[0].length()-1) == FileSystems.getDefault().getSeparator().toCharArray()[0])
			inputFilename = new String(args[0].substring(0, args[0].length()-1));
		else
			inputFilename = args[0];
		int startIndex = inputFilename.lastIndexOf((FileSystems.getDefault().getSeparator()));
		String srcFile = Driver.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String argFilename = args[0].substring(startIndex+1, args[0].length()-3) + "sol";
//		out = Files.asCharSink(new File(inputFilename.substring(0, inputFilename.length()-3) + "sol"), Charsets.UTF_8);
		out = Files.asCharSink(new File(srcFile + FileSystems.getDefault().getSeparator() + argFilename), Charsets.UTF_8);
		
		ArrayList<String> solutionLines = new ArrayList<String>();
		for(Arc e : solution)
			solutionLines.add(e.node1 + " " + e.node2);
		
		try {
			out.writeLines(solutionLines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
