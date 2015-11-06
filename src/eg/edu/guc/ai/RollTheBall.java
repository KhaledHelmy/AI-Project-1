package eg.edu.guc.ai;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import eg.edu.guc.ai.Board.Tile;

// A class representing the search problem of Roll the Ball.
public class RollTheBall extends SearchProblem{
	
	public RollTheBall(Board initialState){
		super(initialState, new ArrayList<Operation>(Arrays.asList(MoveTileOperation.getInstance())));
	}

	public int Heuristic1(Node node){
		Board board = (Board)node.state;
		int[][] grid = board.board;
		int nrows = grid.length;
		int ncolumns = grid[0].length;
		int startX = -1, startY = -1, endX = -1, endY = -1;
		
		for (int i=0; i<nrows; i++) {
			for (int j=0; j<ncolumns; j++) {
				if (Tile.isStart(grid[i][j])) {
					startX = i;
					startY = j;
				}
				if (Tile.isEnd(grid[i][j])) {
					endX = i;
					endY = j;
				}
			}
		}
		
		int[] deltaX = {0, 0, 1, -1};
		int[] deltaY = {1, -1, 0, 0};
		
		int result = Integer.MAX_VALUE;
		
		for(int x1 = 0; x1 < nrows; x1++){
			for(int y1 = 0; y1 < ncolumns; y1++){
				if(findPath(startX, startY, x1, y1, deltaX, deltaY, board, new boolean[nrows][ncolumns])){
					for(int x2 = 0; x2 < nrows; x2++){
						for(int y2 = 0; y2 < ncolumns; y2++){
							if(findPath(x2,  y2, endX, endY, deltaX, deltaY, board, new boolean[nrows][ncolumns])){
								result = Math.min(result, Math.abs(x1 - x2) + Math.abs(y1 - y2));
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	public int Heuristic2(Node node){
		return 0;
	}
	
	@Override
	public Queue<Node> expand(Node node) {
		Queue<Node> children = new LinkedList<Node>();
		Operation operator = this.operations.get(0);
		Board state = (Board) node.state;
		int[] deltaX = {0, 0, 1, -1};
		int[] deltaY = {1, -1, 0, 0};
		for (int i=0; i<state.height; i++) {
			for (int j=0; j<state.width; j++) {
				for (int k=0; k<4; k++) {
					Queue<Node> resultingNodes = operator.execute(node, i, j, deltaX[k], deltaY[k]);
					if (resultingNodes != null) {
						for (Node resultingNode : resultingNodes) {
							if (!visitedStates.contains(resultingNode.state)) {
								visitedStates.add(state);
								children.add(resultingNode);
							}
						}
					}
				}
			}
		}
		return children;
	}

	@Override
	public boolean applyGoalTest(Node node){
		Board state = (Board) node.state;
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
		return findPath(startX, startY, endX, endY, deltaX, deltaY, state, vis);
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
				if ((k == 0 && Tile.isLeftSideOpened(state.board[newX][newY]) && Tile.isRightSideOpened(state.board[curX][curY])) ||
						(k == 1 && Tile.isRightSideOpened(state.board[newX][newY]) && Tile.isLeftSideOpened(state.board[curX][curY])) ||
						(k == 2 && Tile.isUpperSideOpened(state.board[newX][newY]) && Tile.isBottomSideOpened(state.board[curX][curY])) ||
						(k == 3 && Tile.isBottomSideOpened(state.board[newX][newY]) && Tile.isUpperSideOpened(state.board[curX][curY]))) {
					result |= findPath(newX, newY, endX, endY, deltaX, deltaY, state, vis);
				}
			}
		}
		return result;
	}

	// A class representing the move block operation
	public static class MoveTileOperation implements Operation{
		private static MoveTileOperation instance = new MoveTileOperation();
		
		public static MoveTileOperation getInstance(){
			return instance;
		}
		
		// args contain 4 ints, row, col, deltaX, deltaY
		// [row, column] is the cell of the block to be moved.
		// [deltaX, deltaY] is the change to be applied to the co-ordinates of the block.
		// i.e. the block moves from [row, column] to [row + deltaX, column + deltaY].
		@Override
		public Queue<Node> execute(Node node, int... args) {
			Board state = (Board) node.state;
			int x = args[0];
			int y = args[1];
			int newX = args[0] + args[2];
			int newY = args[1] + args[3];
			if (newX >= 0 && newX < state.height && newY >= 0 && newY < state.width) {
				if (Tile.isMovable(state.board[x][y]) && Tile.isBlank(state.board[newX][newY])) {
					Queue<Node> resultingNodes = new LinkedList<Node>();
					Node newNode = new Node(1, node, deepCopyState(state),
							MoveTileOperation.getInstance());
					Board newState = (Board) newNode.state;
					newState.moveTile(args);
					resultingNodes.add(newNode);
					return resultingNodes;
				}
			}
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
