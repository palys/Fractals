package fractals.logic.coloring;

import java.awt.Color;

public interface Colorizer {
	
	Color getColorOf(int iterNumber, float lastVal);
	
	Color getColorOf(int iterNumber);
	
	Color getColorOfLastVal(Color iterColor, Color iterNextColor, float lastVal);

}
