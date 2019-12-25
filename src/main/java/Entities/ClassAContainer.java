package Entities;

public class ClassAContainer extends Item {

	public ClassAContainer(int id, int quantity, double value) {
		super(id, 1.0, 1.0, 2.0, quantity, value);
	}

	@Override
	public String toString() {
		return "Class A";
	}
	
}
