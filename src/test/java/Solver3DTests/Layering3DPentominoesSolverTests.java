package Solver3DTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import TestHelpers.TestRepo;
import TetrisLike3DSolver.*;

public class Layering3DPentominoesSolverTests {


	
	@Test
	public void testRun_ShouldReturnOneContainerWithTwoLayers() {
		ArrayList<Pentomino> Pentominoes = TestRepo.getPentos(3, 'L');
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(1.5,10,2);
		
		ArrayList<LayeredContainer> Solutions = Solver3D.PackAll(Pentominoes);
		assertEquals(2, Solutions.get(0).getLayers().size());
		
	}
	@Test
	public void testRun_ShouldFitInOneLayer() {
		ArrayList<Pentomino> Pentominoes = TestRepo.getPentos(3, 'L');
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(10.0,10.0,10.0);
		
		ArrayList<LayeredContainer> Solutions = Solver3D.PackAll(Pentominoes);
		assertEquals(1, Solutions.get(0).getLayers().size());
	}
	@Test
	public void testRun_ShouldGetTheCorrectValue() {
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(10.0,10.0,10.0);
		
		ArrayList<LayeredContainer> Solutions = Solver3D.PackAll(TestRepo.getPentos(3, 'L', 1));
		assertEquals(3, Solutions.get(0).getValue(), 0.0001);
		
		Solutions = Solver3D.PackAll(TestRepo.getPentos(1, 'L', 1));
		assertEquals(1, Solutions.get(0).getValue(), 0.0001);
		
		Solutions = Solver3D.PackAll(TestRepo.getPentos(2, 'L', 1.5));
		assertEquals(3, Solutions.get(0).getValue(), 0.0001);
	}
	@Test
	public void testRun_ShouldNotFitAny() {
		ArrayList<Pentomino> Pentominoes = TestRepo.getPentos(3, 'L');
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(1.0,1.0,1.0);
		
		ArrayList<LayeredContainer> Solutions = Solver3D.PackAll(Pentominoes);
		
		assertEquals(0, Solutions.size());
	}
}
