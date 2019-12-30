package DLXPentominoesSolverPack;

import java.io.IOException;

public class Solution implements Comparable<Solution>
{
   private Polyomino[] polyominoes;
   private CoordOri[][] placements;
   private int index;   // number in which it was found, 0=first, 1=second, etc.

   public Solution(Puzzle puzzle, int ix){
      // get number of polyominoes
      int p=puzzle.getNumPolyomino();
      polyominoes = new Polyomino[p];
      placements = new CoordOri[p][];
      index = ix;

      // for each polyomino
      for( int i=0; i<p; i++){
         polyominoes[i] = puzzle.getPolyomino(i);
         placements[i] = polyominoes[i].getPlacements();
      }
   }

   public int getNumPoly(){ return polyominoes.length; }
   public Polyomino getPoly(int i){ return polyominoes[i]; }
   public CoordOri[] getPlacement(int i){ return placements[i]; }
   public int getIndex(){ return index; }
   
   public String textRepr(Puzzle puz){
      Coord mn = new Coord();
      Coord t = new Coord();
      CoordOri co = new CoordOri();
      puz.getBoard().getBlockRange(mn,t);
      boolean is3D = puz.getGridType().is3D();
      int ln=0;
      StringBuilder s=new StringBuilder("(");
      for(int i=0; i<polyominoes.length; i++){
         Polyomino poly = puz.getPolyomino(i);
         for(int j=0; j<placements[i].length;j++){
            if( placements[i][j]!=null ){
               if(ln>0) s.append(", ");
               s.append(poly.getId()+j).append(" ");
               co.set(placements[i][j]);
               co.coord.sub(mn);
               s.append(co.toString(is3D,false));
               ln++;
            }
         }
      }
      s.append(")\n");
      return ln==0 ? "" : s.toString();
   }
   public static Solution parse(Parser parser, Puzzle puz) throws IOException{
      if(! puz.isValid())
         throw new IOException("Cannot have solutions to an incompletely specified puzzle.");
      puz.clearBoard();

      // parse Coord
      parser.skipOpenBracket();
      while(true){
         int id = parser.readNumber();
         CoordOri c=CoordOri.parse(parser);
         if( puz.addPlacement(id,c) )
            throw new IOException("Cannot place piece "+id+".");
         String s=parser.readString();
         if( s.equals(")") ) break;
         parser.pushback();
      }
      return puz.getSolution(0);
   }

   @Override
   public int compareTo(Solution sol2) {
      int c = placements.length - sol2.placements.length;
      if( c!=0 ) return c;
      for(int i=0; i<placements.length; i++) {
         c = placements[i].length - sol2.placements[i].length;
         if( c!=0 ) return c;
         for(int j = 0; j<placements[i].length; j++) {
            c = placements[i][j].compareTo(sol2.placements[i][j]);
            if( c!=0 ) return c;
         }
      }
      return 0;
   }
}
