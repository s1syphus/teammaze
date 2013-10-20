package teammaize.android.com;

public class DataStructures { 
	public enum Directions{
		NORTH, EAST, SOUTH, WEST;
	}
	
	public enum MazeSpaces{
		START ('S'), PATH ('.'), WALL ('X'), ROADBLOCK ('R'), FINISH ('F');
		
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
