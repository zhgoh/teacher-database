package guide.gui.forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import guide.gui.Database;

public class MainForm {

	private JFrame frmCharacterGuide;
	private JTable table;
	
	private Database database;
	private RowView rowView;
	private JPanel rowPanel;
	private JTextField columnText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmCharacterGuide.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		database = new Database();
		
		
		frmCharacterGuide = new JFrame();
		frmCharacterGuide.setTitle("Student Database");
		frmCharacterGuide.setBounds(100, 100, 799, 532);
		frmCharacterGuide.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frmCharacterGuide.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
					.addGap(11))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable();
		table.setModel(database.getTableModel());
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Column", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				database.AddColumn(columnText.getText());
				columnText.setText("");
				rowView.Update();
			}
		});
		panel.add(btnNewButton, BorderLayout.SOUTH);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Column Name:");
		panel_3.add(lblNewLabel);
		
		columnText = new JTextField();
		columnText.setColumns(10);
		panel_3.add(columnText);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Row", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton_3 = new JButton("Add");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowView.Add();
			}
		});
		panel_2.add(btnNewButton_3, BorderLayout.SOUTH);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_2.add(scrollPane_3, BorderLayout.CENTER);
		
		rowPanel = new JPanel();
		scrollPane_3.setViewportView(rowPanel);
		rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
		frmCharacterGuide.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmCharacterGuide.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				database.ClearDatabase();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("CSV file", new String[] {"csv"});
				fileChooser.setFileFilter(filter);
				fileChooser.addChoosableFileFilter(filter);
	            int option = fileChooser.showOpenDialog(rowPanel);
	            if(option == JFileChooser.APPROVE_OPTION){
	               File file = fileChooser.getSelectedFile();
	               database.LoadFile(file);
	            }
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Save");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("CSV file", new String[] {"csv"});
				fileChooser.setFileFilter(filter);
				fileChooser.addChoosableFileFilter(filter);
	            int option = fileChooser.showSaveDialog(rowPanel);
	            if(option == JFileChooser.APPROVE_OPTION){
	            	File file = fileChooser.getSelectedFile();
	            	
	            	// get the full path of the file
	            	String absolutePath = file.getAbsolutePath();  

	            	// does the selected file have an extension of CSV?
	            	// if yes then exclude the extension, if no, then add .csv to the file name
	            	if (!absolutePath.endsWith(".csv")) {
	            		absolutePath += ".csv";
	            	}
	            	database.SaveFile(absolutePath);
	            }
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Exit");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_4);
		
		rowView = new RowView(rowPanel, database);
		rowView.Update();
	}
	protected JPanel getRowPanel() {
		return rowPanel;
	}
}
