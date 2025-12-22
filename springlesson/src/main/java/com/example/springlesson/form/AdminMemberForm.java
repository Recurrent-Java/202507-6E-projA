package com.example.springlesson.form;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AdminMemberForm {

    @NotNull(message = "会員IDを入力してください")
    private Integer memberId;
}