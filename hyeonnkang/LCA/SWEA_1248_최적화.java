package offline;
import java.io.*;
import java.util.*;

/*
 * 메모리 : 32,000 kb , 실행시간 : 135 ms
 */
public class SWEA_1248_최적화 {
	
	static int v, e, a, b;
	static List<Integer>[] child; // child[i] : i의 자식들 리스트
	static int[][] parent; // parent[k][i] = i의 2^k번째 부모 
	static int[] depth; // depth[i] = i의 깊이
	static int MAX_K = 0;
	
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          
        int TC = Integer.parseInt(br.readLine());
          
        StringBuilder sb = new StringBuilder();
        for(int tc = 1; tc <= TC; tc++) {
            sb.append("#").append(tc).append(" ");
            
            // 초기화
            StringTokenizer st = new StringTokenizer(br.readLine());
            v = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            MAX_K = (int)(Math.log(v)/Math.log(2)) + 1; // 가능한 최대 깊이
            parent = new int[MAX_K][v+1];
            depth = new int[v+1];
            child = new ArrayList[v+1];
            for(int i = 1; i <= v; i++) {
            	child[i] = new ArrayList<>();
            }
            
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < e; i++) {
            	int p = Integer.parseInt(st.nextToken());
            	int c = Integer.parseInt(st.nextToken());
            	child[p].add(c); // 자식 저장
            	parent[0][c] = p; // 바로 위 부모 
            }
            
            // parent 배열 모두 채우기 
            makeParentTree();
            
            // 깊이 계산
            calculateDepth(1, 0);
            
            // 공통조상 lca 찾기
            int lca = getLca();
            
            // lca의 서브트리 크기 구하기
            int answer = getSubTreeSize(lca);
            
            sb.append(lca).append(" ").append(answer).append("\n");
        }
        System.out.print(sb.toString());
    }
    
    private static int getLca() {
    	// a가 b보다 더 밑에 있도록 설정
    	if(depth[a] < depth[b]) {
    		int tmp = a;
    		a = b;
    		b = tmp;
    	}
    	
    	// a랑 b 높이맞추기
    	for(int k = MAX_K-1; k >= 0; k--) {
    		if(depth[a]-depth[b] >= (1<<k)) {
    			a = parent[k][a];
    		}
    	}
    	
    	// 깊이를 맞췄는데 a랑 b랑 같다면 lca는 a 
    	if(a == b) return a;
    	
    	for(int k = MAX_K-1; k >= 0; k--) {
    		if(parent[k][a] != parent[k][b]) {
    			a = parent[k][a];
        		b = parent[k][b];
    		}
    	}
    	
    	return parent[0][a];
    }
    
    
    private static void calculateDepth(int node, int d) {
    	depth[node] = d;
    	for(int c : child[node]) {
    		calculateDepth(c, d+1);
    	}
	}

    private static void makeParentTree() {
    	for(int k = 1; k < MAX_K; k++) {
    		for(int node = 1; node <= v; node++) {
    			parent[k][node] = parent[k-1][parent[k-1][node]];
    		}
    	}
    }
    
    // 루트노드가 node인 서브트리의 사이즈를 return
	private static int getSubTreeSize(int node) {
		Queue<Integer> q = new ArrayDeque<>();
		q.add(node);
		int size = 0;
		while(!q.isEmpty()) {
			int cur = q.poll();
			++size;
			for(int c : child[cur]) {
				q.add(c);
			}
		}
		return size;
	}
}