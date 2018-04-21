import java.util.ArrayList;



public class Model{
    // This is where all of our logic is going to go for the game
    // This will also be where our game state is handled
    private int objXIncr; // All objects will only move left, not up or down
    static int left; // Again, object is only moving left
    int frameWidth;
    int frameHeight;
    int imgSize;
    ArrayList<InterObj> stuff; // all of the objects with their methods and properties
    Crab player;               // the player with all his methods and properties

    public Model(int frameWidth, int frameHeight, int imgSize){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.imgSize = imgSize;
        stuff = new ArrayList<InterObj>;
        player = new Crab();
    }

    public boolean crash(InterObj object){
        // tells whether the player has hit this object
    }

    public void handleCollisions(){
        // checks each InterObj whether the player has hit it
        // if it has, it calls the object's onCollision method
    }

    public void update(Direction dir){
        // moves the player in the specified direction
        // deals with collisions
        // moves the InterObjs
        // deals with collisions again

        // Will Be Called From Controller With The Dir From View
    }


}
