package com.spotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

     @PostMapping("/playlists")
    public String createPlaylist(@RequestParam String playlistName) {
        try {
            musicPlayerService.createPlaylist(playlistName);
            return "Playlist '" + playlistName + "' created successfully!";
        } catch (Exception e) {
            return "Error creating playlist: " + e.getMessage();
        }
    }

     @PostMapping("/playlists/{playlistName}/add")
    public String addSongToPlaylist(@PathVariable String playlistName, @RequestBody Song song) {
        try {
            musicPlayerService.addSongToPlaylist(playlistName, song);
            return "Added song: " + song.getTitle() + " to playlist: " + playlistName;
        } catch (Exception e) {
            return "Error adding song to playlist: " + e.getMessage();
        }
    }


    @GetMapping("/playlists")
    public Map<String, Playlist> getAllPlaylists() {
        return musicPlayerService.getAllPlaylists();
    }


    //http://localhost:31078/api/music/playlists/{MyPlaylist,name of playlist}/add
    @GetMapping("/playlists/{playlistName}")
    public Playlist getPlaylist(@PathVariable String playlistName) {
        return musicPlayerService.getPlaylist(playlistName);
    }
}
