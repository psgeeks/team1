package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_5373_큐빙 {

    static char[][] u = new char[3][3];
    static char[][] d = new char[3][3];
    static char[][] f = new char[3][3];
    static char[][] b = new char[3][3];
    static char[][] l = new char[3][3];
    static char[][] r = new char[3][3];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            set();

            int N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                cubing(st.nextToken());
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(u[i][j]);
                }
                System.out.println();
            }
        }
    }

    static void cubing(String cmd) {
        char face = cmd.charAt(0);
        int turn = cmd.charAt(1) == '+' ? 1 : 3;

        for (int t = 0; t < turn; t++) {
            if (face == 'U') rotateU();
            else if (face == 'D') rotateD();
            else if (face == 'F') rotateF();
            else if (face == 'B') rotateB();
            else if (face == 'L') rotateL();
            else rotateR();
        }
    }

    static void rotateU() {
        u = rotate(u);

        char[] temp = l[0].clone();
        l[0] = f[0].clone();
        f[0] = r[0].clone();
        r[0] = b[0].clone();
        b[0] = temp;
    }

    static void rotateD() {
        d = rotate(d);

        char[] temp = b[2].clone();
        b[2] = r[2].clone();
        r[2] = f[2].clone();
        f[2] = l[2].clone();
        l[2] = temp;
    }

    static void rotateF() {
        f = rotate(f);

        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) temp[i] = u[2][i];

        for (int i = 0; i < 3; i++) {
            u[2][i] = l[2 - i][2];
            l[2 - i][2] = d[0][2 - i];
            d[0][2 - i] = r[i][0];
            r[i][0] = temp[i];
        }
    }

    static void rotateB() {
        b = rotate(b);

        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) temp[i] = u[0][i];

        for (int i = 0; i < 3; i++) {
            u[0][i] = r[i][2];
            r[i][2] = d[2][2 - i];
            d[2][2 - i] = l[2 - i][0];
            l[2 - i][0] = temp[i];
        }
    }

    static void rotateL() {
        l = rotate(l);

        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) temp[i] = u[i][0];

        for (int i = 0; i < 3; i++) {
            u[i][0] = b[2 - i][2];
            b[2 - i][2] = d[i][0];
            d[i][0] = f[i][0];
            f[i][0] = temp[i];
        }
    }

    static void rotateR() {
        r = rotate(r);

        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) temp[i] = u[i][2];

        for (int i = 0; i < 3; i++) {
            u[i][2] = f[i][2];
            f[i][2] = d[i][2];
            d[i][2] = b[2 - i][0];
            b[2 - i][0] = temp[i];
        }
    }

    static void set() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                u[i][j] = 'w';
                d[i][j] = 'y';
                f[i][j] = 'r';
                b[i][j] = 'o';
                l[i][j] = 'g';
                r[i][j] = 'b';
            }
        }
    }

    static char[][] rotate(char[][] surface) {
        char[][] temp = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temp[j][2 - i] = surface[i][j];
            }
        }
        return temp;
    }
}