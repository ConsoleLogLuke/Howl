package wtf.lpc.howl.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class FileUtils {
    public static void writeText(File file, String text) {
        String[] lines = text.split("\n");

        try {
            Files.write(file.toPath(), Arrays.asList(lines), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readText(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
