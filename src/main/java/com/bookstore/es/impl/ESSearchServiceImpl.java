package com.bookstore.es.impl;

import com.bookstore.constants.ApplicationContants;
import com.bookstore.es.ESSearchService;
import com.bookstore.exception.StoreException;
import com.bookstore.request.BookSearchRequest;
import com.bookstore.request.PostsSearchRequest;
import com.bookstore.vo.Book;
import com.bookstore.vo.Post;
import com.google.gson.Gson;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ESSearchServiceImpl implements ESSearchService {

    private static Logger LOGGER = LoggerFactory.getLogger(ESSearchServiceImpl.class);

    @Resource
    private RestHighLevelClient esClient;

    @Resource
    private Gson gson;

    @Override
    public List<Book> searchBooks(BookSearchRequest bookSearchRequest) {
        SearchRequest request = new SearchRequest(ApplicationContants.ESINDEX_BOOK);
        request.allowPartialSearchResults(true);
        request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(getBookSearchQueryBuilder(bookSearchRequest));
        request.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = esClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("Error in searching books", e);
            throw new RuntimeException("Error in searching books.");
        }
        SearchHit[] hits = searchResponse.getHits().getHits();
        return Arrays.asList(hits).stream().map(hit -> gson.fromJson(hit.getSourceAsString(), Book.class)
        ).collect(Collectors.toList());
    }

    @Override
    public List<Post> searchPosts(PostsSearchRequest postsSearchRequest) {
        SearchRequest request = new SearchRequest(ApplicationContants.ESINDEX_MEDIA);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(postsSearchRequest.getTitle(), "title", "body")
                .type(MatchQuery.Type.PHRASE_PREFIX));
        request.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = esClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("Error in searching books", e);
            throw new StoreException("Error in searching books.");
        }
        SearchHit[] hits = searchResponse.getHits().getHits();
        return Arrays.asList(hits).stream().map(hit -> gson.fromJson(hit.getSourceAsString(), Post.class)
        ).collect(Collectors.toList());
    }

    private QueryBuilder getBookSearchQueryBuilder(BookSearchRequest bookSearchRequest) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (Strings.hasText(bookSearchRequest.getIsbn())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("isbn", bookSearchRequest.getIsbn()));
        }
        if (Strings.hasText(bookSearchRequest.getAuthor())) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("author", getWildCardExpression(bookSearchRequest.getAuthor())));
        }
        if (Strings.hasText(bookSearchRequest.getTitle())) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("title", getWildCardExpression(bookSearchRequest.getTitle())));
        }

        return boolQueryBuilder;
    }

    private String getWildCardExpression(String value) {
        StringBuilder valueBuilder = new StringBuilder("*");
        valueBuilder.append(value.toLowerCase());
        valueBuilder.append("*");

        return valueBuilder.toString();
    }
}
