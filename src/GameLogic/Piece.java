package GameLogic;


import org.jetbrains.annotations.NotNull;

public class Piece{

    private String colour;

    public String getColour() {
        return colour;
    }

    public Piece(@NotNull String colour) throws IllegalColourException{

        if(colour.equals("white") || colour.equals("black"))
            this.colour = colour;
        else
            throw new IllegalColourException("colour must be 'black' or 'white'");
    }


    public int move(int from, int dieRoll){
        if(this.colour.equals("white")) {
            if (from + dieRoll < 23)
                return from + dieRoll;
            else
                return -1;
        }
        else{
            if (from - dieRoll > 0)
                return from - dieRoll;
            else
                return -1;
        }
    }



}