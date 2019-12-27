package TetrisLike3DSolver;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
/**
 * Abstraction for the pentomino entity. 
 */
@SuppressWarnings("serial")
public abstract class Pentomino {
	int id;
	double height;
	public double value;
	public abstract int[][] getRepresentation();
	
	public Pentomino(int id, double height, double value) {
		this.id = id;
		this.height = height;
		this.value = value;
	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getHeight() 
	{
		return this.height;
	}
	public double getValue() {
		return this.value;
	}
	//This assumes that every Pentomino is made out of 0.5 x 0.5 x 0.5 boxes.
	public double getWidth() {
		return this.getRepresentation().length * 0.5;
	}
	@Override
	public String toString() {
		return String.valueOf(getTypeChar());
	}
	//This assumes that every Pentomino is made out of 0.5 x 0.5 x 0.5 boxes.
	public double getLength() {
		return this.getRepresentation()[0].length * 0.5;
	}
	public abstract PentoColor getColor();
	public abstract Character getTypeChar();
	public abstract int getTypeID();
	
	//A quick solution to store this mapping.
	public static Map<Character, Integer> typeIDMap = new HashMap<Character, Integer>()
	{{
		put(LPentomino.typeChar, LPentomino.typeID);
		put(PPentomino.typeChar, PPentomino.typeID);
		put(TPentomino.typeChar, TPentomino.typeID );
	}};
	//A quick solution to store this mapping.
	public static Map<Character, PentoColor> typeColorMap = new HashMap<Character, PentoColor>()
	{{
		put(LPentomino.typeChar, new PentoColor(Color.PINK, javafx.scene.paint.Color.PINK));
		put(PPentomino.typeChar, PPentomino.color);
		put(TPentomino.typeChar, TPentomino.color);
	}};
	//A quick solution to store this mapping.
	public static Map<Integer, PentoColor> typeIDColorMap = new HashMap<Integer, PentoColor>()
	{{
		put(LPentomino.typeID, new PentoColor(Color.PINK, javafx.scene.paint.Color.PINK));
		put(PPentomino.typeID, PPentomino.color);
		put(TPentomino.typeID, TPentomino.color);
	}};
 
}
