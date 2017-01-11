package fractals.newlogic.math;

public interface Complex<T extends Number, C extends Complex<T, C>> {

	T absoluteValue();

	C add(C c);

	C multiply(C c);

	C multiply(Number x);

	C subtract(C c);

	C square();

	T real();

	T imaginary();

	C squareAndAdd(C c);

	T absSquare();

	C negate();

	C approximate(C other, double r, double i);
}
