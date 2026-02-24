import java.util.*;
import java.io.*;

public class Sol2042 {
    static long[] tree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        tree = new long[4*n];
        long[] nums = new long[n+1];
        for (int i = 1; i <= n; i++) nums[i] = Long.parseLong(br.readLine());

        init(nums, 1, 1, n);
        for (int i = 0; i < m+k; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if (type == 1) update(nums, 1, b, 1, n, c);
            else System.out.println(query(1, b, c, 1, n));
        }
    }

    private static void init(long[] nums, int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
            return;
        }
        init(nums, node*2, start, (start+end)/2);
        init(nums, node*2+1, (start+end)/2+1, end);
        tree[node] = tree[node*2] + tree[node*2+1];
    }

    private static void update(long[] nums, int node, int target, int s, int e, long newVal) {
        if (target < s || e < target) return;
        if (s == e) {
            nums[target] = newVal;
            tree[node] = newVal;
            return;
        }

        update(nums, node*2, target, s, (s+e)/2, newVal);
        update(nums, node*2+1, target, (s+e)/2+1, e, newVal);
        tree[node] = tree[node*2] + tree[node*2+1];
    }

    private static long query(int node, int left, long right, int s, int e) {
        if (e < left || right < s) return 0;
        if (left <= s && e <= right) return tree[node];
        return query(node*2, left, right, s, (s+e)/2) + query(node*2+1, left, right, (s+e)/2+1, e);
    }
}
