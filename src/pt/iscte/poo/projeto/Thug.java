package pt.iscte.poo.projeto;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Thug extends Combatant implements Movable, Interactable {

    public Thug(Point2D position) {
        super(position);
        setHp(10);

    }

    @Override
    public int atk() {
        return 3;
    }

    @Override
    public void move() {
        Vector2D movement = Vector2D.movementVector(this.getPosition(),
                GameEngine.getInstance().getHero().getPosition());
        Point2D nextPos = this.getPosition().plus(movement);
        if (GameEngine.getInstance().getObjectUpper(nextPos) != null) {
            interactWith(GameEngine.getInstance().getObjectUpper(nextPos));
        } else {
            this.setPosition(nextPos);
        }

    }

    @Override
    public String getName() {
        return "Thug";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void interactWith(GameElement e) {
        if (e instanceof Hero) {
            Hero c = (Hero) e;
            int rand = (int) (Math.random() * 99);
            if (rand < 30) {
                this.attacks(c);
                System.out.println(this.getName() + " attacked you");
            }
        }
    }
}