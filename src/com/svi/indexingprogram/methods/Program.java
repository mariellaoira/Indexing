package com.svi.indexingprogram.methods;

import java.io.IOException;
import java.util.Scanner;
import com.svi.indexingprogram.objects.*;


public class Program {
	public static void runProgram() throws IOException {
		ParseProperties.parseProperties();
		do {
			@SuppressWarnings("resource")
			Scanner userInput = new Scanner(System.in);
			SearchExcelFiles.searchExcelFiles(); //searching the word in all excel files
			DisplayExcelFiles.displayExcelFiles(); //displaying all search results using hashmap and arraylist
			
			//Program will exit once user input 'n'.
			do {
				System.out.println("Do you want to search again(y/n)");
				ChoiceToContinue.setChoice(userInput.next().charAt(0));
			} while (ChoiceToContinue.getChoice() != 'n' && ChoiceToContinue.getChoice() != 'y');
			if (ChoiceToContinue.getChoice() == 'n') {
				System.out.println("System closed. Thank you!");
				System.exit(0);
			}
		} while (ChoiceToContinue.getChoice() == 'y'); //Program will continue as long as user input 'y'
	}
}
