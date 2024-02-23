import java.util.Arrays;

public class Main{
    public static void main(String[] args){
        long begin = System.nanoTime();

        // System.out.println(Arrays.toString(MoreMath.expand(new double[] {2,3,4},3)));
        // Polynomial polynomial = new Polynomial("0.01x + 0.01x^2 + 0");
        // System.out.println(polynomial.expandBy(2));
        // System.out.println(polynomial.evaluate(1));
        // Graph thing = new Graph(polynomial, 20);
        // System.out.println(MoreMath.isPrime2(0));
        for (int i = 0; i < 1000000; i++){

            System.out.println(MoreMath.isPrime(i));
        }
        System.out.println("\n" + (System.nanoTime() - begin));
       // System.out.println(1099511627775L >>> 64);
       // System.out.println(2 >> 1);
    }
}