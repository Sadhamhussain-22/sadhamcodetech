import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * FileUtility.java
 * -----------------------------------------
 * A simple file handling utility that:
 * 1. Reads a text file
 * 2. Writes content to a file
 * 3. Modifies specific lines
 * -----------------------------------------
 * Developed for CODTECH Java Internship Task-1
 */
public class FileUtility {

    // Path of the file to operate on
    static String filePath = "sample.txt";

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("===== FILE HANDLING UTILITY =====");
            System.out.println("1. Read File");
            System.out.println("2. Write File");
            System.out.println("3. Modify File");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline
            try {
                switch (choice) {
                    case 1 -> readFile();
                    case 2 -> {
                        System.out.print("Enter text to write: ");
                        String content = sc.nextLine();
                        writeFile(content);
                    }
                    case 3 -> {
                        System.out.print("Enter line number to modify: ");
                        int lineNo = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new text: ");
                        String newText = sc.nextLine();
                        modifyFile(lineNo, newText);
                    }
                    case 4 -> System.out.println("Exiting program...");
                    default -> System.out.println("Invalid choice!");
                }
            } catch (IOException e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
    }

    /** Reads and prints file content */
    public static void readFile() {
        System.out.println("\n--- File Content ---");
        Path path = Paths.get(filePath);
        try {
            if (Files.notExists(path)) {
                Files.createFile(path);
                System.out.println("(Note: file not found — created empty file: " + filePath + ")");
                return;
            }
            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.out.println("Unable to read file: " + e.getMessage());
        }
    }

    /** Writes text into the file (overwrites old content) */
    public static void writeFile(String content) throws IOException {
        Files.writeString(Paths.get(filePath), content);
        System.out.println("\nText written successfully!");
    }

    /** Modifies a particular line in the file */
    public static void modifyFile(int lineNo, String newText) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        if (lineNo > 0 && lineNo <= lines.size()) {
            lines.set(lineNo - 1, newText);
            Files.write(Paths.get(filePath), lines);
            System.out.println("\nLine modified successfully!");
        } else {
            System.out.println("\nInvalid line number!");
        }
    }
}
