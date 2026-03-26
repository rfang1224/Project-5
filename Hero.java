package project5;

/**
 * A class representing the hero traversing through the Maze
 * 
 * @author Robert Fang
 */
public class Hero{

    //the life points that the hero currently has
    private int lifePoints;

    /**
     * constructs a new hero object
     * 
     * @param lp the number of life points that the hero has
     */
    public Hero(int lp){
        lifePoints = lp;
    }

    /**
     * returns the number of life points that the hero has
     * 
     * @return the number of life points that the hero has
     */
    public int getLp(){
        return lifePoints;
    }

    /**
     * collects life points at the current MazeNode
     * 
     * @param mn the current MazeNode
     */
    public void collect(MazeNode mn){
        lifePoints += mn.getLifePoints();
    }
}
