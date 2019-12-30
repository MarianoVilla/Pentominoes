package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypePentagon
   extends GridType
{
   private static final int[][] tilesInit = {
      {12,11,0,1,2}, {12,2,3,4,5}, {12,5,6,7,8}, {12,8,9,10,11}
   };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(0,0,0,3), new Coord(0,-1,0,1), new Coord(-1,0,0,3), new Coord(-1,0,0,2), new Coord(0,0,0,1), },
      { new Coord(0,0,0,-1), new Coord(-1,0,0,1), new Coord(0,1,0,-1), new Coord(0,1,0,2), new Coord(0,0,0,1), },
      { new Coord(0,0,0,-1), new Coord(0,1,0,1), new Coord(1,0,0,-1), new Coord(1,0,0,-2), new Coord(0,0,0,1), },
      { new Coord(0,0,0,-1), new Coord(1,0,0,-3), new Coord(0,-1,0,-1), new Coord(0,-1,0,-2), new Coord(0,0,0,-3), },
   };
   private static int[] tileOrbitInit = {0,0,0,0};
   private int blockTab=0;
   private static double tab = 1 / (Math.sqrt(7) + 1);

   public GridTypePentagon(){
      super("Pentagon","Cairo",4,true, false, 13, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=36, icontiletab=10;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx; x<cx+w+icontilesize; x+=icontilesize*2){
         for(int y=cy; y<cy+h+icontilesize; y+=icontilesize*2){
            // cross
            g.drawLine(x-icontilesize+icontiletab,y+icontiletab,x+icontilesize-icontiletab,y-icontiletab);
            g.drawLine(x-icontiletab,y-icontilesize+icontiletab,x+icontiletab,y-icontiletab+icontilesize);
            // left sides of tiles
            g.drawLine(x-icontilesize,y+icontilesize,x-icontilesize+icontiletab,y+icontiletab);
            g.drawLine(x-icontilesize+icontiletab,y+icontiletab,x-icontilesize-icontiletab,y-icontiletab);
            g.drawLine(x-icontilesize-icontiletab,y-icontiletab,x-icontilesize,y-icontilesize);
            // top sides of tiles
            g.drawLine(x-icontilesize,y-icontilesize,x-icontiletab,y+icontiletab-icontilesize);
            g.drawLine(x-icontiletab,y+icontiletab-icontilesize,x+icontiletab,y-icontiletab-icontilesize);
            g.drawLine(x+icontiletab,y-icontiletab-icontilesize,x+icontilesize,y-icontilesize);
         }
      }
   }

   protected void recalcVertices(){
      blockTab = (int)(0.5+blockSize*tab);
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = -blockTab;
      vertexy[1] = blockSize-blockTab;
      vertexx[2] = +blockTab;
      vertexy[2] = blockSize+blockTab;
      vertexx[3] = 0;
      vertexy[3] = blockSize*2;
      vertexx[4] = blockSize-blockTab;
      vertexy[4] = blockSize*2+blockTab;
      vertexx[5] = blockSize+blockTab;
      vertexy[5] = blockSize*2-blockTab;
      vertexx[6] = blockSize*2;
      vertexy[6] = blockSize*2;
      vertexx[7] = blockSize*2+blockTab;
      vertexy[7] = blockSize+blockTab;
      vertexx[8] = blockSize*2-blockTab;
      vertexy[8] = blockSize-blockTab;
      vertexx[9] = blockSize*2;
      vertexy[9] = 0;
      vertexx[10]= blockSize+blockTab;
      vertexy[10]= -blockTab;
      vertexx[11]= blockSize-blockTab;
      vertexy[11]= blockTab;
      vertexx[12]= blockSize;
      vertexy[12]= blockSize;
   }

   protected void rotate(Coord c){
      c.set(c.y(), -c.x(), (c.tile()+1)%4);
   }
   private static ICoord[] reflectOffset = {
      new Coord(0,0,0,0),
      new Coord(-1,0,0,2),
      new Coord(-1,-1,0,0),
      new Coord(0,-1,0,-2),
   };
   protected void reflect(Coord c){
      c.set(-c.y(),-c.x(),c.tile());
      c.add( reflectOffset[c.tile()] );
   }

   protected int getScreenXcoord(ICoord c){
      return offsetX+blockTab+2*blockSize*(c.x()-firstShown.x());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY+blockTab+2*blockSize*(c.y()-firstShown.y());
   }
   protected int getScreenXoffset(ICoord c){
      return 2*blockSize*c.x();
   }
   protected int getScreenYoffset(ICoord c){
      return 2*blockSize*c.y();
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return 2*(last.x() - first.x() + 1 + tab);
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 2*(last.y() - first.y() + 1 + tab);
   }
}
