import java.util.ArrayList;
import java.util.List;

import Algorithms.*;
import Entities.*;

public class PackingService {
	public static ArrayList<ContainerPackingResult> Pack(List<Container> containers, List<Item> itemsToPack, List<Integer> algorithmTypeIDs)
	{
		Object sync = new Object();
		ArrayList<ContainerPackingResult> result = new ArrayList<ContainerPackingResult>();
		
		containers.parallelStream().forEach((container) ->
		{ 
			ContainerPackingResult containerPackingResult = new ContainerPackingResult();
			containerPackingResult.setContainerID(container.getId());
			algorithmTypeIDs.parallelStream().forEach((algorithmTypeID) -> 
			{
				PackingAlgorithm algorithm;
				try {
					algorithm = GetPackingAlgorithmFromTypeID(AlgorithmType.find(algorithmTypeID, () -> AlgorithmType.EB_AFIT));
				} catch (Exception e) {
					return;
				}
				ArrayList<Item> items = new ArrayList<Item>();
				itemsToPack.forEach(item -> { items.add(new Item(item.getId(), item.getDim1(), item.getDim2(), item.getDim3(), item.getQuantity(), item.getValue())); });
				
				Stopwatch stopwatch = new Stopwatch();
				stopwatch.start();
				AlgorithmPackingResult algorithmResult = algorithm.Run(container, items);
				stopwatch.stop();
				
				algorithmResult.setPackTimeInMilliseconds(stopwatch.elapsedInMilli());

				double containerVolume = container.getLength() * container.getWidth() * container.getHeight();
				double itemVolumePacked = algorithmResult.getPackedItems().stream().mapToDouble(Item::getVolume).sum();
				double itemVolumeUnpacked = algorithmResult.getUnpackedItems().stream().mapToDouble(Item::getVolume).sum();
				
				double percentContainerPacked = itemVolumePacked / containerVolume * 100.0;
				algorithmResult.setPercentContainerVolumePacked(percentContainerPacked);
				
				double percentItemPacked = itemVolumePacked / (itemVolumePacked+itemVolumeUnpacked)*100;
				algorithmResult.setPercentItemVolumePacked(percentItemPacked);

				synchronized (sync)
				{
					containerPackingResult.getAlgorithmPackingResults().add(algorithmResult);
				}
				
			});
			
			synchronized (sync)
			{
				result.add(containerPackingResult);
			}
		});
		
		
		return result;
	}

	/// <summary>
	/// Gets the packing algorithm from the specified algorithm type ID.
	/// </summary>
	/// <param name="algorithmTypeID">The algorithm type ID.</param>
	/// <returns>An instance of a packing algorithm implementing AlgorithmBase.</returns>
	/// <exception cref="System.Exception">Invalid algorithm type.</exception>
	public static PackingAlgorithm GetPackingAlgorithmFromTypeID(AlgorithmType algorithmTypeID) throws Exception
	{
		switch (algorithmTypeID)
		{
			case EB_AFIT:
				return new EB_AFIT();

			default:
				throw new Exception("Invalid algorithm type.");
		}
	}
}
