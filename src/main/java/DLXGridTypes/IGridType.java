package DLXGridTypes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import DLXPentominoesSolverPack.Board;
import DLXPentominoesSolverPack.ICoord;

public interface IGridType
{

   void reset();

   public boolean is3D();
   int getNumRotate();
   int getNumReflect();
   boolean hasMirror();
   ICoord[] rotate(ICoord[] t);
   ICoord[] reflect(ICoord[] t);
   int getNumTileTypes();
   String getDisplayName();
   
   // rotate/reflect a set of coordinates
   ICoord[] getRotate(ICoord[] t, int j);

   // draw tiling illustration
   void paintIcon(Graphics g, int x,int y, int w, int h);

   // Paint whole board with given colours. If showVoid true then include a void border.
   void paintComponent(Component gridEditPanel, Graphics g, Board board, Color[] colList, boolean showVoid, Color edgeDarkColor, Color edgeLightColor, Color fillColor, Color bgColor );
   // paint outline of given tile with x,y = centre of part with index floathandle 
   void paintCentredOutline(Graphics g, ICoord[] floatShape, int x, int y, int floatHandle);
   // Outline a region of the board
   void paintOutline(Graphics g, ICoord selectEndCoord, ICoord selectEndCoord2);
   // convert screen coord x,y to board coordinate coord.
   ICoord screen2Grid(int x, int y);

   // get coordinate adjacent to coord
   ICoord getAdjacent(ICoord coord, int j);

   // get the list of orbits the tiles belong to
   int[] getTileOrbits();
   int getNumTileOrbits();
}