package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeRhombusStar
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,2,3,4,5,6,7}, {4,3,2,8}, {6,5,4,9} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(0,-1,0,2), new Coord(0,-1,0,2),
        new Coord(0,0,0,1), new Coord(0,0,0,1),
        new Coord(0,0,0,2), new Coord(0,0,0,2),
        new Coord(-1,0,0,1), new Coord(-1,0,0,1),
      },{
         new Coord(0,0,0,-1),new Coord(0,0,0,-1),
         new Coord(1,0,0,-1),new Coord(1,0,0,-1),
      },{
         new Coord(0,0,0,-2),new Coord(0,0,0,-2),
         new Coord(0,1,0,-2),new Coord(0,1,0,-2),
      }
   };
   private static int[] tileOrbitInit = {0,1,1};

   public GridTypeRhombusStar(){
      super("RhombusStar","Rhombus & Star",4,true, false, 10, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=30;
   final static int icontiled=(int)(.5+icontilesize*(Math.tan(Math.PI/8)));
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx-icontilesize/2; x<cx+w+icontilesize; x+=icontilesize*2){
         for(int y=cy-icontilesize/2; y<cy+h+icontilesize; y+=icontilesize*2){
            g.drawLine(x,y,x+icontilesize,y+icontiled);
            g.drawLine(x+icontilesize,y+icontiled,x+icontilesize*2,y);
            g.drawLine(x+icontilesize*2,y,x+icontilesize*2-icontiled,y+icontilesize);
            g.drawLine(x+icontilesize*2-icontiled,y+icontilesize,x+icontilesize*2,y+icontilesize*2);
            g.drawLine(x+icontilesize*2,y+icontilesize*2,x+icontilesize,y+icontilesize*2-icontiled);
            g.drawLine(x+icontilesize,y+icontilesize*2-icontiled,x,y+icontilesize*2);
            g.drawLine(x,y+icontilesize*2,x+icontiled,y+icontilesize);
            g.drawLine(x+icontiled,y+icontilesize,x,y);
         }
      }
   }

   protected void recalcVertices(){
      int d = (int)(.5+blockSize*(Math.tan(Math.PI/8)));
      
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = blockSize;
      vertexy[1] = d;
      vertexx[2] = blockSize*2;
      vertexy[2] = 0;
      vertexx[3] = blockSize*2-d;
      vertexy[3] = blockSize;
      vertexx[4] = blockSize*2;
      vertexy[4] = blockSize*2;
      vertexx[5] = blockSize;
      vertexy[5] = blockSize*2-d;
      vertexx[6] = 0;
      vertexy[6] = blockSize*2;
      vertexx[7] = d;
      vertexy[7] = blockSize;

      vertexx[8] = blockSize*2+d;
      vertexy[8] = blockSize;
      vertexx[9] = blockSize;
      vertexy[9] = blockSize*2+d;
   }

   protected void rotate(Coord c){
      int x = c.y();
      int y = -c.x();
      int t = c.tile();
      if( t==1 ) {
         y--;
         t=2;
      }else if( t==2 ) {
         t=1;
      }
      c.set(x,y,t);
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
      return 2*(last.x() - first.x() + 1)+Math.sqrt(3)/3;
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 2*(last.y() - first.y() + 1)+Math.sqrt(3)/3;
   }
}
