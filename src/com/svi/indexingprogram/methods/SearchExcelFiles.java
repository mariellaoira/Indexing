package com.svi.indexingprogram.methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.svi.indexingprogram.objects.*;

public class SearchExcelFiles {

	@SuppressWarnings("static-access")
	public static void searchExcelFiles() throws IOException {
		int count = 0;
		@SuppressWarnings("resource")
		Scanner userInput = new Scanner(System.in);
		// Will store the file name, sheet name, row number, and column char
		ArrayList<String> searchResults = new ArrayList<String>();
		// Ask user to input a word
		System.out.println("Please input a word to be searched: ");
		SearchText.setText(userInput.next());
		try {
			for (File f : ExcelFiles.getFiles()) {
				FileInputStream fis = new FileInputStream(new File(f.getPath()));
				// creating workbook instance that refers to .xls file
				@SuppressWarnings("resource")
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				// creating a Sheet object to retrieve the object
				for (int i = 0; i < wb.getNumberOfSheets(); i++) {
					XSSFSheet sheet = wb.getSheetAt(i);
					for (Row row : sheet) // iteration over row using for each loop
					{
						for (Cell cell : row) // iteration over cell using for each loop
						{
							if (cell.getStringCellValue().equalsIgnoreCase(SearchText.getText())) {
								count++;
								int rowNumber = cell.getRow().getRowNum() + 1;
								String s = "(" + count + ")File =  \"" + f.getName() + "\",\tSheet= \""
										+ wb.getSheetName(i) + "\",\t Row= \"" + rowNumber + "\"\tCol = \""
										+ cell.getAddress().toString().charAt(0) + "\"";
								searchResults.add(s);
								SearchResultsArrayList.setList(searchResults);
							}
						}
					}
					Count.setCount(count);
				}
			}
		} catch (POIXMLException e) {
			System.out.println("Error while reading the directory");
		}
	}

}
