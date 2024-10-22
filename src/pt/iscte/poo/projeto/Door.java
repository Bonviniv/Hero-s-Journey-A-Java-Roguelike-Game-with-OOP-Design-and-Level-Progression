package pt.iscte.poo.projeto;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Door extends GameElement {

	private Point2D nextPos;

	private String nextRoom;

	private String reqKey;

	private boolean doorOpen = false;

	public Door(Point2D position, String[] info) {
		super(position);
		Point2D pos = new Point2D(Integer.parseInt(info[4]), Integer.parseInt(info[5]));
		this.nextPos = pos;
		this.nextRoom = info[3];
		if (info.length == 7) {
			this.reqKey = info[6];
		} else {
			this.doorOpen = true;
		}
	}

	public void tryDoor(String keyID) {
		doorOpen = reqKey.equals(keyID);
	}
	
	public boolean isOpen() {
		return this.doorOpen;
	}
	
	public int getNextRoom() {
		return Integer.parseInt(nextRoom.substring(4));
	}
	
	public Point2D getNextPos() {
		return this.nextPos;
	}

	@Override
	public String getName() {
		if (doorOpen) {
			return "DoorOpen";
		}
		return "DoorClosed";
	}

	@Override
	public int getLayer() {
		if (doorOpen) {
			return 1;
		}
		return 2;
	}
}