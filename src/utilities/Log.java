package utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;

public class Log {
    static String filename = "logfile.txt";

    static public void logAnswer(String s) {
        try {
            FileWriter answers = new FileWriter(filename, true);
            answers.append(s).append("\n");
            answers.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logBoard() {
        try {
            PrintStream out = new PrintStream("board.txt");
            Printer.printBoard(out);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void emptyLogfile() {
        try {
            RandomAccessFile answers = new RandomAccessFile(filename, "rw");
            answers.setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
