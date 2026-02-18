package psg;

import java.util.*;
import java.io.*;

public class Sol12100 {
    // 5번 이동 & sout(만들 수 있는 가장 큰 블록)
    // 1 <= n <= 20

    static int[][] map;
    static int n;
    static int max = Integer.MIN_VALUE;
    static int[][] offset = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        map = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) map[i][j] = Integer.parseInt(st.nextToken());
        }

        backtracking(0, 0, map);
        System.out.println(max);
    }

    private static void backtracking(int depth, int curBlock, int[][] curMap) {
        // 여기에 가지치기 한판
    	
        if (depth == 5) {
            max = Math.max(max, curBlock);
            return;
        }

        for (int dir = 0; dir < 4; dir++) {
            Result result = move(dir, curMap);
            backtracking(depth+1,  result.maxValue, result.next);
        }
    }
    
    // 0: 상, 1: 하, 2: 좌, 3: 우
    private static Result move(int dir, int[][] board) {
    	int[][] next = new int[n][n];
    	int maxBlock = 0;
    	
    	for (int i = 0; i < n; i++) {
    		List<Integer> target = new ArrayList<>();
    		for (int j = 0; j < n; j++) {
    			// 위쪽으로 이동하려면 상->하로 순회
    			// 아래쪽으로 이동하려면 하->상으로 순회
    			// 왼쪽으로 이동하려면 좌->우로 순회해야함.
    			// 오른족으로 이동하려면 우->좌로 순회해야함. 나중에 리스트를 순회할 때 오른쪽 원소부터 merge됨.
    			int val = (dir < 2) ? (dir == 0 ? board[j][i] : board[n-1-j][i]) // j가 변경되므로 상/하로 이동하기 위해선 map[j][]로 건드려야함
    								: (dir == 2 ? board[i][j] : board[i][n-1-j]);// 좌/우로 이동하기위해 map[][j]로 건드려야함.
    			
    			if (val != 0) target.add(val); 
    		}
    		
    		List<Integer> merged = new ArrayList<>();
    		for (int j = 0; j < target.size(); j++) {
    			if (j + 1 < target.size() && target.get(j).equals(target.get(j+1))) {
    				merged.add(target.get(j)*2); // 합침
    				j++; // 다음으로 넘김
    			} else merged.add(target.get(j));
    		}
    		
    		for (int j = 0; j < merged.size(); j++) {
    			int val = merged.get(j);
    			
    			if (dir == 0) next[j][i] = val; // [j++][] 형태이므로 상->하 로 이동하며 값을 넣음 == 위로 이동
    			else if (dir == 1) next[n-j-1][i] = val;
    			else if (dir == 2) next[i][j] = val;
    			else next[i][n-j-1] = val;
    			
    			maxBlock = Math.max(val, maxBlock);
    		}
    	}
    	return new Result(next, maxBlock);
    }
    
    static class Result {
    	int[][] next;
    	int maxValue;
    	Result(int[][] board, int val) {next=board;maxValue=val;}
    }
}
