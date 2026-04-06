package beakjoon;

import java.util.*;
import java.io.*;

public class Sol1826 {

    static class Station {
        int dist, oil;
        boolean visited = false;
        public Station(int d, int o) {dist=d;oil=o;}
    }

    static class Node {
        int dist, oil, availableDist, idx;
        public Node(int d, int o, int a, int i) {dist=d;oil=o;availableDist=a;idx=i;}
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        List<Station> stations = new ArrayList<>();
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> {
            if (a.availableDist == b.availableDist) return Integer.compare(b.dist, a.dist);
            return Integer.compare(b.availableDist, a.availableDist);
        }); // 이동가능한 거리가 가장 먼 곳을 앞으로

        stations.add(new Station(0, 0)); // 시작 위치를 임시로 넣어줌, 이후 주유소와 visited를 같이 취급하기 위함

        for (int i = 0; i < n ;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            stations.add(new Station(a, b));
        }
        stations.sort(Comparator.comparingInt(s -> s.dist));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int limit = Integer.parseInt(st.nextToken());

        int count = 0;
        int curOil = Integer.parseInt(st.nextToken());
        pq.offer(new Node(0, curOil, curOil, 0));

        // 매번 현재 위치보다 앞서는(작은) 주유소 중에서 충전했을 때 가장 멀리 갈 수 있는 곳을 선택한다.
        while (!pq.isEmpty()){
            Node node = pq.poll();
            stations.get(node.idx).visited = true;

            if (node.availableDist >= limit) {
                System.out.println(count);
                break;
            }

            pq.clear();
            for (int i = 1; i < stations.size(); i++) {
                Station s = stations.get(i);
                if (s.visited) continue;

                if (s.dist <= node.availableDist) pq.offer(new Node(s.dist, s.oil, s.oil + node.availableDist, i));
                else break;
            }

            if (pq.isEmpty()) {
                System.out.println(-1);
                break;
            }

            count++;
        }


    }
}
