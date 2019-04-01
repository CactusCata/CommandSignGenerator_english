package fr.cactuscata.mcjson;

import java.util.ArrayList;
import java.util.List;

import fr.cactuscata.signgenerator.MainFrame;
import fr.cactuscata.signgenerator.Utils;

public class MCSign {
	private List<MCSignLine> lines;
	private List<MCClickEvent> clickEvents;

	public MCSign() {
		this.lines = new ArrayList<>();
		this.lines.add(new MCSignLine());
		this.lines.add(new MCSignLine());
		this.lines.add(new MCSignLine());
		this.lines.add(new MCSignLine());

		this.clickEvents = new ArrayList<>();
		this.clickEvents.add(null);
		this.clickEvents.add(null);
		this.clickEvents.add(null);
		this.clickEvents.add(null);
	}

	public void setClickEvent(int id, MCClickEvent event) {
		this.clickEvents.set(id, event);
	}

	public MCClickEvent getClickEvent(int id) {
		if (this.clickEvents.get(id) == null) {
			return null;
		}
		return ((MCClickEvent) this.clickEvents.get(id)).copy();
	}

	public void setLine(int id, MCSignLine line) {
		this.lines.set(id, line);
	}

	public MCSignLine getLine(int id) {
		return ((MCSignLine) this.lines.get(id)).copy();
	}

	public String toString() {
		String str = "/blockdata " + MainFrame.getCoordinateString() + " {";
		for (int i = 1; i < 5; i++) {
			str = str + Utils.createSafeString(new StringBuilder("Text").append(i).toString(),
					((MCSignLine) this.lines.get(i - 1)).toString((MCClickEvent) this.clickEvents.get(i - 1)), true);
		}
		str = str + "}";
		str = Utils.removeLastComma(str);

		return str;
	}
}
