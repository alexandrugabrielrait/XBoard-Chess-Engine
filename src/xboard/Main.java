package xboard;

public class Main {

    public static void main(String[] args) {
        String command;

        while (true) {
            command = ChessEngine.sc.nextLine();
            Interpreter.handleCommand(command);
        }
    }

    /**
     * Metoda opreste programul
     */
    static void quit() {
        System.exit(0);
    }
}
