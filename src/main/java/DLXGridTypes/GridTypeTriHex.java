package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeTriHex
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,2,3,4,5}, {2,1,6}, {5,4,7}};
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(-1,0,0,2), new Coord(0,0,0,1), new Coord(0,1,0,2),
        new Coord(1,0,0,1), new Coord(0,0,0,2), new Coord(0,-1,0,1)
      },{
         new Coord(0,0,0,-1), new Coord(-1,0,0,-1), new Coord(0,1,0,-1)
      },{
         new Coord(0,0,0,-2), new Coord(1,0,0,-2), new Coord(0,-1,0,-2)
      }
   };
   private static final int[] tileOrbitInit = {0,1,1};
   private int blockHeight=0;
   private static double factor=Math.sqrt(3);

   public GridTypeTriHex(){
      super("TriHex","TriHex (3.6.3.6)",6,true, false, 8, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final private static int isz=10;
   final private static int ih=(int)(isz*factor);
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int y=cy-isz/2; y<cy+h; y+=isz*4){
         for(int x=cx-ih/2; x<cx+w; x+=ih*4){
            drawIconPart(g, x, y);
            drawIconPart(g, x+ih*2,y+isz*2);
         }
      }
   }
   private void drawIconPart(Graphics g, int x, int y){
      g.drawLine(x,y,x,y+isz*2);
      g.drawLine(x,y,x+ih,y+isz);
      g.drawLine(x,y+isz*2,x+ih,y+isz);
      
      g.drawLine(x+ih,y+isz,x+ih*2,y);
      g.drawLine(x+ih,y+isz,x+ih*2,y+isz*2);
      g.drawLine(x+ih*2,y,x+ih*2,y+isz*2);
   }

   protected void recalcVertices(){
      blockHeight = (int)(0.5 + blockSize*factor);
      vertexx[0] = blockSize*0+blockHeight*2;
      vertexy[0] = 0;
      vertexx[1] = vertexx[0]-blockHeight;
      vertexy[1] = vertexy[0]+blockSize;
      vertexx[2] = vertexx[1];
      vertexy[2] = vertexy[1]+blockSize*2;
      vertexx[3] = vertexx[2]+blockHeight;
      vertexy[3] = vertexy[2]+blockSize;
      
      vertexx[5] = vertexx[0]+blockHeight;
      vertexy[5] = vertexy[0]+blockSize;
      vertexx[4] = vertexx[5];
      vertexy[4] = vertexy[5]+blockSize*2;

      vertexx[6] = vertexx[1]-blockHeight;
      vertexy[6] = vertexy[1]+blockSize;
      vertexx[7] = vertexx[5]+blockHeight;
      vertexy[7] = vertexy[5]+blockSize;
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
      return offsetX + 2*blockHeight*(c.x() - firstShown.x() -c.y() + lastShown.y());
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY + 2*blockSize*(c.y() - firstShown.y() + c.x() - firstShown.x());
   }
   protected int getScreenXoffset(ICoord c){
      return 2*blockHeight*(c.x()-c.y());
   }
   protected int getScreenYoffset(ICoord c){
      return 2*blockSize*(c.x()+c.y());
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return (2*factor)*(last.x() - first.x() + last.y() - first.y() + 2);
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 2*(last.y() - first.y() + last.x() - first.x() + 2);
   }
}
