package project5;

/**
 * A class that represents each node of the BST maze
 * 
 * @author Robert Fang
 */
public class MazeNode implements Comparable<MazeNode>{

    //label of the MazeNode
    private String label;

    //number of life points at that MazeNode
    private int lifePoints;

    /**
     * creates a new MazeNode by initializing life points and label
     * 
     * @param l label of the MazeNode
     * 
     * @param lp life points at that MazeNode
     */
    public MazeNode(String l, int lp){
        label = l;
        lifePoints = lp;
    }

    /**
     * returns the label of the MazeNode
     * 
     * @return the label of the MazeNode
     */
    public String getLabel() {
        return label;
    }

    /**
     * returns the label of the MazeNode
     * 
     * @return the label of the MazeNode
     */
    public int getLifePoints() {
        return lifePoints;
    }

    /**
     * returns the result of a comparison between 2 MazeNodes
     * 
     * @param other the other MazeNode being compared to
     * 
     * @return the result of a comparison between 2 MazeNodes
     */
    public int compareTo(MazeNode other){
        return this.label.compareTo(other.label);
    }
}