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
            "View all student info in database.",
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
            () -> ViewAllStudent(),
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
        database.Create();
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
        database.Show("");
    }

    void ViewAllStudent() {
        System.out.println("View all student");
        System.out.println();
        database.ShowAll();
    }

    void ViewStats() {
        // TODO: Show top scorer, how many students etc
    }

    void ExitProgram() {
        System.out.println("Exiting program...");
        System.exit(0);
    }
}

class Database {
    public String[] headers;
    public List<Student> data;

    public Database() {
        Create();
    }

    public void Create() {
        headers = new String[] {};
        data = new ArrayList<Student>();
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
            for (Student student : data) {
                fileWriter.write(student.toString());
                fileWriter.write('\n');
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
                    data.add(new Student(items));
                }
            }

        }
    }

    public void Add(String entry) {
    }

    public void Remove(String entry) {
    }

    public void Show(String name) {
    }

    public void ShowAll() {
        char sep = '\0';
        for (String elem : headers) {
            System.out.print(sep + elem);
            sep = ',';
        }
        System.out.println();

        sep = '\0';
        for (Student student : data) {
            System.out.println(student);
        }
    }

    public int GetNumHeaders() {
        return headers.length;
    }

    public int GetNumDatas() {
        return data.size();
    }
}

class Student {
    private String name;
    private int[] grades;

    public Student(String[] data) {
        if (data != null && data.length > 0) {
            int[] grades = new int[data.length - 1];
            try {
                for (int i = 1; i < data.length; ++i) {
                    grades[i - 1] = Integer.parseInt(data[i]);
                    System.out.println(grades[i-1]);
                }
            } catch (NumberFormatException e) {
            }
            this.name = data[0];
            this.grades = grades;
        }
    }

    public Student(String name, int[] grades) {
        this.name = name;
        this.grades = grades;
    }

    @Override
    public String toString() {
        String result = name + ",";
        String sep = "";
        for (int elem : grades) {
            result += (sep + elem);
            sep = ",";
        }
        return result;

    }
}
