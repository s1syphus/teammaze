package teammaize.android.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import android.util.Pair;

/**
 * Maze Generation Algorithm
 * Robert Micatka
 * Uses recursive backtracking, outputs result to text file
 */

/*
 * Directions correspond as follows:
 * 0 - North
 * 1 - East
 * 2 - South
 * 3 - West
 */

public class MazeGeneration {
	//public final char start = '.';
	
	private final int x;	//rows
	private final int y;	//columns
<<<<<<< HEAD
	private final Vector<Pair<Integer, Integer>> maze_vector = new Vector<Pair<Integer, Integer>>();
=======
	private final char[][] maze;	//x by y maze
	
>>>>>>> a25166f03651bd7702d566f6de8bd61c3dc23e98
	
	public final char[][] maze;	//x by y maze
	
	// current location of the player
	public Pair<Integer, Integer> user_coords = new Pair<Integer, Integer>(0, 0);
	
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
		Vector<Pair<Integer, Integer>> maze_vector = new Vector<Pair<Integer, Integer>>();
		Pair<Integer, Integer> cur_coords = null, neighbor = null;
		maze_stack.push(Pair.create(cur_x, cur_y));
		maze_vector.add(Pair.create(cur_x,cur_y));
		List<Integer> dir_order = new ArrayList<Integer>();
		dir_order.add(0); 
		dir_order.add(1);
		dir_order.add(2);
		dir_order.add(3);
		
		
		while(!maze_stack.isEmpty()){
			cur_coords = maze_stack.pop();
			maze[cur_coords.first][cur_coords.second]  = '.';
			//examine neighbors
			//randomize
			Collections.shuffle(dir_order);
			for(int i = 0; i < 4; i++){
				neighbor = return_neighbor(cur_coords, dir_order.get(i), maze_vector);
				if(neighbor != null){
					maze_stack.push(neighbor);
					maze_vector.add(neighbor);
				}
			}
		}
		maze[cur_coords.first][cur_coords.second] = 'G';
	}	
	
	public Pair<Integer, Integer> return_neighbor(Pair<Integer, Integer> cur_loc, int dir, Vector<Pair<Integer, Integer>> visited){
	
		Pair<Integer, Integer> neighbor = null;
		
		//check to make sure the potential neighbor hasn't been visited
		//and is within the maze
			
		if(dir == 0){
			//attempting to move north
			if((cur_loc.second - 1) >= 0){
				neighbor = Pair.create(cur_loc.first, cur_loc.second - 1);
			}
		}
		if(dir == 1){
			//attempting to move east
			if((cur_loc.first + 1) < x){
				neighbor = Pair.create(cur_loc.first + 1, cur_loc.second);
			}
		}
		if(dir == 2){
			//attempting to move south
			if((cur_loc.second + 1) < y){
				neighbor = Pair.create(cur_loc.first,  cur_loc.second + 1);
			}
		}
		if(dir == 3){
			//attempting to move west
			if((cur_loc.first - 1) >= 0){
				neighbor = Pair.create(cur_loc.first - 1,  cur_loc.second);
			}
		}
		
		if(!visited.contains(neighbor)){
			return neighbor;
		}
		
		return null;
		
	}
	

	public void output_to_file(){
		
		
		
	}
	
	public void generate_roadblock(int start_x, int start_y){
	
		/*
		 * Solve the maze
		 * Use solved path and place roadblocks every 5 blocks (arbitrary)
		 * Also add the start location in the array
		 */
		
		Stack<Pair<Integer, Integer>> solution = solve_maze(start_x,start_y);
		Pair<Integer, Integer> cur_loc = null;
		int counter = 0;
		
		while(!solution.isEmpty()){
				counter++;
				cur_loc = solution.pop();
				if((counter % 5) == 0){
					maze[cur_loc.first][cur_loc.second] = 'R';
				}
		}
				
		
	}
	
	public Stack<Pair<Integer, Integer>> solve_maze(int start_x, int start_y){
		/*
		 * Solving the maze using DFS (potentially change to A* later if speed/efficiency is needed)
		 */
		
		Stack<Pair<Integer, Integer>> sol_stack = new Stack<Pair<Integer, Integer>>();
		Vector<Pair<Integer, Integer>> sol_vector = new Vector<Pair<Integer, Integer>>();
		Pair<Integer, Integer> cur_coords = null, next_coords = null;
		
		sol_stack.push(Pair.create(start_x, start_y));
		sol_vector.add(Pair.create(start_x, start_y));
		
		
		while(!sol_stack.isEmpty()){
			cur_coords = sol_stack.pop();
			
			if(maze[cur_coords.first][cur_coords.second] == 'G'){
					//found goal, solution is the remainder of the stack
					return sol_stack;
			}
			
			for(int i = 0; i < 4; i++){
				next_coords = return_neighbor(cur_coords,i, sol_vector);
				if(next_coords != null){
					sol_stack.push(next_coords);
					sol_vector.add(next_coords);
				}
			}
		}
		
		//solution not found...this is a problem
		
		return null;
		
		
	}
	
	
	
	
}

