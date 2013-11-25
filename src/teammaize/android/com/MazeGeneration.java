package teammaize.android.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.Vector;

import android.util.Pair;

/*
 * Maze Generation Algorithm
 * Utilizes a randomized depth-first search algorithm to generate the maze
 * Solves the randomly generated maze using the A* algorithm in order to add in roadblocks
 * at appropriate locations
 * 
 * - Robert Micatka
 * 
 */


public class MazeGeneration {
	
	private final int sizeX;	//rows
	private final int sizeY;	//columns
	
	//change the startX/startY potentially, not really sure yet just added this for flexibility
	private final int startX = 0;
	private final int startY = 0;
	private Pair<Integer, Integer> start = new Pair<Integer, Integer>(startX, startY);
	private Pair<Integer, Integer> goal = null;
	public final char[][] maze;	//x by y maze
	
	/*
	 * this is a helper function not utilized inside this class, very similar to returnNext which is though
	 * we have discussed that but really need to decide soon to make the code cleaner
	 */
	
	
	public Pair<Integer, Integer> returnNeighbor(Pair<Integer, Integer> curLoc, int dir){
		/*
		 * 
		 * this is not used for the generation stuff but utilized outside, move to top for support or something
		 * can change to return null if invalid move, ie into wall, already returns null for movement outside maze
		 * 
		 */
		
		Pair<Integer, Integer> neighbor = null;
		if(dir == DataStructures.Directions.NORTH.ordinal()){
			if((curLoc.second - 1) >= 0){
				neighbor = Pair.create(curLoc.first, curLoc.second - 1);
			}
		}
		if(dir == DataStructures.Directions.EAST.ordinal()){
			if((curLoc.first + 1) < sizeX){
				neighbor = Pair.create(curLoc.first + 1, curLoc.second);
			}
		}
		if(dir == DataStructures.Directions.SOUTH.ordinal()){
			if((curLoc.second + 1) < sizeY){
				neighbor = Pair.create(curLoc.first,  curLoc.second + 1);
			}
		}
		if(dir == DataStructures.Directions.WEST.ordinal()){
			if((curLoc.first - 1) >= 0){
				neighbor = Pair.create(curLoc.first - 1,  curLoc.second);
			}
		}
		return neighbor;
	}
	
	public MazeGeneration(int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		maze = new char[this.sizeX][this.sizeY];
		generateMaze(startX,startY);
		generateRoadblock();
	}
	
	private void generateMaze(int startX, int startY){
		/*
		 * Utilizes a randomized depth-first search algorithm to create a maze
		 */
		Stack<Pair<Integer, Integer>> mazeStack = new Stack<Pair<Integer, Integer>>();
		Vector<Pair<Integer, Integer>> mazeVector = new Vector<Pair<Integer, Integer>>();
		Pair<Integer, Integer> curCoords = null, neighbor = null;
		mazeStack.push(Pair.create(startX, startY));
		mazeVector.add(Pair.create(startX, startY));
		List<Integer> dirOrder = new ArrayList<Integer>();
		dirOrder.add(DataStructures.Directions.NORTH.ordinal()); 
		dirOrder.add(DataStructures.Directions.EAST.ordinal());
		dirOrder.add(DataStructures.Directions.SOUTH.ordinal());
		dirOrder.add(DataStructures.Directions.WEST.ordinal());
		for(int i = 0; i < sizeX; i++){
			for(int j = 0; j < sizeY; j++){
				maze[i][j] = DataStructures.MazeSpaces.WALL.SpaceChar();
			}
		}
		while(!mazeStack.isEmpty()){
			curCoords = mazeStack.pop();
			maze[curCoords.first][curCoords.second]  = DataStructures.MazeSpaces.PATH.SpaceChar();
			Collections.shuffle(dirOrder);
			for(int i = 0; i < 4; i++){
				neighbor = returnNextBlock(curCoords, dirOrder.get(i), mazeVector);
				if(neighbor != null){
					mazeStack.push(neighbor);
					mazeVector.add(neighbor);
				}
			}
		}
		maze[startX][startY] = DataStructures.MazeSpaces.START.SpaceChar();
		maze[mazeVector.lastElement().first][mazeVector.lastElement().second] = DataStructures.MazeSpaces.GOAL.SpaceChar();
		goal = Pair.create(mazeVector.lastElement().first, mazeVector.lastElement().second);
	}	
	
	private Pair<Integer, Integer> returnNextBlock(Pair<Integer, Integer> curLoc, int dir, Vector<Pair<Integer, Integer>> visited){
		/*
		 * This returns the next available block for the maze generation step, this allows adjacent elements to not interfere with each other
		 * during the generation
		 */
		Pair<Integer, Integer> neighbor = null;
		if(dir == DataStructures.Directions.NORTH.ordinal()){
			if((curLoc.second - 1) >= 0){
				neighbor = Pair.create(curLoc.first, curLoc.second - 1);
			}
		}
		if(dir == DataStructures.Directions.EAST.ordinal()){
			if((curLoc.first + 1) < sizeX){
				neighbor = Pair.create(curLoc.first + 1, curLoc.second);
			}
		}
		if(dir == DataStructures.Directions.SOUTH.ordinal()){
			if((curLoc.second + 1) < sizeY){
				neighbor = Pair.create(curLoc.first,  curLoc.second + 1);
			}
		}
		if(dir == DataStructures.Directions.WEST.ordinal()){
			if((curLoc.first - 1) >= 0){
				neighbor = Pair.create(curLoc.first - 1,  curLoc.second);
			}
		}
		if((neighbor != null) && available(neighbor, visited, dir)){
			return neighbor;
		}
		return null;
	}
	
