package Entities;

public class ClassBContainer extends Item {

	public ClassBContainer(int id, int quantity, double value) {
		super(id, 1.0, 1.5, 2.0, quantity, value);
	}
	@Override
	public String toString() {
		return "Class B";
	}

}
