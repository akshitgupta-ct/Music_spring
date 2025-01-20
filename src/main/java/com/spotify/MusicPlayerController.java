package com.spotify;

import com.spotify.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/music")
public class MusicPlayerController {

    @Autowired
    private MusicPlayerService musicPlayerService;

    @GetMapping("/songs")
    public List<Song> getSongs() {
        return musicPlayerService.getSongLibrary();
    }

    @PostMapping("/load")
    public String loadSongs(@RequestParam String filePath) {
        try {
            musicPlayerService.loadSongs(filePath);
            return "Songs loaded successfully!";
        } catch (IOException e) {
            return "Error loading songs: " + e.getMessage();
        }
    }

    @PostMapping("/play")
    public String playSong(@RequestBody Song song) {
        try {
            musicPlayerService.playSong(song);
            return "Playing song: " + song.getTitle();
        } catch (Exception e) {
            return "Error playing song: " + e.getMessage();
        }
    }


}
