
// Source code is decompiled from a .class file using FernFlower decompiler.
import java.math.BigInteger;
import java.util.BitSet;
import java.io.*;

public class prime {
   static BitSet primes;

   public prime() {
   }

   static boolean isPrime(int var0) {
      return var0 > 0 && (var0 == 2 || var0 % 2 != 0 && primes.get(var0 / 2));
   }

   static void generatePrimesUpTo(int var0) {
      primes = new BitSet(var0 / 2);

      int var1;
      for (var1 = 0; var1 < primes.size(); ++var1) {
         primes.set(var1, true);
      }

      primes.set(0, false);
      var1 = (int) Math.sqrt((double) var0) + 1;
      int var2 = 0;
      boolean var3 = false;
      System.out.println("generating primes...");
      long var4 = System.currentTimeMillis();

      for (int var6 = 0; var6 <= var1; ++var6) {
         int var9 = var2;
         var2 = (int) (((double) var6 + 1.0) / ((double) var1 / 100.0));
         if (var2 <= 100 && var2 != var9) {
            System.out.println("" + var2 + "%");
         }

         if (primes.get(var6)) {
            int var7 = var6 * 2 + 1;

            for (int var8 = var7 * 2; var8 < var0 && var8 >= 0; var8 += var7) {
               if (var8 % 2 != 0) {
                  // System.out.println(var8);
                  primes.set(var8 / 2, false);
                  return;
               }
            }
         }
      }

      long var10 = System.currentTimeMillis() - var4;
      System.out.println("finished generating primes ~" + var10 / 1000L + " seconds");
   }

   private static void test(int var0, int var1) {
      int var2 = 0;
      boolean var3 = false;
      long var4 = System.currentTimeMillis();
      System.out.println("testing isProbablePrime(" + var0 + ") from 1 to " + var1);

      for (int var6 = 1; var6 < var1; ++var6) {
         int var9 = var2;
         var2 = (int) (((double) var6 + 1.0) / ((double) var1 / 100.0));
         if (var2 <= 100 && var2 != var9) {
            System.out.println("" + var2 + "%");
         }

         BigInteger var7 = new BigInteger(String.valueOf(var6));
         boolean var8 = var7.isProbablePrime(var0);
         if (isPrime(var6) != var8) {
            System.out.println("ERROR: isProbablePrime(" + var0 + ") returns " + var8 + " for i=" + var6 + " while it "
                  + (isPrime(var6) ? "is" : "isn't") + " a prime");
            return;
         }
      }

      long var10 = System.currentTimeMillis() - var4;
      System.out.println("finished testing in ~" + var10 / 1000L / 60L
            + " minutes, no false positive or false negative found for isProbablePrime(" + var0 + ")");
   }

   public static void main(String[] args) {
      generatePrimesUpTo(Integer.MAX_VALUE);
      long begin = System.nanoTime();
      for (int i = 0; i < 10000; i++)
      {
         String a = "" + (isPrime(i));
      }
      System.out.println(System.nanoTime() - begin);
      begin = System.nanoTime();
      for (int i = 0; i < 10000; i++)
      {
         String a  = "" + MoreMath.isPrime(i);
      }
      System.out.println(System.nanoTime() - begin);
   

   }
   public static void printBitSetFileContent(String filename) {
      try (FileInputStream fis = new FileInputStream(filename);
           DataInputStream dis = new DataInputStream(fis)) {
  
          int bitSetSize = dis.readInt();
          System.out.println("Bit set size: " + bitSetSize);
  
          System.out.print("Bit set content: ");
          for (int i = 0; i < bitSetSize; i += 8) {
              byte byteValue = dis.readByte();
              for (int j = 7; j >= 0 && i + j < bitSetSize; j--) {
                  System.out.print((byteValue >> j) & 1);
              }
          }
          System.out.println();
  
      } catch (IOException e) {
          System.err.println("Error reading from file: " + e.getMessage());
      }
  }

   // Retrieve the bit at the specified index from the file
   public static boolean checkPrime(int bitIndex) {
      String filename = "output.txt";
      try (FileInputStream fis = new FileInputStream(filename);
             DataInputStream dis = new DataInputStream(fis)) {

            int bitSetSize = dis.readInt();
            int byteIndex = bitIndex / 8;
            int bitOffset = 7 - (bitIndex % 8);

            if (byteIndex >= bitSetSize / 8) {
                System.err.println("Invalid bit index.");
                return false;
            }

            fis.skip(4 + byteIndex); // Skip 4 bytes for the integer representing bit set size

            byte byteValue = dis.readByte();
            return (byteValue & (1 << bitOffset)) != 0;

        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return false;
        }
    }

    public static void writeBitSetToFile(BitSet bitSet, String filename) {
      try (FileOutputStream fos = new FileOutputStream(filename);
           DataOutputStream dos = new DataOutputStream(fos)) {
  
          int bitSetSize = bitSet.size();
          dos.writeInt(bitSetSize);
  
          for (int i = 0; i < bitSetSize; i += 8) {
              byte value = 0;
              for (int j = 0; j < 8 && i + j < bitSetSize; j++) {
                  if (bitSet.get(i + j)) {
                      value |= (1 << (7 - (i + j) % 8)); // Set the j-th bit from the left
                  }
              }
              dos.writeByte(value);
          }
  
      } catch (IOException e) {
          System.err.println("Error writing to file: " + e.getMessage());
      }
  }
  

   public static String arrayToNumber(long[] longArray) {
      // Determine the number of digits required to represent the maximum value of a
      // long
      int maxDigits = 19; // Maximum number of digits in a long

      // Create a string of zeros with the same length
      String zeros = "0".repeat(maxDigits);

      StringBuilder numberBuilder = new StringBuilder();
      for (long num : longArray) {
         if (num == -1L) {
            // If element is -1, it represents a sequence of zeros
            numberBuilder.append(zeros);
         } else {
            // Otherwise, add the element to the string after padding with leading zeros
            String numString = String.format("%0" + maxDigits + "d", num);
            numberBuilder.append(numString);
         }
      }
      return numberBuilder.toString();
   }
}
