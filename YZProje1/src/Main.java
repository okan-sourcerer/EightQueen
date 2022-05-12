public class Main {

    public static void main(String[] args) {

        System.out.format("%10s%25s%25s%30s\n", "Runs", "Random Restart sayısı", "Yer değiştirme sayısı", "İşlem süresi (Nanosaniye)");

        int totalRandomRestart = 0;
        int totalMoveCount = 0;
        long totalSolvingTime = 0;
        for (int i = 0; i < 15; i++){ // Eight queen problem solved 15 times:
            EightQueen eightQueen = new EightQueen();
            eightQueen.solve();
            totalMoveCount += eightQueen.getNumberOfMoves();
            totalRandomRestart += eightQueen.getRandomRestart();
            totalSolvingTime += eightQueen.getSolvingTime();
            System.out.format("%10s%25d%25d%30d\n", "Run " + (i + 1), eightQueen.getRandomRestart(), eightQueen.getNumberOfMoves(), eightQueen.getSolvingTime());
        }
        System.out.format("%10s%25.2f%25.2f%30.2f\n", "Average", (double)totalRandomRestart / 15, (double)totalMoveCount / 15, (double)totalSolvingTime / 15);
    }


}
