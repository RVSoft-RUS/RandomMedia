package ru.rvsoft.randommedia;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Mixer {
    private final File dirWithMusic;

    public Mixer(File dirWithMusic) {
        if (dirWithMusic == null || !dirWithMusic.isDirectory()) {
            throw new IllegalStateException("Not a directory!");
        }
        this.dirWithMusic = dirWithMusic;
    }

    public void processDir() {
        File[] files = dirWithMusic.listFiles((dir, name) -> name.endsWith(".mp3"));
        if (files == null || files.length == 0) {
            throw new IllegalStateException("No mp3 files found in directory: " + dirWithMusic);
        }

        int fileCount = files.length;

        //initialize list of random ints
        List<String> intList = new ArrayList<>(fileCount);
        Map<File, String> fileMap = new HashMap<>(fileCount);

        Random random = new Random();
        while (intList.size() < fileCount) {
            String randomInt = Integer.toString(random.nextInt(Integer.MAX_VALUE - 1000000) + 1000000);
            if (!intList.contains(randomInt)) {
                intList.add(randomInt);
            }
        }
        System.out.println(intList);

        int fileNum = 0;
        for (File file : files) {
            fileMap.put(file, intList.get(fileNum));
            fileNum++;
        }

        System.out.println(fileMap);
        //process all files in map
        fileMap.forEach(this::processFile);
    }

    public void processFile(File file, String prefix) {
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
        Mixer mixer = new Mixer(new File(dir));
        mixer.processDir();
    }
}
