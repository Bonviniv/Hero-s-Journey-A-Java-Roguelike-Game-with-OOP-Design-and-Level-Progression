package pt.iscte.poo.projeto;

import pt.iscte.poo.utils.Point2D;

public class Black extends GameElement {

	public Black(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Black";
	}

	@Override
	public int getLayer() {
		return 0;
	}

}
