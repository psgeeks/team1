import java.util.*;
import java.io.*;

public class Baekjoon_2667_단지번호붙이기 {
    static int N;
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {0, 0, 1, -1}; // 상하좌우 탐색을 위한 배열
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        visited = new boolean[N][N];

        // 지도 데이터 입력 처리
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                map[i][j] = line.charAt(j) - '0';
            }
        }

        List<Integer> result = new ArrayList<>();
        // 전체 맵을 돌면서 집(1)을 찾고 방문하지 않았다면 BFS 시작
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 1 && !visited[i][j]) {
                    result.add(bfs(i, j)); // 새로운 단지의 집 개수를 리스트에 추가
                }
            }
        }

        // 각 단지별 집의 개수를 오름차순으로 정렬
        Collections.sort(result);
        System.out.println(result.size()); // 총 단지 수 출력
        for (int count : result) {
            System.out.println(count); // 단지별 집 수 출력
        }
    }

    static int bfs(int x, int y) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x, y});
        visited[x][y] = true;
        int count = 1; // 단지 내 집의 개수 카운트

        while (!q.isEmpty()) {
            int[] curr = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = curr[0] + dx[i];
                int ny = curr[1] + dy[i];

                // 맵 범위 내에 있고 집이 존재하며 방문하지 않은 경우
                if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
                    if (map[nx][ny] == 1 && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        q.add(new int[]{nx, ny});
                        count++;
                    }
                }
            }
        }
        return count;
    }
}