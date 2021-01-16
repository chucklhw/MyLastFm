package com.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {

    @RequestMapping(value = "/error")
    public String index() {
        return "forward:" + getErrorPath();
    }

    @Override
    public String getErrorPath() {
        return "/LastFmWeb/index.html";
    }
}
