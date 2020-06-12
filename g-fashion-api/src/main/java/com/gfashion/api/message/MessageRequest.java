package com.gfashion.api.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Message request class sent from the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    @NotBlank(message = "Receiver field is required.")
    private String receiver;

    @NotBlank(message = "Message title is required.")
    private String title;

    @NotBlank(message = "Message content is required.")
    private String content = "";

    private String picture = "";

    private String contract = "";

    private String data = "";

}
