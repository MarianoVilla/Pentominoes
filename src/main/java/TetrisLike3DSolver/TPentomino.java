package TetrisLike3DSolver;

import java.awt.Color;
/**
 * An PPentomino extending the abstract Pentomino.
 */
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
	public TPentomino(int id, double height, double value, int Qty) {
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
		return this.getQty() > 0 ?  "tile flip " + this.getQty() + " ((0,0)(1,0)(1,1)(1,2)(2,0))" + " __polyID: 3"
				: "tile flip((0,0)(1,0)(1,1)(1,2)(2,0))" + " __polyID: 3";
	}
	@Override
	public String getPolyRepresentation(int Qty) {
		return "tile flip " + Qty + " ((0,0)(1,0)(1,1)(1,2)(2,0))" + " __polyID: 3";
	}
	public static String getStaticPolyRepresentation(int Qty) {
		return "tile flip "+Qty+" ((0,0)(1,0)(1,1)(1,2)(2,0)) __polyID: 3\r\n";
		//return "tile flip " + Qty + " ((0,0)(1,0)(1,1)(1,2)(2,0))" + " __polyID: 3\\r\\n";
	}

}
