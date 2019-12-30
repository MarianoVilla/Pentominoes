package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeBrick
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,2,3,4,5} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,0,0,0), new Coord(0,1,0,0), new Coord(1,1,0,0),
        new Coord(1,0,0,0), new Coord(0,-1,0,0), new Coord(-1,-1,0,0) },
   };
   private static int[] tileOrbitInit = {0};
   final static double ratio = 1.0;
   private int blockWidth;

   public GridTypeBrick(){
      super("Brick","Brick",2,true, false, 6, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontileheight=26;
   final static int icontilewidth=(int)(icontileheight*ratio);
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      boolean even = true;
      for(int y=cy-icontileheight/2; y<cy+h; y+=icontileheight){
         g.drawLine(cx,y,cx+w,y);
         for(int x=cx + (even?-icontilewidth:icontilewidth)/4; x<cx+w; x+=icontilewidth){
            g.drawLine(x,y,x,y+icontileheight);
         }
         even = !even;
      }
   }

   protected void recalcVertices(){
      blockWidth = (int)(blockSize*ratio/2);
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = 0;
      vertexy[1] = blockSize;
      vertexx[2] = blockWidth;
      vertexy[2] = blockSize;
      vertexx[3] = blockWidth*2;
      vertexy[3] = blockSize;
      vertexx[4] = blockWidth*2;
      vertexy[4] = 0;
      vertexx[5] = blockWidth;
      vertexy[5] = 0;
   }

   protected void rotate(Coord c){
      c.set(-c.x(), -c.y(), c.tile());
   }
   protected void reflect(Coord c){
      c.set(-c.x()+c.y(),c.y(),c.tile());
   }

   protected int getScreenXcoord(ICoord c){
      return offsetX + blockWidth*(2*(c.x() - firstShown.x()) -(c.y() - lastShown.y()));
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY + blockSize*(c.y() - firstShown.y());
   }
   protected int getScreenXoffset(ICoord c){
      return (2*c.x()-c.y())*blockWidth;
   }
   protected int getScreenYoffset(ICoord c){
      return c.y()*blockSize;
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return ratio/2*(2*(last.x() - first.x() + 1) + (last.y() - first.y()));
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return (last.y() - first.y() + 1);
   }
}
