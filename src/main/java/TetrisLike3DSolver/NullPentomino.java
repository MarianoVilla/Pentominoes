package TetrisLike3DSolver;

import java.awt.Color;

/**
 * NullPentomino extending the abstract Pentomino. Used for special case handling, see: https://www.martinfowler.com/eaaCatalog/specialCase.html 
 */
public class NullPentomino extends Pentomino {

	public NullPentomino(int id, double height, double value) {
		super(0, 0, 0);
	}
	public static final PentoColor color = new PentoColor(Color.BLACK, javafx.scene.paint.Color.BLACK);

	@Override
	public int[][] getRepresentation() {
		return null;
	}

	@Override
	public PentoColor getColor() {
		return color;
	}

	@Override
	public Character getTypeChar() {
		return null;
	}

	@Override
	public int getTypeID() {
		return 0;
	}

}
