package pt.iscte.poo.projeto;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class GameElement implements ImageTile {

	private Point2D position;

	public GameElement(Point2D position) {
		this.position = position;
	}

	public void setRoom(int N) {
	}

	@Override
	public abstract String getName();

	@Override
	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D pos) {
		this.position = pos;
	}

	public int getRoom() {
		return 0;
	}

	@Override
	public abstract int getLayer();

	public static GameElement createGameElement(String type, Point2D pos, String[] info) {
		switch (type) {
		case "Skeleton":
			return new Skeleton(pos);
		case "Bat":
			return new Bat(pos);
		case "Sword":
			return new Sword(pos);
		case "Door":
			return new Door(pos, info);
		case "Key":
			return new Key(pos, info);
		case "HealingPotion":
			return new HealingPotion(pos);
		case "Thug":
			return new Thug(pos);
		case "Treasure":
			return new Treasure(pos);
		case "Armor":
			return new Armor(pos);
		case "Scorpio":
			return new Scorpio(pos);
		case "Thief":
			return new Thief(pos);

		default:
			throw new IllegalArgumentException();
		}
	}
}
