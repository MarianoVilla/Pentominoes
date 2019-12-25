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
		containerPackingResult.setContainer(container);

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
			containerPackingResult.setContainer(container);
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
