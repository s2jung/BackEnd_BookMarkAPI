package com.bookmarker.api.service;

import com.bookmarker.api.domain.Bookmark;
import com.bookmarker.api.domain.BookmarkRepository;
import com.bookmarker.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository repository;
    private final BookmarkMapper mapper;

    /* Constructor Injection (Autowired 안될 때는 이렇게, 목 객체를 못 넣음..?)
    public BookmarkService(BookmarkRepository repository) {
        this.repository = repository;
    }*/

    /* readOnly 속성 사용하면 성능면에서 유리*/
    @Transactional(readOnly = true)
    public BookmarksDTO getBookmarks(Integer page) {
//    public List<Bookmark> getBookmarks(Integer page) {

        // JPA 의 페이지번호가 0부터 시작하기 때문에
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "id");

        // Page<BookMark> => Page<BookmarkDTO>
//        Page<BookmarkDTO> bookmarkPage = repository.findAll(pageable) //Page<Bookmark>// ctrl + alt + v (여기서 오류 났었음)
//                // map(Function) Function 의 추상메서드 R apply(T t)
////                .map(bookmark -> mapper.toDTO(bookmark));
//                // Method Reference 람다식
//                .map(mapper::toDTO);

        // Custom Query method 호출
        Page<BookmarkDTO> bookmarkPage = repository.findBookmarks(pageable);

        return new BookmarksDTO(bookmarkPage);

//        return repository.findAll(pageable).getContent();
//        return repository.findAll(pageable) 의 return type 이 Page<T> 가 돼서 오류메시지가 뜸. -> getContent

//        return repository.findAll();


    }

    @Transactional(readOnly = true)
    public BookmarksDTO<?> searchBookmarks(String query, Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
//        Page<BookmarkDTO> bookmarkPage = repository.searchBookmarks(query, pageable);
//        Page<BookmarkDTO> bookmarkPage = repository.findByTitleContainingIgnoreCase(query, pageable);
        Page<BookmarkVM> bookmarkPage = repository.findByTitleContainingIgnoreCase(query, pageable);
        return new BookmarksDTO<>(bookmarkPage);
    }

    public BookmarkDTO createBookmark(CreateBookmarkRequest request) {
        Bookmark bookmark = new Bookmark(request.getTitle(), request.getUrl(), Instant.now());
        Bookmark savedBookmark = repository.save(bookmark);
        return mapper.toDTO(savedBookmark);
    }
}