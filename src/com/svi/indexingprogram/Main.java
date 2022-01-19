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

public class Main {
	static String excelDirectory;
	static String searchText;
	static char choice;
	static List<File> files;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		parseProperties();
		searchExcelFiles();
	}

	public static void parseProperties() throws IOException {
		// Getting 'Input Directory' and saving it to 'excelDirectory' variable
		FileReader reader = new FileReader("C:\\Users\\Ayeeh\\eclipse-workspace\\MOIRA_IndexingProgram\\resource\\config.properties");
		Properties p = new Properties();
		p.load(reader);
		excelDirectory = p.getProperty("Input_directory");

		// Display 'Program Version'
		System.out.println("Program Version: " + p.getProperty("version") + "\n");

		files = Files.list(Paths.get(excelDirectory)).filter(Files::isRegularFile).map(Path::toFile)
				.collect(Collectors.toList());
	}

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
			searchText = userInput.next();
			try {
				for (File f : files) {
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
								if (cell.getStringCellValue().equalsIgnoreCase(searchText)) {
									count++;
									String s = "(" + count + ")File =  \"" + f.getName() + "\",\tSheet= \""
											+ wb.getSheetName(i) + "\",\t Row= \"" + cell.getRow().getRowNum()
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
				searchResultMap.put(searchText, searchResults);
				for (Map.Entry<String, ArrayList<String>> map : searchResultMap.entrySet()) {
					ArrayList<String> displaySearchResult = new ArrayList<String>();
					// print message for count of times word found in sheets
					System.out.println("Search keyword \"" + map.getKey() + "\" found (" + count
							+ ") times in the ff locations: ");
					displaySearchResult = map.getValue();
					// print message for file name, Sheet name, row no, column no. where word is
					// found
					for (String s : displaySearchResult) {
						System.out.println(s);
					}
				}
			} else {
				System.out.println("Search keyword not found in files");
				searchExcelFiles();
			}
			do {
				System.out.println("Do you want to search again(y/n)");
				choice = userInput.next().charAt(0);
			} while (choice != 'n' && choice != 'y');
			if (choice == 'n') {
				System.out.println("System closed. Thank you!");
				System.exit(0);
			}
		} while (choice == 'y');
	}
}
