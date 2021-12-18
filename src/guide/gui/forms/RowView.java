package guide.gui.forms;

import java.util.ArrayList;

import javax.swing.JPanel;

import guide.gui.Database;

public class RowView {
	private JPanel rowPanel;
	private Database database;
	private ArrayList<LabelText> labelTexts;
	
	public RowView(JPanel jpanel, Database database) {
		this.rowPanel = jpanel;
		this.database = database;
		this.labelTexts = new ArrayList<LabelText>();
	}
	
	public void Update() {
		rowPanel.removeAll();
		labelTexts.clear();
		
		for (String col : database.GetColumns()) {
			LabelText labelText = new LabelText(col);
			labelTexts.add(labelText);
			rowPanel.add(labelText);
		}
		rowPanel.revalidate();
	}
	
	public void Add() {
		String[] allInputs = new String[labelTexts.size()];
		for (int i = 0; i < labelTexts.size(); i++) {
			LabelText labelText = labelTexts.get(i);
			allInputs[i] = labelText.GetText();
			labelText.ClearText();
		}
		
		database.AddRow(allInputs);
	}
}
