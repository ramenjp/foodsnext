package com.dev_training.controller;

import com.dev_training.form.CommentSearchForm;
import com.dev_training.service.CommentSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * コメント検索コントローラ。
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentSearchController {

    /*** HTTPセッション */
    private final CommentSearchService service;

    @Autowired
    public CommentSearchController(CommentSearchService commentSearchService) {
        this.service = commentSearchService;

    }

    @RequestMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<CommentSearchForm> search() {
        List<CommentSearchForm> list = service.findComment();
        return list;
    }
}
