package TetrisLike3DSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import Entities.Container;
import Entities.DefaultContainer;
import GeneralPurposeHelpers.ListUtils;
import TetrisLike2DSolver.Pentominos2DSolverMT;
import TetrisLike2DSolver.Pentominos2DSolverMT.Solution;

public class Layering3DPentominoesSolver {
	private boolean foundPerfectSolution = false;
	private double containerWidth;
	private double containerHeight;
	private double containerLength;
	private int permutableLimit = 6;
	//TODO: we know this is the height for this problem domain. Still, a more general approach for getting the layers height
	//based on the pentominoes received would be nice. Keep in mind that there's no actual constraint in the Pentomino class
	//to keep a height of .5. We either enforce that there or here, or develop a smarter height solution.
	double layersHeight = 0.5;
	
	/**
	 * Class that solves 3D pentominoes problems by stacking 2D solutions.
	 * This implementation is severely dependent on the specific "0.5x0.5x0.5" pentos.
	 * Hence, there are domain-specific operations.
	 * Do not use this class as a generic solver without taking care of those operations. 
	 * @param containerWidth The container width. It'll multiplied by 2!
	 * @param containerHeight The container height. It'll multiplied by 2!
	 * @param containerLength The container length. It'll multiplied by 2!
	 */
	public Layering3DPentominoesSolver(double containerWidth, double containerHeight, double containerLength) {
		this.containerHeight = containerHeight;
		this.containerWidth = containerWidth;
		this.containerLength = containerLength;
	}
	private boolean isPermutableAmount(int amount) {
		return amount <= permutableLimit;
	}
	/**
	 * Note: this is only useful if we face a container limit. Otherwise, it makes NO SENSE. 
	 * Brute forcing permutations is an O(n!) problem; hence, the cost/benefit drops notably fast as n gets higher.
	 * Still, up until certain limits and until we get a better solution, this works as a special case, where the list
	 * received is so small that a deterministic approach is doable.
	 * @param Pentominoes The pentominoes that will be bruteforcely worked out.
	 * @return The collection of layered containers with the highest value of all possible permutations. 
	 */
	private ArrayList<LayeredContainer> computePermutableSolution(List<Pentomino> Pentominoes) {

		ArrayList<ArrayList<LayeredContainer>> possibleGlobalSolutions = new ArrayList<ArrayList<LayeredContainer>>();
		List<List<Pentomino>> Permutations = ListUtils.recursivePermutations(Pentominoes);
		for (List<Pentomino> perm : Permutations) {
			possibleGlobalSolutions.add(computeSolution(perm));
			if (foundPerfectSolution)
				break;
		}
		double finalSolutionValue = 0;
		int highestValueSolutionIndex = -1;
		for(int i = 0; i < possibleGlobalSolutions.size(); i++) {
			double possibleSolutionValue = possibleGlobalSolutions.get(i).stream().mapToDouble(LayeredContainer::getValue).sum();
			if (finalSolutionValue < possibleSolutionValue) {
				finalSolutionValue = possibleSolutionValue;
				highestValueSolutionIndex = i;
			}
		}
		return possibleGlobalSolutions.get(highestValueSolutionIndex);

	}
	/**
	 * The main interface of the class. Sorts by value and returns a collection of layered containers.
	 * @param Pentominoes
	 * @return
	 */
	public ArrayList<LayeredContainer> PackAll(List<Pentomino> Pentominoes) {
		
		Pentominoes.sort((p1, p2) -> Double.compare(p2.getValue(), p1.getValue()));

		return computeSolution(Pentominoes);
	}
	//TODO: refactor.
	//TODO: unit test.
	//TODO: implement in UI.
	public LayeredContainer Pack(LayeredContainer container, List<Pentomino> Pentominoes) {
		Pentominoes.sort((p1, p2) -> Double.compare(p2.getValue(), p1.getValue()));
		
		double containerHeight = container.getHeight();
		Pentominos2DSolverMT Solver;
		ArrayList<SolutionLayer> Solutions = new ArrayList<SolutionLayer>();
		Solution Sol = null;
		ArrayList<LayeredContainer> Containers = new ArrayList<LayeredContainer>() {{add(container);}};
		ArrayList<Pentomino> pentosForThisLayer = new ArrayList<Pentomino>();
		int i = 0;
		while(i < Pentominoes.size() && containerHeight >= layersHeight) {
				if(impossibleFit(Pentominoes.get(i), i)) {
					i++;
					continue;
				}
				alreadyPlacedIndexes.add(i);
				pentosForThisLayer.add(Pentominoes.get(i));
				Solver = new Pentominos2DSolverMT((int)(containerWidth/0.5), (int)(containerLength/0.5), pentosForThisLayer);
				Sol = Solver.solve();
				//The current config broke the solution: step back. 
				//Since we broke the solution, we have at least one extra item. Hence, there's no need to check if we've reached the end.
				if(Sol == null) {
					alreadyPlacedIndexes.remove(i);
					pentosForThisLayer.remove(Pentominoes.get(i));
					//Special case: we failed on the first pentomino.
					if(pentosForThisLayer.size() == 0) {
						i++;
						impossibleFits.add(Pentominoes.get(i).getClass());
						continue;
					}
					//Get the last working solution.
					Solver = new Pentominos2DSolverMT((int)(containerWidth/0.5), (int)(containerLength/0.5), pentosForThisLayer);
					Sol = Solver.solve();
					Solutions.add(new SolutionLayer(layersHeight, Sol));
					//We found a solution layer: reset the counters. 
					i = 0; pentosForThisLayer.clear();
				}else {
					i++;
					//If we don't have a null solution and haven't reached the last pentomino, we have to keep iterating.
					if(Pentominoes.size() == i) {
						//The solution is not null and there aren't pentominoes left:
						//we got every pentomino in the container; no need to keep searching.
						Solutions.add(new SolutionLayer(layersHeight, Sol));
						foundPerfectSolution = true;
						return fitLayersInContainers(Solutions, Containers).get(0);
					}
					//Otherwise, we still have pentominoes and our container seems to be able to fit more.
				}
				containerHeight -= layersHeight;
		}
		return Containers.get(0);
		
	}
	/**
	 * Stores the classes that cannot fit in the configured container size. This means that we tried to put one item of this class
	 * in an empty container and failed. Given that case, we assume that this kind of item won't ever fit. 
	 */
	@SuppressWarnings("rawtypes")
	private List<Class> impossibleFits = new ArrayList<Class>();
	/**
	 * Keeps the index of all placed shapes for the current set of pentominoes, to randomly exclude them.
	 */
	ArrayList<Integer> alreadyPlacedIndexes = new ArrayList<Integer>();
	
