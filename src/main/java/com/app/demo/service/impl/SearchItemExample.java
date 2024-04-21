package com.app.demo.service.impl;

import com.app.demo.dto.response.MusicResponseDTO;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
public class SearchItemExample {

    static String accessToken = "BQAmZ7zOKXmM1gInMGsU34QeI-zKv160wx5je4ofAIMgwKPZJlsHcBSPfVxjnnNLUfZfv7pHKbxwGZIt8cS0hST_ajcKqN53bsn3uBSGDWkkOUyFLq4";
    static SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();



    public static List<MusicResponseDTO.SearchResponseDTO> searchMusic(String keyword, int page) {
      //  getAccessToken();
        List<MusicResponseDTO.SearchResponseDTO> searchResponseDtoList = new ArrayList<>();
        try {
            SearchTracksRequest searchTrackRequest = spotifyApi.searchTracks(keyword)
                    .limit(10)
                    .offset(page*10)
                    .build();

            Paging<Track> searchResult = searchTrackRequest.execute();
            Track[] tracks = searchResult.getItems();

            for (Track track : tracks) {
                searchResponseDtoList.add(MusicResponseDTO.SearchResponseDTO.builder()
                        .title(track.getName())
                        .artistName(track.getArtists()[0].getName())
                        .imageUrl(track.getAlbum().getImages().length > 0 ? track.getAlbum().getImages()[0].getUrl() : "NO_IMAGE")
                        .build());
            }

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
        }
        return searchResponseDtoList;
    }

    public static void main(String[] args) {
        System.out.println(searchMusic("IU",1));
    }

}
