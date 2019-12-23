package TetrisLike3DSolver;

import TetrisLike2DSolver.Pentominos2DSolverMT.Solution;

public class SolutionLayer {
	
	private double height;
	private Solution solution;
	
	public SolutionLayer(double height, Solution solution) {
		this.height = height;
		this.solution = solution;
	}
	
	public double getHeight() {
		return this.height;
	}
	public Solution getSolution() {
		return this.solution;
	}
	public int[][] getBoard(){
		return this.solution.getBoard();
	}
	public double getValue() {
		return this.solution.getValue();
	}

}
