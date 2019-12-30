package Entities;

/**
 * A ClassAItem is an Item with dimensions: 1x1x2. When you create an Item, you can pass it a quantity property. 
 * That way, you can use it as a single item or a collection of them. 
 */
public class ClassAItem extends Item {
	public ClassAItem(int id, int quantity, double value) {
		super(id, 1.0, 1.0, 2.0, quantity, value);
	}
	@Override
	public String toString() {
		return "Class A";
	}
	
}
