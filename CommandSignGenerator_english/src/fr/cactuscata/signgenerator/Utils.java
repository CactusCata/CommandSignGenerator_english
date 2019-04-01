package fr.cactuscata.signgenerator;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import fr.cactuscata.mcjson.MCSignLine;
import fr.cactuscata.mcjson.SimpleJSONElement;

public class Utils {
	private static String chars = "abcdefghijklmnopqrstuvwxyz _!?=()/&%$�";
	private static Random r = new Random();

	public static class LinkMouseListener extends MouseAdapter {
		private String url = null;

		public LinkMouseListener(String url) {
			this.url = url;
		}

		public void mouseClicked(MouseEvent evt) {
			JLabel l = (JLabel) evt.getSource();
			try {
				URI uri = new URI(this.url);
				new Utils.LinkRunner(uri).execute();
			} catch (URISyntaxException use) {
				throw new AssertionError(use + ": " + l.getText());
			}
		}
	}

	private static class LinkRunner extends SwingWorker<Void, Void> {
		private final URI uri;

		private LinkRunner(URI u) {
			if (u == null) {
				throw new NullPointerException();
			}
			this.uri = u;
		}

		protected Void doInBackground() throws Exception {
			Desktop desktop = Desktop.getDesktop();
			desktop.browse(this.uri);
			return null;
		}

		protected void done() {
			try {
				get();
			} catch (ExecutionException ee) {
				handleException(this.uri, ee);
			} catch (InterruptedException ie) {
				handleException(this.uri, ie);
			}
		}

