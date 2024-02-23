import java.util.Arrays;
import java.util.Random;

public class FFT{
    public static void main(String[] args){
        double[][] numbers = generateRandomArrays(2, 10000);
        long begin = System.nanoTime();
        for (int i = 0; i < 1; i++)
        {
            String s = (Arrays.toString(fftConvolve(numbers[i], numbers[1 - i])));
            // String a = (Arrays.toString(MoreMath.convolute(numbers[i], numbers[19999 - i])));
        }
        System.out.println(System.nanoTime() - begin);
        begin = System.nanoTime();
        for (int i = 0; i < 1; i++)
        {
            // String s = (Arrays.toString(fftConvolve(numbers[i], numbers[19999 - i])));
            String a = (Arrays.toString(MoreMath.convolute(numbers[i], numbers[1 - i])));
        }
        System.out.println(System.nanoTime() - begin); 
    }
    public static int nextPowerOfTwo(int n) {
        n--; // Ensure that if n is already a power of 2, the result will be n itself
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return ++n;
    }    
    public static double[][] generateRandomArrays(int numArrays, int arrayLength) {
        double[][] randomArrays = new double[numArrays][arrayLength];
        Random random = new Random();
        for (int i = 0; i < numArrays; i++) {
            for (int j = 0; j < arrayLength; j++) {
                randomArrays[i][j] = random.nextDouble();
            }
        }
        return randomArrays;
    }
    public static double[] fftConvolve(double[] signal1, double[] signal2) {
        int paddedLength = nextPowerOfTwo(signal1.length + signal2.length - 1);
        
        // Pad the signals to ensure equal length is 2^n
        double[] paddedSignal1 = padSignal(signal1, paddedLength);
        double[] paddedSignal2 = padSignal(signal2, paddedLength);

        // Perform FFT on padded signals
        Complex[] fftSignal1 = fft(Complex.generateComplexArray(paddedSignal1), paddedSignal1.length);
        Complex[] fftSignal2 = fft(Complex.generateComplexArray(paddedSignal2), paddedSignal2.length);
        // System.out.println(Arrays.toString(fftSignal1) + " " + Arrays.toString(fftSignal2));

        // Multiply FFT of signals element-wise
        Complex[] convolutionResult = new Complex[paddedLength];
        for (int i = 0; i < paddedLength; i++) {
            convolutionResult[i] = fftSignal1[i].times(fftSignal2[i]);
            // System.out.print("(" + fftSignal1[i] + ") * (" + fftSignal2[i] + ") = " + convolutionResult[i] + " | | \n");
        }
        System.out.println();
        // System.out.println(Arrays.toString(convolutionResult));

        // Perform inverse FFT on the result
        double[] convolvedSignalPadded = Complex.extractRealParts(inverseFFT(convolutionResult, paddedLength));
        double[] convolvedSignal = new double[signal1.length + signal2.length - 1];
        System.arraycopy(convolvedSignalPadded, 0, convolvedSignal, 0, convolvedSignal.length);
        // System.out.println(Arrays.toString(convolvedSignal));
        return convolvedSignal;
    }
    public static double[] padSignal(double[] signal, int paddedLength) {
        double[] paddedSignal = new double[paddedLength];
        System.arraycopy(signal, 0, paddedSignal, 0, signal.length);
        // System.out.println(Arrays.toString(paddedSignal));
        return paddedSignal;
    }

    public static Complex[] fft(Complex[] coef, int length)
    {
        if (length == 1){
            return coef;
        }
        Complex[] evenCoef = new Complex[length / 2];
        Complex[] oddCoef = new Complex[length / 2];
        
        // Separate even and odd coefficients
        for (int i = 0; i < length; i += 2) {
            evenCoef[i / 2] = coef[i];
            oddCoef[i / 2] = coef[i + 1]; 
        }

        Complex[] fftEvens = fft(evenCoef, evenCoef.length);
        Complex[] fftOdds = fft(oddCoef, oddCoef.length);
        Complex[] y = new Complex[length];
        for (int j = 0; j < length / 2; j++)
        {
            Complex omega = new Complex(Math.cos((2 * j * Math.PI) / length), Math.sin((2 * j * Math.PI) / length));
            y[j] = fftEvens[j].plus(fftOdds[j].times(omega));
            y[j + (length / 2)] = fftEvens[j].minus(fftOdds[j].times(omega));
        } 
        return y;
    }
    public static Complex[] ifft(Complex[] coef, int length)
    {
        if (length == 1){
            return coef;
        }
        Complex[] evenCoef = new Complex[length / 2];
        Complex[] oddCoef = new Complex[length / 2];
        
        // Separate even and odd coefficients
        for (int i = 0; i < length; i += 2) {
            evenCoef[i / 2] = coef[i];
            oddCoef[i / 2] = coef[i + 1]; // Corrected index calculation
        }

        Complex[] fftEvens = ifft(evenCoef, evenCoef.length);
        Complex[] fftOdds = ifft(oddCoef, oddCoef.length);
        Complex[] y = new Complex[length];
        for (int j = 0; j < length / 2; j++)
        {
            Complex omega = new Complex(Math.cos((2 * j * Math.PI) / length), -1 *Math.sin((2 * j * Math.PI) / length));
            y[j] = fftEvens[j].plus(fftOdds[j].times(omega));
            y[j + (length / 2)] = fftEvens[j].minus(fftOdds[j].times(omega));
        } 
        return y;
    }
    public static Complex[] inverseFFT(Complex[] arr, int length)
    {
        Complex[] returnVal = ifft(arr, length);
        for (int i = 0; i < length; i++)
        {
            returnVal[i] = returnVal[i].scale(1 / (double) length);
        }
        return returnVal;
    }
}