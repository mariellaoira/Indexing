package com.svi.indexingprogram.methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.svi.indexingprogram.objects.*;

public class DisplayExcelFiles {
	/**
     * Method for storing Search Results in a hashmap and displaying all result values
     * @return Search Results based on user's input text to be searched.
     */
	public static void displayExcelFiles() throws IOException {
		// Will store the 'searched text' and the searchResults ArrayList collection
		Map<String, ArrayList<String>> searchResultMap = new HashMap<String, ArrayList<String>>();
		if (Count.getCount() > 0) {
			searchResultMap.put(SearchText.getText(), SearchResultsArrayList.getList());
			for (Map.Entry<String, ArrayList<String>> map : searchResultMap.entrySet()) {
				ArrayList<String> displaySearchResult = new ArrayList<String>();
				// print message for count of times word found in sheets
				System.out.println("Search keyword \"" + map.getKey() + "\" found (" + Count.getCount()
						+ ") time/s in the following locations: ");
				displaySearchResult = map.getValue();
				// print message for file name, Sheet name, row no, column no. where word is
				// found
				for (String s : displaySearchResult) {
					System.out.println(s);
				}
			}
		} else {
			System.out.println("Sorry, '" + SearchText.getText() + "' is not found in the spreadsheets. Please try another word.");
			SearchExcelFiles.searchExcelFiles();
			displayExcelFiles();
		}
	}
}
