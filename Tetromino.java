package model;

import java.util.ArrayList;
/**
 *     
    Cyan I
    Yellow O
    Purple T
    Green S
    Red Z
    Blue J
    Orange L 
 *
 */
public abstract class Tetromino {
	protected final Cell cell;
	protected final String name;
	
	/**
	 * This list represents the most current coordinates of the
	 * Tetromino piece on the game board 
	 */
	protected ArrayList<Coordinate> locations;
	
	/**
	 * This is an array of ArrayLists where each ArrayList represents
	 * the layout of the Tetromino piece at a given orientation.
	 * Each ArrayList should be designed in such a way where if one
	 * cell of the piece were to be marked as the origin (0,0)
	 * the coordinates of the rest of the cells should maintain 
	 * the shape of the piece.
	 * Once initialized, the layout should NOT be altered
	 * Your initialization of layout up to you
	 * array index = orientation
	 */
	protected ArrayList<Coordinate> [] layout;
	
	protected Game game;
	public abstract boolean rotate();
	public int orientation; // 0 up, 1 right, 2 down, 3 left
	protected int length;   // length of the piece when it is initially inserted
	protected int height;   // height of the piece when it is initially inserted
	
	
	@SuppressWarnings("unchecked")
	public Tetromino(Game game, String name, Cell cell) {
		this.name = name;
		this.cell = cell;
		locations = new ArrayList<>();
		this.game = game;
		layout = (ArrayList<Coordinate> [])(new ArrayList[4]);
		
	}
	
	public Cell getCell() { return cell;}
	
	/**
	 * This will move the piece down by one row. Otherwise known
	 * as a "soft drop." Not to be confused by the fall() method
	 * @return true if allowed, false otherwise
	 */
	public boolean moveDown() {

		// Returning current Origin coordinate
		Coordinate x = getOrigin();

		// Translating Origin for setOrigin method
		Coordinate y = x.translate(0, 1);
		return setOrigin(y);
	}
	
	/**
	 * This will move the piece to the left by one column
	 * @return true if allowed, false otherwise
	 */
	public boolean moveLeft() {

		// Returning current Origin coordinate
		Coordinate x = getOrigin();

		// Translating Origin for setOrigin method
		Coordinate y = x.translate(-1, 0);
		return setOrigin(y);
	}
	
	/**
	 * This will move the piece to the right by one column
	 * @return true if allowed, false otherwise
	 */
	public boolean moveRight() {
		// Returning current Origin coordinate
		Coordinate x = getOrigin();

		// Translating Origin for setOrigin method
		Coordinate y = x.translate(1, 0);

		return setOrigin(y);
	}

	/**
	 * This will trigger a "hard drop." This means move the piece
	 * as far down as you are allowed to do so. Not to be confused
	 * with the moveDown() method. 
	 * @return true if allowed, false otherwise
	 */
	public boolean fall() {

		// Count variable
		int count = 0;

		// Using x and y int variables for new Origin coordinate
		int x = getOrigin().col;
		int y = getOrigin().row;

		// Counting how many times piece can move down
		for (int i = y; i < game.getMaxRows(); i++) {
			if (moveDown()) {
				count++;
			}
		}

		// Creating new Origin
		Coordinate newOrigin = new Coordinate(x, y + count);
		return setOrigin(newOrigin);

	}
	
	/**
	 * This insets the piece at the top center of the game board only if 
	 * all the tiles it will occupy are empty
	 * @param center is a coordinate representing the top center of the game board
	 * @return true if the piece was successfully inserted, false if the piece
	 * 			could not be inserted. 
	 */
	public boolean insertAt(Coordinate center) {

		// Checking if cells that new shape will occupy are empty
		for (int i = 0; i < locations.size(); i++) {
			if (game.getBoardCell(locations.get(i).col, locations.get(i).row) != Cell.EMPTY) {
				return false;
			}
		}
		return setOrigin(center);
	}
	
