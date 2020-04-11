package com.bookstore.remote.service;


import com.bookstore.vo.Post;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class GetAllPostsCommand {

    private static Logger LOGGER = LoggerFactory.getLogger(GetAllPostsCommand.class);

    @Value("${media.service.host}")
    private String baseUrl;

    @Resource
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getAllPostsFallback", commandKey = "getAllPostsCommand",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            })
    public List<Post> getAllPosts() {

        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/posts").toUriString();
        ResponseEntity<Post[]> result = restTemplate.getForEntity(uri, Post[].class);
        return Arrays.asList(result.getBody());
    }

    public List<Post> getAllPostsFallback(Throwable commandException) {
        LOGGER.error("Error in getting posts from media service. {}",commandException.getMessage());
        return Collections.EMPTY_LIST;
    }
}
