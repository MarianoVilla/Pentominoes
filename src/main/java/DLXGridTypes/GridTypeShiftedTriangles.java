package DLXGridTypes;

import java.awt.Graphics;

import DLXPentominoesSolverPack.Coord;
import DLXPentominoesSolverPack.ICoord;

class GridTypeShiftedTriangles
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,2,3,4,5}, {2,1,6,7,8,9}, {5,4,10,11,12,13} };
   private static final ICoord[][] adjOffsetInit = {
      { new Coord(0,-1,0,2), new Coord(0,0,0,1), new Coord(1,0,0,2),
        new Coord(0,1,0,1), new Coord(0,0,0,2), new Coord(-1,0,0,1),
      },{
         new Coord(0,0,0,-1), new Coord(0,-1,0,1), new Coord(0,-1,0,-1),
         new Coord(1,-1,0,1), new Coord(1,0,0,-1), new Coord(1,0,0,1),
      },{
         new Coord(0,0,0,-2), new Coord(0,1,0,-1), new Coord(0,1,0,-2),
         new Coord(-1,1,0,-1), new Coord(-1,0,0,-2), new Coord(-1,0,0,-1),
      }
   };
   private static int[] tileOrbitInit = {0,1,1};
   private int blockHeight=0;
   private static double factor=Math.sqrt(3);
 
   public GridTypeShiftedTriangles(){
      super("ShiftedTriangles","Shifted Triangles",6,false, false, 14, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final private static int isz=10;
   final private static int ih=(int)(isz*factor);
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      // ix*(4ih,2isz) + iy*(-3ih,5isz) = (cx,cy)
      // ix*(3ih,1isz) + iy*(-2ih,4isz) = (cx,cy)
      // ix = (cx*4isz + cy*2ih)/14*ih*isz
      // iy = (cx*-1isz+ cy*3ih)/14*ih*isz
      int ixmn = 0;
      int ixmx = ((cx+w)*4*isz + (cy+h)*2*ih)/(14*ih*isz) +1;
      int iymn = ((cx+w)*-1*isz+ cy    *3*ih)/(14*ih*isz) -1;
      int iymx = ( cx   *-1*isz+ (cy+h)*3*ih)/(14*ih*isz) +1;
      for(int iy=iymn; iy<=iymx; iy++){
         for(int ix=ixmn; ix<=ixmx; ix++){
            int x = cx + 3*ih*ix -2*ih*iy -ih/3;
            int y = cy + 1*isz*ix+4*isz*iy;
            drawIconPart(g, x, y);
         }
      }
   }
   private void drawIconPart(Graphics g, int x, int y){
      g.drawLine(x,y,x+ih,y-isz);
      g.drawLine(x+ih,y-isz,x+ih*2,y);
      g.drawLine(x+ih*2,y,x+ih*2,y+isz*2);
      g.drawLine(x+ih*3,y+isz,x+ih,y+isz*3);
      g.drawLine(x+ih*2,y+isz*4,x,y+isz*2);
      g.drawLine(x,y+isz*4,x,y);
   }

   protected void recalcVertices(){
      blockHeight = (int)(0.5 + blockSize*factor);
      vertexx[0] = blockHeight*3;
      vertexy[0] = blockSize;
      vertexx[1] = vertexx[0]+blockHeight;
      vertexy[1] = vertexy[0]+blockSize;
      vertexx[2] = vertexx[1];
      vertexy[2] = vertexy[1]+blockSize*2;
      vertexx[3] = vertexx[2]-blockHeight;
      vertexy[3] = vertexy[2]+blockSize;
      vertexx[4] = vertexx[3]-blockHeight;
      vertexy[4] = vertexy[3]-blockSize;
      vertexx[5] = vertexx[4];
      vertexy[5] = vertexy[4]-blockSize*2;

      vertexx[6] = vertexx[1];
      vertexy[6] = vertexy[1]-blockSize*2;
      vertexx[7] = vertexx[6]+blockHeight;
      vertexy[7] = vertexy[6]+blockSize;
      vertexx[8] = vertexx[7]+blockHeight;
      vertexy[8] = vertexy[7]+blockSize;
      vertexx[9] = vertexx[8]-blockHeight;
      vertexy[9] = vertexy[8]+blockSize;

      vertexx[10] = vertexx[4];
      vertexy[10] = vertexy[4]+blockSize*2;
      vertexx[11] = vertexx[10]-blockHeight;
      vertexy[11] = vertexy[10]-blockSize;
      vertexx[12] = vertexx[11]-blockHeight;
      vertexy[12] = vertexy[11]-blockSize;
      vertexx[13] = vertexx[12]+blockHeight;
      vertexy[13] = vertexy[12]-blockSize;
   }

   protected void rotate(Coord c){
      int x=c.x()-c.y();
      int y=c.x();
      int t=c.tile();
      if(t==1){
         t=2;
         x++;
      }else if(t==2){
         t=1;
         x--;
      }
      c.set(x,y,t);
   }
   protected void reflect(Coord c){
   }

   protected int getScreenXcoord(ICoord c){
      return offsetX + blockHeight*(3*(c.x() - firstShown.x()) -2*(c.y() - lastShown.y()) );
   }
   protected int getScreenYcoord(ICoord c){
      return offsetY + blockSize*( 4*(c.y() - firstShown.y()) + 1*(c.x() - firstShown.x()) );
   }
   protected int getScreenXoffset(ICoord c){
      return blockHeight*(3*c.x()-2*c.y());
   }
   protected int getScreenYoffset(ICoord c){
      return blockSize*(4*c.y() + 1*c.x());
   }

   protected double calcWidthInBlocks(ICoord first, ICoord last){
      return factor*( 3*(last.x() - first.x() + 1) + 2*(last.y() - first.y() + 1) + 1);
   }
   protected double calcHeightInBlocks(ICoord first, ICoord last){
      return 4*(last.y() - first.y()+1) + 1*(last.x() - first.x() + 1) + 1;
   }
}
