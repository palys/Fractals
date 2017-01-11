package fractals.newlogic.math;

public class FloatComplex implements Complex<Float, FloatComplex> {

	public static final FloatComplex ZERO = new FloatComplex(0, 0);

	public static final FloatComplex ONE = new FloatComplex(1, 0);

	public static final FloatComplex I = new FloatComplex(0, 1);

	private final float re;

	private final float im;

	private FloatComplex(float re, float im) {
		this.re = re;
		this.im = im;
	}

	public static FloatComplex ofCannonical(float re, float im) {
		return new FloatComplex(re, im);
	}

	public static FloatComplex ofReal(float real) {
		return new FloatComplex(real, 0);
	}

	public static FloatComplex ofImaginary(float imaginary) {
		return new FloatComplex(0, imaginary);
	}

	@Override
	public Float absoluteValue() {
		return (float) Math.sqrt(re * re + im * im);
	}

	@Override
	public FloatComplex add(FloatComplex c) {
		return new FloatComplex(re + c.re, im + c.im);
	}

	@Override
	public FloatComplex multiply(FloatComplex c) {
		return new FloatComplex(re * c.re - im * c.im, im * c.re + re * c.im);
	}

	@Override
	public FloatComplex multiply(Number x) {
		return new FloatComplex(re * x.floatValue(), im * x.floatValue());
	}

	@Override
	public FloatComplex subtract(FloatComplex c) {
		return new FloatComplex(re - c.re, im - c.im);
	}

	@Override
	public FloatComplex square() {
		return new FloatComplex(re * re - im * im, 2 * re * im);
	}

	@Override
	public Float real() {
		return re;
	}

	@Override
	public Float imaginary() {
		return im;
	}

	@Override
	public FloatComplex squareAndAdd(FloatComplex c) {
		return new FloatComplex(re * re - im * im + c.re, 2 * re * im + c.im);
	}

	@Override
	public Float absSquare() {
		return re * re + im * im;
	}

	@Override
	public FloatComplex negate() {
		return new FloatComplex(-re, -im);
	}

	@Override
	public FloatComplex approximate(FloatComplex other, double r, double i) {
		final double x = (other.re - re) * r + re;
		final double y = (other.im - im) * i + im;
		return new FloatComplex((float) x, (float) y);
	}

}
