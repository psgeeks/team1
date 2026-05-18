package 모노토닉스택;

class Solution {
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int len = heights.length;
        int max = 0;
        for(int idx = 0; idx < len; idx++){
            while(!stack.isEmpty() && heights[idx] < heights[stack.peek()]){
                int top = stack.pop();
                int left_end = !stack.isEmpty()? stack.peek() + 1: 0;
                int right_end = idx - 1;
                int width = right_end - left_end + 1;
                max = Math.max(max, width*heights[top]);
            }
            stack.push(idx);
        }
        while(!stack.isEmpty()){
            int top = stack.pop();
            int width = 0;
            if(!stack.isEmpty()){
                width = (len - (stack.peek()+1)); //len - left_end
            }else{
                width = len;
            }
            max = Math.max(max, width*heights[top]);
        }
        return max;
    }
}