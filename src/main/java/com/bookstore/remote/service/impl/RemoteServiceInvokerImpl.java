package com.bookstore.remote.service.impl;

import com.bookstore.remote.service.GetAllPostsCommand;
import com.bookstore.remote.service.RemoteServiceInvoker;
import com.bookstore.vo.Post;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RemoteServiceInvokerImpl implements RemoteServiceInvoker {

    @Resource
    private GetAllPostsCommand getAllPostsCommand;

    @Override
    public List<Post> getPostsFromMediaService() {
        return getAllPostsCommand.getAllPosts();
    }
}
