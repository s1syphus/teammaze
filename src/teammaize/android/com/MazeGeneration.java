package teammaize.android.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
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
 *	To do in this class:
 *
 *	1. Change names of functions/variables to fit naming scheme
 *	2. Use ENUM for directions
 *	3. Rewrite/minimize "returnNeighbor" functions
 *	4. Remove functions that are only used for debugging
 *	5. Comment "completely"/clean up code in general
 *	6. Optimize loops etc. (optional, if performance is fine, no need to change)
 *
 *
 */

public class MazeGeneration {
	
	private final int x;	//rows
	private final int y;	//columns
	public final char[][] maze;	//x by y maze
	
	// current location of the player
	public Pair<Integer, Integer> user_coords = new Pair<Integer, Integer>(0, 0);
	
	private class myComparator implements Comparator<Pair<Integer, Pair<Integer, Integer>>>{
		public int compare(Pair<Integer, Pair<Integer, Integer>> a, Pair<Integer, Pair<Integer, Integer>> b){
			if(a.first == b.first){
				return 0;
			}
			if(a.first > b.first){
				return 1;
			}
			return -1;
		}
	}
	
	
	
	public MazeGeneration(int x, int y){
		this.x = x;
		this.y = y;
		maze = new char[this.x][this.y];	//create the maze array
		initialize_maze();			//initial all locations to walls (X)
		
		
		System.out.println("After initialization");
		
	
		
	//Generate works but is commented out atm to test roadblock generation
	
		generate(0,0);				//start the generation at (0,0) or some other start location
		
		
		//display_maze();
		
		/*
		 * Hard-coding the maze, this is only for roadblock debugging atm, delete soon
		 */
		
		/*
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
		
		maze[0][0] = 'S';
		*/
		
		
		/*
		 * Manual debug maze...ugh
		 */
		/*
		maze[0][0] = 'S';
		maze[0][1] = '.';
		maze[0][2] = '.';
		maze[0][3] = '.';
		maze[1][0] = '.';
		maze[1][2] = '.';
		maze[1][4] = '.';
		maze[2][1] = '.';
		maze[2][2] = '.';		
		maze[2][3] = '.';
		maze[2][4] = '.';
		maze[2][5] = '.';
		maze[3][2] = '.';
		maze[3][4] = 'G';
		maze[4][1] = '.';
		maze[4][2] = '.';
		maze[4][3] = '.';
		maze[4][5] = '.';

		display_maze();
		*/
		
		generateRoadblock();
		
		
		
	}

	//this function is only for debugging purposes, delete soon

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
	
