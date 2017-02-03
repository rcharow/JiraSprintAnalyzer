package com.analyzer.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rcharow on 2/2/17.
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "redirect:/a/";
    }

    @RequestMapping("/a/*")
    public String appRender() {
        return "/index.html";
    }

    @RequestMapping("/a/**")
    public String appRenderAny() {
        return "/index.html";
    }
}
