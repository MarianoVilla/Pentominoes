package Entities;

public class Stopwatch {
	  private long startTimeNano;
	  private long stopTimeNano;
	  private long startTimeMilli;
	  private long stopTimeMilli;

	  /**
	   starting the stop watch.
	  */
	  public void start(){
		  startTimeNano = System.nanoTime();
		  startTimeMilli = System.currentTimeMillis();
	  }

	  /**
	   stopping the stop watch.
	  */
	  public void stop(){
		  stopTimeNano = System.nanoTime();
		  stopTimeMilli = System.currentTimeMillis();
	  }

	  /**
	  elapsed time in nanoseconds.
	  */
	  public long elapsedInNano(){
	        return (stopTimeNano - startTimeNano);
	  }
	  public long elapsedInMilli() {
		  return (stopTimeMilli - startTimeMilli);
	  }

	  public String toString(){
	      return "elapsed time: " + elapsedInNano() + " nanoseconds.";
	  }

}
