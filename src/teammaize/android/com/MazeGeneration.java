package teammaize.android.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;

import android.util.Pair;

/**
 * Maze Generation Algorithm
 * Robert Micatka
 */

/*
 * Directions correspond as follows:
 * 0 - North
 * 1 - East
 * 2 - South
 * 3 - West
 */

/*
 *	Extensions: Hint of correct path (using the A* used to create the roadblocks)
 */



public class MazeGeneration {
	
	private final int x;	//rows
	private final int y;	//columns
	public final char[][] maze;	//x by y maze
	
	// current location of the player
	public Pair<Integer, Integer> user_coords = new Pair<Integer, Integer>(0, 0);
	
	public MazeGeneration(int x, int y){
		this.x = x;
		this.y = y;
		maze = new char[this.x][this.y];	//create the maze array
		initialize_maze();			//initial all locations to walls (X)
		
		
		System.out.println("After initialization");
		
	//	display_maze();
		
		
		
	//Generate works but is commented out atm to test roadblock generation
	
	//	generate(0,0);				//start the generation at (0,0) or some other start location
		
		//output
		
		
	//	System.out.println("After generation");
		
		//display_maze();
		
		
		/*
		 * Hard-coding the maze
		 */
		
		maze[0][0] = '.';
		maze[0][1] = '.';
		maze[0][2] = '.';
		maze[1][2] = '.';
		maze[1][3] = '.';
		maze[2][3] = '.';
		maze[3][3] = '.';
		maze[3][4] = '.';
		maze[3][5] = '.';
		maze[3][6] = '.';
		maze[4][5] = '.';
		maze[5][5] = '.';
		maze[5][4] = '.';
		maze[5][3] = '.';
		maze[6][3] = '.';
		maze[7][3] = 'G';
		
		
	//	display_maze();
		maze[0][0] = 'S';
//		generate_roadblock();
		
		
		
	}
	
