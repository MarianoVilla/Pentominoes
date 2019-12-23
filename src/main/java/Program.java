import java.util.ArrayList;


import Entities.*;
import TetrisLike2DSolver.*;
import TetrisLike2DSolver.Pentominos2DSolverMT.Solution;
import TetrisLike3DSolver.*;



public class Program {

	public static void main(String[] args) {
		/*
		 * int i = 1; ArrayList<Container> Containers = new ArrayList<Container>();
		 * Containers.add(new DefaultContainer(i)); ArrayList<Item> Items = new
		 * ArrayList<Item>(); Items.add(new ClassAContainer(1, 50, 50)); Items.add(new
		 * ClassBContainer(1, 50, 50)); Items.add(new ClassCContainer(1, 50, 50));
		 * ArrayList<Integer> AlgorythmIDs = new ArrayList<Integer>();
		 * AlgorythmIDs.add(1); ArrayList<ContainerPackingResult> PackingResults = new
		 * ArrayList<ContainerPackingResult>(); ContainerPackingResult Res =
		 * PackingService.Pack(Containers, Items, AlgorythmIDs).get(0);
		 * PackingResults.add(Res); ArrayList<Item> UnpackedItems =
		 * Res.getAlgorithmPackingResults().get(0).getUnpackedItems();
		 * while(UnpackedItems.size() > 0) { Containers.set(0, new DefaultContainer(i));
		 * Res = PackingService.Pack(Containers, UnpackedItems, AlgorythmIDs).get(0);
		 * UnpackedItems = Res.getAlgorithmPackingResults().get(0).getUnpackedItems();
		 * PackingResults.add(Res); i++; }
		 * 
		 * System.out.println(Res.toString());
		 */
		/*
		 * Pentominos2DSolverMT solver = new Pentominos2DSolverMT(2,5, 2,0,0); Solution
		 * sol = solver.solve();
		 */
		
		/*
		 * Pentominos2DSolverMT solver = new Pentominos2DSolverMT(2,5, 2,0,0); Solution
		 * sol = solver.solve();
		 */
		ArrayList<Pentomino> Pentominoes = new ArrayList<Pentomino>();
		Pentominoes.add(PentominoesDefaultFactory.Create('L'));
		Pentominoes.add(PentominoesDefaultFactory.Create('L'));
		Pentominoes.add(PentominoesDefaultFactory.Create('L'));
		

		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(3.0,3.0,3.0);
		//Each Solution is a 2D board. So we should stack them according to the height (0.5), and we'll get the maximum value 3D solution.
		ArrayList<LayeredContainer> Solutions = Solver3D.PackAll(Pentominoes);
	}

}
