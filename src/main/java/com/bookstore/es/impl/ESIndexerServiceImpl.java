package com.bookstore.es.impl;

import com.bookstore.constants.ApplicationContants;
import com.bookstore.es.ESIndexerService;
import com.bookstore.exception.StoreException;
import com.bookstore.vo.Book;
import com.bookstore.vo.Post;
import com.google.gson.Gson;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Component
public class ESIndexerServiceImpl implements ESIndexerService {

    private static Logger LOGGER = LoggerFactory.getLogger(ESIndexerServiceImpl.class);

    @Resource
    private RestHighLevelClient esClient;

    @Resource
    private Gson gson;

    @Override
    public void indexBooks(Book book) {
        IndexRequest request = new IndexRequest(ApplicationContants.ESINDEX_BOOK);
        request.source(gson.toJson(book), XContentType.JSON);
        request.id(book.getIsbn());
        try {
            esClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("Error in indexing books", e);
            throw new StoreException("Error in indexing books.");
        }
    }

    @Override
    public void indexPosts(List<Post> posts) {

        posts.forEach(post -> {
            IndexRequest request = new IndexRequest(ApplicationContants.ESINDEX_MEDIA);
            request.source(gson.toJson(post), XContentType.JSON);
            request.id(post.getId());
            try {
                esClient.index(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                LOGGER.error("Error in indexing media posts", e);
                throw new StoreException("Error in indexing media posts.");
            }
        });
    }
}
