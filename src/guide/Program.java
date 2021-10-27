package guide;

import java.lang.System;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
            "Create new database with new subjects.",
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
            ClearScreen();
            System.out.println();
            System.out.println("Teacher's student database v0.1");
            int headers = database.GetNumHeaders();
            headers = headers == 0 ? 0 : headers - 1;
            System.out.printf("Number of Subjects: %d.\n", headers);
            System.out.printf("Number of Students: %d.\n", database.GetNumDatas());
            System.out.println("You can input one of the options below, e.g. 0.");
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
            System.out.print("Input: ");
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
        System.out.println("Creating database ...");
        System.out.println();

        int numSubjects = 0;
        while (true) {
            System.out.print("Input number of subjects to create for this database: ");
            try {
                String input = scanner.nextLine();
                numSubjects = Integer.parseInt(input);

                if (numSubjects <= 0) {
                    System.out.println("Invalid number detected, input valid whole numbers.");
                    continue;
                }

                break;
            } catch(InputMismatchException | NumberFormatException  e) {
                System.out.println();
                System.out.println("Invalid number detected, input valid whole numbers.");
                System.out.println();
            }
        }

        // +1 to account for header name in csv
        final int numHeaders = numSubjects + 1;
        String[] subjects = new String[numHeaders];
        subjects[0] = "name";
        int i = 1;

        while (i < numHeaders) {
            try {
                System.out.print("Input subject name: ");
                subjects[i] = scanner.nextLine();
                if (subjects[i].isBlank()) {
                    System.out.println("Invalid subject name detected, input valid strings.");
                    continue;
                }
                ++i;
            } catch(InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid subject name detected, input valid strings.");
            }
        }

        System.out.println();
        database.Create(subjects);
        System.out.println("Created database.");

        PromptEnterKey();
    }

    void LoadDatabase() {
        System.out.println("Opening file ...");
        System.out.println();

        while (true) {
            System.out.println("Input name of file to open, (excluding .csv), e.g. data, file");
            System.out.println("To cancel, leave blank and press enter.");
            System.out.println();
            System.out.print("Input: ");

            String input = scanner.nextLine();
            try {
                System.out.println();
                if (input.equals("")) {
                    System.out.println("No filename detected, will quit loading database.");
                    break;
                }
                database.Load(input + ".csv");
                System.out.println("Database loaded.");
                break;
            } catch (IOException e) {
                System.out.println();
                System.out.println("No file found, please try again.");
                System.out.println();
            }
        }
        PromptEnterKey();
    }

    void SaveDatabase() {
        if (database.GetNumHeaders() <= 1) {
            System.out.println("Error: There are no subjects in database. Please consider adding subjects before saving.");
            PromptEnterKey();
            return;
        }

        System.out.println("Saving file ...");
        System.out.println();

        while (true) {
            System.out.println("Input name of file to save to, (excluding .csv), e.g. data, file");
            System.out.println("To cancel, leave blank and press enter.");
            System.out.println();
            System.out.print("Input: ");

            String input = scanner.nextLine();
            try {
                System.out.println();
                if (input.equals("")) {
                    System.out.println("No filename detected, will quit saving database.");
                    break;
                }

                database.Save(input + ".csv");
                System.out.println("Database saved.");
                break;
            } catch (IOException io) {
                System.out.println();
                System.out.println("No file found, please try again.");
                System.out.println();
            }
        }
        PromptEnterKey();
    }

    void AddStudent() {
        if (database.GetNumHeaders() <= 1) {
            System.out.println("Error: There are no subjects in database. Please consider adding subjects before adding students.");
            PromptEnterKey();
            return;
        }

        System.out.println("Adding student ...");
        System.out.println();

        System.out.println("Input the name of the students followed by their grades.");
        System.out.println("To stop adding students, leave the name of the student blank.");

        while (true) {
            System.out.println();
            System.out.print("Student name: ");
            String name = scanner.nextLine();

            if (name.equals("")) {
                System.out.println();
                System.out.println("No student name detected, will stop adding students.");
                break;
            }

            final int numSubjects = database.GetNumHeaders() - 1;
            int[] grades = new int[numSubjects];
            int i = 0;

            while (i < numSubjects) {
                try {
                    System.out.printf("%s: ", database.GetSubject(i));
                    String line = scanner.nextLine();
                    grades[i] = Integer.parseInt(line);
                    ++i;
                } catch(ArrayIndexOutOfBoundsException |
                        InputMismatchException |
                        NumberFormatException e) {
                    System.out.println("Invalid number detected, input valid whole numbers.");
                }
            }

            Student student = new Student(name, grades);
            database.Add(student);
        }

        PromptEnterKey();
    }

    void DeleteStudent() {
        if (database.GetNumHeaders() <= 1) {
            System.out.println("Error: There are no subjects in database. Please consider adding subjects before removing.");
            PromptEnterKey();
            return;
        }

        if (database.GetNumDatas() == 0) {
            System.out.println("Error: There are no students in database. Please consider adding students before removing.");
            PromptEnterKey();
            return;
        }

        System.out.println("Deleting student ...");
        System.out.println();
        System.out.println("Input the name of student you wish to delete, leave student name blank to stop deleting.");

        while (true) {
            System.out.println();
            database.ShowAll();
            System.out.println();
            System.out.print("Student name: ");
            String name = scanner.nextLine();

            if (name.equals("")) {
                System.out.println();
                System.out.println("No student name detected, will stop deleting students.");
                break;
            }

            if (database.Remove(name)) {
                System.out.printf("Deleted %s from database.\n", name);
            } else {
                System.out.printf("Could not find %s from database.\n", name);
            }
        }
        PromptEnterKey();
    }

    void ViewStudent() {
        System.out.println("Viewing student ...");
        System.out.println();

        database.Show("");
        PromptEnterKey();
    }

    void ViewAllStudent() {
        System.out.println("Viewing all student ...");
        System.out.println();

        database.ShowAll();
        PromptEnterKey();
    }

    void ViewStats() {
        // TODO: Show top scorer, how many students etc
        System.out.println("Viewing all student stats ...");
        System.out.println();

        System.out.printf("Number of Headers: %d.\n", database.GetNumHeaders());
        System.out.printf("Number of Datas: %d.\n", database.GetNumDatas());
        PromptEnterKey();
    }

    void ExitProgram() {
        System.out.println("Exiting program...");
        System.exit(0);
    }

    void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    void PromptEnterKey() {
        System.out.println();
        System.out.print("Press \"ENTER\" to continue... ");
        scanner.nextLine();
    }
}

