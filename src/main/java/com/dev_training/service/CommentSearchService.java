package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.entity.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * コメント登録サービス。
 */
@Service
public class CommentSearchService {

    /** コメントリポジトリ */
    private final CommentRepository commentRepository;
    /** アカウントリポジトリ */
    private final AccountRepository accountRepository;

    @Autowired
    public CommentSearchService(CommentRepository commentRepository,AccountRepository accountRepository) {
        this.commentRepository = commentRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * アカウント最新データ取得処理。
     *
     * @param id ID
     * @return アカウント
     */
    @Transactional(readOnly = true)
    public Account findAccountById(int id) {
        Account account = accountRepository.findAccountById(id);
        return account;
    }
}
