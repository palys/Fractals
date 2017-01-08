package fractals.logic.coloring;

import java.awt.Color;
import java.util.TreeMap;

public class BufferedColorizer implements Colorizer {

	private final Colorizer colorizer;

	private final TreeMap<Integer, Color> bufferedColors;

	public BufferedColorizer(Colorizer colorizer) {
		this.colorizer = colorizer;
		this.bufferedColors = new TreeMap<>();
	}

	private void generateColorsTo(int maxIters) {
		int maxKey = 0;
		if (bufferedColors.size() > 0) {
			maxKey = bufferedColors.lastKey();
		}

		for (int i = maxKey + 1; i <= maxIters; i++) {
			bufferedColors.put(i, colorizer.getColorOf(i));
		}
	}

	@Override
	public Color getColorOf(int iterNumber, float lastVal) {
		final Color c1 = getColorOf(iterNumber);
		final Color c2 = getColorOf(iterNumber + 1);
		final Color finalColor = getColorOfLastVal(c1, c2, lastVal);
		return finalColor;
	}

	@Override
	public Color getColorOf(int iterNumber) {
		if (!bufferedColors.containsKey(iterNumber)) {
			generateColorsTo(iterNumber);
		}

		return bufferedColors.get(iterNumber);
	}

	@Override
	public Color getColorOfLastVal(Color iterColor, Color iterNextColor, float lastVal) {
		return colorizer.getColorOfLastVal(iterColor, iterNextColor, lastVal);
	}

}
