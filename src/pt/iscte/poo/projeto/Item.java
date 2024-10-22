package pt.iscte.poo.projeto;

import pt.iscte.poo.utils.Point2D;

public abstract class Item extends GameElement {
	
	private boolean itemStatus = false;

	public Item(Point2D position) {
		super(position);
	}
	
	public void pickedUp() {
		this.itemStatus=true;
	}
	
	public void droped() {
		this.itemStatus=false;
	}
	
	public boolean inInventory() {
		return itemStatus;
	}
}
