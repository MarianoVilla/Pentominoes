package Services;
import java.util.ArrayList;
import java.util.List;

import Entities.ClassAContainer;
import Entities.ClassBContainer;
import Entities.ClassCContainer;
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
		case "Entities.ClassAContainer": return new ClassAContainer(id, quantity, value);
		case "Entities.ClassBContainer": return new ClassBContainer(id, quantity, value);
		case "Entities.ClassCContainer": return new ClassCContainer(id, quantity, value);
		default: throw new IllegalArgumentException("No known item.");
		}
	}
}
