package fractals.logic.math;

public final class Complex {
	
	private final double real;
	
	private final double imaginary;
	
	public static final Complex ZERO = new Complex(0.0, 0.0);
	
	public static final Complex ONE = new Complex(1.0, 0.0);
	
	public static final Complex I = new Complex(0.0, 1.0);
	
	private Complex(double re, double im) {
		real = re;
		imaginary = im;
	}
	
	public static Complex ofCannonical(double re, double im) {
		return new Complex(re, im);
	}
	
	public static Complex ofReal(double real) {
		return new Complex(real, 0.0);
	}
	
	public static Complex ofImaginary(double imaginary) {
		return new Complex(0.0, imaginary);
	}
	
	public double absoluteValue() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}
	
	public Complex add(Complex c) {
		return new Complex(real + c.real, imaginary + c.imaginary);
	}
	
	public Complex multiply(Complex c) {
		return new Complex(this.real * c.real - this.imaginary * c.imaginary, this.imaginary * c.real + this.real * c.imaginary);
	}
	
	public Complex multiply(double x) {
		return new Complex(this.real * x, this.imaginary * x);
	}
	
	public Complex subtract(Complex c) {
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	public Complex square() {
		return this.multiply(this);
	}
	
	public double real() {
		return real;
	}
	
	public double imaginary() {
		return imaginary;
	}

	public String toString() {
		return real + " + " + imaginary + "i";
	}
	
	public Complex squareAndAdd(Complex c) {
		return new Complex(real * real - imaginary * imaginary + c.real, 2 * real * imaginary + c.imaginary);
	}
	
	public double absSquare() {
		return real * real + imaginary * imaginary;
	}
}
