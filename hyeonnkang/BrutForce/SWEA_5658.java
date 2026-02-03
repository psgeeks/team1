import java.util.*;
import java.io.*;
 
/*
 * 메모리: 33,152kb / 실행시간: 157ms
 */
public class SWEA_5658 {
    static int n, k;
    static char[] arr;
    static TreeSet<Integer> tset = new TreeSet<>((a, b) -> {
        return b - a;
    });
     
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int tc = 1; tc <= T; tc++) {
            sb.append("#" + tc + " ");
             
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
             
            arr = new char[n];
            String s = br.readLine();
            for(int i = 0; i < n; i++) {
                arr[i] = s.charAt(i);
            }
             
            tset.clear();
             
            readPwd(); // 기존 숫자 읽기 
             
            for(int i = 0; i < n-1; i++) { // n-1번 회전
                // 회전
                shift();
                 
                // 숫자 읽기
                readPwd();
            }
             
            // k-1개 제거 
            for(int i = 0; i < k-1; i++ ) tset.pollFirst();
         
            sb.append(tset.pollFirst() + "\n");
             
        }
        System.out.print(sb.toString());
    }
     
    static void shift() {
        char tmp = arr[n-1];
        for(int i = n-1; i > 0; i--) arr[i] = arr[i-1];
        arr[0] = tmp;
    }
     
    static void readPwd() {
        for(int i = 1; i <= 4; i++) {
            int sum = 0;
            for(int j = i*(n/4)-1, k = 1; j >= (i-1)*(n/4); j--, k*=16) {
                char c = arr[j];
                int num = 0;
                if(Character.isDigit(c)) {
                    num = c-'0';
                }else {
                    num = (c-'A')+10;
                }
                sum += num*k;
            }
            tset.add(sum);
        }
    }
}