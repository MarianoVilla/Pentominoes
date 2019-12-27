package TetrisLike3DSolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Takes care of the layers packing.
 *
 */
public class LayersPacker {
	private double containerWidth;
	private double containerHeight;
	private double containerLength;
	private double layersHeight;
	public LayersPacker(double containerWidth, double containerHeight, double containerLength, double layersHeight) {
		this.containerWidth = containerWidth;
		this.containerHeight = containerHeight;
		this.containerLength = containerLength;
		this.layersHeight = layersHeight;
	}
	/**
	 * Takes a collection of solution layers and fits it in layered containers. It uses as many containers as needed to fit every layer. 
	 * @param solutionLayers The layers to be placed.
	 * @param containers The containers. The current implementation ignores everything that's already in this collection and
	 * simply adds containers with the layers to it. 
	 * @return The collection of layerer containers.
	 */
	int lastPackedLayerIndex = 0;
	public ArrayList<LayeredContainer> fitLayersInContainers(ArrayList<SolutionLayer> solutionLayers){
		
		ArrayList<LayeredContainer> containers = new ArrayList<LayeredContainer>();
		if(solutionLayers.size() == 0)
			return containers;
		lastPackedLayerIndex = 0;
		while(lastPackedLayerIndex < solutionLayers.size()) {
			containers.add(fitLayersInContainer(solutionLayers));
		}
		return containers;
		
	}
	public LayeredContainer fitLayersInContainer(List<SolutionLayer> solutionLayers) {
		
		LayeredContainer container = new LayeredContainer(0, containerLength, containerWidth, containerHeight);
		if(solutionLayers.size() == 0)
			return container;
		
		double containerHeightState = containerHeight;
		
		while(lastPackedLayerIndex < solutionLayers.size() && containerHeightState >= layersHeight) {
			container.getLayers().add(solutionLayers.get(lastPackedLayerIndex));
			containerHeightState -= solutionLayers.get(lastPackedLayerIndex).getHeight();
			lastPackedLayerIndex++;
		}
		return container;
	}
}
