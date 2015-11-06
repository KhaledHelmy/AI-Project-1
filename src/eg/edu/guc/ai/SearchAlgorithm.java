package eg.edu.guc.ai;
import java.util.LinkedList;
import java.util.Queue;


public class SearchAlgorithm {
	
	public class SearchOutput{
		public boolean failure;
		public Node solution;
		public int numberOfExpandedNodes;
		
		public SearchOutput(boolean failure, Node solution, int numberOfExpandedNodes){
			this.failure = failure;
			this.solution = solution;
			this.numberOfExpandedNodes = numberOfExpandedNodes;
		}
		
		public boolean isFailure(){
			return this.failure;
		}
	}
	
	public static class DFS implements Strategy{

		@Override
		public Queue<Node> execute(Queue<Node> nodes, Queue<Node> newNodes) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static class BFS implements Strategy{

		@Override
		public Queue<Node> execute(Queue<Node> nodes, Queue<Node> newNodes) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class IDS implements Strategy{

		@Override
		public Queue<Node> execute(Queue<Node> nodes, Queue<Node> newNodes) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static class Greedy implements Strategy{

		@Override
		public Queue<Node> execute(Queue<Node> nodes, Queue<Node> newNodes) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class AStar implements Strategy{

		@Override
		public Queue<Node> execute(Queue<Node> nodes, Queue<Node> newNodes) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static Queue<Node> makeQueue(Node node){
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(node);
		return queue;
	}
	
	public SearchOutput search(SearchProblem problem, Strategy strategy){
		Queue<Node> queue = SearchAlgorithm.makeQueue(Node.makeNode(problem.initialState));
		int numberOfExpandedNodes = 0;
		
		while(!queue.isEmpty()){
			Node node = queue.poll();
			numberOfExpandedNodes ++;
			
			if(problem.applyGoalTest(node) == true){
				return new SearchOutput(false, node, numberOfExpandedNodes);
			}
			
			queue = strategy.execute(queue, problem.expand(node));
		}
		
		return new SearchOutput(true, null, numberOfExpandedNodes);
	}
}
