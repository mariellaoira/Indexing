package com.svi.indexingprogram.methods;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Collectors;

import com.svi.indexingprogram.objects.*;

public class ParseProperties {
	/**
     * Method for getting and displaying Program Version a from config.properties
     *  @return Program Version
     */
	public static void parseProperties() throws IOException {
		// Getting 'Input Directory' and saving it to 'excelDirectory' variable
		FileReader reader = new FileReader("C:\\Users\\Ayeeh\\eclipse-workspace\\MOIRA_IndexingProgram\\resource\\config.properties");
		Properties p = new Properties();
		p.load(reader);
		ExcelDirectory.setDirectory(p.getProperty("Input_directory"));
		// Display 'Program Version'
		System.out.println("Program Version: " + p.getProperty("version") + "\n");

		ExcelFiles.setFiles(Files.list(Paths.get(ExcelDirectory.getDirectory())).filter(Files::isRegularFile).map(Path::toFile)
				.collect(Collectors.toList()));
	}
}
