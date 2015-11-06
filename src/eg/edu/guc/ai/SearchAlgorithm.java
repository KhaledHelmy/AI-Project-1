package eg.edu.guc.ai;
import java.util.LinkedList;
import java.util.Queue;

import eg.edu.guc.ai.Board.Tile;


public class SearchAlgorithm {
	
	public class SearchOutput{
		public boolean failure;
		public Node solution;
		public int numberOfExpandedNodes;
		public LinkedList<String> goalSteps = new LinkedList<String>();
		
		public SearchOutput(boolean failure, Node solution, int numberOfExpandedNodes){
			this.failure = failure;
			this.solution = solution;
			this.numberOfExpandedNodes = numberOfExpandedNodes;
			if (!failure) {
				this.generateGoalPath();
			}
		}
		
		public boolean isFailure(){
			return this.failure;
		}

		private void generateGoalPath() {
			goalSteps.clear();
			Board state = (Board) solution.state;
			int startX = 0, startY = 0;
			int endX = state.height-1, endY = state.width-1;
			for (int i=0; i<state.height; i++) {
				for (int j=0; j<state.width; j++) {
					if (Tile.isStart(state.board[i][j])) {
						startX = i;
						startY = j;
					}
					if (Tile.isEnd(state.board[i][j])) {
						endX = i;
						endY = j;
					}
				}
			}
			boolean[][] vis = new boolean[state.height][state.width];
			int[] deltaX = {0, 0, 1, -1};
			int[] deltaY = {1, -1, 0, 0};
			int curX = startX, curY = startY;
			while (curX != endX && curY != endY) {
				vis[curX][curY] = true;
				for (int k=0; k<4; k++) {
					int newX = curX + deltaX[k];
					int newY = curY + deltaY[k];
					if (newX >= 0 && newX < state.height && newY >= 0 && newY < state.width && !vis[newX][newY]) {
						if ((k == 0 && Tile.isLeftSideOpened(state.board[newX][newY]) && Tile.isRightSideOpened(state.board[curX][curY])) ||
								(k == 1 && Tile.isRightSideOpened(state.board[newX][newY]) && Tile.isLeftSideOpened(state.board[curX][curY])) ||
								(k == 2 && Tile.isUpperSideOpened(state.board[newX][newY]) && Tile.isBottomSideOpened(state.board[curX][curY])) ||
								(k == 3 && Tile.isBottomSideOpened(state.board[newX][newY]) && Tile.isUpperSideOpened(state.board[curX][curY]))) {
							curX = newX;
							curY = newY;
							if (k == 0) goalSteps.add("Right");
							else if (k == 1) goalSteps.add("Left");
							else if (k == 2) goalSteps.add("Down");
							else if (k == 3) goalSteps.add("Up");
						}
					}
				}
			}
		}
	}
	
	public static class DFS implements Strategy{

		@Override
		public Queue<Node> execute(Queue<Node> nodes, Queue<Node> newNodes) {
			LinkedList<Node> resultingNodes = new LinkedList<Node>(nodes);
			for (Node newNode : newNodes) {
				resultingNodes.addFirst(newNode);
			}
			return resultingNodes;
		}
	}
	
	public static class BFS implements Strategy{

		@Override
		public Queue<Node> execute(Queue<Node> nodes, Queue<Node> newNodes) {
			LinkedList<Node> resultingNodes = new LinkedList<Node>(nodes);
			for (Node newNode : newNodes) {
				resultingNodes.addLast(newNode);
			}
			return resultingNodes;
		}
	}

	public static class IDS implements Strategy{
		private static int maxDepth = 0;
		private static int foundDepth = 0;

		@Override
		public Queue<Node> execute(Queue<Node> nodes, Queue<Node> newNodes) {
			LinkedList<Node> resultingNodes = new LinkedList<Node>(nodes);
			for (Node newNode : newNodes) {
				foundDepth = Math.max(foundDepth, newNode.depth);
				if (newNode.depth <= maxDepth) {
					resultingNodes.addFirst(newNode);
				}
			}
			return resultingNodes;
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
	
	public SearchOutput search(SearchProblem problem, Strategy strategy, boolean visualize){
		problem.visitedStates.clear();
		Queue<Node> queue = SearchAlgorithm.makeQueue(Node.makeNode(problem.initialState));
		int numberOfExpandedNodes = 0;
		
		while(!queue.isEmpty()){
			Node node = queue.poll();
			numberOfExpandedNodes ++;
			
			if(problem.applyGoalTest(node) == true){
				if (visualize) {
					generateBoardPath(node);
				}
				return new SearchOutput(false, node, numberOfExpandedNodes);
			}

			queue = strategy.execute(queue, problem.expand(node));

			if (strategy instanceof IDS) {
				if (queue.isEmpty() && IDS.maxDepth < IDS.foundDepth) {
					problem.visitedStates.clear();
					queue.add(Node.makeNode(problem.initialState));
					IDS.maxDepth ++;
				}
			}
		}

		if (visualize) {
			System.out.println("No solution found!");
		}
		return new SearchOutput(true, null, numberOfExpandedNodes);
	}

	private void generateBoardPath(Node node) {
		LinkedList<Node> pathNodes = new LinkedList<Node>();
		Node curNode = node;
		while (curNode != null) {
			pathNodes.addFirst(curNode);
			curNode = curNode.parentNode;
		}
		for (Node pathNode : pathNodes) {
			System.out.println(pathNode.state.visualize());
		}
	}
}
