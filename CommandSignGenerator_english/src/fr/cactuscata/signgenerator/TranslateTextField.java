package fr.cactuscata.signgenerator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class TranslateTextField extends JTextField {
	private static final long serialVersionUID = 1L;

	public TranslateTextField() {
		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if ((!Character.isLetter(c)) && (!Character.isDigit(c)) && (c != '.')) {
					e.consume();
				}
			}
		});
	}
}
