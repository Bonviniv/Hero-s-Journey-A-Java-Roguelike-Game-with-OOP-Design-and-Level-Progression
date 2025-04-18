package pt.iscte.poo.projeto;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class HealingPotion extends Item {

	public HealingPotion(Point2D position) {
		super(position);
	}

    @Override
    public String getName() {
        return "HealingPotion";
    }
    
    @Override
    public int getLayer() {
        return 1;
    }
}