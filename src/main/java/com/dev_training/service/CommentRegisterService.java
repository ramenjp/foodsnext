package com.dev_training.service;

import com.dev_training.entity.Account;
import com.dev_training.entity.AccountRepository;
import com.dev_training.entity.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * アカウント登録サービス。
 */
@Service
public class CommentRegisterService {

    /** アカウントリポジトリ */
    private final CommentRepository commentRepository;

    @Autowired
    public CommentRegisterService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


}
