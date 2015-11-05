import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// A class representing the search problem of Roll the Ball.
public class RollTheBall extends SearchProblem{
	
	public RollTheBall(Board initialState){
		super(initialState, new ArrayList<Operation>(Arrays.asList(MoveBlockOperation.getInstance())));
	}

	@Override
	public boolean applyGoalTest(Node node){
		// TODO Auto-generated method stub
		return false;
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
