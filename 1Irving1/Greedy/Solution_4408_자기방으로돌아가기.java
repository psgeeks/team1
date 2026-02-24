import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] corridor = new int[201];

            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());

                // start가 end보다 작도록 정렬
                if (start > end) {
                    int temp = start;
                    start = end;
                    end = temp;
                }

                // 방 번호 매핑
                int startPos = (start + 1) / 2;
                int endPos = (end + 1) / 2;

                for (int j = startPos; j <= endPos; j++) {
                    corridor[j]++;
                }
            }

            // 최대 겹침 횟수 찾기
            int maxOverlap = 0;
            for (int i = 1; i <= 200; i++) {
                if (corridor[i] > maxOverlap) {
                    maxOverlap = corridor[i];
                }
            }
            sb.append("#").append(tc).append(" ").append(maxOverlap).append("\n");
        }
        System.out.print(sb);
    }
}