package fr.cactuscata.signgenerator;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import fr.cactuscata.mcjson.SimpleJSONElement;

public class SimpleUIElement extends JLayeredPane {
	private static final long serialVersionUID = 2194672420628809616L;

	public void setSelected(boolean selected) {
		setOpaque(selected);
	}

	public SimpleUIElement(SimpleJSONElement e, int maxWidth, final int line) {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Component c1 = SimpleUIElement.this.getParent().getComponentAt(SimpleUIElement.this.getX(),
						SimpleUIElement.this.getY());
				for (int i = 0; i < SimpleUIElement.this.getParent().getComponentCount(); i++) {
					Component c = SimpleUIElement.this.getParent().getComponent(i);
					if (c.equals(c1)) {
						MainFrame.preview.select(line, i);
						break;
					}
				}
			}
		});
		construct(e, maxWidth);
	}

	public void construct(final SimpleJSONElement e, int maxWidth) {
		removeAll();

		setLayout(null);

		String text = "";
		if (e.getText() != null) {
			text = e.getText();
		} else if (e.getScore() != null) {
			text = "9001";
		} else if (e.getTranslate() != null) {
			text = e.getTranslate();
		} else if (e.getSelector() != null) {
			text = "CrushedPixel";
		}
		if (text.length() == 0) {
			text = " ";
		}
		final JLabel textLbl = new JLabel(text);
		final JLabel boldLbl = new JLabel();

		int w = 0;
		textLbl.setForeground(e.getColor().toColor());
		if (e.isItalic()) {
			textLbl.setFont(MainFrame.minecrafterFont.deriveFont(2, MainFrame.fontSize));
		} else {
			textLbl.setFont(MainFrame.minecrafterFont);
		}
		add(textLbl, new Integer(5));
		textLbl.setLocation(0, 0);
		if (e.isBold()) {
			boldLbl.setText(text);
			if (e.isItalic()) {
				boldLbl.setFont(MainFrame.minecrafterFont.deriveFont(2, MainFrame.fontSize));
			} else {
				boldLbl.setFont(MainFrame.minecrafterFont);
			}
			boldLbl.setForeground(e.getColor().toColor());
			w = getFontMetrics(boldLbl.getFont()).stringWidth(text);
			boldLbl.setSize(new Dimension(w, 22));
			add(boldLbl, new Integer(5));
			boldLbl.setLocation(2, 0);
		}
		FontMetrics metrics = getFontMetrics(textLbl.getFont());

		String inText = text;
		int l = 0;
		while (metrics.stringWidth(text) > maxWidth) {
			text = text.substring(0, text.length() - 1);
			l++;
			if (l > inText.length()) {
				text = "";
				break;
			}
		}
		w = getFontMetrics(textLbl.getFont()).stringWidth(text);
		textLbl.setSize(new Dimension(w, 22));
		if (e.isUnderlined()) {
			Canvas canvas = new Canvas();
			canvas.setBackground(e.getColor().toColor());
			Rectangle r = textLbl.getBounds();
			canvas.setBounds((int) r.getMinX(), (int) r.getMaxY(), (int) r.getWidth() + 2, 3);
			add(canvas);
		}
		if (e.isObfuscated()) {
			Timer t = new Timer();
			t.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					String rand = Utils.randomString(textLbl.getText().length());
					if (e.isBold()) {
						boldLbl.setText(rand);
					}
					textLbl.setText(rand);
				}
			}, 75L, 75L);
		}
		if (e.isBold()) {
			boldLbl.setText(text);
		}
		textLbl.setText(text);

		int width = Math.min(maxWidth, textLbl.getWidth() + 2);

		setPreferredSize(new Dimension(width, 24));
		setSize(new Dimension(width, 24));
		if (e.isStrikethrough()) {
			Canvas canvas = new Canvas();
			canvas.setBackground(e.getColor().toColor());
			Rectangle r = textLbl.getBounds();
			int miny = (int) (r.getMinY() + r.getHeight() / 2.0D);
			canvas.setBounds((int) r.getMinX(), miny + 1, (int) r.getWidth() + 2, 3);
			add(canvas, new Integer(10));
		}
		setBackground(Color.LIGHT_GRAY);
	}
}