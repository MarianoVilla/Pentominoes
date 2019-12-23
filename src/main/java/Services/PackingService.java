package Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Algorithms.*;
import Entities.*;
import GeneralPurposeHelpers.ListUtils;

public class PackingService {

	/**
	 * The simplest version of the Pack method. Assumes the algorithm is EB_AFIT.
	 * 
	 * @param container
	 * @param itemsToPack
	 * @return
	 */
	public static ContainerPackingResult Pack(Container container, List<Item> itemsToPack) {

		ContainerPackingResult containerPackingResult = new ContainerPackingResult();
		containerPackingResult.setContainerID(container.getId());

		PackingAlgorithm algorithm = new EB_AFIT();

		Stopwatch stopwatch = new Stopwatch();
		stopwatch.start();
		AlgorithmPackingResult algorithmResult = algorithm.Run(container, itemsToPack);
		stopwatch.stop();

		algorithmResult.setPackTimeInMilliseconds(stopwatch.elapsedInMilli());

		double containerVolume = container.getLength() * container.getWidth() * container.getHeight();
		double itemVolumePacked = algorithmResult.getPackedItems().stream().mapToDouble(Item::getVolume).sum();
		double itemVolumeUnpacked = algorithmResult.getUnpackedItems().stream().mapToDouble(Item::getVolume).sum();

		double percentContainerPacked = itemVolumePacked / containerVolume * 100.0;
		algorithmResult.setPercentContainerVolumePacked(Math.round(percentContainerPacked * 100) / 100.0);

		double percentItemPacked = itemVolumePacked / (itemVolumePacked + itemVolumeUnpacked) * 100;
		algorithmResult.setPercentItemVolumePacked(percentItemPacked);

		algorithmResult.setValue(algorithmResult.getPackedItems().stream().mapToDouble(i -> i.getValue()).sum());

		containerPackingResult.setAlgorithmPackingResults(new ArrayList<AlgorithmPackingResult>() {
			{
				add(algorithmResult);
			}
		});

		return containerPackingResult;
	}

