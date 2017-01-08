package fractals.logic.coloring;

import java.awt.Color;

import fractals.logic.math.Utils;

public class LinearColorizer implements Colorizer {
	
	private Color[] colors;
	
	private int loopLength;
	
	private int colorCount;
	
	public LinearColorizer(int loopLength, Color... colors) {
		this.colors = colors;
		this.loopLength = loopLength;
		this.colorCount = colors.length;
	}
	
	private Color interpolate(Color c1, Color c2, float dist) {
		float red = Utils.interpolate(c1.getRed(), c2.getRed(), dist) / 256;
		float green = Utils.interpolate(c1.getGreen(), c2.getGreen(), dist) / 256;
		float blue = Utils.interpolate(c1.getBlue(), c2.getBlue(), dist) / 256;
		return new Color(red, green, blue);
	}

	@Override
	public Color getColorOf(int iterNumber, float lastVal) {
		return getColorOf(iterNumber);
	}

	@Override
	public Color getColorOf(int iterNumber) {
		
		int wholeLoop = loopLength * colorCount;
		
		int mod = iterNumber % wholeLoop;
		
		int colorNum = mod / loopLength;
		
		Color c1 = colors[colorNum];
		Color c2 = colors[colorNum + 1 != colorCount ? colorNum + 1 : 0];
		float dist = (mod % loopLength) / (float)loopLength;
		
		return interpolate(c1, c2, dist);
	}

	@Override
	public Color getColorOfLastVal(Color iterColor, Color iterNextColor,
			float lastVal) {
		return iterColor;
	}

}
