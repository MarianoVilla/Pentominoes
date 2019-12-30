package Algorithms;

import java.util.ArrayList;
import java.util.List;

import Entities.*;

/**
 * Java implementation of Ethan Baltacioglu's heuristic for bin packing. A copy of the thesis can be found at:
 * http://betterwaysystems.github.io/packer/reference/AirForceBinPacking.pdf
 * The original code is written in C in the appendix B of the document.
 * I based my implementation on this C# version: https://github.com/davidmchapman/3DContainerPacking.
 * This version has an additional value constraint.
 *
 */
public class EB_AFIT implements PackingAlgorithm{
	public AlgorithmPackingResult Run(Container container, List<Item> items)
	{
		Initialize(container, items);
		ExecuteIterations(container);
		Report(container);

		AlgorithmPackingResult result = new AlgorithmPackingResult();
		result.setAlgorithmID(AlgorithmType.EB_AFIT.getValue());
		result.setAlgorithmName(AlgorithmType.EB_AFIT.toString());

		for (int i = 1; i <= itemsToPackCount; i++)
		{
			itemsToPack.get(i).setQuantity(1);

			if (!itemsToPack.get(i).isPacked())
			{
				result.getUnpackedItems().add(itemsToPack.get(i));
			}
		}

		result.setPackedItems(itemsPackedInOrder);
		
		if (result.getUnpackedItems().size() == 0)
		{
			result.setIsCompletePack(true);
		}

		return result;
	}

	private ArrayList<Item> itemsToPack;
	private ArrayList<Item> itemsPackedInOrder;
	private ArrayList<Layer> layers;
	@SuppressWarnings("unused")
	private ContainerPackingResult result;

	private ScrapPad scrapfirst;
	private ScrapPad smallestZ;
	@SuppressWarnings("unused")
	private ScrapPad trash;

	private Boolean evened;
	private Boolean hundredPercentPacked = false;
	private Boolean layerDone;
	private Boolean packing;
	private Boolean packingBest = false;
	private Boolean quit = false;

	private int bboxi;
	private int bestIteration;
	private int bestVariant;
	private int boxi;
	private int cboxi;
	private int layerListLen;
	private int x;

	private double bbfx;
	private double bbfy;
	private double bbfz;
	private double bboxx;
	private double bboxy;
	private double bboxz;
	private double bboxValue;
	private double bfx;
	private double bfy;
	private double bfz;
	private double boxx;
	private double boxy;
	private double boxz;
	private double cboxx;
	private double cboxy;
	private double cboxz;
	private double layerinlayer;
	private double layerThickness;
	private double lilz;
	private double packedVolume;
	private double packedValue;
	private double packedy;
	private double prelayer;
	private double prepackedy;
	private double preremainpy;
	private double px;
	private double py;
	private double pz;
	private double remainpy;
	private double remainpz;
	private int itemsToPackCount;
	private double totalItemVolume;
	private double totalContainerVolume;
	
	
	/**
	 * Analyzes each unpacked box to find the best fitting one to the empty space given.
	 * @param hmx
	 * @param hy
	 * @param hmy
	 * @param hz
	 * @param hmz
	 * @param dim1
	 * @param dim2
	 * @param dim3
	 */
	private void AnalyzeBox(double hmx, double hy, double hmy, double hz, double hmz, double dim1, double dim2, double dim3, double boxValue)
	{
		if(dim1 > hmx || dim2 > hmy || dim3 > hmz) {
			return;
		}
		if (dim2 <= hy)
		{
			if ((hy - dim2 < bfy) || boxValue > bboxValue)
			{
				boxx = dim1;
				boxy = dim2;
				boxz = dim3;
				bboxValue = boxValue;
				bfx = hmx - dim1;
				bfy = hy - dim2;
				bfz = Math.abs(hz - dim3);
				boxi = x;
			}
			else if (hy - dim2 == bfy && hmx - dim1 < bfx && boxValue >= bboxValue)
			{
				boxx = dim1;
				boxy = dim2;
				boxz = dim3;
				bboxValue = boxValue;
				bfx = hmx - dim1;
				bfy = hy - dim2;
				bfz = Math.abs(hz - dim3);
				boxi = x;
			}
			else if (hy - dim2 == bfy && hmx - dim1 == bfx && Math.abs(hz - dim3) < bfz && boxValue >= bboxValue)
			{
				boxx = dim1;
				boxy = dim2;
				boxz = dim3;
				bboxValue = boxValue;
				bfx = hmx - dim1;
				bfy = hy - dim2;
				bfz = Math.abs(hz - dim3);
				boxi = x;
			}
		}
		else
		{
			if (dim2 - hy < bbfy && boxValue >= bboxValue)
			{
				bboxx = dim1;
				bboxy = dim2;
				bboxz = dim3;
				bboxValue = boxValue;
				bbfx = hmx - dim1;
				bbfy = dim2 - hy;
				bbfz = Math.abs(hz - dim3);
				bboxi = x;
			}
			else if (dim2 - hy == bbfy && hmx - dim1 < bbfx && boxValue >= bboxValue)
			{
				bboxx = dim1;
				bboxy = dim2;
				bboxz = dim3;
				bboxValue = boxValue;
				bbfx = hmx - dim1;
				bbfy = dim2 - hy;
				bbfz = Math.abs(hz - dim3);
				bboxi = x;
			}
			else if (dim2 - hy == bbfy && hmx - dim1 == bbfx && Math.abs(hz - dim3) < bbfz && boxValue >= bboxValue)
			{
				bboxx = dim1;
				bboxy = dim2;
				bboxz = dim3;
				bboxValue = boxValue;
				bbfx = hmx - dim1;
				bbfy = dim2 - hy;
				bbfz = hz - dim3;
				bboxi = x;
			}
		}
	}

