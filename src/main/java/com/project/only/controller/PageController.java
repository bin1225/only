package com.project.only.controller;

import com.project.only.domain.PageRequest;
import com.project.only.domain.PageResponse;
import com.project.only.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @PostMapping("/only/page")
    public ResponseEntity<PageResponse> addPage(@RequestBody PageRequest pageRequest) {
        final PageResponse pageResponse = pageService.savePage(pageRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pageResponse);
    }

    @GetMapping("/only/page/{pageId}")
    public ResponseEntity<PageResponse> getPage(@PathVariable Long pageId) {
        final PageResponse pageResponse = pageService.findPageById(pageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(pageResponse);
    }

}
