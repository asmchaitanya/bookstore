package com.bookstore.bookstore.remote.service;

import com.bookstore.bookstore.util.TestDataUtil;
import com.bookstore.remote.service.GetAllPostsCommand;
import com.bookstore.vo.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetAllPostCommandTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GetAllPostsCommand getAllPostsCommand;

    @Test
    public void testGetAllPosts() {
        Post[] posts = new Post[]{TestDataUtil.getPost()};
        String uri = "https://jsonplaceholder.typicode.com/posts";

        ReflectionTestUtils.setField(getAllPostsCommand, "baseUrl", "https://jsonplaceholder.typicode.com");

        Mockito.when(restTemplate.getForEntity(uri, Post[].class)).thenReturn(new ResponseEntity<Post[]>(posts, HttpStatus.OK));

        List<Post> result = getAllPostsCommand.getAllPosts();

        Assertions.assertEquals(posts.length, result.size());
    }

}
