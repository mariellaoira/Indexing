package com.svi.indexingprogram;

//all import files
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class represents the period codes for searching a word in multiple Excel files and displaying the results.
 * @author Mariella Joyce Oira
 * */
public class Main {
	static char CHOICE;
	
	static String EXCEL_DIRECTORY;
	static String SEARCH_TEXT;
	
	static List<File> FILES;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		parseProperties();
		searchExcelFiles();
	}
	
	 /**
     * Method for displaying Program Version and reading all Excel files in Input_directory from config.properties
     *  @return Program Version and Excel files in Input_Directory
     */
	public static void parseProperties() throws IOException {
		// Getting 'Input Directory' and saving it to 'excelDirectory' variable
		FileReader reader = new FileReader("C:\\Users\\Ayeeh\\eclipse-workspace\\MOIRA_IndexingProgram\\resource\\config.properties");
		Properties p = new Properties();
		p.load(reader);
		EXCEL_DIRECTORY = p.getProperty("Input_directory");

		// Display 'Program Version'
		System.out.println("Program Version: " + p.getProperty("version") + "\n");

		FILES = Files.list(Paths.get(EXCEL_DIRECTORY)).filter(Files::isRegularFile).map(Path::toFile)
				.collect(Collectors.toList());
	}
	
	/**
     * Method for searching a word in the program. 
     * @param Enter a word, and this method will search the word from all Excel files in Input_directory.
     * @param Store all data in ArrayList and HashMap.
     *  @return Display search results from all Excel files, 
     */
	@SuppressWarnings("resource")
	public static void searchExcelFiles() throws IOException {
		do {
			int count = 0;
			Scanner userInput = new Scanner(System.in);
			// Will store the file name, sheet name, row number, and column char
			ArrayList<String> searchResults = new ArrayList<String>();
			// Will store the 'searched text' and the searchResults ArrayList collection
			Map<String, ArrayList<String>> searchResultMap = new HashMap<String, ArrayList<String>>();
			// Ask user to input a word
			System.out.println("Please input a word to be searched: ");
			SEARCH_TEXT = userInput.next();
			try {
				for (File f : FILES) {
					FileInputStream fis = new FileInputStream(new File(f.getPath()));
					// creating workbook instance that refers to .xls file
					XSSFWorkbook wb = new XSSFWorkbook(fis);
					// creating a Sheet object to retrieve the object
					for (int i = 0; i < wb.getNumberOfSheets(); i++) {
						XSSFSheet sheet = wb.getSheetAt(i);
						for (Row row : sheet) // iteration over row using for each loop
						{
							for (Cell cell : row) // iteration over cell using for each loop
							{
								if (cell.getStringCellValue().equalsIgnoreCase(SEARCH_TEXT)) {
									count++;
									int rowNumber = cell.getRow().getRowNum() + 1;
									String s = "(" + count + ")File =  \"" + f.getName() + "\",\tSheet= \""
											+ wb.getSheetName(i) + "\",\t Row= \"" + rowNumber
											+ "\"\tCol = \"" + cell.getAddress().toString().charAt(0) + "\"";
									searchResults.add(s);
								}
							}
						}
					}
				}
			} catch (POIXMLException e) {
				System.out.println("Error while reading the directory");
			}
			if (count > 0) {
				searchResultMap.put(SEARCH_TEXT, searchResults);
				for (Map.Entry<String, ArrayList<String>> map : searchResultMap.entrySet()) {
					ArrayList<String> displaySearchResult = new ArrayList<String>();
					// print message for count of times word found in sheets
					System.out.println("Search keyword \"" + map.getKey() + "\" found (" + count
							+ ") time/s in the following locations: ");
					displaySearchResult = map.getValue();
					// print message for file name, Sheet name, row no, column no. where word is
					// found
					for (String s : displaySearchResult) {
						System.out.println(s);
					}
				}
			} else {
				System.out.println("Sorry, '" + SEARCH_TEXT + "' is not found in the spreadsheets. Please try another word.");
				searchExcelFiles();
			}
			//Program will exit once user input 'n'.
			do {
				System.out.println("Do you want to search again(y/n)");
				CHOICE = userInput.next().charAt(0);
			} while (CHOICE != 'n' && CHOICE != 'y');
			if (CHOICE == 'n') {
				System.out.println("System closed. Thank you!");
				System.exit(0);
			}
		} while (CHOICE == 'y'); //Program will continue as long as user input 'y'
	}
}
