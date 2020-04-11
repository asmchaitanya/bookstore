package com.bookstore.service;

import com.bookstore.response.MediaCoverageResponse;

public interface MediaService {

    /**
     * Fetch all posts from media ad index them using elastic search.
     */
    void indexMediaPosts();


    /**
     * Finds all the posts which cover/mention provided book title.
     * @param isbn
     * @return MediaCoverageResponse
     */
    MediaCoverageResponse findCoverage(String isbn);
}
