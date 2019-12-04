public class Piece {

    public int stackNumber;
    public String colour;
    public int xPosition;
    public int yPosition;
    public Boolean middle;
    public int linkButton;

    // ADJUST STACK NUMBERS WHEN IN MIDDLE OR WHEN OUT OF GAME
    //// 29 == MIDDLE BLACK
    //// 30 == MIDDLE WHITE
    //// 31 == OUT OF GAME WHITE
    //// 32 == OUT OF GAME BLACK

    public Piece(String colour, int stackNumberz, int height) throws IllegalColourException {

        // ASSIGN STACK NUMBER
        if (colour.equals("white") || colour.equals("black")) {
            this.colour = colour;
            this.stackNumber = stackNumberz;
            this.middle = false;
            calculateCoordinates("Board", height);
        } else
            throw new IllegalColourException("colour must be 'black' or 'white'");
    }

    public String getColour() {
        return colour;
    }

    public void calculateCoordinates(String type, int height) {
      if (type == "Board"){
              if (this.stackNumber >= 0 && this.stackNumber <= 5) {
                  this.xPosition = 721 - (this.stackNumber * 60);
                  this.yPosition = 46 + ((height - 1) * 19);
              } else if (this.stackNumber >= 6 && this.stackNumber <= 11) {
                  this.xPosition = 341 - ((this.stackNumber - 6) * 60);
                  this.yPosition = 46 + ((height - 1) * 19);
              } else if (this.stackNumber >= 12 && this.stackNumber <= 17) {
                  this.xPosition = 41 + ((this.stackNumber - 12) * 60);
                  this.yPosition = 596 - ((height - 1) * 19);
              } else if (this.stackNumber >= 18 && this.stackNumber <= 23) {
                  this.xPosition = 421 + ((this.stackNumber - 18) * 60);
                  this.yPosition = 596 - ((height - 1) * 19);
          }
      } else if (type == "Removed"){
//        int height = getHeightRemoved(stack);
        if (this.colour =="white"){
          this.xPosition = 816;
          this.yPosition = 596 - ((height - 1) * 19);
        } else if (this.colour =="black"){
          this.xPosition = 816;
          this.yPosition = 46 + ((height - 1) * 19);
        }
      } else if (type == "Middle"){
//        int height = getHeightMiddle(stack);
        if (this.colour =="white"){
          this.xPosition = 721 - (6 * 60);
          this.yPosition = 46 + ((17 - 1) * 19);
        } else if (this.colour =="black"){
          this.xPosition = 421 + ((17 - 18) * 60);
          this.yPosition = 596 - ((17 - 1) * 19);
        }
      }
    }

    public void setMiddle(Boolean value) {
      this.middle = value;
    }

    public int move(int from, int dieRoll) {
        if (this.colour.equals("white")) {
            if (from + dieRoll < 24) {
                this.stackNumber = from + dieRoll;
                return from + dieRoll;
            } else
                // CHANGE STACK NUMBER
                return -1;
        } else {
            if (from - dieRoll >= 0) {
                this.stackNumber = from - dieRoll;
                return from - dieRoll;
            } else
                // CHANGE STACK NUMBER
                return -1;
        }
    }

    public int getHeight(PieceStack[] arr) {
        return arr[this.stackNumber].indexOf(this);
    }

    public void linkButton(int value) {
        this.linkButton = value;
    }

    public int getHeightMiddle(PieceStack middle) {
        return middle.indexOf(this);
    }

    public int getHeightRemoved(PieceStack removed) {
        return removed.indexOf(this);
    }

    // public Boolean isInMiddle(PieceStack[] arr) {
    //
    //   if ( arr.indexOf(this)){ // defined
    //     return true;
    //   } else {
    //     return false;
    //   }
    // }
}
