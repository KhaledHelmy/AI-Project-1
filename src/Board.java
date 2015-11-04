
public class Board {
	public static class Tile{
		// bit 0 = is movable
		// bit 1 = is left side opened
		// bit 2 = is upper side opened
		// bit 3 = is right side opened
		// bit 4 = is bottom side opened
		// bit 5 = is start tile
		// bit 6 = is end tile
		// bit 
		
		public static boolean isMovable(int tile){
			return (tile & (1 << 0)) != 0;
		}
		
		public static boolean isLeftSideOpened(int tile){
			return (tile & (1 << 1)) != 0;
		}
		
		public static boolean isUpperSideOpened(int tile){
			return (tile & (1 << 2)) != 0;
		}
		
		public static boolean isRightSideOpened(int tile){
			return (tile & (1 << 3)) != 0;
		}
		
		public static boolean isBottomSideOpened(int tile){
			return (tile & (1 << 4)) != 0;
		}
	}
	
	public int board[][];
	public int width;
	public int height;
	
	public Board(int board[][]){
		this.board = board;
		height = board.length;
		width = board[0].length;
	}
}
