package com.app.demo.converter;

import com.app.demo.dto.response.MemberPlaylistResponseDTO;
import com.app.demo.entity.Diary;
import com.app.demo.entity.MemberPlaylist;
import com.app.demo.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MemberPlaylistInfoConverter {
    public static List<MemberPlaylistResponseDTO.MemberPlaylistInfoDTO> toMemberPlaylistInfo(List<MemberPlaylist> memberPlaylistList){
        List<Diary> diaryList = new ArrayList<>();
        for(MemberPlaylist memberPlaylist : memberPlaylistList){
            diaryList.add(memberPlaylist.getDiary());
        }
        List<MemberPlaylistResponseDTO.MemberPlaylistInfoDTO> infoList = new ArrayList<>();
        for(Diary diary : diaryList){
            infoList.add(MemberPlaylistResponseDTO.MemberPlaylistInfoDTO.builder()
                    .memberEmotion(diary.getMemberEmotion())
                    .playlistDate(diary.getWrittenDate())
                    .pictureKey(diary.getPictureKey())
                    .build());
        }
        return infoList;
    }
}
