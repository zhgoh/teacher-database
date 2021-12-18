package guide.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.swing.table.DefaultTableModel;

public class Database {
	private DefaultTableModel tableModel;

	public Database() {
		tableModel = new DefaultTableModel();
		AddColumn("Name");
	}

	public void ClearDatabase() {
		tableModel.setNumRows(0);
		tableModel.setColumnCount(0);

		AddColumn("Name");
	}

	public void AddColumn(String name) {
		if (name.isBlank()) {
			return;
		}

		name = name.toLowerCase();

		if (tableModel.findColumn(name) == -1) {
			tableModel.addColumn(name);
		}
	}

	public void AddRow(String[] rows) {
		tableModel.addRow(rows);
	}

	public String[] GetColumns() {
		int colCount = tableModel.getColumnCount();
		String[] columns = new String[colCount];
		for (int i = 0; i < colCount; i++) {
			columns[i] = tableModel.getColumnName(i);
		}
		return columns;

	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void LoadFile(File file) {
		tableModel.setNumRows(0);
		tableModel.setColumnCount(0);

		try {
			boolean header = true;
			FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
			try (BufferedReader br = new BufferedReader(fileReader)) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] items = line.split(",");
					if (header) {
						for (String elem : items) {
							tableModel.addColumn(elem);
						}
						header = false;
					} else {
						tableModel.addRow(items);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading file.");
		}
	}

	public void SaveFile(String path) {
		try {
			FileWriter fileWriter = new FileWriter(path, StandardCharsets.UTF_8);
			try {

				final int colCount = tableModel.getColumnCount();
				String sep = "";
				for (int i = 0; i < colCount; i++) {
					String value = (String) tableModel.getColumnName(i);
					fileWriter.write(sep + value);
					sep = ",";
				}
				fileWriter.write('\n');

				for (int i = 0; i < tableModel.getRowCount(); i++) {
					sep = "";
					for (int j = 0; j < colCount; j++) {
						String value = (String) tableModel.getValueAt(i, j);
						fileWriter.write(sep + value);
						sep = ",";
					}
					fileWriter.write('\n');
				}
			} finally {
				fileWriter.flush();
				fileWriter.close();
			}
		} catch (IOException e) {
			System.err.println("Error saving file.");
		}
	}

}
