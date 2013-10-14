package teammaize.android.com;

import java.util.Stack;

import android.util.Pair;

/**
 * Maze Generation Algorithm
 * Robert Micatka
 * Uses recursive backtracking, outputs result to text file
 *
 */

/*
 * Directions correspond as follows:
 * 0 - North
 * 1 - East
 * 2 - South
 * 3 - West
 */


public class MazeGeneration {
	private final int x;	//rows
	private final int y;	//columns
	private final char[][] maze;	//x by y maze
	
	public MazeGeneration(int x, int y){
		this.x = x;
		this.y = y;
		maze = new char[this.x][this.y];	//create the maze array
		initialize_maze();							//initial all locations to walls (X)
		generate(0,0);						//start the generation at (0,0) or some other start location
	}
	
	public void initialize_maze(){
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				maze[i][j] = 'X';
			}
		}
	}
	
	public void generate(int cur_x, int cur_y){
		Stack<Pair<Integer, Integer>> maze_stack = new Stack<Pair<Integer, Integer>>();
		Pair<Integer, Integer> cur_coords = null, neighbor = null;
		maze_stack.push(Pair.create(cur_x, cur_y));
		
		//add in start soon
		
		while(!maze_stack.isEmpty()){
			cur_coords = maze_stack.pop();
			maze[cur_coords.first][cur_coords.second]  = '.';
			//find neighbors
			for(int i = 0; i < 4; i++){
				neighbor = return_neighbor(cur_coords, i);
				if(neighbor != null){
					maze_stack.push(neighbor);
				}
			}
		}
		maze[cur_coords.first][cur_coords.second] = 'G';
	}
		
		
	
	public Pair<Integer, Integer> return_neighbor(Pair<Integer, Integer> cur_loc, int dir){
	
		Pair<Integer, Integer> neighbor = null;
		
		//check to make sure the potential neighbor hasn't been visited
		//and is within the maze
		
		
		return neighbor;
		
	}
	
	
	
	public void output_to_file(){
		
		
		
	}
	
	
}

