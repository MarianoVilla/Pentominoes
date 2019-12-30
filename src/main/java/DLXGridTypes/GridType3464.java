package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridType3464
   extends GridType
{
   private static final int[][] tilesInit = {
         {0,1,2,3,4,5},
         {1,6,7,2},
         {2,7,8},
         {2,8,9,3},
         {3,9,10},
         {3,10,11,4},
   };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,0,0,5), new Coord(0,0,0,1), new Coord(0,0,0,3),
          new Coord(0,0,0,5), new Coord(0,-1,0,1), new Coord(-1,-1,0,3) },
      { new Coord(-1,0,0,3), new Coord(0,1,0,-1), new Coord(0,0,0,1), new Coord(0,0,0,-1) },
      { new Coord(0,0,0,-1), new Coord(0,1,0,3), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,1,0,-3), new Coord(0,0,0,1), new Coord(0,0,0,-3) },
      { new Coord(0,0,0,-1), new Coord(1,0,0,-3), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,0,0,-5), new Coord(0,-1,0,-3), new Coord(0,0,0,-5) },
   };
   private static int[] tileOrbitInit = {0,1,2,1,2,1};
   private int blockHeight=0;
   private static double factor=Math.sqrt(3);

   public GridType3464(){
      super("3464","Rhombitrihex (3.4.6.4)",6,true, false, 12, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=15;
   final static int icontileheight=26;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int y=cy-icontileheight; y<cy+h+icontileheight; y+=icontileheight*2+icontilesize*2){
         for(int x=cx-icontilesize/2; x<cx+w; x+=icontilesize*6+2*icontileheight){
            g.drawLine(x,y,x-icontilesize,y+icontileheight);
            g.drawLine(x-icontilesize,y+icontileheight,x,y+2*icontileheight);
            g.drawLine(x,y+2*icontileheight,x+2*icontilesize,y+2*icontileheight);
            g.drawLine(x+2*icontilesize,y+2*icontileheight,x+3*icontilesize,y+icontileheight);
            g.drawLine(x+3*icontilesize,y+icontileheight,x+2*icontilesize,y);
            g.drawLine(x+2*icontilesize,y,x,y);

            g.drawLine(x,y+2*icontileheight,x,y+2*icontileheight+2*icontilesize);
            g.drawLine(x+2*icontilesize,y+2*icontileheight,x+2*icontilesize,y+2*icontileheight+2*icontilesize);
            g.drawLine(x+2*icontilesize,y+2*icontileheight,x+2*icontilesize+icontileheight,y+2*icontileheight+icontilesize);
            g.drawLine(x+3*icontilesize,y+icontileheight,x+3*icontilesize+icontileheight,y+icontileheight+icontilesize);
            g.drawLine(x+3*icontilesize,y+icontileheight,x+3*icontilesize+icontileheight,y+icontileheight-icontilesize);
            g.drawLine(x+2*icontilesize,y,x+2*icontilesize+icontileheight,y-icontilesize);
            
            g.drawLine(x+3*icontilesize+icontileheight,y+icontilesize+icontileheight,x+2*icontilesize+icontileheight,y+icontilesize+2*icontileheight);
            g.drawLine(x+2*icontilesize+icontileheight,y+icontilesize+2*icontileheight,x+3*icontilesize+icontileheight,y+icontilesize+3*icontileheight);
            g.drawLine(x+3*icontilesize+icontileheight,y+icontilesize+3*icontileheight,x+5*icontilesize+icontileheight,y+icontilesize+3*icontileheight);
            g.drawLine(x+5*icontilesize+icontileheight,y+icontilesize+3*icontileheight,x+6*icontilesize+icontileheight,y+icontilesize+2*icontileheight);
            g.drawLine(x+6*icontilesize+icontileheight,y+icontilesize+2*icontileheight,x+5*icontilesize+icontileheight,y+icontilesize+icontileheight);
            g.drawLine(x+5*icontilesize+icontileheight,y+icontilesize+icontileheight,x+3*icontilesize+icontileheight,y+icontilesize+icontileheight);

            g.drawLine(x+3*icontilesize+icontileheight,y-icontilesize+icontileheight,x+3*icontilesize+icontileheight,y+icontilesize+icontileheight);
            g.drawLine(x+5*icontilesize+icontileheight,y-icontilesize+icontileheight,x+5*icontilesize+icontileheight,y+icontilesize+icontileheight);
            g.drawLine(x+5*icontilesize+icontileheight,y+icontilesize+3*icontileheight,x+5*icontilesize+2*icontileheight,y+2*icontilesize+3*icontileheight);
            g.drawLine(x+6*icontilesize+icontileheight,y+icontilesize+2*icontileheight,x+6*icontilesize+2*icontileheight,y+2*icontilesize+2*icontileheight);
            g.drawLine(x+6*icontilesize+icontileheight,y+icontilesize+2*icontileheight,x+6*icontilesize+2*icontileheight,y+2*icontileheight);
            g.drawLine(x+5*icontilesize+icontileheight,y+icontilesize+icontileheight,x+5*icontilesize+2*icontileheight,y+icontileheight);
         }
      }
   }

   protected void recalcVertices(){
      blockHeight = (int)(0.5 + blockSize*factor);
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = -blockSize;
      vertexy[1] = blockHeight;
      vertexx[2] = 0;
      vertexy[2] = blockHeight*2;
      vertexx[3] = blockSize*2;
      vertexy[3] = blockHeight*2;
      vertexx[4] = blockSize*3;
      vertexy[4] = blockHeight;
      vertexx[5] = blockSize*2;
      vertexy[5] = 0;
      
      vertexx[6] = -blockHeight-blockSize;
      vertexy[6] = blockHeight+blockSize;
      vertexx[7] = -blockHeight;
      vertexy[7] = blockHeight*2+blockSize;
      vertexx[8] = 0;
      vertexy[8] = blockHeight*2+blockSize*2;
      vertexx[9] = blockSize*2;
      vertexy[9] = blockHeight*2+blockSize*2;
      vertexx[10] = blockSize*2+blockHeight;
      vertexy[10] = blockHeight*2+blockSize;
      vertexx[11] = blockSize*3+blockHeight;
      vertexy[11] = blockHeight+blockSize;
   }

   
   
   private static ICoord[] rotateOffset = {
         new Coord(0,0,0,0),
         new Coord(-1,0,0,4),
         new Coord(-1,0,0,2),
         new Coord(0,0,0,-2),
         new Coord(0,0,0,-2),
         new Coord(0,0,0,-2),
      };
   protected void rotate(Coord c){
      c.set(c.x()-c.y(), c.x(), c.tile());
      c.add(rotateOffset[c.tile()]); 
   }   

   protected void reflect(Coord c){
      int t = (6-c.tile())%6;
      c.set(c.y(), c.x(), c.z(), t);
   }

   protected int getScreenXcoord(ICoord c){
      return offsetX + blockSize+blockHeight + (3*blockSize+blockHeight)*(c.x() - firstShown.x() -c.y() + lastShown.y());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY + (blockSize+blockHeight)*(c.y() - firstShown.y() + c.x() - firstShown.x());
   }
   protected int getScreenXoffset(ICoord c){
      return (c.x()-c.y())*(3*blockSize+blockHeight);
   }
   protected int getScreenYoffset(ICoord c){
      return (c.x()+c.y())*(blockSize+blockHeight);
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return (3+factor)*(last.x() - first.x() + last.y() - first.y() + 1) + (1+factor);
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return (1+factor)*(last.y() - first.y() + last.x() - first.x() + 2);
   }
}
