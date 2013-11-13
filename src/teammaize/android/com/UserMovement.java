package teammaize.android.com;

import teammaize.android.com.DataStructures.Directions;
import teammaize.android.com.DataStructures.MazeSpaces;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

/**
 * User movement through the maze
 * Erika Jansen
 * Uses MazeGeneration, MazeGUI, starts new intents when appropriate
 */

public class UserMovement {
	
	private static MazeGUI mazeGui;
	private static View view;
	private static MazeGeneration mazeObj;
	
	public static boolean tryMove(MazeGeneration mazeObj, Directions dir)
	{
		boolean validMove = true;
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.returnNeighbor(mazeObj.userCoords, dir.ordinal());
			char nextSpace = mazeObj.maze[nextCoords.first][nextCoords.second];
			if(nextCoords == null || nextSpace == MazeSpaces.WALL.SpaceChar()) {
				validMove = false;
			}
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in tryMove: " + dir.ordinal() + " "+ e.toString());
			validMove = false;
		}
		
		return validMove;
	}
	
	public static MazeGeneration movePlayer(MazeGeneration maze_obj, View v, MazeGUI maze_gui, Directions dir) {
		mazeObj = maze_obj;
		view = v;
		mazeGui = maze_gui;
		
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.returnNeighbor(mazeObj.userCoords, dir.ordinal());
			return movePlayer(nextCoords);
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in movePlayer: " + dir + " " + e.toString());
			return null;
		}
		
	}
	
	private static MazeGeneration movePlayer(Pair<Integer, Integer> nextCoords) {
		char nextSpace = mazeObj.maze[nextCoords.first][nextCoords.second];
		if(nextSpace == MazeSpaces.PATH.SpaceChar() || nextSpace == MazeSpaces.START.SpaceChar()) {
			//move user (update userCoords to nextCoords)
			mazeObj.userCoords = nextCoords;
		}
		else if(nextSpace == MazeSpaces.ROADBLOCK.SpaceChar()) {
			//update userCoords and start roadblock intent
			mazeObj.userCoords = nextCoords;
			
			mazeGui.roadBlockEnc(view);
		}
		else if(nextSpace == MazeSpaces.GOAL.SpaceChar()) {
			//update userCoords and congratulate player (offer to return to start menu or start new maze
			Toast toast = Toast.makeText(mazeGui, "Congratulations!", Toast.LENGTH_LONG);
			mazeObj.userCoords = nextCoords;
		}
		else {
			Log.v("UserMovement", "Unexpected character encountered in the next space to move to.");
		}
		return mazeObj;
	}
	
}
