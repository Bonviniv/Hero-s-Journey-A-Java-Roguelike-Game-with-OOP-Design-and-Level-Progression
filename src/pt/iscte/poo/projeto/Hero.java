package pt.iscte.poo.projeto;

import java.util.ArrayList;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Hero extends Combatant implements Movable, Interactable {

	private Direction d;
	private int room = 0;
	private ArrayList<GameElement> inventory = new ArrayList<>();
	private ArrayList<GameElement> keyChain = new ArrayList<>();
	private boolean poison = false;

	public Hero(Point2D position) {
		super(position);
		
	}

	public void setDirection(Direction d) {
		this.d = d;
	}
	
	@Override
	public int getRoom() {
		return room;
	}
	@Override
	public void setRoom(int N) {
		this.room = N;
	}

	@Override
	public String getName() {
		return "Hero";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	public ArrayList<GameElement> getInventory() {
		return inventory;
	}

	public ArrayList<GameElement> getKeyChain() {
		return keyChain;
	}

	@Override
	public boolean hasArmor() {
		for (GameElement e : inventory) {
			if (e instanceof Armor) {
				return true;
			}
		}
		return false;
	}

	public void dropItem(int key) {
		if (key > inventory.size()) {
			return;
		} else {
			inventory.remove(key);
			for (GameElement n : inventory) {
				int i = inventory.indexOf(n);
				inventory.get(i).setPosition(new Point2D((7 + i), 10));
			}
		}
	}

	private void addToInventory(GameElement e) {
		inventory.add(e);
		int n = inventory.indexOf(e);
		inventory.get(n).setPosition(new Point2D((7 + n), 10));
	}
	
	public boolean isPoisoned() {
		return this.poison;
	}
	
	public void poisoned() {
		this.poison = true;
	}
	
	public void drinkPotion() {
		
	}

	@Override
	public void move() {
		Vector2D movement = d.asVector();
		Point2D nextPos = this.getPosition().plus(movement);
		if (GameEngine.getInstance().getObjectUpper(nextPos) != null) {
			interactWith(GameEngine.getInstance().getObjectUpper(nextPos));
		} else {
			this.setPosition(nextPos);
			interactWith(GameEngine.getInstance().getObjectLower(nextPos));
		}
	}

	@Override
	public int atk() {
		for (GameElement e : inventory) {
			if (e instanceof Sword) {
				return 2;
			}
		}
		return 1;
	}

	@Override
	public void interactWith(GameElement e) {
		if (e instanceof Combatant) {
			Combatant c = (Combatant) e;
			this.attacks(c);
			System.out.println("You attacked a " + c.getName() + " - Current HP: " + c.getHp());
		}
		if (e instanceof Item) {
			if (!(e instanceof Key)) {
				if (inventory.size() < 3) {
					addToInventory(e);
					Item i = (Item) e;
					i.pickedUp();
				}
			}
		}
		if (e instanceof Key) {
			keyChain.add(e);
			Item i = (Item) e;
			i.pickedUp();
		}
		if (e instanceof Door) {
			Door d = (Door) e;
			if (d.isOpen()) {
				this.setPosition(d.getNextPos());
				this.setRoom(d.getNextRoom());
			} else {
				for (GameElement n : keyChain) {
					Key k = (Key) n;
					d.tryDoor(k.getKeyID());
					if (d.isOpen()) {
						return;
					}
				}
			}
		}
		if (e instanceof Treasure) {
			System.out.print("You won :)");
			System.exit(2);
		}
	}
}
