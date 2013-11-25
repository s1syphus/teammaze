package teammaize.android.com;

/**
 * DataStructures Class to hold enums to make code more readable
 * Erika Jansen
 * MazeSpaces for the types of spaces in the char[][] maze
 * Directions for the user movement (and in creating the maze)
 */

public class DataStructures { 
	public enum Directions{
		NORTH, EAST, SOUTH, WEST;
	}
	
	public enum MazeSpaces{
		START ('S'), PATH ('.'), WALL ('X'), ROADBLOCK ('R'), PASSED ('P'), GOAL ('G');
		
		private final char spaceChar;
		
		MazeSpaces(char space)
		{
			spaceChar = space;
		}
		
		char SpaceChar() {
			return spaceChar;
		}
	}
}
