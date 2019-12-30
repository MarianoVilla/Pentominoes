package DLXPentominoesSolverPack;
class MatrixCell {
	public MatrixColumnHeader header;
	public MatrixRowHeader rowHeader;
	public MatrixCell left, right, up, down;

	public MatrixCell( MatrixColumnHeader mh, MatrixRowHeader mrh){
		header = mh;
		rowHeader = mrh;
		if( mrh!=null && mh!=null ){
			// add to row;
			MatrixCell mc = mrh.first;
			right=mc;
			left = mc.left;
			mc.left.right = this;
			mc.left = this;
			
			// add to col
         mc = mh.first;
         down=mc;
         up = mc.up;
         mc.up.down = this;
         mc.up = this;
         mh.numberCells++;			
		}else{
			left = right = this;
			up = down = this;
		}
	}
	public void unlinkRow(){
		MatrixCell p=rowHeader.first.right;
		while(p!=rowHeader.first){
			p.header.numberCells--;
			p.up.down = p.down;
			p.down.up = p.up;
			p=p.right;
		}
	}
	public void linkRow(){
		MatrixCell p=rowHeader.first.left;
		while(p!=rowHeader.first){
			p.up.down = p;
			p.down.up = p;
			p.header.numberCells++;
			p=p.left;
		}
	}

	public void unlinkColumn(){	// only to be called on first cell
		MatrixCell p=this.down;
		while(p!=this){
			p.left.right = p.right;
			p.right.left = p.left;
			p=p.down;
		}
	}
	public void linkColumn(){	// only to be called on first cell
		MatrixCell p=this.up;
		while( p!=this ){
			p.left.right = p;
			p.right.left = p;
			p=p.up;
		}
	}
	public boolean isAllowed(){
		return header.isAllowed(1);
	}
}