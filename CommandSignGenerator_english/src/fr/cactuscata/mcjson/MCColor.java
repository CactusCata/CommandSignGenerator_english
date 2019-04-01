package fr.cactuscata.mcjson;

import java.awt.Color;

public enum MCColor {
	black,
	dark_blue,
	dark_green,
	dark_aqua,
	dark_red,
	dark_purple,
	gold,
	gray,
	dark_gray,
	blue,
	green,
	aqua,
	red,
	light_purple,
	yellow,
	white;

	public Color toColor() {
		switch (this) {
		case green:
			return Color.decode("#55FF55");
		case aqua:
			return Color.decode("#55FFFF");
		case gold:
			return Color.decode("#FFAA00");
		case dark_aqua:
			return Color.decode("#00AAAA");
		case black:
			return Color.decode("#000000");
		case dark_red:
			return Color.decode("#AA0000");
		case blue:
			return Color.decode("#5555FF");
		case dark_gray:
			return Color.decode("#555555");
		case dark_blue:
			return Color.decode("#0000AA");
		case dark_green:
			return Color.decode("#00AA00");
		case dark_purple:
			return Color.decode("#AA00AA");
		case gray:
			return Color.decode("#AAAAAA");
		case red:
			return Color.decode("#FF5555");
		case light_purple:
			return Color.decode("#FF55FF");
		case yellow:
			return Color.decode("#FFFF55");
		case white:
			return Color.decode("#FFFFFF");
		}
		return Color.decode("#000000");
	}
}