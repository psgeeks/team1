import java.io.*;
import java.util.*;

/*
 * 메모리: 14,276kb, 실행시간: 148ms
 */
public class BOJ_1300 {
	static long n, k;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        k = Integer.parseInt(br.readLine());
        
        long left = 1, right = n*n;
        long answer = 0;
        while(left <= right) {
        	long mid = (left+right)/2;
        	
        	if(count(mid) >= k) {
        		answer = mid;
        		right = mid-1;
        	}else {
        		left = mid+1;
        	}
        }
        System.out.println(answer);
    }
    
    static long count(long x) {
    	long cnt = 0;
    	for(int i = 1; i <= n; i++) {
    		cnt += Math.min(x/i, n);
    	}
    	return cnt;
    }
}