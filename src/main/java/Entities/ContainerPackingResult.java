package Entities;

import java.util.ArrayList;

/**
 * A container packing result is an abstraction that can hold multiple algorithm packing results for a given container.
 *
 */
public class ContainerPackingResult {
	
	private Container container;
	private ArrayList<AlgorithmPackingResult> algorithmPackingResults;
	
	public ContainerPackingResult()
	{
		this.algorithmPackingResults = new ArrayList<AlgorithmPackingResult>();
	}
	

	public Container getContainer() {
		return container;
	}


	public void setContainer(Container container) {
		this.container = container;
	}
	public ArrayList<AlgorithmPackingResult> getAlgorithmPackingResults() {
		return algorithmPackingResults;
	}
	public double getContainerWidth() {
		return this.container.getWidth();
	}
	public double getContainerHeight() {
		return this.container.getHeight();
	}
	public double getContainerLength() {
		return this.container.getLength();
	}
	public double getContainerVolume() {
		return this.container.getVolume();
	}
	/**
	 * The following three props are here to allow direct field binding from JavaFX.
	 * Still, it's to be noted that we're assuming we'll always use the first packing result in this object.
	 */
	public long getPackTimeInMilliseconds() {
		return this.algorithmPackingResults.get(0).getPackTimeInMilliseconds();
	}
	public double getPercentContainerVolumePacked() {
		return this.algorithmPackingResults.get(0).getPercentContainerVolumePacked();
	}
	public int getPackedItemsCount() {
		return this.algorithmPackingResults.get(0).getPackedItemsCount();
	}
	
	public void setAlgorithmPackingResults(ArrayList<AlgorithmPackingResult> algorithmPackingResults) {
		this.algorithmPackingResults = algorithmPackingResults;
	}
	
	public double getValue() {
		return algorithmPackingResults.stream().mapToDouble(a -> a.getValue()).sum();
	}
	


}
