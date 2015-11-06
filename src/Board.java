
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

		public static boolean isStart(int tile) {
			return (tile & (1 << 5)) != 0;
		}

		public static boolean isEnd(int tile) {
			return (tile & (1 << 6)) != 0;
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

	public void moveTile(int... args) {
		int oldLocationTile = this.board[args[0]][args[1]];
		int newLocationTile = this.board[args[0]+args[2]][args[1]+args[3]];
		if (Tile.isMovable(oldLocationTile) && isBlank(newLocationTile)) {
			int blankTile = 0;
			blankTile |= (oldLocationTile & (1 << 5));
			blankTile |= (oldLocationTile & (1 << 6));
			this.board[args[0]+args[2]][args[1]+args[3]] = this.board[args[0]][args[1]];
			this.board[args[0]+args[2]][args[1]+args[3]] |= (newLocationTile & (1 << 5));
			this.board[args[0]+args[2]][args[1]+args[3]] |= (newLocationTile & (1 << 6));
			this.board[args[0]][args[1]] = blankTile;
		} else {
			throw new RuntimeException("Tile is not movable");
		}
	}

	private boolean isBlank(int tile) {
		return !Tile.isMovable(tile) &&
				!Tile.isLeftSideOpened(tile) &&
				!Tile.isRightSideOpened(tile) &&
				!Tile.isUpperSideOpened(tile) &&
				!Tile.isBottomSideOpened(tile);
	}
}
