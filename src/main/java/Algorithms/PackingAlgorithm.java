package Algorithms;

import java.util.List;

import Entities.*;

public interface PackingAlgorithm {
	
	AlgorithmPackingResult Run(Container container, List<Item> items);

}
