package project5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This program processes the information in a txt file to construct a maze
 * The program traverses through the constructed maze with a hero
 * The program then prints out all the paths that the hero took given certain constraints
 * The valid paths printed must end at leaf nodes of the deepest level
 * The valid paths must also not overexhaust all of the hero's life points during traversal
 * 
 * @author Robert Fang
 */
public class BinaryTreeMaze {
    public static void main(String[] args) {

        String fileName = args[0];
        File file = new File(fileName);

        if (!file.exists()) {
            System.err.println("Error: the file " + file.getAbsolutePath() + " does not exist.");
            return;
        }

        //create a new empty Maze
        Maze maze = new Maze();
        try {
            Scanner scanner = new Scanner(file);
            boolean hasNodes = false;

            //scan each line of the file to make maze nodes
            //maze nodes are made with the label and life point on each line
            //add these maze nodes to the maze
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()){
                    continue;
                }

                //split label from life points for each line
                String[] parts = line.split(" ");

                if (parts.length < 2){
                    continue;
                } 

                //set label and life points
                String label = parts[0];
                int lifePoints;

                try {
                    lifePoints = Integer.parseInt(parts[1]);
                } 
                catch (NumberFormatException e) {
                    continue;
                }

                //create and add a new MazeNode with label and life points
                maze.add(new MazeNode(label, lifePoints));
                hasNodes = true;
            }
            if(!hasNodes){
                System.err.println("Maze does not contain any nodes.");
                return;
            }
        }

        catch (FileNotFoundException e) {
            System.err.println("Error: the file " + file.getAbsolutePath() + " does not exist.");
            return;
        }

        //create a new hero
        //print out all valid hero paths
        Hero hero = new Hero(0);
        int maxDepth = maze.height();
        maze.generatePaths(maze.root, "", 1, hero, maxDepth);
    }
}
