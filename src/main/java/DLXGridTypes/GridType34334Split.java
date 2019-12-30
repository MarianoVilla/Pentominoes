package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridType34334Split
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,7}, {1,8,7}, {8,3,4}, {8,4,5},
      {1,2,9},{2,3,9},{8,1,9},{3,8,9},
      {6,7,10},{5,6,10},{7,8,10},{8,5,10},
   };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,0,0,9), new Coord(0,0,0,1), new Coord(0,-1,0,5) },
      { new Coord(0,0,0,5), new Coord(0,0,0,9), new Coord(0,0,0,-1) },
      { new Coord(0,0,0,5), new Coord(0,1,0,6), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,0,0,1), new Coord(0,0,0,8) },

      { new Coord(-1,0,0,-1), new Coord(0,0,0,1), new Coord(0,0,0,2) },
      { new Coord(0,1,0,-5), new Coord(0,0,0,2), new Coord(0,0,0,-1) },
      { new Coord(0,0,0,-5), new Coord(0,0,0,-2), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-5), new Coord(0,0,0,-1), new Coord(0,0,0,-2) },

      { new Coord(0,-1,0,-6), new Coord(0,0,0,2), new Coord(0,0,0,1) },
      { new Coord(1,0,0,-9), new Coord(0,0,0,-1), new Coord(0,0,0,2) },
      { new Coord(0,0,0,-9), new Coord(0,0,0,1), new Coord(0,0,0,-2) },
      { new Coord(0,0,0,-8), new Coord(0,0,0,-2), new Coord(0,0,0,-1) },
   };
   private static int[] tileOrbitInit = {0,0,0,0,1,1,1,1,1,1,1,1};


   public GridType34334Split(){
      super("34334Split", "Split Snub-Square", 4,true, false, 11, tilesInit, adjOffsetInit, tileOrbitInit );
   }

   final static double sin15 = Math.sin(Math.toRadians(15));

   final static int icontilesize=30;
   public void paintIcon(Graphics g, int cx,int cy, int w0, int h0){
      int h = (int)(icontilesize*sin15);
      for(int y=cy-icontilesize; y<cy+h0; y+=icontilesize*2){
         for(int x=cx-icontilesize; x<cx+w0; x+=icontilesize*2){
            g.drawLine(x,y,x+icontilesize,y+h);
            g.drawLine(x,y,x+h,y+icontilesize);
            g.drawLine(x+icontilesize,y+h,x+h, y+icontilesize);
            g.drawLine(x+icontilesize,y+h,x+icontilesize+h, y+icontilesize+h);
            g.drawLine(x+h,y+icontilesize,x+icontilesize+h, y+icontilesize+h);
            g.drawLine(x+icontilesize+h, y+icontilesize+h, x+icontilesize*2, y+icontilesize*2);
            g.drawLine(x+icontilesize,y+h, x+icontilesize*2,y);
            g.drawLine(x+h,y+icontilesize, x,y+icontilesize*2);
            g.drawLine(x+icontilesize+h, y+icontilesize+h, x+icontilesize, y+icontilesize*2+h);
            g.drawLine(x+icontilesize+h, y+icontilesize+h, x+icontilesize*2+h, y+icontilesize);

            g.drawLine(x+icontilesize+h, y+icontilesize+h, x, y+icontilesize*2);
            g.drawLine(x+icontilesize+h, y+icontilesize+h, x+icontilesize*2, y);
            g.drawLine(x+h,y+icontilesize, x+icontilesize,y+icontilesize*2+h);
            g.drawLine(x+icontilesize,y+h, x+icontilesize*2+h,y+icontilesize);
         }
      }
   }

   private int blockTab;
   protected void recalcVertices(){
      blockTab = (int)(blockSize*sin15+0.5);
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = blockTab;
      vertexy[1] = blockSize;
      vertexx[2] = 0;
      vertexy[2] = 2*blockSize;
      vertexx[3] = blockSize;
      vertexy[3] = 2*blockSize+blockTab;
      vertexx[4] = 2*blockSize;
      vertexy[4] = 2*blockSize;
      vertexx[5] = 2*blockSize+blockTab;
      vertexy[5] = blockSize;
      vertexx[6] = 2*blockSize;
      vertexy[6] = 0;
      vertexx[7] = blockSize;
      vertexy[7] = blockTab;
      vertexx[8] = blockSize+blockTab;
      vertexy[8] = blockSize+blockTab;
      vertexx[9] = (blockSize+blockTab)/2;
      vertexy[9] = (3*blockSize+blockTab)/2;
      vertexx[10] = (3*blockSize+blockTab)/2;
      vertexy[10] = (blockSize+blockTab)/2;
   }

   private static ICoord[] rotateOffset = {
      new Coord(0,0,0,2),
      new Coord(0,0,0,2),
      new Coord(1,0,0,-1),
      new Coord(1,0,0,-3),
      new Coord(1,0,0,1),
      new Coord(1,0,0,2),
      new Coord(1,0,0,-2),
      new Coord(1,0,0,-1),
      new Coord(0,0,0,2),
      new Coord(0,0,0,-1),
      new Coord(0,0,0,1),
      new Coord(0,0,0,-2),
   };
   protected void rotate(Coord c){
      c.set(c.y(), -c.x(), c.z(), c.tile());
      c.add(rotateOffset[c.tile()]); 
   }
   private static ICoord[] reflectOffset = {
      new Coord(0,0,0,0),
      new Coord(0,0,0,0),
      new Coord(0,0,0,1),
      new Coord(0,0,0,-1),
      new Coord(0,0,0,4),
      new Coord(0,0,0,4),
      new Coord(0,0,0,4),
      new Coord(0,0,0,4),
      new Coord(0,0,0,-4),
      new Coord(0,0,0,-4),
      new Coord(0,0,0,-4),
      new Coord(0,0,0,-4),
   };
   protected void reflect(Coord c){
      c.set(c.y(), c.x(), c.z(), c.tile());
      c.add(reflectOffset[c.tile()]); 
      rotate(c);
      rotate(c);
      rotate(c);
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
      return sin15 + (last.x() - first.x() + 1)*2;
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return sin15 + (last.y() - first.y() + 1)*2;
   }
}
