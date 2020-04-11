package com.bookstore.service.impl;

import com.bookstore.es.ESIndexerService;
import com.bookstore.es.ESSearchService;
import com.bookstore.exception.StoreException;
import com.bookstore.remote.service.RemoteServiceInvoker;
import com.bookstore.request.PostsSearchRequest;
import com.bookstore.response.MediaCoverageResponse;
import com.bookstore.service.BooksService;
import com.bookstore.service.MediaService;
import com.bookstore.vo.Book;
import com.bookstore.vo.Post;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MediaServiceImpl implements MediaService {

    @Resource
    private RemoteServiceInvoker remoteServiceInvoker;

    @Resource
    private ESIndexerService esIndexerService;

    @Resource
    private ESSearchService esSearchService;

    @Resource
    private BooksService booksService;

    @Override
    public void indexMediaPosts() {
        List<Post> posts = remoteServiceInvoker.getPostsFromMediaService();
        if (CollectionUtils.isEmpty(posts)) {
            throw new StoreException("Error in getting posts.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        esIndexerService.indexPosts(posts);
    }

    @Override
    public MediaCoverageResponse findCoverage(String isbn) {

        Book book = booksService.findBookByIsbn(isbn);
        if (null == book) {
            throw new StoreException("No books with given isbn",HttpStatus.NOT_FOUND);
        }

        PostsSearchRequest searchRequest = new PostsSearchRequest();
        searchRequest.setTitle(book.getTitle());

        List<Post> searchResults = esSearchService.searchPosts(searchRequest);

        return new MediaCoverageResponse(searchResults.stream().map(Post::getTitle).collect(Collectors.toList()));
    }
}
