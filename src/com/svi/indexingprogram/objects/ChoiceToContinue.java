package com.svi.indexingprogram.objects;

public class ChoiceToContinue {
	private static char choiceToContinue;

	public static char getChoice() {
		return choiceToContinue;
	}

	public static void setChoice(char choice) {
		ChoiceToContinue.choiceToContinue = choice;
	}
}
