import java.util.Arrays;

public class Main{
    public static void main(String[] args){
        long begin = System.nanoTime();

        System.out.println(Arrays.toString(MoreMath.expand(new double[] {2,3,4},3)));
        Polynomial polynomial = new Polynomial("0.01x + 0.01x^2 + 0");
        System.out.println(polynomial.expandBy(2));
        System.out.println(polynomial.evaluate(1));
        Graph thing = new Graph(polynomial, 20);
        System.out.println("\n" + (System.nanoTime() - begin));
    }
}