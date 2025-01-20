package com.spotify;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MusicPlayerService {
    private List<Song> songLibrary;
    private Map<String, Playlist> playlists;
    private ExecutorService executor;

    public MusicPlayerService() {
        this.songLibrary = new ArrayList<>();
        this.playlists = new HashMap<>();
//        this.executor = Executors.newSingleThreadExecutor();
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
//            try {
//                Thread.sleep(3000); // Simulate playing time
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
        });
    }

//    public void shutdown() {
//        executor.shutdown();
//    }

    // Playlist management
    public void createPlaylist(String playlistName) {
        if (playlists.containsKey(playlistName)) {
            throw new IllegalArgumentException("Playlist already exists: " + playlistName);
        }
        playlists.put(playlistName, new Playlist(playlistName));
    }

    public void addSongToPlaylist(String playlistName, Song song) {
        Playlist playlist = playlists.get(playlistName);
        if (playlist == null) {
            throw new IllegalArgumentException("Playlist not found: " + playlistName);
        }
        playlist.addSong(song);
    }

    public Map<String, Playlist> getAllPlaylists() {
        return playlists;
    }

    public Playlist getPlaylist(String playlistName) {
        Playlist playlist = playlists.get(playlistName);
        if (playlist == null) {
            throw new IllegalArgumentException("Playlist not found: " + playlistName);
        }
        return playlist;
    }
}
