package fr.cactuscata.mcjson;

import fr.cactuscata.signgenerator.Utils;

public class SimpleJSONElement {
	private boolean bold;
	private boolean italic;
	private boolean strikethrough;
	private boolean underlined;
	private boolean obfuscated;

	public SimpleJSONElement() {
		this.text = "";
		this.color = MCColor.black;
	}

	private String text;
	private String selector;
	private String translate;
	private MCScore score;
	private MCColor color;

	public SimpleJSONElement(String text, boolean bold, boolean italic, boolean strikethrough, boolean underlined,
			boolean obfuscated, MCColor color) {
		this.text = text;
		this.bold = bold;
		this.italic = italic;
		this.strikethrough = strikethrough;
		this.underlined = underlined;
		this.obfuscated = obfuscated;
		this.color = color;
	}

	public SimpleJSONElement(boolean bold, boolean italic, boolean strikethrough, boolean underlined,
			boolean obfuscated, String selector, MCColor color) {
		this.selector = selector;
		this.bold = bold;
		this.italic = italic;
		this.strikethrough = strikethrough;
		this.underlined = underlined;
		this.obfuscated = obfuscated;
		this.color = color;
	}

	public SimpleJSONElement(boolean bold, boolean italic, boolean strikethrough, boolean underlined,
			boolean obfuscated, MCColor color, String translate) {
		this.translate = translate;
		this.bold = bold;
		this.italic = italic;
		this.strikethrough = strikethrough;
		this.underlined = underlined;
		this.obfuscated = obfuscated;
		this.color = color;
	}

	public SimpleJSONElement(MCScore score, boolean bold, boolean italic, boolean strikethrough, boolean underlined,
			boolean obfuscated, MCColor color) {
		this.score = score;
		this.bold = bold;
		this.italic = italic;
		this.strikethrough = strikethrough;
		this.underlined = underlined;
		this.obfuscated = obfuscated;
		this.color = color;
	}

	public boolean isBold() {
		return this.bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isItalic() {
		return this.italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public boolean isStrikethrough() {
		return this.strikethrough;
	}

	public void setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	public boolean isUnderlined() {
		return this.underlined;
	}

	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}

	public boolean isObfuscated() {
		return this.obfuscated;
	}

	public void setObfuscated(boolean obfuscated) {
		this.obfuscated = obfuscated;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
		this.selector = null;
		this.score = null;
		this.translate = null;
	}

	public String getSelector() {
		return this.selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
		this.text = null;
		this.score = null;
		this.translate = null;
	}

	public String getTranslate() {
		return this.translate;
	}

	public void setTranslate(String translate) {
		this.translate = translate;
		this.text = null;
		this.score = null;
		this.selector = null;
	}

	public MCScore getScore() {
		return this.score;
	}

	public void setScore(MCScore score) {
		this.score = score;
		this.selector = null;
		this.text = null;
		this.translate = null;
	}

	public MCColor getColor() {
		return this.color;
	}

	public void setColor(MCColor color) {
		this.color = color;
	}

	public String toString() {
		return toString(false, false, false, false, false);
	}

	public String toString(boolean boldForce, boolean italicForce, boolean ulForce, boolean strForce,
			boolean obfusForce) {
		String json = "{";

		if (this.text != null) json += Utils.createSafeString("\"text\"", this.text, true);
		else if (this.selector != null) json += Utils.createSafeString("\"selector\"", this.selector, true);
		else if (this.score != null) json += Utils.createPlainString("\"score\"", this.score.toString(), false);
		else if (this.translate != null) json += Utils.createSafeString("\"translate\"", this.translate, true);

		json += Utils.booleanJson("\"bold\"", this.bold, boldForce);
		json += Utils.booleanJson("\"italic\"", this.italic, italicForce);
		json += Utils.booleanJson("\"underlined\"", this.underlined, ulForce);
		json += Utils.booleanJson("\"strikethrough\"", this.strikethrough, strForce);
		json += Utils.booleanJson("\"obfuscated\"", this.obfuscated, obfusForce);

		json += Utils.createSafeColor("\"color\"", this.color);

		json += "}";
		return json;
	}
}
