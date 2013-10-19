package teammaize.android.com;

import java.util.ArrayList;

public class UserMovement {
	
	public static boolean tryMoveUp(MazeGeneration mazeObj)
	{
		boolean validMove = true;
		if (mazeObj.return_neighbor(mazeObj.user_coords, 0) == null) {
			validMove = false;
		}
		else {
			//update user coords
		}
		return validMove;
	}
	
	public static boolean tryMoveDown(MazeGeneration mazeObj)
	{
		boolean validMove = true;
		if(mazeObj.return_neighbor(mazeObj.user_coords, 2) == null) {
			validMove = false;
		}
		else {
			//update user coords
		}
		return validMove;
	}
	
	public static boolean tryMoveRight(MazeGeneration mazeObj)
	{
		boolean validMove = true;
		if(mazeObj.return_neighbor(mazeObj.user_coords, 1) == null) {
			validMove = false;
		}
		else {
			//update user coords
		}
		return validMove;
	}
	
	public static boolean tryMoveLeft(MazeGeneration mazeObj)
	{
		boolean validMove = true;
		if(mazeObj.return_neighbor(mazeObj.user_coords, 3) == null) {
			validMove = false;
		}
		else {
			//update user coords
		}
		return validMove;
	}
}
