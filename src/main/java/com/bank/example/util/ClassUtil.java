package com.bank.example.util;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ClassUtil {

    @SneakyThrows
    public static Class<?> getModelClassByName(String className) {

        Optional<Path> fileNameOptional = Files.find(Paths.get("src/main/java/com/bank/example/model"),
                Integer.MAX_VALUE,
                (filePath, fileAttr) -> filePath.endsWith(className + ".java"))
                .findFirst();

        if(!fileNameOptional.isPresent()) {
            throw new RuntimeException("Класс " + className + " не найден в пакете src/main/java/com/bank/example/model");
        }

        Path path = fileNameOptional.get();
        int pathLength = path.getNameCount();

        String fileName = path.subpath(3, pathLength).toString();
        fileName = fileName.replaceAll("/", ".");
        fileName = fileName.substring(0, fileName.lastIndexOf('.'));

        System.out.println(path);
        System.out.println(fileName);

        return Class.forName(fileName);
    }
}
