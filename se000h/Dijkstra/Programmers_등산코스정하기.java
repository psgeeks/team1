//가장 긴 시간을 해당 등산코스의 intensity
//어떤 출발 지점에서 어떤 봉우리까지의 경로의 최소 intensity만 구하면됨
//다익스트라: intensity가 최소인 경로를 기준으로 업데이트(min heap)
// 봉우리에서는 최소 휴식시간을 갱신하는 방식 O
// 경로는 출발점, 다른 봉우리 포함 안되야함 O
//intensity가 같다면 봉의 번호 제일 낮은걸로 O

import java.util.*;
class Solution {
    ArrayList<int[]>[] adjList;
    int n;
    boolean[] isSummit;
    boolean[] isGate;
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        
        /////////변수 초기화//////////
        this.n = n;
        isSummit = new boolean[n+1];
        isGate = new boolean[n+1];
        adjList = new ArrayList[n+1];
        
        for(int k: summits) isSummit[k] = true;
        
        for(int k: gates) isGate[k] = true;
        
        for(int k = 0; k < n+1; k++){
            adjList[k] = new ArrayList<>();
        }
        
        for(int k = 0; k < paths.length; k++){
            int i = paths[k][0];
            int j = paths[k][1];
            int w = paths[k][2];
            adjList[i].add(new int[] {j, w});
            adjList[j].add(new int[] {i, w});
        }
        
        /////////초기화 끝//////////

        Queue<int[]> q = new PriorityQueue<>((a,b)-> Integer.compare(a[1], b[1]));
        int[] dis = new int[n+1];
        Arrays.fill(dis, Integer.MAX_VALUE);
        
        for(int k: gates){
            q.offer(new int[] {k, 0});
            //dis[k] = 0;
        }
        
        while(!q.isEmpty()){
            
            int[] cur = q.poll();
            int cur_n = cur[0];
            int cur_dis = cur[1];
            
            if(isSummit[cur_n]) continue; //출입구
            if(cur_dis > dis[cur_n]) continue; //cur 경로의 intensity 현재까지 갱신된 것보다 크면 의미 X
            
            for(int[] next: adjList[cur_n]){
                
                int next_n = next[0];
                int next_dis = Math.max(cur_dis, next[1]); //intensity 갱신
                
                if(isGate[next_n]) continue; //출입구
                
                if(next_dis < dis[next_n]){ //intensity가 더 작은 경로가 발견됨
                    dis[next_n] = next_dis;
                    q.offer(new int[] {next_n, next_dis});
                    
                }
            }
        }
        //System.out.println(Arrays.toString(dis));
        Arrays.sort(summits);
        int min_summit = 0;
        int min = Integer.MAX_VALUE;
        for(int k : summits){
            if(dis[k] < min){
                min_summit = k;
                min = dis[k];
            }
        }
        
        int[] answer = {min_summit, min};
        return answer;
    }
}