package TetrisLike3DSolver;

import java.awt.Color;

public class TPentomino extends Pentomino {
	
	private int[][] representation = 
		{
			{1,1,1},
			{0,1,0},
			{0,1,0}
		};
	public static final char typeChar = 'T';
	public static final int typeID = 3;
	public static final PentoColor color = new PentoColor(Color.CYAN, javafx.scene.paint.Color.CYAN);
	
	public TPentomino(int id, double height, double value) {
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
