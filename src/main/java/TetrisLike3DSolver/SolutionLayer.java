package TetrisLike3DSolver;

import java.util.ArrayList;

import TetrisLike2DSolver.Pentominoes2DSolverMT.Solution;

/**
 * A solution layer stores a 2D solution for pentominoes stacking, plus a height for the addition dimension.
 *
 */
public class SolutionLayer {
	
	private double height;
	private int itemsCount;
	private int[][] board;
	//TODO: get this dependency out of 2DMT solver.
	private Solution solution;
	private ArrayList<Pentomino> layerPentominoes;
	
	
	public SolutionLayer(double height, Solution solution) {
		this.height = height;
		this.solution = solution;
		this.board = solution.getBoard();
		this.itemsCount = solution.getPentominoes().size();
	}
	public SolutionLayer(double height, int[][] board, ArrayList<Pentomino> Pentominoes) {
		this.height = height;
		this.board = board;
		this.layerPentominoes = Pentominoes;
	}
	public SolutionLayer(double height, int[][] board) {
		this.height = height;
		this.board = board;
	}
	public double getHeight() {
		return this.height;
	}
	public Solution getSolution() {
		return this.solution;
	}
	public int[][] getBoard(){
		return this.board == null ? this.solution.getBoard() : this.board;
	}
	public double getValue() {
		return this.solution.getValue();
	}
	public int getItemsCount() {
		return this.itemsCount;
	}
	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}
	public ArrayList<Pentomino> getLayerPentominoes() {
		return layerPentominoes;
	}
	public void setLayerPentominoes(ArrayList<Pentomino> layerPentominoes) {
		this.layerPentominoes = layerPentominoes;
	}

}
