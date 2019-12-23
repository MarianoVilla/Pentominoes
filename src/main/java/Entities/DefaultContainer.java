package Entities;

public class DefaultContainer extends Container {

	/**
	 * Delegates to the Container constructor, using the hardcoded dimensions of our interest (L: 16.5, W: 2.5, H: 4.0).
	 * @param id The id of the container. It isn't used by any implemented algorithm so far. It could be eliminated in the spirit of YAGNI. 
	 */
	public DefaultContainer(int id) {
		super(id, 16.5, 2.5, 4.0);
	}
	/**
	 * ID-less overload of the DefaultContainer. Used to consistently assign -1 to every instance with a unimportant ID.
	 */
	public DefaultContainer() {
		super(-1, 16.5, 2.5, 4.0);
	}

}
