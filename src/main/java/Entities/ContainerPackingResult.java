package Entities;

import java.util.ArrayList;

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
