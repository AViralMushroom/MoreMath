public class Complex {
    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }
    public static double fastAtan2(double y, double x) {
        final double ONEQTR_PI = Math.PI / 4.0;
        final double THRQTR_PI = 3.0 * Math.PI / 4.0;
        double r, angle;
        double abs_y = Math.abs(y) + 1e-10; // kludge to prevent 0/0 condition
        if (x < 0.0) {
            r = (x + abs_y) / (abs_y - x);
            angle = THRQTR_PI;
        } else {
            r = (x - abs_y) / (x + abs_y);
            angle = ONEQTR_PI;
        }
        angle += (0.1963 * r * r - 0.9817) * r;
        if (y < 0.0)
            return -angle; // negate if in quad III or IV
        else
            return angle;
    }
    public static double pow(final double a, final double b) {
        final long tmp = Double.doubleToLongBits(a);
        final long tmp2 = (long)(b * (tmp - 4606921280493453312L)) + 4606921280493453312L;
        return Double.longBitsToDouble(tmp2);
    }     
    public static double log(double x) {
        return 6 * (x - 1) / (x + 1 + 4 * (Math.sqrt(x)));
    }
    
    public static double exp(double val) {
        final long tmp = (long) (1512775 * val + 1072632447);
        return Double.longBitsToDouble(tmp << 32);
    }    

    public double re() {
        return re;
    }

    public double im() {
        return im;
    }

    public Complex times(Complex other) {
        double real = this.re * other.re() - this.im * other.im();
        double imag = this.re * other.im() + this.im * other.re();
        return new Complex(real, imag);
    }
    public Complex plus(Complex other) {
        double real = this.re + other.re();
        double imag = this.im + other.im();
        return new Complex(real, imag);
    }

    public Complex minus(Complex other) {
        double real = this.re - other.re();
        double imag = this.im - other.im();
        return new Complex(real, imag);
    }
    public String toString() {
        if (im < 0) {
            return re + " - " + Math.abs(im) + "i";
        } else {
            return re + " + " + im + "i";
        }
    }
    public Complex conjugate() {
        return new Complex(re, -im);
    }
    public Complex scale(double factor) {
        return new Complex(re * factor, im * factor);
    }
    public Complex pow(double power) {
        double magnitude = pow(Math.sqrt(re * re + im * im), power);
        double argument = fastAtan2(im, re) * power;
        return new Complex(magnitude * Math.cos(argument), magnitude * Math.sin(argument));
    }
    public static Complex[] generateComplexArray(double[] real) {
        Complex[] complexArray = new Complex[real.length];
        for (int i = 0; i < real.length; i++) {
            complexArray[i] = new Complex(real[i], 0); // Imaginary part is 0
        }
        return complexArray;
    }
    public static double[] extractRealParts(Complex[] complexArray) {
        double[] realParts = new double[complexArray.length];
        for (int i = 0; i < complexArray.length; i++) {
            realParts[i] = complexArray[i].re;
        }
        return realParts;
    }
}
