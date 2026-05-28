import java.util.Arrays;

public class 노란불_신호등_mod {
	
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
	
	static boolean isYellow(int t, int g, int y, int r) {
		int cycle = g + y + r;
	    long mod = (t - 1) % cycle;
	    return g <= mod && mod < g + y;
	}
	
	public int solution(int[][] signals) {
	    long period = 1;
	    
	    for (int[] s : signals) {
	    	long cycle = s[0] + s[1] + s[2];
	    	period = lcm(period, cycle);
	    }
	    
	    for (int t = 1; t <= period; t++) {
	    	boolean ok = true;
	    	for (int[] s : signals) {
	    		if (!isYellow(t, s[0], s[1], s[2])) {
	    			ok = false;
	    			break;
	    		}
	    	}
	    	if (ok) return t;
	    }
	    
	    return -1;
	}
	
	public static void main(String[] args) {
		노란불_신호등_mod s = new 노란불_신호등_mod();
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
