package com.bookmarker.api.service;

import com.bookmarker.api.domain.Bookmark;
import com.bookmarker.api.domain.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository repository;

    /* Constructor Injection (Autowired 안될 때는 이렇게, 목 객체를 못 넣음..?)
    public BookmarkService(BookmarkRepository repository) {
        this.repository = repository;
    }*/

    /* readOnly 속성 사용하면 성능면에서 유리*/
    @Transactional(readOnly = true)
    public List<Bookmark> getBookmarks() {
        return repository.findAll();
    }
    
}