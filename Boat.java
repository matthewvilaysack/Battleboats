public class Boat {
  private boolean orientation; // horizontal = false, vertical = true
  private int boatSize;
  private Cell[] boatLocation; //where the boat is on the board (x,y)

  //example Boat: (true, 3, ({2,4}, {2,5})
  
  public Boat(boolean orientation, int boatSize, Cell[] boatLocation) {
    this.orientation = orientation;
    this.boatSize = boatSize;
    this.boatLocation = boatLocation;
  }
  public int getBoatSize() {
    return this.boatSize;
  }
  public void setBoatSize(int boatSize) {
    this.boatSize = boatSize;
  }
  public void setOrientation(boolean orientation) {
    this.orientation = orientation;
  } 
  public boolean getOrientation() {
    return this.orientation;
  }
  public Cell[] getBoatLocation() {
    return location;
  }
  public void setBoatLocation(int index, Cell c) {
    boatLocation[index] = c;
  }

}