package com.dev_training.controller27;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * トップコントローラー
 */
@Controller
@RequestMapping("/top")
public class TopController {
    public String match(){return"/top/matching1";}

}
