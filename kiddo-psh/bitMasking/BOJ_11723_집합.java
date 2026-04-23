package bitMasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_11723_집합 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder output = new StringBuilder();

        int M = Integer.parseInt(br.readLine());
        int S = 0;
        while(M-->0) {
            st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
            if (command.equals("add")) {
                int x = Integer.parseInt(st.nextToken());
                S |= (1<<x);
            } else if (command.equals("remove")) {
                int x = Integer.parseInt(st.nextToken());
                S &= ~(1<<x);
            } else if (command.equals("check")) {
                int x = Integer.parseInt(st.nextToken());
                if ((S & (1<<x)) != 0) output.append(1);
                else output.append(0);
                output.append("\n");
            } else if (command.equals("toggle")) {
                int x = Integer.parseInt(st.nextToken());
                S ^= (1<<x);
            } else if (command.equals("all")) {
                S = (1<<21)-1;
            } else if (command.equals("empty")) {
                S = 0;
            }
        }
        System.out.println(output);
        br.close();
    }
}
