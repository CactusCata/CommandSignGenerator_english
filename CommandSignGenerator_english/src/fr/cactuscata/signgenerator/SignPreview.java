package fr.cactuscata.signgenerator;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import fr.cactuscata.mcjson.MCColor;
import fr.cactuscata.mcjson.MCSignLine;
import fr.cactuscata.mcjson.SimpleJSONElement;

public class SignPreview extends JPanel {
	private static final long serialVersionUID = 5878301947141694896L;
	private static JLayeredPane panelLine1;
	private static JLayeredPane panelLine2;
	private static JLayeredPane panelLine3;
	private static JLayeredPane panelLine4;
	private static List<JLayeredPane> linePanels = new ArrayList<>();

	public void applyCurrentElement() {
		SimpleJSONElement e = MainFrame.elementPane.getElement();
		MainFrame.updateElement(e);
		MainFrame.updateJSON();
	}

	public void selectFirst() {
		try {
			((SimpleUIElement) panelLine1.getComponent(0)).setSelected(true);
		} catch (Exception localException) {
		}
	}

	public void select(int row, int element) {
		try {
			deselectAll();

			MainFrame.selected = new Pair<Integer, Integer>(row, element);

			JLayeredPane pane = (JLayeredPane) linePanels.get(row);
			SimpleUIElement ui = (SimpleUIElement) pane.getComponent(element);
			MCSignLine l = MainFrame.sign.getLine(row);

			SimpleJSONElement e = (SimpleJSONElement) l.get(element);
			ui.setSelected(true);
			MainFrame.elementPane.applyElement(e);
			MainFrame.clickPane.setClickEvent(MainFrame.sign.getClickEvent(row));
			repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deselectAll() {
		JLayeredPane pane;
		Iterator<JLayeredPane> localIterator = linePanels.iterator();
		for (int i = 1; i < 5; i++) {
			pane = (JLayeredPane) localIterator.next();
			Component c = pane.getComponent(0);
			if (c instanceof SimpleUIElement) {
				((SimpleUIElement) c).setSelected(false);

			}
		}

	}

	public SignPreview() {
		setBounds(100, 100, 256, 128);
		setLayout(null);

		panelLine1 = new JLayeredPane();
		panelLine1.setBounds(4, 8, 248, 24);
		add(panelLine1);
		FlowLayout fl_panelLine1 = new FlowLayout(1, 0, 0);
		panelLine1.setLayout(fl_panelLine1);

		panelLine2 = new JLayeredPane();
		panelLine2.setBounds(4, 37, 248, 24);
		add(panelLine2);
		panelLine2.setLayout(new FlowLayout(1, 0, 0));

		panelLine3 = new JLayeredPane();
		panelLine3.setBounds(4, 67, 248, 24);
		add(panelLine3);
		panelLine3.setLayout(new FlowLayout(1, 0, 0));

		panelLine4 = new JLayeredPane();
		panelLine4.setBounds(4, 96, 248, 24);
		add(panelLine4);
		panelLine4.setLayout(new FlowLayout(1, 0, 0));

		JLabel labelSgn = new JLabel("");
		labelSgn.setIcon(new ImageIcon(SignPreview.class.getResource("/res/sign_text.png")));
		labelSgn.setBounds(0, 0, 256, 128);
		add(labelSgn);

		linePanels.add(panelLine1);
		linePanels.add(panelLine2);
		linePanels.add(panelLine3);
		linePanels.add(panelLine4);

		MCSignLine line = new MCSignLine();
		line.add(new SimpleJSONElement("Add text", false, false, false, false, false, MCColor.black));

		setTextLine(0, line);
		setTextLine(1, line);
		setTextLine(2, line);
		setTextLine(3, line);
	}

	public void setTextLine(int id, MCSignLine line) {
		MainFrame.sign.setLine(id, line);
		JLayeredPane pane = (JLayeredPane) linePanels.get(id);
		pane.removeAll();
		int w = 0;
		for (SimpleJSONElement e : line) {
			SimpleUIElement el = new SimpleUIElement(e, 230 - w, id);
			w += el.getWidth();
			pane.add(el);
		}
		pane.revalidate();
		pane.repaint();
	}
}
