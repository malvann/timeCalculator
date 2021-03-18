package com.lepskaja.timeCalculator.manager;

import com.lepskaja.timeCalculator.converter.ProjectDataConverter;
import com.lepskaja.timeCalculator.exception.ProjFileException;
import com.lepskaja.timeCalculator.validator.ProjectDataValidator;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class ProjManager {
    private static final ConcurrentMap<String, Integer> PROJECT_MAP = new ConcurrentHashMap<>();

    public static ConcurrentMap<String, Integer> getProjectMap() throws ProjFileException, IOException {
        if (PROJECT_MAP.isEmpty()){
            List<String> rows = FileManager.read();
            fillProjectMap(rows);
        }
        Thread thread = new SaveManager();
        thread.setDaemon(true);
        thread.start();
        return PROJECT_MAP;
    }

    private static void fillProjectMap(List<String> data) {
        if (data==null) return;
    data.forEach(
        s -> {
          if (ProjectDataValidator.isValid(s)) {
            Map.Entry<String, Integer> e = ProjectDataConverter.convertToEntry(s);
            PROJECT_MAP.put(e.getKey(), e.getValue());
          }
        });
    }

    @SneakyThrows
    public static void saveProjMap() {
        FileManager.wright(PROJECT_MAP);
    }

    static class SaveManager extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            while (!this.isInterrupted()){
                saveProjMap();
                TimeUnit.MINUTES.sleep(20);
            }
        }
    }
}
