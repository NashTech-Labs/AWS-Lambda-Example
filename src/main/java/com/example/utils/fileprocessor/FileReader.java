package com.example.utils.fileprocessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileReader {

    public static String getDataFromFile(String folderPath) throws IOException {
        return Files.readAllLines(Paths.get(folderPath)).stream().collect(Collectors.joining("\n"));
    }

}
