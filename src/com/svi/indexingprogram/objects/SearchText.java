package com.svi.indexingprogram.objects;


public class SearchText {
	private static String searchText;

	public static String getText() {
		return searchText;
	}

	public static void setText(String searchText) {
		SearchText.searchText = searchText;
	}
}
