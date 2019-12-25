package Entities;


public class Item {


	private Integer id;
	private boolean isPacked;
	private double dim1;
	private double dim2;
	private double dim3;
	private double coordX;
	private double coordY;
	private double coordZ;
	public int quantity;
	private double packDimX;
	private double packDimY;
	private double packDimZ;
	private double volume;
	private double value;

	/// <summary>
	/// Initializes a new instance of the Item class.
	/// </summary>
	/// <param name="id">The item ID.</param>
	/// <param name="dim1">The length of one of the three item dimensions.</param>
	/// <param name="dim2">The length of another of the three item dimensions.</param>
	/// <param name="dim3">The length of the other of the three item dimensions.</param>
	/// <param name="itemQuantity">The item quantity.</param>
	public Item(Integer id, double dim1, double dim2, double dim3, int quantity, double value)
	{
		this.setId(id);
		this.setDim1(dim1);
		this.setDim2(dim2);
		this.setDim3(dim3);
		this.volume = dim1 * dim2 * dim3;
		this.setQuantity(quantity);
		this.setValue(value);
	}
	
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer iD) {
		this.id = iD;
	}


	public boolean isPacked() {
		return isPacked;
	}


	public void setPacked(boolean isPacked) {
		this.isPacked = isPacked;
	}


	public double getDim1() {
		return dim1;
	}


	public void setDim1(double dim1) {
		this.dim1 = dim1;
	}


	public double getDim2() {
		return dim2;
	}


	public void setDim2(double dim2) {
		this.dim2 = dim2;
	}


	public double getDim3() {
		return dim3;
	}


	public void setDim3(double dim3) {
		this.dim3 = dim3;
	}


	public double getCoordX() {
		return coordX;
	}


	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}


	public double getCoordY() {
		return coordY;
	}


	public void setCoordY(double coordY) {
		this.coordY = coordY;
	}


	public double getCoordZ() {
		return coordZ;
	}


	public void setCoordZ(double coordZ) {
		this.coordZ = coordZ;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getPackDimX() {
		return packDimX;
	}


	public void setPackDimX(double packDimX) {
		this.packDimX = packDimX;
	}


	public double getPackDimY() {
		return packDimY;
	}


	public void setPackDimY(double packDimY) {
		this.packDimY = packDimY;
	}


	public double getPackDimZ() {
		return packDimZ;
	}


	public void setPackDimZ(double packDimZ) {
		this.packDimZ = packDimZ;
	}


	public double getVolume() {
		return volume;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}



}
