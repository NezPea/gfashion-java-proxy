package com.gfashion.api.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageIdRequest {
    @NotBlank(message = "Message id is required.")
    private String id;
}
