package segmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * 기존 세그먼트 트리가 자식 노드 둘에 구간을 반으로 나눠서 구간 합을 구하는 것이었다면
 * 이 문제에서는 구간별 최대값과 최소값을 저장하는 세그먼트 트리를 구현하는 것이다.
 */
public class BOJ_2357_최솟값과최댓값 {
	static int N, M;
	static int[] num;
	static int[] minTree;
	static int[] maxTree;
	
	static void initMinTree(int start, int end, int node) {
		if (start == end) {
			minTree[node] = num[start];
			return;
		}
		int mid = (start + end) / 2;
		initMinTree(start, mid, node*2);
		initMinTree(mid+1, end, node*2+1);
		minTree[node] = Math.min(minTree[node*2], minTree[node*2+1]);
	}
	
	static void initMaxTree(int start, int end, int node) {
		if (start == end) {
			maxTree[node] = num[start];
			return;
		}
		int mid = (start + end) / 2;
		initMaxTree(start, mid, node*2);
		initMaxTree(mid+1, end, node*2+1);
		maxTree[node] = Math.max(maxTree[node*2], maxTree[node*2+1]);
	}
	
	static int findMin(int start, int end, int node, int left, int right) {
		if (left>end || right<start) return Integer.MAX_VALUE;
		
		if (left <= start && right >= end) {
			return minTree[node];
		}
		
		int mid = (start + end) / 2;
		int lMin = findMin(start, mid, node*2, left, right);
		int rMin = findMin(mid+1, end, node*2+1, left, right);
		
		return Math.min(lMin, rMin);
	}
	
	static int findMax(int start, int end, int node, int left, int right) {
		if (left>end || right<start) return Integer.MIN_VALUE;
		
		if (left <= start && right >= end) {
			return maxTree[node];
		}
		
		int mid = (start + end) / 2;
		int lMax = findMax(start, mid, node*2, left, right);
		int rMax = findMax(mid+1, end, node*2+1, left, right);
		
		return Math.max(lMax, rMax);
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		num = new int[N];
		minTree = new int[4*N];
		maxTree = new int[4*N];
		
		for (int i=0; i<N; i++) {
			num[i] = Integer.parseInt(br.readLine());
		}
		
		initMaxTree(0, N-1, 1);
		initMinTree(0, N-1, 1);
		
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			
			System.out.println(findMin(0,N-1,1,a,b) + " " + findMax(0,N-1,1,a,b));
		}
		
		br.close();
	}
}
