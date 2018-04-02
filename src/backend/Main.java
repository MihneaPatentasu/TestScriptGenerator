package backend;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<String> paths=new ArrayList<>();
		paths.add("D:\\Useful info for modules\\K");
		paths.add("D:\\Useful info for modules\\PM");
		TestScriptGenerator.generateTestScript("D:\\Useful info for modules\\Test.py", paths);
	}

}