		private static void handleException(URI u, Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Sorry, a problem occurred while trying to open this link in your system's standard browser.",
					"A problem occured", 0);
		}
	}

	private static String linkIfy(String s) {
		return "<a href=\"".concat(s).concat("\">").concat(s).concat("</a>");
	}

	private static String htmlIfy(String s) {
		return "<html>".concat(s).concat("</html>");
	}

	public static void makeLinkable(JLabel c, MouseListener ml) {
		assert (ml != null);
		c.setText(htmlIfy(linkIfy(c.getText())));
		c.setCursor(new Cursor(12));
		c.addMouseListener(ml);
	}

	public static boolean isBrowsingSupported() {
		if (!Desktop.isDesktopSupported()) {
			return false;
		}
		boolean result = false;
		Desktop desktop = Desktop.getDesktop();
		if (desktop.isSupported(Desktop.Action.BROWSE)) {
			result = true;
		}
		return result;
	}

	public static String randomString(int length) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int x = r.nextInt(chars.length());
			String str = chars.substring(x, x + 1);
			if (r.nextBoolean()) {
				str = str.toUpperCase();
			}
			b.append(str);
		}
		return b.toString();
	}

	public static TreePath getPath(TreeNode treeNode) {
		List<Object> nodes = new ArrayList<>();
		if (treeNode != null) {
			nodes.add(treeNode);
			treeNode = treeNode.getParent();
			while (treeNode != null) {
				nodes.add(0, treeNode);
				treeNode = treeNode.getParent();
			}
		}
		return nodes.isEmpty() ? null : new TreePath(nodes.toArray());
	}

	public static String getNodeNameEmpty(String key, Object value) {
		String str = "<empty>";
		if ((value != null) && (value.toString().trim().length() > 0)) {
			str = value.toString();
		}
		return key + ": " + str;
	}

	public static String getNodeName(String key, Object value) {
		String str = "<empty>";
		if ((value != null) && (value.toString().trim().length() > 0)) {
			str = value.toString();
		} else {
			return "null";
		}
		return key + ": " + str;
	}

	public static DefaultMutableTreeNode getStringNode(String key, String value) {
		String str = "<empty>";
		if ((value != null) && (value.trim().length() > 0)) {
			str = value;
		}
		return new DefaultMutableTreeNode(key + ": " + str);
	}

	public static String createListString(String key, List<?> elements, boolean safe) {
		if ((elements == null) || (elements.size() == 0)) {
			return "";
		}
		String json = "[";
		for (Object e : elements) {
			if (safe) {
				json = json + createSafeString(null, e, false);
			} else {
				json = json + createPlainString(null, e, false);
			}
		}
		json = removeLastComma(json);
		json = json + "]";
		if (safe) {
			return createSafeString(key, json, false);
		}
		return createPlainString(key, json, false);
	}

	public static String hardcoreClean(String str) {
		if (str == null) {
			return null;
		}
		return str.replace("\"", "").replace("\\", "");
	}

	public static String removeQuotations(String str) {
		if (str == null) {
			return null;
		}
		return str.replace("\"", "");
	}

	public static String makeSafe(String string, boolean quotation) {
		if (string == null) {
			return null;
		}
		String str = "";
		if (quotation) {
			str = str + "\"";
		}
		str = str + string.replace("\\", "\\\\").replace("\"", "\\\"");
		if (quotation) {
			str = str + "\"";
		}
		return str;
	}

	public static String createPlainString(String key, Object value, boolean quotation) {
		if (value == null) {
			return "";
		}
		String str = "";
		if (key != null) {
			str = key + ":";
		}
		if (quotation) {
			str = str + "\"";
		}
		str = str + value.toString();
		if (quotation) {
			str = str + "\"";
		}
		str = str + ",";
		return str;
	}

	public static String quotate(String value) {
		return "\"" + value + "\"";
	}

	public static String removeLastComma(String value) {
		String inverted = new StringBuilder(value).reverse().toString();
		value = inverted.replaceFirst(",", "");
		return new StringBuilder(value).reverse().toString();
	}

	public static String createSafeString(String key, Object value, boolean quotation) {
		if (value == null) {
			return "";
		}
		String str = "";
		if (key != null) {
			str = key + ":";
		}
		if (quotation) {
			str = str + "\"";
		}
		str = str + value.toString().replace("\\n", "N_E_W_LINE_CODE").replace("\\", "\\\\").replace("\"", "\\\"")
				.replace("N_E_W_LINE_CODE", "\\n");
		if (quotation) {
			str = str + "\"";
		}
		str = str + ",";
		return str;
	}

	public static String booleanJson(String key, boolean value, boolean force) {
		if (force) {
			return createSafeString(key, Boolean.valueOf(value), true);
		}
		if (value) {
			return createSafeString(key, Boolean.valueOf(value), true);
		}
		return "";
	}

	public static String booleanJsonNull(String key, boolean value, boolean force) {
		if (force) {
			return key + ": " + value;
		}
		if (value) {
			return key + ": " + value;
		}
		return "null";
	}

	public static MCSignLine getCutLine(MCSignLine line) {
		MCSignLine trim = new MCSignLine();

		int w = 0;
		String text;
		for (SimpleJSONElement e : line) {
			if (w >= 230) {
				break;
			}
			if (e.getText() != null) {
				text = e.getText();
				String inText = text;
				int l = 0;
				while (getSize(text) > 230 - w) {
					text = text.substring(0, text.length() - 1);
					l++;
					if (l > inText.length()) {
						break;
					}
				}
				w += getSize(text);
				e.setText(text);
				trim.add(e);
			} else {
				trim.add(e);
			}
		}
		MCSignLine trim1 = new MCSignLine();
		for (SimpleJSONElement j : trim) {
			if (j.getText() != null) {
				if (j.getText().length() > 0) {
					trim1.add(j);
				}
			} else {
				trim1.add(j);
			}
		}
		return trim1;
	}

	public static int getSize(String text) {
		FontMetrics metrics = MainFrame.preview.getFontMetrics(MainFrame.minecrafterFont);
		return metrics.stringWidth(text);
	}

	public static String createSafeColor(String key, Object value) {
		if (value == null) return "";
		return key + ":\"" + value.toString() + "\",";

	}
}