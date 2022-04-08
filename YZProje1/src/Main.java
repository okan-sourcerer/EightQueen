public class Main {

    public static void main(String[] args) {

        System.out.format("%25s%25s%30s\n", "Random Restart sayısı", "Yer değiştirme sayısı", "İşlem süresi (Nanosaniye)");

        for (int i = 0; i < 15; i++){ // Eight queen problem solved 15 times:
            EightQueen eightQueen = new EightQueen();
            eightQueen.solve();
            System.out.format("%25d%25d%30d\n", eightQueen.getRandomRestart(), eightQueen.getNumberOfMoves(), eightQueen.getSolvingTime());
        }

    }


}
