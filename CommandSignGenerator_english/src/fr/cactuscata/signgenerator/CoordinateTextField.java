package fr.cactuscata.signgenerator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class CoordinateTextField extends JTextField {
	private static final long serialVersionUID = 1L;

	public CoordinateTextField() {
		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if ((c != '~') && (c != '-') && (!Character.isDigit(c))) {
					e.consume();
				} else if (((Character.isDigit(c)) || (c == '-'))
						&& (CoordinateTextField.this.getText().startsWith("~"))
						&& (CoordinateTextField.this.getCaretPosition() == 0)) {
					e.consume();
				} else if ((CoordinateTextField.this.getText().contains("~")) && (c == '~')) {
					e.consume();
				} else if ((c == '~') && (CoordinateTextField.this.getCaretPosition() != 0)) {
					e.consume();
				} else if ((c == '-') && (CoordinateTextField.this.getCaretPosition() != 0)
						&& ((!CoordinateTextField.this.getText().contains("~"))
								|| (CoordinateTextField.this.getCaretPosition() != 1))) {
					e.consume();
				}
			}
		});
	}
}
