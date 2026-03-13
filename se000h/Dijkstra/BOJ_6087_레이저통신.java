
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/*
 * 16600 mb
 * 168 ms
 */
public class BOJ_6087_레이저통신 {
	static int H, W;
	static int[][] cs;
	static String[][] map;
	static int[] dr = {-1, 1, 0, 0}; //상하좌우
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		map = new String[H][W];
		cs = new int[2][2];
		int ccnt = 0;
		for(int h = 0; h < H; h++) {
			String str = br.readLine();
			for(int w = 0; w < W; w++) {
				String tmp = str.charAt(w) + "";
				if(tmp.equals("C")) {
					cs[ccnt][0] = h;
					cs[ccnt++][1] = w;
				}
				map[h][w] = tmp;
			}
		}
		//입력 끝
		
		System.out.println(bfs());
		
		
	}
	
	static int bfs() {
		int[][][] visited = new int[H][W][4];
		for(int h = 0; h < H; h++) {
			for(int w = 0; w < W; w++) {
				Arrays.fill(visited[h][w], Integer.MAX_VALUE);
			}
		}
		PriorityQueue<int[]> q = new PriorityQueue<>((a, b)-> a[3]-b[3]);
		q.offer(new int[] {cs[0][0], cs[0][1], -1, 0}); //r, c, dir, mcnt
		
		visited[cs[0][0]][cs[0][1]][0] = 0;
		visited[cs[0][0]][cs[0][1]][1] = 0;
		visited[cs[0][0]][cs[0][1]][2] = 0;
		visited[cs[0][0]][cs[0][1]][3] = 0;
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			int r = cur[0];
			int c = cur[1];
			int dir = cur[2];
			int mcnt = cur[3];
			if(r == cs[1][0] && c == cs[1][1]) {
				return visited[r][c][dir];
			}
			for(int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				if(nr < 0 || nr >= H || nc < 0 || nc >= W) continue; //범위
				if(map[nr][nc].equals("*")) continue;
				int nmcnt = 0;
				if(dir == -1) {
					nmcnt = 0;
				}else if(d != dir) { // 방향이 다르면 거울 추가  
					nmcnt = mcnt + 1;
				}else {
					nmcnt = mcnt;
				}
				if(nmcnt < visited[nr][nc][d]) { //경신 값이 작으면  
					visited[nr][nc][d] = nmcnt;
					q.offer(new int[] {nr, nc, d, nmcnt}); //경신한 값만 큐에 넣기  
				}
				
			}
		}
		return -1;
	}
}
