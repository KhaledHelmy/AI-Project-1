package eg.edu.guc.ai;

public class Board extends State {
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

		public static boolean isBlank(int tile) {
			return !isMovable(tile) &&
					!isLeftSideOpened(tile) &&
					!isRightSideOpened(tile) &&
					!isUpperSideOpened(tile) &&
					!isBottomSideOpened(tile);
		}

		public static boolean isBlock(int tile) {
			return isMovable(tile) &&
					!isLeftSideOpened(tile) &&
					!isRightSideOpened(tile) &&
					!isUpperSideOpened(tile) &&
					!isBottomSideOpened(tile);
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
		int x = args[0];
		int y = args[1];
		int newX = args[0] + args[2];
		int newY = args[1] + args[3];
		if (newX >= 0 && newX < height && newY >= 0 && newY < width) {
			if (Tile.isMovable(this.board[x][y]) && Tile.isBlank(this.board[newX][newY])) {
				int blankTile = 0;
				blankTile |= (this.board[x][y] & (1 << 5));
				blankTile |= (this.board[x][y] & (1 << 6));
				this.board[newX][newY] = this.board[x][y];
				this.board[newX][newY] |= (this.board[newX][newY] & (1 << 5));
				this.board[newX][newY] |= (this.board[newX][newY] & (1 << 6));
				this.board[x][y] = blankTile;
				return;
			}
		}
		throw new RuntimeException("Cannot move tile!");
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (int i=0; i<this.height; i++) {
			for (int j=0; j<this.width; j++) {
				result += this.board[i][j];
				result %= 2000000000;
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Board b = (Board) obj;
		if (this.height == b.height && this.width == b.width) {
			for (int i=0; i<this.height; i++) {
				for (int j=0; j<this.width; j++) {
					if (this.board[i][j] != b.board[i][j]) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String visualize() {
		char[][] map = new char[this.height*3][this.width*3];
		for (int i=0; i<this.height; i++) {
			for (int j=0; j<this.width; j++) {
				int mappedI = i * 3;
				int mappedJ = j * 3;
				char left = Tile.isLeftSideOpened(this.board[i][j]) ? ' ' : '|';
				char right = Tile.isRightSideOpened(this.board[i][j]) ? ' ' : '|';
				char up = Tile.isUpperSideOpened(this.board[i][j]) ? ' ' : '_';
				char down = Tile.isBottomSideOpened(this.board[i][j]) ? ' ' : '-';
				char special = ' ';
				//S --> StartTile, E --> EndTile, B --> BlankTile, C --> BlockTile
				if (Tile.isStart(this.board[i][j])) special = 'S';
				else if (Tile.isEnd(this.board[i][j])) special = 'E';
				else if (Tile.isBlank(this.board[i][j])) special = 'B';
				else if (Tile.isBlock(this.board[i][j])) special = 'C';
				map[mappedI][mappedJ] = ' ';
				map[mappedI][mappedJ+1] = up;
				map[mappedI][mappedJ+2] = ' ';
				map[mappedI+1][mappedJ] = left;
				map[mappedI+1][mappedJ+1] = special;
				map[mappedI+1][mappedJ+2] = right;
				map[mappedI+2][mappedJ] = ' ';
				map[mappedI+2][mappedJ+1] = down;
				map[mappedI+2][mappedJ+2] = ' ';
			}
		}
		StringBuilder result = new StringBuilder();
		for (int i=0; i<map.length; i++) {
			for (int j=0; j<map[i].length; j++) {
				result.append(map[i][j]);
			}
			result.append(System.lineSeparator());
		}
		for (int i=0; i<this.width*3; i++) {
			result.append("=");
		}
		result.append(System.lineSeparator());
		return result.toString();
	}
}
