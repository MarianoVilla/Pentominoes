package Entities;

/**
 * A ClassCItem is an Item with dimensions: 1.5x1.5x1.5. When you create an Item, you can pass it a quantity property. 
 * That way, you can use it as a single item or a collection of them.
 */
public class ClassCItem extends Item {

	public ClassCItem(int id, int quantity, double value) {
		super(id, 1.5, 1.5, 1.5, quantity, value);
	}
	@Override
	public String toString() {
		return "Class C";
	}

}
