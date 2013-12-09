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
	private static MazeGeneration mazeObj;
	private Pair<Integer, Integer> loc;
	private Pair<Integer, Integer> prevLoc;
	
	public UserMovement(Pair<Integer, Integer> startLoc)
	{
		loc = startLoc;
		prevLoc = null;
	}
	
	public void setCurLoc(Pair<Integer, Integer> newLoc)
	{
		loc = newLoc;
	}
	
	public Pair<Integer, Integer> getCurLoc()
	{
		return loc;
	}
	
	//For use with roadblocks to track where the player attempted to move
	public void setLastLoc(Pair<Integer, Integer> nextLoc)
	{
		prevLoc = nextLoc;
	}
	
	//Only for use with roadblocks
	public Pair<Integer, Integer> getLastLoc()
	{
		return prevLoc;
	}
	
	public boolean tryMove(MazeGeneration maze_obj, Directions dir)
	{
		mazeObj = maze_obj;
		boolean validMove = true;
		
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.returnNeighbor(loc, dir.ordinal());
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
	
	public void movePlayer(View view, MazeGUI maze_gui, Directions dir) {
		mazeGui = maze_gui;
		
		try {
			Pair<Integer, Integer> nextCoords = mazeObj.returnNeighbor(loc, dir.ordinal());
			char nextSpace = mazeObj.maze[nextCoords.first][nextCoords.second];
			
			Log.v("UserMovement", "Player moving to " + nextCoords.first + " " + nextCoords.second);
			
			if(nextSpace == MazeSpaces.PATH.SpaceChar() || nextSpace == MazeSpaces.START.SpaceChar()
					|| nextSpace == MazeSpaces.PASSED.SpaceChar()) {
				//move user (update userCoords to nextCoords)
				prevLoc = loc;
				loc = nextCoords;
			}
			else if(nextSpace == MazeSpaces.ROADBLOCK.SpaceChar()) {
				//start roadblock intent and save these userCoords
				mazeGui.roadBlockEnc(view);
				prevLoc = loc;
				loc = nextCoords;
			}
			else if(nextSpace == MazeSpaces.GOAL.SpaceChar()) {
				//update userCoords and congratulate player
				prevLoc = loc;
				loc = nextCoords;
				
				//start new game dialog
				mazeGui.finishedMaze();
			}
			else {
				Log.v("UserMovement", "Unexpected character encountered in the next space to move to.");
			}
		}
		catch(Exception e) {
			Log.v("UserMovement", "Exception caught in movePlayer: " + dir + " " + e.toString());
		}
		
	}
}
