package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeOctagon
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,2,3,4,5,6,7},{3,8,9,4} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,0,0,0), new Coord(-1,0,0,1),
        new Coord(0,1,0,0), new Coord(0,0,0,1),
        new Coord(1,0,0,0), new Coord(0,-1,0,1),
        new Coord(0,-1,0,0), new Coord(-1,-1,0,1),
      },{
         new Coord(0,1,0,-1),
         new Coord(1,1,0,-1),
         new Coord(1,0,0,-1),
         new Coord(0,0,0,-1),
      }
   };
   private static int[] tileOrbitInit = {0,1};

   public GridTypeOctagon(){
      super("Octagon","Octagon (4.8.8)",4,true, false, 10, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static double factor = Math.sqrt(2)/2;
   private int blockSz2=0;
   
   final static int icontilesize=10;
   final static int icontilesz2=(int)(icontilesize*factor+.5);
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx-icontilesize/2; x<cx+w+icontilesize; x+=icontilesize+icontilesz2*2){
         for(int y=cy-icontilesize/2; y<cy+h+icontilesize; y+=icontilesize+icontilesz2*2){
            g.drawLine(x+icontilesz2,y+icontilesz2*2+icontilesize,x,y+icontilesz2+icontilesize);
            g.drawLine(x,y+icontilesz2+icontilesize,x,y+icontilesz2);
            g.drawLine(x,y+icontilesz2,x+icontilesz2,y);
            g.drawLine(x+icontilesz2,y,x+icontilesz2+icontilesize,y);
            g.drawLine(x+icontilesz2+icontilesize,y,x+icontilesz2*2+icontilesize,y+icontilesz2);
            g.drawLine(x+icontilesz2+icontilesize,y+icontilesz2*2+icontilesize,x+icontilesz2*2+icontilesize,y+icontilesz2+icontilesize);
         }
      }
   }

   protected void recalcVertices(){
      blockSz2 = (int)(blockSize*factor+.5);
      vertexx[0] = 0;
      vertexy[0] = blockSz2;
      vertexx[1] = vertexx[0];
      vertexy[1] = vertexy[0]+blockSize;
      vertexx[2] = vertexx[1]+blockSz2;
      vertexy[2] = vertexy[1]+blockSz2;
      vertexx[3] = vertexx[2]+blockSize;
      vertexy[3] = vertexy[2];
      vertexx[4] = vertexx[3]+blockSz2;
      vertexy[4] = vertexy[3]-blockSz2;
      vertexx[5] = vertexx[4];
      vertexy[5] = vertexy[4]-blockSize;
      vertexx[6] = vertexx[5]-blockSz2;
      vertexy[6] = vertexy[5]-blockSz2;
      vertexx[7] = vertexx[6]-blockSize;
      vertexy[7] = vertexy[6]+0;
      vertexx[8] = vertexx[3]+blockSz2;
      vertexy[8] = vertexy[3]+blockSz2;
      vertexx[9] = vertexx[4]+blockSz2;
      vertexy[9] = vertexy[4]+blockSz2;
   }

   protected void rotate(Coord c){
      int x = c.y();
      int y = -c.x();
      if( c.tile()==1 ) y--;
      c.set(x,y,c.tile());
   }
   protected void reflect(Coord c){
      int x = -c.x();
      if( c.tile() == 1 ) x--;  
      c.set(x,c.y(),c.tile());
   }
   
   protected int getScreenXcoord(ICoord c){
      return offsetX+(2*blockSz2+blockSize)*(c.x()-firstShown.x());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY+(2*blockSz2+blockSize)*(c.y()-firstShown.y());
   }
   protected int getScreenXoffset(ICoord c){
      return (2*blockSz2+blockSize)*c.x();
   }
   protected int getScreenYoffset(ICoord c){
      return (2*blockSz2+blockSize)*c.y();
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return (1+2*factor)*(last.x() - first.x() + 1)+factor;
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return (1+2*factor)*(last.y() - first.y() + 1)+factor;
   }
}
