package com.project.only.controller;

import com.project.only.domain.Diary;
import com.project.only.domain.DiaryRequest;
import com.project.only.domain.DiaryResponse;
import com.project.only.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/only/diary/create")
    public ResponseEntity<DiaryResponse> createDiary(@RequestParam Long memberId, @RequestBody DiaryRequest diaryRequest) {
        Diary diary = diaryService.createDiary(memberId, diaryRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body( DiaryResponse.of(diary));
    }

    @GetMapping("/only/diary/get")
    public ResponseEntity<List<DiaryResponse>> getDiary(@RequestParam Long memberId) {
        List<Diary> diaries = diaryService.getDiaries(memberId);
        List<DiaryResponse> diaryResponses = diaries.stream()
                .map(DiaryResponse::of)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(diaryResponses);
    }

}
