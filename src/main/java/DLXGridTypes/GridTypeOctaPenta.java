package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeOctaPenta
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,2,3,12},{3,4,5,6,12},{6,7,8,9,12},{9,10,11,0,12}, {4,3,2,13,14,15,16,17} };
   private static final ICoord[][] adjOffsetInit = {
         { new Coord(-1, 0,0,4), new Coord( 0,-1,0, 2), new Coord( 0, 0,0,4), new Coord(0,0,0, 1), new Coord(0,0,0, 3) },
         { new Coord( 0, 0,0,3), new Coord( 1, 0,0, 2), new Coord( 0, 1,0,3), new Coord(0,0,0, 1), new Coord(0,0,0,-1) },
         { new Coord( 0, 1,0,2), new Coord( 0, 1,0,-2), new Coord(-1, 1,0,2), new Coord(0,0,0, 1), new Coord(0,0,0,-1) },
         { new Coord(-1, 1,0,1), new Coord(-1, 0,0,-2), new Coord(-1, 0,0,1), new Coord(0,0,0,-3), new Coord(0,0,0,-1) },
         { new Coord(0, 0,0,-3), new Coord(0, 0,0,-4), new Coord(0,-1,0,-2), new Coord(0,-1,0,-3),
           new Coord(1,-1,0,-1), new Coord(1,-1,0,-2), new Coord(1, 0,0,-4), new Coord(1, 0,0,-1) }
   };
   private static int[] tileOrbitInit = {0,0,0,0,1};
   private int r=0, d=0;

   public GridTypeOctaPenta(){
      super("OctaPenta",4,true, false, 18, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=50;
   final static int icontilerad=(int)(.5 + icontilesize*(1-Math.sqrt(2)/2));
   final static int icontileraddiag=(int)(.5 + icontilesize*(Math.sqrt(2)-1)/2);
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx-icontilesize/2; x<cx+w+icontilesize; x+=icontilesize){
         for(int y=cy-icontilesize/2; y<cy+h+icontilesize; y+=icontilesize){
            g.drawLine(x,y+icontilerad,x+icontileraddiag,y+icontileraddiag);
            g.drawLine(x+icontileraddiag,y+icontileraddiag, x+icontilerad, y);
            
            g.drawLine(x+icontilesize-icontilerad, y, x+icontilesize-icontileraddiag, y+icontileraddiag);
            g.drawLine(x+icontilesize-icontileraddiag, y+icontileraddiag, x+icontilesize, y+icontilerad );

            g.drawLine(x+icontilesize, y+icontilesize-icontilerad, x+icontilesize-icontileraddiag, y+icontilesize-icontileraddiag );
            g.drawLine(x+icontilesize-icontileraddiag, y+icontilesize-icontileraddiag, x+icontilesize-icontilerad, y+icontilesize );

            g.drawLine(x+icontilerad, y+icontilesize, x+icontileraddiag, y+icontilesize-icontileraddiag );
            g.drawLine(x+icontileraddiag, y+icontilesize-icontileraddiag, x, y+icontilesize-icontilerad );
            
            g.drawLine(x+icontilerad, y, x+icontilesize-icontilerad, y);
            g.drawLine(x, y+icontilerad, x, y+icontilesize-icontilerad);

            g.drawLine(x+icontileraddiag, y+icontilesize-icontileraddiag, x+icontilesize-icontileraddiag, y+icontileraddiag );
            g.drawLine(x+icontileraddiag, y+icontileraddiag, x+icontilesize-icontileraddiag, y+icontilesize-icontileraddiag );
         }
      }
   }

   protected void recalcVertices(){
      int b = 2*blockSize;
      r = (int)(.5 + b*(1-Math.sqrt(2)/2));
      d = (int)(.5 + b*(Math.sqrt(2)-1)/2);
      
      vertexx[0] = d;
      vertexy[0] = d;
      vertexx[1] = r;
      vertexy[1] = 0;
      vertexx[2] = b-r;
      vertexy[2] = 0;
      vertexx[3] = b-d;
      vertexy[3] = d;
      vertexx[4] = b;
      vertexy[4] = r;
      vertexx[5] = b;
      vertexy[5] = b-r;
      vertexx[6] = b-d;
      vertexy[6] = b-d;
      vertexx[7] = b-r;
      vertexy[7] = b;
      vertexx[8] = r;
      vertexy[8] = b;
      vertexx[9] = d;
      vertexy[9] = b-d;
      vertexx[10] = 0;
      vertexy[10] = b-r;
      vertexx[11] = 0;
      vertexy[11] = r;
      vertexx[12] = blockSize;
      vertexy[12] = blockSize;

      vertexx[13] = b-d;
      vertexy[13] = -d;
      vertexx[14] = b;
      vertexy[14] = -r;
      vertexx[15] = b+d;
      vertexy[15] = -d;
      vertexx[16] = b+r;
      vertexy[16] = 0;
      vertexx[17] = b+d;
      vertexy[17] = d;

   }

   protected void rotate(Coord c){
      int t = c.tile();
      if( t<4 ) {
         c.set(-c.y(), c.x(), (t+1)%4);
      }else {
         c.set(-c.y(), c.x()+1, t);
      }
   }
   protected void reflect(Coord c){
      int t = c.tile();
      if( t == 0 || t==2 ) {
         c.set(-c.x(), c.y(), t);
         t ^= 2;
      }else if (t==4) {
         c.set(-c.x()-1, c.y(), t);
      }else {
         c.set(-c.x(), c.y(), t^2);
      }
   }
   
   protected int getScreenXcoord(ICoord c){
      return offsetX+2*blockSize*(c.x()-firstShown.x());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY+2*blockSize*(c.y()-firstShown.y())+r;
   }
   protected int getScreenXoffset(ICoord c){
      return 2*blockSize*c.x();
   }
   protected int getScreenYoffset(ICoord c){
      return 2*blockSize*c.y();
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return 2*(last.x() - first.x() + 1) + (2-Math.sqrt(2));
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 2*(last.y() - first.y() + 1) + (2-Math.sqrt(2));
   }
}
