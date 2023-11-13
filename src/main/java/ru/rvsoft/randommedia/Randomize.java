package ru.rvsoft.randommedia;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Randomize {
    private final File dirWithMusic;
    private Map<File, String> fileMap;
    private int fileCount = 0;

    public Randomize(File dirWithMusic) {
        if (dirWithMusic == null || !dirWithMusic.isDirectory()) {
            throw new IllegalStateException("Not a directory!");
        }
        this.dirWithMusic = dirWithMusic;
    }

    public void prepareDir() {
        File[] files = dirWithMusic.listFiles();
        if (files == null) {
            throw new IllegalStateException("Not a music!");
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".mp3")) {
                fileCount++;
            }
        }
        if (fileCount == 0) {
            throw new IllegalStateException("No mp3 files found in directory!");
        }
        System.out.println(fileCount);
    }

    public void processFile(File file) {
        try {
            Mp3File mp3File = new Mp3File(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedTagException e) {
            throw new RuntimeException(e);
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String dir = (String) System.getProperties().get("user.dir");
        System.out.println(dir);
        Randomize randomize = new Randomize(new File(dir));
        randomize.prepareDir();
    }
}
