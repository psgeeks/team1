import java.util.*;
/**
2차원 좌표로 방문할 곳이 주어지고, 이를 이진 트리로 구성

1. 좌표를 트리로 구성하기
2. preorder와 postorder로 순회하기 -> 그 값을 리턴 -> 그 값을 리턴

노드 개수 <= 10000
트리의 깊이 <= 1000

좌표를 기반으로 트리를 구성하는 것이 문제의 핵심. 순회는 O(N)이 소요
노드의 좌표 값 <= 100000

노드를 y좌표의 내림차순으로 정렬
Binary Search Tree 처럼 삽입
삽입할 노드에 자식이 있으면 자식이 없을 때까지 재귀적으로 내려가서 노드 추가
*/
class Solution {
    int[][] nodeinfo;
    List<Node> nodeList;
    int[][] answer;
    int preIdx, postIdx;
    Node root;
    
    public int[][] solution(int[][] nodeinfo) {
        this.nodeinfo = nodeinfo;
        nodeList = new ArrayList<>();
        for (int i = 0; i < nodeinfo.length; i++) {
            Node node = new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]);
            nodeList.add(node);
        }

        // y 좌표 내림차순 정렬
        nodeList.sort((a, b) -> Integer.compare(b.y, a.y));
        
        // 트리 만들기
        root = nodeList.get(0);
        makeTree();
        
        // 트리 순회
        answer = new int[2][nodeinfo.length];
        preIdx = 0;
        preorderTraversal(root);
        postIdx = 0;
        postorderTraversal(root);
        return answer;
    }
    
    // 모든 노드를 root부터 비교하며 삽입
    private void makeTree() {
        for (int i = 1; i < nodeList.size(); i++) {
            insert(root, nodeList.get(i));
        }
    }
    
    private void insert(Node parent, Node child) {
        if (child.x < parent.x) {
            if (parent.left == null) {
                parent.left = child;
            } else {
                insert(parent.left, child);
            }    
        } else {
            if (parent.right == null) {
                parent.right = child;
            } else {
                insert(parent.right, child);
            }
        }
    }
    
    // root -> left -> right
    private void preorderTraversal(Node root) {
        if (root == null) return;
        
        answer[0][preIdx++] = root.idx;
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }
    
    // left -> right -> root
    private void postorderTraversal(Node root) {
        if (root == null) return;
        
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        answer[1][postIdx++] = root.idx;
    }
    
    static class Node {
        int idx, x, y;
        Node left, right;
        Node (int idx, int x, int y) {
            this.idx = idx;
            this.x = x;
            this.y = y;
        }
    }
}