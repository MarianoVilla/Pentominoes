package TetrisLike3DSolver;

import java.util.ArrayList;

import Entities.Container;

/**
 * A derived container used to store the 3D pentominoes solutions.
 * It's usable when the size of the pentominoes being stored allows for exact layering, using a divide and conquer approach.
 * In our case, the pentominoes are made out of 0.5x0.5x0.5 boxes, so we can stack them in the 4.0 height of our container.  
 */
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
