package TetrisLike3DSolver;

import java.awt.Color;
/**
 * An PPentomino extending the abstract Pentomino.
 */
public class PPentomino extends Pentomino {
	
	private int[][] representation = 
		{
			{1,1},
			{1,1},
			{1,0}
		};
	public static final char typeChar = 'P';
	public static final int typeID = 2;
	public static final PentoColor color = new PentoColor(Color.BLUE, javafx.scene.paint.Color.BLUE);
	
	public PPentomino(int id, double height, double value) {
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
