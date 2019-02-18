package com.dev_training.controller;

import com.dev_training.service.CommentDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * コメント削除コントローラ。
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentDeleteController {

    /*** コメント削除サービス */
    private final CommentDeleteService service;

    @Autowired
    public CommentDeleteController(CommentDeleteService commentDeleteService) {
        this.service = commentDeleteService;
    }

    /**
     * コメント削除処理。
     *
     * @param id　コメントのID
     */
    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.deleteById(id);
    }
}

