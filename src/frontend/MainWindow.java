package frontend;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import backend.TestScriptGenerator;

import java.awt.Font;
import javax.swing.JScrollPane;

public class MainWindow {

	private JFrame frame;
	private JTextField testToRunFileNameTextField;
	private JTextField testToRunFileSaveLocationTextField;
	
	private String lastBrowsedPath=".";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("TestScript Generator");
		frame.setSize(645, 540);
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, 636, 83);
		frame.getContentPane().add(topPanel);
		topPanel.setLayout(null);
		
		JLabel testToRunFileNameLabel = new JLabel("_TestToRun File Name:");
		testToRunFileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		testToRunFileNameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		testToRunFileNameLabel.setBounds(45, 9, 152, 14);
		topPanel.add(testToRunFileNameLabel);
		
		testToRunFileNameTextField = new JTextField();
		testToRunFileNameTextField.setBounds(200, 6, 317, 20);
		topPanel.add(testToRunFileNameTextField);
		testToRunFileNameTextField.setColumns(10);
		
		JLabel testToRunFileSaveLocationLabel = new JLabel("_TestToRun File Save Location:");
		testToRunFileSaveLocationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		testToRunFileSaveLocationLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		testToRunFileSaveLocationLabel.setBounds(10, 36, 175, 14);
		topPanel.add(testToRunFileSaveLocationLabel);
		
		testToRunFileSaveLocationTextField = new JTextField();
		testToRunFileSaveLocationTextField.setBounds(200, 33, 317, 20);
		topPanel.add(testToRunFileSaveLocationTextField);
		testToRunFileSaveLocationTextField.setColumns(10);
		
		
		
		JButton browseButton = new JButton("Browse");
		browseButton.setBounds(527, 32, 81, 23);
		browseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(lastBrowsedPath));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				chooser.setAcceptAllFileFilterUsed(false);
				int returnVal = chooser.showDialog(frame.getContentPane(), "Save Path");
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					lastBrowsedPath=chooser.getSelectedFile().getAbsolutePath();
					testToRunFileSaveLocationTextField.setText(lastBrowsedPath);
				}
			}
		});
		topPanel.add(browseButton);
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setBounds(0, 83, 636, 408);
		frame.getContentPane().add(lowerPanel);
		lowerPanel.setLayout(null);
		
		JLabel listOfAddedHeader = new JLabel("List of added folders:");
		listOfAddedHeader.setFont(new Font("Tahoma", Font.BOLD, 15));
		listOfAddedHeader.setBounds(328, 0, 166, 15);
		lowerPanel.add(listOfAddedHeader);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(203, 26, 413, 371);
		lowerPanel.add(scrollPane);
		
		DefaultListModel<String> listModel = new DefaultListModel<>();
		JList<String> foldersList = new JList<>(listModel);
		scrollPane.setViewportView(foldersList);
		
		JButton removeFolderButton = new JButton("Remove folder from list");
		removeFolderButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		removeFolderButton.setBounds(10, 153, 183, 23);
		removeFolderButton.setEnabled(false);
		removeFolderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(foldersList.getSelectedIndex()<0) {
					JOptionPane.showMessageDialog(frame,
						    "Please select/highlight a folder from the list!",
						    "Remove test folder error",
						    JOptionPane.ERROR_MESSAGE);
				}
				else {
					for (int i=foldersList.getSelectedIndices().length-1; i>=0;i--)
						listModel.remove(foldersList.getSelectedIndices()[i]);
					int size=listModel.getSize();
					if(size==0) {
						removeFolderButton.setEnabled(false);
					}
				}
			}
		});
		lowerPanel.add(removeFolderButton);
		
		JButton addFolderButton = new JButton("Add folder(s) with tests");
		addFolderButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		addFolderButton.setBounds(10, 119, 183, 23);
		addFolderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(lastBrowsedPath));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setMultiSelectionEnabled(true);
				chooser.setAcceptAllFileFilterUsed(false);
				
				int returnVal = chooser.showDialog(frame.getContentPane(), "Add folder");
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					File[] folders=chooser.getSelectedFiles();
					for(File folder : folders) {
						listModel.addElement(folder.getAbsolutePath());
						if(listModel.size()==1)
							removeFolderButton.setEnabled(true);
					}
					lastBrowsedPath=folders[folders.length-1].getAbsolutePath();
					lastBrowsedPath=lastBrowsedPath.concat("//..");
				}
			}
		});
		lowerPanel.add(addFolderButton);
		
		JButton generateTestToRunFileButton = new JButton("Generate _TestToRun File");
		generateTestToRunFileButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		generateTestToRunFileButton.setBounds(10, 187, 183, 23);
		generateTestToRunFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(testToRunFileNameTextField.getText()==null || testToRunFileNameTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
						    "The name of the _TestToRun file is empty.\nTry specifying a name!",
						    "_TestToRun file name error",
						    JOptionPane.ERROR_MESSAGE);
				}
				else if(testToRunFileSaveLocationTextField.getText()==null || testToRunFileSaveLocationTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
						    "The path where the _TestToRun file will be saved is empty.\nTry specifying a path!",
						    "_TestToRun file save location error",
						    JOptionPane.ERROR_MESSAGE);
				}
				else {
					File path = new File(testToRunFileSaveLocationTextField.getText());
					if(!path.exists()) {
						JOptionPane.showMessageDialog(frame,
							    "The path where the _TestToRun file will be saved is incorrect.\nTry choosing a different save path!",
							    "_TestToRun file save location error",
							    JOptionPane.ERROR_MESSAGE);
					}
					else {
						if(listModel.isEmpty()) {
							JOptionPane.showMessageDialog(frame,
								    "The list of folders with tests is empty.\nTry adding folders to the list!",
								    "No folders with tests added",
								    JOptionPane.ERROR_MESSAGE);
						}
						else {
							String fileName= testToRunFileNameTextField.getText();
							if(!fileName.endsWith(".py")) {
								fileName=fileName+".py";
							}
							TestScriptGenerator.generateTestScript(testToRunFileSaveLocationTextField.getText().concat("\\"+fileName), Arrays.asList(listModel.toArray()).toArray(new String[listModel.getSize()]));
							JOptionPane.showMessageDialog(frame,
								    "The test script was generated successfully!",
								    "Test script generated",
								    JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		lowerPanel.add(generateTestToRunFileButton);
	}
}
