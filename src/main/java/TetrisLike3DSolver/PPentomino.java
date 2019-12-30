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
	public static final int typeID = 9;
	public static final PentoColor color = new PentoColor(Color.BLUE, javafx.scene.paint.Color.BLUE);
	
	public PPentomino(int id, double height, double value) {
		super(id, height, value);
	}
	public PPentomino(int id, double height, double value, int Qty) {
		super(id, height, value, Qty);
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

	@Override
	public String getPolyRepresentation() {
		return this.getQty() > 0 ?  "tile flip " + this.getQty() + " ((0,0)(0,1)(0,2)(1,0)(1,1))" + " __polyID: 9"
				: "tile flip((0,0)(0,1)(0,2)(1,0)(1,1))" + " __polyID: 9";
	}
	@Override
	public String getPolyRepresentation(int Qty) {
		return "tile flip " + Qty + " ((0,0)(0,1)(0,2)(1,0)(1,1))" + " __polyID: 9";
	}

}
