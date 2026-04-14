package bitMasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2064_IP주소 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long max = 0;
        long min = Long.MAX_VALUE;

        while(N-->0) {
            StringBuilder sb = new StringBuilder();
            String S = br.readLine();
            String[] str = S.split("\\.");

            long value = 0;
            for (int i=0; i<4; i++) {
                int num = Integer.parseInt(str[i]);
                value = (value<<8) | num;
            }
            min = Math.min(min, value);
            max = Math.max(max, value);
        }

        long diff = min ^ max;
        long mask;

        if (diff == 0) {
            mask = (1L<<32)-1;
        } else {
            int shift = 0;
            while (diff > 0) {
                diff >>= 1;
                shift++;
            }

            mask = ((1L << 32) - 1) << shift;
            mask &= (1L << 32) - 1;
        }

        long address = (long)min & mask;

        System.out.println(longToIp(address));
        System.out.print(longToIp(mask));

        br.close();
    }
    static String longToIp(long n) {
        return ((n>>24) & 255) + "." +
                ((n>>16) & 255) + "." +
                ((n>>8) & 255) + "." +
                (n & 255);
    }
}
