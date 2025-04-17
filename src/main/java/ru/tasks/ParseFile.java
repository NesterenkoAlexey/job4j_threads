package main.java.ru.tasks;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> predicate) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
        int data;
        while ((data = input.read()) > 0) {
            if (predicate.test(data)) {
                result.append((char) data);
            }
        }
        return result.toString();
    }

    public synchronized String getContent() throws IOException {
        return getContent(integer -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return getContent(integer -> integer < 0x80);
    }
}