	private void generate(int startX, int startY){
		
		System.out.println("during generation");
		
		Stack<Pair<Integer, Integer>> maze_stack = new Stack<Pair<Integer, Integer>>();
		Vector<Pair<Integer, Integer>> maze_vector = new Vector<Pair<Integer, Integer>>();
		Pair<Integer, Integer> cur_coords = null, neighbor = null;
		maze_stack.push(Pair.create(startX, startY));
		maze_vector.add(Pair.create(startX, startY));
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
				neighbor = return_next_block(cur_coords, dir_order.get(i), maze_vector);
				if(neighbor != null){
					maze_stack.push(neighbor);
					maze_vector.add(neighbor);
				}
			}
		}
		//add the start and goal
		maze[startX][startY] = 'S';
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
	
	
	
	private Pair<Integer, Integer> returnNext(Pair<Integer, Integer> cur_loc, int dir){
		/*
		 *	return_neighbor function to check for valid moves, used for movement stuff (not overloaded anymore, changed above)
		 *	check to make sure the potential neighbor hasn't been visited
		 *	and is within the maze
		 *
		 */

		Pair<Integer, Integer> next = null;
		
	//	System.out.println("curloc = <"+cur_loc.first+", "+cur_loc.second+"> dir = "+dir);
			
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
		
		if((next != null) && (maze[next.first][next.second] != 'X')){
			return next;
		}
		
				
		return null;
	}
	
	private Integer manDist(Pair<Integer, Integer> start, Pair<Integer, Integer> goal){
		
		return (Math.abs(goal.first - start.first) + Math.abs(goal.second - start.second));
	}
	
	private Vector<Pair<Integer, Integer>> reconstructPath(HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> cameFrom, Pair<Integer, Integer> start, Pair<Integer, Integer> goal){

		Vector<Pair<Integer, Integer>> sol = new Vector<Pair<Integer, Integer>>();
		Pair<Integer, Integer> cur, prev;
		boolean found = false;
		cur = goal;
		while(!found){
			if(cur.equals(start)){
				found = true;
			}
			prev = cameFrom.get(cur);
			sol.add(cur);
			cur = prev;
		}
		return sol;
	}
	
	/*
	
	public Vector<Pair<Integer, Integer>> solve_maze(){

		//openset is a set of nodes that going to be visited
		//closedset is a set of nodes that have been visited
		//F_Score is a sorted treemap, sorted by f_score (key), value is the pair of ints
		//G_score is just a map, key is the pair, value is the g_score (distance from start)
		
		//HashSet<Pair<Integer, Integer>> openset = new HashSet<Pair<Integer, Integer>>();
		
		
		TreeMap<Integer, Pair<Integer, Integer>> openset = new TreeMap<Integer, Pair<Integer, Integer>>();
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
		openset.put(g_score.get(start) + man_dist(start, goal), start);
		
		
		System.out.println("start f_score = "+ (g_score.get(start) + man_dist(start, goal)));
		
		while(!openset.isEmpty()){
		//for(int j = 0; j < 20; j++){
//		cur = f_score.get(f_score.firstKey());
		//	cur = openset.get(openset.firstKey());
			cur = openset.pollFirstEntry().getValue();
			if(cur.equals(goal)){
				System.out.println("goal found");
				return reconstruct_path(came_from, start, goal);
			}
			
			System.out.println("cur = <"+cur.first+", "+cur.second+">");
			
	//		openset.remove(openset.firstKey());
			
			
			closedset.add(cur);
			
			for(int i = 0; i < 4; i++){
				next = return_next(cur, i);
				if(next != null){
					tent_g = g_score.get(cur) + 1;
					tent_f = tent_g + man_dist(next, goal);
					System.out.println("next = <"+next.first+", "+next.second+">, tent_g = "+tent_g+" tent_f = "+tent_f);
					System.out.println("man_dist = "+man_dist(next,goal));
					if(!(closedset.contains(next) && (tent_f >= (g_score.get(next) + man_dist(next, goal))))){
						System.out.println("blah");
						if((!openset.containsValue(next)) || (tent_f < (g_score.get(next) + man_dist(next, goal)))){
							System.out.println("potential openset addition");
							came_from.put(next, cur);
							g_score.put(next, tent_g);
							f_score.put(tent_f, next);
							if(!openset.containsValue(next)){
								System.out.println("adding <"+next.first+", "+next.second+"> to openset");
								openset.put(tent_f, next);
							}
						}	
					}
				}
						
			}
			
		}
		System.out.println("size of openset = "+openset.size());
		
		System.out.println("solution not found");

		//not found
		
		return null;
		
	}
	*/
	
	/*
	public Vector<Pair<Integer, Integer>> solve_maze(){

		//openset is a set of nodes that going to be visited
		//closedset is a set of nodes that have been visited
		//F_Score is a sorted treemap, sorted by f_score (key), value is the pair of ints
		//G_score is just a map, key is the pair, value is the g_score (distance from start)
		
		//HashSet<Pair<Integer, Integer>> openset = new HashSet<Pair<Integer, Integer>>();
		
		
		
		//	TreeMap<Integer, Pair<Integer, Integer>> openset = new TreeMap<Integer, Pair<Integer, Integer>>();
		//TreeMap<Pair<Integer, Integer>, Integer> openset = new TreeMap<Pair<Integer, Integer>, Integer>(myComparator);
		
		
		
		SortedSet<Pair<Integer, Pair<Integer, Integer>>> openset = new TreeSet<Pair<Integer, Pair<Integer, Integer>>>(new myComparator());
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
		//openset.put(g_score.get(start) + man_dist(start, goal), start);
		openset.add(Pair.create((g_score.get(start) + man_dist(start, goal)), start));
		
		System.out.println("start f_score = "+ (g_score.get(start) + man_dist(start, goal)));
		
		while(!openset.isEmpty()){
		//for(int j = 0; j < 20; j++){
//		cur = f_score.get(f_score.firstKey());
		//	cur = openset.get(openset.firstKey());
		//	cur = openset.pollFirstEntry().getKey();
			cur = openset.first().second;
			if(cur.equals(goal)){
				System.out.println("goal found");
				return reconstruct_path(came_from, start, goal);
			}
			
			System.out.println("cur = <"+cur.first+", "+cur.second+">");
			
			openset.remove(openset.first());
			
			
			closedset.add(cur);
			
			for(int i = 0; i < 4; i++){
				next = return_next(cur, i);
				if(next != null){
					tent_g = g_score.get(cur) + 1;
					tent_f = tent_g + man_dist(next, goal);
					System.out.println("next = <"+next.first+", "+next.second+">, tent_g = "+tent_g+" tent_f = "+tent_f);
				//	System.out.println("man_dist = "+man_dist(next,goal));
					if(closedset.contains(next) && (tent_f >= (g_score.get(next) + man_dist(next, goal)))){
					}
					else{
						System.out.println("blah");
						if((!openset.contains(next)) || (tent_f < (g_score.get(next) + man_dist(next, goal)))){
							System.out.println("potential openset addition");
							came_from.put(next, cur);
							g_score.put(next, tent_g);
							f_score.put(tent_f, next);
							if(!openset.contains(next)){
								System.out.println("adding <"+next.first+", "+next.second+"> to openset");
								openset.add(Pair.create(tent_f, next));
							}
						}	
					}
				}
						
			}
			
		}
		System.out.println("size of openset = "+openset.size());
		
		System.out.println("solution not found");

		//not found
		
		return null;
		
	}
	*/
	
	private Pair<Integer, Integer> minimumValue(HashMap<Pair<Integer, Integer>, Integer> map){
		
		System.out.println("finding minimumValue");
		
		List<Pair<Integer, Integer>> minKeyList = new ArrayList<Pair<Integer, Integer>>();
		Entry<Pair<Integer, Integer>, Integer> min = null;
		for(Entry<Pair<Integer, Integer>, Integer> entry: map.entrySet()){
			if((min == null) || (min.getValue() >= entry.getValue())){
				if((min == null) || (min.getValue() > entry.getValue())){
					min = entry;
					minKeyList.clear();
				}
				minKeyList.add(entry.getKey());
			}
		}
		if(minKeyList.isEmpty()){
			System.out.println("did not find minimumValue, problem");
			return null;
		}
		
		System.out.println("found minimumValue");
		
		return minKeyList.get(0);
	}
	
	
	
	public Vector<Pair<Integer, Integer>> solveMaze(){

		HashMap<Pair<Integer, Integer>, Integer> fScore = new HashMap<Pair<Integer, Integer>, Integer>();
		HashMap<Pair<Integer, Integer>, Integer> gScore = new HashMap<Pair<Integer, Integer>, Integer>();
		HashMap<Pair<Integer, Integer>, Integer> fScoreOpenSet = new HashMap<Pair<Integer, Integer>, Integer>();
		List<Pair<Integer, Integer>> openSet = new ArrayList<Pair<Integer, Integer>>();
		List<Pair<Integer, Integer>> closedSet = new ArrayList<Pair<Integer, Integer>>();
		HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> cameFrom = new HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>();	//key = cur, value = came_from
		Pair<Integer, Integer> start = null, goal = null, cur = null, next = null;
		int tentG, tentF;
		
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
		
		openSet.add(start);
		gScore.put(start, 0);
		fScore.put(start, manDist(start, goal));
		fScoreOpenSet.put(start, manDist(start, goal));
		
		while(!openSet.isEmpty()){
	//	for(int j = 0; j < 10; j++){
			cur = minimumValue(fScoreOpenSet);
			System.out.println("cur = <"+cur.first+", "+cur.second+">");
			
			
			if(cur.equals(goal)){
				System.out.println("goal found");
				return reconstructPath(cameFrom, start, goal);
			}
			openSet.remove(cur);
			fScoreOpenSet.remove(cur);
			closedSet.add(cur);
			for(int i = 0; i < 4; i++){
				next = returnNext(cur, i);
				if(next != null){
					tentG = gScore.get(cur) + 1;
					tentF = tentG + manDist(next, goal);
		//			System.out.println("next = <"+next.first+", "+next.second+">, tent_g = "+tentG+" tent_f = "+tentF);
					if(closedSet.contains(next) && (tentF >= fScore.get(next))){
						//change logic, this is a continue, I didn't want to mess it up
					}
					else{
						if((!openSet.contains(next)) || (tentF < fScore.get(next))){
							System.out.println("potential openset addition");
							cameFrom.put(next, cur);
							gScore.put(next, tentG);
							fScore.put(next, tentF);
							fScoreOpenSet.put(next, tentF);
							if(!openSet.contains(next)){
								System.out.println("adding <"+next.first+", "+next.second+"> to openset");
								openSet.add(next);
							}
						}
					}
				}
			}
		}
		
		
		System.out.println("goal not found");
		
		
		return null;
	}
	
	

	private void generateRoadblock(){

		
		Vector<Pair<Integer, Integer>> solution = solveMaze();	
		Pair<Integer, Integer> curLoc = null;
	
		//Change this to every 5 or something
		
		for(int i = 0; i < solution.size(); i++){
			curLoc = solution.get(i);
			maze[curLoc.first][curLoc.second] = 'R';
		}
		
		
	}
		


}

