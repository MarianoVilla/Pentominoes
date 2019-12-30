package Solver3DTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import TestHelpers.TestRepo;
import TetrisLike2DSolver.Pentominoes2DSolver;
import TetrisLike3DSolver.Pentomino;

public class Pentominoes2DSolverTests {
	@Test
	public void testSolve_ShouldSolve() {
		ArrayList<Pentomino> Pentominoes = TestRepo.getRandomPentos(1);
		Pentominoes2DSolver Solver = new Pentominoes2DSolver(10,10,Pentominoes);
		
		assertNotNull(Solver.solve());
	}
	@Test
	public void testSove_StressTest() {
		ArrayList<Pentomino> Pentominoes = TestRepo.getRandomPentos(10);
		Pentominoes2DSolver Solver = new Pentominoes2DSolver(16,5,Pentominoes);
		
		assertNotNull(Solver.solve());
	}
}
