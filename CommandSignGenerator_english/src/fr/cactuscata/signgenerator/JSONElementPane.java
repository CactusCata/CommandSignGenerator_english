package fr.cactuscata.signgenerator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import fr.cactuscata.mcjson.MCColor;
import fr.cactuscata.mcjson.MCScore;
import fr.cactuscata.mcjson.SimpleJSONElement;

public class JSONElementPane extends JPanel {
	private static MouseListener mouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			MainFrame.preview.applyCurrentElement();
		}

		public void mouseReleased(MouseEvent e) {
			MainFrame.preview.applyCurrentElement();
		}
	};
	private static final long serialVersionUID = -6644639999597980501L;
	public JTabbedPane mainTabPanel;
	public JTextField textText;
	public JTextField scorePlayerText;
	public JTextField scoreObjectiveText;
	public JTextField selectorSelectorText;
	public JComboBox<Object> colorCombo;
	public JCheckBox checkBold;
	public JCheckBox checkUnderlined;
	public JCheckBox checkObfuscated;
	public JCheckBox checkStrikethrough;
	public JCheckBox checkItalic;
	public JTextField insertionText;
	public JButton btnChangeHoverevent;
	public JLabel scoreValueError;
	public JLabel selectorValueError;
	public JLabel translateValueError;
	public JTextField translateKeyText;

	public void applyElement(SimpleJSONElement element) {
		for (int i = 0; i < MCColor.values().length; i++) {
			if (MCColor.values()[i] == element.getColor()) {
				this.colorCombo.setSelectedIndex(i);
				break;
			}
		}
		this.checkBold.setSelected(element.isBold());
		this.checkItalic.setSelected(element.isItalic());
		this.checkObfuscated.setSelected(element.isObfuscated());
		this.checkStrikethrough.setSelected(element.isStrikethrough());
		this.checkUnderlined.setSelected(element.isUnderlined());

		this.textText.setText("");
		this.scoreObjectiveText.setText("");
		this.scorePlayerText.setText("");
		this.selectorSelectorText.setText("");
		this.translateKeyText.setText("");
		if (element.getText() != null) {
			this.mainTabPanel.setSelectedIndex(0);
			this.textText.setText(element.getText());
		} else if (element.getScore() != null) {
			this.mainTabPanel.setSelectedIndex(1);
			this.scorePlayerText.setText(element.getScore().getName());
			this.scoreObjectiveText.setText(element.getScore().getObjective());
		} else if (element.getSelector() != null) {
			this.mainTabPanel.setSelectedIndex(2);
			this.selectorSelectorText.setText(element.getSelector());
		} else if (element.getTranslate() != null) {
			this.mainTabPanel.setSelectedIndex(3);
			this.translateKeyText.setText(element.getTranslate());
		}
	}

	public SimpleJSONElement getElement() {
		MCColor color = MCColor.valueOf(this.colorCombo.getSelectedItem().toString());
		boolean bold = this.checkBold.isSelected();
		boolean italic = this.checkItalic.isSelected();
		boolean underlined = this.checkUnderlined.isSelected();
		boolean strikethrough = this.checkStrikethrough.isSelected();
		boolean obfuscated = this.checkObfuscated.isSelected();

		int sel = this.mainTabPanel.getSelectedIndex();

		SimpleJSONElement element = null;
		switch (sel) {
		case 0:
			element = new SimpleJSONElement(this.textText.getText(), bold, italic, strikethrough, underlined,
					obfuscated, color);
			break;
		case 1:
			MCScore score = new MCScore(this.scorePlayerText.getText().trim(),
					this.scoreObjectiveText.getText().trim());
			element = new SimpleJSONElement(score, bold, italic, strikethrough, underlined, obfuscated, color);
			break;
		case 2:
			element = new SimpleJSONElement(bold, italic, strikethrough, underlined, obfuscated,
					this.selectorSelectorText.getText().trim(), color);
			break;
		case 3:
			element = new SimpleJSONElement(bold, italic, strikethrough, underlined, obfuscated, color,
					this.translateKeyText.getText().trim());
		}
		return element;
	}

	public JSONElementPane() {
		setBounds(5, 40, 319, 293);
		setLayout(null);
		setVisible(true);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(6, 6, 307, 279);
		add(mainPanel);

		this.mainTabPanel = new JTabbedPane(1);
		this.mainTabPanel.setBounds(6, 6, 295, 135);
		this.mainTabPanel.addMouseListener(mouseListener);

		JPanel tabTextPanel = new JPanel();
		this.mainTabPanel.addTab("Text", null, tabTextPanel, null);
		tabTextPanel.setLayout(null);

		JLabel lblText = new JLabel("Text:");
		lblText.setBounds(6, 12, 32, 16);
		tabTextPanel.add(lblText);

		this.textText = new JTextField();
		this.textText.setBounds(49, 6, 219, 28);
		tabTextPanel.add(this.textText);
		this.textText.setColumns(10);
		this.textText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				MainFrame.preview.applyCurrentElement();
			}
		});
		JPanel tabScorePanel = new JPanel();
		this.mainTabPanel.addTab("Score", null, tabScorePanel, null);
		tabScorePanel.setLayout(null);
		tabScorePanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Player:");
		lblNewLabel.setBounds(6, 12, 79, 16);
		tabScorePanel.add(lblNewLabel);

		this.scorePlayerText = new JTextField();
		this.scorePlayerText.setBounds(86, 6, 182, 28);
		tabScorePanel.add(this.scorePlayerText);
		this.scorePlayerText.setColumns(10);
		this.scorePlayerText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				JSONElementPane.this.scoreValueError
						.setVisible((JSONElementPane.this.scorePlayerText.getText().trim().length() == 0)
								|| (JSONElementPane.this.scoreObjectiveText.getText().trim().length() == 0));
				MainFrame.preview.applyCurrentElement();
			}
		});
		JLabel lblObjective = new JLabel("Objective:");
		lblObjective.setBounds(6, 40, 79, 16);
		tabScorePanel.add(lblObjective);

		this.scoreObjectiveText = new JTextField();
		this.scoreObjectiveText.setBounds(86, 34, 182, 28);
		tabScorePanel.add(this.scoreObjectiveText);
		this.scoreObjectiveText.setColumns(10);
		this.scoreObjectiveText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				JSONElementPane.this.scoreValueError
						.setVisible((JSONElementPane.this.scorePlayerText.getText().trim().length() == 0)
								|| (JSONElementPane.this.scoreObjectiveText.getText().trim().length() == 0));
				MainFrame.preview.applyCurrentElement();
			}
		});
		this.scoreValueError = new JLabel("Values must not be empty!");
		this.scoreValueError.setForeground(Color.RED);
		this.scoreValueError.setBounds(6, 68, 182, 16);
		tabScorePanel.add(this.scoreValueError);

		JPanel tabSelectorPanel = new JPanel();
		this.mainTabPanel.addTab("Selector", null, tabSelectorPanel, null);
		tabSelectorPanel.setLayout(null);

		JLabel lblSelector = new JLabel("Selector:");
		lblSelector.setBounds(6, 12, 54, 16);
		tabSelectorPanel.add(lblSelector);

		this.selectorSelectorText = new JTextField();
		this.selectorSelectorText.setBounds(72, 6, 196, 28);
		tabSelectorPanel.add(this.selectorSelectorText);
		this.selectorSelectorText.setColumns(10);
		this.selectorSelectorText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				JSONElementPane.this.selectorValueError
						.setVisible(JSONElementPane.this.selectorSelectorText.getText().trim().length() == 0);
				MainFrame.preview.applyCurrentElement();
			}
		});
		this.selectorValueError = new JLabel("Selector value must not be empty!");
		this.selectorValueError.setForeground(Color.RED);
		this.selectorValueError.setBounds(6, 40, 214, 16);
		tabSelectorPanel.add(this.selectorValueError);

		JPanel tabTranslatePanel = new JPanel();
		this.mainTabPanel.addTab("Translate", null, tabTranslatePanel, null);
		tabTranslatePanel.setLayout(null);

		this.translateValueError = new JLabel("Translate value must not be empty!");
		this.translateValueError.setForeground(Color.RED);
		this.translateValueError.setHorizontalAlignment(2);
		this.translateValueError.setBounds(6, 40, 222, 16);
		tabTranslatePanel.add(this.translateValueError);

		this.translateKeyText = new TranslateTextField();
		this.translateKeyText.setBounds(46, 6, 222, 28);
		tabTranslatePanel.add(this.translateKeyText);
		this.translateKeyText.setColumns(10);
		this.translateKeyText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				JSONElementPane.this.translateValueError
						.setVisible(JSONElementPane.this.translateKeyText.getText().trim().length() == 0);
				MainFrame.preview.applyCurrentElement();
			}
		});
		JLabel lblTranslationKey = new JLabel("Key:");
		lblTranslationKey.setBounds(6, 12, 101, 16);
		tabTranslatePanel.add(lblTranslationKey);
		
		JLabel linkLabel = new JLabel("List of all keys");
		linkLabel.setLocation(20, 60);
		linkLabel.setSize(164, 33);
		linkLabel.setFont(new Font("Lucida Grande", 2, 12));
		if (Utils.isBrowsingSupported()) {
			Utils.makeLinkable(linkLabel,
					new Utils.LinkMouseListener("http://www.mediafire.com/file/f9lvil2d6purajw/EN.lang.txt"));
		}
		tabTranslatePanel.add(linkLabel);

		JPanel panel_6 = new JPanel();
		panel_6.setBounds(6, 142, 295, 23);
		panel_6.setLayout(null);

		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(6, 0, 38, 23);
		panel_6.add(lblColor);

		this.colorCombo = new JComboBox<Object>();
		DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>(MCColor.values());
		this.colorCombo.setModel(model);
		this.colorCombo.setSelectedIndex(0);
		this.colorCombo.addMouseListener(mouseListener);
		this.colorCombo.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				MainFrame.preview.applyCurrentElement();
			}

			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
		this.colorCombo.setBounds(56, 0, 239, 23);
		panel_6.add(this.colorCombo);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(6, 177, 295, 23);
		panel_7.setLayout(new GridLayout(1, 0, 0, 0));

		this.checkBold = new JCheckBox("Bold");
		panel_7.add(this.checkBold);
		mainPanel.setLayout(null);
		mainPanel.add(this.mainTabPanel);
		mainPanel.add(panel_6);
		mainPanel.add(panel_7);
		this.checkBold.addMouseListener(mouseListener);

		this.checkUnderlined = new JCheckBox("Underlined");
		panel_7.add(this.checkUnderlined);
		this.checkUnderlined.addMouseListener(mouseListener);

		JPanel panel_8 = new JPanel();
		panel_8.setBounds(6, 212, 295, 23);
		mainPanel.add(panel_8);
		panel_8.setLayout(new GridLayout(1, 0, 0, 0));

		this.checkItalic = new JCheckBox("Italic");
		panel_8.add(this.checkItalic);
		this.checkItalic.addMouseListener(mouseListener);

		this.checkObfuscated = new JCheckBox("Obfuscated");
		panel_8.add(this.checkObfuscated);
		this.checkObfuscated.addMouseListener(mouseListener);

		this.checkStrikethrough = new JCheckBox("Strikethrough");
		this.checkStrikethrough.setBounds(6, 247, 295, 23);
		this.checkStrikethrough.addMouseListener(mouseListener);
		mainPanel.add(this.checkStrikethrough);
	}
}
