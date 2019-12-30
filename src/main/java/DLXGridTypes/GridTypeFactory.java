package DLXGridTypes;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class GridTypeFactory
{
   public static final Object[] gridTypesMenu = {
         "Grids",
         new Object[]{
               "2-fold symmetry",
               new GridTypeIsosceles(),
               new GridTypeRect(),
               new GridTypeBrick(),
               new GridTypeDiamond(),
               new GridTypeKite(),
               new GridTypeHeptagon(),
         },
         new Object[]{
               "4-fold symmetry",
               new GridTypeSquare(),
               new GridTypeTan(),
               new GridTypeOctagon(),
               new GridTypeOctagon2(),
               new GridTypeUnequalSquares(),
               new GridTypePentagon(),
               new GridTypePentagonHalf(),
               new GridType34334(),
               new GridType34334Split(),
               new GridTypeOctaPenta(),
               new GridTypeOctagonStar(),
               new GridTypeOctagonStar2(),
               new GridTypeRhombusStar(),
               new GridTypeSquareHexa(),
         },
         new Object[]{
               "6-fold symmetry",
               new GridTypeTriangle(),
               new GridTypeHexagon(),
               new GridTypeDrafter(),
               new GridTypeCubeTile(),
               new GridTypeNonagon(),
               new GridTypeNonagon2(),
               new GridTypeShiftedTriangles(),
               new GridType3464(),
               new GridType3464Split(),
               new GridTypeDodecagon(),
               new GridTypeShiftedHexagons(),
               new GridTypeTriHex(),
         },
         new Object[]{
               "3-dimensional",
               new GridTypeCube(),
         },
   };

   static public IGridType factory(String s){
      return factory(s,gridTypesMenu);
   }
   static private IGridType factory(String s, Object nodeData){
      if( nodeData instanceof IGridType ){
         IGridType gt = (IGridType)nodeData;
         if( gt.toString().equalsIgnoreCase(s))
            return factory(gt);
         return null;
      }
      Object[] submenu= (Object[])nodeData;
      for( int i=1; i<submenu.length; i++) {
         IGridType gt = factory(s, submenu[i]);
         if( gt!=null) return gt;
      }
      return null;
   }
   static public IGridType factory(IGridType gt){
      IGridType g=null;
      try{ g=gt.getClass().newInstance();} catch(Exception e){}
      return g;
   }
   
   static public TreeNode createTree(){
      TreeNode root = createNode(gridTypesMenu);
      return root;
   }
   static private MutableTreeNode createNode(Object nodeData){
      if( nodeData instanceof IGridType ){
         return new DefaultMutableTreeNode(nodeData) {
            public String toString(){
               return ((IGridType)getUserObject()).getDisplayName();
            }
         };
      }
      Object[] nodeList = (Object[])nodeData;
      DefaultMutableTreeNode node = new DefaultMutableTreeNode();
      node.setUserObject(nodeList[0]);
      for(int i=1; i<nodeList.length; i++){
         MutableTreeNode subnode = createNode(nodeList[i]);
         if( subnode!=null )node.add(subnode);
      }
      return node;
   }
}
