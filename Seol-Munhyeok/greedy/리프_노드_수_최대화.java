import java.util.Arrays;

public class 리프_노드_수_최대화 {
    public int solution(int dist_limit, int split_limit) {
    	// 2와 3의 거듭제곱을 저장
        long[] pow2 = new long[32];
        long[] pow3 = new long[21];
        
        // 만들 수 있는 최대 2구간, 3구간의 길이 저장
        int len2 = 1;
        int len3 = 1;
        
        // split_limit 범위 내에서 만들 수 있는 2와 3의 거듭제곱 저장
        pow2[0] = 1L;
        while (len2 < pow2.length && pow2[len2 - 1] * 2L <= split_limit) {
        	pow2[len2] = pow2[len2 - 1] * 2L;
        	len2++;
        }
        
        pow3[0] = 1L;
        while (len3 < pow3.length && pow3[len3 - 1] * 3L <= split_limit) {
        	pow3[len3] = pow3[len3 - 1] * 3L;
        	len3++;
        }
        
        long answer = 1;  // dist_limit = 0 인 경우 포함
        
        // 2구간 길이 a, 3구간 길이 b를 전부 확인
        // 이때 a의 개수를 최소로 하는 것이 이득
        for (int a = 0; a < len2; a++) {
        	for (int b = 0; b < len3; b++) {
        		if (pow2[a] > split_limit / pow3[b]) break;  // 2^a * 3^b > split_limit (분배도 제한)
        		
        		long cand;
        		
        		// 3구간이 하나도 없는 경우: 전부 2구간
        		if (b == 0) {
        			long fullDist = pow2[a] - 1L;  // 1 + 2 + ... + 2^(a-1)
        			// 리프 수 = 1 + 사용한 2자식 분배 노드 수
        			cand = 1L + Math.min(dist_limit, fullDist);
        		} 
        		else {
        			long ternarySum = (pow3[b] - 1L) / 2L; // 1 + 3 + ... + 3^(b-1)
                    long fullDist = (pow2[a] - 1L) + pow2[a] * ternarySum;
                    
                    // 완전 트리를 만들 수 있으면 리프 수는 2^a * 3^b
                    if (dist_limit >= fullDist) {
                    	cand = pow2[a] * pow3[b];
                    } 
                    else {
                    	// 3구간 시작 전, 2구간 끝에 있는 리프 노드의 수 = y
                    	// twoCost(a, y) : 2구간에서 리프 y개 만들기 위한 비용
                    	// y * ternarySum : 그 리프들로 3구간에서 쓸 수 있는 최대 분배 노드
                    	// twoCost(a, y) + y * ternarySum >= dist_limit인 최소 y 찾기
                    	
                    	long lo = 0L;
                    	long hi = pow2[a];
                    	
                    	while (lo < hi) {
                    		long mid = (lo + hi) / 2;
                    		long cap = twoCost(a, mid) + mid * ternarySum;
                    		
                    		if (cap >= dist_limit) hi = mid;
                    		else lo = mid + 1;
                    	}
                    	
                    	long minTwo = twoCost(a, lo);
                    	cand = 1L + 2L * dist_limit - minTwo;
                    }
        		}
        		
        		answer = Math.max(answer, cand);
        	}
        }
        
        return (int) answer;
    }
    
    // a개의 2레벨 뒤에 y개의 노드를 만들기 위한
    // 2자식 분배 노드의 최소 개수
    private long twoCost(int a, long y) {
        long need = y;
        long cost = 0L;

        for (int i = 0; i < a; i++) {
            need = (need + 1L) / 2L; // ceil(need / 2)
            cost += need;
        }

        return cost;
    }
    
    
    public static void main(String[] args) {
    	리프_노드_수_최대화 sol = new 리프_노드_수_최대화();
    	int[] dist_limit = {3, 0, 3, 5};
    	int[] split_limit = {6, 10, 100, 16};
    	int[] result = {6, 1, 7, 9};
    	
    	for (int i = 0; i < 4; i++) {
    		int res = sol.solution(dist_limit[i], split_limit[i]);
    		if (res == result[i]) System.out.println("PASS");
    		else System.out.println("WRONG! res = " + res);
    	}
    	
	}
}