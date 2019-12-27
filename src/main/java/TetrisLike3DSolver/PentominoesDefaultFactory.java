package TetrisLike3DSolver;

import java.util.ArrayList;

/**
 * A relaxed implementation of a factory for pentominoes. See: https://en.wikipedia.org/wiki/Factory_method_pattern 
 *
 */
public class PentominoesDefaultFactory {
	
	public static ArrayList<Pentomino> CreateMany(int howMany, char typeChar){
		ArrayList<Pentomino> pentos = new ArrayList<Pentomino>();
		for(int i = 0; i < howMany; i++) {
			pentos.add(PentominoesDefaultFactory.Create(typeChar));
		}
		return pentos;
	}
	public static ArrayList<Pentomino> CreateMany(int howMany, Character typeChar, double value){
		ArrayList<Pentomino> pentos = new ArrayList<Pentomino>();
		for(int i = 0; i < howMany; i++) {
			pentos.add(PentominoesDefaultFactory.Create(typeChar, value));
		}
		return pentos;
	}
	public static Pentomino Create(char typeCharCode) {
		switch(Character.toUpperCase(typeCharCode)) {
		    case '0': return new NullPentomino(0,0,0);
		    case ' ': return new NullPentomino(0,0,0);
			case 'L': return new LPentomino(0, 0.5, 1.0);
			case 'P': return new PPentomino(0, 0.5, 2.0);
			case 'T': return new TPentomino(0, 0.5, 3.0);
			default: throw new IllegalArgumentException("No known pentomino.");
		}
	}
	public static Pentomino Create(char typeCharCode, double value) {
		switch(Character.toUpperCase(typeCharCode)) {
		    case '0': return new NullPentomino(0,0,0);
		    case ' ': return new NullPentomino(0,0,0);
			case 'L': return new LPentomino(0, 0.5, value);
			case 'P': return new PPentomino(0, 0.5, value);
			case 'T': return new TPentomino(0, 0.5, value);
			default: throw new IllegalArgumentException("No known pentomino.");
		}
	}
	public static Pentomino Create(int typeID) {
		switch(typeID) {
			case 0: return new NullPentomino(0,0,0); 
			case 1: return new LPentomino(0, 0.5, 1.0);
			case 2: return new PPentomino(0, 0.5, 2.0);
			case 3: return new TPentomino(0, 0.5, 3.0);
			default: throw new IllegalArgumentException("No known pentomino."); 
		}
	}
	public static Pentomino Create(int typeID, double value) {
		switch(typeID) {
			case 0: return new NullPentomino(0,0,0); 
			case 1: return new LPentomino(0, 0.5, value);
			case 2: return new PPentomino(0, 0.5, value);
			case 3: return new TPentomino(0, 0.5, value);
			default: throw new IllegalArgumentException("No known pentomino."); 
		}
	}
}
