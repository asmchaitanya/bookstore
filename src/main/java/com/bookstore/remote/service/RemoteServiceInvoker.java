package com.bookstore.remote.service;

import com.bookstore.vo.Post;

import java.util.List;

public interface RemoteServiceInvoker {

    List<Post> getPostsFromMediaService();
}
