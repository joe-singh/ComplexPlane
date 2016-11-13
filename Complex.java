package mandy;

/**
 * Complex number class to generate Mandelbrot set.
 * @author Jyotirmai Singh
 */
public class Complex {

    /** Real component of the complex number. */
    private double _re;
    /** Imaginary component of the complex number. */
    private double _im;
    /** Max number of iterations for Mandelbrot generator. */
    static final int MAX_ITER = 10000;

    public Complex(double re, double im) {
        _re = re;
        _im = im;
    }

    public Complex(double re) {
        _re = re;
        _im = 0;
    }

    @Override
    public String toString() {
        if (_im > 0) {
            return "" + _re + " + " + _im + "i";
        } else if (_im == 0) {
            return "" + _re;
        }
        else {
            return "" + _re + " - " + Math.abs(_im) + "i";
        }

    }

    /** The real component of the complex number. */
    public double Re() {
        return _re;
    }

    /** The imaginary component of the complex number. */
    public double Im() {
        return _im;
    }

    /** Addition on two complex numbers.
     * @param other the number to which this complex number
     *              is added.
     * @return sum of the two complex numbers. */
    public Complex add(Complex other) {
        return new Complex(Re() + other.Re(), Im() + other.Im());
    }

    /** Multiplication on two complex numbers.
     * @param other the number to which this complex number
     *              is multiplied.
     * @return product of the two complex numbers. */
    public Complex mul(Complex other) {
        double a = Re();
        double b = Im();
        double c = other.Re();
        double d = other.Im();

        return new Complex(a*c - b*d, a*d + b*c);
    }

    /** Scales the complex number by factor num.
     * @param num scaling factor
     * @return the scaled complex number.*/
    public Complex scale(double num) {
        return new Complex(Re()*num, Im()*num);
    }

    /** The conjugate of the complex number.
     *  @return z* */
    public Complex conjugate() {
        return new Complex(Re(), -Im());
    }

    /** Utility method to return the square of the complex number.
     *  @return z^2 */
    public Complex square() {
        return new Complex(_re * _re - _im * _im, 2 * _re * _im);
    }

    /** Division on two complex numbers.
     * @param other the number by which this complex number
     *              is divided.
     * @return quotient of the two complex numbers. */
    public Complex div(Complex other) {
        double c = other.Re();
        double d = other.Im();
        return (this.mul(other.conjugate())).scale(1/(c*c + d*d));
    }

    /** Modulus of complex number.
     *  @return modulus of z */
    public double modulus() {
        return Math.sqrt(_re * _re + _im * _im);
    }

    /** Argument of complex number.
     *  @return arg(z) */
    public double arg() {
        if (_re == 0) {
            if (_im > 0) {
                return Math.PI/2;
            } else if (_im < 0) {
                return -Math.PI/2;
            } else {
                return 0xFFFFFF;
            }
        } else if (_re > 0) {
            return Math.atan(_im/_re);
        } else if (_re < 0 && _im >= 0) {
            return Math.atan(_im/_re) + Math.PI;
        } else if (_re < 0 && _im < 0) {
            return Math.atan(_im/_re) - Math.PI;
        }
        throw new IllegalArgumentException("Illegal complex number");
    }

    /** Checks if a number z is in the mandelbrot set. Assumes that
     *  if, after 10000 iterations the result is within the disk of
     *  radius 2 in the complex plane, then it is in the mandelbrot
     *  set.
     *  @param z complex number to be tested
     *  @return the number of iterations before the number goes
     *          out of bounds, or 10000 if it stays inbounds. */

    static int mandelbrot(Complex z) {
        Complex c = new Complex(0);
        for (int i = 0; i < MAX_ITER; i++) {
            if (c.modulus() >= 2) {
                return i;
            }
            c = (c.square()).add(z);
        }
        return MAX_ITER;
    }

}