	private ArrayList<LayeredContainer> computeSolution(List<Pentomino> Pentominoes) {
		
		Pentominos2DSolverMT Solver;
		ArrayList<SolutionLayer> Solutions = new ArrayList<SolutionLayer>();
		Solution Sol = null;
		ArrayList<LayeredContainer> Containers = new ArrayList<LayeredContainer>();
		ArrayList<Pentomino> pentosForThisLayer = new ArrayList<Pentomino>();
		int i = 0;
		while(i < Pentominoes.size()) {
				if(impossibleFit(Pentominoes.get(i), i)) {
					i++;
					continue;
				}
				alreadyPlacedIndexes.add(i);
				pentosForThisLayer.add(Pentominoes.get(i));
				Solver = new Pentominos2DSolverMT((int)(containerWidth/0.5), (int)(containerLength/0.5), pentosForThisLayer);
				Sol = Solver.solve();
				//The current config broke the solution: step back. 
				//Since we broke the solution, we have at least one extra item. Hence, there's no need to check if we've reached the end.
				if(Sol == null) {
					alreadyPlacedIndexes.remove(i);
					pentosForThisLayer.remove(Pentominoes.get(i));
					//Special case: we failed on the first pentomino.
					if(pentosForThisLayer.size() == 0) {
						i++;
						impossibleFits.add(Pentominoes.get(i).getClass());
						continue;
					}
					//Get the last working solution.
					Solver = new Pentominos2DSolverMT((int)(containerWidth/0.5), (int)(containerLength/0.5), pentosForThisLayer);
					Sol = Solver.solve();
					Solutions.add(new SolutionLayer(layersHeight, Sol));
					//We found a solution layer: reset the counters. 
					i = 0; pentosForThisLayer.clear();
				}else {
					i++;
					//If we don't have a null solution and haven't reached the last pentomino, we have to keep iterating.
					if(Pentominoes.size() == i) {
						//The solution is not null and there aren't pentominoes left:
						//we got every pentomino in the container; no need to keep searching.
						Solutions.add(new SolutionLayer(layersHeight, Sol));
						foundPerfectSolution = true;
						return fitLayersInContainers(Solutions, Containers);
					}
					//Otherwise, we still have pentominoes and our container seems to be able to fit more.
				}
		}

		return fitLayersInContainers(Solutions, Containers);
	}
	private boolean impossibleFit(Pentomino pento, int index) {
		return alreadyPlacedIndexes.contains(index) || impossibleFits.contains(pento.getClass());
	}
	/**
	 * Takes a collection of solution layers and fits it in layered containers. It uses as many containers as needed to fit every layer. 
	 * @param solutionLayers The layers to be placed.
	 * @param containers The containers. The current implementation ignores everything that's already in this collection and
	 * simply adds containers with the layers to it. 
	 * @return
	 */
	private ArrayList<LayeredContainer> fitLayersInContainers(ArrayList<SolutionLayer> solutionLayers, ArrayList<LayeredContainer> containers){
		
		if(solutionLayers.size() == 0)
			return containers;
		double layersHeightSum = 0;
		LayeredContainer container = new LayeredContainer(0, containerLength, containerWidth, containerHeight);
		for(SolutionLayer s : solutionLayers) {
			if((container.getHeight()-layersHeightSum) < layersHeight) {
				containers.add(container);
				container = new LayeredContainer(0, containerLength, containerWidth, containerHeight);
				layersHeightSum = 0;
				continue;
			}
			container.getLayers().add(s);
			layersHeightSum += layersHeight;
		}
		containers.add(container);
		return containers;
		
	}
}