	/**
	 * This returns the location of the piece's cell that corresponds 
	 * to the origin cell in your layout
	 * @return a Coordinate of the origin cell
	 */
	public final Coordinate getOrigin() {
		// Origin will always be first Coordinate in locations list
		return locations.get(0);
	}
	
	/**
	 * This sets the location of the origin cell to the coordinate co and sets the
	 * remaining cells to be located around co in the appropriate layout.
	 * This checks to see if the tiles it will occupy are already occupied or not 
	 * before updating the locations. Used when moving and rotating pieces around.
	 * @param coordinate of where the new origin cell should be
	 * @return true if the coordinates were able to be set, or false if any of
	 * the coordinates could not be set
	 */
	


	
	public final boolean setOrigin(Coordinate co) {

		boolean setOrigin = true;
		ArrayList<Coordinate> newCoordinates = new ArrayList<>();

		// Creating new coordinate list based off orientation and newOrigin
		for (int i = 0; i < layout[orientation].size(); i++) {
			Coordinate x = null;
			x = co.translate(layout[orientation].get(i).col, layout[orientation].get(i).row);
			newCoordinates.add(x);
		}

		// Checking if new Coordinates is of positive points
		for (int i = 0; i < newCoordinates.size(); i++) {

			int x = newCoordinates.get(i).col;
			int y = newCoordinates.get(i).row;

			if (x <= -1 || y <= -1) {
				setOrigin = false;
				break;
			}

			if (y >= game.getMaxRows() || x >= game.getMaxCols()) {
				setOrigin = false;
				break;
			}
		}

		// Checking if Cells are EMPTY or Same Cell
		if (setOrigin) {
			for (int i = 0; i < newCoordinates.size(); i++) {

				int x = newCoordinates.get(i).col;
				int y = newCoordinates.get(i).row;

				// location[i] okay if cell is empty or if the coordinates overlap where the
				// piece currently is
				if (game.getBoardCell(x, y) != Cell.EMPTY && !locations.contains(newCoordinates.get(i))) {
					// System.out.println(newCoordinates.get(i).equals(locations.get(i + 1)));
					setOrigin = false;
					break;
				}
			}
		}

		// Making old Cells Empty
		if (setOrigin) {
			for (int i = 0; i < locations.size(); i++) {
				game.setBoardCell(locations.get(i).col, locations.get(i).row, Cell.EMPTY);
			}
		}

		// Updating Locations list to new locations (new Coordinates)
		if (setOrigin) {
			for (int i = 0; i < newCoordinates.size(); i++) {
				locations.set(i, newCoordinates.get(i));
				// System.out.println(locations.get(i));
			}
		}

		// Making New Coordinates the Appropriate Color Cell
		if (setOrigin) {
			for (int i = 0; i < locations.size(); i++) {
				game.setBoardCell(locations.get(i).col, locations.get(i).row, cell);
			}
		}
		return setOrigin;
	}
	
	protected boolean positiveCoords(Coordinate co) {

		ArrayList<Coordinate> newCoordinates = new ArrayList<>();

		int ori = 0;

		if (orientation == 3) {
			ori = 0;
		} else {
			ori = orientation + 1;
		}

		// Creating new coordinate list based off orientation and newOrigin
		for (int i = 0; i < layout[ori].size(); i++) {
			Coordinate x = null;
			x = co.translate(layout[ori].get(i).col, layout[ori].get(i).row);
			newCoordinates.add(x);
		}

		// Checking if new Coordinates is of positive points
		for (int i = 0; i < newCoordinates.size(); i++) {

			int x = newCoordinates.get(i).col;
			int y = newCoordinates.get(i).row;

			if (x <= -1 || y <= -1) {
				return false;
			}

			if (y >= game.getMaxRows() || x >= game.getMaxCols()) {
				return false;
			}
		}
		return true;
	}
}
