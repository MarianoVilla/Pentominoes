package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeSquareHexa
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,2,3}, {2,1,4,5,6,7}, {3,2,7,8,9,10} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(0,-1,0,2), new Coord(0,0,0,1),
        new Coord(0,0,0,2), new Coord(-1,0,0,1),
      },{
         new Coord(0,0,0,-1),new Coord(0,-1,0,1),
         new Coord(1,-1,0,1),new Coord(1,0,0,-1),
         new Coord(1,0,0,1),new Coord(0,0,0,1),
      },{
         new Coord(0,0,0,-2),new Coord(0,0,0,-1),
         new Coord(0,1,0,-1),new Coord(0,1,0,-2),
         new Coord(-1,1,0,-1),new Coord(-1,0,0,-1),
      }
   };
   private static int[] tileOrbitInit = {0,1,1};

   public GridTypeSquareHexa(){
      super("SquareHexa","Bevelled Squares",4,true, false, 11, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=30;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx-icontilesize/2; x<cx+w+icontilesize; x+=icontilesize*2){
         for(int y=cy-icontilesize/2; y<cy+h+icontilesize; y+=icontilesize*2){
            g.drawLine(x,y,x+icontilesize,y);
            g.drawLine(x+icontilesize,y,x+icontilesize,y+icontilesize);
            g.drawLine(x+icontilesize,y+icontilesize,x,y+icontilesize);
            g.drawLine(x,y+icontilesize,x,y);
            g.drawLine(x+icontilesize,y+icontilesize,x+icontilesize*2,y+icontilesize*2);
            g.drawLine(x+icontilesize*2,y+icontilesize,x+icontilesize,y+icontilesize*2);
         }
      }
   }

   protected void recalcVertices(){
      vertexx[0] = blockSize;
      vertexy[0] = blockSize;
      vertexx[1] = blockSize*3;
      vertexy[1] = blockSize;
      vertexx[2] = blockSize*3;
      vertexy[2] = blockSize*3;
      vertexx[3] = blockSize;
      vertexy[3] = blockSize*3;

      vertexx[4] = blockSize*4;
      vertexy[4] = 0;
      vertexx[5] = blockSize*5;
      vertexy[5] = blockSize;
      vertexx[6] = blockSize*5;
      vertexy[6] = blockSize*3;
      vertexx[7] = blockSize*4;
      vertexy[7] = blockSize*4;

      vertexx[8] = blockSize*3;
      vertexy[8] = blockSize*5;
      vertexx[9] = blockSize;
      vertexy[9] = blockSize*5;
      vertexx[10] = 0;
      vertexy[10] = blockSize*4;
   }

   protected void rotate(Coord c){
      int x = c.y();
      int y = -c.x();
      int t = c.tile();
      if( t==1 ) {
         y--;
         t=2;
      }else if( t==2 ) {
         t=1;
      }
      c.set(x,y,t);
   }
   protected void reflect(Coord c){
      int x = -c.x();
      if( c.tile() == 1 ) x--;  
      c.set(x,c.y(),c.tile());
   }
   
   protected int getScreenXcoord(ICoord c){
      return offsetX+4*blockSize*(c.x()-firstShown.x());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY+4*blockSize*(c.y()-firstShown.y());
   }
   protected int getScreenXoffset(ICoord c){
      return 4*blockSize*c.x();
   }
   protected int getScreenYoffset(ICoord c){
      return 4*blockSize*c.y();
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return 4*(last.x() - first.x() + 1)+1;
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 4*(last.y() - first.y() + 1)+1;
   }
}
