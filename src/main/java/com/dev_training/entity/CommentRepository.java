package com.dev_training.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * コメントリポジトリ。
 */
public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {

    /**
     * コメントを作成日時の降順で検索する。
     *
     * @return コメント
     */
    @Query(value = "SELECT * FROM comment ORDER BY created_tms DESC", nativeQuery = true)
    List<Comment> findComment();
}