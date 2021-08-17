package model;

/**
 * 
 *  Piece: J
 *  Orientation:  
 *        up       left      down      right
 *       _____     _____     _____     _____
 *     0|  *  |  0|     |  0|  ** |  0| *   |
 *     1|  *  |  1| *** |  1|  *  |  1| *** | 
 *     2| **  |  2|   * |  2|  *  |  2|     |
 *     3|_____|  3|_____|  3|_____|  3|_____|
 *       01234     01234     01234     01234
 */

import java.util.ArrayList;

public class J extends Tetromino {

	// Origin Coordinate
	Coordinate Origin = new Coordinate(0, 0);

	// layout[0] - UP
	Coordinate twoUp = new Coordinate(0, 1);
	Coordinate threeUp = new Coordinate(0, 2);
	Coordinate fourUp = new Coordinate(-1, 2);

	// layout[1] - LEFT
	Coordinate twoLeft = new Coordinate(1, 0);
	Coordinate threeLeft = new Coordinate(2, 0);
	Coordinate fourLeft = new Coordinate(2, 1);

	// layout[2] - DOWN
	Coordinate twoDown = new Coordinate(0, -1);
	Coordinate threeDown = new Coordinate(0, -2);
	Coordinate fourDown = new Coordinate(1, -2);

	// layout[3] - RIGHT
	Coordinate twoRight = new Coordinate(-1, 0);
	Coordinate threeRight = new Coordinate(-2, 0);
	Coordinate fourRight = new Coordinate(-2, -1);

	public J(Game game) {
		/**
		 * Constructor. You may want to modify
		 * 
		 * @param game used in the call to super constructor
		 */
		super(game, "J", Cell.BLUE);

		int center = game.getMaxCols() / 2;

		Coordinate a = new Coordinate(center, 0);
		Coordinate b = new Coordinate(center, 1);
		Coordinate c = new Coordinate(center, 2);
		Coordinate d = new Coordinate(center - 1, 2);

		// locations
		locations.add(a);
		locations.add(b);
		locations.add(c);
		locations.add(d);
		
		// ArrayLists

		layout[0] = new ArrayList<>();
		layout[1] = new ArrayList<>();
		layout[2] = new ArrayList<>();
		layout[3] = new ArrayList<>();

		// layout[0]
		layout[0].add(Origin);
		layout[0].add(twoUp);
		layout[0].add(threeUp);
		layout[0].add(fourUp);

		// layout[1]
		layout[1].add(Origin);
		layout[1].add(twoLeft);
		layout[1].add(threeLeft);
		layout[1].add(fourLeft);

		// layout[2]
		layout[2].add(Origin);
		layout[2].add(twoDown);
		layout[2].add(threeDown);
		layout[2].add(fourDown);

		// layout[3]
		layout[3].add(Origin);
		layout[3].add(twoRight);
		layout[3].add(threeRight);
		layout[3].add(fourRight);

	}

	/**
	 * rotates the piece counter-clockwise. See above orientation for reference on
	 * which tile to rotate around.
	 */
	@Override
	public boolean rotate() {

		boolean rotate = true;

		Coordinate newOrigin = null;

		switch (orientation) {
		case 0:
			newOrigin = getOrigin().translate(-1, 1);
			/*
			if (newOrigin.col < 0 || newOrigin.row < 0) {
				rotate = false;
				break;
			} else {
				orientation++;
				setOrigin(newOrigin);
				break;
			}
			*/
			if(!positiveCoords(newOrigin)) {
				rotate = false;
				break;
			}else {
				orientation++;
				setOrigin(newOrigin);
				break;
			}
		case 1:
			newOrigin = getOrigin().translate(1, 1);
			/*
			if (newOrigin.col < 0 || newOrigin.row < 0) {
				rotate = false;
				break;
			} else {
				orientation++;
				setOrigin(newOrigin);
				break;
			}
			*/
			if(!positiveCoords(newOrigin)) {
				rotate = false;
				break;
			}else {
				orientation++;
				setOrigin(newOrigin);
				break;
			}
		case 2:
			newOrigin = getOrigin().translate(1, -1);
			/*
			if (newOrigin.col < 0 || newOrigin.row < 0) {
				rotate = false;
				break;
			} else {
				orientation++;
				setOrigin(newOrigin);
				break;
			}
			*/
			if(!positiveCoords(newOrigin)) {
				rotate = false;
				break;
			}else {
				orientation++;
				setOrigin(newOrigin);
				break;
			}
		case 3:
			newOrigin = getOrigin().translate(-1, -1);
			/*
			if (newOrigin.col < 0 || newOrigin.row < 0) {
				rotate = false;
				break;
			} else {
				orientation = 0;
				setOrigin(newOrigin);
				break;
			}
			*/
			if(!positiveCoords(newOrigin)) {
				rotate = false;
				break;
			}else {
				orientation = 0;
				setOrigin(newOrigin);
				break;
			}
		}

		return rotate;
	}
}
