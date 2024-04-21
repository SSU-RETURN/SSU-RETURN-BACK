package com.app.demo.controller;


import com.app.demo.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/music")
@RequiredArgsConstructor
public class MusicController {
    private final MusicService musicService;

}
