package Entities;

/**
 * A ClassBItem is an Item with dimensions: 1x1.5x2. When you create an Item, you can pass it a quantity property. 
 * That way, you can use it as a single item or a collection of them.
 */
public class ClassBItem extends Item {

	public ClassBItem(int id, int quantity, double value) {
		super(id, 1.0, 1.5, 2.0, quantity, value);
	}
	@Override
	public String toString() {
		return "Class B";
	}

}
