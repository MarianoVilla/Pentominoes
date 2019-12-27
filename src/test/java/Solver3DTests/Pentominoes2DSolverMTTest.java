package Solver3DTests;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

import TestHelpers.TestRepo;
import TetrisLike2DSolver.Pentominoes2DSolverMT;
import TetrisLike2DSolver.Pentominoes2DSolverMT.Solution;
import TetrisLike3DSolver.Pentomino;

/**
 * Unit tests for the Pentominoes2DSolverMT.
 *
 */
public class Pentominoes2DSolverMTTest {
	
	@Test
	public void testEarlyReturn() {
		ArrayList<Pentomino> pentos = TestRepo.getRandomPentos(1);
		
		Pentominoes2DSolverMT Solver = new Pentominoes2DSolverMT(1, 1, pentos);
		Solution Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertNull(Sol);
	}

	@Test
	public void testSolve_RandomPentoInput_ShouldFitAll() {
		int amount = 12;
		ArrayList<Pentomino> pentos = TestRepo.getRandomPentos(amount);
		
		Pentominoes2DSolverMT Solver = new Pentominoes2DSolverMT(8, 8, pentos);
		Solution Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertEquals(amount, Sol.getPentominoes().size());
		
	}
	
	
	@Test
	public void testSolve_LPentoInput_ShouldFitAll() {
		ArrayList<Pentomino> pentos = TestRepo.getPentos(1,'L');
		
		Pentominoes2DSolverMT Solver = new Pentominoes2DSolverMT(2, 4, pentos);
		Solution Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertEquals(1, Sol.getPentominoes().size());
		
		pentos = TestRepo.getPentos(2, 'L');
		
		Solver = new Pentominoes2DSolverMT(2, 5, pentos);
		Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertEquals(2, Sol.getPentominoes().size());
		
		Solver = new Pentominoes2DSolverMT(5, 2, pentos);
		Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertEquals(2, Sol.getPentominoes().size());
		
	}
	@Test
	public void testSolve_TPentoInput_ShouldFitAll() {
		ArrayList<Pentomino> pentos = TestRepo.getPentos(1,'T');
		
		Pentominoes2DSolverMT Solver = new Pentominoes2DSolverMT(3, 3, pentos);
		Solution Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertEquals(1, Sol.getPentominoes().size());
		
		pentos = TestRepo.getPentos(2, 'T');

		Solver = new Pentominoes2DSolverMT(4, 4, pentos);
		Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertEquals(2, Sol.getPentominoes().size());
		
		pentos = TestRepo.getPentos(3, 'T');

		Solver = new Pentominoes2DSolverMT(5, 5, pentos);
		Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertEquals(3, Sol.getPentominoes().size());
		
	}
	@Test
	public void testSolve_PPento_ShouldFitAll() {
		ArrayList<Pentomino> pentos = TestRepo.getPentos(1,'P');
		
		Pentominoes2DSolverMT Solver = new Pentominoes2DSolverMT(2, 4, pentos);
		Solution Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertEquals(1, Sol.getPentominoes().size());
		
		pentos = TestRepo.getPentos(2, 'P');
		
		Solver = new Pentominoes2DSolverMT(2, 5, pentos);
		Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertEquals(2, Sol.getPentominoes().size());
		
		Solver = new Pentominoes2DSolverMT(5, 2, pentos);
		Sol = Solver.solve();
		
		assertTrue(Solver.isSolved());
		assertEquals(2, Sol.getPentominoes().size());
	}
}
