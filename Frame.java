import java.util.Arrays;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Frame {
    private static int NUM_COLS;
    private static int NUM_ROWS;

    private static char[][] frame;

    private static void refresh() {
        System.out.print("\033[H\033[2J");  // ANSI escape sequence to clear the screen
        System.out.flush();
    }

    public static void frameSetup() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        NUM_COLS = screenSize.width / 2;
        NUM_ROWS = screenSize.height;

        frame = new char[NUM_COLS][NUM_ROWS];
        clearFrame();
    }

    public static void clearFrame() {
        for (char[] row : frame) {
            Arrays.fill(row, ' ');
        }
        refresh();
    }

    public static void printFrame() {
        for (int j = NUM_ROWS - 1; j >= 0; j--) {
            for (int i = 0; i < NUM_COLS; i++) {
                System.out.print(frame[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void drawLine(int x0, int y0, int x1, int y1) {
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

    private static void plotLineLow(int x0, int y0, int x1, int y1) {
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

    private static void plotLineHigh(int x0, int y0, int x1, int y1) {
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

    private static int getTerminalWidth() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.width;
    }

    private static int getTerminalHeight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.height;
    }

    public static void main(String[] args) {
        frameSetup();
        drawLine(10, 5, 70, 15);
        printFrame();
    }
}
