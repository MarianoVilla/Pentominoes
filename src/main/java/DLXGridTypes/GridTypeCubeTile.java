package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeCubeTile
   extends GridType
{
   private static final int[][] tilesInit = { {6,0,1,2}, {6,2,3,4}, {6,4,5,0} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(0,0,0,2),  new Coord(-1,0,0,1), new Coord(0,1,0,2), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,1,0,1), new Coord(1,0,0,-1), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(0,-1,0,-2), new Coord(-1,-1,0,-1), new Coord(0,0,0,-2) },
   };
   private static int[] tileOrbitInit = {0,0,0};
   private int blockHeight=0;
   private static double factor=Math.sqrt(3);

   public GridTypeCubeTile(){
      super("Cubetiling", "Cube Tiling",6,true, false, 7, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=15;
   final static int icontileheight=26;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int y=cy-icontileheight/2; y<cy+h; y+=icontileheight*2){
         for(int x=cx; x<cx+w; x+=icontilesize*6){
            g.drawLine(x,y,x+icontilesize,y+icontileheight);
            g.drawLine(x,y,x+icontilesize,y-icontileheight);
            g.drawLine(x,y,x+icontilesize*2,y);
            g.drawLine(x+icontilesize,y-icontileheight,x+icontilesize*3,y-icontileheight);
            g.drawLine(x+icontilesize*2,y,x+icontilesize*3,y-icontileheight);
            g.drawLine(x+icontilesize*2,y,x+icontilesize*3,y+icontileheight);

            g.drawLine(x+icontilesize*4,y,x+icontilesize*3,y+icontileheight);
            g.drawLine(x+icontilesize*4,y,x+icontilesize*3,y-icontileheight);
            g.drawLine(x+icontilesize*4,y,x+icontilesize*6,y);
            g.drawLine(x+icontilesize*3,y-icontileheight,x+icontilesize*5,y-icontileheight);
            g.drawLine(x+icontilesize*6,y,x+icontilesize*5,y-icontileheight);
            g.drawLine(x+icontilesize*6,y,x+icontilesize*5,y+icontileheight);
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
      vertexx[6] = blockSize;
      vertexy[6] = blockHeight;
   }

   protected void rotate(Coord c){
      int x=c.x()-c.y();
      int y=c.x();
      int t;
      if( c.tile()==2 ){ t=0; }
      else if(c.tile()==1){ t=2; y++; }
      else{ t=1; x--; }
      c.set(x,y,t);
   }
   protected void reflect(Coord c){
      int t = c.tile();
      if( t!=0 ) t=3-t;
      c.set(-c.y(),-c.x(),t);
      rotate(c);
      rotate(c);
      rotate(c);
   }

   protected int getScreenXcoord(ICoord c){
      return offsetX + blockSize + 3*blockSize*(c.x() - firstShown.x() -c.y() + lastShown.y());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY + blockHeight*(c.y() - firstShown.y() + c.x() - firstShown.x());
   }
   protected int getScreenXoffset(ICoord c){
      return (c.x()-c.y())*3*blockSize;
   }
   protected int getScreenYoffset(ICoord c){
      return (c.x()+c.y())*blockHeight;
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return 3*(last.x() - first.x() + last.y() - first.y() + 1) + 1;
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return (last.y() - first.y() + last.x() - first.x() + 2) * factor;
   }
}
