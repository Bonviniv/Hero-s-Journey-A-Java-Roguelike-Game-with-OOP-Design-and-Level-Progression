package pt.iscte.poo.projeto;

import java.util.ArrayList;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Skeleton extends Combatant implements Movable,Interactable  {

	public Skeleton(Point2D position) {
		super(position);
		setHp(5);
	}


	@Override
	public void move() {
		Vector2D movement = Vector2D.movementVector(this.getPosition(),
				GameEngine.getInstance().getHero().getPosition());
		Point2D nextPos = this.getPosition().plus(movement);
		if (GameEngine.getInstance().getObjectUpper(nextPos) != null) {
			interactWith(GameEngine.getInstance().getObjectUpper(nextPos));
		} else if (GameEngine.getInstance().getTurn() % 2 == 0) {
			this.setPosition(nextPos);
		}

	}

	@Override
	public String getName() {
		return "Skeleton";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public void interactWith(GameElement e) {
		if (e instanceof Hero) {
			Hero c = (Hero) e;
			this.attacks(c);
			System.out.println(this.getName() + " attacked you");
		}
	}
}
