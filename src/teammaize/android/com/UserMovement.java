package teammaize.android.com;

import teammaize.android.com.DataStructures.Directions;
import teammaize.android.com.DataStructures.MazeSpaces;

import android.util.Log;
import android.util.Pair;
import android.view.View;

/**
 * User movement through the maze
 * Erika Jansen
 * Uses MazeGeneration, MazeGUI, starts new intents when appropriate
 */

public class UserMovement {
	
	private static MazeGUI maze_gui;
	private static View view;
	private static MazeGeneration mazeObj;
	
	public static boolean tryMoveNorth(MazeGeneration mazeObj)
	{
		boolean validMove = true;
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.return_neighbor(mazeObj.user_coords, 0);
			char nextSpace = mazeObj.maze[nextCoords.first][nextCoords.second];
			if (nextCoords == null || nextSpace == MazeSpaces.WALL.SpaceChar()) {
				validMove = false;
			}
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in tryMoveNorth. " + e.toString());
			validMove = false;
		}
		return validMove;
	}
	
	public static boolean tryMoveSouth(MazeGeneration mazeObj)
	{
		boolean validMove = true;
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.return_neighbor(mazeObj.user_coords, 2);
			char nextSpace = mazeObj.maze[nextCoords.first][nextCoords.second];
			if(nextCoords == null || nextSpace == MazeSpaces.WALL.SpaceChar()) {
				validMove = false;
			}
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in tryMoveSouth. " + e.toString());
			validMove = false;
		}
		return validMove;
	}
	
	public static boolean tryMoveEast(MazeGeneration mazeObj)
	{
		boolean validMove = true;
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.return_neighbor(mazeObj.user_coords, 1);
			char nextSpace = mazeObj.maze[nextCoords.first][nextCoords.second];
			if(nextCoords == null || nextSpace == MazeSpaces.WALL.SpaceChar()) {
				validMove = false;
			}
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in tryMoveEast. " + e.toString());
			validMove = false;
		}
		return validMove;
	}
	
	public static boolean tryMoveWest(MazeGeneration mazeObj)
	{
		boolean validMove = true;
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.return_neighbor(mazeObj.user_coords, 3);
			char nextSpace = mazeObj.maze[nextCoords.first][nextCoords.second];
			if(nextCoords == null || nextSpace == MazeSpaces.WALL.SpaceChar()) {
				validMove = false;
			}
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in tryMoveWest. " + e.toString());
			validMove = false;
		}
		
		return validMove;
	}
	
	public static MazeGeneration movePlayerNorth(MazeGeneration maze_obj, View v, MazeGUI mazeGui) {
		mazeObj = maze_obj;
		view = v;
		maze_gui = mazeGui;
		
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.return_neighbor(mazeObj.user_coords, Directions.NORTH.ordinal());
			return movePlayer(nextCoords);
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in movePlayerNorth. " + e.toString());
			return null;
		}
		
	}
	
	public static MazeGeneration movePlayerEast(MazeGeneration maze_obj, View v, MazeGUI mazeGui) {
		mazeObj = maze_obj;
		view = v;
		maze_gui = mazeGui;
		
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.return_neighbor(mazeObj.user_coords, Directions.EAST.ordinal());
			return movePlayer(nextCoords);
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in movePlayerEast. " + e.toString());
			return null;
		}
		
	}
	
	public static MazeGeneration movePlayerSouth(MazeGeneration maze_obj, View v, MazeGUI mazeGui) {
		mazeObj = maze_obj;
		view = v;
		maze_gui = mazeGui;
		
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.return_neighbor(mazeObj.user_coords, Directions.SOUTH.ordinal());
			return movePlayer(nextCoords);
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in movePlayerSouth. " + e.toString());
			return null;
		}
	}
	
	public static MazeGeneration movePlayerWest(MazeGeneration maze_obj, View v, MazeGUI mazeGui) {
		mazeObj = maze_obj;
		view = v;
		maze_gui = mazeGui;
		
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.return_neighbor(mazeObj.user_coords, Directions.WEST.ordinal());
			return movePlayer(nextCoords);
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in movePlayerWest. " + e.toString());
			return null;
		}
	}
	
	private static MazeGeneration movePlayer(Pair<Integer, Integer> nextCoords) {
		char nextSpace = mazeObj.maze[nextCoords.first][nextCoords.second];
		if(nextSpace == MazeSpaces.PATH.SpaceChar()) {
			//move user (update user_coords to nextCoords)
			mazeObj.user_coords = nextCoords;
		}
		else if(nextSpace == MazeSpaces.ROADBLOCK.SpaceChar()) {
			//update user_coords and start roadblock intent
			mazeObj.user_coords = nextCoords;
			
			maze_gui.roadBlockEnc(view);
		}
		else if(nextSpace == MazeSpaces.FINISH.SpaceChar()) {
			//update user_coords and congratulate player (offer to return to start menu or start new maze
			
			mazeObj.user_coords = nextCoords;
		}
		else {
			Log.v("UserMovement", "Unexpected character encountered in the next space to move to.");
		}
		return mazeObj;
	}
	
}
