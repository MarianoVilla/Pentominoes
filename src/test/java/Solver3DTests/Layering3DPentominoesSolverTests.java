package Solver3DTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import TestHelpers.TestRepo;
import TetrisLike3DSolver.*;

/**
 * Unit tests for the Layering3DPentominoesSolver.
 *
 */
public class Layering3DPentominoesSolverTests {
	
	@Test
	public void testPackAll_ShouldReturnOneContainerWithTwoLayers() {
		ArrayList<Pentomino> Pentominoes = TestRepo.getPentos(3, 'L');
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(1.5,10,2);
		
		ArrayList<LayeredContainer> Solutions = Solver3D.PackAll(Pentominoes);
		assertEquals(2, Solutions.get(0).getLayers().size());
		
	}
	@Test
	public void testPackAll_ShouldFitInOneLayer() {
		ArrayList<Pentomino> Pentominoes = TestRepo.getPentos(3, 'L');
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(10.0,10.0,10.0);
		
		ArrayList<LayeredContainer> Solutions = Solver3D.PackAll(Pentominoes);
		assertEquals(1, Solutions.get(0).getLayers().size());
	}
	@Test
	public void testPackAll_ShouldGetTheCorrectValue() {
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(10.0,10.0,10.0);
		
		ArrayList<LayeredContainer> Solutions = Solver3D.PackAll(TestRepo.getPentos(3, 'L', 1));
		assertEquals(3, Solutions.get(0).getValue(), 0.0001);
		
		Solver3D = new Layering3DPentominoesSolver(10.0,10.0,10.0);
		Solutions = Solver3D.PackAll(TestRepo.getPentos(1, 'L', 1));
		assertEquals(1, Solutions.get(0).getValue(), 0.0001);
		
		Solver3D = new Layering3DPentominoesSolver(10.0,10.0,10.0);
		Solutions = Solver3D.PackAll(TestRepo.getPentos(2, 'L', 1.5));
		assertEquals(3, Solutions.get(0).getValue(), 0.0001);
	}
	@Test
	public void testPackAll_ShouldNotFitAny() {
		ArrayList<Pentomino> Pentominoes = TestRepo.getPentos(3, 'L');
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(1.0,1.0,1.0);
		
		ArrayList<LayeredContainer> Solutions = Solver3D.PackAll(Pentominoes);
		
		assertEquals(0, Solutions.size());
	}
	
	@Test
	public void testPack_ShouldFitAll() {
		ArrayList<Pentomino> Pentominoes = TestRepo.getPentos(3, 'L');
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(10,10,10);
		
		LayeredContainer Solution = Solver3D.Pack(Pentominoes);
		
		assertEquals(3, Solution.getPackedItemsCount());
	}
	@Test
	public void testPackInDefaultContainer_ShouldFitAll() {
		ArrayList<Pentomino> Pentominoes = TestRepo.getPentos(25, 'L');
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(2.5,4,16);
		
		LayeredContainer Solution = Solver3D.Pack(Pentominoes);
		
		assertEquals(25, Solution.getPackedItemsCount());
	}
	@Test
	public void testPack_ShouldFitTheHighestValueOne() {
		ArrayList<Pentomino> Pentominoes = new ArrayList<Pentomino>() 
		{{  
			addAll(TestRepo.getPentos(1, 'L', 1)); 
			addAll(TestRepo.getPentos(1,'L', 100));
			addAll(TestRepo.getPentos(1, 'L', 1)); 
		}}; 
		
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(1.5,0.5,2);
		
		LayeredContainer Solution = Solver3D.Pack(Pentominoes);
		
		assertEquals(2, Solution.getPackedItemsCount());
		assertEquals(101, Solution.getValue(), 0.0001);
	}
}
