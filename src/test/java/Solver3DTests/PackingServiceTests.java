package Solver3DTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import Entities.ClassAContainer;
import Entities.ClassBContainer;
import Entities.ClassCContainer;
import Entities.Container;
import Entities.ContainerPackingResult;
import Entities.DefaultContainer;
import Entities.Item;
import Services.PackingService;
import TestHelpers.TestRepo;

public class PackingServiceTests {
	
	@Test
	public void testPackAll_ShouldFitInOneContainer() {
		
		ArrayList<Item> Items = TestRepo.getThreeStandardItems();

		ArrayList<ContainerPackingResult> PackingResults = PackingService.PackAll(Items, new DefaultContainer(1));
		
		assertEquals(1, PackingResults.size());
		assertEquals(3, PackingResults.get(0).getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(100.0, PackingResults.get(0).getAlgorithmPackingResults().get(0).getPercentItemVolumePacked(), 0.00001);
	}
	/**
	 * We create a container that can only fit some of the given items. The heuristic should pick the packing with the highest value.
	 */
	@Test
	public void testPackMaximizingValue_ShouldPickTheOneWithTheHighestValue() {
		//Should fit the highest C item.
		ArrayList<Item> Items = new ArrayList<Item>() {{
			add(new ClassBContainer(1, 1, 1));
			add(new ClassAContainer(1, 1, 1));
			add(new ClassAContainer(1, 1, 1));
			add(new ClassAContainer(1, 1, 1));
			add(new ClassAContainer(1, 1, 1));
			add(new ClassAContainer(1, 1, 1));
			add(new ClassCContainer(1, 1, 6));
			add(new ClassCContainer(1, 1, 2));
			add(new ClassCContainer(1, 1, 10));
		}};
		
		ContainerPackingResult packingResult = PackingService.Pack(new Container(1, 2, 2, 2), Items);
		
		assertEquals(10.0, packingResult.getValue(), 0.0001);
		assertEquals(1, packingResult.getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(8, packingResult.getAlgorithmPackingResults().get(0).getUnpackedItems().size());
		
		//Should fit the highest C item.
		Items = new ArrayList<Item>() {{
			add(new ClassBContainer(1, 1, 1));
			add(new ClassAContainer(1, 1, 1));
			add(new ClassCContainer(1, 1, 6));
			add(new ClassCContainer(1, 1, 2));
		}};
		
		packingResult = PackingService.Pack(new Container(1, 2, 2, 2), Items);
		
		assertEquals(6.0, packingResult.getValue(), 0.0001);
		assertEquals(1, packingResult.getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(3, packingResult.getAlgorithmPackingResults().get(0).getUnpackedItems().size());
		
		//Should fit the A and B items, adding up to 21 in value.
		Items = new ArrayList<Item>() {{
			add(new ClassBContainer(1, 1, 1));
			add(new ClassAContainer(1, 1, 20));
			add(new ClassCContainer(1, 1, 6));
			add(new ClassCContainer(1, 1, 2));
		}};
		
		packingResult = PackingService.Pack(new Container(1, 2, 2, 2), Items);
		
		assertEquals(21.0, packingResult.getValue(), 0.0001);
		assertEquals(2, packingResult.getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(2, packingResult.getAlgorithmPackingResults().get(0).getUnpackedItems().size());
		
		//Should fit the B and both of the A items, adding up to 26.001 in value.
		Items = new ArrayList<Item>() {{
			add(new ClassBContainer(1, 1, 1));
			add(new ClassAContainer(1, 1, 19.001));
			add(new ClassAContainer(1, 1, 6));
			add(new ClassCContainer(1, 1, 26));
		}};
		
		packingResult = PackingService.Pack(new Container(1, 2, 2, 2), Items);
		
		assertEquals(26.001, packingResult.getValue(), 0.0001);
		assertEquals(3, packingResult.getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(1, packingResult.getAlgorithmPackingResults().get(0).getUnpackedItems().size());
		
		//Now it should prefer the C item, with a value of 26.1.
		Items = new ArrayList<Item>() {{
			add(new ClassBContainer(1, 1, 1));
			add(new ClassAContainer(1, 1, 19.001));
			add(new ClassAContainer(1, 1, 6));
			add(new ClassCContainer(1, 1, 26.1));
		}};
		
		packingResult = PackingService.Pack(new Container(1, 2, 2, 2), Items);
		
		assertEquals(26.1, packingResult.getValue(), 0.0001);
		assertEquals(1, packingResult.getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(3, packingResult.getAlgorithmPackingResults().get(0).getUnpackedItems().size());
	}
}
