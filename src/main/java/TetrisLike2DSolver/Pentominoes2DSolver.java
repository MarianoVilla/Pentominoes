package TetrisLike2DSolver;

import java.util.ArrayList;
import java.util.List;

import TetrisLike3DSolver.Pentomino;

public class Pentominoes2DSolver {
	private int width;
	private int height;
	private Solution solution = null;
	private boolean solved = false;
	private int[][] field;
	private int[] controlInput;
	private boolean[] used;
	private ArrayList<Pentomino> Pentominoes;
	
	public Pentominoes2DSolver(int width, int height, ArrayList<Pentomino> pentos) {
		if(!possibleBoard(width, height))
			solved = true;
		else {
			field = new int[width][height];
			initializeField();
			this.Pentominoes = pentos;
			this.controlInput = inputToInt(pentos);
			this.used = new boolean[controlInput.length];
		}
	}
	private int[] inputToInt(List<Pentomino> pentos) {
		int[] pentID = new int[pentos.size()];
		for(int i = 0; i < pentos.size(); i++) {
			pentID[i] = pentos.get(i).getTypeID();
		}
		return pentID;
	}
	private boolean possibleBoard(int width, int height) {
		return (width * height) % 5 == 0;
	}
	private void initializeField() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				// -1 in the state matrix corresponds to empty square
				// Any positive number identifies the ID of the pentomino
				field[i][j] = -1;
			}
		}
	}
	public Solution solve() {
		if(solved)
			return this.solution;
		searchFunction(0, used, field, controlInput);
		return this.solution;
		
	}
	private void searchFunction(int piecesUsed, boolean used[], int field[][], int[] controlInput) {
		// used a boolean of (used) to mark the used of peaces
		int i;
		int j;
		int positionEmpty[] = nextEmpty(field); // using the function nextEmpty
		if ((nextEmpty(field))[0] == -1 || piecesUsed == this.Pentominoes.size()) {
			// Checks for the first -1 in the table. If there are none, it means it is
			// solved
			solved = true;
			solution = new Solution(field, this.Pentominoes);
			return;
		}
		// changed array used from int to boolean as to check if a pieces was used or
		// not
		else {
			for (i = 0; i < controlInput.length; i++) {
				int IDfigure = controlInput[i];// The inputs in the array correspond to the ID of the figures
				if (!used[i]) {

					for (j = 0; j < PentominoDatabase.data[IDfigure].length; j++) {
						int[][] copy = new int[field.length][field[0].length];
						for (int k = 0; k < field.length; k++) {
							copy[k] = field[k].clone(); // changed the clone with 1d array because we cannot use with 2d
														// arrays,
						}
						// When we call method addPiece, field is internally changed, so we make sure we
						// store the previous situation
						// addpiece changed from void to boolean instead adding the piece and then
						// checking if it fits, this function does both
						boolean itFits = addPiece(copy, PentominoDatabase.data[IDfigure][j], IDfigure, positionEmpty[0],
								positionEmpty[1]); // Be careful with 0 and 1 in PositionEmpty, it really depends on how
													// it is defined
						if (itFits) {
							boolean[] newused = used.clone();
							newused[i] = true;
							
							// Figures placed contains the order in which each of the elements was placed.
							searchFunction(piecesUsed + 1, newused, copy, controlInput);
						}
					}
				}
			}
		}
	}

	/**
	 * Takes as parameter a 2d in array and it returns the coordinates of the first
	 * -1 it finds in it. Those coordinates are the first and the second element of
	 * the two dimensional array given
	 * 
	 * @return nextEmpty
	 * 
	 */

	public int[] nextEmpty(int[][] field) {
		int i;
		int j;
		int[] coordinates = new int[2];
		for (i = 0; i < field.length; i++) {
			for (j = 0; j < field[i].length; j++) {
				if (field[i][j] == -1) {
					coordinates[0] = i;
					coordinates[1] = j;
					return coordinates;
				}
			}
		}
		coordinates[0] = -1;
		coordinates[1] = -1;
		return coordinates;
	}
	// Adds a pentomino to the position on the field (overriding current board at
	// that position)
	public boolean addPiece(int[][] field, int[][] piece, int pieceID, int x, int y) {
		for (int k = 0; k < piece.length; k++) { // 2 loops moves one figure as find x y (empty spaces)
			for (int l = 0; l < piece[k].length; l++) {
				int i = 0;
				int j = 0;
				// L is a label for the loop to break it
				L: for (i = 0; i < piece.length; i++) { // loop over x position of pentomino
					for (j = 0; j < piece[i].length; j++) { // loop over y position of pentomino
						if (piece[i][j] == 1) {
							// Add the ID of the pentomino to the board if the pentomino occupies this
							// square
							if (x + i - k < field.length && y + j - l < field[i].length && x + i - k >= 0
									&& y + j - l >= 0) { // && making sure we are inside the field
								if (field[x + i - k][y + j - l] != -1)
									break L;
								field[x + i - k][y + j - l] = pieceID;
							} else
								break L;
						}
					}
				}
				if (i == piece.length && j == piece[0].length && itFits(field))
					return true;
			}
		}
		return false;
	}
	/**
	 * This method checks whether for every empty block in our rectangle, all the
	 * squares in it sum up to a number divisible by 5, if so returns 5
	 * 
	 * @param field
	 * @return control
	 */
	private boolean itFits(int field[][]) {

		// We will use this table in order to prune the algorithm. All the values are
		// set to false
		boolean[][] beenThere = new boolean[width][height];
		int aux;
		boolean control = true;
		int i;
		int j;

		for (i = 0; i < width; i++) {
			for (j = 0; j < height; j++) {
				// We check whether for every empty block in our rectangle, all the empty squares
				// sum up to 5*k
				aux = divisibleBy5(field, beenThere, i, j);
				if (aux % 5 != 0)
					control = false;
			}
		}
		return control;

	}
	
	/**
	 * @param field
	 * @param beenThere
	 * @param x
	 * @param y
	 * @return
	 */
	private int divisibleBy5(int field[][], boolean beenThere[][], int y, int x) {

		// We make sure the position is inside the rectangle
		if ((y < 0 || y >= width) || (x < 0 || x >= height))
			return 0;

		// If that position has already been checked or is not "empty", then it returns
		// -1
		else if (beenThere[y][x] || (field[y][x]) != -1)
			return 0;

		else {
			beenThere[y][x] = true; // We store the current position as visited
			int morex = x + 1;
			int lessx = x - 1;
			int morey = y + 1;
			int lessy = y - 1;
			return (1 + divisibleBy5(field, beenThere, y, morex) // wrong method
					+ divisibleBy5(field, beenThere, y, lessx) + divisibleBy5(field, beenThere, morey, x)
					+ divisibleBy5(field, beenThere, lessy, x));
		}

	}
}
