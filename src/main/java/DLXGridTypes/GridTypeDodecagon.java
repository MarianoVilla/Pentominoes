package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeDodecagon
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,2,3,4,5,6,7,8,9,10,11}, {4,3,12}, {10,9,13}};
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,-1,0,0), new Coord(-1,0,0,2), new Coord(-1,0,0,0),
        new Coord(0,0,0,1), new Coord(0,1,0,0), new Coord(0,1,0,2),
        new Coord(1,1,0,0), new Coord(1,0,0,1), new Coord(1,0,0,0),
        new Coord(0,0,0,2), new Coord(0,-1,0,0), new Coord(0,-1,0,1)
      },{
         new Coord(0,0,0,-1), new Coord(-1,0,0,-1), new Coord(0,1,0,-1)
      },{
         new Coord(0,0,0,-2), new Coord(1,0,0,-2), new Coord(0,-1,0,-2)
      }
   };
   private static int[] tileOrbitInit = {0,1,1};
   private int blockHeight=0;
   private static double factor=Math.sqrt(3);

   public GridTypeDodecagon(){
      super("Dodecagon","Dodecagon (3.12.12)",6,true, false, 14, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final private static int isz=10;
   final private static int ih=(int)(isz*factor);
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int y=cy-ih/2; y<cy+h; y+=isz*4+ih*2){
         for(int x=cx; x<cx+w; x+=ih*4+isz*6){
            drawIconPart(g, x, y);
            drawIconPart(g, x+ih*2+isz*3,y+isz*2+ih);
         }
      }
   }
   private void drawIconPart(Graphics g, int x, int y){
      g.drawLine(x,y,x,y+isz*2);
      g.drawLine(x,y,x+ih,y+isz);
      g.drawLine(x,y+isz*2,x+ih,y+isz);
      
      g.drawLine(x,y+isz*2,x-isz,y+isz*2+ih);
      g.drawLine(x+ih,y+isz,x+ih+isz*2,y+isz);
      
      g.drawLine(x+ih+isz*2,y+isz,x+ih*2+isz*2,y);
      g.drawLine(x+ih+isz*2,y+isz,x+ih*2+isz*2,y+isz*2);
      g.drawLine(x+ih*2+isz*2,y,x+ih*2+isz*2,y+isz*2);
      
      g.drawLine(x+ih*2+isz*2,y+isz*2, x+ih*2+isz*3,y+isz*2+ih);
   }

   protected void recalcVertices(){
      blockHeight = (int)(0.5 + blockSize*factor);
      vertexx[0] = blockSize*3+blockHeight*2;
      vertexy[0] = 0;
      vertexx[1] = vertexx[0]-blockSize*2;
      vertexy[1] = vertexy[0];
      vertexx[2] = vertexx[1]-blockHeight;
      vertexy[2] = vertexy[1]+blockSize;
      vertexx[3] = vertexx[2]-blockSize;
      vertexy[3] = vertexy[2]+blockHeight;
      vertexx[4] = vertexx[3];
      vertexy[4] = vertexy[3]+blockSize*2;
      vertexx[5] = vertexx[4]+blockSize;
      vertexy[5] = vertexy[4]+blockHeight;
      vertexx[6] = vertexx[5]+blockHeight;
      vertexy[6] = vertexy[5]+blockSize;
      
      vertexx[11] = vertexx[0]+blockHeight;
      vertexy[11] = vertexy[0]+blockSize;
      vertexx[10] = vertexx[11]+blockSize;
      vertexy[10] = vertexy[11]+blockHeight;
      vertexx[9] = vertexx[10];
      vertexy[9] = vertexy[10]+blockSize*2;
      vertexx[8] = vertexx[9]-blockSize;
      vertexy[8] = vertexy[9]+blockHeight;
      vertexx[7] = vertexx[8]-blockHeight;
      vertexy[7] = vertexy[8]+blockSize;

      vertexx[12] = vertexx[3]-blockHeight;
      vertexy[12] = vertexy[3]+blockSize;
      vertexx[13] = vertexx[10]+blockHeight;
      vertexy[13] = vertexy[10]+blockSize;
   }

   protected void rotate(Coord c){
      int x=c.x()-c.y();
      int y=c.x();
      int t=c.tile();
      if(t==1){
         t=2;
         x--;
      }else if(t==2){
         t=1;
         x++;
      }
      c.set(x,y,t);
   }
   protected void reflect(Coord c){
      int t = c.tile();
      if( t>0 ) t^=3;
      c.set(c.y(),c.x(),t);
   }

   protected int getScreenXcoord(ICoord c){
      return offsetX + (3*blockSize+2*blockHeight)*(c.x() - firstShown.x() -c.y() + lastShown.y());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY + (2*blockSize + blockHeight)*(c.y() - firstShown.y() + c.x() - firstShown.x());
   }
   protected int getScreenXoffset(ICoord c){
      return (3*blockSize+2*blockHeight)*(c.x()-c.y());
   }
   protected int getScreenYoffset(ICoord c){
      return (2*blockSize + blockHeight)*(c.x()+c.y());
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return (3+2*factor)*(last.x() - first.x() + last.y() - first.y() + 1) + (1+2*factor);
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return (2+factor)*(last.y() - first.y() + last.x() - first.x() + 2);
   }
}
