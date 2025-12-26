package com.example.springlesson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivacyController {

  @GetMapping("/privacy")
  public String index() {
    // templatesフォルダからの相対パスを指定
    return "privacy/privacy";
  }
}