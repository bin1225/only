package com.project.only.service;

import com.project.only.domain.Page;
import com.project.only.domain.PageRequest;
import com.project.only.domain.PageResponse;
import com.project.only.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;

    public PageResponse savePage(PageRequest pageRequest) {
        Page page = Page.builder()
                .title(pageRequest.getTitle())
                .content(pageRequest.getContent())
                .build();
        Page save = pageRepository.save(page);

        return PageResponse.builder()
                .id(save.getId())
                .title(save.getTitle())
                .content(save.getContent())
                .createDateTime(save.getCreateDateTime())
                .updateDateTime(save.getUpdateDateTime())
                .build();
    }


    public PageResponse findPageById(Long id) {
        Page save = pageRepository.find(id);

        return PageResponse.builder()
                .id(save.getId())
                .title(save.getTitle())
                .content(save.getContent())
                .createDateTime(save.getCreateDateTime())
                .updateDateTime(save.getUpdateDateTime())
                .build();
    }
}
