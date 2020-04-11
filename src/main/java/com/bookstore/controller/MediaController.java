package com.bookstore.controller;

import com.bookstore.response.BaseResponse;
import com.bookstore.response.MediaCoverageResponse;
import com.bookstore.service.MediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Apis related to media posts about books.
 */
@RestController
@RequestMapping(value = "/api/v1/media")
public class MediaController {

    @Resource
    private MediaService mediaService;

    @RequestMapping(method = RequestMethod.POST, value = "/index")
    public ResponseEntity<BaseResponse> indexPosts() {
        mediaService.indexMediaPosts();
        return new ResponseEntity<BaseResponse>(new BaseResponse("Media posts indexing initiated."), HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/coverage")
    public MediaCoverageResponse findCoverage(@RequestParam String isbn) {
        return mediaService.findCoverage(isbn);
    }
}