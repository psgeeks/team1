class Solution {
    Deque<Integer> stack;
    public int[] dailyTemperatures(int[] temperatures) {
        stack = new ArrayDeque<>();
        int[] answer = new int[temperatures.length];
        int cur = 0;
        for(int i = 0; i < temperatures.length; i++){
            cur = temperatures[i];
            while(!stack.isEmpty() && temperatures[stack.peek()] < cur){
                int out = stack.pop();
                answer[out] = i - out;
            
            }
            stack.push(i);
        }
        while(!stack.isEmpty()){
            answer[stack.pop()] = 0;
        }
        return answer;
    }
}