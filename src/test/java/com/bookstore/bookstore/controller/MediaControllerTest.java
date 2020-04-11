package com.bookstore.bookstore.controller;

import com.bookstore.controller.MediaController;
import com.bookstore.exception.StoreException;
import com.bookstore.response.BaseResponse;
import com.bookstore.response.MediaCoverageResponse;
import com.bookstore.service.MediaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class MediaControllerTest {

    @Mock
    private MediaService mediaService;

    @InjectMocks
    private MediaController mediaController;

    @Test
    public void testFindCoverage() {
        String title = "test";
        MediaCoverageResponse coverageResponse = new MediaCoverageResponse(Arrays.asList("post1", "post2"));

        Mockito.when(mediaService.findCoverage(title)).thenReturn(coverageResponse);

        MediaCoverageResponse result = mediaController.findCoverage(title);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(coverageResponse.getResults().size(), result.getResults().size());
    }

    @Test
    public void testFindCoverageForException() {
        String title = "test";
        MediaCoverageResponse coverageResponse = new MediaCoverageResponse(Arrays.asList("post1", "post2"));

        Mockito.when(mediaService.findCoverage(title)).thenThrow(new StoreException("Error in searching posts"));

        Assertions.assertThrows(StoreException.class, () -> mediaController.findCoverage(title));

    }

    @Test
    public void testIndexPosts(){

        Mockito.doNothing().when(mediaService).indexMediaPosts();

        ResponseEntity<BaseResponse> result = mediaController.indexPosts();
        Mockito.verify(mediaService,Mockito.times(1)).indexMediaPosts();
        Assertions.assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());

    }
}
