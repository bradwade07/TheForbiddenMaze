package Map;

/**
 * Immutable path finding class. Given a 2D array of CellState objects, it
 * computes if there is a path between cell locations.
 *
 */
public class PathFinder {
	private static final int INVALID_COLOUR = -1;
	private static final int START_COLOUR = 0;

	private final int[][] regionArray;
	private final int width;
	private final int height;

	public PathFinder(final Cell[][] data) {
		height = data.length;
		width = data[0].length;
		regionArray = new int[height][width];

		floodColoursHorizontally(data);
		unifyColoursVertically();
	}

	private void floodColoursHorizontally(final Cell[][] data) {
		int nextColour = START_COLOUR;
		for (int i = 0; i < height; i++) {
			int currentColour = INVALID_COLOUR;
			for (int j = 0; j < width; j++) {
				if (data[i][j].isWallOrBarricade()) {
					currentColour = INVALID_COLOUR;
				} else if (currentColour == INVALID_COLOUR) {
					currentColour = nextColour;
					nextColour++;
				}
				regionArray[i][j] = currentColour;
			}
		}
	}

	private void unifyColoursVertically() {
		// If a cell and one below it are not walls, then both should be the same colour.
		for (int i = 0; i < height - 1; i++) {
			for (int j = 0; j < width; j++) {
				int topColour = regionArray[i][j];
				int bottomColour = regionArray[i + 1][j];
				if (topColour != INVALID_COLOUR && bottomColour != INVALID_COLOUR) {
					replaceAllWith(regionArray, bottomColour, topColour);
				}
			}
		}
	}

	private void replaceAllWith(int[][] data, int replace, int with) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (data[i][j] == replace) {
					data[i][j] = with;
				}
			}
		}
	}

	public boolean hasPath(Point cell1, Point cell2) {
		// There exists a path between the start and the end iff they are
		// the same colour.
		int startColour = regionArray[cell1.getHeight()][cell1.getWidth()];
		int endColour   = regionArray[cell2.getHeight()][cell2.getWidth()];
		return (startColour == endColour);
	}
}
