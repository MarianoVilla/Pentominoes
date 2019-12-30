package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeHeptagon
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,2,3,4,5,6}, 
      {0,6,7,8,9,10,11},
      {1,0,11,12,13},
      {7,6,5,14,15},
   };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(0,0,0,2), new Coord(1,0,0,1), new Coord(1,0,0,3), new Coord(0,-1,0,2), new Coord(0,-1,0,1), new Coord(0,0,0,3), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(0,0,0,2), new Coord(-1,0,0,-1), new Coord(-1,0,0,1), new Coord(0,1,0,2), new Coord(0,1,0,-1), new Coord(0,0,0,1) },

      { new Coord(0,0,0,-2), new Coord(0,0,0,-1), new Coord(0,1,0,-2), new Coord(1,1,0,1), new Coord(1,0,0,-1) },
      { new Coord(0,0,0,-2), new Coord(0,0,0,-3), new Coord(0,-1,0,-2), new Coord(-1,-1,0,-1), new Coord(-1,0,0,-3) },
   };
   private static final int[] tileOrbitInit = {0,0,1,1};


   public GridTypeHeptagon(){
      super("Heptagon", 2,true, false, 16, tilesInit, adjOffsetInit, tileOrbitInit );
   }

   final static double s1 = Math.sin(Math.toRadians(  360/7.));
   final static double c1 = Math.cos(Math.toRadians(  360/7.));
   final static double s2 = Math.sin(Math.toRadians(2*360/7.));
   final static double c2 = Math.cos(Math.toRadians(2*360/7.));
   final static double s3 = Math.sin(Math.toRadians(3*360/7.));
   final static double c3 = Math.cos(Math.toRadians(3*360/7.));

   public void paintIcon(Graphics g, int cx,int cy, int w0, int h0){
      blockSize = 20;
      recalcVertices();
      int dx = vertexx[3]-vertexx[9]+vertexx[14]-vertexx[15];
      int dy = vertexy[12]-vertexy[15];
      int dx2 = vertexx[3]-vertexx[15];
      int dy2 = vertexy[3]-vertexy[15];
      for(int y=cy-dy; y<cy+h0+dy; y+=dy){
         for(int x=cx-dx; x<cx+w0+dx; x+=dx){
            g.drawLine(x+vertexx[15],y+vertexy[15],x+vertexx[7],y+vertexy[7]);
            g.drawLine(x+vertexx[7],y+vertexy[7],x+vertexx[8],y+vertexy[8]);
            g.drawLine(x+vertexx[8],y+vertexy[8],x+vertexx[9],y+vertexy[9]);
            g.drawLine(x+vertexx[9],y+vertexy[9],x+vertexx[10],y+vertexy[10]);
            g.drawLine(x+vertexx[10],y+vertexy[10],x+vertexx[11],y+vertexy[11]);
            g.drawLine(x+vertexx[11],y+vertexy[11],x+vertexx[12],y+vertexy[12]);
            g.drawLine(x+vertexx[12],y+vertexy[12],x+vertexx[13],y+vertexy[13]);
            g.drawLine(x+vertexx[7],y+vertexy[7],x+vertexx[6],y+vertexy[6]);
            g.drawLine(x+vertexx[6],y+vertexy[6],x+vertexx[5],y+vertexy[5]);
            g.drawLine(x+vertexx[6],y+vertexy[6],x+vertexx[0],y+vertexy[0]);
            g.drawLine(x+vertexx[0],y+vertexy[0],x+vertexx[11],y+vertexy[11]);
            g.drawLine(x+vertexx[0],y+vertexy[0],x+vertexx[1],y+vertexy[1]);

            g.drawLine(x+dx2+vertexx[15],y+dy2+vertexy[15],x+dx2+vertexx[7],y+dy2+vertexy[7]);
            g.drawLine(x+dx2+vertexx[7],y+dy2+vertexy[7],x+dx2+vertexx[8],y+dy2+vertexy[8]);
            g.drawLine(x+dx2+vertexx[8],y+dy2+vertexy[8],x+dx2+vertexx[9],y+dy2+vertexy[9]);
            g.drawLine(x+dx2+vertexx[9],y+dy2+vertexy[9],x+dx2+vertexx[10],y+dy2+vertexy[10]);
            g.drawLine(x+dx2+vertexx[10],y+dy2+vertexy[10],x+dx2+vertexx[11],y+dy2+vertexy[11]);
            g.drawLine(x+dx2+vertexx[11],y+dy2+vertexy[11],x+dx2+vertexx[12],y+dy2+vertexy[12]);
            g.drawLine(x+dx2+vertexx[12],y+dy2+vertexy[12],x+dx2+vertexx[13],y+dy2+vertexy[13]);
            g.drawLine(x+dx2+vertexx[7],y+dy2+vertexy[7],x+dx2+vertexx[6],y+dy2+vertexy[6]);
            g.drawLine(x+dx2+vertexx[6],y+dy2+vertexy[6],x+dx2+vertexx[5],y+dy2+vertexy[5]);
            g.drawLine(x+dx2+vertexx[6],y+dy2+vertexy[6],x+dx2+vertexx[0],y+dy2+vertexy[0]);
            g.drawLine(x+dx2+vertexx[0],y+dy2+vertexy[0],x+dx2+vertexx[11],y+dy2+vertexy[11]);
            g.drawLine(x+dx2+vertexx[0],y+dy2+vertexy[0],x+dx2+vertexx[1],y+dy2+vertexy[1]);
         }
      }
   }

   protected void recalcVertices(){
      int dx1 = (int)(blockSize*s1);
      int dy1 = (int)(blockSize*c1);
      int dx2 = (int)(blockSize*s2);
      int dy2 = (int)(blockSize*c2);
      int dx3 = (int)(blockSize*s3);
      int dy3 = -dy1-dy2-blockSize/2;
      
      vertexx[0] = dx1+dx2+dx3;
      vertexy[0] = dy1-dy3+blockSize;
      vertexx[1] = vertexx[0]+dx1;
      vertexy[1] = vertexy[0]+dy1;
      vertexx[2] = vertexx[1]+dx2;
      vertexy[2] = vertexy[1]+dy2;
      vertexx[3] = vertexx[2]+dx3;
      vertexy[3] = vertexy[2]+dy3;
      vertexx[6] = vertexx[0];
      vertexy[6] = vertexy[0]-blockSize;
      vertexx[5] = vertexx[6]+dx1;
      vertexy[5] = vertexy[6]-dy1;
      vertexx[4] = vertexx[5]+dx2;
      vertexy[4] = vertexy[5]-dy2;

      vertexx[7] = vertexx[6]-dx1;
      vertexy[7] = vertexy[6]-dy1;
      vertexx[8] = vertexx[7]-dx2;
      vertexy[8] = vertexy[7]-dy2;
      vertexx[11] = vertexx[0]-dx1;
      vertexy[11] = vertexy[0]+dy1;
      vertexx[10] = vertexx[11]-dx2;
      vertexy[10] = vertexy[11]+dy2;
      vertexx[9] = vertexx[10]-dx3;
      vertexy[9] = vertexy[10]+dy3;

      vertexx[12] = vertexx[11]-vertexx[4]+vertexx[3];
      vertexy[12] = vertexy[11]-vertexy[4]+vertexy[3];
      vertexx[13] = vertexx[1]-vertexx[8]+vertexx[9];
      vertexy[13] = vertexy[1]-vertexy[8]+vertexy[9];

      vertexx[14] = vertexx[5]-dx3;
      vertexy[14] = vertexy[5]+dy3;
      vertexx[15] = vertexx[7]+dx3;
      vertexy[15] = vertexy[7]+dy3;
   }

   protected void rotate(Coord c){
      c.set(-c.x(),-c.y(),c.tile()^1);
   }
   protected void reflect(Coord c){
      int t = c.tile();
      if( t <=1 ) t ^= 1;
      c.set(c.y(), c.x(), t);
   }
   
   protected int getScreenXcoord(ICoord c){
      int dx1 = (int)(blockSize*s1);
      int dx2 = (int)(blockSize*s2);
      return offsetX+(2*dx1+dx2)*(c.x()-firstShown.x() - (c.y()-lastShown.y()));
   }
   protected int getScreenYcoord(ICoord c){
      int dy1 = (int)(blockSize*c1);
      int dy2 = (int)(blockSize*c2);
      return offsetY+(2*dy1+dy2+blockSize)*(c.x()-firstShown.x() + (c.y()-firstShown.y()));
   }
   protected int getScreenXoffset(ICoord c){
      int dx1 = (int)(blockSize*s1);
      int dx2 = (int)(blockSize*s2);
      return (2*dx1+dx2)*(c.x() - c.y());
   }
   protected int getScreenYoffset(ICoord c){
      int dy1 = (int)(blockSize*c1);
      int dy2 = (int)(blockSize*c2);
      return (2*dy1+dy2+blockSize)*(c.x() + c.y());
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return (2*s1+s2)*(last.x() - first.x() + last.y() - first.y() + 1)+(s2+2*s3);
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return (1+2*c1+c2)*(last.x() - first.x() + last.y() - first.y() + 2);
      
   }

}
