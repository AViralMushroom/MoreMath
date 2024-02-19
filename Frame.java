import java.util.Arrays;

public class Frame {
    public int NUM_COLS; // Default value
    private int NUM_ROWS; // Default value

    private char[][] frame;

    public Frame(int height){
        int width = 80; // Default width

        try {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("win")) {
                width = Integer.parseInt(System.getenv("CMDER_ROWS"));
                // height = Integer.parseInt(System.getenv("CMDER_COLUMNS"));
            } else {
                width = Integer.parseInt(System.getenv("COLUMNS"));
                // height = Integer.parseInt(System.getenv("LINES"));
            }
        } catch (NumberFormatException | NullPointerException e) {
            // Use default values
        }
        NUM_COLS = width;
        NUM_ROWS = height; 
    }
    private static void refresh() {
        // System.out.print("\033[H\033[2J");  // ANSI escape sequence to clear the screen
        System.out.flush();
    }

    public void frameSetup() {
        frame = new char[this.NUM_COLS][this.NUM_ROWS];
        this.clearFrame();
    }

    public void clearFrame() {
        for (char[] row : this.frame) {
            Arrays.fill(row, ' ');
        }
        refresh();
    }

    public void printFrame() {
        for (int j = this.NUM_ROWS - 1; j >= 0; j--) {
            for (int i = 0; i < this.NUM_COLS; i++) {
                System.out.print(this.frame[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void drawLine(int x0, int y0, int x1, int y1) {
        if (Math.abs(y1 - y0) < Math.abs(x1 - x0)) {
            if (x0 > x1) {
                plotLineLow(x1, y1, x0, y0);
            } else {
                plotLineLow(x0, y0, x1, y1);
            }
        } else {
            if (y0 > y1) {
                plotLineHigh(x1, y1, x0, y0);
            } else {
                plotLineHigh(x0, y0, x1, y1);
            }
        }
    }

    private void plotLineLow(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int yi = 1;
        if (dy < 0) {
            yi = -1;
            dy = -dy;
        }
        int D = 2 * dy - dx;
        int y = y0;

        for (int x = x0; x <= x1; x++) {
            frame[x][y] = '*';
            if (D > 0) {
                y += yi;
                D += 2 * (dy - dx);
            } else {
                D += 2 * dy;
            }
        }
    }

    private void plotLineHigh(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int xi = 1;
        if (dx < 0) {
            xi = -1;
            dx = -dx;
        }
        int D = 2 * dx - dy;
        int x = x0;

        for (int y = y0; y <= y1; y++) {
            frame[x][y] = '*';
            if (D > 0) {
                x += xi;
                D += 2 * (dx - dy);
            } else {
                D += 2 * dx;
            }
        }
    }

    public static void main(String[] args) {
        // Frame thing = new Frame(100); 
        // thing.frameSetup();
        // thing.drawLine(0, 0, 49, 49);
        // thing.printFrame();
    }
}
