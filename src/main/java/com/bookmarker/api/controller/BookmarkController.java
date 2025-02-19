package com.bookmarker.api.controller;

import com.bookmarker.api.domain.Bookmark;
import com.bookmarker.api.dto.BookmarksDTO;
import com.bookmarker.api.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    // api/bookmarks?page=1
    // api/bookmarks?page=2&query=boot
    @GetMapping
    public BookmarksDTO getBookmarks(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "query", defaultValue = "") String query) {
//    public List<Bookmark> getBookmarks(@RequestParam(name = "page", defaultValue = "1")
//                                           Integer page) {

//        return bookmarkService.getBookmarks(page);

        /*
            Java 11 String 추가된 메서드
            isBlank() => isEmpty() + trim()
        * */
//        if(query == null || query.trim().isEmpty()) {
        if(query == null || query.isBlank()) {
            return bookmarkService.getBookmarks(page);
        }
        return bookmarkService.searchBookmarks(query, page);
    }
}