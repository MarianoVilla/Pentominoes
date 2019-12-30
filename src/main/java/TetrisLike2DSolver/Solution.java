package TetrisLike2DSolver;

import java.util.ArrayList;
import java.util.List;

import TetrisLike3DSolver.Pentomino;
import TetrisLike3DSolver.PentominoesDefaultFactory;

public class Solution {
		private final int[][] board;
		private ArrayList<Pentomino> Pentominoes = new ArrayList<Pentomino>();

		Solution(int[][] board) {
			this.board = board;
		}

		public Solution(double value) {
			this.board = new int[0][0];
			this.Pentominoes.add(PentominoesDefaultFactory.Create('L', value));
		}
		public Solution(int[][] board, ArrayList<Pentomino> Pentominoes) {
			this.board = board;
			this.Pentominoes = Pentominoes;
		}

		public Solution() {
			this.board = new int[0][0];
		}

		public double getValue() {
			return this.Pentominoes.stream().mapToDouble(p -> p.getValue()).sum();
		}

		public int[][] getBoard() {
			return this.board;
		}

		/**
		 *
		 * @return number of rows
		 */
		public int getRows() {
			return board.length;
		}

		/**
		 *
		 * @return number of columns
		 */
		public int getColumns() {
			return board[0].length;
		}

		/**
		 * 
		 * @return Pentominoes in the solution.
		 */
		public ArrayList<Pentomino> getPentominoes() {
			return this.Pentominoes;
		}

		/**
		 * get the content of a cell in the solution
		 *
		 * @param row row
		 * @param col column
		 * @return content
		 */
		public int get(int row, int col) {
			return board[row][col];
		}

		/**
		 * get a copy of the solution
		 *
		 * @return
		 */
		public int[][] get() {
			int[][] copy = new int[board.length][board[0].length];
			for (int y = 0; y < board.length; y++) {
				System.arraycopy(board[y], 0, copy[y], 0, board[0].length);
			}
			return copy;
		}

	}
