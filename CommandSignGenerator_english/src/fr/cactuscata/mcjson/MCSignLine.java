package fr.cactuscata.mcjson;

import java.util.ArrayList;

import fr.cactuscata.signgenerator.Utils;

public class MCSignLine extends ArrayList<SimpleJSONElement> {
	private static final long serialVersionUID = -7792604191054853170L;

	public MCSignLine() {
	}

	public MCSignLine(MCSignLine line) {
		for (SimpleJSONElement e : line) {
			add(e);
		}
	}

	public MCSignLine copy() {
		return new MCSignLine(this);
	}

	public String toString(MCClickEvent ce) {
		if (ce == null) {
			return toString();
		}
		String json = "{";
		MCSignLine trim = Utils.getCutLine(copy());
		if (trim.size() == 0) {
			json = json + Utils.createPlainString("\"clickEvent\"", ce.toString(), false);
			return Utils.removeLastComma(json + "}");
		}
		json = json + ((SimpleJSONElement) trim.get(0)).toString().substring(1,
				((SimpleJSONElement) trim.get(0)).toString().length() - 1);
		json = json + Utils.createPlainString("\"clickEvent\"", ce.toString(), false);
		if (trim.size() > 1) {
			SimpleJSONElement last = (SimpleJSONElement) get(0);

			json = json + "extra:[";

			int i = -1;
			for (SimpleJSONElement el : trim) {
				i++;
				if (i != 0) {
					boolean boldForce = last.isBold();
					boolean italicForce = last.isItalic();
					boolean ulForce = last.isUnderlined();
					boolean strForce = last.isStrikethrough();
					boolean obfusForce = last.isObfuscated();
					json = json
							+ Utils.removeLastComma(el.toString(boldForce, italicForce, ulForce, strForce, obfusForce))
							+ ",";
				}
			}
			json = json + "]";
		}
		json = Utils.removeLastComma(json + "}");

		return json;
	}

	public String toString() {
		String json = "{";
		MCSignLine trim = Utils.getCutLine(copy());
		if (trim.size() == 0) {
			return " ";
		}
		json = json + ((SimpleJSONElement) trim.get(0)).toString().substring(1,
				((SimpleJSONElement) trim.get(0)).toString().length() - 1);
		if (trim.size() > 1) {
			json = json + "extra:[";

			int i = -1;
			for (SimpleJSONElement el : trim) {
				i++;
				if (i != 0) {
					SimpleJSONElement last = (SimpleJSONElement) get(i - 1);
					boolean boldForce = last.isBold();
					boolean italicForce = last.isItalic();
					boolean ulForce = last.isUnderlined();
					boolean strForce = last.isStrikethrough();
					boolean obfusForce = last.isObfuscated();
					json = json
							+ Utils.removeLastComma(el.toString(boldForce, italicForce, ulForce, strForce, obfusForce))
							+ ",";
				}
			}
			json = json + "]";
		}
		json = Utils.removeLastComma(json + "}");

		return json;
	}
}
