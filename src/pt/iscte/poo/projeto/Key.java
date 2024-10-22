package pt.iscte.poo.projeto;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Key extends Item  {

	private String ID;

	public Key(Point2D position, String [] info) {
		super(position);
		this.ID = info[3];
	}

	@Override
	public String getName() {
		return "Key";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	public String getKeyID() {
		return this.ID;
	}
}