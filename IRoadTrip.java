import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class IRoadTrip {
	
	
	public IRoadTrip(String[] args) {
		adjacencyList= new HashMap<>();
	}

    // graph structure 
    private HashMap<String, HashMap<String, Integer>> adjacencyList;
    HashMap<String,String> specialCases;
 
    //checking special cases 
    public void specialCases() throws FileNotFoundException{
		File f  = new File("diffnames.txt");
		Scanner scan1 = new Scanner(f);
		
		while (scan1.hasNextLine()) {
			String[] line = scan1.nextLine().split("=");
			specialCases.put(line[0],line[1]);
		}
		scan1.close();		
	}
    
    //reads the borders file to initialize the graph with their neighbors 
    private void addBorder() throws IOException{
		File f = new File("borders.txt");
    	Scanner scan = new Scanner(f);
    	
    	while (scan.hasNextLine()) {
    	 	String[] s = scan.nextLine().trim().split("[=;]");
    	 	if (s.length!=1) {//takes care of islands
    	 		adjacencyList.put(s[0].trim(), new HashMap<>());
        	 	
        	 	for (int i = 1; i<s.length;i++) {
        	 		String temp = s[i].trim();
        	 		String name = "";
        	 		
        	 		
        	 		for (int j = 0; j < temp.length();j++) {
        	 			if (Character.isDigit(temp.charAt(j))) {
        	 				break;
        	 			}
        	 			name += temp.charAt(j);
        	 		}
        	 		name=name.trim();

        	 		adjacencyList.get(s[0].trim()).put(name, addDistance(s[0].trim(),name));//handles weird cases 
//        	 		System.out.println(s[0]);
        	 	}
    	 	}
    	}
    	scan.close();	
	}
  
    //reads the capdist file 
    private int  addDistance(String origin, String dest) throws FileNotFoundException{
    	origin = specialCases.containsKey(origin)? specialCases.get(origin): origin;
    	dest = specialCases.containsKey(dest)? specialCases.get(dest): dest;
    	
    	origin = origin.trim();
    	dest = dest.trim();
    	
    	String originCode="";
    	String destCode="";
    	
    	File f  = new File("state_name.tsv");
		Scanner scan = new Scanner(f);
		
		while (scan.hasNextLine()) {
			String[] line = scan.nextLine().split("\t");
			
			if (line[2].equals(origin)) {
				originCode=line[0];
			}else if (line[2].equals(dest)) {
				destCode=line[0];
			}
		}
		scan.close();
		return capitalDist(originCode, destCode);
    }
    
    public int capitalDist( String originCode, String destCode) throws FileNotFoundException {
    	
    	File f = new File("capdist.csv");
		Scanner scan = new Scanner(f);
		
		while (scan.hasNextLine()) {
			String[] line = scan.nextLine().split(",");
			if (line[0].equals(originCode) && line[2].equals(destCode)) {
				return Integer.valueOf(line[4]);
			}
		}
    	return -1;
    }
    
	public int getDistance (String country1, String country2) throws FileNotFoundException {
        // Replace with your code
        return -1;
//		String standardizedCountry1 = specialCases.getOrDefault(country1, country1);
//	    String standardizedCountry2 = specialCases.getOrDefault(country2, country2);
//
//	    return capitalDist(standardizedCountry1, standardizedCountry2);
    }

	public List<String> findShortestPath(String startName, String endName) {
	    Map<String, Integer> distances = new HashMap<>();
	    Map<String, String> predecessors = new HashMap<>();
	    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparing(Node::getDistance));
	    distances.put(startName, 0);
	    queue.add(new Node(startName, 0));
	    while (!queue.isEmpty()) {
	        Node current = queue.poll();

	        // Assuming `getNeighbors` returns a map of neighbor names and distances
	        Map<String, Integer> neighbors = getNeighbors(current.getName());
	        for (String neighbor : neighbors.keySet()) {
	            int newDist = distances.get(current.getName()) + neighbors.get(neighbor);
	            if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
	                distances.put(neighbor, newDist);
	                predecessors.put(neighbor, current.getName());
	                queue.add(new Node(neighbor, newDist));
	            }
	        }
	    }
	    List<String> path = new ArrayList<>();
	    String at = endName;
	    while (at != null && predecessors.containsKey(at)) {
	        path.add(at);
	        at = predecessors.get(at);
	    }
	    Collections.reverse(path);
	    return path;
	}
	
	private static class Node {
	    private String name;
	    private int distance;

	    public Node(String name, int distance) {
	        this.name = name;
	        this.distance = distance;
	    }

	    public String getName() {
	        return name;
	    }

	    public int getDistance() {
	        return distance;
	    }
	}
	    
    public List<String> findPath (String country1, String country2) {
        // Replace with your code
        return null;
    }

	
	public void acceptUserInput() throws FileNotFoundException {
		System.out.print("IRoadTrip - skeleton\n");
		
		Scanner scanner = new Scanner(System.in);

	    while (true) {
	        //asking the user to input 2 countries
	    	System.out.println("Enter the first country (or 'exit' to quit): ");
	        String country1 = scanner.nextLine().trim();
	        if (country1.equalsIgnoreCase("exit")) break;

	        System.out.println("Enter the second country (or 'exit' to quit): ");
	        String country2 = scanner.nextLine().trim();
	        if (country2.equalsIgnoreCase("exit")) break;

	        //using getDistance or findPath to calculate the distance or path
	        int distance = getDistance(country1, country2);
	        List<String> path = findPath(country1, country2);

	        System.out.println("Distance: " + distance);
	        System.out.println("Path: " + path);
	    }

	    scanner.close();

  }
	public static void main(String[] args) throws FileNotFoundException {
		IRoadTrip a3 = new IRoadTrip(args);
		
		a3.acceptUserInput();
	}
	

  
}

//My code does not completely work, I tried to do as much as I could but this was a very challenging project for me.
//My program does print something but does not check complete.







