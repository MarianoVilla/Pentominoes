package Solver3DTests;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import DLXPentominoesSolverPack.DLXPentominoesSolver;
import TestHelpers.TestRepo;
import TetrisLike3DSolver.LayeredContainer;
import TetrisLike3DSolver.Pentomino;

public class DLXPentominoesSolverTests {
	
	private static DLXPentominoesSolver solver;
	
	@Before
	public void setUp() {
		solver = new DLXPentominoesSolver();
	}
	@Test
	public void testPack_ShouldFitAll() {
		ArrayList<Pentomino> Pentominoes = new ArrayList<Pentomino>();
		Pentominoes.add(TestRepo.getMultiPento(20, 'L'));
		Pentominoes.add(TestRepo.getMultiPento(20, 'P'));
		Pentominoes.add(TestRepo.getMultiPento(20, 'T'));
		LayeredContainer solution;
		solution = solver.Pack(Pentominoes);
		
		assertEquals(60, solution.getPackedItemsCount());
		assertTrue(true);
	}
	@Test
	public void testPack_StressTest() {
		ArrayList<Pentomino> Pentominoes = new ArrayList<Pentomino>();
		Pentominoes.add(TestRepo.getMultiPento(1000, 'L'));
		Pentominoes.add(TestRepo.getMultiPento(1000, 'P'));
		Pentominoes.add(TestRepo.getMultiPento(1000, 'T'));
		LayeredContainer solution;
		solution = solver.Pack(Pentominoes);
	}
	@Test
	public void testPack_HighestValueWithFullBin() {
		assertTrue(pickedTheHighestValue(1000, 1, 1, 1000));
		assertTrue(pickedTheHighestValue(1, 1000, 1, 1000));
		assertTrue(pickedTheHighestValue(1, 1, 1000, 1000));
		assertTrue(pickedTheHighestValue(1000, 1, 1000, 1000));
		assertTrue(pickedTheHighestValue(1000, 1000, 1000, 1000));
		assertTrue(pickedTheHighestValue(1, 1, 1, 1));
		assertTrue(pickedTheHighestValue(1, 2, 1, 2));
		assertTrue(pickedTheHighestValue(1, 5, 1, 5));
	}
	private boolean pickedTheHighestValue(double lValue, double pValue, double tValue, double expectedFactor) {
		solver = new DLXPentominoesSolver();
		ArrayList<Pentomino> Pentominoes = new ArrayList<Pentomino>();
		Pentominoes.add(TestRepo.getMultiPento(300, 'L', lValue));
		Pentominoes.add(TestRepo.getMultiPento(300, 'P', pValue));
		Pentominoes.add(TestRepo.getMultiPento(300, 'T', tValue));
		LayeredContainer solution = solver.Pack(Pentominoes);
		
		assertEquals(expectedFactor*solution.getPackedItemsCount(), solution.getValue(), 0.0001);
		return true;
	}
	@Test public void testPack_HighestValueWithPartialBin() {
		solver = new DLXPentominoesSolver();
		ArrayList<Pentomino> Pentominoes = new ArrayList<Pentomino>();
		Pentominoes.add(TestRepo.getMultiPento(10, 'L', 10));
		Pentominoes.add(TestRepo.getMultiPento(10, 'P', 10));
		Pentominoes.add(TestRepo.getMultiPento(10, 'T', 10));
		LayeredContainer solution = solver.Pack(Pentominoes);
		
		assertEquals(10*solution.getPackedItemsCount(), solution.getValue(), 0.0001);
	}
}
