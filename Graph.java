public class Graph {
    
    public Graph(Polynomial polynomial, int width)
    {
        Frame frame = new Frame((int) (2 * polynomial.evaluate(width) + 1)); 
        frame.frameSetup();
        for (double x = 0; x < width; x++)
        {
            frame.drawLine((int) x, 
                (int) polynomial.evaluate(x), (int) x + 1, 
                (int) polynomial.evaluate(x + 1));
        }
        frame.printFrame();
    }
    public static void main(String args[])
    {
        Graph thing = new Graph(new Polynomial("0.01x^2+0.01x"), 10);
    }
    
}
