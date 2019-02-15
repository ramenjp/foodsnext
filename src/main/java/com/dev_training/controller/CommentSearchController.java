package com.dev_training.controller;

import com.dev_training.entity.Comment;
import com.dev_training.form.CommentRegisterForm;
import com.dev_training.service.CommentRegisterService;
import com.dev_training.service.CommentSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * コメント検索コントローラ。
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentSearchController {

    /** HTTPセッション */
    private final CommentSearchService service;

    @Autowired
    public CommentSearchController(CommentSearchService commentSearchService) {
        this.service = commentSearchService;

    }

//    @ResponseBody
//    @RequestMapping(path = "search", method = RequestMethod.GET)
//    public List<Comment> search() {
////        List<Comment> comments = service.findAccountById()
//
////    }
}
