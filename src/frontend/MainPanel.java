package frontend;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MainPanel extends JPanel{
	public MainPanel() {
		this.add(new PathChooserPanel());
	}
}
