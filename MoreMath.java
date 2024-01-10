import java.util.ArrayList;

class MoreMath {

  public MoreMath() {
    System.out.println();
  }

  // PRECONDITION: input is greater than 0
  public static ArrayList<Integer> primeFactors(int num) { 
    ArrayList<Integer> primeFactors = new ArrayList<Integer>();
    int currentNum = num;
    while (currentNum % 2 == 0) { // take out all twos, iterate half as many times
      currentNum /= 2;
      primeFactors.add(2);
    }
    int check = 3; // twos already checked, start from bottom, always hit primes
    while (check * check <= currentNum) {
      if (currentNum % check == 0) { // divisibility check
        currentNum /= check;
        primeFactors.add(check);
      } else {
        check += 2; // always two for twin primes
      }
    }
    if (currentNum != 1) { // either it'll end on a 1 or an uncounted prime number
      primeFactors.add(currentNum);
    }

    System.out.println(num + ": " + primeFactors);
    return primeFactors;
  }

  public static boolean isPrime(int num) {
    if (num % 2 == 0 || num <= 0 || num == 1) // count by twos first
      return false;
    for (int i = 3; check * check <= currentNum; i += 2) {
      if (num % i == 0)
        return false;
    }
    return true;
  }

  public static String base10ToNConverter(int baseTenNum, int newBase){
        // change of base formula, number of digits in new number
        int newNumDigits = ((int) (Math.log10(baseTenNum) / Math.log10(newBase))) + 1; 
        // write new number as string for letters
        String baseN = "";
        int placeValueAti = 0;
        // invalid inputs, error messages
        if (baseTenNum == -1){
          System.out.println("Invalid Input");
          baseN = "0";
        } else {
          for (int i = newNumDigits; i >= 1; i--){
            // figures out the place value at any value in base ten
            placeValueAti = (baseTenNum / (int) Math.pow(newBase, i - 1)) % newBase;
            // writes base ten value in single digit base N value
            if (placeValueAti > 9)
              baseN += String.valueOf((char) (placeValueAti - 10 + 65));
            else
              baseN += String.valueOf(placeValueAti);
          }
        } 
        return baseN;
      }
    
      public static int baseNTo10Converter(String baseNNumB, int oldBase){
        String baseNNum = baseNNumB.toUpperCase();
        int baseTenNum = 0;
        // ascii table values and letters
        int charValueOfi = 0;
        // only bases 2 and above
        for (int i = 0; i < baseNNum.length(); i++){
          if (oldBase <= 1){
            baseTenNum = -1;
            break;
          }
          // getting char value
          charValueOfi = (int) baseNNum.charAt(i);
          // below 9 place values
          if ((charValueOfi >= 48) && (charValueOfi <= 57)){
            if ((charValueOfi - 48) >= oldBase){
              baseTenNum = -1;
              break;
            }
            // convert to regular
            baseTenNum += Math.pow(oldBase, baseNNum.length() - i - 1) * (charValueOfi - 48);
            // above 9 place values
          } else if ((charValueOfi >= 65) && (charValueOfi <= 90)) {
            if ((charValueOfi - 65 + 10) >= oldBase){
              baseTenNum = -1;
              break;
            }
            baseTenNum += Math.pow(oldBase, baseNNum.length() - i - 1) * (charValueOfi - 65 + 10);
          }
        }
        return baseTenNum;
      }
    
      public static String baseNToNConverter(String baseNNum, int oldBase, int newBase){
        int baseTen = baseNTo10Converter(baseNNum, oldBase);
        return base10ToNConverter(baseTen, newBase);
      }
}