	private boolean available(Pair<Integer, Integer> toBeExamined, Vector<Pair<Integer, Integer>> visited, int comingFrom){
		/*
		 * clean this up, remove if-statement logic, it's repetitive due to returnNextBlock()
		 * still need to check the visited stuff though
		 */
		Pair<Integer, Integer> tempExam = null;	
		//Checking movement to the north
		if((toBeExamined.second - 1) >= 0){
			//north is a valid move
			tempExam = Pair.create(toBeExamined.first, toBeExamined.second - 1);
			if((comingFrom != DataStructures.Directions.SOUTH.ordinal()) && visited.contains(tempExam)){
				return false;
			}
		}
		//Checking movement to the east
		if((toBeExamined.first + 1) < sizeX){
			tempExam = Pair.create(toBeExamined.first + 1, toBeExamined.second);
			if((comingFrom != DataStructures.Directions.WEST.ordinal()) && visited.contains(tempExam)){
				return false;
			}
		}
		//Checking movement to the south
		if((toBeExamined.second + 1) < sizeY){
			//north is a valid move
			tempExam = Pair.create(toBeExamined.first, toBeExamined.second + 1);
			if((comingFrom != DataStructures.Directions.NORTH.ordinal()) && visited.contains(tempExam)){
				return false;
			}
		}
		//Checking movement to the west
		if((toBeExamined.first - 1) >= 0){
			tempExam = Pair.create(toBeExamined.first - 1, toBeExamined.second);
			if((comingFrom != DataStructures.Directions.EAST.ordinal()) && visited.contains(tempExam)){
				return false;
			}
		}		
		return true;
	}
	
	private Pair<Integer, Integer> returnNext(Pair<Integer, Integer> curLoc, int dir){
		Pair<Integer, Integer> next = null;
		if(dir == DataStructures.Directions.NORTH.ordinal()){
			if((curLoc.second - 1) >= 0){
				next = Pair.create(curLoc.first, curLoc.second - 1);
			}
		}
		if(dir == DataStructures.Directions.EAST.ordinal()){
			if((curLoc.first + 1) < sizeX){
				next = Pair.create(curLoc.first + 1, curLoc.second);
			}
		}
		if(dir == DataStructures.Directions.SOUTH.ordinal()){
			if((curLoc.second + 1) < sizeY){
				next = Pair.create(curLoc.first,  curLoc.second + 1);
			}
		}
		if(dir == DataStructures.Directions.WEST.ordinal()){
			if((curLoc.first - 1) >= 0){
				next = Pair.create(curLoc.first - 1,  curLoc.second);
			}
		}
		if((next != null) && (maze[next.first][next.second] != DataStructures.MazeSpaces.WALL.SpaceChar())){
			return next;
		}
		return null;
	}
	
	private Integer manDist(Pair<Integer, Integer> start, Pair<Integer, Integer> goal){
		/*
		 * The Manhattan distance heuristic used for weighting
		 */
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
	
	private Pair<Integer, Integer> minimumValue(HashMap<Pair<Integer, Integer>, Integer> map){
		/*
		 * Custom sorting method in order to find the minimum value of the hash-map that contains the elements and their corresponding weights
		 */
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
			return null;
		}
		return minKeyList.get(0);
	}
	
	public Vector<Pair<Integer, Integer>> solveMaze(){
		/*
		 * Utilizes the A* algorithm in order to solve the maze
		 */
		HashMap<Pair<Integer, Integer>, Integer> fScore = new HashMap<Pair<Integer, Integer>, Integer>();
		HashMap<Pair<Integer, Integer>, Integer> gScore = new HashMap<Pair<Integer, Integer>, Integer>();
		HashMap<Pair<Integer, Integer>, Integer> fScoreOpenSet = new HashMap<Pair<Integer, Integer>, Integer>();
		List<Pair<Integer, Integer>> openSet = new ArrayList<Pair<Integer, Integer>>();
		List<Pair<Integer, Integer>> closedSet = new ArrayList<Pair<Integer, Integer>>();
		HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> cameFrom = new HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>();	//key = cur, value = came_from
		Pair<Integer, Integer> cur = null, next = null;
		int tentG, tentF;
		openSet.add(start);
		gScore.put(start, 0);
		fScore.put(start, manDist(start, goal));
		fScoreOpenSet.put(start, manDist(start, goal));
		while(!openSet.isEmpty()){
			cur = minimumValue(fScoreOpenSet);
			if(cur.equals(goal)){
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
					if(closedSet.contains(next) && (tentF >= fScore.get(next))){
						//change logic, this is a continue, I didn't want to mess it up
					}
					else{
						if((!openSet.contains(next)) || (tentF < fScore.get(next))){
							cameFrom.put(next, cur);
							gScore.put(next, tentG);
							fScore.put(next, tentF);
							fScoreOpenSet.put(next, tentF);
							if(!openSet.contains(next)){
								openSet.add(next);
							}
						}
					}
				}
			}
		}
		return null;
	}

	private void generateRoadblock(){
		/*
		 * This calls the solveMaze method in order to get a solution path in which to place the road-blocks in
		 */
		Vector<Pair<Integer, Integer>> solution = solveMaze();	
		Pair<Integer, Integer> curLoc = null;
		for(int i = 0; i < solution.size(); i++){
			curLoc = solution.get(i);
			if((maze[curLoc.first][curLoc.second] == DataStructures.MazeSpaces.PATH.SpaceChar()) && (((i+3) % 4) == 0)){
				maze[curLoc.first][curLoc.second] = DataStructures.MazeSpaces.ROADBLOCK.SpaceChar();
			}
		}
	}
}