	public static ArrayList<ContainerPackingResult> Pack(List<Container> containers, List<Item> itemsToPack,
			List<Integer> algorithmTypeIDs) {
		Object sync = new Object();
		ArrayList<ContainerPackingResult> result = new ArrayList<ContainerPackingResult>();

		containers.parallelStream().forEach((container) -> {
			ContainerPackingResult containerPackingResult = new ContainerPackingResult();
			containerPackingResult.setContainerID(container.getId());
			algorithmTypeIDs.parallelStream().forEach((algorithmTypeID) -> {
				PackingAlgorithm algorithm;
				try {
					algorithm = GetPackingAlgorithmFromTypeID(AlgorithmType.find(algorithmTypeID, () -> AlgorithmType.EB_AFIT));
				} catch (Exception e) {
					return;
				}
				ArrayList<Item> items = new ArrayList<Item>();
				itemsToPack.forEach(item -> {
					items.add(new Item(item.getId(), item.getDim1(), item.getDim2(), item.getDim3(), item.getQuantity(),
							item.getValue()));
				});

				Stopwatch stopwatch = new Stopwatch();
				stopwatch.start();
				AlgorithmPackingResult algorithmResult = algorithm.Run(container, items);
				stopwatch.stop();

				algorithmResult.setPackTimeInMilliseconds(stopwatch.elapsedInMilli());

				double containerVolume = container.getLength() * container.getWidth() * container.getHeight();
				double itemVolumePacked = algorithmResult.getPackedItems().stream().mapToDouble(Item::getVolume).sum();
				double itemVolumeUnpacked = algorithmResult.getUnpackedItems().stream().mapToDouble(Item::getVolume)
						.sum();

				double percentContainerPacked = itemVolumePacked / containerVolume * 100.0;
				algorithmResult.setPercentContainerVolumePacked(Math.round(percentContainerPacked * 100) / 100.0);

				double percentItemPacked = itemVolumePacked / (itemVolumePacked + itemVolumeUnpacked) * 100;
				algorithmResult.setPercentItemVolumePacked(percentItemPacked);

				algorithmResult
						.setValue(algorithmResult.getPackedItems().stream().mapToDouble(i -> i.getValue()).sum());

				synchronized (sync) {
					containerPackingResult.getAlgorithmPackingResults().add(algorithmResult);
				}

			});
			synchronized (sync) {
				result.add(containerPackingResult);
			}
		});

		return result;
	}
	/**
	 * Packs every item, using as many clones of the given container as needed. 
	 * @param itemsToPack
	 * @param algorithmTypeIDs
	 * @return
	 */
	public static ArrayList<ContainerPackingResult> PackAll(List<Item> itemsToPack, Container firstContainer) {
		int i = firstContainer.getId();
		ArrayList<ContainerPackingResult> PackingResults = new ArrayList<ContainerPackingResult>();
		ContainerPackingResult Res = Pack(firstContainer, itemsToPack);
		PackingResults.add(Res);
		ArrayList<Item> UnpackedItems = Res.getAlgorithmPackingResults().get(0).getUnpackedItems();
		while (UnpackedItems.size() > 0) {
			i++;
			Res = Pack(firstContainer.clone(i), UnpackedItems);
			UnpackedItems = Res.getAlgorithmPackingResults().get(0).getUnpackedItems();
			PackingResults.add(Res);
		}
		return PackingResults;
	}
	/*	*//**
			 * The limit until we consider a solution be computed by brute force.
			 */
	/*
	 * static int permutableSizeLimit = 6;
	 *//**
		 * Up to a rather small limit, brute forcing the best value is possible. After
		 * that, we'll need other techniques to maximize value.
		 * 
		 * @return Weather the given amount is smaller than the configured value
		 *         (permutableSizeLimit).
		 */
	/*
	 * private static boolean isPermutableSize(int amount) { return amount <=
	 * permutableSizeLimit; }
	 * 
	 * 
	 * public static <T extends Container> ContainerPackingResult
	 * PackMaximizingValue(List<Item> itemsToPack, T containerInstance) { if
	 * (isPermutableSize(itemsToPack.size())) { return PackBruteForce(itemsToPack,
	 * containerInstance); } itemsToPack.sort((i1, i2) ->
	 * Double.compare(i2.getValue(), i1.getValue())); return
	 * Pack(Arrays.asList(containerInstance.clone()), itemsToPack,
	 * Arrays.asList(1)).get(0); }
	 * 
	 * 
	 * private static <T extends Container> ContainerPackingResult
	 * PackBruteForce(List<Item> itemsToPack, T containerInstance) {
	 * List<List<Item>> permutations = ListUtils.recursivePermutations(itemsToPack);
	 * 
	 * List<ArrayList<ContainerPackingResult>> possibleSolutions = new
	 * ArrayList<ArrayList<ContainerPackingResult>>();
	 * 
	 * for(List<Item> perm : permutations) {
	 * possibleSolutions.add(Pack(Arrays.asList(containerInstance.clone()), perm,
	 * Arrays.asList(1))); }
	 * 
	 * return getHighestValueSolution(possibleSolutions); }
	 *//**
		 * Used by PackBruteForce to get the solution with the highest value of all.
		 * 
		 * @param possibleSolutions
		 * @return
		 *//*
			 * private static ContainerPackingResult
			 * getHighestValueSolution(List<ArrayList<ContainerPackingResult>>
			 * possibleSolutions) { double highestValue = -1; int highestValueIndex = -1;
			 * for(int i = 0; i < possibleSolutions.size(); i++) { double thisSolutionsValue
			 * =
			 * possibleSolutions.get(i).get(0).getAlgorithmPackingResults().get(0).getValue(
			 * ); if(highestValue < thisSolutionsValue) { highestValue = thisSolutionsValue;
			 * highestValueIndex = i; } } return
			 * possibleSolutions.get(highestValueIndex).get(0); }
			 */

	/**
	 * Gets the packing algorithm from the specified algorithm type ID.
	 * 
	 * @param algorithmTypeID The algorithm type ID.
	 * @return An instance of a packing algorithm implementing AlgorithmBase.
	 * @throws Exception: Invalid algorithm type.
	 */
	public static PackingAlgorithm GetPackingAlgorithmFromTypeID(AlgorithmType algorithmTypeID) throws Exception {
		switch (algorithmTypeID) {
		case EB_AFIT:
			return new EB_AFIT();

		default:
			throw new Exception("Invalid algorithm type.");
		}
	}
}
