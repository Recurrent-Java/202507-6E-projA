package com.example.springlesson.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AdminMemberDeleteForm {
    
    @NotNull(message = "会員IDを入力してください")
    @Min(value = 1, message = "有効な会員IDを入力してください")
    private Long memberId;
}