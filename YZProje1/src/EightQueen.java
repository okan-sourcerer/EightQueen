import java.util.ArrayList;
import java.util.Random;

public class EightQueen {

    private ArrayList<Position> positions;
    private int randomRestart = -1;
    private int numberOfMoves = 0;
    private long solvingTime = 0;

    public EightQueen(){
        // initialize the board.
        resetBoard();
    }

    private void resetBoard(){
        randomRestart++;
        //System.out.println("Setting board!");
        positions = new ArrayList<>(8); // create an arraylist with capacity of number of queens.
        randomInitializer();
    }

    /**
     * Initialize the board with positions created using Random class.
     * Positions can not overlap.
     */
    private void randomInitializer(){
        int initialized = 0;

        Random rn = new Random();
        while (initialized < 8){
            int x = rn.nextInt(8);
            int y = rn.nextInt(8);
            Position newPos = new Position(x, y);
            if(checkPosition(newPos)){
                // created new unique position
                initialized++;
                positions.add(newPos);
            }
        }
    }

    /**
     * @param pos When creating positions, check if this position is occupied or not.
     * @return Returns false if position already exists in the list.
     */
    private boolean checkPosition(Position pos){
        for(Position p: positions){

            if(pos.compareTo(p) == 0){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return Calculates overall board score which is number of direct contact of the chess pieces.
     */
    public int calculateScore(){
        int score = 0;
        for (int i = 0; i < positions.size() - 1; i++){
            Position outer = positions.get(i);
            for (int j = i + 1; j < positions.size(); j++){
                Position inner = positions.get(j);
                if (outer.getX() == inner.getX() || outer.getY() == inner.getY()){ // check if they are on the same line or column
                    score++;
                }
                else if(Math.abs(outer.getX() - inner.getX()) == Math.abs(outer.getY() - inner.getY())){ // check if positions are crossed
                    score++;
                }
            }
        }
        return score;
    }

    /**
     * @param samplePos Position to calculate the score for.
     * @return Returns number of conflicts in the board of the given position
     */
    private int individualScore(Position samplePos){
        int score = 0;

        for (Position inner : positions) {
            if (samplePos.getX() == inner.getX() && samplePos.getY() == inner.getY()) { // Check if @param samplePos is this position.
                // only 1 position must be in this position. inner is samplePos.
                continue;
            }
            if (samplePos.getX() == inner.getX() || samplePos.getY() == inner.getY()) { // check if they are on the same line or column
                score++;
            } else if (Math.abs(samplePos.getX() - inner.getX()) == Math.abs(samplePos.getY() - inner.getY())) { // check if positions are crossed
                score++;
            }
        }
        return score;
    }

    private void movePerIteration(Position pos, int iterasyon) {
        switch (iterasyon){
            case 0: // 12 o'clock direction
                pos.setY(pos.getY() + 1);
                break;
            case 1: // 1.30 direction
                pos.setX(pos.getX() + 1);
                pos.setY(pos.getY() + 1);
                break;
            case 2: // 3 o'clock direction
                pos.setX(pos.getX() + 1);
                break;
            case 3:  // 4.5 direction
                pos.setX(pos.getX() + 1);
                pos.setY(pos.getY() - 1);
                break;
            case 4:  // 6 o'clock direction
                pos.setY(pos.getY() - 1);
                break;
            case 5: // 7.5 direction
                pos.setX(pos.getX() - 1);
                pos.setY(pos.getY() - 1);
                break;
            case 6: // 9 o'clock direction
                pos.setX(pos.getX() - 1);
                break;
            default: // 10.5 direction
                pos.setX(pos.getX() - 1);
                pos.setY(pos.getY() + 1);
                break;
        }
    }

    public void solve(){
        long start = System.nanoTime();
        while(calculateScore() != 0){
            findOptimumMove();
        }
        long end = System.nanoTime();
        solvingTime = end - start;
    }


    public void findOptimumMove(){
        int bestPosition = 0; // index of the best position
        int bestX = -1;
        int bestY = -1;
        int bestOverallScore = calculateScore();

        for (int i = 0; i < positions.size(); i++){
            Position backupPos = new Position(positions.get(i)); // get backup for the position
            Position pos = positions.remove(i); // remove position to avoid conflict
            int currentIndScore = individualScore(pos);
            if(currentIndScore == 0){ // this position is already in an optimized state. Go next.
                positions.add(i, backupPos);
                continue;
            }
            int changeX = pos.getX(); // position before searching
            int changeY = pos.getY();
            int iteration = 0; // there will be 8 iterations. Clockwise, 45 degrees. Starts from 90 degree
            int iterationScore = 0; // score in that iteration
            while(iteration != 8 && currentIndScore != 0){
                int currentX = pos.getX(), currentY = pos.getY(); // save position

                while(true){ //if the bishop moves out of the board, end while loop
                    if (pos.getX() < 0 || pos.getY() < 0){
                        System.out.println("Error");
                        break;
                    }
                    movePerIteration(pos, iteration);
                    if (!checkPosition(pos)){
                        break;
                    }
                    else if (pos.getX() >= 8 || pos.getX() < 0 || pos.getY() >= 8 || pos.getY() < 0){
                        break;
                    }
                    iterationScore = individualScore(pos);
                    if (iterationScore < currentIndScore){
                        changeX = pos.getX();
                        changeY = pos.getY();
                        currentIndScore = iterationScore;
                    }
                }
                iteration++;
                pos.setY(currentY);
                pos.setX(currentX);
            }
            positions.add(i, pos);
            positions.get(i).setX(changeX);
            positions.get(i).setY(changeY);
            if (bestOverallScore > calculateScore()){ // best position for the last iteration is better than overall score
                //System.out.println("Found better move");
                bestX = changeX;
                bestY = changeY;
                bestPosition = i;
                bestOverallScore = calculateScore();
            }
            positions.set(i, backupPos);
        }

        if (bestX != -1){ // move in the direction we found best result in
            Position pos = positions.get(bestPosition);
            pos.setX(bestX);
            pos.setY(bestY);
            numberOfMoves++;
            //System.out.println("Setting new position");
        }
        else{ // we found the local minimum. Reset board.
            resetBoard();
        }
    }

    public void printBoard(){
        for (int i = 0; i < positions.size(); i++){ // x axis

            for (int j = 0; j < positions.size(); j++){ // y axis
                boolean foundPosition = false;
                for(Position p: positions){
                    if (p.getX() == i && p.getY() == j){
                        System.out.print("X");
                        foundPosition = true;
                        break;
                    }
                }
                if (!foundPosition){
                    System.out.print("-");
                }
                System.out.print("\t");
            }
            System.out.println();
        }
        System.out.println("Score of the board is " + calculateScore() + "\n");
    }

    public void printPositions() {
        for (Position p : positions){
            System.out.println(p.getX() + 1 + ":" + (p.getY() + 1));
        }
    }

    public int getRandomRestart() {
        return randomRestart;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public long getSolvingTime() {
        return solvingTime;
    }
}
