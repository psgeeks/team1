import java.util.*;
/**
dfs 수행할 때 지금까지 방문한 노드 집합을 bitmask로 관리
이미 방문한 노드 집합이면 탐색하지 않음
*/
class Solution {
    int n, answer;
    int[] info;
    List<Integer>[] tree;
    boolean[] visitedState;
    
    public int solution(int[] info, int[][] edges) {
        n = info.length;
        this.info = info;

        tree = makeTree(edges);
        visitedState = new boolean[1 << n];

        answer = 1;
        dfs(1);  // 0번 노드만 방문한 상태
        return answer;
    }
    
    private List<Integer>[] makeTree(int[][] edges) {
        List<Integer>[] tree = new List[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            tree[e[0]].add(e[1]);  // 단방향
        }
        return tree;  
    }
    
    // state = 방문한 노드 집합
    private void dfs(int state) {
        if (visitedState[state]) return;
        visitedState[state] = true;
        
        int sheep = 0;
        int wolf = 0;
        
        // 현재 state에 포함된 노드들의 양/늑대 개수 계산
        for (int i = 0; i < n; i++) {
            if ( (state & (1 << i)) == 0) continue;
            
            if (info[i] == 0) sheep++;
            else wolf++;
        }
        
        // 불가능한 상태
        if (sheep <= wolf) return;
        
        answer = Math.max(answer, sheep);
        
        // 현재 방문한 노드들의 자식을 하나씩 추가
        for (int i = 0; i < n; i++) {
            if ( (state & (1 << i)) == 0) continue;
            
            for (int child : tree[i]) {
                int nextState = state | (1 << child);
                
                // 이미 포함된 자식이면 상태가 변하지 않으므로 생략
                if (nextState == state) continue;
                
                dfs(nextState);
            }
        }        
    }
}