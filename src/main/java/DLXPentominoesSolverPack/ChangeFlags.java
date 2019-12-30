package DLXPentominoesSolverPack;


public class ChangeFlags implements IChangeFlag {
	   private boolean wasSaved = true;
	   private boolean isValidated = true;
	   
	   public void setChange(){
	      setChangeWithoutInvalidating();
	      isValidated = false;
	   }
	   public void setChangeWithoutInvalidating(){
	      if( wasSaved ){
	         wasSaved = false;
	         //TODO: remove dependency.
	         //PolySolver.updateTitle();         
	      }
	   }
	   public boolean isValid(){ return isValidated; }
	   public boolean isSaved(){ return wasSaved; }
	   public void setValid(){ isValidated = true; }
	   public void setSaved(){ wasSaved = true; }
	}
