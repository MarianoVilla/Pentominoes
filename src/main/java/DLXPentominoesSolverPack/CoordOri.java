package DLXPentominoesSolverPack;

import java.io.IOException;


public class CoordOri implements Comparable<CoordOri>
{
   public Coord coord;
   public int ori;
   public CoordOri(){
      coord = new Coord();
      ori=0;
   }
   public CoordOri(CoordOri c){
      coord = new Coord(c.coord);
      ori=c.ori;
   }
   public CoordOri(ICoord c, int v){
      coord = new Coord(c);
      ori=v;
   }
   public void set(){ coord.set(); ori=0;}
   public void set(ICoord c, int v){
      coord.set(c);
      ori=v;
   }
   public void set(CoordOri c){
      coord.set(c.coord);
      ori = c.ori;
   }
   public String toString(){
      return toString( true, true );
   }
   public String toString(boolean is3D, boolean hasTile){
      String s = "(" + coord.x() + "," + coord.y();
      if( is3D || coord.z()!=0 ) s += "," + coord.z();
      if( hasTile || coord.tile()!=0 ) s += ";" + coord.tile();
      s += "|" + ori + ")";
      return s;
   }
   public static CoordOri parse(Parser parser) throws IOException{
      // parse Coord
      String s=parser.readString();
      if( !s.equals("(") )
         throw new IOException("Expected '(' at start of coordinate.");
      int x=parser.readNumber();
      int y=parser.readNumber();
      s=parser.readString();
      int z = 0;
      if( !s.equals(":") && !s.equals("|") && !s.equals(")") ){
         parser.pushback();
         z=parser.readNumber();
         s=parser.readString();
      }
      int tile = 0;
      if( !s.equals("|") && !s.equals(")") ){
         parser.pushback();
         tile=parser.readNumber();
         s=parser.readString();
      }
      int ori = 0;
      if( !s.equals(")") ){
         if( !s.equals("|") ) parser.pushback();
         ori=parser.readNumber();
         s=parser.readString();
      }
      if( !s.equals(")") )
         throw new IOException("Expected ')' at end of coordinate.");
      return new CoordOri(new Coord(x,y,z,tile),ori);
   }
   public boolean equals(Object p){
      if( p instanceof CoordOri ){
         CoordOri c = (CoordOri) p;
         return coord.equals(c.coord);
      }
      return false;
   }

   public int hashCode() { return coord.hashCode(); }
   @Override
   public int compareTo(CoordOri co2) {
      int c = coord.compareTo(co2.coord);
      if( c!=0) return c;
      c = ori - co2.ori;
      return c;
   }
}