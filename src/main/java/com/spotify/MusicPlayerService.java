package com.spotify;

import com.spotify.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MusicPlayerService {
    private List<Song> songLibrary;
    private ExecutorService executor;

    public MusicPlayerService() {
        this.songLibrary = new ArrayList<>();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void loadSongs(String filePath) throws IOException {
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts;
                if ("csv".equals(extension)) {
                    parts = line.split(",");
                } else if ("tsv".equals(extension)) {
                    parts = line.split("\t");
                } else {
                    throw new IllegalArgumentException("Unsupported file format: " + extension);
                }

                if (parts.length >= 2) {
                    songLibrary.add(new Song(parts[0].trim(), parts[1].trim()));
                }
            }
        }
    }

    public List<Song> getSongLibrary() {
        return songLibrary;
    }

    public void playSong(Song song) {
        executor.submit(() -> {
            System.out.println("Now playing: " + song);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}
