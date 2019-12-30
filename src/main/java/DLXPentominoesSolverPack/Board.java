package DLXPentominoesSolverPack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Board
implements Iterable<Map.Entry<ICoord,Integer>>
{
//contents of board; 0=empty, -1=hole, >0=tilenumber
private Map<ICoord, Integer> blockList = new HashMap<ICoord, Integer>();
private IChangeFlag changeFlag;

public Board(IChangeFlag cf){ changeFlag = cf; }

//all get/set stuff
public Iterator<Map.Entry<ICoord,Integer>> iterator(){ return blockList.entrySet().iterator(); }
public int getNumBlocks(){return blockList.size();}
public boolean isEmpty(){return blockList.isEmpty();}
public int[] getNumBlocksArray(int numOrb, int[] tileOrb){
   int[] count = new int[numOrb];
   for( ICoord c: blockList.keySet()){
      count[tileOrb[c.tile()]]++;
   }
   return count;
}
public String getNumBlocksString(int numOrb, int[] tileOrb){
   int[] count = getNumBlocksArray(numOrb, tileOrb);
   String s = "";
   for( int i=0; i<numOrb; i++ ){
      if( i!=0 ) s+= "+";
      s += count[i];
   }
   return s;
}

// count empty spaces
public int getNumEmptyBlocks(){
   int c=0;
   for( Map.Entry<ICoord,Integer> k : blockList.entrySet() ){
      if( k.getValue().intValue() ==0 ) c++;
   }
   return c;
}
// count empty spaces of each type of tile
public int[] getNumEmptyBlocks(int numTileOrbit, int tileOrbit[]){
   int[] count = new int[numTileOrbit];
   for( Map.Entry<ICoord,Integer> k : blockList.entrySet() ){
      if( k.getValue().intValue() ==0 )
         count[tileOrbit[k.getKey().tile()]]++;
   }
   return count;
}
public int getContents( ICoord c){
   Object d = blockList.get(c);
   if( d==null ) return -1;
   return ((Integer)d).intValue();
}
public void setContentsUnsafe( ICoord c,int v){
   blockList.put(new Coord(c),v);
}

public void toggleBlock( ICoord c ){
   if( blockList.containsKey(c) ){
      blockList.remove(c);
   }else{
      blockList.put(new Coord(c),0);
   }
   if(changeFlag!=null) changeFlag.setChange();
}

public void addBlock( ICoord c ){
   if( !blockList.containsKey(c) ){
      blockList.put(new Coord(c),0);
   }
   if(changeFlag!=null) changeFlag.setChange();
}

// remove all pieces
public void clearPieces(){
   for( Map.Entry<ICoord,Integer> k : blockList.entrySet() ){
      k.setValue(Integer.valueOf(0));
   }
}
// destroy board, so it is of zero size.
public void destroyBoard(){
   blockList.clear();
   if(changeFlag!=null) changeFlag.setChange();
}

public ICoord[] getBlockArray(){
   return blockList.keySet().toArray(new ICoord[0]);
}
public Map<ICoord, Integer> getBlockList() {
	   return blockList;
}
public void setBoardContents(Solution sol){
   clearPieces();
   for( int i=0; i<sol.getNumPoly(); i++){
      Polyomino poly = sol.getPoly(i);
      CoordOri[] places = sol.getPlacement(i);
      for( int j=0; j<places.length; j++){
         if( places[j]!=null ){
            poly.place(this,places[j],j);
         }
      }
   }
}
//set a list of coordinates into the board
public void setBoardBlocks(ICoord[] b,int colour){
   blockList.clear();
   if(changeFlag!=null) changeFlag.setChange();

   // write out contents
   for(int j=0; j<b.length;j++)
      blockList.put(b[j], colour);
}
public void getTileRange(Coord s,Coord e){
   s.set();
   e.set();
   boolean first = true;
   for( ICoord c : blockList.keySet() ){
      if( first ){
         s.set(c);e.set(c);
         first=false;
      }else{
         s.min(c);
         e.max(c);
      }
   }
}
public void getBlockRange(Coord s,Coord e){
   getTileRange(s,e);
   s.setTile(0);
   e.setTile(0);
}

//all text io stuff
public static Board parse(Parser parser, IChangeFlag cf) throws IOException{
   parser.skipOpenBracket();
   Coord mx = Coord.parse(parser);
   Coord mn = new Coord();

   // create empty board of that size
   Board brd=new Board(cf);
   Coord t = Coord.getFirstInRange(mn, mn);
   do{
      brd.toggleBlock(t);
   }while( t.getNextInRange(mn,mx,mx.tile()+1));

   // parse the list of voids
   String s=parser.readString();
   while(!s.equals(")")){
      parser.pushback();
      t = Coord.parse(parser);
      brd.toggleBlock(t);
      s=parser.readString();
   }
   return brd;
}

public String textRepr(boolean is3D, boolean hasTile){
   if( isEmpty() ) return "";
   StringBuilder s=new StringBuilder();
   Coord mn = new Coord();
   Coord mx = new Coord();
   getTileRange(mn,mx);
   Coord t = new Coord(mx);
   t.sub(mn);
   s.append("Board ( ").append(t.toString(is3D,hasTile));
   Coord c = Coord.getFirstInRange(mn, mx);
   do{
      if( getContents(c)==-1 ){
         t.set(c); t.sub(mn);
         s.append(t.toString(is3D,hasTile));
      }
   }while( c.getNextInRange(mn, mx, mx.tile()+1));
   s.append(")\n");
   return s.toString();
}
}
