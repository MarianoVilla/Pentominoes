package Services;

import Entities.ClassAItem;
import Entities.ClassBItem;
import Entities.ClassCItem;
import Entities.Item;

/**
 * Factory of predefined items.
 *
 */
public class ItemsFactory {
	
	/**
	 * Creates an item of the given class.
	 * @param itemClassName
	 * @param quantity
	 * @param value
	 * @param id
	 * @return
	 */
	public static Item Create(String itemClassName, int quantity, double value, int id) {
		switch(itemClassName) {
		case "Class A": return new ClassAItem(id, quantity, value);
		case "Class B": return new ClassBItem(id, quantity, value);
		case "Class C": return new ClassCItem(id, quantity, value);
		default: throw new IllegalArgumentException("No known item.");
		}
	}
}
