import java.io.*;
import java.util.*;

/*
 * 메모리 : 14,968kb, 실행시간 : 116ms
 */
public class BOJ_17471 {
	static int n; //총 지역 개수 
	static int[] people; // people[i] : i 지역의 인구 수 
	static List<Integer>[] edges;  // edges[i] : i지역과 연결된 지역 번호 리스트 
	static int minDiff = -1; // 두 선거구의 인원 차이의 최솟값 
	static int total; // 모든 지역의 인구수 총 합 
	static int[] a; // 선택된 a 지역들 
	
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        n = Integer.parseInt(br.readLine());
        people = new int[n+1]; 
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= n; i++) {
        	people[i] = Integer.parseInt(st.nextToken());
        	total += people[i];
        }
        
        edges = new List[n+1];
        for(int i = 1; i <= n; i++) {
        	st = new StringTokenizer(br.readLine());
        	int k = Integer.parseInt(st.nextToken());
        	edges[i] = new ArrayList<>();
        	for(int j = 0; j < k; j++) {
        		edges[i].add(Integer.parseInt(st.nextToken()));
        	}
        }
        
        a = new int[n+1];
        
        divide(0, 1);
        
        System.out.println(minDiff);
    }
    
    static void divide(int cnt, int idx) {
    	if(idx > n) {
    		if(cnt == 0 || cnt == n) return; // 모든 지역이 A이거나 B인 경우는 패스 
    		
    		if(!isOk()) return; // 선거구가 제대로 나뉘어지지 않았을 때 패스
    		
    		int sumA = 0;
    		for(int i = 0; i < cnt; i++) {
    			sumA += people[a[i]];
    		}
    		if(sumA == -1) return; // 선거구가 제대로 나뉘어지지 X
    		int sumB = total - sumA;
    		int diff = Math.abs(sumA-sumB);
    		minDiff = (minDiff == -1) ? diff : Math.min(minDiff, diff);
    		
    		return;
    	}
    	if(cnt > n) return;
    	
    	a[cnt] = idx;
    	divide(cnt+1, idx+1); // idx 지역을 A 선거구로 선택 x 
    	a[cnt] = 0;
    	divide(cnt, idx+1); // idx 지역을 A 선거구로 선택 x 
    }
    
    static boolean isOk() {
    	boolean[] visited = new boolean[n+1];
    	Queue<Integer> q = new ArrayDeque<>();
    	
    	// 선거구 A 방문 
    	q.add(a[0]);
    	visited[a[0]] = true;
    	while(!q.isEmpty()) {
    		int cur = q.poll();    		
    		for(int x : edges[cur]) {
    			if(visited[x]) continue;
    			if(!isA(x)) continue; // b 선거구로 선택된 지역은 패스 
    			q.add(x);
    			visited[x] = true;
    		}
    	}
    	// a 모두 방문했는지 체크
    	for(int i = 0; i < n; i++) {
    		if(a[i] != 0 && !visited[a[i]]) return false;
    	}
    	
    	// 선거구 B 방문 
    	for(int i = 1; i <= n; i++) {
    		if(!visited[i]) {
    			q.add(i);
    			visited[i] = true;
    			break;
    		}
    	}
    	while(!q.isEmpty()) {
    		int cur = q.poll();
    		for(int x : edges[cur]) {
    			if(visited[x]) continue;
    			q.add(x);
    			visited[x] = true;
    		}
    	}
    	
    	// 방문처리 안된 곳이 있으면 선거구가 제대로 안 나뉜것이므로 -1 return 
    	for(int i = 1; i <= n; i++) {
    		if(!visited[i]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    static boolean isA(int x) {
    	for(int num : a) {
    		if(num == x) return true;
    	}
    	return false;
    }

}
