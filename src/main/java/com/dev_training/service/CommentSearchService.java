package com.dev_training.service;

import com.dev_training.extended_entity.ExtendedComment;
import com.dev_training.extended_entity.ExtendedCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * コメント検索サービス。
 */
@Service
public class CommentSearchService {

    /** コメントテーブル結合用リポジトリ */
    private final ExtendedCommentRepository extendedCommentRepository;

    @Autowired
    public CommentSearchService(ExtendedCommentRepository extendedCommentRepository) {
        this.extendedCommentRepository = extendedCommentRepository;
    }

    /**
     * コメント検索処理。
     *
     * @return 検索結果のリスト
     */
    @Transactional(readOnly = true)
    public List<ExtendedComment> findComment() {
        List<ExtendedComment> comments = extendedCommentRepository.findComment();
        for (ExtendedComment comment : comments) {
            //作成日時を取得し、substringで値を編集し、フォームに格納
            String createdTms = modifyTms(comment.getCreatedTms());
            comment.setCreatedTms(createdTms);
        }
        return comments;
    }

    /**
     * 作成日を「YYYY/MM/DD」の形式に編集。
     *
     * @param targetTms 作成日時
     * @return 編集後の作成日時
     */
    private String modifyTms(String targetTms) {
        return targetTms.substring(0, 4) + '/' + targetTms.substring(5, 7) + '/' + targetTms.substring(8, 16);
    }
}
