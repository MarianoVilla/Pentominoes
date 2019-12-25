package TetrisLike3DSolver;

import java.util.ArrayList;

import Entities.Container;

public class LayeredContainer extends Container {
	
	public LayeredContainer(int id, double length, double width, double height) {
		super(id, length, width, height);
	}
	private ArrayList<SolutionLayer> layers = new ArrayList<SolutionLayer>();
	
	public ArrayList<SolutionLayer> getLayers(){
		return this.layers;
	}
	@Override
	public double getValue() {
		return this.layers.stream().mapToDouble(x -> x.getValue()).sum();
	}
	public int getPackedItemsCount() {
		return this.layers.stream().mapToInt(x -> x.getItemsCount()).sum();
	}
	
}
