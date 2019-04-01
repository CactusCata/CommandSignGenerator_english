package fr.cactuscata.mcjson;

import fr.cactuscata.signgenerator.Utils;

public class MCClickEvent {
	private MCClickAction action;
	private String value;

	public boolean equals(Object obj) {
		if (obj == null) return false;

		if (!(obj instanceof MCClickEvent)) return false;

		MCClickEvent event = (MCClickEvent) obj;
		return (event.action == this.action) && (event.value.equals(this.value));
	}

	public String toString() {
		String json = "{";

		json = json + Utils.createSafeString("\"action\"", this.action, true);
		json = json + Utils.createSafeString("\"value\"", this.value, true);

		json = Utils.removeLastComma(json);

		json = json + "}";
		return json;
	}

	public MCClickAction getAction() {
		return this.action;
	}

	public void setAction(MCClickAction action) {
		this.action = action;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MCClickEvent(MCClickAction action, String value) {
		this.action = action;
		this.value = value;
	}

	public MCClickEvent copy() {
		return new MCClickEvent(this.action, this.value);
	}
}