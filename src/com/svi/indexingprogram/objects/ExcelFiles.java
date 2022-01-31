package com.svi.indexingprogram.objects;

import java.io.File;
import java.util.List;

public class ExcelFiles {
	private static List<File> excelFiles;

	public static List<File> getFiles() {
		return excelFiles;
	}

	public static void setFiles(List<File> excelFiles) {
		ExcelFiles.excelFiles = excelFiles;
	}
}
