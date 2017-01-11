package fractals.newlogic.math;

import java.math.BigDecimal;

public class BigDecimalComplex implements Complex<BigDecimal, BigDecimalComplex> {

	public static final BigDecimalComplex ZERO = new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.ZERO);

	public static final BigDecimalComplex ONE = new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO);

	public static final BigDecimalComplex I = new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.ONE);

	private final BigDecimal re;

	private final BigDecimal im;

	private final BigDecimal squaredReal;

	private final BigDecimal squaredImaginary;

	private BigDecimalComplex(BigDecimal re, BigDecimal im) {
		this.re = re;
		this.im = im;
		this.squaredReal = re.multiply(re);
		this.squaredImaginary = im.multiply(im);
	}

	public static BigDecimalComplex ofCannonical(BigDecimal re, BigDecimal im) {
		return new BigDecimalComplex(re, im);
	}

	public static BigDecimalComplex ofReal(BigDecimal real) {
		return new BigDecimalComplex(real, BigDecimal.ZERO);
	}

	public static BigDecimalComplex ofImaginary(BigDecimal imaginary) {
		return new BigDecimalComplex(BigDecimal.ZERO, imaginary);
	}

	@Override
	public BigDecimal absoluteValue() {
		return BigDecimalMath.sqrt(squaredReal.add(squaredImaginary));
	}

	@Override
	public BigDecimalComplex add(BigDecimalComplex c) {
		return new BigDecimalComplex(re.add(c.re), im.add(c.im));
	}

	@Override
	public BigDecimalComplex multiply(BigDecimalComplex c) {
		return new BigDecimalComplex(re.multiply(c.re).subtract(im.multiply(c.im)),
				im.multiply(c.re).add(re.multiply(c.im)));
	}

	@Override
	public BigDecimalComplex multiply(Number x) {
		return new BigDecimalComplex(re.multiply(BigDecimal.valueOf(x.doubleValue())),
				im.multiply(BigDecimal.valueOf(x.doubleValue())));
	}

	@Override
	public BigDecimalComplex subtract(BigDecimalComplex c) {
		return new BigDecimalComplex(re.subtract(c.re), im.subtract(c.im));
	}

	@Override
	public BigDecimalComplex square() {
		return new BigDecimalComplex(squaredReal.subtract(squaredImaginary),
				re.multiply(im).multiply(BigDecimal.valueOf(2)));
	}

	@Override
	public BigDecimal real() {
		return re;
	}

	@Override
	public BigDecimal imaginary() {
		return im;
	}

	@Override
	public BigDecimalComplex squareAndAdd(BigDecimalComplex c) {
		return square().add(c);
	}

	@Override
	public BigDecimal absSquare() {
		return squaredImaginary.add(squaredReal);
	}

	@Override
	public BigDecimalComplex negate() {
		return new BigDecimalComplex(re.negate(), im.negate());
	}

	@Override
	public BigDecimalComplex approximate(BigDecimalComplex other, double r, double i) {
		final BigDecimal dr = other.re.subtract(re);
		final BigDecimal di = other.im.subtract(im);

		final BigDecimal rScale = BigDecimal.valueOf(r);
		final BigDecimal iScale = BigDecimal.valueOf(i);

		final BigDecimal x = dr.multiply(rScale).add(re);
		final BigDecimal y = di.multiply(iScale).add(im);

		return new BigDecimalComplex(x, y);
	}

}
