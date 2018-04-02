package backend;

import java.awt.EventQueue;

import javax.swing.JFrame;

import frontend.MainPanel;
import frontend.SizedFrame;

public class Main {

	public static void main(String[] args) {
//		ArrayList<String> paths=new ArrayList<>();
//		paths.add("D:\\Useful info for modules\\K");
//		paths.add("D:\\Useful info for modules\\PM");
//		TestScriptGenerator.generateTestScript("D:\\Useful info for modules\\Test.py", paths);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new SizedFrame();
				frame.setTitle("TestGenerator");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(null);
				frame.add(new MainPanel());
				frame.setVisible(true);
			}
		});
	}

}
