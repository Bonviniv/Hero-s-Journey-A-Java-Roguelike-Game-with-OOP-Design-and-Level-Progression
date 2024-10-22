package pt.iscte.poo.projeto;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Armor extends Item {

    public Armor(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Armor";
    }
    
    @Override
    public int getLayer() {
        return 1;
    }
}