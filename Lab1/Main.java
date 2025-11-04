import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        // Размеры
        final int ROWS = 16; // длина s
        final int COLS = 12; // длина x
        final double SHIFT = 2.5;
        final double DEN = 17.0;

        // 1) s: short[16] = 16..1
        short[] s = new short[ROWS];
        for (int i = 0; i < ROWS; i++) {
            s[i] = (short) (ROWS - i);
        }

        // 2) x: float[12] случайные из [-6.0, 11.0]
        float[] x = new float[COLS];
        for (int j = 0; j < COLS; j++) {
            x[j] = (float) ThreadLocalRandom.current().nextDouble(-6.0, Math.nextUp(11.0));
        }

        // 3) w: 16x12
        double[][] w = new double[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            short sv = s[i];
            for (int j = 0; j < COLS; j++) {
                double xv = x[j];
                if (sv == 14) {
                    double sinx = Math.sin(xv);
                    double sin2 = sinx * sinx;
                    double outerExp = Math.exp(Math.exp(Math.log(sin2)));
                    w[i][j] = Math.asin(1.0 / outerExp);
                } else if (sv == 7 || sv == 8 || sv == 9 || sv == 11 || sv == 12 || sv == 13 || sv == 15 || sv == 16) {
                    double asinArg = (xv + SHIFT) / DEN;
                    asinArg = Math.max(-1.0, Math.min(1.0, asinArg));
                    double a = 0.5 - Math.asin(asinArg);
                    double b = Math.exp(Math.cbrt(xv));
                    w[i][j] = a * b;
                } else {
                    double asinArg = (xv + SHIFT) / DEN;
                    asinArg = Math.max(-1.0, Math.min(1.0, asinArg));
                    double asinVal = Math.asin(asinArg);
                    double inner = Math.pow(1.0 - asinVal, Math.cos(xv));
                    double s2 = Math.sin(Math.tan(inner));
                    w[i][j] = Math.log(s2 * s2);
                }
            }
        }

        // 4) Печать
        Locale locale = Locale.forLanguageTag("ru-RU");
        printMatrix(w, locale);
    }

    static void printMatrix(double[][] w, Locale locale) {
        StringBuilder row = new StringBuilder();
        for (int i = 0; i < w.length; i++) {
            row.setLength(0);
            for (int j = 0; j < w[i].length; j++) {
                if (j > 0) row.append(' ');
                row.append(String.format(locale, "%.4f", w[i][j]));
            }
            System.out.println(row);
        }
    }
}
