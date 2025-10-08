package core.lab_3;

import java.util.*;
import java.io.*;

public record Dictionary(List<TranslationPair> entries) {
    public Dictionary(List<TranslationPair> entries) {
        this.entries = entries;
        sortByLengthDesc();
    }

    private void sortByLengthDesc() {
        entries.sort((a, b) ->
                Integer.compare(b.getOriginal().length(), a.getOriginal().length()));
    }

    public static Dictionary loadFromFile(String filename)
            throws InvalidFileFormatException, FileReadException {
        List<TranslationPair> entries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                processLine(line, lineNumber, entries);
            }
        } catch (IOException e) {
            throw new FileReadException("Ошибка чтение файла: " + filename, e);
        }
        return new Dictionary(entries);
    }

    private static void processLine(String line, int lineNumber,
                                    List<TranslationPair> entries) throws InvalidFileFormatException {
        line = line.trim();
        if (line.isEmpty()) return;
        String[] parts = line.split("\\|", 2);

        if (parts.length != 2) {
            throw new InvalidFileFormatException(
                    "Неверный формат в строке " + lineNumber + ": " + line
            );
        }

        String original = parts[0].trim();
        String translation = parts[1].trim();

        if (original.isEmpty() || translation.isEmpty()) {
            throw new InvalidFileFormatException(
                    "Пустая пара переводов в строке " + lineNumber
            );
        }

        entries.add(new TranslationPair(
                original.toLowerCase(),
                translation
        ));
    }
}
