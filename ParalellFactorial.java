
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

// TODO: Auto-generated Javadoc
/**
* The Class ParalellFactorial.
*/
public class ParalellFactorial {

  /**
   * Adjusts a too long number to the size of the console.
   *
   * @param number     the number to be adjusted
   * @param characters the number of characters on which to break the lines
   */
  // Adjust the number when it's too long
  public static void adjustNumberToConsole(StringBuilder number, Integer characters) {
      final var length = number.length();
      if ( length > characters ) {
          int startIndex = 0, endIndex = characters;
          while ( startIndex < length ) {
              final var portion = new StringBuilder(number.substring(startIndex, endIndex));
              System.out.println(portion);
              startIndex += characters;
              endIndex += characters;

              if ( endIndex >= length ) {
                  endIndex = length;
              }
          }
      }
      else {
          System.out.println(number);
      }
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
      final var keyboard = new Scanner(System.in);
      BigInteger number;
      ParalellFactorial paralellFactorial;
      var error = false;
      System.out.println("FACTORIAL OF A NUMBER");
      do {
          System.out.println("Enter a positive integer:");
          try {
              number = keyboard.nextBigInteger();
              paralellFactorial = new ParalellFactorial();
              final var startTime = System.nanoTime();
              final var result = paralellFactorial.factorial(number);
              final var endTime = System.nanoTime();
              error = false;
              System.out.println("Factorial of " + number + ":");
              final var numberSb = new StringBuilder(result.toString());
              adjustNumberToConsole(numberSb, 80);
              System.out.println("Total execution time: " + (endTime - startTime) + " nanoseconds");
              System.out.println("Number of digits: " + numberSb.length());
          }
          catch ( InputMismatchException | IllegalArgumentException e ) {
              error = true;
              keyboard.nextLine();
          }
      }
      while ( error );
      keyboard.close();
  }

  /**
   * Factorial.
   *
   * @param n the number of which we want to calculate the factorial
   *
   * @return the factorial of the number
   */
  public BigInteger factorial(BigInteger n) {
      if ( n == null ) { throw new IllegalArgumentException("The argument cannot be null"); }
      if ( n.signum() == - 1 ) {
          // negative
          throw new IllegalArgumentException("Argument must be a non-negative integer");
      }
      BigInteger result;
      // For small input, iterative is faster
      if ( n.compareTo(new BigInteger("9495")) <= 0 ) {
          result = factorialIterative(n);
      }
      else {
          // Stream is faster
          result = Stream.iterate(BigInteger.TWO, bi -> bi.compareTo(n) <= 0, bi -> bi.add(BigInteger.ONE)).parallel()
                  .reduce(BigInteger.ONE, BigInteger::multiply);
      }
      return result;
  }

  /**
   * Factorial iterative.
   *
   * @param n the number of which we want to calculate the factorial
   *
   * @return the factorial of the number
   */
  private BigInteger factorialIterative(BigInteger n) {
      if ( n == null ) { throw new IllegalArgumentException(); }
      if ( n.signum() == - 1 ) {
          // negative
          throw new IllegalArgumentException("Argument must be a non-negative integer");
      }
      if ( n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE) ) { return BigInteger.ONE; }
      var factorial = BigInteger.ONE;
      for ( var i = new BigInteger("2"); i.compareTo(n) < 1; i = i.add(BigInteger.ONE) ) {
          factorial = factorial.multiply(i);
      }
      return factorial;
  }

}