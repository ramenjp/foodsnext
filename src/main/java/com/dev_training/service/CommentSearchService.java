package com.dev_training.service;

import com.dev_training.entity.AccountRepository;
import com.dev_training.entity.Comment;
import com.dev_training.entity.CommentRepository;
import com.dev_training.form.CommentSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * コメント検索サービス。
 */
@Service
public class CommentSearchService {

    /** コメントリポジトリ */
    private final CommentRepository commentRepository;
    /** アカウントリポジトリ */
    private final AccountRepository accountRepository;

    @Autowired
    public CommentSearchService(CommentRepository commentRepository, AccountRepository accountRepository) {
        this.commentRepository = commentRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * アカウントIDに紐づくアカウント名を取得。
     *
     * @param id ID
     * @return アカウント
     */
    @Transactional(readOnly = true)
    public String findAccountName(int id) {
        return accountRepository.findAccountNameById(id);
    }

    /**
     * 作成日時を取得し、substringで値を編集。
     *
     * @param targetTms 作成日時
     * @return 編集後の作成日時
     */
    private String modifyTms(String targetTms) {
        return targetTms.substring(0, 4) + '/' + targetTms.substring(5, 7) + '/' + targetTms.substring(8, 16);
    }

    /**
     * コメント検索フォーム作成処理。
     *
     * @return コメント検索フォームのリスト
     */
    @Transactional(readOnly = true)
    public List<CommentSearchForm> findComment() {
        List<Comment> comments = commentRepository.findComment();
        List<CommentSearchForm> list = new ArrayList<CommentSearchForm>();
        for (Comment comment : comments) {
            //アカウントIDに紐づくアカウント名を取得し、フォームに格納
            String accountName = findAccountName(comment.getAccountId());
            //作成日時を取得し、substringで値を編集し、フォームに格納
            String createdTms = modifyTms(comment.getCreatedTms());
            //コメント検索フォーム作成
            CommentSearchForm form = new CommentSearchForm();
            form.setId(comment.getId());
            form.setAccountId(comment.getAccountId());
            form.setName(accountName);
            form.setComment(comment.getComment());
            form.setCreatedTms(createdTms);
            //コメント検索フォームをリストに格納
            list.add(form);
        }
        return list;
    }
}
