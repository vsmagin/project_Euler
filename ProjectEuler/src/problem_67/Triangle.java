package problem_67;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * program finds the maximum total in a triangle 
 * from top to bottom, by starting at the top of the triangle 
 * and moving to adjacent numbers on the row below.
 * triangle is taken from a provided file
 * 
 * @author Elektronik
 */
public class Triangle {
	private int[][] triangle;
	private int height;	//height of the triangle
	
	public Triangle(String fromFile) throws FileNotFoundException {
		java.io.File file = new java.io.File(fromFile);
		
		//count the number of line in the file
		Scanner input = new Scanner(file);
		while (input.hasNextLine()) {
			input.nextLine();
			height++;
		}
		input.close();
		
		//fill out the triangle array from file
		this.triangle = new int[height][];
		input = new Scanner(file);
		for (int i = 0; i < height; i++) {
			String line = input.nextLine();
			String[] words = line.split(" ");
			this.triangle[i] = new int[words.length];
			for (int j = 0; j < words.length; j++) 
				this.triangle[i][j] = Integer.parseInt(words[j]);
		}
		input.close();
	}
	
	//find the maximum total from top to bottom, by starting at the top of the triangle 
	//and moving to adjacent numbers on the row below
	public int findMaxTotal() {
		int[][] distTo = new int[height][]; //distance to every node of the triangle
		int[][] edgeTo = new int[height][]; //edge to next node of higher distance
		int maxDist = 0;	//maximum total
		
		//initialize distance to every node of the triangle and create "edgeTo" array
		distTo[0] = new int[1];
		distTo[0][0] = triangle[0][0]; //distance to first node is its own weight
        for (int i = 1; i < this.triangle.length; i++) { //rest of distances are initialized to 0
        	distTo[i] = new int[this.triangle[i].length];
        	edgeTo[i] = new int[this.triangle[i].length];
        	for (int j = 0; j < this.triangle[i].length; j++)
        		distTo[i][j] = 0;
        }
                
        //find longest distance to every node of the triangle
        for (int i = 0; i < this.height-1; i++) {
        	for (int j = 0; j < this.triangle[i].length; j++) {
        		if (distTo[i+1][j] < distTo[i][j] + this.triangle[i+1][j]) {
        			distTo[i+1][j] = distTo[i][j] + this.triangle[i+1][j];
        			edgeTo[i+1][j] = j;
        		}
        		if (distTo[i+1][j+1] < distTo[i][j] + this.triangle[i+1][j+1]) {
        			distTo[i+1][j+1] = distTo[i][j] + this.triangle[i+1][j+1];
        			edgeTo[i+1][j+1] = j;
        		} 
        	}
        }
        
        //find max distance on the bottom row
        for (int j = 0; j < this.triangle[height-1].length; j++) {
        	if (distTo[height-1][j] > maxDist)
        		maxDist = distTo[height-1][j];
        } 

        return maxDist;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Triangle triangle = new Triangle("triangle4.txt");
		System.out.println("Maximum total from top to bottom is: " + triangle.findMaxTotal());
	}

}
