package com.bookstore.bookstore.service;

import com.bookstore.bookstore.util.TestDataUtil;
import com.bookstore.es.ESIndexerService;
import com.bookstore.es.ESSearchService;
import com.bookstore.remote.service.RemoteServiceInvoker;
import com.bookstore.request.PostsSearchRequest;
import com.bookstore.response.MediaCoverageResponse;
import com.bookstore.service.BooksService;
import com.bookstore.service.MediaService;
import com.bookstore.service.impl.MediaServiceImpl;
import com.bookstore.vo.Book;
import com.bookstore.vo.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MediaServiceTest {

    @Mock
    private ESIndexerService esIndexerService;

    @Mock
    private ESSearchService esSearchService;

    @Mock
    private RemoteServiceInvoker remoteServiceInvoker;

    @Mock
    private BooksService booksService;

    @InjectMocks
    private MediaService mediaService = new MediaServiceImpl();

    @Test
    public void testIndexPosts() {
        List<Post> posts = Arrays.asList(getMediaPost());

        Mockito.when(remoteServiceInvoker.getPostsFromMediaService()).thenReturn(posts);
        Mockito.doNothing().when(esIndexerService).indexPosts(posts);

        mediaService.indexMediaPosts();

        Mockito.verify(esIndexerService, Mockito.times(1)).indexPosts(posts);
        Mockito.verify(remoteServiceInvoker, Mockito.times(1)).getPostsFromMediaService();

    }

    @Test
    public void testFindCoverage() {
        String isbn = TestDataUtil.ISBN;
        List<Post> posts = Arrays.asList(getMediaPost());
        List<String> postTitles = Arrays.asList(getMediaPost().getTitle());
        Book book = TestDataUtil.getBook();

        Mockito.when(esSearchService.searchPosts(Mockito.any(PostsSearchRequest.class))).thenReturn(posts);
        Mockito.when(booksService.findBookByIsbn(isbn)).thenReturn(book);

        MediaCoverageResponse result = mediaService.findCoverage(isbn);
        Assertions.assertEquals(postTitles.size(), result.getResults().size());
    }

    private Post getMediaPost() {
        Post post = new Post();

        post.setBody("post body");
        post.setTitle("post title");
        post.setUserId("post-user");
        post.setId("post-id");

        return post;
    }

}
