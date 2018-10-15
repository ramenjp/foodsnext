package com.dev_training.service;

import com.dev_training.entity.Todo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * TODO検索条件。
 */
class TodoSpecifications {

    /** title:like */
    static Specification<Todo> titleContains(String title) {
        return StringUtils.isEmpty(title) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("title"), "%" + title + "%");
            }
        };
    }

    /** detail:like */
    static Specification<Todo> detailContains(String detail) {
        return StringUtils.isEmpty(detail) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("detail"), "%" + detail + "%");
            }
        };
    }

    /** remarks:like */
    static Specification<Todo> remarksContains(String remarks) {
        return StringUtils.isEmpty(remarks) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("remarks"), "%" + remarks + "%");
            }
        };
    }

    /** startDate:greaterThanOrEqualTo */
    static Specification<Todo> startDateContains(String startDate) {
        return StringUtils.isEmpty(startDate) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThanOrEqualTo(root.get("startDate"), startDate);
            }
        };
    }

    /** endDate:lessThanOrEqualTo */
    static Specification<Todo> endDateContains(String endDate) {
        return StringUtils.isEmpty(endDate) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.lessThanOrEqualTo(root.get("endDate"), endDate);
            }
        };
    }

    /** issuePersonId:equal */
    static Specification<Todo> issuePersonIdContains(String issuePersonId) {
        return StringUtils.isEmpty(issuePersonId) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("issuePersonId"), issuePersonId);
            }
        };
    }

    /** personInChargeId:equal */
    static Specification<Todo> personInChargeIdContains(String personInChargeId) {
        return StringUtils.isEmpty(personInChargeId) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("personInChargeId"), Integer.valueOf(personInChargeId));
            }
        };
    }

    /** status:equal */
    static Specification<Todo> statusContains(String status) {
        return StringUtils.isEmpty(status) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("status"), status);
            }
        };
    }

    /** priority:equal */
    static Specification<Todo> priorityContains(String priority) {
        return StringUtils.isEmpty(priority) ? null : new Specification<Todo>() {
            @Override
            public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("priority"), priority);
            }
        };
    }

}
