package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeOctagonStar
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,2,3,4,5,6,7},{6,5,4,8,9,10,11,12} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,-1,0,1), new Coord(-1,-1,0,1),
        new Coord(0,-1,0,1), new Coord(0,-1,0,1),
        new Coord(0,0,0,1), new Coord(0,0,0,1),
        new Coord(-1,0,0,1), new Coord(-1,0,0,1),
      },{
         new Coord(0,0,0,-1),new Coord(0,0,0,-1),
         new Coord(1,0,0,-1),new Coord(1,0,0,-1),
         new Coord(1,1,0,-1),new Coord(1,1,0,-1),
         new Coord(0,1,0,-1),new Coord(0,1,0,-1),
      }
   };
   private static int[] tileOrbitInit = {0,1};

   public GridTypeOctagonStar(){
      super("OctagonStar","Octagon & Star",4,true, false, 13, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=30;
   final static int icontiled=(int)(.5+icontilesize*(1-Math.sqrt(2)/2));
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx-icontilesize/2; x<cx+w+icontilesize; x+=icontilesize*2){
         for(int y=cy-icontilesize/2; y<cy+h+icontilesize; y+=icontilesize*2){
            g.drawLine(x,y+icontilesize,x+icontiled,y+icontiled);
            g.drawLine(x+icontiled,y+icontiled,x+icontilesize,y);
            g.drawLine(x+icontilesize,y,x+icontilesize*2-icontiled,y+icontiled);
            g.drawLine(x+icontilesize*2-icontiled,y+icontiled,x+icontilesize*2,y+icontilesize);
            g.drawLine(x+icontilesize*2,y+icontilesize,x+icontilesize*2-icontiled,y+icontilesize*2-icontiled);
            g.drawLine(x+icontilesize*2-icontiled,y+icontilesize*2-icontiled,x+icontilesize,y+icontilesize*2);
            g.drawLine(x+icontilesize,y+icontilesize*2,x+icontiled,y+icontilesize*2-icontiled);
            g.drawLine(x+icontiled,y+icontilesize*2-icontiled,x,y+icontilesize);
         }
      }
   }

   protected void recalcVertices(){
      int d = (int)(.5+blockSize*(1-Math.sqrt(2)/2));
      
      vertexx[0] = 0;
      vertexy[0] = blockSize;
      vertexx[1] = d;
      vertexy[1] = d;
      vertexx[2] = blockSize;
      vertexy[2] = 0;
      vertexx[3] = blockSize*2-d;
      vertexy[3] = d;
      vertexx[4] = blockSize*2;
      vertexy[4] = blockSize;
      vertexx[5] = blockSize*2-d;
      vertexy[5] = blockSize*2-d;
      vertexx[6] = blockSize;
      vertexy[6] = blockSize*2;
      vertexx[7] = d;
      vertexy[7] = blockSize*2-d;

      vertexx[8] = blockSize*2+d;
      vertexy[8] = blockSize*2-d;
      vertexx[9] = blockSize*3;
      vertexy[9] = blockSize*2;
      vertexx[10] = blockSize*2+d;
      vertexy[10] = blockSize*2+d;
      vertexx[11] = blockSize*2;
      vertexy[11] = blockSize*3;
      vertexx[12] = blockSize*2-d;
      vertexy[12] = blockSize*2+d;
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
      return 2*(last.x() - first.x() + 1)+1;
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 2*(last.y() - first.y() + 1)+1;
   }
}
