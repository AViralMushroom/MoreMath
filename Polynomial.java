public class Polynomial {
  String polynomial;
  double[] polyArr;

  public Polynomial(String polynomial) {
    this.polynomial = polynomial.replaceAll("\\s", ""); // Remove all spaces
    // Split the polynomial by '+' and trim spaces
    String[] terms = this.polynomial.split("\\+");
    // Initialize polyArr with the appropriate size
    int maxPower = 0;
    for (String term : terms) {
      int power = 0;
      if (term.contains("^")) {
        power = Integer.parseInt(term.split("\\^")[1]);
      } else if (term.contains("x")) {
        power = 1;
      }
      maxPower = Math.max(maxPower, power);
    }
    this.polyArr = new double[maxPower + 1];

    // Loop through each term to extract coefficients
    for (String term : terms) {
      double coefficient = 0;
      if (term.contains("x")) {
        String[] parts = term.split("x");
        if (parts[0].isEmpty() || parts[0].equals("-")) {
          coefficient = parts[0].equals("-") ? -1 : 1;
        } else {
          coefficient = Double.parseDouble(parts[0]);
        }
      } else {
        coefficient = Double.parseDouble(term);
      }
      int power = 0;
      if (term.contains("^")) {
        power = Integer.parseInt(term.split("\\^")[1]);
      } else if (term.contains("x")) {
        power = 1;
      }
      this.polyArr[power] = coefficient;
    }
  }

  @Override
  public String toString() {
    return this.polynomial;
  }

  /**
   * COMBINATORICS
   **/
  // public static int fallingPower(int n, int r) {
  //   int output = 1;
  //   for (int i = 0; i < r; i++) {
  //     output *= (n - i);
  //   }
  //   return output;
  // }

  // public static int nCr(int n, int r) {
  //   return fallingPower(n, r) / fallingPower(r, r);
  // }

  // public static double[] convolute(double[] poly1, double[] poly2) {
  //   double[] outputCoefficients = new double[poly1.length + poly2.length - 1];
  //   for (int i = 0; i < poly1.length; i++) {
  //     for (int j = 0; j < poly2.length; j++) {
  //       outputCoefficients[i + j] += poly1[i] * poly2[j];
  //     }
  //   }
  //   return outputCoefficients;
  // }

  /**
   * Polynomial Expansions
   **/

  // PRECONDITION: coefficients array only contains two elements
  // public static double[] expandBinomial(double[] coefficients, int exponent) {
  //   double[] outputCoefficients = new double[exponent + 1];
  //   for (int i = 0; i < exponent + 1; i++) {
  //     outputCoefficients[i] = (nCr(exponent, i) *
  //         Math.pow(coefficients[0], exponent - i) *
  //         Math.pow(coefficients[1], i));
  //   }
  //   return outputCoefficients;
  // }

  // public static double[] expand(double[] coefficients, int exponent) {
  //   if (exponent == 1)
  //     return coefficients;
  //   double[] outputCoefficients = convolute(coefficients, coefficients);
  //   for (int i = 0; i < exponent - 2; i++) {
  //     outputCoefficients = convolute(coefficients, outputCoefficients);
  //   }
  //   return outputCoefficients;
  // }

  public Polynomial expandBy(int exponent) {
    return new Polynomial(MoreMath.coefficientsToPolynomial(MoreMath.expand(this.polyArr, exponent)));
  }
}
