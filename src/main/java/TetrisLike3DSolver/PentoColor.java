package TetrisLike3DSolver;

import java.awt.Color;

/**
 * To avoid depending on color frameworks, and specially to avoid spreading color conversion logic all around, we have this class.
 * It wraps AWT and FX colors and overrides toString to get a descriptive name.
 */
public class PentoColor {

	private java.awt.Color awtColor;
	private javafx.scene.paint.Color fxColor;
	
	/**
	 * Strict implementation constructor.
	 * @param awtColor
	 * @param fxColor
	 */
	public PentoColor(java.awt.Color awtColor, javafx.scene.paint.Color fxColor) {
		this.awtColor = awtColor;
		this.fxColor = fxColor;
	}
	/**
	 * RGBA constructor.
	 * @param red
	 * @param green
	 * @param blue
	 * @param opacity
	 */
	public PentoColor(float red, float green, float blue, float opacity) {
		this.fxColor = new javafx.scene.paint.Color(red, green, blue, opacity);
		this.awtColor = new java.awt.Color(red, green, blue, opacity);
	}
	
	@Override
	public String toString() {
		String myColor = null;
		if(this.awtColor.equals(Color.BLUE)) {
			myColor = "Blue";
		}
		if(this.awtColor.equals(Color.RED)) {
			myColor = "Red";
		}
		if (this.awtColor.equals(Color.GREEN)){
			myColor = "Green";
		}
		if(this.awtColor.equals(Color.PINK)) {
			myColor = "Pink";
		}
		if(this.awtColor.equals(Color.YELLOW)) {
			myColor = "Yellow";
		}
		if(this.awtColor.equals(Color.BLACK)) {
			myColor = "Black";
		}
		if(this.awtColor.equals(Color.CYAN)) {
			myColor = "Cyan";
		}
		if(this.awtColor.equals(Color.ORANGE)) {
			myColor = "Orange";
		}
		return myColor == null ? super.toString() : myColor;
	}

	public java.awt.Color getAwtColor() {
		return awtColor;
	}

	public void setAwtColor(java.awt.Color awtColor) {
		this.awtColor = awtColor;
	}

	public javafx.scene.paint.Color getFxColor() {
		return fxColor;
	}

	public void setFxColor(javafx.scene.paint.Color fxColor) {
		this.fxColor = fxColor;
	}
	

}
