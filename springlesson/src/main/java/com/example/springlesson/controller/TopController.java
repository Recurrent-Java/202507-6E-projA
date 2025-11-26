package com.example.springlesson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// コントローラーからhtmlを呼び出す
@Controller
public class TopController {
  @GetMapping("/")
  public String index() {
    return "index";  // index.htmlを呼び出す
  }

}
