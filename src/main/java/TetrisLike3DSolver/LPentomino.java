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
	public static final int typeID = 8;
	public static final PentoColor color = new PentoColor(Color.PINK, javafx.scene.paint.Color.PINK);
	
	public LPentomino(int id, double height, double value) {
		super(id, height, value);
	}
	public LPentomino(int id, double height, double value, int Qty) {
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
		return this.getQty() > 0 ?  "tile flip " + this.getQty() + " ((0,0)(0,1)(0,2)(0,3)(1,0))" + " __polyID: 8"
				: "tile flip((0,0)(0,1)(0,2)(0,3)(1,0))" + " __polyID: 8";
	}
	@Override
	public String getPolyRepresentation(int Qty) {
		return "tile flip " + Qty + " ((0,0)(0,1)(0,2)(0,3)(1,0))" + " __polyID: 8";
	}
	public static String getStaticPolyRepresentation(int Qty) {
		return "tile flip "+Qty+" ((0,0)(0,1)(0,2)(0,3)(1,0)) __polyID: 8\r\n";
	}


}