	/**
	 * After finding each box, the candidate boxes and the condition of the layer are examined.
	 */
	private void CheckFound()
	{
		evened = false;

		if (boxi != 0)
		{
			cboxi = boxi;
			cboxx = boxx;
			cboxy = boxy;
			cboxz = boxz;
		}
		else
		{
			if ((bboxi > 0) && (layerinlayer != 0 || (smallestZ.Pre == null && smallestZ.Post == null)))
			{
				if (layerinlayer == 0)
				{
					prelayer = layerThickness;
					lilz = smallestZ.CumZ;
				}

				cboxi = bboxi;
				cboxx = bboxx;
				cboxy = bboxy;
				cboxz = bboxz;
				layerinlayer = layerinlayer + bboxy - layerThickness;
				layerThickness = bboxy;
			}
			else
			{
				if (smallestZ.Pre == null && smallestZ.Post == null)
				{
					layerDone = true;
				}
				else
				{
					evened = true;

					if (smallestZ.Pre == null)
					{
						trash = smallestZ.Post;
						smallestZ.CumX = smallestZ.Post.CumX;
						smallestZ.CumZ = smallestZ.Post.CumZ;
						smallestZ.Post = smallestZ.Post.Post;
						if (smallestZ.Post != null)
						{
							smallestZ.Post.Pre = smallestZ;
						}
					}
					else if (smallestZ.Post == null)
					{
						smallestZ.Pre.Post = null;
						smallestZ.Pre.CumX = smallestZ.CumX;
					}
					else
					{
						if (smallestZ.Pre.CumZ == smallestZ.Post.CumZ)
						{
							smallestZ.Pre.Post = smallestZ.Post.Post;

							if (smallestZ.Post.Post != null)
							{
								smallestZ.Post.Post.Pre = smallestZ.Pre;
							}

							smallestZ.Pre.CumX = smallestZ.Post.CumX;
						}
						else
						{
							smallestZ.Pre.Post = smallestZ.Post;
							smallestZ.Post.Pre = smallestZ.Pre;

							if (smallestZ.Pre.CumZ < smallestZ.Post.CumZ)
							{
								smallestZ.Pre.CumX = smallestZ.CumX;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Executes the packing algorithm variants.
	 * @param container
	 */
	private void ExecuteIterations(Container container)
	{
		int itelayer;
		int layersIndex;
		double bestValue = 0.0;
		for (int containerOrientationVariant = 1; (containerOrientationVariant <= 6) && !quit; containerOrientationVariant++)
		{
			switchContainerOrientationVariant(container, containerOrientationVariant);
			
			Layer nLayer = new Layer();
			nLayer.LayerEval = -1;
			layers.add(nLayer);
			ListCanditLayers();
			layers.sort((l1, l2) -> Double.compare(l1.LayerEval, l2.LayerEval));

			for (layersIndex = 1; (layersIndex <= layerListLen) && !quit; layersIndex++)
			{
				packedVolume = 0;
				packedValue = 0;
				packedy = 0;
				packing = true;
				layerThickness = layers.get(layersIndex).LayerDim;
				itelayer = layersIndex;
				remainpy = py;
				remainpz = pz;
				for (x = 1; x <= itemsToPackCount; x++)
				{
					itemsToPack.get(x).setPacked(false);
				}

				do
				{
					layerinlayer = 0;
					layerDone = false;

					PackLayer();

					packedy = packedy + layerThickness;
					remainpy = py - packedy;

					if (layerinlayer != 0 && !quit)
					{
						prepackedy = packedy;
						preremainpy = remainpy;
						remainpy = layerThickness - prelayer;
						packedy = packedy - layerThickness + prelayer;
						remainpz = lilz;
						layerThickness = layerinlayer;
						layerDone = false;

						PackLayer();

						packedy = prepackedy;
						remainpy = preremainpy;
						remainpz = pz;
					}

					FindLayer(remainpy);
				} while (packing && !quit);
				

				
				if((packedValue > bestValue) && !quit) {
					bestValue = packedValue; 
					bestVariant = containerOrientationVariant;
					bestIteration = itelayer;
				}
				 

				if (hundredPercentPacked) break;
			}

			if (hundredPercentPacked) break;

			if ((container.getLength() == container.getHeight()) && (container.getHeight() == container.getWidth())) containerOrientationVariant = 6;

			layers = new ArrayList<Layer>();
		}
	}
	
	private void switchContainerOrientationVariant(Container container, int containerOrientationVariant) {
		switch (containerOrientationVariant)
		{
			case 1:
				px = container.getLength(); py = container.getHeight(); pz = container.getWidth();
				break;

			case 2:
				px = container.getWidth(); py = container.getHeight(); pz = container.getLength();
				break;

			case 3:
				px = container.getWidth(); py = container.getLength(); pz = container.getHeight();
				break;

			case 4:
				px = container.getHeight(); py = container.getLength(); pz = container.getWidth();
				break;

			case 5:
				px = container.getLength(); py = container.getWidth(); pz = container.getHeight();
				break;

			case 6:
				px = container.getHeight(); py = container.getWidth(); pz = container.getLength();
				break;
		}
	}
		
	
	/**
	 * Finds the most proper boxes by looking at all six possible orientations,
	 * empty space given, adjacent boxes, and pallet limits.
	 * @param hmx
	 * @param hy
	 * @param hmy
	 * @param hz
	 * @param hmz
	 */
	private void FindBox(double hmx, double hy, double hmy, double hz, double hmz)
	{
		int y;
		bfx = 32767;
		bfy = 32767;
		bfz = 32767;
		bbfx = 32767;
		bbfy = 32767;
		bbfz = 32767;
		boxi = 0;
		bboxi = 0;

		for (y = 1; y <= itemsToPackCount; y = y + itemsToPack.get(y).getQuantity())
		{
			for (x = y; x < x + itemsToPack.get(y).getQuantity() - 1; x++)
			{
				if (!itemsToPack.get(x).isPacked()) break;
			}

			if (itemsToPack.get(x).isPacked()) continue;

			if (x > itemsToPackCount) return;

			AnalyzeBox(hmx, hy, hmy, hz, hmz, itemsToPack.get(x).getDim1(), itemsToPack.get(x).getDim2(), itemsToPack.get(x).getDim3(), itemsToPack.get(x).getValue());

			if ((itemsToPack.get(x).getDim1() == itemsToPack.get(x).getDim3()) && (itemsToPack.get(x).getDim3() == itemsToPack.get(x).getDim2())) continue;

			AnalyzeBox(hmx, hy, hmy, hz, hmz, itemsToPack.get(x).getDim1(), itemsToPack.get(x).getDim3(), itemsToPack.get(x).getDim2(), itemsToPack.get(x).getValue());
			AnalyzeBox(hmx, hy, hmy, hz, hmz, itemsToPack.get(x).getDim2(), itemsToPack.get(x).getDim1(), itemsToPack.get(x).getDim3(), itemsToPack.get(x).getValue());
			AnalyzeBox(hmx, hy, hmy, hz, hmz, itemsToPack.get(x).getDim2(), itemsToPack.get(x).getDim3(), itemsToPack.get(x).getDim1(), itemsToPack.get(x).getValue());
			AnalyzeBox(hmx, hy, hmy, hz, hmz, itemsToPack.get(x).getDim3(), itemsToPack.get(x).getDim1(), itemsToPack.get(x).getDim2(), itemsToPack.get(x).getValue());
			AnalyzeBox(hmx, hy, hmy, hz, hmz, itemsToPack.get(x).getDim3(), itemsToPack.get(x).getDim2(), itemsToPack.get(x).getDim1(), itemsToPack.get(x).getValue());
		}
	}

	/**
	 * Finds the most proper layer height by looking at the unpacked boxes and the remaining empty space available.
	 * @param thickness
	 */
	private void FindLayer(double thickness)
	{
		double exdim = 0;
		double dimdif;
		double dimen2 = 0;
		double dimen3 = 0;
		int y;
		int z;
		double layereval;
		double eval = 1000000;
		double layerValue;
		double val = -1;
		layerThickness = 0;

		for (x = 1; x <= itemsToPackCount; x++)
		{
			if (itemsToPack.get(x).isPacked()) continue;

			for (y = 1; y <= 3; y++)
			{
				switch (y)
				{
					case 1:
						exdim = itemsToPack.get(x).getDim1();
						dimen2 = itemsToPack.get(x).getDim2();
						dimen3 = itemsToPack.get(x).getDim3();
						break;

					case 2:
						exdim = itemsToPack.get(x).getDim2();
						dimen2 = itemsToPack.get(x).getDim1();
						dimen3 = itemsToPack.get(x).getDim3();
						break;

					case 3:
						exdim = itemsToPack.get(x).getDim3();
						dimen2 = itemsToPack.get(x).getDim1();
						dimen3 = itemsToPack.get(x).getDim2();
						break;
				}

				layereval = 0;
				layerValue = 0;

				if ((exdim <= thickness) && (((dimen2 <= px) && (dimen3 <= pz)) || ((dimen3 <= px) && (dimen2 <= pz))))
				{
					for (z = 1; z <= itemsToPackCount; z++)
					{
						if (!(x == z) && !(itemsToPack.get(z).isPacked()))
						{
							dimdif = Math.abs(exdim - itemsToPack.get(z).getDim1());

							if (Math.abs(exdim - itemsToPack.get(z).getDim2()) < dimdif)
							{
								dimdif =  Math.abs(exdim - itemsToPack.get(z).getDim2());
							}

							if (Math.abs(exdim - itemsToPack.get(z).getDim3()) < dimdif)
							{
								dimdif = Math.abs(exdim - itemsToPack.get(z).getDim3());
							}

							layereval += dimdif;
						}
					}
					layerValue += itemsToPack.get(x).getValue(); 
					if (layereval < eval || layerValue > val)
					{
						eval = layereval;
						layerThickness = exdim;
						val = layerValue;
					}
				}
			}
		}

		if (layerThickness == 0 || layerThickness > remainpy) packing = false;
	}

	/**
	 * Finds the first to be packed gap in the layer edge.
	 */
	private void FindSmallestZ()
	{
		ScrapPad scrapmemb = scrapfirst;
		smallestZ = scrapmemb;

		while (scrapmemb.Post != null)
		{
			if (scrapmemb.Post.CumZ < smallestZ.CumZ)
			{
				smallestZ = scrapmemb.Post;
			}

			scrapmemb = scrapmemb.Post;
		}
	}

	/**
	 * Initializes everything.
	 * @param container
	 * @param items
	 */
	private void Initialize(Container container, List<Item> items)
	{
		itemsToPack = new ArrayList<Item>();
		itemsPackedInOrder = new ArrayList<Item>();
		result = new ContainerPackingResult();

		// The original code uses 1-based indexing everywhere. This fake entry is added to the beginning
		// of the list to make that possible.
		itemsToPack.add(new Item(0, 0, 0, 0, 0, 0));

		layers = new ArrayList<Layer>();
		itemsToPackCount = 0;
		for(Item item : items) {
			
			for (int i = 1; i <= item.getQuantity(); i++)
			{
				itemsToPack.add(new Item(item.getId(), item.getDim1(), item.getDim2(), item.getDim3(), item.getQuantity(), item.getValue()));
			}

			itemsToPackCount += item.getQuantity();	
		}

		itemsToPack.add(new Item(0, 0, 0, 0, 0, 0));

		totalContainerVolume = container.getLength() * container.getHeight() * container.getWidth();
		totalItemVolume = 0;

		for (x = 1; x <= itemsToPackCount; x++)
		{
			totalItemVolume += itemsToPack.get(x).getVolume();
		}

		scrapfirst = new ScrapPad();

		scrapfirst.Pre = null;
		scrapfirst.Post = null;
		packingBest = false;
		hundredPercentPacked = false;
		quit = false;
	}
	/**
	 * Lists all possible layer heights by giving a weight value to each of them.
	 */
	private void ListCanditLayers()
	{
		Boolean same;
		double exdim = 0;
		double dimdif;
		double dimen2 = 0;
		double dimen3 = 0;
		int y;
		int z;
		int k;
		double layereval;
		double layerValue;

		layerListLen = 0;

		for (x = 1; x <= itemsToPackCount; x++)
		{
			for (y = 1; y <= 3; y++)
			{
				switch (y)
				{
					case 1:
						exdim = itemsToPack.get(x).getDim1();
						dimen2 = itemsToPack.get(x).getDim2();
						dimen3 = itemsToPack.get(x).getDim3();
						break;

					case 2:
						exdim = itemsToPack.get(x).getDim2();
						dimen2 = itemsToPack.get(x).getDim1();
						dimen3 = itemsToPack.get(x).getDim3();
						break;

					case 3:
						exdim = itemsToPack.get(x).getDim3();
						dimen2 = itemsToPack.get(x).getDim1();
						dimen3 = itemsToPack.get(x).getDim2();
						break;
				}

				if ((exdim > py) || (((dimen2 > px) || (dimen3 > pz)) && ((dimen3 > px) || (dimen2 > pz)))) continue;

				same = false;

				for (k = 1; k <= layerListLen; k++)
				{
					if (exdim == layers.get(k).LayerDim)
					{
						same = true;
						continue;
					}
				}

				if (same) continue;

				layereval = 0;
				layerValue = 0;
				
				for (z = 1; z <= itemsToPackCount; z++)
				{
					if (!(x == z))
					{
						dimdif = Math.abs(exdim - itemsToPack.get(z).getDim1());

						if (Math.abs(exdim - itemsToPack.get(z).getDim2()) < dimdif)
						{
							dimdif = Math.abs(exdim - itemsToPack.get(z).getDim2());
						}
						if (Math.abs(exdim - itemsToPack.get(z).getDim3()) < dimdif)
						{
							dimdif = Math.abs(exdim - itemsToPack.get(z).getDim3());
						}
						layereval = layereval + dimdif;
						layerValue += itemsToPack.get(z).getValue();
					}
				}

				layerListLen++;

				layers.add(new Layer());
				layers.get(layerListLen).LayerEval = layereval;
				layers.get(layerListLen).LayerValue = layerValue;
				layers.get(layerListLen).LayerDim = exdim;
			}
		}
	}

	/**
	* Transforms the found coordinate system to the one entered by the user and writes them
	* to the report file.
	*/
	private void OutputBoxList()
	{
		double packCoordX = 0;
		double packCoordY = 0;
		double packCoordZ = 0;
        double packDimX = 0;
        double packDimY = 0;
        double packDimZ = 0;

		switch (bestVariant)
		{
			case 1:
				packCoordX = itemsToPack.get(cboxi).getCoordX();
				packCoordY = itemsToPack.get(cboxi).getCoordY();
				packCoordZ = itemsToPack.get(cboxi).getCoordZ();
				packDimX = itemsToPack.get(cboxi).getPackDimX();
				packDimY = itemsToPack.get(cboxi).getPackDimY();
				packDimZ = itemsToPack.get(cboxi).getPackDimZ();
				break;

			case 2:
				packCoordX = itemsToPack.get(cboxi).getCoordZ();
				packCoordY = itemsToPack.get(cboxi).getCoordY();
				packCoordZ = itemsToPack.get(cboxi).getCoordX();
				packDimX = itemsToPack.get(cboxi).getPackDimZ();
				packDimY = itemsToPack.get(cboxi).getPackDimY();
				packDimZ = itemsToPack.get(cboxi).getPackDimX();
				break;

			case 3:
				packCoordX = itemsToPack.get(cboxi).getCoordY();
				packCoordY = itemsToPack.get(cboxi).getCoordZ();
				packCoordZ = itemsToPack.get(cboxi).getCoordX();
				packDimX = itemsToPack.get(cboxi).getPackDimY();
				packDimY = itemsToPack.get(cboxi).getPackDimZ();
				packDimZ = itemsToPack.get(cboxi).getPackDimX();
				break;

			case 4:
				packCoordX = itemsToPack.get(cboxi).getCoordY();
				packCoordY = itemsToPack.get(cboxi).getCoordX();
				packCoordZ = itemsToPack.get(cboxi).getCoordZ();
				packDimX = itemsToPack.get(cboxi).getPackDimY();
				packDimY = itemsToPack.get(cboxi).getPackDimX();
				packDimZ = itemsToPack.get(cboxi).getPackDimZ();
				break;

			case 5:
				packCoordX = itemsToPack.get(cboxi).getCoordX();
				packCoordY = itemsToPack.get(cboxi).getCoordZ();
				packCoordZ = itemsToPack.get(cboxi).getCoordY();
				packDimX = itemsToPack.get(cboxi).getPackDimX();
				packDimY = itemsToPack.get(cboxi).getPackDimZ();
				packDimZ = itemsToPack.get(cboxi).getPackDimY();
				break;

			case 6:
				packCoordX = itemsToPack.get(cboxi).getCoordZ();
				packCoordY = itemsToPack.get(cboxi).getCoordX();
				packCoordZ = itemsToPack.get(cboxi).getCoordY();
				packDimX = itemsToPack.get(cboxi).getPackDimZ();
				packDimY = itemsToPack.get(cboxi).getPackDimX();
				packDimZ = itemsToPack.get(cboxi).getPackDimY();
				break;
		}

		itemsToPack.get(cboxi).setCoordX(packCoordX);
		itemsToPack.get(cboxi).setCoordY(packCoordY);
		itemsToPack.get(cboxi).setCoordZ(packCoordZ);
		itemsToPack.get(cboxi).setPackDimX(packDimX);
		itemsToPack.get(cboxi).setPackDimY(packDimY);
		itemsToPack.get(cboxi).setPackDimZ(packDimZ);

		itemsPackedInOrder.add(itemsToPack.get(cboxi));
	}

	/**
	 * Packs the boxes found and arranges all variables and records properly.
	 */
	private void PackLayer()
	{
		double lenx;
		double lenz;
		double lpz;

		if (layerThickness == 0)
		{
			packing = false;
			return;
		}

		scrapfirst.CumX = px;
		scrapfirst.CumZ = 0;

		for (; !quit;)
		{
			FindSmallestZ();

			if ((smallestZ.Pre == null) && (smallestZ.Post == null))
			{
				//*** SITUATION-1: NO BOXES ON THE RIGHT AND LEFT SIDES ***

				lenx = smallestZ.CumX;
				lpz = remainpz - smallestZ.CumZ;
				FindBox(lenx, layerThickness, remainpy, lpz, lpz);
				CheckFound();

				if (layerDone) break;
				if (evened) continue;

				itemsToPack.get(cboxi).setCoordX(0);
				itemsToPack.get(cboxi).setCoordY(packedy);
				itemsToPack.get(cboxi).setCoordZ(smallestZ.CumZ);
				if (cboxx == smallestZ.CumX)
				{
					smallestZ.CumZ = smallestZ.CumZ + cboxz;
				}
				else
				{
					smallestZ.Post = new ScrapPad();

					smallestZ.Post.Post = null;
					smallestZ.Post.Pre = smallestZ;
					smallestZ.Post.CumX = smallestZ.CumX;
					smallestZ.Post.CumZ = smallestZ.CumZ;
					smallestZ.CumX = cboxx;
					smallestZ.CumZ = smallestZ.CumZ + cboxz;
				}
			}
			else if (smallestZ.Pre == null)
			{
				//*** SITUATION-2: NO BOXES ON THE LEFT SIDE ***

				lenx = smallestZ.CumX;
				lenz = smallestZ.Post.CumZ - smallestZ.CumZ;
				lpz = remainpz - smallestZ.CumZ;
				FindBox(lenx, layerThickness, remainpy, lenz, lpz);
				CheckFound();

				if (layerDone) break;
				if (evened) continue;

				itemsToPack.get(cboxi).setCoordY(packedy);
				itemsToPack.get(cboxi).setCoordZ(smallestZ.CumZ);
				if (cboxx == smallestZ.CumX)
				{
					itemsToPack.get(cboxi).setCoordX(0);

					if (smallestZ.CumZ + cboxz == smallestZ.Post.CumZ)
					{
						smallestZ.CumZ = smallestZ.Post.CumZ;
						smallestZ.CumX = smallestZ.Post.CumX;
						trash = smallestZ.Post;
						smallestZ.Post = smallestZ.Post.Post;

						if (smallestZ.Post != null)
						{
							smallestZ.Post.Pre = smallestZ;
						}
					}
					else
					{
						smallestZ.CumZ = smallestZ.CumZ + cboxz;
					}
				}
				else
				{
					itemsToPack.get(cboxi).setCoordX(smallestZ.CumX - cboxx);

					if (smallestZ.CumZ + cboxz == smallestZ.Post.CumZ)
					{
						smallestZ.CumX = smallestZ.CumX - cboxx;
					}
					else
					{
						smallestZ.Post.Pre = new ScrapPad();

						smallestZ.Post.Pre.Post = smallestZ.Post;
						smallestZ.Post.Pre.Pre = smallestZ;
						smallestZ.Post = smallestZ.Post.Pre;
						smallestZ.Post.CumX = smallestZ.CumX;
						smallestZ.CumX = smallestZ.CumX - cboxx;
						smallestZ.Post.CumZ = smallestZ.CumZ + cboxz;
					}
				}
			}
			else if (smallestZ.Post == null)
			{
				//*** SITUATION-3: NO BOXES ON THE RIGHT SIDE ***

				lenx = smallestZ.CumX - smallestZ.Pre.CumX;
				lenz = smallestZ.Pre.CumZ - smallestZ.CumZ;
				lpz = remainpz - smallestZ.CumZ;
				FindBox(lenx, layerThickness, remainpy, lenz, lpz);
				CheckFound();

				if (layerDone) break;
				if (evened) continue;

				itemsToPack.get(cboxi).setCoordY(packedy);
				itemsToPack.get(cboxi).setCoordZ(smallestZ.CumZ);
				itemsToPack.get(cboxi).setCoordX(smallestZ.Pre.CumX);

				if (cboxx == smallestZ.CumX - smallestZ.Pre.CumX)
				{
					if (smallestZ.CumZ + cboxz == smallestZ.Pre.CumZ)
					{
						smallestZ.Pre.CumX = smallestZ.CumX;
						smallestZ.Pre.Post = null;
					}
					else
					{
						smallestZ.CumZ = smallestZ.CumZ + cboxz;
					}
				}
				else
				{
					if (smallestZ.CumZ + cboxz == smallestZ.Pre.CumZ)
					{
						smallestZ.Pre.CumX = smallestZ.Pre.CumX + cboxx;
					}
					else
					{
						smallestZ.Pre.Post = new ScrapPad();

						smallestZ.Pre.Post.Pre = smallestZ.Pre;
						smallestZ.Pre.Post.Post = smallestZ;
						smallestZ.Pre = smallestZ.Pre.Post;
						smallestZ.Pre.CumX = smallestZ.Pre.Pre.CumX + cboxx;
						smallestZ.Pre.CumZ = smallestZ.CumZ + cboxz;
					}
				}
			}
			else if (smallestZ.Pre.CumZ == smallestZ.Post.CumZ)
			{
				//*** SITUATION-4: THERE ARE BOXES ON BOTH OF THE SIDES ***

				//*** SUBSITUATION-4A: SIDES ARE EQUAL TO EACH OTHER ***

				lenx = smallestZ.CumX - smallestZ.Pre.CumX;
				lenz = smallestZ.Pre.CumZ - smallestZ.CumZ;
				lpz = remainpz - smallestZ.CumZ;

				FindBox(lenx, layerThickness, remainpy, lenz, lpz);
				CheckFound();

				if (layerDone) break;
				if (evened) continue;

				itemsToPack.get(cboxi).setCoordY(packedy);
				itemsToPack.get(cboxi).setCoordZ(smallestZ.CumZ);

				if (cboxx == smallestZ.CumX - smallestZ.Pre.CumX)
				{
					itemsToPack.get(cboxi).setCoordX(smallestZ.Pre.CumX);

					if (smallestZ.CumZ + cboxz == smallestZ.Post.CumZ)
					{
						smallestZ.Pre.CumX = smallestZ.Post.CumX;

						if (smallestZ.Post.Post != null)
						{
							smallestZ.Pre.Post = smallestZ.Post.Post;
							smallestZ.Post.Post.Pre = smallestZ.Pre;
						}
						else
						{
							smallestZ.Pre.Post = null;
						}
					}
					else
					{
						smallestZ.CumZ = smallestZ.CumZ + cboxz;
					}
				}
				else if (smallestZ.Pre.CumX < px - smallestZ.CumX)
				{
					if (smallestZ.CumZ + cboxz == smallestZ.Pre.CumZ)
					{
						smallestZ.CumX = smallestZ.CumX - cboxx;
						itemsToPack.get(cboxi).setCoordX(smallestZ.CumX - cboxx);
					}
					else
					{
						itemsToPack.get(cboxi).setCoordX(smallestZ.Pre.CumX);
						smallestZ.Pre.Post = new ScrapPad();

						smallestZ.Pre.Post.Pre = smallestZ.Pre;
						smallestZ.Pre.Post.Post = smallestZ;
						smallestZ.Pre = smallestZ.Pre.Post;
						smallestZ.Pre.CumX = smallestZ.Pre.Pre.CumX + cboxx;
						smallestZ.Pre.CumZ = smallestZ.CumZ + cboxz;
					}
				}
				else
				{
					if (smallestZ.CumZ + cboxz == smallestZ.Pre.CumZ)
					{
						smallestZ.Pre.CumX = smallestZ.Pre.CumX + cboxx;
						itemsToPack.get(cboxi).setCoordX(smallestZ.Pre.CumX);
					}
					else
					{
						itemsToPack.get(cboxi).setCoordX(smallestZ.CumX - cboxx);
						smallestZ.Post.Pre = new ScrapPad();

						smallestZ.Post.Pre.Post = smallestZ.Post;
						smallestZ.Post.Pre.Pre = smallestZ;
						smallestZ.Post = smallestZ.Post.Pre;
						smallestZ.Post.CumX = smallestZ.CumX;
						smallestZ.Post.CumZ = smallestZ.CumZ + cboxz;
						smallestZ.CumX = smallestZ.CumX - cboxx;
					}
				}
			}
			else
			{
				//*** SUBSITUATION-4B: SIDES ARE NOT EQUAL TO EACH OTHER ***

				lenx = smallestZ.CumX - smallestZ.Pre.CumX;
				lenz = smallestZ.Pre.CumZ - smallestZ.CumZ;
				lpz = remainpz - smallestZ.CumZ;
				FindBox(lenx, layerThickness, remainpy, lenz, lpz);
				CheckFound();

				if (layerDone) break;
				if (evened) continue;

				itemsToPack.get(cboxi).setCoordY(packedy);
				itemsToPack.get(cboxi).setCoordZ(smallestZ.CumZ);
				itemsToPack.get(cboxi).setCoordX(smallestZ.Pre.CumX);

				if (cboxx == (smallestZ.CumX - smallestZ.Pre.CumX))
				{
					if ((smallestZ.CumZ + cboxz) == smallestZ.Pre.CumZ)
					{
						smallestZ.Pre.CumX = smallestZ.CumX;
						smallestZ.Pre.Post = smallestZ.Post;
						smallestZ.Post.Pre = smallestZ.Pre;
					}
					else
					{
						smallestZ.CumZ = smallestZ.CumZ + cboxz;
					}
				}
				else
				{
					if ((smallestZ.CumZ + cboxz) == smallestZ.Pre.CumZ)
					{
						smallestZ.Pre.CumX = smallestZ.Pre.CumX + cboxx;
					}
					else if (smallestZ.CumZ + cboxz == smallestZ.Post.CumZ)
					{
						itemsToPack.get(cboxi).setCoordX(smallestZ.CumX - cboxx);
						smallestZ.CumX = smallestZ.CumX - cboxx;
					}
					else
					{
						smallestZ.Pre.Post = new ScrapPad();

						smallestZ.Pre.Post.Pre = smallestZ.Pre;
						smallestZ.Pre.Post.Post = smallestZ;
						smallestZ.Pre = smallestZ.Pre.Post;
						smallestZ.Pre.CumX = smallestZ.Pre.Pre.CumX + cboxx;
						smallestZ.Pre.CumZ = smallestZ.CumZ + cboxz;
					}
				}
			}

			VolumeCheck();
		}
	}

	/**
	 * Using the parameters found, packs the best solution found and
	 * reports to the console.
	 * @param container
	 */
	private void Report(Container container)
	{
		quit = false;

		switch (bestVariant)
		{
			case 1:
				px = container.getLength(); py = container.getHeight(); pz = container.getWidth();
				break;

			case 2:
				px = container.getWidth(); py = container.getHeight(); pz = container.getLength();
				break;

			case 3:
				px = container.getWidth(); py = container.getLength(); pz = container.getHeight();
				break;

			case 4:
				px = container.getHeight(); py = container.getLength(); pz = container.getWidth();
				break;

			case 5:
				px = container.getLength(); py = container.getWidth(); pz = container.getHeight();
				break;

			case 6:
				px = container.getHeight(); py = container.getWidth(); pz = container.getLength();
				break;
		}

		packingBest = true;

		//Print("BEST SOLUTION FOUND AT ITERATION                      :", bestIteration, "OF VARIANT", bestVariant);
		//Print("TOTAL ITEMS TO PACK                                   :", itemsToPackCount);
		//Print("TOTAL VOLUME OF ALL ITEMS                             :", totalItemVolume);
		//Print("WHILE CONTAINER ORIENTATION X - Y - Z                 :", px, py, pz);

		layers.clear();
		Layer nLayer = new Layer();
		nLayer.LayerEval = -1;
		layers.add(nLayer);
		ListCanditLayers();
		layers.sort((l1, l2) -> Double.compare(l1.LayerEval, l2.LayerEval));
		packedVolume = 0;
		packedy = 0;
		packing = true;
		layerThickness = layers.get(bestIteration).LayerDim;
		remainpy = py;
		remainpz = pz;

		for (x = 1; x <= itemsToPackCount; x++)
		{
			itemsToPack.get(x).setPacked(false);
		}

		do
		{
			layerinlayer = 0;
			layerDone = false;
			PackLayer();
			packedy = packedy + layerThickness;
			remainpy = py - packedy;

			if (layerinlayer > 0.0001)
			{
				prepackedy = packedy;
				preremainpy = remainpy;
				remainpy = layerThickness - prelayer;
				packedy = packedy - layerThickness + prelayer;
				remainpz = lilz;
				layerThickness = layerinlayer;
				layerDone = false;
				PackLayer();
				packedy = prepackedy;
				remainpy = preremainpy;
				remainpz = pz;
			}
			if (!quit)
			{
				FindLayer(remainpy);
			}
		} while (packing && !quit);
	}

	/**
	 * After packing of each item, the 100% packing condition is checked.
	 */
	private void VolumeCheck()
	{
		itemsToPack.get(cboxi).setPacked(true);
		itemsToPack.get(cboxi).setPackDimX(cboxx);
		itemsToPack.get(cboxi).setPackDimY(cboxy);
		itemsToPack.get(cboxi).setPackDimZ(cboxz);
		packedVolume = packedVolume + itemsToPack.get(cboxi).getVolume();
		//Addendum.
		packedValue = packedValue + itemsToPack.get(cboxi).getValue();
		if (packingBest)
		{
			OutputBoxList();
		}
		else if (packedVolume == totalContainerVolume || packedVolume == totalItemVolume)
		{
			packing = false;
			hundredPercentPacked = true;
		}
	}


	/**
	 * A list that stores all the different lengths of all item dimensions.
	 * From the master's thesis:
	 * "Each Layerdim value in this array represents a different layer thickness
	 * value with which each iteration can start packing. Before starting iterations,
	 * all different lengths of all box dimensions along with evaluation values are
	 * stored in this array" (p. 3-6).
	 */
	private class Layer
	{
		/**
		 * (Addendum.) The layer value. 
		 */
		@SuppressWarnings("unused")
		public double LayerValue;

		/**
		 * The layer dimension value, representing a layer thickness.
		 */
		public double LayerDim;

		/**
		 * The layer eval value, representing an evaluation weight value for the corresponding LayerDim value.
		 */
		public double LayerEval;
	}

	/**
	 * From the master's thesis:
	 * "The double linked list we use keeps the topology of the edge of the
	 * current layer under construction. We keep the x and z coordinates of
	 * each gap's right corner. The program looks at those gaps and tries to
	 * fill them with boxes one at a time while trying to keep the edge of the
	 * layer even" (p. 3-7).
	 *
	 */
	private class ScrapPad
	{
		/**
		 * The x coordinate of the gap's right corner.
		 */
		public double CumX;
		
		/**
		 * The z coordinate of the gap's right corner.
		 */
		public double CumZ;

		/**
		 * The following entry.
		 */
		public ScrapPad Post;
		
		/**
		 * The previous entry. 
		 */
		public ScrapPad Pre;
	}

}
