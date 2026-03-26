package project5;

/**
 * A class that represents a maze, a BST of MazeNodes
 * 
 * @author Robert Fang
 */
public class Maze extends BST<MazeNode>{
    /**
     * creates a new maze using the BST constructor and MazeNodes
     */
    public Maze(){
        super();
    }

    /**
     * prints out all valid paths that the hero can take in the maze to the exit
     * life point constraints accounted for
     * trapdoor paths are not printed
     * 
     * @param node node that the hero is currently on
     * 
     * @param path String representing the path to be printed
     * 
     * @param level level of the current node
     * 
     * @param hero hero that is traversing the maze
     * 
     * @param maxDepth the depth that the exit leaves are at
     */
    public void generatePaths(Node node, String path, int level, Hero hero, int maxDepth){
        //if current node is null, then stop
        if(node == null) return;

        //create a new hero with the same life points 
        Hero h = new Hero(hero.getLp());
        //add the life points at the current node to the hero
        h.collect(node.data);

        //add the label of the current node to the path
        if(!path.isEmpty()) path += " ";
        path += node.data.getLabel();

        //if the leaf is an exit at the lowest level, print the path
        if(level == maxDepth && node.left == null && node.right == null){
            System.out.println(path);
            return;
        }

        //if hero has no more life points, then stop
        if(h.getLp() <= 0) return;

        //traverse down the left subtree
        if(node.left != null){
            Hero leftHero = new Hero(h.getLp() - 1);
            generatePaths(node.left, path, level + 1, leftHero, maxDepth);
        }
        //traverse down the right subtree
        if(node.right != null){
            Hero rightHero = new Hero(h.getLp() - 1);
            generatePaths(node.right, path, level + 1, rightHero, maxDepth);
        }
    }
}