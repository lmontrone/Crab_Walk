import java.util.ArrayList;

import java.io.*;

import java.util.Random;

import javax.swing.JOptionPane;

import java.util.Iterator;

import java.awt.Rectangle;

import java.io.Serializable;

import java.lang.Throwable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;

import java.util.HashSet;

public class Model implements Serializable {
    	// This is where all of our logic is going to go for the game
    	// This will also be where our game state is handled
    	private int objXIncr; // All objects will only move left, not up or down
    	static int left; // Again, object is only moving left
    	int frameWidth;
    	private final int NORTHSTEP = -30;
    	private final int SOUTHSTEP = 30;
    	private final int EASTSTEP = 30;
    	private final int WESTSTEP = -30;
        HashSet<InterObj> crashes;
    	int frameHeight;
    	int imgSize;
    	private int noIncr = 0;
    	ArrayList<InterObj> stuff; // all of the objects with their methods and properties
    	Crab player;               // the player with all his methods and properties
    	int trashCtr = 0;
        Random rand = new Random();
        int randnum;
        boolean crash;


    /**
     * Checks if one object is equal to another
     * @param other The object that is being compared to
     * @return Whether or not the two objects are equal
    */

    	@Override
    	public boolean equals(Object other){
    		if (!(other instanceof Model)){
    			return false;
    		}
    		else{
    			Model o = (Model)other;
    			return (getXIncr() == o.getXIncr()) &&
    					(getFrameWidth() == o.getFrameWidth()) &&
    					(getFrameHeight() == o.getFrameHeight()) &&
    					(getImgSize() == o.getImgSize()) &&
    					(getStuff().equals(o.getStuff())) &&
    					(getPlayer().equals(o.getPlayer()));
    		}
    	}
    	
    	public int getFrameHeight(){
    		return this.frameHeight;
    	}
    	
