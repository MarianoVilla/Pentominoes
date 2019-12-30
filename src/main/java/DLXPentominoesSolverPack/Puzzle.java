package DLXPentominoesSolverPack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DLXGridTypes.GridTypeFactory;
import DLXGridTypes.IGridType;

public class Puzzle
implements Runnable
{
public enum SolveMode{
   FillAll,
   FillMost,
   UseAll,
   UseMost,
   UseMostEarlyBreak,
   Unknown
}

private ChangeFlags changeFlags = new ChangeFlags();
private List<Polyomino> polyominoList = new ArrayList<Polyomino>();
private Board board = new Board(changeFlags);
private ForgetfulList<Solution> solutions;
private int cellsUsedInSolution;
private int piecesUsedInSolution;
private ActionListener listener;
private IGridType gridType=GridTypeFactory.factory("square");  // default
private Solution startPosition = null;
private int[] maxSpacers;
private int[] minSpacers;
private boolean[] solveModeAvailable = {true,true,true,true,true};
private SolveMode solveMode = SolveMode.Unknown;

private String description = "";
private String notes = "";

private class Stack {
   ArrayList<Boolean> typeIsRow; 
   ArrayList<Object> rowcols; 
   Stack(int size){
      typeIsRow = new ArrayList<Boolean>(size); 
      rowcols = new ArrayList<Object>(size); 
   }
   void push(MatrixRowHeader mrh){
      rowcols.add(mrh);
      typeIsRow.add(true);
   }
   void push(MatrixColumnHeader mch){
      rowcols.add(mch);
      typeIsRow.add(false);
   }
   boolean lastIsRow(){
      return typeIsRow.get(typeIsRow.size()-1);
   }
   MatrixRowHeader popRow(){
      if(!lastIsRow()){
         throw new RuntimeException();
      }
      typeIsRow.remove(typeIsRow.size()-1);
      MatrixRowHeader mrh = (MatrixRowHeader) rowcols.get(rowcols.size()-1);
      rowcols.remove(rowcols.size()-1);
      return mrh;
   }
   MatrixColumnHeader popCol(){
      if(lastIsRow()){
         throw new RuntimeException();
      }
      typeIsRow.remove(typeIsRow.size()-1);
      MatrixColumnHeader mch = (MatrixColumnHeader) rowcols.get(rowcols.size()-1);
      rowcols.remove(rowcols.size()-1);
      return mch;
   }
}

public Puzzle(){
   //int numRetained = PolySolver.settingsManager.getIntSetting(PolySolver.settingKeySolutionsRetained);
   //System.out.println("maxnumsol="+numRetained);
   solutions = new ForgetfulList<Solution>(0);
}

public String getDescription(){ return description; }
public void setDescription(String d){
   String newtext = d == null ? "" : d;
   if( !newtext.equals(description)){
      description = newtext;
      changeFlags.setChangeWithoutInvalidating();
   }
}
public String getNotes(){ return notes; }
public void setNotes(String d){
   String newtext = d == null ? "" : d;
   if( !newtext.equals(notes)){
      notes = newtext;
      changeFlags.setChangeWithoutInvalidating();
   }
}

public boolean hasUnsavedChanges(){
   return !changeFlags.isSaved();
}
public void setNoUnsavedChanges(){
   changeFlags.setSaved();
} 

//all validation stuff
public boolean isValid(){
   if( changeFlags.isValid() ) return true;

   //reset data
   solutions.clear();
   cellsUsedInSolution = 0;
   piecesUsedInSolution = 0;
   startPosition = null;

   if( board.isEmpty() ) return false;
   if(polyominoList.size()==0) return false;
   int id=1;
   for(Polyomino poly : polyominoList){
      poly.setId(id);
      poly.initialiseOrients(gridType);
      id+=poly.getMaxAmount();
   }
   changeFlags.setValid();
   return true;
}

//all get/set stuff
public void setGridType(IGridType gt){
   if( gridType.getClass() != gt.getClass()){
      gridType = gt;
      changeFlags.setChange();
      description = "";
   }
}
public IGridType getGridType(){ return gridType; }
public int getNumPolyomino(){return polyominoList.size(); }
public int getNumPieces(){
   int s=0;
   for(Polyomino poly : polyominoList){
      s+=poly.getMaxAmount();
   }
   return s;
}
public Polyomino getPolyomino(int i){return polyominoList.get(i); }
public Color[] getColors(){
   Color[] colors = new Color[getNumPieces()];
   int s=0;
   for(Polyomino poly : polyominoList){
      int m=poly.getMaxAmount();
      Color c = poly.getColor();
      for(int i=0; i<m; i++){
         colors[s++] = c;
      }
   }
   return colors;
}
public int getNumSolutions(){return solutions.size(); }
public int getNumSkippedSolutions(){return solutions.NumForgotten(); }
public void setStartPosition(Solution s){
   if( startPosition != s){
      startPosition = s;
      solutions.clear();
      cellsUsedInSolution = 0;
      piecesUsedInSolution = 0;
      changeFlags.setChangeWithoutInvalidating();
   }
}
public Solution getSolution(int i){
   return i==0 ? new Solution(this, getNumSolutions()) : solutions.get(i-1);
}
public Board getBoard(){return board;}
public void setActionListener(ActionListener al){listener = al; }

public void addPolyomino( Polyomino poly ){
   poly.setChangeFlag(changeFlags);
   polyominoList.add(poly);
   changeFlags.setChange();
}
public void removePolyomino( Polyomino poly ){
   polyominoList.remove(poly);
   poly.setChangeFlag(null); // for safety only
   changeFlags.setChange();
}
public String getShapesNumBlocks(){
   return getShapesNumBlocks(polyominoList);
}
public String getShapesNumBlocks( List<Polyomino> plist){
   int[] min = getMinNumShapeTiles( plist );
   int[] max = getMaxNumShapeTiles( plist );
   return getNumTileString(min, max);
}
public int[] getMinNumShapeTiles( List<Polyomino> plist){
   int numTileOrbits = gridType.getNumTileOrbits();
   int[] tileOrbits = gridType.getTileOrbits();
   int[] min = new int[numTileOrbits];
   for(Polyomino poly : plist){
      poly.getMinNumTiles(min,tileOrbits);
   }
   return min;
}
public int[] getMaxNumShapeTiles( List<Polyomino> plist){
   int numTileOrbits = gridType.getNumTileOrbits();
   int[] tileOrbits = gridType.getTileOrbits();
   int[] max = new int[numTileOrbits];
   for(Polyomino poly : plist){
      poly.getMaxNumTiles(max,tileOrbits);
   }
   return max;
}
public static String getNumTileString(int[] min, int[] max)
{
   String s = "";
   for( int i=0; i<min.length; i++ ){
      if(i!=0) s+="+";
      if( min[i]==max[i] ){
         s += max[i];
      }else{
         if( min.length==1 ) s += min[i] + ".." + max[i];
         else  s += "(" + min[i] + ".." + max[i] + ")";
      }
   }
   return s;
}

public void clearBoard(){
   board.clearPieces();
   for(Polyomino poly : polyominoList){
      poly.reset();
   }
}
public boolean addPlacement(int po, CoordOri c){
   // first find piece
   int i=0;
   Polyomino poly=null;
   for(Polyomino poly2 : polyominoList){
      if( po>=poly2.getId() && po<poly2.getId()+poly2.getMaxAmount() ){
         poly=poly2; break;
      }
   }
   if( i>=polyominoList.size() ) return true;
   if( poly==null || !poly.canPlace(board,c) ) return true;

   poly.placeS(c);
   poly.place(board,c);
   return false;
}
public void removeDuplicates(boolean combine){
   // get all orientations
   for(Polyomino poly : polyominoList){
      poly.initialiseOrients(gridType);
   }
   // weed out list
   for( int i=0; i<getNumPolyomino(); i++){
      Polyomino poly1 = getPolyomino(i);
      for( int j=i+1; j<getNumPolyomino(); j++){
         Polyomino poly2 = getPolyomino(j);
         if( poly1.sameShape(poly2)){
            int n=poly1.getMaxAmount();
            if( combine ) n+=poly2.getMaxAmount();
            else if( n<poly2.getMaxAmount() ) n=poly2.getMaxAmount();
            poly1.setMaxAmount(n);
            polyominoList.remove(j);
            j--;
         }
      }
   }
}

public Polyomino createPolyomino(){
   Polyomino poly = new Polyomino(changeFlags);
   addPolyomino(poly);
   return poly;
}
//all text io stuff

public static Puzzle parse(Reader r) throws IOException{
   BufferedReader br = new BufferedReader(r); 
   Puzzle p=new Puzzle();
   String d = br.readLine();
   d = d.replace('\n', '\t');

   Parser parser = new Parser(br);
   String s;

   // get gridType
   s=parser.readString(true);
   if( s==null ) return p;
   IGridType g = GridTypeFactory.factory(s);
   if(g==null){
      if( !s.equalsIgnoreCase("board") && ! s.equalsIgnoreCase("tile"))
         throw new IOException("'"+s+"' is not a recognised grid type.");
      parser.pushback();
      g = GridTypeFactory.factory("square");
   }
   p.setGridType(g);
   p.description = d;

   // get tiles
   while(true){
      s=parser.readString(true);
      if(s==null) break;
      // get board
      else if(s.equalsIgnoreCase("board"))
         p.board = Board.parse(parser, p.changeFlags);
      // get tile(s)
      else if(s.equalsIgnoreCase("tile"))
         p.addPolyomino(Polyomino.parse(parser, p.changeFlags));
      else { break; }
   }
   
   if(s!=null && s.equalsIgnoreCase("place")){
      Solution sol = Solution.parse(parser, p);
      if( p.isValid() ){
         p.setStartPosition(sol);
      }
      s=parser.readString(true);
   }
   
   if(s!=null && s.equalsIgnoreCase("mode")){
      s=parser.readString(false);
      int i=0;
      while( i<5 && !s.equalsIgnoreCase(SolveMode.values()[i].toString().toLowerCase())) i++;
      if( i>=5 ) throw new IOException("Unrecognized solve mode '"+s+"'.");
      p.setSolveMode(SolveMode.values()[i]);
      s=parser.readString(true);
   }
   //TODO: fix parse error when there was no solution.
   while(s!=null && s.equalsIgnoreCase("solution")){
      Solution sol = Solution.parse(parser, p);
      if( p.isValid() ){
         p.solutions.add(sol);
      }
      s=parser.readString(true);
   }

   if(s!=null && s.equalsIgnoreCase("notes")){
      p.setNotes( Parser.decode(parser.readString()));
      s=parser.readString(true);
   }

   p.changeFlags.setSaved();
   //p.changeFlags.setValid();

   if( s!=null ){
      throw new IOException("Unrecognized command '"+s+"'.");
   }
   
   return p;
}

public String textRepr(boolean allowLarge){
   boolean is3D = gridType.is3D();
   boolean hasTile = gridType.getNumTileTypes()>1;
   StringBuilder sb = new StringBuilder();
   
   sb.append( description.replace('\n', '\t') ).append( '\n' );
   sb.append( gridType ).append( '\n' );
   sb.append( board.textRepr(is3D,hasTile) );
   if(!polyominoList.isEmpty()){
      sb.append( "# " ).append( polyominoList.size() ).append( " pieces\n" );
      for(Polyomino p : polyominoList)
         sb.append( p.textRepr(is3D,hasTile) );
   }

   if( startPosition!=null && !board.isEmpty() && !polyominoList.isEmpty()){
      String t = startPosition.textRepr(this);
      if( !t.isEmpty() ) sb.append( "place " ).append( t );
   }
   
   if( solveMode!=SolveMode.Unknown){
      sb.append("mode ").append(solveMode.toString()).append("\n");
   }

   if(  !solutions.isEmpty() && !board.isEmpty() && !polyominoList.isEmpty()){
      sb.append( "# " ).append( solutions.size() ).append( " solutions\n" );
      if( allowLarge || solutions.size()<2000 ){
         for( Solution sol : solutions ){
            sb.append( "solution " ).append( sol.textRepr(this) );
         }
      }
   }
   if( !notes.isEmpty() ){
      sb.append("notes ").append(Parser.encode(notes)).append("\n");
   }
   return sb.toString();
}

//all solver stuff

// initialise just before solving / playing
public boolean prepareSolve(){
   // make sure everything is ok
   if( ! isValid() ) return true;

   clearBoard();
   // set initial position
   if( startPosition!=null ){
      for( int i=0; i<startPosition.getNumPoly(); i++){
         Polyomino poly = startPosition.getPoly(i);
         CoordOri[] places = startPosition.getPlacement(i);
         for( int j=0; j<places.length; j++){
            if( places[j]!=null ){
               poly.place(board,places[j]);
               poly.placeS(places[j]);
            }
         }
      }
   }

   // Count number of cells that can be used by remaining pieces
   maxSpacers  = getMinNumShapeTiles( polyominoList );
   minSpacers  = getMaxNumShapeTiles( polyominoList );
   int[] bc = board.getNumEmptyBlocks(gridType.getNumTileOrbits(), gridType.getTileOrbits() );
   boolean cannotUseAllPieces = false;
   boolean mustHaveSpacers = false;
   for( int i=0; i<bc.length; i++ ){
      maxSpacers[i] = bc[i]-maxSpacers[i];
      minSpacers[i] = bc[i]-minSpacers[i];
      // Check if definitely cannot use all pieces
      if(maxSpacers[i] < 0) cannotUseAllPieces = true;
      if(minSpacers[i] > 0) mustHaveSpacers = true;
   }

   // set available solvemodes
   for( int i=0; i<solveModeAvailable.length; i++) solveModeAvailable[i] = true;
   if( cannotUseAllPieces ) solveModeAvailable[SolveMode.UseAll.ordinal()] = false;
   if( mustHaveSpacers ) solveModeAvailable[SolveMode.FillAll.ordinal()] = false;
   
   if( !isSolveModeAvailable(solveMode) ){
      if(!mustHaveSpacers) solveMode=SolveMode.FillAll;
      else if(!cannotUseAllPieces) solveMode=SolveMode.UseAll;
      else solveMode=SolveMode.FillMost;
   }

   return false;
}

public boolean isSolveModeAvailable(SolveMode mode){
   return mode!=SolveMode.Unknown && solveModeAvailable[mode.ordinal()];
}
public SolveMode getSolveMode(){
   return solveMode;
}
public void setSolveMode(SolveMode mode){
   if( mode!=solveMode && isSolveModeAvailable(mode)){
      solveMode = mode;
      changeFlags.setChangeWithoutInvalidating();
   }
}

// count number of nodes visited during search
private long nodeCount;

public long getNodeCount(){ return nodeCount; }

private void solveDLX(){
   solutions.clear();
   cellsUsedInSolution = 0;
   piecesUsedInSolution = 0;
   nodeCount = 0;

   // set up matrix for current search
   //make headers
   int numRows=0;

   MatrixColumnHeader rootCol, mh;
   MatrixRowHeader rootRow;
   rootCol = new MatrixColumnHeader();
   rootRow = new MatrixRowHeader(null,null);

   int t=0; // counts number of rows+columns

   // empty cell column headers
   for( Map.Entry<ICoord,Integer> k : board ){
      if( (k.getValue()).intValue()==0 ){
         mh = new MatrixColumnHeader(k.getKey());
         rootCol.insert(mh);
         t++;
      }
   }

   // build up matrix rows, also adding polyomino columns
   // get range of translations available
   Coord start=new Coord();
   Coord end=new Coord();
   board.getBlockRange(start,end);
   // for each available polyomino
   for(Polyomino poly : polyominoList){
      if(poly.getAvailable()!=0){
         // create column header
         mh = new MatrixColumnHeader(poly, solveMode==SolveMode.UseAll);
         rootCol.insert(mh);
         t++;

         // for each available translation
         CoordOri c=new CoordOri(start,0);
         do{

            // for each orientation
            for( int l = 0; l<poly.getNumOrient(); l++){
               c.ori = l;
               // test if poly can be placed
               if( poly.canPlace(board,c)){
                  // build row of matrix
                  MatrixRowHeader mrh = new MatrixRowHeader(poly,c);
                  rootRow.insert(mrh);
                  t++;numRows++;
                  // add tile entry
                  new MatrixCell(mh,mrh);
                  // add location entries
                  for( int j=0; j<poly.getNumBlocks(l); j++){
                     new MatrixCell(rootCol.find(poly.getCoord(j,c)),mrh);
                  }
                  mrh.numCells = poly.getNumBlocks(l);
                  mrh.numPieces = 1;
               }
            }
         }while(c.coord.getNextInRange(start,end));
      }
      if( wantToStop ) break;
   }

   // add spacer cells if needed
   int[] bc = board.getNumEmptyBlocks(gridType.getNumTileOrbits(), gridType.getTileOrbits() );
   for( int i=0; i<maxSpacers.length; i++ ){
      if( solveMode == SolveMode.FillMost || solveMode == SolveMode.UseMost || minSpacers[i]>0 ){
         // add spacer column header
         mh = solveMode == SolveMode.FillMost || solveMode == SolveMode.UseMost ?
               new MatrixColumnHeader( 0, bc[i]) :
               new MatrixColumnHeader( minSpacers[i], maxSpacers[i]);
         rootCol.insert(mh);
         t++;
         // build up matrix rows for each available coordinate
         for( Map.Entry<ICoord,Integer> k : board ){
            if( k.getValue().intValue() == 0 ){
               ICoord c = k.getKey();
               if( gridType.getTileOrbits()[c.tile()] == i ){
                  // build row of matrix
                  MatrixRowHeader mrh = new MatrixRowHeader(null,new CoordOri(c,0));
                  rootRow.insert(mrh);
                  t++; numRows++;
                  // add spacer tile entry
                  new MatrixCell(mh,mrh);
                  // add location entries
                  new MatrixCell(rootCol.find(c),mrh);
                  // don't count spacers
                  mrh.numCells = 0;
                  mrh.numPieces = 0;
               }
            }
         }
      }
   }
   
   stack = new Stack(t);

   // Reduction pass:
   //    Remove any rows which are trivially not usable, e.g. those
   //    which enclose an unfillable area in a corner, etc.
   //if( PolySolver.settingsManager.getBoolSetting(PolySolver.settingKeyEnableReductionPass) )
 	  if( true ){
      int badrows = 0;
      MatrixRowHeader row = rootRow.down;
      while(row!=rootRow && !wantToStop){
         // do this move
         chooseRow(row);
         // check if any columns are now unsatisfiable
         boolean isOk = true;
         MatrixColumnHeader col = rootCol.right;
         while(col != rootCol){
            if( !col.canBeSatisfied() ){
               isOk = false;
               break;
            }
            col = col.right;
         }
         // undo this move
         unchooseRow(row);
         // remove row if was bad
         if( !isOk ){
            row.unlinkRow();
            badrows++;
         }
         row = row.down;
      }

      //System.out.println("num rows="+numRows+"  discarded="+badrows);
   }

   // finally do actual solve
   if( !wantToStop ) solveDLX( rootCol,0,0,0 );

   // clean up
   stack = null;

   // break most links to make it easier for gc
   MatrixColumnHeader tc;
   mh = rootCol;
   do{
      MatrixCell f,r,tr;
      f = r = mh.first;
      if( f!=null ){
         do{
            tr=r.down;
            r.left = r.right = r.up = r.down = null;
            r.header = null;
            r=tr;
         }while( r!=f );
      }
      tc=mh.right;
      mh.left = mh.right = null;
      mh.first = null;
      mh=tc;
   }while( mh !=rootCol );
}


Stack stack;

private void chooseRow (MatrixRowHeader mrh){
   // mark start of move
   stack.push((MatrixColumnHeader)null);

   // add row r to solution subset
   mrh.place(board);

   //unlink row r
   mrh.unlinkRow();
   stack.push(mrh);

   //for any column d with entry in row r (including c itself)
   MatrixCell dcol=mrh.first.right;
   while(dcol != mrh.first ){
      MatrixColumnHeader mch = dcol.header;
      // Adjust value of d
      mch.incValue();
      // if condition d is fully satisfied, unlink column d
      // note that row is not affected since it is already unlinked
      if( mch.isFullySatisfied() ){
         mch.unlinkColumn();
         stack.push(mch);
      }
      // update any conflicts in this column
      updateCol(mch);
      
      dcol=dcol.right;
   }
}

// update any conflicts in this column 
private void updateCol (MatrixColumnHeader mch){
   // for any active row r with entry in column d
   MatrixCell r = mch.first.down;
   while( r!=mch.first ){
      // if r conflicts with condition d
      if( !r.isAllowed() ){
         // unlink row r
         r.rowHeader.unlinkRow();
         stack.push(r.rowHeader);
      }
      r=r.down;
   }
}
private void undoChanges(){
   //relink all discarded rows/columns
   while(true){
      if(stack.lastIsRow()){
         MatrixRowHeader mrh = stack.popRow();
         if( mrh==null ) break;
         mrh.linkRow();
      }else{
         MatrixColumnHeader mch = stack.popCol();
         if( mch==null ) break;
         mch.linkColumn();
      }
   }
}
private void unchooseRow(MatrixRowHeader mrh){
   //relink all discarded rows/columns
   undoChanges();

   //restore  changed condition values
   //for any column d with entry in row r (including c itself)
   MatrixCell dcol=mrh.first.right;
   while(dcol != mrh.first ){
      // Adjust value of d
      dcol.header.decValue();
      dcol=dcol.right;
   }

   //remove row r from solution subset
   // undo move from board
   mrh.remove(board);
}

private void solveDLX (MatrixColumnHeader root, int depth, int cellsUsed, int piecesUsed){
   if( wantToStop ) return;

   nodeCount++;
   // Choose unsatisfied column c with fewest entries left.
   MatrixColumnHeader col = root, best=col;
   int mn = -1;
   while(true){
      col=col.right;
      if( col==root ) break;
      if( col.isSatisfied()) continue;
      //if an column cannot be satisfied, then reached dead end
      if( !col.canBeSatisfied()) return;
      //if( t==0 && col.isPoly() && !solutionExact ) continue;
      int t = col.getNumberCells();
      if( t<=mn || mn<0 ){
         mn=t; best=col;
      }
   }
   col=best;

   // no unsatisfied columns, so have a solution
   if(mn<0){
      foundSolution(cellsUsed, piecesUsed);
      return;
   }

   //for each row r in column c that has entry, do
   MatrixCell row = col.first.down;
   int rowCount = 0;
   while(row!=col.first){

      // add row r to solution subset
      MatrixRowHeader mrh = row.rowHeader;
      
      chooseRow(mrh);

      //call recursively
      solveDLX(root, depth+1, cellsUsed+mrh.numCells, piecesUsed+mrh.numPieces);

       // undo the move
      unchooseRow(mrh);
      // remove row anyway since we have already tried it.
      mrh.unlinkRow();
      stack.push(mrh);
      rowCount++;

      row=row.down;
   }
   // reinsert removed rows
   while( rowCount >0){
      rowCount--;
      MatrixRowHeader mrh = stack.popRow();
      mrh.linkRow();
   }
   
}

private void foundSolution(int cellsUsed, int piecesUsed){
   if( ( solveMode == SolveMode.FillMost && cellsUsedInSolution>cellsUsed) ||
         ( solveMode == SolveMode.UseMost && piecesUsedInSolution>piecesUsed)){
      return;
   }

   if( ( solveMode == SolveMode.FillMost && cellsUsedInSolution<cellsUsed) ||
         ( solveMode == SolveMode.UseMost && piecesUsedInSolution<piecesUsed)){
      solutions.clear();
      cellsUsedInSolution = cellsUsed;
      piecesUsedInSolution = piecesUsed;
   }
   Solution sol = new Solution(this, getNumSolutions());
   solutions.add(sol);
   if(solveMode == SolveMode.UseMostEarlyBreak) {
 	  this.stopSolve();
   }
   if( listener!=null ){
      listener.actionPerformed(new ActionEvent(this,0,""));
   }
}


//all stuff to do with running solver as a thread

boolean running = false;
boolean wantToStop = false;
public void run(){
   solutions.clear();
   cellsUsedInSolution = 0;
   piecesUsedInSolution = 0;
   running=true;
   wantToStop=false;
   if( !prepareSolve() ){
      listener.actionPerformed(new ActionEvent(this,2,""));
      solveDLX();
   }
   // end of solve
   running=false;
   listener.actionPerformed(new ActionEvent(this,1,""));
}
public boolean isRunning(){ return running; }
public void stopSolve(){
   wantToStop=true;
   while( isRunning() ){
      try{
         Thread.sleep(100);
         running = false;
      }catch(Exception e){}
   }
   // end of solve
   running = false;
   listener.actionPerformed(new ActionEvent(this,1,""));
}
}
