package fr.cactuscata.mcjson;

import fr.cactuscata.signgenerator.Utils;

public class MCScore {
	private String name;
	private String objective;

	public boolean equals(Object o2) {
		if (o2 == null) return false;

		if (!(o2 instanceof MCScore)) return false;

		MCScore o = (MCScore) o2;
		return (o.name.equals(this.name)) && (o.objective.equals(this.objective));
	}

	public String toString() {
		String json = "{";

		json = json + Utils.createSafeString("\"name\"", this.name, true);
		json = json + Utils.createSafeString("\"objective\"", this.objective, true);

		json = Utils.removeLastComma(json);

		json = json + "}";
		return json;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjective() {
		return this.objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public MCScore(String name, String objective) {
		this.name = name;
		this.objective = objective;
	}
}
