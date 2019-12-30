package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeSquare
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,2,3} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,0,0,0), new Coord(0,1,0,0), new Coord(1,0,0,0), new Coord(0,-1,0,0) }
   };
   private static int[] tileOrbitInit = {0};

   public GridTypeSquare(){
      super("Square","Square (4.4.4.4)",4,true, false, 4, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=30;
   public void paintIcon(Graphics g, int cx,int cy, int w, int h){
      for(int x=icontilesize/2; x<w; x+=icontilesize){
         g.drawLine(cx+x,cy,cx+x,cy+h);
      }
      for(int y=icontilesize/2; y<h; y+=icontilesize){
         g.drawLine(cx,cy+y,cx+w,cy+y);
      }
   }

   protected void recalcVertices(){
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = 0;
      vertexy[1] = blockSize;
      vertexx[2] = blockSize;
      vertexy[2] = blockSize;
      vertexx[3] = blockSize;
      vertexy[3] = 0;
   }

   protected void rotate(Coord c){
      c.set(c.y(), -c.x(), c.tile());
   }
   protected void reflect(Coord c){
      c.set(-c.x(), c.y(), c.tile());
   }
   
   protected int getScreenXcoord(ICoord c){
      return offsetX+blockSize*(c.x()-firstShown.x());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY+blockSize*(c.y()-firstShown.y());
   }
   protected int getScreenXoffset(ICoord c){
      return blockSize*c.x();
   }
   protected int getScreenYoffset(ICoord c){
      return blockSize*c.y();
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return last.x() - first.x() + 1;
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return last.y() - first.y() + 1;
   }
}
