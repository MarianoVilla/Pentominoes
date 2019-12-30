package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeOctagon2
   extends GridType
{
   private static final int[][] tilesInit = {
         {1,2,3,4,0},{4,5,6,7,0},{7,8,9,10,0},{10,11,12,1,0},
         {3,2,13},{6,5,14},{9,8,15},{12,11,16},
      };
   private static final ICoord[][] adjOffsetInit = {
      {  new Coord(-1,0,0,1), new Coord(0,0,0,4), new Coord(0,-1,0,3), new Coord(0,0,0,1), new Coord(0,0,0,3), },
      {  new Coord(0,-1,0,1), new Coord(0,0,0,4), new Coord(1,0,0,-1), new Coord(0,0,0,1), new Coord(0,0,0,-1), },
      {  new Coord(1,0,0,1), new Coord(0,0,0,4), new Coord(0,1,0,-1), new Coord(0,0,0,1), new Coord(0,0,0,-1), },
      {  new Coord(0,1,0,-3), new Coord(0,0,0,4), new Coord(-1,0,0,-1), new Coord(0,0,0,-3), new Coord(0,0,0,-1), },
      {  new Coord(0,0,0,-4), new Coord(-1,0,0,1), new Coord(0,-1,0,3), },
      {  new Coord(0,0,0,-4), new Coord(0,-1,0,1), new Coord(1,0,0,-1), },
      {  new Coord(0,0,0,-4), new Coord(1,0,0,1), new Coord(0,1,0,-1), },
      {  new Coord(0,0,0,-4), new Coord(0,1,0,-3), new Coord(-1,0,0,-1), },
   };
   private static int[] tileOrbitInit = {0,0,0,0,1,1,1,1};

   public GridTypeOctagon2(){
      super("Octagon2","Split Octagon",4,true, false, 17, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static double factor = Math.sqrt(2);
   private int blockSz2=0;
   
   final static int icontilesize=5;
   final static int icontilesz2=(int)(icontilesize*factor+.5);
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx-icontilesize; x<cx+w+icontilesize; x+=(icontilesize+icontilesz2)*2){
         for(int y=cy-icontilesize; y<cy+h+icontilesize; y+=(icontilesize+icontilesz2)*2){
            g.drawLine(x,y+icontilesz2,x+icontilesz2,y);
            g.drawLine(x+icontilesz2,y+(icontilesz2+icontilesize)*2,x,y+icontilesz2+icontilesize*2);
            g.drawLine(x+icontilesz2+icontilesize*2,y,x+(icontilesz2+icontilesize)*2,y+icontilesz2);
            g.drawLine(x+icontilesz2+icontilesize*2,y+(icontilesz2+icontilesize)*2,x+(icontilesz2+icontilesize)*2,y+icontilesz2+icontilesize*2);
            
            g.drawLine(x,y,x+(icontilesz2+icontilesize)*2,y);
            g.drawLine(x,y,x,y+(icontilesz2+icontilesize)*2);
            g.drawLine(x,y+icontilesize+icontilesz2,x+(icontilesz2+icontilesize)*2,y+icontilesize+icontilesz2);
            g.drawLine(x+icontilesize+icontilesz2,y,x+icontilesize+icontilesz2,y+(icontilesz2+icontilesize)*2);
         }
      }
   }

   protected void recalcVertices(){
      blockSz2 = (int)(blockSize*factor+.5);
      vertexx[0] = blockSz2+blockSize;
      vertexy[0] = blockSz2+blockSize;

      vertexx[1] = 0;
      vertexy[1] = blockSz2+blockSize;
      vertexx[2] = vertexx[1];
      vertexy[2] = vertexy[1]-blockSize;
      vertexx[3] = vertexx[2]+blockSz2;
      vertexy[3] = vertexy[2]-blockSz2;
      vertexx[4] = vertexx[3]+blockSize;
      vertexy[4] = vertexy[3];
      vertexx[13] = vertexx[2];
      vertexy[13] = vertexy[2]-blockSz2;
      
      vertexx[5] = vertexx[4]+blockSize;
      vertexy[5] = vertexy[4];
      vertexx[6] = vertexx[5]+blockSz2;
      vertexy[6] = vertexy[5]+blockSz2;
      vertexx[7] = vertexx[6];
      vertexy[7] = vertexy[6]+blockSize;
      vertexx[14] = vertexx[5]+blockSz2;
      vertexy[14] = vertexy[5];
      
      vertexx[8] = vertexx[7];
      vertexy[8] = vertexy[7]+blockSize;
      vertexx[9] = vertexx[8]-blockSz2;
      vertexy[9] = vertexy[8]+blockSz2;
      vertexx[10] = vertexx[9]-blockSize;
      vertexy[10] = vertexy[9];
      vertexx[15] = vertexx[8];
      vertexy[15] = vertexy[8]+blockSz2;

      vertexx[11] = vertexx[10]-blockSize;
      vertexy[11] = vertexy[10];
      vertexx[12] = vertexx[11]-blockSz2;
      vertexy[12] = vertexy[11]-blockSz2;
      vertexx[16] = vertexx[11]-blockSz2;
      vertexy[16] = vertexy[11];
   }

   protected void rotate(Coord c){
      int x = c.y();
      int y = -c.x();
      int t = c.tile();
      if( t==0 || t==4 ) t+=3;
      else t--;
      c.set(x,y,t);
   }
   protected void reflect(Coord c){
      int x = -c.x();
      int t = c.tile()^1;
      c.set(x,c.y(),t);
   }
   
   protected int getScreenXcoord(ICoord c){
      return offsetX+2*(blockSz2+blockSize)*(c.x()-firstShown.x());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY+2*(blockSz2+blockSize)*(c.y()-firstShown.y());
   }
   protected int getScreenXoffset(ICoord c){
      return 2*(blockSz2+blockSize)*c.x();
   }
   protected int getScreenYoffset(ICoord c){
      return 2*(blockSz2+blockSize)*c.y();
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return 2*(1+factor)*(last.x() - first.x() + 1);
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 2*(1+factor)*(last.y() - first.y() + 1);
   }
}
