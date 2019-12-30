package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeUnequalSquares
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,2,3,4,5,6,7},{3,8,9,4} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,0,0,0), new Coord(-1,0,0,1),
        new Coord(0,1,0,0), new Coord(0,0,0,1),
        new Coord(1,0,0,0), new Coord(0,-1,0,1),
        new Coord(0,-1,0,0), new Coord(-1,-1,0,1),
      },{
         new Coord(0,1,0,-1),
         new Coord(1,1,0,-1),
         new Coord(1,0,0,-1),
         new Coord(0,0,0,-1),
      }
   };
   private static int[] tileOrbitInit = {0,1};

   public GridTypeUnequalSquares(){
      super("UnequalSquares","Unequal Squares",4,false, false, 10, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=10;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx-icontilesize; x<cx+w+icontilesize*2; x+=icontilesize*5){
         for(int y=cy-icontilesize*3; y<cy+h+icontilesize*2; y+=icontilesize*5){
            g.drawLine(x-2*icontilesize,y-icontilesize,x+icontilesize*4,y+icontilesize*2);
            g.drawLine(x+5*icontilesize,y,x+icontilesize*2,y+icontilesize*6);
         }
      }
   }

   protected void recalcVertices(){
      vertexx[0] = blockSize;
      vertexy[0] = blockSize*2;
      vertexx[1] = 0;
      vertexy[1] = blockSize*4;
      vertexx[2] = blockSize*2;
      vertexy[2] = blockSize*5;
      vertexx[3] = blockSize*4;
      vertexy[3] = blockSize*6;
      vertexx[4] = blockSize*5;
      vertexy[4] = blockSize*4;
      vertexx[5] = blockSize*6;
      vertexy[5] = blockSize*2;
      vertexx[6] = blockSize*4;
      vertexy[6] = blockSize*1;
      vertexx[7] = blockSize*2;
      vertexy[7] = 0;
      vertexx[8] = blockSize*6;
      vertexy[8] = blockSize*7;
      vertexx[9] = blockSize*7;
      vertexy[9] = blockSize*5;
   }

   protected void rotate(Coord c){
      int x = c.y();
      int y = -c.x();
      if( c.tile()==1 ) y--;
      c.set(x,y,c.tile());
   }
   protected void reflect(Coord c){
   }
   
   protected int getScreenXcoord(ICoord c){
      return offsetX+5*blockSize*(c.x()-firstShown.x());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY+5*blockSize*(c.y()-firstShown.y());
   }
   protected int getScreenXoffset(ICoord c){
      return 5*blockSize*c.x();
   }
   protected int getScreenYoffset(ICoord c){
      return 5*blockSize*c.y();
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return 5*(last.x() - first.x() + 1)+2;
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 5*(last.y() - first.y() + 1)+2;
   }
}
