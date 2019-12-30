package TetrisLike3DSolver;

import java.util.ArrayList;
import java.util.List;
import GeneralPurposeHelpers.ListUtils;
import TetrisLike2DSolver.Pentominoes2DSolverMT;
import TetrisLike2DSolver.Pentominoes2DSolverMT.Solution;

public class Layering3DPentominoesSolver {
	private boolean foundPerfectSolution = false;
	private double containerWidth;
	private double containerHeight;
	private double containerLength;
	private LayersPacker layersPacker;
	private int permutableLimit = 6;
	//TODO: we know this is the height for this problem domain. Still, a more general approach for getting the layers height
	//based on the pentominoes received would be nice. Keep in mind that there's no actual constraint in the Pentomino class
	//to keep a height of .5. We either enforce that there or here, or develop a smarter height solution.
	double layersHeight = 0.5;
	
	/**
	 * Class that solves 3D pentominoes problems by stacking 2D solutions.
	 * This implementation is severely dependent on the specific "0.5x0.5x0.5" pentos constraint.
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
		this.layersPacker = new LayersPacker(containerWidth, containerHeight, containerLength, layersHeight);
	}
	/**
	 * Sorts by value, packs and returns a collection of layered containers.
	 * It packs EVERY given pentomino, using as many layers/containers as needed.
	 * @param Pentominoes
	 * @return
	 */
	public ArrayList<LayeredContainer> PackAll(List<Pentomino> Pentominoes) {
		
		if(isPermutableAmount(Pentominoes.size())) {
			ArrayList<SolutionLayer> Layers = computePermutableSolution(Pentominoes);
			return layersPacker.fitLayersInContainers(Layers);
		}
		ArrayList<SolutionLayer> Layers = ComputeLayers(Pentominoes);
		Layers.sort((l1, l2) ->  Double.compare(l1.getValue(), l2.getValue()));
		return layersPacker.fitLayersInContainers(Layers);
	}
	/**
	 * Sorts by value, packs and return a LayeredContainer.
	 * It only packs as many items as can fit in one container, trying to maximize the value.
	 * @param Pentominoes The pentominoes to pack.
	 * @return One LayeredContainer with as many containers as it can hold.
	 */
	public LayeredContainer Pack(List<Pentomino> Pentominoes) {
		if(isPermutableAmount(Pentominoes.size())) {
			ArrayList<SolutionLayer> Layers = computePermutableSolution(Pentominoes);
			return layersPacker.fitLayersInContainer(Layers);
		}
		ArrayList<SolutionLayer> Layers = ComputeLayers(Pentominoes);
		Layers.sort((l1, l2) ->  Double.compare(l1.getValue(), l2.getValue()));
		return layersPacker.fitLayersInContainer(Layers);
	}
	/**
	 * Checks if a given amount is less than or equal to the permutable limit.
	 */
	private boolean isPermutableAmount(int amount) {
		return amount <= permutableLimit;
	}
	/**
	 * Note: this is only useful if we face a container limit. Otherwise, it makes NO SENSE. 
	 * Brute forcing permutations is an O(n!) problem; hence, the cost/benefit drops notably fast as n gets higher.
	 * Still, up until certain limits, this works as a special case, where the list
	 * received is so small that a deterministic approach is doable.
	 * @param Pentominoes The pentominoes that will be bruteforcely worked out.
	 * @return The collection of layered containers with the highest value of all possible permutations. 
	 */
	private ArrayList<SolutionLayer> computePermutableSolution(List<Pentomino> Pentominoes) {

		ArrayList<ArrayList<SolutionLayer>> possibleGlobalSolutions = new ArrayList<ArrayList<SolutionLayer>>();
		List<List<Pentomino>> Permutations = ListUtils.recursivePermutations(Pentominoes);
		for (List<Pentomino> perm : Permutations) {
			possibleGlobalSolutions.add(ComputeLayers(perm));
			if (foundPerfectSolution)
				break;
		}
		double finalSolutionValue = 0;
		int highestValueSolutionIndex = 0;
		for(int i = 0; i < possibleGlobalSolutions.size(); i++) {
			double possibleSolutionValue = possibleGlobalSolutions.get(i).stream().mapToDouble(SolutionLayer::getValue).sum();
			if (finalSolutionValue < possibleSolutionValue) {
				finalSolutionValue = possibleSolutionValue;
				highestValueSolutionIndex = i;
			}
		}
		return possibleGlobalSolutions.get(highestValueSolutionIndex);
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
	/**
	 * Given a list of pentominoes, this method will create a list of layers containing them.
	 * The layers can then be put into LayeredContainers.
	 * This isn't a general solution: we assume the layers' height to be 0.5 and hence there are some hardcoded bits.
	 * @param Pentominoes
	 * @return
	 */
	private ArrayList<SolutionLayer> ComputeLayers(List<Pentomino> Pentominoes) {
		
		Pentominoes.sort((p1, p2) -> Double.compare(p2.getValue(), p1.getValue()));
		
		LayeredContainer container = new LayeredContainer(0, containerLength, containerWidth, containerHeight);
		
		double remainingContainerHeight = container.getHeight();
		Pentominoes2DSolverMT Solver;
		ArrayList<SolutionLayer> Solutions = new ArrayList<SolutionLayer>();
		Solution Sol = null;
		ArrayList<Pentomino> pentosForThisLayer = new ArrayList<Pentomino>();
		int i = 0;
		//We could improve this algorithm by first trying to solve for the layer using an X amount of pentominoes for a layer Y,
		//considering that sum(X.volume) < Y.volume. That way, we go backwards, taking a rather optimistic approach:
		//first, we assume that X fits perfectly in Y. If it doesn't, we can take one pento out and try again.
		//I can't profile the two solutions right now, but let's add a task: TODO: test and try it out.
		while(i < Pentominoes.size() && remainingContainerHeight >= layersHeight) {
				if(impossibleFit(Pentominoes.get(i), i)) {
					i++;
					continue;
				}
				alreadyPlacedIndexes.add(i);
				pentosForThisLayer.add(Pentominoes.get(i));
				Solver = new Pentominoes2DSolverMT((int)(containerWidth/0.5), (int)(containerLength/0.5), pentosForThisLayer);
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
					Solver = new Pentominoes2DSolverMT((int)(containerWidth/0.5), (int)(containerLength/0.5), pentosForThisLayer);
					Sol = Solver.solve();
					Solutions.add(new SolutionLayer(layersHeight, Sol));
					//We found a solution layer: reset the counters and subtract the height of the layer to the remaining container height.
					remainingContainerHeight -= layersHeight;
					i = 0; pentosForThisLayer.clear();
				}else {
					i++;
					//If we don't have a null solution and haven't reached the last pentomino, we have to keep iterating.
					if(Pentominoes.size() == i) {
						//The solution is not null and there aren't pentominoes left:
						//we got every pentomino in the container; no need to keep searching.
						Solutions.add(new SolutionLayer(layersHeight, Sol));
						foundPerfectSolution = true;
						break;
					}
					//Otherwise, we still have pentominoes and our container seems to be able to fit more.
				}
		}
		cleanUp();
		return Solutions;
	}
	/**
	 * Extraction of a double conditional. Checks whether a pento has been put in the impossibleFits collection,
	 * and if the given index is in the alreadyPlacedIndexes collection.
	 * @param pento
	 * @param index
	 * @return
	 */
	private boolean impossibleFit(Pentomino pento, int index) {
		return alreadyPlacedIndexes.contains(index) || impossibleFits.contains(pento.getClass());
	}
	/**
	 * Although unused, this method should preserve the idea of this object having a global state. Solving has permanent side effects.
	 */
	private void cleanUp() {
		alreadyPlacedIndexes.clear();
		impossibleFits.clear();
	}
}
