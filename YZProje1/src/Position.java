public class Position implements Comparable<Position>{

    private int x;
    private int y;

    public Position(Position pos){
        x = pos.getX();
        y = pos.getY();
    }

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @param pos Position to compare to
     * @returns 0 if positions are overlapping.
     * Returns 1 if positions point different locations
     */
    @Override
    public int compareTo(Position pos) {
        if ((x == pos.getX()) && (y == pos.getY())){
            return 0;
        }
        return 1;
    }
}