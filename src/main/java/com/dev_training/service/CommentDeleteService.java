package com.dev_training.service;

import com.dev_training.entity.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * コメント削除サービス。
 */
@Service
public class CommentDeleteService {

    /** コメントリポジトリ */
    private final CommentRepository commentRepository;

    @Autowired
    public CommentDeleteService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * コメント削除処理。
     *
     * @param id　コメントのID
     */
    public void deleteById(int id){
        commentRepository.deleteById(id);
    }
}
