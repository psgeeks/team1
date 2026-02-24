import java.io.*;
import java.util.*;

/*
 * 메모리 : 119,392kb, 실행시간: 488ms
 */
public class BOJ_2805 {
	
	static int N, M;
	static int[] tree;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        tree = new int[N];
        
        st = new StringTokenizer(br.readLine());
        int left = 0, right = 0;
        for(int i = 0; i < N; i++) {
        	tree[i] = Integer.parseInt(st.nextToken());
        	right = Math.max(right, tree[i]);
        }
        
        int answer = 0;
        while(left <= right) {
        	int mid = (left+right)/2;
        	if(cutTree(mid)) {
        		answer = Math.max(answer, mid);
        		left = mid+1;
        	}else {
        		right = mid-1;
        	}
        }
        System.out.println(answer);
    }
    
    // 나무를 height 만큼 잘랐을 때 M미터 이상의 나무를 얻을 수 있을 경우 true return 
    static boolean cutTree(int height) {
    	long sum = 0;
    	for(int i = 0; i < N; i++) {
    		sum += (tree[i]-height > 0 ? tree[i]-height : 0);
    	}
    	return sum >= M;
    }
}