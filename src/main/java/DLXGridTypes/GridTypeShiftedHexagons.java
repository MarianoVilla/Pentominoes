package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeShiftedHexagons
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
 
   public GridTypeShiftedHexagons(){
      super("ShiftedHexagons","Shifted Hexagons",6,false, false, 14, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final private static int isz=10;
   final private static int ih=(int)(isz*factor);
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      // ix*(4ih,2isz) + iy*(-3ih,5isz) = (cx,cy)
      // ix = (cx*5isz + cy*3ih)/26*ih*isz
      // iy = (cx*-2isz+ cy*4ih)/26*ih*isz
      int ixmn = 0;
      int ixmx = ((cx+w)*5*isz + (cy+h)*3*ih)/(26*ih*isz) +1;
      int iymn = ((cx+w)*-2*isz+ cy    *4*ih)/(26*ih*isz) -1;
      int iymx = ( cx   *-2*isz+ (cy+h)*4*ih)/(26*ih*isz) +1;
      for(int iy=iymn; iy<=iymx; iy++){
         for(int ix=ixmn; ix<=ixmx; ix++){
            int x = cx + 4*ih*ix -3*ih*iy -ih/3;
            int y = cy + 2*isz*ix+5*isz*iy;
            drawIconPart(g, x, y);
         }
      }
   }
   private void drawIconPart(Graphics g, int x, int y){
      g.drawLine(x,y,x+ih*2,y-isz*2);
      g.drawLine(x+ih*2,y-isz*2,x+ih*4,y);
      g.drawLine(x+ih*4,y,x+ih*4,y+isz*4);
      g.drawLine(x+ih*4,y+isz*4,x+ih*2,y+isz*6);
      g.drawLine(x+ih*2,y+isz*6,x,y+isz*4);
      g.drawLine(x,y+isz*4,x,y);
   }

   protected void recalcVertices(){
      blockHeight = (int)(0.5 + blockSize*factor);
      vertexx[0] = blockHeight*3;
      vertexy[0] = 0;
      vertexx[1] = vertexx[0]-blockHeight;
      vertexy[1] = vertexy[0]+blockSize;
      vertexx[2] = vertexx[1]-blockHeight;
      vertexy[2] = vertexy[1]+blockSize;
      vertexx[3] = vertexx[2];
      vertexy[3] = vertexy[2]+blockSize*2;
      vertexx[4] = vertexx[3];
      vertexy[4] = vertexy[3]+blockSize*2;
      vertexx[5] = vertexx[4]+blockHeight;
      vertexy[5] = vertexy[4]+blockSize;
      vertexx[6] = vertexx[5]+blockHeight;
      vertexy[6] = vertexy[5]+blockSize;
      
      vertexx[11] = vertexx[0]+blockHeight;
      vertexy[11] = vertexy[0]+blockSize;
      vertexx[10] = vertexx[11]+blockHeight;
      vertexy[10] = vertexy[11]+blockSize;
      vertexx[9] = vertexx[10];
      vertexy[9] = vertexy[10]+blockSize*2;
      vertexx[8] = vertexx[9];
      vertexy[8] = vertexy[9]+blockSize*2;
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
   }

   protected int getScreenXcoord(ICoord c){
      return offsetX + blockHeight*(4*(c.x() - firstShown.x()) -3*(c.y() - lastShown.y()) );
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY + blockSize*( 5*(c.y() - firstShown.y()) + 2*(c.x() - firstShown.x()) );
   }
   protected int getScreenXoffset(ICoord c){
      return blockHeight*(4*c.x()-3*c.y());
   }
   protected int getScreenYoffset(ICoord c){
      return blockSize*(5*c.y() + 2*c.x());
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return factor*( 4*(last.x() - first.x() + 1) + 3*(last.y() - first.y() + 1) );
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 5*(last.y() - first.y()+1) + 2*(last.x() - first.x() + 1) + 1;
   }
}
