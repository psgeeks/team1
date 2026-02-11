package offline;
import java.io.*;
import java.util.*;

/*
 * 메모리 : 35,328 kb , 실행시간 : 133ms
 */
public class SWEA_1248 {
	
	static int v, e, a, b;
	static List<Integer>[] child = new ArrayList[10010]; // child[i] : i의 자식들 리스트
	static int[] parent = new int[10010]; // parent[i] = i의 부모 
	
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          
        int TC = Integer.parseInt(br.readLine());
          
        StringBuilder sb = new StringBuilder();
        for(int tc = 1; tc <= TC; tc++) {
            sb.append("#").append(tc).append(" ");
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            v = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            
            for(int i = 1; i <= v; i++) {
            	child[i] = new ArrayList<>();
            }
            
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < e; i++) {
            	int p = Integer.parseInt(st.nextToken());
            	int c = Integer.parseInt(st.nextToken());
            	
            	child[p].add(c); // 자식 저장
            	parent[c] = p; // 부모 저장 
            }
            
            // a의 부모 리스트 만들기 
            HashSet<Integer> aParents = new HashSet<>();
            int cur = parent[a];
            while(cur >= 1) {
            	aParents.add(cur);
            	cur = parent[cur];
            }
            
            // b의 조상 중 a의 조상이 나올때까지 찾는다.
            int lca = 0;
            cur = parent[b];
            while(cur >= 1) {
            	if(aParents.contains(cur)) {
            		lca = cur;
            		break;
            	}
            	cur = parent[cur];
            }
            
            int size = getSubTreeSize(lca);
            sb.append(lca).append(" ").append(size).append("\n");
        }
        System.out.print(sb.toString());
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