    	public int getImgSize(){
    		return this.imgSize;
    	}

    	
    	/**
		 * This gives us how fast the objects and player are moving at
		 * @return The x incrementor
		 */
    	public int getXIncr(){
    		return this.objXIncr;
    	}
    	/**
		 * This tells us how wide the game frame is
		 * @return The width of the frame
		 */
    	public int getFrameWidth(){
    		return this.frameWidth;
		}
    /**
     *Implementation of the Model Constructor
     * @param frameWidth This is the width of the playable frame
     * @param frameHeight This is the height of the playable frame
     * @param imgSize This is the size of the images that will be in play
    */
    	public Model(int frameHeight, int frameWidth, int imgSize){
        	this.frameWidth = frameWidth;
        	this.frameHeight = frameHeight;
        	this.imgSize = imgSize;
        	stuff = new ArrayList<InterObj>();
        	player = new Crab(frameWidth); 
        	this.crash = false;
            crashes = new HashSet<InterObj>();
    	}
    /**
     * Returns the player. Meant for using the Crab at various times
     * @return A Crab
    */
    	public Crab getPlayer(){
        	return player;
    	}
    /**
     * Meant for determining where the Crab crashed into
     * @return A boolean that says whether or not a crash has occurred
    */
    	public boolean getCrash(){
        	return crash;
    	}
    /**
     * Meant to change the crash boolean
     *  @param y Whether or not a crash occurs
    */
    	public void setCrash(boolean y){
        	crash = y;
    	}
    /**
     * Gets the array of interactive objects
     * @return An ArrayList of Interactive Objects
    */
    	public ArrayList<InterObj> getStuff(){
        	return this.stuff; 
    	}
    /**
     * Determines whether a crash has occurred between a player and a given object
     * @param object The object that we are checking to see if there is a crash between it and the player
     * @return A boolean that says whether or not the crash happened
     */
		public boolean crash(InterObj object){ // tells whether the player has hit this object
			if(Math.abs(player.getXLoc()-object.getXLoc()) < 50 && (Math.abs(player.getYLoc()-object.getYLoc()) < 50))
			{
				System.out.println(player.getXLoc()-object.getXLoc());
				System.out.println(player.getYLoc()-object.getYLoc());
			}
			return (Math.abs(player.getXLoc()-object.getXLoc()) < 40 && (Math.abs(player.getYLoc()-object.getYLoc()) < 40));
			//return(player.getHitBox().intersects(object.getHitBox()));
    	}
    /**
     * Checks the crash method, and if a collision happens, then it handles it accordingly
     * @param objects The array of interactive objects
     */
    	public void handleCollisions(ArrayList<InterObj> objects){
        	// checks each InterObj whether the player has hit it
        	// if it has, it calls the object's onCollision method
        	// This method largely depends on other collision methods already being created
            
        	for (InterObj o: stuff){
            		if(crash(o) & View.crashlesstime == 0 ){
			    	    crashes.add(o);
            	    }

        	}
        }
    /**
     * Creates a new set of interactive objects for the game
     */
    	public void generateNewStuff(){
    		randnum = rand.nextInt(7);
    		switch (randnum) {
            case 0: 
            	stuff.add(new Trash(frameWidth,1));
            	break;
            case 1: 
            	stuff.add(new Trash(frameWidth,2));
            	break;
            case 2: 
            	stuff.add(new Trash(frameWidth,3));
            	break;
            case 3: 
            	stuff.add(new Trash(frameWidth,4));
            	break;
            case 4: 
            	if (rand.nextInt(10) > 6){
            	stuff.add(new Trash(frameWidth,5));
            	}
            	break;
            case 5: 
            	stuff.add(new Invasive(frameWidth,6));
            	break;
            case 6: 
            	stuff.add(new Invasive(frameWidth,7));
            	break;
    		}
        	
        	
    	}
    /**
     * This is the method that moves the player in the desired direction
     * @param newPlayer This is the crab that is being moved
     */
    	public void update(Crab newPlayer){
        	// moves the player in the specified direction
        	// deals with collisions
        	// moves the InterObjs
        	// deals with collisions again
        	// Will Be Called From Controller With The Dir From View
		player = newPlayer;
        	switch(player.getDir()){
        	case NORTH:	 
			player.setYIncr(NORTHSTEP);
			player.setXIncr(noIncr);
		    	break;
       		case SOUTH: 
			player.setYIncr(SOUTHSTEP);
		    	player.setXIncr(noIncr);
		    	break;
        	case EAST:
			player.setXIncr(EASTSTEP);
		   	player.setYIncr(noIncr);
		   	break;
        	case WEST:
			player.setXIncr(WESTSTEP);
		   	player.setYIncr(noIncr);
		   	break;
        	default:
			player.setXIncr(noIncr);
                	player.setYIncr(noIncr);
        	}
        	
        	
        	player.move();
        	handleCollisions(stuff);
        	
        	for(InterObj o : stuff){
            		o.move();
        	}
        	
        	handleCollisions(stuff);
            if (crashes.size() == 0){
                View.notQuizTime();
            }
            for (InterObj o: crashes){
                System.out.println("What's next?");
                System.out.println("Collision!");
                o.onCollision(player);
                if (o.gone){
                    System.out.println("Bye!");
                    stuff.remove(o);
                }
            }
        	
        	if (View.crashlesstime != 0){
        		View.crashlesstime --;
        	}
        	
        	player.setDir(Direction.STILL);

        	if (trashCtr++ %10 == 0) {
			   generateNewStuff();
		    }
            crashes = new HashSet<InterObj>();
    	}
    	
    	public void saveGame(View view, int timerCtr) throws IOException {
    		try{
				SavedGame game = new SavedGame(view, timerCtr);
    		
    			FileOutputStream fileout = new FileOutputStream("SavedFile.txt");
				ObjectOutputStream out = new ObjectOutputStream(fileout);
				out.writeObject(game);
				out.close();
			}
			catch(IOException e){
				e.printStackTrace();
			} 		
    	}
    	
    	public void loadGame() throws FileNotFoundException, IOException, ClassNotFoundException {
			try{
				SavedGame game = null;
    		
				FileInputStream fileIn = new FileInputStream("./SavedFile.txt");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				
				game = (SavedGame) in.readObject();
				
				in.close();
				fileIn.close();
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
    	}
    	
}
