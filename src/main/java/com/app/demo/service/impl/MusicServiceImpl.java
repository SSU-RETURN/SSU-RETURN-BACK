package com.app.demo.service.impl;

import com.app.demo.entity.Music;
import com.app.demo.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    @Override
    public Music getMusicFromApi() {
        return null;
    }
}
