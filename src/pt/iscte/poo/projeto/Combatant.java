package pt.iscte.poo.projeto;

import pt.iscte.poo.utils.Point2D;

public abstract class Combatant extends GameElement {

	private int hitPoints = 10;

	public Combatant( Point2D position) {
		super(position);
	}

	public void setHp(int hp) {
		this.hitPoints = hp;
	}

	public int getHp() {
		return hitPoints;
	}

	public int atk() {
		return 1;
	}

	public boolean hasArmor() {
		return false;
	}

	public void attacks(Combatant e) {
		if (e.hasArmor()) {
			int rand = (int) (Math.random() * 99);
			if (rand < 50) {
				return;
			}
		}
		e.setHp(e.getHp() - atk());
	}
}
