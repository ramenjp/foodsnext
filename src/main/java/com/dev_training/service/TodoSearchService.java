package com.dev_training.service;

import com.dev_training.entity.Todo;
import com.dev_training.entity.TodoRepository;
import com.dev_training.form.TodoSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dev_training.service.TodoSpecifications.*;

/**
 * TODO検索サービス。
 */
@Service
@Transactional
public class TodoSearchService {

    /** TODOリポジトリ */
    TodoRepository todoRepository;

    @Autowired
    public TodoSearchService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * 検索。
     *
     * @param form 検索条件
     * @return 検索結果
     */
    @Transactional(readOnly = true)
    public List<Todo> findTodo(TodoSearchForm form) {
        return todoRepository.findAll(
                Specification
                        .where(titleContains(form.getTitle()))
                        .and(detailContains(form.getDetail()))
                        .and(remarksContains(form.getRemarks()))
                        .and(startDateContains(form.getStartDate()))
                        .and(endDateContains(form.getEndDate()))
                        .and(issuePersonIdContains(form.getIssuePersonId()))
                        .and(personInChargeIdContains(form.getPersonInChargeId()))
                        .and(statusContains(form.getSelectedStatus()))
                        .and(priorityContains(form.getSelectedPriority())));
    }

    /**
     * 検索（ページング）。
     *
     * @param form     検索条件
     * @param pageable ページャブル
     * @return 検索結果
     */
    @Transactional(readOnly = true)
    public Page<Todo> findTodo(TodoSearchForm form, Pageable pageable) {
        return todoRepository.findAll(
                Specification
                        .where(titleContains(form.getTitle()))
                        .and(detailContains(form.getDetail()))
                        .and(remarksContains(form.getRemarks()))
                        .and(startDateContains(form.getStartDate()))
                        .and(endDateContains(form.getEndDate()))
                        .and(issuePersonIdContains(form.getIssuePersonId()))
                        .and(personInChargeIdContains(form.getPersonInChargeId()))
                        .and(statusContains(form.getSelectedStatus()))
                        .and(priorityContains(form.getSelectedPriority()))
                , pageable);
    }
}
