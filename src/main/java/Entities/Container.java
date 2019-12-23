package Entities;


public class Container {

	private int id;
	private double length;
	private double width;
	private double height;
	private double volume;
	private double value;


	/// <summary>
	/// Initializes a new instance of the Container class.
	/// </summary>
	/// <param name="id">The container ID.</param>
	/// <param name="length">The container length.</param>
	/// <param name="width">The container width.</param>
	/// <param name="height">The container height.</param>
	public Container(int id, double length, double width, double height)
	{
		this.setId(id);
		this.setLength(length);
		this.setWidth(width);
		this.setHeight(height);
		this.volume = height * length *width;
	}




	public int getId() {
		return id;
	}

	public void setId(int iD) {
		this.id = iD;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
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
	
	public Container clone() {
		return new Container(this.id, this.length, this.width, this.height);
	}
	public Container clone(int id) {
		return new Container(id, this.length, this.width, this.height);
	}



}
