package pt.iscte.poo.projeto;

import java.util.ArrayList;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat extends Combatant implements Movable, Interactable {

	public Bat(Point2D position) {
		super(position);
		setHp(3);
	}

	private int hpStolen;

	@Override
	public void move() {
		Vector2D movement = Vector2D.movementVector(this.getPosition(),
				GameEngine.getInstance().getHero().getPosition());
		Point2D nextPos = new Point2D(0, 0);
		int rand = (int) (Math.random() * 99);
		if (rand < 50) {
			nextPos = this.getPosition().plus(movement);
		} else {
			Direction randDirection = Direction.random();
			Vector2D randVector = randDirection.asVector();
			nextPos = this.getPosition().plus(randVector);
		}
		if (GameEngine.getInstance().getObjectUpper(nextPos) != null) {
			interactWith(GameEngine.getInstance().getObjectUpper(nextPos));
		} else {
			this.setPosition(nextPos);
		}
	}

	@Override
	public String getName() {
		return "Bat";
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
			int rand = (int) (Math.random() * 99);
			if (rand < 50 && hpStolen < 4) {
				setHp(getHp() + 1);
			}
			System.out.println(this.getName() + " attacked you");
		}
	}
}
