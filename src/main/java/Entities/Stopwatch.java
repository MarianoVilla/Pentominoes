package Entities;
/**
 * Helper stopwatch class to make algorithm time measuring easier.
 */
public class Stopwatch {
	  private long startTimeNano;
	  private long stopTimeNano;
	  private long startTimeMilli;
	  private long stopTimeMilli;

	  /**
	   * Starting the stopwatch.
	   */
	  public void start(){
		  startTimeNano = System.nanoTime();
		  startTimeMilli = System.currentTimeMillis();
	  }

	  /**
	   * Stopping the stop watch. 
	   */
	  public void stop(){
		  stopTimeNano = System.nanoTime();
		  stopTimeMilli = System.currentTimeMillis();
	  }

	  /**
	   * @return Elapsed time in nanoseconds.
	   */
	  public long elapsedInNano(){
	        return (stopTimeNano - startTimeNano);
	  }
	  /**
	   * @return Elapsed time in milliseconds.
	   */
	  public long elapsedInMilli() {
		  return (stopTimeMilli - startTimeMilli);
	  }
	  /**
	   * Uses the elapsed in nanoseconds. To get a milliseconds version, use toStringMilli() 
	   */
	  public String toString(){
	      return "Elapsed time: " + elapsedInNano() + " nanoseconds.";
	  }
	  public String toStringMilli() {
		  return "Elapsed time: " + elapsedInMilli() + " milliseconds.";
	  }

}
