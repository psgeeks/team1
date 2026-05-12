import java.util.*;
/**
노드 수 = 17
DFS 탐색 => 현재 양과 늑대 개수를 저장
           '양 <= 늑대'이면 탐색 중단
           info[i] = 0 (양), = 1 (늑대)
일단 DFS 탐색 순서와 달리, 자식으로 가지않고 삼촌(부모의 형제) 노드로도 이동할 수 있음
모든 가능한 탐색에 대해 양의 개수의 최댓값을 갱신
*/
class Solution {
    int n, answer;
    int[] info;
    int[][] edges;
    List<Integer>[] tree;
    boolean[] visited;
    List<Integer> cands;
    
    public int solution(int[] info, int[][] edges) {
        n = info.length;
        this.info = info;
        this.edges = edges;
        
        visited = new boolean[n];
        cands = new ArrayList<>();
        tree = makeTree();

        answer = 1;
        dfs(0, 1, 0, cands);
        return answer;
    }
    
    private List<Integer>[] makeTree() {
        List<Integer>[] tree = new List[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            tree[e[0]].add(e[1]);  // 단방향
        }
        return tree;  
    }
    
    // cands = 다음에 방문 가능한 노드 집합
    private void dfs(int cur, int sheep, int wolf, List<Integer> cands) {
        if (sheep <= wolf) return;
        
        answer = Math.max(answer, sheep);
        
        List<Integer> newCands = new ArrayList<>(cands);
        newCands.remove(Integer.valueOf(cur));
        
        for (int next : tree[cur]) {
            newCands.add(next);
        }
        
        for (int next : newCands) {
            if (visited[next]) continue;
            dfs(next,
                sheep + ((info[next] == 0) ? 1 : 0),
                wolf + ((info[next] == 1) ? 1 : 0),
                newCands);
        }
        
    }
}