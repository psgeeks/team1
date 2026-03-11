import java.util.Arrays;

public class 노란불_신호등 {
	
	static long gcd(long a, long b) {
		while (b != 0) {
			long t = a % b;
			a = b;
			b = t;
		}
		return a;
	}
	
	static long lcm(long a, long b) {
		return a / gcd(a, b) * b;
	}
	
	public int solution(int[][] signals) {
	    int N = signals.length;
	    
	    int[] totalSeconds = new int[N];
	    long maxSecond = 1;
	    
	    // 각 신호등 주기 계산 + 전체 LCM 계산
	    for (int i = 0; i < N; i++) {
	    	totalSeconds[i] = signals[i][0] + signals[i][1] + signals[i][2];
	    	maxSecond = lcm(maxSecond, totalSeconds[i]);
	    }
	    
	    int period = (int) maxSecond;
	    
	    // yellow[i][t] = i번 신호등이 t초(0-based)에서 노란불인지 여부
	    boolean[][] yellow = new boolean[N][];
	    
	    for (int i = 0; i < N; i++) {
	    	int cycle = totalSeconds[i];
	    	yellow[i] = new boolean[cycle];
	    	
	    	int g = signals[i][0];
	    	int y = signals[i][1];
	    	
	    	// 예: [2,1,2] -> G G Y R R
	        // 노란불 구간은 인덱스 g ~ g+y-1
	        for (int t = g; t < g + y; t++) {
	            yellow[i][t] = true;
	        }
	    }
	    
	    for (int t = 1; t <= period; t++) {
	    	boolean allYellow = true;
	    	
	    	for (int i = 0; i < N; i++) {
	    		int idx = (t - 1) % totalSeconds[i];
	    		if (!yellow[i][idx]) {
	    			allYellow = false;
	    			break;
	    		}
	    	}
	    	
	    	if (allYellow) return t;
	    }
	    
	    return -1;
	}
	
	public static void main(String[] args) {
		노란불_신호등 s = new 노란불_신호등();
		int[][] a = {{2, 1, 2}, {5, 1, 1}};
		int[][] b = {{2, 3, 2}, {3, 1, 3}, {2, 1, 1}};
		int[][] c = {{3, 3, 3}, {5, 4, 2}, {2, 1, 2}};
		int[][] d = {{1, 1, 4}, {2, 1, 3}, {3, 1, 2}, {4, 1, 1}};
		
		System.out.println(s.solution(a));
		System.out.println(s.solution(b));
		System.out.println(s.solution(c));
		System.out.println(s.solution(d));
		
	}
}
