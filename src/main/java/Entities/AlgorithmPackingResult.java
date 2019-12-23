package Entities;

import java.util.ArrayList;

public class AlgorithmPackingResult {

	private int algorithmID;
	private String algorithmName;
	private Boolean isCompletePack;
	private ArrayList<Item> packedItems;
	private long packTimeInMilliseconds;
	private double percentContainerVolumePacked;
	private double percentItemVolumePacked;
	private ArrayList<Item> unpackedItems;
	private double value;
	private int packedItemsCount;
	


	//TODO: optimize. It's a fire and forget prop.
	public int getPackedItemsCount() {
		return packedItems.size();
	}

	public AlgorithmPackingResult()
	{
		this.packedItems = new ArrayList<Item>();
		this.unpackedItems = new ArrayList<Item>();
	}
	
	public double getValue() {
		return this.value;
	}


	public void setValue(double value) {
		this.value = value;
	}

	

	public int getAlgorithmID() {
		return algorithmID;
	}

	public void setAlgorithmID(int algorithmID) {
		this.algorithmID = algorithmID;
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public Boolean getIsCompletePack() {
		return isCompletePack;
	}

	public void setIsCompletePack(Boolean isCompletePack) {
		this.isCompletePack = isCompletePack;
	}

	public ArrayList<Item> getPackedItems() {
		return packedItems;
	}

	public void setPackedItems(ArrayList<Item> packedItems) {
		this.packedItems = packedItems;
	}

	public long getPackTimeInMilliseconds() {
		return packTimeInMilliseconds;
	}

	public void setPackTimeInMilliseconds(long packTimeInMilliseconds) {
		this.packTimeInMilliseconds = packTimeInMilliseconds;
	}

	public double getPercentContainerVolumePacked() {
		return percentContainerVolumePacked;
	}

	public void setPercentContainerVolumePacked(double percentContainerVolumePacked) {
		this.percentContainerVolumePacked = percentContainerVolumePacked;
	}

	public double getPercentItemVolumePacked() {
		return percentItemVolumePacked;
	}

	public void setPercentItemVolumePacked(double percentItemVolumePacked) {
		this.percentItemVolumePacked = percentItemVolumePacked;
	}

	public ArrayList<Item> getUnpackedItems() {
		return unpackedItems;
	}

	public void setUnpackedItems(ArrayList<Item> unpackedItems) {
		this.unpackedItems = unpackedItems;
	}



}
