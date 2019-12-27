package TetrisLike3DSolver;

import java.awt.Color;

/**
 * An LPentomino extending the abstract Pentomino.
 */
public class LPentomino extends Pentomino {
	
	private int[][] representation = 
		{
			{1,0},
			{1,0},
			{1,0},
			{1,1}
		};
	public static final char typeChar = 'L';
	public static final int typeID = 5;
	public static final PentoColor color = new PentoColor(Color.PINK, javafx.scene.paint.Color.PINK);
	
	public LPentomino(int id, double height, double value) {
		super(id, height, value);
	}
	
	@Override
	public int[][] getRepresentation() {
		return representation;
	}
	@Override
	public PentoColor getColor() {
		return color;
	}

	@Override
	public Character getTypeChar() {
		return typeChar;
	}

	@Override
	public int getTypeID() {
		return typeID;
	}


}
