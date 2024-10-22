package pt.iscte.poo.projeto;

import pt.iscte.poo.utils.Point2D;

public class HealthBar extends GameElement {

	private String health = "green";
	private int segment;

	public HealthBar(Point2D position) {
		super( position);

	}
	public void setGreen() {
		this.health="green";
	}
	public void setRed() {
		this.health="red";
	}
	public void setMid() {
		this.health="redgreen";
	}

	public void setSegment(int n) {
		this.segment = n + 1;
	}

	public int getSegment() {
		return this.segment;
	}
	
	public String getColor() {
		return this.health;
	}

	@Override
	public String getName() {
		switch (health) {
		case "green":
			return "Green";
		case "red":
			return "Red";
		case "redgreen":
			return "RedGreen";
		default:
			return "Green";
		}
	}

	@Override
	public int getLayer() {
		return 2;
	}

}
