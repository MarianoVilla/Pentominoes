package Entities;

import java.util.ArrayList;

public class ContainerPackingResult {
	
	private int containerID;
	private ArrayList<AlgorithmPackingResult> algorithmPackingResults;
	
	public ContainerPackingResult()
	{
		this.algorithmPackingResults = new ArrayList<AlgorithmPackingResult>();
	}

	public int getContainerID() {
		return containerID;
	}

	public void setContainerID(int containerID) {
		this.containerID = containerID;
	}

	public ArrayList<AlgorithmPackingResult> getAlgorithmPackingResults() {
		return algorithmPackingResults;
	}

	public void setAlgorithmPackingResults(ArrayList<AlgorithmPackingResult> algorithmPackingResults) {
		this.algorithmPackingResults = algorithmPackingResults;
	}
	
	public double getValue() {
		return algorithmPackingResults.stream().mapToDouble(a -> a.getValue()).sum();
	}
	


}
