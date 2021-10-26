package guide;

import java.lang.System;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

//TODO: Instead of making this a program for char guide, make this into a program that keep tracks of scores for example

class Program {
    public static void main(String[] args) {
        // Construct our program class
        new Program();
    }

    private Scanner scanner;

    public Program() {
        //TODO: New, Open, Save, Add, Delete, List one, List many, See top students
        String[] choices = {
            "Open database from file.",
            "View characters in database.",
            "Compare characters in database.",
            "List all characters in database.",
            "Add characters to database.",
            "Remove characters to database.",
            "Save characters to database.",
            "Quit program."
        };

        Runnable[] action = {
            () -> OpenFile(),
            () -> ViewCharacters(),
            () -> CompareCharacters(),
            () -> ListCharacters(),
            () -> AddCharacters(),
            () -> DeleteCharacters(),
            () -> SaveFile(),
            () -> System.exit(0),
        };


        //System.in is a standard input stream
        scanner = new Scanner(System.in);

        while (true) {
            // TODO: Create different modes
            System.out.println();
            System.out.println("Welcome to our Java game guide database program for viewing game characters!!");
            System.out.println("You can select one of the options below.");
            System.out.println();

            // Print all the choices from choices array
            for (int i = 0; i < choices.length; i++) {
                System.out.printf("%d.) %s\n", i, choices[i]);
            }
            System.out.println();

            int choice = GetChoice(choices.length);
            action[choice].run();
        }
    }

    int GetChoice(int maxChoice) {
        while (true) {
            System.out.print("Choice: ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 0 && choice < maxChoice) {
                    System.out.println();
                    return choice;
                } else {
                    System.out.println("Invalid choice.");
                }
            } catch(NumberFormatException e) {
                System.out.println("Not a valid number, enter a valid number.");
                System.out.println();
                continue;
            }
        }
    }

    void OpenFile() {
        System.out.println("Open file");
        while (true) {
            System.out.print("Input name of file to open (including .csv): ");
            String input = scanner.nextLine();
            try {
                CSV csv = new CSV(input);
                break;
            } catch (IOException e) {
                System.out.println("No file found, please try again.");
            }
        }
    }

    void ViewCharacters() {
        System.out.println("View characters");
    }

    void CompareCharacters() {
        //TODO: Compare multiple characters?
        
        System.out.println("Compare characters");

        System.out.print("Input the name of the first character: ");
        String nameFirstChar = scanner.nextLine();
        // System.out.println(nameFirstChar);

        System.out.print("Input the name of the second character: ");
        String nameSecondChar = scanner.nextLine();
        // System.out.println(nameSecondChar);

        // TODO: Implement the comparision strategy
        System.out.println("Result: ");
    }

    void ListCharacters() {
        System.out.println("List characters");
    }

    void AddCharacters() {
        System.out.println("Add characters");
        
    }

    void DeleteCharacters() {
        System.out.println("Delete characters");
        
    }

    void SaveFile() {
        System.out.println("Save file");
        
    }
}

class CSV {
    public CSV(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName, StandardCharsets.UTF_8);
        try (BufferedReader br = new BufferedReader(fileReader)) {
            var sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }

            System.out.println(sb);
        }
    }
    //TODO: Create helper methods
}
