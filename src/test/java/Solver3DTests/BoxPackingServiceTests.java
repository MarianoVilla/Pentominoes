package Solver3DTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Entities.AlgorithmPackingResult;
import Entities.ClassAItem;
import Entities.ClassBItem;
import Entities.ClassCItem;
import Entities.Container;
import Entities.ContainerPackingResult;
import Entities.DefaultContainer;
import Entities.Item;
import Services.BoxPackingService;
import TestHelpers.TestRepo;

/**
 * Unit tests for the BoxPackingService.
 *
 */
@SuppressWarnings("serial")
public class BoxPackingServiceTests {
	
	@Test
	public void testPackAll_ShouldFitInOneContainer() {
		
		ArrayList<Item> Items = TestRepo.getThreeStandardItems();

		ArrayList<ContainerPackingResult> PackingResults = BoxPackingService.packAll(new DefaultContainer(1), Items);
		
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
			add(new ClassBItem(1, 1, 1));
			add(new ClassAItem(1, 1, 1));
			add(new ClassAItem(1, 1, 1));
			add(new ClassAItem(1, 1, 1));
			add(new ClassAItem(1, 1, 1));
			add(new ClassAItem(1, 1, 1));
			add(new ClassCItem(1, 1, 6));
			add(new ClassCItem(1, 1, 2));
			add(new ClassCItem(1, 1, 10));
		}};
		
		ContainerPackingResult packingResult = BoxPackingService.pack(new Container(1, 2, 2, 2), Items);
		
		assertEquals(10.0, packingResult.getValue(), 0.0001);
		assertEquals(1, packingResult.getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(8, packingResult.getAlgorithmPackingResults().get(0).getUnpackedItems().size());
		
		//Should fit the highest C item.
		Items = new ArrayList<Item>() {{
			add(new ClassBItem(1, 1, 1));
			add(new ClassAItem(1, 1, 1));
			add(new ClassCItem(1, 1, 6));
			add(new ClassCItem(1, 1, 2));
		}};
		
		packingResult = BoxPackingService.pack(new Container(1, 2, 2, 2), Items);
		
		assertEquals(6.0, packingResult.getValue(), 0.0001);
		assertEquals(1, packingResult.getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(3, packingResult.getAlgorithmPackingResults().get(0).getUnpackedItems().size());
		
		//Should fit the A and B items, adding up to 21 in value.
		Items = new ArrayList<Item>() {{
			add(new ClassBItem(1, 1, 1));
			add(new ClassAItem(1, 1, 20));
			add(new ClassCItem(1, 1, 6));
			add(new ClassCItem(1, 1, 2));
		}};
		
		packingResult = BoxPackingService.pack(new Container(1, 2, 2, 2), Items);
		
		assertEquals(21.0, packingResult.getValue(), 0.0001);
		assertEquals(2, packingResult.getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(2, packingResult.getAlgorithmPackingResults().get(0).getUnpackedItems().size());
		
		//Should fit the B and both of the A items, adding up to 26.001 in value.
		Items = new ArrayList<Item>() {{
			add(new ClassBItem(1, 1, 1));
			add(new ClassAItem(1, 1, 19.001));
			add(new ClassAItem(1, 1, 6));
			add(new ClassCItem(1, 1, 26));
		}};
		
		packingResult = BoxPackingService.pack(new Container(1, 2, 2, 2), Items);
		
		assertEquals(26.001, packingResult.getValue(), 0.0001);
		assertEquals(3, packingResult.getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(1, packingResult.getAlgorithmPackingResults().get(0).getUnpackedItems().size());
		
		//Now it should prefer the C item, with a value of 26.1.
		Items = new ArrayList<Item>() {{
			add(new ClassBItem(1, 1, 1));
			add(new ClassAItem(1, 1, 19.001));
			add(new ClassAItem(1, 1, 6));
			add(new ClassCItem(1, 1, 26.1));
		}};
		
		packingResult = BoxPackingService.pack(new Container(1, 2, 2, 2), Items);
		
		assertEquals(26.1, packingResult.getValue(), 0.0001);
		assertEquals(1, packingResult.getAlgorithmPackingResults().get(0).getPackedItemsCount());
		assertEquals(3, packingResult.getAlgorithmPackingResults().get(0).getUnpackedItems().size());
	}
	
	@Test
	public void testPack_NoOverlap() {
		ArrayList<Item> Items = TestRepo.getRandomItems(50);

		AlgorithmPackingResult PackingResults = BoxPackingService.pack(new DefaultContainer(1), Items).getAlgorithmPackingResults().get(0);
		
		for(int i = 0; i < PackingResults.getPackedItems().size()-1; i++) {
			if(isOverlapping(PackingResults.getPackedItems().get(i), PackingResults.getPackedItems().get(i+1)))
				assertTrue(false);
		}
		Items.clear();
		Items.add(new ClassBItem(1, 20, 1));
		Items.add(new ClassCItem(1, 20, 1));
		PackingResults = BoxPackingService.pack(new DefaultContainer(1), Items).getAlgorithmPackingResults().get(0);
		
		for(int i = 0; i < PackingResults.getPackedItems().size()-1; i++) {
			if(isOverlapping(PackingResults.getPackedItems().get(i), PackingResults.getPackedItems().get(i+1)))
				assertTrue(false);
		}
	}
    private boolean isOverlapping(Item a, Item b)
    {
        return (a.getCoordX() < b.getPackDimX() && b.getCoordX() < a.getPackDimX() && a.getCoordY() < b.getPackDimY() && b.getCoordY() < a.getPackDimY() && a.getCoordZ() < b.getPackDimZ() && b.getCoordZ() < a.getPackDimZ());
    }
}
