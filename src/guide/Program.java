package guide;

import java.lang.System;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Program {
    public static void main(String[] args) {
        // Construct our program class
        new Program();
    }

    private Scanner scanner;
    private Database database;

    public Program() {
        scanner = new Scanner(System.in);
        database = new Database();

        String[] choices = {
            "Create new database.",
            "Load database from file.",
            "Save database to file.",
            "Add student to database.",
            "Delete student from database.",
            "View student info in database.",
            "View stats of students.",
            "Quit program."
        };
        Runnable[] action = {
            () -> CreateDatabase(),
            () -> LoadDatabase(),
            () -> SaveDatabase(),
            () -> AddStudent(),
            () -> DeleteStudent(),
            () -> ViewStudent(),
            () -> ViewStats(),
            () -> ExitProgram()
        };

        assert choices.length == action.length;

        while (true) {
            // TODO: Create different modes
            System.out.println();
            System.out.println("Teacher's student database v0.1");
            int headers = database.GetNumHeaders();
            headers = headers == 0 ? 0 : headers - 1;
            System.out.printf("Number of Subjects: %d, Number of data: %d\n", headers, database.GetNumDatas());
            System.out.println("You can select one of the options below.");
            System.out.println();

            // Print all the choices from choices array
            for (int i = 0; i < choices.length; ++i) {
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

    void CreateDatabase() {
        System.out.println("Create database");
        //TODO: Prompt for input
        database.Create(0);
    }

    void LoadDatabase() {
        System.out.println("Open file");
        while (true) {
            System.out.print("Input name of file to open (including .csv), to cancel type 0: ");
            String input = scanner.nextLine();
            try {
                database.Load(input);
                break;
            } catch (IOException e) {
                try {
                    int exit = Integer.parseInt(input);
                    if (exit == 0) {
                        System.out.println("Quit loading database.");
                        break;
                    }
                } catch (NumberFormatException nfe) {
                }
                System.out.println();
                System.out.println("No file found, please try again.");
                System.out.println("Type in 0 and press enter if you want to cancel and go back to main menu.");
                System.out.println();
            }
        }
    }

    void SaveDatabase() {
        System.out.println("Save database");
        while (true) {
            System.out.print("Input name of file to save to, (including .csv), to cancel type 0: ");
            String input = scanner.nextLine();
            try {
                database.Save(input);
                break;
            } catch (IOException io) {
                try {
                    int exit = Integer.parseInt(input);
                    if (exit == 0) {
                        System.out.println("Quit loading database.");
                        break;
                    }
                } catch (Exception e){
                }
                System.out.println();
                System.out.println("No file found, please try again.");
                System.out.println("Type in 0 and press enter if you want to cancel and go back to main menu.");
                System.out.println();
            }
        }

    }

    void AddStudent() {
        System.out.println("Add student");
    }

    void DeleteStudent() {
        System.out.println("Delete student");
    }

    void ViewStudent() {
        System.out.println("View student");
        System.out.println();
        database.Show();
    }

    void ViewStats() {
        //TODO: Compare multiple students?

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

    void ExitProgram() {
        System.out.println("Exiting program...");
        System.exit(0);
    }
}

class Database {
    public String[] headers;
    public List<String[]> data;

    public Database() {
        // headers = new String[] {};
        // data = new ArrayList<String[]>();
        Create(0);
    }

    public void Create(int numHeaders) {
        headers = new String[numHeaders];
        data = new ArrayList<String[]>();
        // headers = new String[]{ "Name", "English", "Math", "Science" };
        // data = Arrays.asList(new String[][] {{"Jane", "10", "10", "20"}, {"May", "10", "20", "30"}});
    }

    public void Save(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, StandardCharsets.UTF_8);
        try {
            char sep = '\0';

            for (String elem : headers) {
                fileWriter.write(sep + elem);
                sep = ',';
            }
            fileWriter.write('\n');

            sep = '\0';
            for (String[] row : data) {
                for (String elem : row) {
                    fileWriter.write(sep + elem);
                    sep = ',';
                }
                fileWriter.write('\n');
                sep = '\0';
            }
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
        System.out.println("Successfully written to " + fileName);
    }

    public void Load(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName, StandardCharsets.UTF_8);
        try (BufferedReader br = new BufferedReader(fileReader)) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                String[] items = line.split(",");
                if (header) {
                    header = false;
                    headers = items;
                } else {
                    data.add(items);
                }
            }

        }
    }

    public void Add(String entry) {
    }

    public void Remove(String entry) {
    }

    public void Show() {
        char sep = '\0';
        for (String elem : headers) {
            System.out.print(sep + elem);
            sep = ',';
        }
        System.out.println();

        sep = '\0';
        for (String[] row : data) {
            for (String elem : row) {
                System.out.print(sep + elem);
                sep = ',';
            }
            System.out.println();
            sep = '\0';
        }
    }

    public int GetNumHeaders() {
        return headers.length;
    }

    public int GetNumDatas() {
        return data.size();
    }
}
