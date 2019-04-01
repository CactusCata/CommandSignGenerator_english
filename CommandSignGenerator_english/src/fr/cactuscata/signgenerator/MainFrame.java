package fr.cactuscata.signgenerator;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import fr.cactuscata.mcjson.MCClickEvent;
import fr.cactuscata.mcjson.MCSign;
import fr.cactuscata.mcjson.MCSignLine;
import fr.cactuscata.mcjson.SimpleJSONElement;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 5677592725661861551L;
	public static int fontSize = 20;
	public static Font minecrafterFont;
	public static JPanel contentPane;
	private static JTextPane jsonPane;
	public static Pair<Integer, Integer> selected = new Pair<Integer, Integer>(0, 0);
	public static MCSign sign = new MCSign();
	public static JSONElementPane elementPane;
	public static ClickEventPane clickPane;
	public static SignPreview preview;
	private static JTextField textField;
	private static JTextField textField_1;
	private static JTextField textField_2;
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	private static Clipboard clipboard = toolkit.getSystemClipboard();

	public static void updateClickEvent(MCClickEvent e) {
		sign.setClickEvent(((Integer) selected.left).intValue(), e);
		updateJSON();
	}

	/* Error */
	public static void main(String[] args) {
		minecrafterFont = new Font("Minecraftia", 0, fontSize);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		InputStream is = null;
		try {
			is = MainFrame.class.getResourceAsStream("/res/Minecraftia.ttf");
			minecrafterFont = Font.createFont(0, is);
			minecrafterFont = minecrafterFont.deriveFont(0, fontSize);
		} catch (Exception e) {
			e.printStackTrace();

			if (is != null) {
				try {
					is.close();
				} catch (Exception localException1) {
				}
			}
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception localException2) {
				}
			}
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	public MainFrame() {
		setResizable(false);
		setDefaultCloseOperation(3);
		setBounds(100, 100, 600, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		preview = new SignPreview();
		preview.setBounds(338, 53, 256, 128);
		contentPane.add(preview);

		JLabel lblMinecraftJsonSign = new JLabel("Minecraft JSON Sign Generator");
		lblMinecraftJsonSign.setHorizontalAlignment(0);
		lblMinecraftJsonSign.setBounds(6, 6, 588, 35);
		lblMinecraftJsonSign.setFont(minecrafterFont.deriveFont(22.0F));
		contentPane.add(lblMinecraftJsonSign);
		preview.setVisible(true);

		elementPane = new JSONElementPane();
		contentPane.add(elementPane);

		JLabel lblMc = new JLabel("MC 1.8/1.9/1.10/1.11");
		lblMc.setVerticalAlignment(1);
		lblMc.setHorizontalAlignment(11);
		lblMc.setBounds(460, 2, 125, 22);
		lblMc.setFont(minecrafterFont.deriveFont(8.0F));
		contentPane.add(lblMc);

		JButton btnAddElement = new JButton("Add");
		btnAddElement.setBounds(338, 193, 125, 29);
		contentPane.add(btnAddElement);
		btnAddElement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MCSignLine line = MainFrame.sign.getLine(((Integer) MainFrame.selected.left).intValue());
				line.add(((Integer) MainFrame.selected.right).intValue() + 1, new SimpleJSONElement());
				MainFrame.sign.setLine(((Integer) MainFrame.selected.left).intValue(), line);
				MainFrame.selected.right = Integer.valueOf(((Integer) MainFrame.selected.right).intValue() + 1);
				MainFrame.preview.setTextLine(((Integer) MainFrame.selected.left).intValue(), line);
				MainFrame.preview.select(((Integer) MainFrame.selected.left).intValue(),
						((Integer) MainFrame.selected.right).intValue());
			}
		});
		JButton btnRemoveElement = new JButton("Remove");
		btnRemoveElement.setBounds(469, 193, 125, 29);
		contentPane.add(btnRemoveElement);
		btnRemoveElement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MCSignLine line = MainFrame.sign.getLine(((Integer) MainFrame.selected.left).intValue());
				line.remove(((Integer) MainFrame.selected.right).intValue());
				if (((Integer) MainFrame.selected.right).intValue() >= line.size()) {
					MainFrame.selected.right = Integer.valueOf(line.size() - 1);
				}
				if (line.size() <= 0) {
					line.add(new SimpleJSONElement());
					MainFrame.selected.right = Integer.valueOf(0);
				}
				MainFrame.sign.setLine(((Integer) MainFrame.selected.left).intValue(), line);
				MainFrame.preview.setTextLine(((Integer) MainFrame.selected.left).intValue(), line);
				MainFrame.preview.select(((Integer) MainFrame.selected.left).intValue(),
						((Integer) MainFrame.selected.right).intValue());
			}
		});
		JButton btnCopyToClipboard = new JButton("Copy to Clipboard");
		btnCopyToClipboard.setBounds(338, 221, 256, 29);
		btnCopyToClipboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.clipboard.setContents(new StringSelection(MainFrame.jsonPane.getText()), null);
			}
		});
		contentPane.add(btnCopyToClipboard);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(338, 296, 256, 128);
		contentPane.add(scrollPane);

		jsonPane = new JTextPane();
		jsonPane.setEditable(false);
		scrollPane.setViewportView(jsonPane);

		elementPane.setBounds(6, 36, 307, 280);

		JLabel lblMinecraftJsonBook_1 = new JLabel(
				"Minecraft JSON Sign Generator by CrushedPixel and update by CactusCata - ");
		lblMinecraftJsonBook_1.setBounds(20, 455, 350, 32);
		contentPane.add(lblMinecraftJsonBook_1);
		lblMinecraftJsonBook_1.setHorizontalAlignment(2);
		lblMinecraftJsonBook_1.setFont(new Font("Lucida Grande", 0, 9));

		JLabel linkLabel = new JLabel("http://youtube.com/CrushedPixel");
		linkLabel.setLocation(353, 456);
		linkLabel.setSize(164, 33);
		linkLabel.setFont(new Font("Lucida Grande", 0, 10));
		if (Utils.isBrowsingSupported()) {
			Utils.makeLinkable(linkLabel, new Utils.LinkMouseListener("http://youtube.com/CrushedPixel"));
		}
		contentPane.add(linkLabel);

		JLabel lblSupp = new JLabel("If this tool is useful, feel free to support my work by donating on PayPal:");
		lblSupp.setHorizontalAlignment(0);
		lblSupp.setBounds(5, 436, 485, 16);
		contentPane.add(lblSupp);

		JLabel lblDonate = new JLabel();
		lblDonate.setHorizontalAlignment(0);
		lblDonate.setIcon(new ImageIcon(MainFrame.class.getResource("/res/btn_donate_LG.gif")));
		lblDonate.setBounds(478, 422, 116, 45);
		lblDonate.setCursor(new Cursor(12));
		lblDonate.addMouseListener(new Utils.LinkMouseListener(
				"https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=B8XRXQNP5QW7J"));
		contentPane.add(lblDonate);

		clickPane = new ClickEventPane();
		clickPane.setBounds(12, 318, 303, 110);
		contentPane.add(clickPane);

		JLabel lblX = new JLabel("x");
		lblX.setBounds(348, 262, 8, 16);
		contentPane.add(lblX);

		textField = new CoordinateTextField();
		textField.setText("~");
		textField.setBounds(361, 256, 43, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				MainFrame.preview.applyCurrentElement();
			}
		});
		JLabel lblY = new JLabel("y");
		lblY.setBounds(434, 262, 8, 16);
		contentPane.add(lblY);

		textField_1 = new CoordinateTextField();
		textField_1.setText("~1");
		textField_1.setColumns(10);
		textField_1.setBounds(447, 256, 43, 28);
		textField_1.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				MainFrame.preview.applyCurrentElement();
			}
		});
		contentPane.add(textField_1);

		JLabel lblZ = new JLabel("z");
		lblZ.setBounds(525, 262, 8, 16);
		contentPane.add(lblZ);

		textField_2 = new CoordinateTextField();
		textField_2.setText("~");
		textField_2.setColumns(10);
		textField_2.setBounds(538, 256, 43, 28);
		contentPane.add(textField_2);
		textField_2.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				MainFrame.preview.applyCurrentElement();
			}
		});
		preview.select(0, 0);

		preview.applyCurrentElement();
	}

	public static String getCoordinateString() {
		StringBuilder sb = new StringBuilder();
		if (textField.getText().isEmpty()) sb.append("~");
		else sb.append(textField.getText());

		sb.append(" ");
		if (textField_1.getText().isEmpty()) sb.append("~1");
		else sb.append(textField_1.getText());

		sb.append(" ");
		if (textField_2.getText().isEmpty()) sb.append("~");
		else sb.append(textField_2.getText());

		return sb.toString();
	}

	public static void updateJSON() {
		jsonPane.setText(sign.toString());
	}

	public static void updateElement(SimpleJSONElement element) {
		MCSignLine line = sign.getLine(((Integer) selected.left).intValue());
		if (line.size() >= ((Integer) selected.right).intValue() + 1) {
			line.set(((Integer) selected.right).intValue(), element);
		} else {
			line.add(element);
			selected.right = Integer.valueOf(line.size() - 1);
		}
		preview.setTextLine(((Integer) selected.left).intValue(), line);
		preview.select(((Integer) selected.left).intValue(), ((Integer) selected.right).intValue());
	}
}
