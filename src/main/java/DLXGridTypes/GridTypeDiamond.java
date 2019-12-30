package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeDiamond
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,2,3} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,0,0,0), new Coord(0,1,0,0), new Coord(1,0,0,0), new Coord(0,-1,0,0) }
   };
   private static int[] tileOrbitInit = {0};
   private int blockHeight;
   static private double factor = Math.sqrt(3);

   public GridTypeDiamond(){
      super("Diamond",2,true, false, 4, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=15;
   final static int icontileheight=26;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int y=cy-icontileheight/2; y<cy+h; y+=icontileheight*2){
         g.drawLine(cx,y,cx+w,y);
         g.drawLine(cx,y+icontileheight,cx+w,y+icontileheight);
         for(int x=cx; x<cx+w; x+=icontilesize*2){
            g.drawLine(x+icontilesize*2,y,x,y+icontileheight*2);
         }
      }
   }

   protected void recalcVertices(){
      blockHeight = (int)(0.5 + blockSize*factor);
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = -blockSize;
      vertexy[1] = blockHeight;
      vertexx[2] = blockSize;
      vertexy[2] = blockHeight;
      vertexx[3] = blockSize*2;
      vertexy[3] = 0;
   }

   protected void rotate(Coord c){
      c.set(-c.x(),-c.y(),c.tile());
   }
   protected void reflect(Coord c){
      c.set(c.y(), c.x(), c.tile());
   }

   protected int getScreenXcoord(ICoord c){
      return offsetX+2*blockSize*(c.x()-firstShown.x())-blockSize*(c.y()-firstShown.y()) + blockSize*(lastShown.y()-firstShown.y()+1);
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY+blockHeight*(c.y()-firstShown.y());
   }
   protected int getScreenXoffset(ICoord c){
      return 2*blockSize*c.x() - blockSize*c.y();
   }
   protected int getScreenYoffset(ICoord c){
      return blockHeight*c.y();
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return 2*(last.x() - first.x() + 1) + (last.y() - first.y() + 1);
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return (last.y() - first.y() + 1)*factor;
   }
}
