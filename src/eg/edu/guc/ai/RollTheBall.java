package eg.edu.guc.ai;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import eg.edu.guc.ai.Board.Tile;

// A class representing the search problem of Roll the Ball.
public class RollTheBall extends SearchProblem{
	
	public RollTheBall(Board initialState){
		super(initialState, new ArrayList<Operation>(Arrays.asList(MoveBlockOperation.getInstance())));
	}

	@Override
	public boolean applyGoalTest(Node node){
		// TODO Auto-generated method stub
		int startX = 0, startY = 0;
		int endX = node.state.height-1, endY = node.state.width-1;
		for (int i=0; i<node.state.height; i++) {
			for (int j=0; j<node.state.width; j++) {
				if (Tile.isStart(node.state.board[i][j])) {
					startX = i;
					startY = j;
				}
				if (Tile.isEnd(node.state.board[i][j])) {
					endX = i;
					endY = j;
				}
			}
		}
		boolean[][] vis = new boolean[node.state.height][node.state.width];
		int[] deltaX = {0, 0, 1, -1};
		int[] deltaY = {1, -1, 0, 0};
		return findPath(startX, startY, endX, endY, deltaX, deltaY, node.state, vis);
	}

	private boolean findPath(int curX, int curY, int endX, int endY,
			int[] deltaX, int[] deltaY, Board state, boolean[][] vis) {
		if (curX == endX && curY == endY) {
			return true;
		}
		if (vis[curX][curY]) {
			return false;
		}
		vis[curX][curY] = true;
		boolean result = false;
		for (int k=0; k<4; k++) {
			int newX = curX + deltaX[k];
			int newY = curY + deltaY[k];
			if (newX >= 0 && newX < state.height && newY >= 0 && newY < state.width) {
				if ((k == 0 && Tile.isLeftSideOpened(state.board[newX][newY])) ||
						(k == 1 && Tile.isRightSideOpened(state.board[newX][newY])) ||
						(k == 2 && Tile.isUpperSideOpened(state.board[newX][newY])) ||
						(k == 3 && Tile.isBottomSideOpened(state.board[newX][newY]))) {
					result |= findPath(newX, newY, endX, endY, deltaX, deltaY, state, vis);
				}
			}
		}
		return result;
	}

	// A class representing the move block operation
	public static class MoveBlockOperation implements Operation{
		private static MoveBlockOperation instance = new MoveBlockOperation();
		
		public static MoveBlockOperation getInstance(){
			return instance;
		}
		
		// args contain 4 ints, row, col, deltaX, deltaY
		// [row, column] is the cell of the block to be moved.
		// [deltaX, deltaY] is the change to be applied to the co-ordinates of the block.
		// i.e. the block moves from [row, column] to [row + deltaX, column + deltaY].
		@Override
		public Queue<Node> execute(Node node, int... args) {
			Queue<Node> resultingNodes = new LinkedList<Node>();
			Node newNode = new Node(1, node, deepCopyState(node.state),
					MoveBlockOperation.getInstance());
			newNode.state.moveTile(args);
			resultingNodes.add(newNode);
			return null;
		}

		private Board deepCopyState(Board state) {
			int [][] board = new int[state.board.length][];
			for (int i = 0; i < state.board.length; i++) {
		        board[i] = Arrays.copyOf(state.board[i], state.board[i].length);
		    }
			return new Board(board);
		}		
	}
}
