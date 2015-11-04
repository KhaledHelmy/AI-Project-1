import java.util.ArrayList;
import java.util.Arrays;
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
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
