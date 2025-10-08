package core.lab_3;

import java.io.*;

public class FileUtils {
    public static boolean isFileReadable(String filename) {
        File file = new File(filename);
        return file.exists() && file.canRead();
    }
}