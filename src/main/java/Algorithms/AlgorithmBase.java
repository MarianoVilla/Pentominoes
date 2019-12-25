package Algorithms;

import java.util.ArrayList;
import Entities.*;

public abstract class AlgorithmBase {

		public abstract ContainerPackingResult Run(Container container, ArrayList<Item> items);
}
