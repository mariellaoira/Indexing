package com.svi.indexingprogram.objects;

import java.util.ArrayList;

public class SearchResultsArrayList {
	private static ArrayList<String> searchResults;

	public static ArrayList<String> getList() {
		return searchResults;
	}

	public static void setList(ArrayList<String> searchResults) {
		SearchResultsArrayList.searchResults = searchResults;
	}
}
