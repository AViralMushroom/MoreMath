import java.util.ArrayList;
import java.util.Arrays;

class MoreMath {


  public MoreMath() {
    
  }


  /**
   * COMBINATORICS
   **/
  public static int fallingPower(int n, int r) {
    int output = 1;
    for (int i = 0; i < r; i++) {
      output *= (n - i);
    }
    return output;
  }

  public static int nCr(int n, int r) {
    return fallingPower(n, r) / fallingPower(r, r);
  }

  public static double[] convolute(double[] poly1, double[] poly2) {
    double[] outputCoefficients = new double[poly1.length + poly2.length - 1];
    for (int i = 0; i < poly1.length; i++) {
      for (int j = 0; j < poly2.length; j++) {
        outputCoefficients[i + j] += poly1[i] * poly2[j];
      }
    }
    return outputCoefficients;
  }

  /**
   * Polynomial Expansions
   **/

  // PRECONDITION: coefficients array only contains two elements
  public static double[] expandBinomial(double[] coefficients, int exponent) {
    double[] outputCoefficients = new double[exponent + 1];
    for (int i = 0; i < exponent + 1; i++) {
      outputCoefficients[i] = (nCr(exponent, i) *
          Math.pow(coefficients[0], exponent - i) *
          Math.pow(coefficients[1], i));
    }
    return outputCoefficients;
  }

  public static double[] expand(double[] coefficients, int exponent) {
    if (exponent == 1)
      return coefficients;
    double[] outputCoefficients = convolute(coefficients, coefficients);
    for (int i = 0; i < exponent - 2; i++) {
      outputCoefficients = convolute(coefficients, outputCoefficients);
    }
    return outputCoefficients;
  }

  /**
   * STANDARD FUNCTIONS
   **/
  public static int factorial(int num) {
    if (num < 0)
      throw new ArithmeticException("No negative integer factorials");
    int factorial = 1;
    for (int i = num; i > 1; i--) {
      factorial *= i;
    }
    return factorial;
  }

  public static double factorial(double num) {
    double factorialCalculationNum = num - ((int) num) + ((num < 0) ? 1 : 0);
    long accuracyVal = Integer.MAX_VALUE / 2;
    double result = 1;
    for (long i = accuracyVal; i > 0; i--) {
      result *= i / (factorialCalculationNum + i);
      if (i == accuracyVal / 1.5) {
        result *= Math.pow(accuracyVal, factorialCalculationNum);
      }
    }
    while ((num >= 1) && (factorialCalculationNum != num)) {
      factorialCalculationNum += 1;
      result *= factorialCalculationNum;
    }
    while ((num < 0) && (factorialCalculationNum != num)) {
      result /= factorialCalculationNum;
      factorialCalculationNum -= 1;
    }
    return result;
  }

  public static int oneToNSum(int num) {
    return (num * num + num) / 2;
  }

  public static double oneToNSum(double num) {
    return (num * num + num) / 2;
  }

  /**
   * PRIME NUMBERS
   **/
  // PRECONDITION: input is greater than 0
  public static ArrayList<Integer> primeFactors(int num) {
    ArrayList<Integer> primeFactors = new ArrayList<Integer>();
    int currentNum = num;
    while (currentNum % 2 == 0) { // take out all twos, iterate half as many times
      currentNum /= 2;
      primeFactors.add(2);
    }
    while (currentNum % 3 == 0) {
      currentNum /= 3;
      primeFactors.add(3);
    }
    int check = 5; // twos and threes already checked, start from bottom, always hit primes
    while (check * check <= currentNum) {
      if (currentNum % check == 0) { // divisibility check
        currentNum /= check;
        primeFactors.add(check);
      } else
        check += 2 + (((check - 1) % 6 == 0) ? 2 : 0); // always two for twin primes, 6n + 3 never prime
    }

    if (currentNum != 1) { // either it'll end on a 1 or an uncounted prime number
      primeFactors.add(currentNum);
    }
    // Collections.sort(primeFactors);
    System.out.println(num + ": " + primeFactors);
    return primeFactors;
  }

  public static boolean isPrime(int num) {
    if (num == 2 || num == 3) {
      return true;
    }
    if ((((num + 1) % 6 != 0) && ((num - 1) % 6 != 0)) || num <= 1) {
      return false;
    }
    int check = 5;
    while (check * check <= num) {
      if (num % check == 0) { // divisibility check
        return false;
      } else
        check += 2 + (((check - 1) % 6 == 0) ? 2 : 0); // always two for twin primes, 6n + 3 never prime
    }
    return true;
  }

  /**
   * BASE NUMBER SYSTEMS
   **/
  public static String decimalToBinary(int decimal) {
    return Integer.toBinaryString(decimal);
  }

  public static int binaryToDecimal(String binary) {
    int decimal = 0;
    for (int i = 0; i < binary.length(); i++) {
      decimal += (binary.substring(binary.length() - 1 - i, binary.length() - i).equals("0")) ? 0 : Math.pow(2, i);
    }
    return decimal;
  }

  public static String base10ToNConverter(int baseTenNum, int newBase) {
    // change of base formula, number of digits in new number
    int newNumDigits = ((int) (Math.log10(baseTenNum) / Math.log10(newBase))) + 1;
    // write new number as string for letters
    String baseN = "";
    int placeValueAti = 0;
    // invalid inputs, error messages
    for (int i = newNumDigits; i >= 1; i--) {
      // figures out the place value at any value in base ten
      placeValueAti = (baseTenNum / (int) Math.pow(newBase, i - 1)) % newBase;
      // writes base ten value in single digit base N value
      if (placeValueAti > 9)
        baseN += String.valueOf((char) (placeValueAti - 10 + 65));
      else
        baseN += String.valueOf(placeValueAti);
    }
    return baseN;
  }

  public static int baseNTo10Converter(String baseNNumB, int oldBase) {
    String baseNNum = baseNNumB.toUpperCase();
    int baseTenNum = 0;
    // ascii table values and letters
    int charValueOfi = 0;
    // only bases 2 and above
    for (int i = 0; i < baseNNum.length(); i++) {
      if (oldBase <= 1) {
        baseTenNum = -1;
        break;
      }
      // getting char value
      charValueOfi = (int) baseNNum.charAt(i);
      // below 9 place values
      if ((charValueOfi >= 48) && (charValueOfi <= 57)) {
        if ((charValueOfi - 48) >= oldBase) {
          baseTenNum = -1;
          break;
        }
        // convert to regular
        baseTenNum += Math.pow(oldBase, baseNNum.length() - i - 1) * (charValueOfi - 48);
        // above 9 place values
      } else if ((charValueOfi >= 65) && (charValueOfi <= 90)) {
        if ((charValueOfi - 65 + 10) >= oldBase) {
          baseTenNum = -1;
          break;
        }
        baseTenNum += Math.pow(oldBase, baseNNum.length() - i - 1) * (charValueOfi - 65 + 10);
      }
    }
    return baseTenNum;
  }

  public static String baseNToNConverter(String baseNNum, int oldBase, int newBase) {
    int baseTen = baseNTo10Converter(baseNNum, oldBase);
    return base10ToNConverter(baseTen, newBase);
  }
}