	private void display_maze(){
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				System.out.print(maze[i][j]);
			}
				System.out.println();
		}
	}
		
	private void initialize_maze(){
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				maze[i][j] = 'X';
			}
		}
	}
	
	private void generate(int cur_x, int cur_y){
		
		System.out.println("during generation");
		
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
		//	display_maze();	//for debug
			
			//examine neighbors
			//randomize
			Collections.shuffle(dir_order);
			for(int i = 0; i < 4; i++){
	//			System.out.println("direction: " + dir_order.get(i));
				neighbor = return_next_block(cur_coords, dir_order.get(i), maze_vector);
				if(neighbor != null){
					maze_stack.push(neighbor);
					maze_vector.add(neighbor);
				}
			}
		}
		//maze[cur_coords.first][cur_coords.second] = 'G';
		maze[maze_vector.lastElement().first][maze_vector.lastElement().second] = 'G';
		
	}	
	
	private Pair<Integer, Integer> return_next_block(Pair<Integer, Integer> cur_loc, int dir, Vector<Pair<Integer, Integer>> visited){
	
		Pair<Integer, Integer> neighbor = null;
		
		//check to make sure the potential neighbor hasn't been visited, or is one "block" away from a visited neighbor
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
		
		if((neighbor != null) && available(neighbor, visited, dir)){
			System.out.println("about to add an available neighbor");
			return neighbor;
		}
	
		return null;
		
	}

	
	private boolean available(Pair<Integer, Integer> to_be_examined, Vector<Pair<Integer, Integer>> visited, int coming_from){
		
		//	check if any neighbors of to_be_examined are currently in the visited vector
		//	we require walls between blocks
		
		Pair<Integer, Integer> temp_exam = null;	
		
		//this will take on the location of the potential N, E, S, W blocks
		// can easily be modified to prevent NE, SE, SW, NW locations as well (corners)
		
		/*
		 * Directions correspond as follows: (use enum soon)
		 * 0 - North
		 * 1 - East
		 * 2 - South
		 * 3 - West
		 */
		
		
		//For north
		
		if((to_be_examined.second - 1) >= 0){
			//north is a valid move
			temp_exam = Pair.create(to_be_examined.first, to_be_examined.second - 1);
			if((coming_from != 2) && visited.contains(temp_exam)){
				return false;
			}
		}
		
		//For east
		
		if((to_be_examined.first + 1) < x){
			//north is a valid move
			temp_exam = Pair.create(to_be_examined.first + 1, to_be_examined.second);
			if((coming_from != 3) && visited.contains(temp_exam)){
				return false;
			}
		}
				
		//For south
		if((to_be_examined.second + 1) < y){
			//north is a valid move
			temp_exam = Pair.create(to_be_examined.first, to_be_examined.second + 1);
			if((coming_from != 0) && visited.contains(temp_exam)){
				return false;
			}
		}
		
		//For west
		if((to_be_examined.first - 1) >= 0){
			//north is a valid move
			temp_exam = Pair.create(to_be_examined.first - 1, to_be_examined.second);
			if((coming_from != 1) && visited.contains(temp_exam)){
				return false;
			}
		}		
			
		return true;
	}
	
	public Pair<Integer, Integer> return_neighbor(Pair<Integer, Integer> cur_loc, int dir){

		/*
		 *	return_neighbor function to check for valid moves, used for movement stuff (not overloaded anymore, changed above)
		 *	check to make sure the potential neighbor hasn't been visited
		 *	and is within the maze
		 *
		 */

		Pair<Integer, Integer> neighbor = null;
		
			
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
		
		return neighbor;
	}
	
	
	
	private Pair<Integer, Integer> return_next(Pair<Integer, Integer> cur_loc, int dir){
		/*
		 *	return_neighbor function to check for valid moves, used for movement stuff (not overloaded anymore, changed above)
		 *	check to make sure the potential neighbor hasn't been visited
		 *	and is within the maze
		 *
		 */

		Pair<Integer, Integer> next = null;
		
			
		if(dir == 0){
			//attempting to move north
			if((cur_loc.second - 1) >= 0){
				next = Pair.create(cur_loc.first, cur_loc.second - 1);
			}
		}
		if(dir == 1){
			//attempting to move east
			if((cur_loc.first + 1) < x){
				next = Pair.create(cur_loc.first + 1, cur_loc.second);
			}
		}
		if(dir == 2){
			//attempting to move south
			if((cur_loc.second + 1) < y){
				next = Pair.create(cur_loc.first,  cur_loc.second + 1);
			}
		}
		if(dir == 3){
			//attempting to move west
			if((cur_loc.first - 1) >= 0){				
				next = Pair.create(cur_loc.first - 1,  cur_loc.second);
			}
		}
		
		if((maze[next.first][next.second] == '.') || (maze[next.first][next.second] == 'G')){
			return next;
		}
		
		return null;
	}
	
	private Integer man_dist(Pair<Integer, Integer> start, Pair<Integer, Integer> goal){
		return (Math.abs(goal.first - start.first) + Math.abs(goal.second - start.second));
	}
	
	private Vector<Pair<Integer, Integer>> reconstruct_path(HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> came_from, Pair<Integer, Integer> start, Pair<Integer, Integer> goal){
		
		Vector<Pair<Integer, Integer>> sol = new Vector<Pair<Integer, Integer>>();
		Pair<Integer, Integer> cur, prev;
		boolean found = false;
		cur = goal;
		
		System.out.println("reconstruct path");
		
		while(!found){
			if(cur.equals(start)){
				found = true;
			}
			prev = came_from.get(cur);
			sol.add(cur);
			cur = prev;
		}
		return sol;
	}
	
	
	public Vector<Pair<Integer, Integer>> solve_maze(){

		//openset is a set of nodes that going to be visited
		//closedset is a set of nodes that have been visited
		//F_Score is a sorted treemap, sorted by f_score (key), value is the pair of ints
		//G_score is just a map, key is the pair, value is the g_score (distance from start)
		
		HashSet<Pair<Integer, Integer>> openset = new HashSet<Pair<Integer, Integer>>();
		HashSet<Pair<Integer, Integer>> closedset = new HashSet<Pair<Integer, Integer>>();
		TreeMap<Integer, Pair<Integer, Integer>> f_score = new TreeMap<Integer, Pair<Integer, Integer>>();
		HashMap<Pair<Integer, Integer>, Integer> g_score = new HashMap<Pair<Integer, Integer>, Integer>();
		HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> came_from = new HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>();	//key = cur, value = came_from
		
		Pair<Integer, Integer> start = null, goal = null, cur = null, next = null;
		
		int tent_g, tent_f;
		
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				if(maze[i][j] == 'S'){
					start = Pair.create(i,j);
				}
				if(maze[i][j] == 'G'){
					goal = Pair.create(i,j);
				}
			}
		}
		
		
		System.out.println("start = <" + start.first+", "+start.second+">");
		System.out.println("goal = <" + goal.first+", "+goal.second+">");
		
		//Initialize g_score and f_score
		g_score.put(start, 0);
		f_score.put(g_score.get(start) + man_dist(start, goal), start);
		openset.add(start);
		

		
		while(!openset.isEmpty()){
			cur = f_score.get(f_score.firstKey());
			System.out.println("cur = "+cur.first+", "+cur.second+">");
			
			if(cur.equals(goal)){
				return reconstruct_path(came_from, start, goal);
			}
			
			openset.remove(cur);
			closedset.add(cur);
			
			for(int i = 0; i < 4; i++){
				
				next = return_next(cur, i);
				System.out.println("looking at "+next.first+", "+next.second+">");
				if(next != null){
					tent_g = g_score.get(cur) + 1;
					tent_f = tent_g + man_dist(cur, goal);
					if(!(closedset.contains(next) && (tent_f >= (g_score.get(next) + man_dist(next, goal))))){
						if((!openset.contains(next)) || (tent_f < (g_score.get(next) + man_dist(next, goal)))){
							came_from.put(next, cur);
							g_score.put(next, tent_g);
							f_score.put(tent_f, next);
							if(!openset.contains(next)){
								openset.add(next);
							}
						}	
					}
				}
						
			}
			
		}
		
		System.out.println("solution not found");

		//not found
		
		return null;
		
	}
	

	private void generate_roadblock(){

		
		Vector<Pair<Integer, Integer>> solution = solve_maze();	
		Pair<Integer, Integer> cur_loc = null;
	
		/*
		for(int i = 0; i < solution.size(); i++){
			cur_loc = solution.get(i);
			maze[cur_loc.first][cur_loc.second] = 'R';
		}
		*/
	}
		


}

