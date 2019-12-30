package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeNonagon2
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,8,7,6,5,4,3,2,1}, 
      {3,4,15,14,13,12,11,10,9},
      {23,6,5,4},
      {23,4,15,14},
      {23,14,16,17},
      {23,17,18,19},
      {23,19,20,21},
      {23,21,22,6},
   };
   private static final ICoord[][] adjOffsetInit = {
      {
         new Coord(-1,-1,0,4),new Coord(-1,-1,0,4),new Coord(-1,0,0,1),
         new Coord(0,0,0,2),new Coord(0,0,0,2),new Coord(0,0,0,1),
         new Coord(0,-1,0,6),new Coord(0,-1,0,6),new Coord(-1,-1,0,1),
      },{
         new Coord(0,0,0,-1),new Coord(0,0,0,2),new Coord(0,0,0,2),
         new Coord(1,1,0,-1),new Coord(1,0,0,6),new Coord(1,0,0,6),
         new Coord(1,0,0,-1),new Coord(0,-1,0,4),new Coord(0,-1,0,4),
      },{
         new Coord(0,0,0,5),new Coord(0,0,0,-2),new Coord(0,0,0,-2),new Coord(0,0,0,1),
      },{
         new Coord(0,0,0,-1),new Coord(0,0,0,-2),new Coord(0,0,0,-2),new Coord(0,0,0,1),
      },{
         new Coord(0,0,0,-1),new Coord(1,1,0,-4), new Coord(1,1,0,-4),new Coord(0,0,0,1),
      },{
         new Coord(0,0,0,-1),new Coord(0,1,0,-4),new Coord(0,1,0,-4),new Coord(0,0,0,1),//5
      },{
         new Coord(0,0,0,-1),new Coord(0,1,0,-6),new Coord(0,1,0,-6),new Coord(0,0,0,1),//6
      },{
         new Coord(0,0,0,-1),new Coord(-1,0,0,-6),new Coord(-1,0,0,-6),new Coord(0,0,0,-5),//7
      },
   };
   private static final int[] tileOrbitInit = {0,0,1,1,1,1,1,1};


   public GridTypeNonagon2(){
      super("Nonagon2", "Split Nonagon", 6,true, false, 24, tilesInit, adjOffsetInit, tileOrbitInit );
   }

   public void paintIcon(Graphics g, int cx,int cy, int w0, int h0){
      blockSize = 20;
      recalcVertices();
      int dx = vertexx[10]-vertexx[7];
      int dy = vertexy[14]-vertexy[0];
      int dx2 = vertexx[14]-vertexx[0];
      for(int y=cy-dy; y<cy+h0+dy; y+=dy+dy){
         for(int x=cx-dx; x<cx+w0+dx; x+=dx){
            drawPoly(g,x,y,new int[]{19,23,4,5,6,7,8,0,1,2,3});
            drawPoly(g,x,y,new int[]{21,23,14,15,4,3,9,10});
            drawPoly(g,x,y,new int[]{11,12,13});
            drawPoly(g,x,y,new int[]{17,23,6});
            drawPoly(g,x+dx2,y+dy,new int[]{19,23,4,5,6,7,8,0,1,2,3});
            drawPoly(g,x+dx2,y+dy,new int[]{21,23,14,15,4,3,9,10});
            drawPoly(g,x+dx2,y+dy,new int[]{11,12,13});
            drawPoly(g,x+dx2,y+dy,new int[]{17,23,6});
         }
      }
   }
   private void drawPoly(Graphics g, int x, int y, int[] ix){
      for( int i=0; i+1<ix.length; i++){
         g.drawLine(x+vertexx[ix[i]],y+vertexy[ix[i]],x+vertexx[ix[i+1]],y+vertexy[ix[i+1]]);
      }
   }

   final static double s1 = Math.sin(Math.toRadians(  360/9));
   final static double c1 = Math.cos(Math.toRadians(  360/9));
   final static double s2 = Math.sin(Math.toRadians(2*360/9));
   final static double c2 = Math.cos(Math.toRadians(2*360/9));
   final static double s3 = Math.sin(Math.toRadians(3*360/9));
   final static double c3 = Math.cos(Math.toRadians(3*360/9));
   final static double s4 = Math.sin(Math.toRadians(4*360/9));
   final static double c4 = Math.cos(Math.toRadians(4*360/9));

   protected void recalcVertices(){
      int dx1 = (int)(blockSize*c1);
      int dy1 = (int)(blockSize*s1);
      int dx2 = (int)(blockSize*c2);
      int dy2 = (int)(blockSize*s2);
      int dx3 = (int)(blockSize*c3);
      int dy3 = (int)(blockSize*s3);
      int dx4 = -dx1-dx2-dx3-blockSize/2;
      int dy4 = (int)(blockSize*s4);
      
      vertexx[0] = 2*dx1+2*dx2+dx3;
      vertexy[0] = 0;
      vertexx[1] = vertexx[0]+blockSize;
      vertexy[1] = vertexy[0];
      vertexx[2] = vertexx[1]+dx1;
      vertexy[2] = vertexy[1]+dy1;
      vertexx[3] = vertexx[2]+dx2;
      vertexy[3] = vertexy[2]+dy2;
      vertexx[4] = vertexx[3]+dx3;
      vertexy[4] = vertexy[3]+dy3;
      vertexx[5] = vertexx[4]+dx4;
      vertexy[5] = vertexy[4]+dy4;
      
      vertexx[8] = vertexx[0]-dx1;
      vertexy[8] = vertexy[0]+dy1;
      vertexx[7] = vertexx[8]-dx2;
      vertexy[7] = vertexy[8]+dy2;
      vertexx[6] = vertexx[7]-dx3;
      vertexy[6] = vertexy[7]+dy3;

      vertexx[15] = vertexx[4]+dx2;
      vertexy[15] = vertexy[4]+dy2;
      vertexx[14] = vertexx[15]+dx1;
      vertexy[14] = vertexy[15]+dy1;
      vertexx[13] = vertexx[14]+blockSize;
      vertexy[13] = vertexy[14];
      vertexx[12] = vertexx[13]+dx1;
      vertexy[12] = vertexy[13]-dy1;
      vertexx[11] = vertexx[12]+dx2;
      vertexy[11] = vertexy[12]-dy2;
      vertexx[10] = vertexx[11]+dx3;
      vertexy[10] = vertexy[11]-dy3;
      vertexx[9 ] = vertexx[10]+dx4;
      vertexy[9 ] = vertexy[10]-dy4;

      vertexx[16] = vertexx[14]-dx1;
      vertexy[16] = vertexy[14]+dy1;
      vertexx[17] = vertexx[16]-dx2;
      vertexy[17] = vertexy[16]+dy2;
      vertexx[18] = vertexx[17]+dx4;
      vertexy[18] = vertexy[17]-dy4;

      vertexx[22] = vertexx[ 6]-dx2;
      vertexy[22] = vertexy[ 6]+dy2;
      vertexx[21] = vertexx[22]-dx1;
      vertexy[21] = vertexy[22]+dy1;
      vertexx[20] = vertexx[21]+dx1;
      vertexy[20] = vertexy[21]+dy1;
      vertexx[19] = vertexx[20]+dx2;
      vertexy[19] = vertexy[20]+dy2;
      
      vertexx[23] = vertexx[ 5];
      vertexy[23] = vertexy[ 5]-dy4+dy2+dy1;
   }

   protected void rotate(Coord c){
      int x=c.y();
      int y=c.y()-c.x();
      int t = c.tile();
      if( t==0 ) {
         t = 1;
         x--;
      }else if( t==1){
         t = 0;
      }else if( t==2){
         t=7;
      }else{
         t--;
      }
      c.set(x,y,t);
   }
   protected void reflect(Coord c){
      int t = c.tile();
      if( t <=1 ) t ^= 1;
      else if(t>=4) t=11-t;
      else t=5-t;
      c.set(-c.x(),c.y()-c.x(), t);
      rotate(c);
   }
   
   protected int getScreenXcoord(ICoord c){
      int dx0 = blockSize;
      int dx1 = (int)(blockSize*c1);
      int dx2 = (int)(blockSize*c2);
      int dx3 = (int)(blockSize*c3);
      return offsetX+(4*dx2+4*dx1+2*dx0+2*dx3)*(c.x()-firstShown.x()) - (dx0+2*dx1+2*dx2+dx3)*(c.y()-lastShown.y());
   }
   protected int getScreenYcoord(ICoord c){
      int dy1 = (int)(blockSize*s1);
      int dy2 = (int)(blockSize*s2);
      int dy3 = (int)(blockSize*s3);
      return offsetY+(2*dy1+2*dy2+dy3)*(c.y()-firstShown.y());
   }
   protected int getScreenXoffset(ICoord c){
      int dx0 = blockSize;
      int dx1 = (int)(blockSize*c1);
      int dx2 = (int)(blockSize*c2);
      int dx3 = (int)(blockSize*c3);
      return (4*dx2+4*dx1+2*dx0+2*dx3)*c.x() - (dx0+2*dx1+2*dx2+dx3)*c.y();
   }
   protected int getScreenYoffset(ICoord c){
      int dy1 = (int)(blockSize*s1);
      int dy2 = (int)(blockSize*s2);
      int dy3 = (int)(blockSize*s3);
      return (2*dy1+2*dy2+dy3)*c.y();
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return (4*c2+4*c1+2+2*c3)*(last.x() - first.x() + 1) + (1+2*c1+2*c2+c3)*(last.y() - first.y()) +c1+c2;
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return (2*s1+2*s2+s3)*(last.y() - first.y() + 1) + s1 + s2;
   }

}
