import java.util.Arrays;

public class Main{
    public static void main(String[] args){
        long begin = System.nanoTime();

        System.out.println(Arrays.toString(MoreMath.expand(new double[] {2,3,4},3)));

        System.out.println(System.nanoTime() - begin);
    }
}