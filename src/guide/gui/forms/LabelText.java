package guide.gui.forms;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.*;

public class LabelText extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7312162445257784269L;
	private JTextField textField;
	
	public LabelText(String text) {
		setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		textField = new JTextField(10);
		JLabel label = new JLabel(text);
		
        add(label);
        add(textField);
	}
	
	public LabelText() {
		this("Test");
	}
	
	public String GetText() {
		return textField.getText();
	}
	
	public void ClearText() {
		textField.setText("");
	}
	
	@Override
	public Dimension getMaximumSize() {
		Dimension size = super.getMaximumSize();
		size.height = getPreferredSize().height;
		return size;
	}
}