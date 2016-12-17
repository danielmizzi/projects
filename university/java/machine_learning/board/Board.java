package ics2207.board;

public class Board {
    
    private final int size;
    
    private final boolean base[][];
    
    public Board(final int size) {
	this.size = size;
	
	this.base = new boolean[size][size];
    }
    
    public int getSize() {
	return size;
    }
    
    public boolean isQueen(final int x, final int y) {
	return base[x][y];
    }
    
    public void setEmpty(final int[] ... positions) {
	for (int[] position : positions) {
	    base[position[0]][position[1]] = false;
	}
    }
    
    public void setQueens(final int[] ... positions) {
	for (int[] position : positions) {
	    base[position[0]][position[1]] = true;
	}	
    }
    
    public int[][] getQueens() {
	final int[][] positions = new int[size][1];
	
	int totalAdded = 0;
	
	for (int x = 0; x < size; x ++) {
	    for (int y = 0; y < size ; y ++) {
		if (isQueen(x, y)) {
		    positions[totalAdded] = new int[]{x, y};
		    totalAdded++;
		}
	    }
	}
	return positions;
    }
}