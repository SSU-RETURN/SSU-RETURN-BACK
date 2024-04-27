package com.app.demo.service.impl;

import com.app.demo.dto.request.MusicRequestDTO;
import com.app.demo.dto.response.MusicResponseDTO;
import com.app.demo.entity.Music;
import com.app.demo.repository.MusicRepository;
import com.app.demo.service.MusicService;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;

    SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(System.getenv("SPOTIFY_CLIENT_ID"))
            .setClientSecret(System.getenv("SPOTIFY_CLIENT_SECRET"))
            .build();


    @Override
    public String getAccessToken() {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
             spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            log.info("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
        }
        return spotifyApi.getAccessToken();
    }

    @Override
    public List<MusicResponseDTO.MusicSearchContentDTO> searchMusic(String keyword, int page) {
        getAccessToken();
        List<MusicResponseDTO.MusicSearchContentDTO> searchResponseDTOList = new ArrayList<>();
        try {
            SearchTracksRequest searchTrackRequest = spotifyApi.searchTracks(keyword)
                    .limit(10)
                    .offset(page*10)
                    .build();

            Paging<Track> searchResult = searchTrackRequest.execute();
            Track[] tracks = searchResult.getItems();

            for (Track track : tracks) {
                searchResponseDTOList.add(MusicResponseDTO.MusicSearchContentDTO.builder()
                        .title(track.getName())
                        .artist(track.getArtists()[0].getName())
                        .pictureKey(track.getAlbum().getImages().length > 0 ? track.getAlbum().getImages()[0].getUrl() : "NO_IMAGE")
                        .build());
            }

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
        }
        return searchResponseDTOList;
    }

    @Override
    @Transactional
    public List<Music> saveMusic(MusicRequestDTO.SaveMusicDTO request) {
        List<Music> savedMusics = new ArrayList<>();
        for (MusicRequestDTO.MusicContentDTO musicInfo : request.getMusics()){
            Optional<Music> existingMusic = musicRepository.findByTitleAndArtist(musicInfo.getTitle(), musicInfo.getArtist());

            if(existingMusic.isEmpty()) {
                Music music = Music.builder()
                        .title(musicInfo.getTitle())
                        .artist(musicInfo.getArtist())
                        .pictureKey(musicInfo.getPictureKey())
                        .build();

                musicRepository.save(music);
                savedMusics.add(music);
            }
        }

        return savedMusics;
    }
}
