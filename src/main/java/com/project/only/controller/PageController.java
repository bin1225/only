package com.project.only.controller;

import com.project.only.domain.Page;
import com.project.only.domain.PageRequest;
import com.project.only.domain.PageResponse;
import com.project.only.domain.PageUpdateDTO;
import com.project.only.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @PostMapping("/only/page")
    public ResponseEntity<PageResponse> addPage(@RequestBody PageRequest pageRequest) {
        Page page = pageService.savePage(pageRequest);
        PageResponse pageResponse = PageResponse.of(page);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pageResponse);
    }

    @GetMapping("/only/page/{pageId}")
    public ResponseEntity<PageResponse> getPage(@PathVariable Long pageId) {
        Page page = pageService.findPageById(pageId);
        PageResponse pageResponse = PageResponse.of(page);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pageResponse);
    }

    @GetMapping("/only/{diaryId}/page")
    public ResponseEntity<List<PageResponse>> getAllPages(@PathVariable Long diaryId){
        List<PageResponse> pageResponseList = pageService.findPagesOfDiary(diaryId)
                .stream()
                .map(PageResponse::of)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(pageResponseList);
    }


    @PostMapping("/only/page/{pageId}")
    public ResponseEntity<PageResponse> updatePage(@PathVariable Long pageId, @RequestBody PageRequest pageRequest) {
        Page page = pageService.updatePage(PageUpdateDTO.builder()
                .pageId(pageId)
                .title(pageRequest.getTitle())
                .content(pageRequest.getContent())
                .build());
        PageResponse pageResponse = PageResponse.of(page);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pageResponse);
    }
}
