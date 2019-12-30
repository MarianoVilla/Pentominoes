package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeKite
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,2,3}, {0,3,4,5}
   };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,0,0,1), new Coord(-1,1,0,1), new Coord(0,1,0,1), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,0,0,-1), new Coord(1,-1,0,-1), new Coord(0,-1,0,-1) },
   };
   private static int[] tileOrbitInit = {0,0};
   private static double factor=Math.sqrt(3);

   public GridTypeKite(){
      super("Kite",2,true, false, 6, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      int sz = 40;
      int dh = (int)(0.5+sz*factor/2);
      for(int y=cy-sz/2; y<cy+h; y+=sz){
         for(int x=cx; x<cx+w+sz/2; x+=sz){
            g.drawLine(x,y,x+sz/2,y+dh);
            g.drawLine(x,y,x-sz/2,y+dh);
            g.drawLine(x+sz/2,y+dh,x,y+sz);
            g.drawLine(x-sz/2,y+dh,x,y+sz);
         }
      }
   }

   protected void recalcVertices(){
      int height2 = (int)(0.5 + blockSize*factor);
      vertexx[0] = blockSize;
      vertexy[0] = blockSize*2-height2;
      vertexx[1] = 0;
      vertexy[1] = blockSize*2;
      vertexx[2] = blockSize;
      vertexy[2] = blockSize*4-height2;
      vertexx[3] = blockSize*2;
      vertexy[3] = blockSize*2;
      vertexx[4] = blockSize*3;
      vertexy[4] = blockSize*2-height2;
      vertexx[5] = blockSize*2;
      vertexy[5] = 0;
   }

   protected void rotate(Coord c){
      c.set(-c.x(), -c.y(), 1-c.tile());
   }
   protected void reflect(Coord c){
      c.set(-c.x() -c.tile(), c.y(), c.tile());
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
      return 2*(last.x() - first.x() + 1) + 1;
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 2*(last.y() - first.y() + 1) + (2-factor);
   }
}