class Database {
    public String[] headers;
    public List<Student> data;

    public Database() {
        Create(new String[]{});
    }

    public void Create(String[] subjects) {
        headers = subjects;
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
                if (line.isBlank()) {
                    continue;
                }
                String[] items = line.split(",");
                if (header) {
                    header = false;
                    headers = items;
                } else {
                    data.add(new Student(items));
                }
            }
        }
        System.out.println("Successfully loaded from " + fileName);
    }

    public void Add(Student student) {
        data.add(student);
    }

    public boolean Remove(String name) {
        int i = 0;
        boolean found = false;
        for (Student student : data) {
            if (student.GetName().equals(name)) {
                found = true;
                break;
            }
            ++i;
        }

        data.remove(i);
        return found;
    }

    public void Show(String name) {
    }

    public void ShowAll() {
        for (String elem : headers) {
            String[] items = elem.split(",");
            for (String item : items) {
                System.out.printf("%s\t", item);
            }
        }
        System.out.println();

        for (Student student : data) {
            String[] items = student.toString().split(",");
            for (String item : items) {
                System.out.printf("%s\t", item);
            }
            System.out.println();
        }
    }

    public int GetNumHeaders() {
        return headers.length;
    }

    public int GetNumDatas() {
        return data.size();
    }

    public String GetSubject(int i) {
        return headers[i + 1];
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

    public String GetName() {
        return name;
    }
}
