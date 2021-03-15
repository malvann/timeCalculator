package com.lepskaja.timeCalculator.manager;

import com.lepskaja.timeCalculator.converter.ProjectDataConverter;
import com.lepskaja.timeCalculator.exception.ProjFileException;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileManager {
    private static final String ERR_MESSAGE = "File/directory does not exist.";
    private static final File FILE = new File("src/main/resources/base/files.txt");
    private static final File COPY1 = new File("src/main/resources/base/files1.txt");
    private static final File COPY2 = new File("src/main/resources/base/files2.txt");
    private static final File DIR = new File("src/main/resources/base/");

    private FileManager() {}

    public static List<String> read() throws ProjFileException, IOException {
        if (!FILE.exists()) throw new ProjFileException(ERR_MESSAGE);
        List<String> rows;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))){
            rows = reader.lines().collect(Collectors.toList());
        }
        return rows;
    }

    public static void wright(Map<String, Integer> projMap) throws ProjFileException, IOException {
        createCopy();
        List<String> projSteam = projMap.entrySet().stream().map(ProjectDataConverter::convertToString)
                .collect(Collectors.toList());

        if (!DIR.isDirectory()) throw new ProjFileException(ERR_MESSAGE);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))){
            for (String s: projSteam) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void createCopy() throws IOException {
//        Files.delete(COPY2.toPath());
//        Files.copy(COPY1.toPath(), COPY2.toPath());
//        Files.delete(COPY1.toPath());
//        Files.copy(FILE.toPath(), COPY1.toPath());
    }
}
