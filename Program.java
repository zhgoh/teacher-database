import java.lang.System;
import java.util.Scanner;  

class Program {
    public static void main(String[] args) {
		// TODO: Load character data
		
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
		// TODO: Create 2 modes (View, Compare)
		
		// 1. View mode
		// 2. Compare mode
		System.out.print("Input the name of the first character: ");
		String nameFirstChar = sc.nextLine();  
		// System.out.println(nameFirstChar);
		
		System.out.print("Input the name of the second character: ");
		String nameSecondChar = sc.nextLine();
		// System.out.println(nameSecondChar);
		
		// TODO: Implement the comparision strategy
		System.out.println("Result: ");
    }
}
