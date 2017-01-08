package fractals.logic.math;

import java.awt.Point;

public class Utils {

	public static int dist2(Point p1, Point p2) {
		return (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
	}
	
	public static float interpolate(float v1, float v2, float distance) {
		float res = v1 * (1 - distance) + v2 * distance;
		return res;
	}
}
