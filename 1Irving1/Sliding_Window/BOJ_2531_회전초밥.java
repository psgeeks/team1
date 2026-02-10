import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 메모리 - 18540 KB
// 시간 - 140 ms

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 접시 수
        int d = Integer.parseInt(st.nextToken()); // 초밥 가짓수
        int k = Integer.parseInt(st.nextToken()); // 연속 몇번
        int c = Integer.parseInt(st.nextToken()); // 쿠폰 번호

        int[] belt = new int[N];
        for(int i=0; i<N; i++){
            belt[i] = Integer.parseInt(br.readLine());
        }

        int[] eaten = new int[d+1]; // 초밥 개수
        int count = 0; // 먹은 초밥의 종류 count

        for(int i=0; i<k; i++){
            if(eaten[belt[i]]==0){
                count++;
            }
            eaten[belt[i]]++;
        }

        int maxCount = count;
        if(eaten[c]==0) maxCount = count+1;

        //슬라이딩 윈도우
        for(int i=0; i<N; i++){
            //맨 앞의 초밥 제거
            eaten[belt[i]]--;
            if(eaten[belt[i]]==0){
                count--;
            }

            //새 초밥 추가
            int next = belt[(i+k)%N];
            if (eaten[next] == 0) {
                count++;
            }
            eaten[next]++;

            if(eaten[c]==0){
                maxCount = Math.max(maxCount, count+1);
            }else{
                maxCount = Math.max(maxCount, count);
            }
        }
        System.out.println(maxCount);
    }
}