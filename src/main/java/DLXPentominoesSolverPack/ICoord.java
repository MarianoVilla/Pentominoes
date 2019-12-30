package DLXPentominoesSolverPack;


public interface ICoord extends Comparable<ICoord> {
	int x();

	int y();

	int z();

	int tile();

// test whether coordinate lies in a range
	boolean isInRange(ICoord start, ICoord end);

	public String toString(boolean is3D, boolean hasTile);
}
