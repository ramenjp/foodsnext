package com.dev_training.service;

import com.dev_training.entity.Todo;
import com.dev_training.entity.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * TODO登録サービス。
 */
@Service
@Transactional
public class TodoRegisterService {

    /** TODOリポジトリ */
    private TodoRepository todoRepository;

    @Autowired
    public TodoRegisterService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * TODO登録処理。
     */
    public void register(Todo todo) {
        todoRepository.save(todo);
    }

    /**
     * 日付の前後有効性チェック。
     *
     * @param startDate 開始日
     * @param endDate 終了日
     * @return true:無効 false:有効
     */
    public boolean isValidDate(String startDate, String endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) return false;
        return startDate.compareTo(endDate) > 0;
    }
}
