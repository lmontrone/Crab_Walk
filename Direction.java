public enum Direction{
    STILL("not moving"),
    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west");
    

    private String name = null;

    private Direction(String s){
        name = s;
    }
    public String getName(){
        return name;
    }

}
