package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypePentagonHalf
   extends GridType
{
   private static final int[][] tilesInit = {
      {12,11,13,2}, {0,1,13,11},
      {12,2,14,5}, {3,4,14,2},
      {12,5,15,8}, {6,7,15,5},
      {12,8,16,11}, {9,10,16,8}
   };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(0,0,0,6), new Coord(0,0,0,1), new Coord(-1,0,0,5), new Coord(0,0,0,2) },
      { new Coord(-1,0,0,6), new Coord(-1,0,0,3), new Coord(0,0,0,-1), new Coord(0,-1,0,2) },
      { new Coord(0,0,0,-2), new Coord(0,0,0,1), new Coord(0,1,0,5), new Coord(0,0,0,2) },
      { new Coord(0,1,0,-2), new Coord(0,1,0,3), new Coord(0,0,0,-1), new Coord(-1,0,0,2) },
      { new Coord(0,0,0,-2), new Coord(0,0,0,1), new Coord(1,0,0,-3), new Coord(0,0,0,2) },
      { new Coord(1,0,0,-2), new Coord(1,0,0,-5), new Coord(0,0,0,-1), new Coord(0,1,0,2) },
      { new Coord(0,0,0,-2), new Coord(0,0,0,1), new Coord(0,-1,0,-3), new Coord(0,0,0,-6) },
      { new Coord(0,-1,0,-2), new Coord(0,-1,0,-5), new Coord(0,0,0,-1), new Coord(1,0,0,-6) },
   };
   private static int[] tileOrbitInit = {0,0,0,0,0,0,0,0};
   private int blockTab=0;
   private static double tab = 1 / (Math.sqrt(7) + 1);

   public GridTypePentagonHalf(){
      super("Pentagonhalf","Split Cairo",4,true, false, 17, tilesInit, adjOffsetInit, tileOrbitInit);
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
            g.drawLine(x,y+icontilesize,x-icontilesize-icontiletab,y-icontiletab);
            g.drawLine(x-icontilesize-icontiletab,y-icontiletab,x-icontilesize,y-icontilesize);
            // top sides of tiles
            g.drawLine(x-icontilesize,y-icontilesize,x-icontiletab,y+icontiletab-icontilesize);
            g.drawLine(x-icontilesize,y,x+icontiletab,y-icontiletab-icontilesize);
            g.drawLine(x+icontiletab,y-icontiletab-icontilesize,x+icontilesize,y-icontilesize);
            // split two more pentagons
            g.drawLine(x,y-icontilesize,x+icontilesize-icontiletab,y-icontiletab);
            g.drawLine(x+icontilesize,y,x+icontiletab,y-icontiletab+icontilesize);
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
      vertexx[13]= 0;
      vertexy[13]= blockSize;
      vertexx[14]= blockSize;
      vertexy[14]= blockSize*2;
      vertexx[15]= blockSize*2;
      vertexy[15]= blockSize;
      vertexx[16]= blockSize;
      vertexy[16]= 0;
   }

   protected void rotate(Coord c){
      c.set(c.y(), -c.x(),(c.tile()+2)%8);
   }
   private static ICoord[] reflectOffset = {
      new Coord(0,0,0,1),
      new Coord(0,0,0,-1),
      new Coord(-1,0,0,5),
      new Coord(-1,0,0,3),
      new Coord(-1,-1,0,1),
      new Coord(-1,-1,0,-1),
      new Coord(0,-1,0,-3),
      new Coord(0,-1,0,-5),
   };
   protected void reflect(Coord c){
      c.set(-c.y(), -c.x(), c.tile());
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
