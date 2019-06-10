package com.dev_training.controller27;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * トップコントローラー
 */
@Controller
@RequestMapping("/top")
public class TopController {

    /**
     * トップ画面
     * @param
     * @return
     */
    @RequestMapping(value = "/matching1", params = "match", method = RequestMethod.POST)
    public String match(){return"matching_count";}

    @RequestMapping(value = "/history", params = "historyCheck", method = RequestMethod.POST)
    public String historyCheck(){ return "historyCheck";}

    @RequestMapping(value = "/setting", params = "accountUpdate", method = RequestMethod.POST)
    public String accountUpdate(){ return "accountUpdateForm";}


}
