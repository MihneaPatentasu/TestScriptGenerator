package frontend;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class PathChooserPanel extends JPanel {
	public PathChooserPanel() {
		JLabel nameOfGeneratedFileLabel = new JLabel("_TestsToRun file name:");
		nameOfGeneratedFileLabel.setBounds(100, 100, 100, 100);
		JTextField nameOfGeneratedFileTextField = new JTextField();
		nameOfGeneratedFileTextField.setBounds(25, 25, 25, 25);
		JLabel saveLocationOfGeneratedFileLabel = new JLabel("_TestsToRun file save location (ex: _TestsToRun_*.py):");
		add(nameOfGeneratedFileLabel);
		add(nameOfGeneratedFileTextField);
		add(saveLocationOfGeneratedFileLabel);
	}
}
