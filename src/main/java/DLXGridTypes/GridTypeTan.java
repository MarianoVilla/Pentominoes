package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeTan
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,4},{1,2,4},{2,3,4},{3,0,4} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,0,0,2), new Coord(0,0,0,1),  new Coord(0,0,0,3) },
      { new Coord(0,1,0,2), new Coord(0,0,0,1),  new Coord(0,0,0,-1)  },
      { new Coord(1,0,0,-2), new Coord(0,0,0,1),  new Coord(0,0,0,-1)  },
      { new Coord(0,-1,0,-2), new Coord(0,0,0,-3),  new Coord(0,0,0,-1)  }
   };
   private static int[] tileOrbitInit = {0,0,0,0};

   public GridTypeTan(){
      super("Tan",4,true, false, 5, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=30;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx-icontilesize/2; x<cx+w+icontilesize; x+=icontilesize){
         for(int y=cy-icontilesize/2; y<cy+h+icontilesize; y+=icontilesize){
            g.drawLine(x+icontilesize,y+icontilesize,x,y);
            g.drawLine(x,y,x+icontilesize,y);
            g.drawLine(x+icontilesize,y,x,y+icontilesize);
            g.drawLine(x,y+icontilesize,x,y);
         }
      }
   }

   protected void recalcVertices(){
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = 0;
      vertexy[1] = blockSize*2;
      vertexx[2] = blockSize*2;
      vertexy[2] = blockSize*2;
      vertexx[3] = blockSize*2;
      vertexy[3] = 0;
      vertexx[4] = blockSize;
      vertexy[4] = blockSize;
   }

   protected void rotate(Coord c){
      c.set(c.y(), -c.x(), (c.tile() +1)%4);
   }
   protected void reflect(Coord c){
      int t = c.tile();
      if( t == 0 || t==2 ) t ^= 2;  
      c.set(-c.x(), c.y(), t);
   }
   
   protected int getScreenXcoord(ICoord c){
      return offsetX+2*blockSize*(c.x()-firstShown.x());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY+2*blockSize*(c.y()-firstShown.y());
   }
   protected int getScreenXoffset(ICoord c){
      return 2*blockSize*c.x();
   }
   protected int getScreenYoffset(ICoord c){
      return 2*blockSize*c.y();
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return 2*(last.x() - first.x() + 1);
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 2*(last.y() - first.y() + 1);
   }
}
