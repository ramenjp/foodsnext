package com.dev_training.controller;

import com.dev_training.service.TodoDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

/**
 * TODO削除コントローラ。
 */
@Controller
@RequestMapping(value = "/todo/delete")
public class TodoDeleteController {

    /** TODO検索サービス */
    private final TodoDeleteService service;
    /** メッセージソース */
    private final MessageSource messageSource;

    @Autowired
    public TodoDeleteController(TodoDeleteService todoDeleteService, MessageSource messageSource) {
        this.service = todoDeleteService;
        this.messageSource = messageSource;
    }

    /**
     * TODO削除-確認画面表示。
     *
     * @param todoId 削除対象のTODOのID
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/confirm",  method = RequestMethod.POST)
    public String deleteConfirm(@RequestParam String todoId, Model model) {
        // IDが正しく渡ってこなければ、エラー表示。
        if (todoId == null || StringUtils.isEmpty(todoId)) {
            model.addAttribute("errorMsg", messageSource.getMessage("validation.invalid.screen,transition", null, Locale.JAPAN));
            return "common/commonError";
        }

        // TODOが存在しなければ、エラー表示。
        if (!service.isExistsById(Integer.parseInt(todoId))) {
            model.addAttribute("errorMsg", messageSource.getMessage("validation.incorrect.specification.todo", null, Locale.JAPAN));
            return "common/commonError";
        }
        model.addAttribute("todoId", todoId);
        return "todo/todoDeleteConfirmForm";
    }

    /**
     * TODO削除-完了画面表示。
     *
     * @param todoId 削除対象のTODOのID
     * @param model モデル
     * @return Path
     */
    @RequestMapping(value = "/do", method = RequestMethod.POST)
    public String unsubscribeComplete(@RequestParam String todoId, Model model) {
        // IDが正しく渡ってこなければ、エラー表示。
        if (todoId == null || StringUtils.isEmpty(todoId)) {
            model.addAttribute("errorMsg", messageSource.getMessage("validation.invalid.screen,transition", null, Locale.JAPAN));
            return "common/commonError";
        }

        // TODOが存在しなければ、エラー表示。
        if (!service.isExistsById(Integer.parseInt(todoId))) {
            model.addAttribute("errorMsg", messageSource.getMessage("validation.incorrect.specification.todo", null, Locale.JAPAN));
            return "common/commonError";
        }
        service.delete(Integer.parseInt(todoId));
        return "todo/todoDeleteCompleteForm";
    }

}
