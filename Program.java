package guide;

import java.lang.System;
import java.util.Scanner;  

class Program {
    public static void main(String[] args) {
        // Construct our program class
        new Program();
    }

    private Scanner scanner;

    public Program() {
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
        System.out.println("Load file");
    }

    void ViewCharacters() {
        System.out.println("View characters");
    }

    void CompareCharacters() {
        System.out.println("Compare characters");
        // 2. Compare mode
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
        
    }

    void AddCharacters() {
        
    }

    void DeleteCharacters() {
        
    }

    void SaveFile() {
        
    }
}
