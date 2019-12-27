package Algorithms;

import java.util.List;

import Entities.*;

/**
 * Defines the interface of a packing algorithm.
 *
 */
public interface PackingAlgorithm {
	
	AlgorithmPackingResult Run(Container container, List<Item> items);

}
