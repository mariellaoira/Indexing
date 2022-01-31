package com.svi.indexingprogram.objects;

public class ExcelDirectory {
	private static String excelDirectory;

	public static String getDirectory() {
		return excelDirectory;
	}

	public static void setDirectory(String excelDirectory) {
		ExcelDirectory.excelDirectory = excelDirectory;
	}
}
