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

  public double evaluate(double x){
    return MoreMath.evaluatePolynomial(this.polyArr, x);
  }

  public Polynomial expandBy(int exponent) {
    return new Polynomial(MoreMath.coefficientsToPolynomial(MoreMath.expand(this.polyArr, exponent)));
  }
}
