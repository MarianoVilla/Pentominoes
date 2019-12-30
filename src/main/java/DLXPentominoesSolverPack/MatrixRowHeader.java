package DLXPentominoesSolverPack;

class MatrixRowHeader {
	public Polyomino poly;
	public int numCells;
	public int numPieces;
	public CoordOri coord;
	public MatrixCell first;
	public MatrixRowHeader up, down;

	public MatrixRowHeader(Polyomino p, CoordOri c){
		poly=p;
		if( c!=null) coord = new CoordOri(c);
		first = new MatrixCell(null,this);
		up=down=this;
	}
	public void place(Board b){
		if( poly!=null ) poly.placeS(coord);
		else b.setContentsUnsafe(coord.coord,-2);
	}
	public void remove(Board b){
		if( poly!=null ) poly.removeS();
		else b.setContentsUnsafe(coord.coord,0);
	}

   public void unlinkRow(){
      up.down = down;
      down.up = up;
      first.unlinkRow();
   }
   public void linkRow(){
      up.down = this;
      down.up = this;
      first.linkRow();
   }

   public void insert(MatrixRowHeader mh){
		mh.up=up;
		mh.down=this;
		up.down =mh;
		up = mh;
	}

}