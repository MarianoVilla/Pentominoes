package DLXPentominoesSolverPack;

class MatrixColumnHeader {
	public MatrixColumnHeader left, right;
	public MatrixCell first;// points to dummy root cell
	public int numberCells;
	private Polyomino poly;
	private ICoord coord;
	private int minValue, maxValue;
	private int currentValue;
	
	public MatrixColumnHeader(){
		init(null,0,0,null);
	}
	public MatrixColumnHeader(int min, int max){
		init(null,min, max,null);
	}
	public MatrixColumnHeader(Polyomino p, boolean mustUseAll){
	   int max = p.getAvailable();
	   int min = p.getMinAmount() - (p.getMaxAmount()-p.getAvailable());
		init(p, mustUseAll ? max : min,  max,null);
	}
	public MatrixColumnHeader(ICoord c){
		init(null, 1, 1,c);
	}

	private void init(Polyomino p, int min, int max, ICoord c0){
		poly=p;
		coord = c0;
		currentValue=0;
		minValue = min;
		maxValue = max;
		left=right=this;
		first = new MatrixCell(this,null);
	}
	public boolean isPoly(){ return coord==null; }
	public int getNumberCells(){ return numberCells; }
   public boolean isFullySatisfied(){ return currentValue==maxValue; }
   public boolean isSatisfied(){ return currentValue<=maxValue && currentValue>=minValue; }
   public boolean canBeSatisfied(){ return currentValue<=maxValue && currentValue+numberCells>=minValue; }
   public boolean isAllowed(int val){ return currentValue+val<=maxValue; }
	public void incValue(){
	   currentValue++;
	}
	public void decValue(){
	   currentValue--;
	}
	public void unlinkColumn(){
		left.right = right;
		right.left = left;
		first.unlinkColumn();
	}
	public void linkColumn(){
		left.right = this;
		right.left = this;
		first.linkColumn();
	}
	public void insert(MatrixColumnHeader mh){
		mh.left=left;
		mh.right=this;
		left.right =mh;
		left = mh;
	}
	public MatrixColumnHeader find(Polyomino p){
		if( poly == p ) return this;
		MatrixColumnHeader mh = right;
		while( mh!=this && mh.poly != p ) mh=mh.right;
		return (mh==this)? null : mh;
	}
	public MatrixColumnHeader find(ICoord c){
		if( c.equals(coord) ) return this;
		MatrixColumnHeader mh = right;
		while( mh!=this && !c.equals(mh.coord) ) mh=mh.right;
		return (mh==this)? null : mh;
	}
}