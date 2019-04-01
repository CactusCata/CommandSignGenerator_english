package fr.cactuscata.signgenerator;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import fr.cactuscata.mcjson.MCClickAction;
import fr.cactuscata.mcjson.MCClickEvent;

public class ClickEventPane extends JPanel {
	private static final long serialVersionUID = 827196439544878375L;
	private JTextField textField;
	private JComboBox<?> comboBox;
	private JCheckBox checkBox;

	public void setClickEvent(MCClickEvent event) {
		this.comboBox.setEnabled(event != null);
		this.textField.setEnabled(event != null);
		if (event == null) {
			this.checkBox.setSelected(false);
			this.comboBox.setSelectedIndex(0);
			this.textField.setText("");
		} else {
			this.checkBox.setSelected(true);
			MCClickAction[] actions = MCClickAction.values();
			for (int i = 0; i < actions.length; i++) {
				if (actions[i] == event.getAction()) {
					this.comboBox.setSelectedIndex(i);
					break;
				}
			}
			this.textField.setText(event.getValue());
		}
	}

	public MCClickEvent getClickEvent() {
		if (this.checkBox.isSelected()) {
			return new MCClickEvent(MCClickAction.valueOf(this.comboBox.getSelectedItem().toString()),
					this.textField.getText());
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ClickEventPane() {
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EtchedBorder(1, null, null));
		panel.setBounds(6, 41, 295, 64);
		add(panel);

		JLabel label = new JLabel("Action:");
		label.setBounds(6, 10, 45, 16);
		panel.add(label);

		this.comboBox = new JComboBox<>();
		this.comboBox.setModel(new DefaultComboBoxModel(MCClickAction.values()));
		this.comboBox.setEnabled(false);
		this.comboBox.setBounds(63, 6, 226, 27);
		panel.add(this.comboBox);
		this.comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				MainFrame.updateClickEvent(ClickEventPane.this.getClickEvent());
			}

			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
		JLabel label_1 = new JLabel("Value:");
		label_1.setBounds(6, 38, 45, 16);
		panel.add(label_1);

		this.textField = new JTextField();
		this.textField.setEnabled(false);
		this.textField.setColumns(10);
		this.textField.setBounds(63, 32, 226, 28);
		panel.add(this.textField);
		this.textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				MainFrame.updateClickEvent(ClickEventPane.this.getClickEvent());
			}
		});
		this.checkBox = new JCheckBox("clickEvent");
		this.checkBox.setBounds(6, 6, 106, 23);
		add(this.checkBox);
		this.checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClickEventPane.this.comboBox.setEnabled(ClickEventPane.this.checkBox.isSelected());
				ClickEventPane.this.textField.setEnabled(ClickEventPane.this.checkBox.isSelected());

				MainFrame.updateClickEvent(ClickEventPane.this.getClickEvent());
			}
		});
		JLabel lblOnlyOneClickevent = new JLabel("Only one clickEvent per line possible");
		lblOnlyOneClickevent.setFont(new Font("Lucida Grande", 0, 10));
		lblOnlyOneClickevent.setBounds(124, 6, 177, 16);
		add(lblOnlyOneClickevent);

		JLabel lblAllClickeventsAre = new JLabel("All clickEvents are run at once");
		lblAllClickeventsAre.setFont(new Font("Lucida Grande", 0, 10));
		lblAllClickeventsAre.setBounds(124, 23, 177, 16);
		add(lblAllClickeventsAre);
	}
}