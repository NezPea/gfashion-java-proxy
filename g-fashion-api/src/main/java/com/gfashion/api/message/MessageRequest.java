package com.gfashion.api.message;

import com.gfashion.api.message.validator.MessageConstrain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;

/**
 * Message request class sent from the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    @NotBlank(message = "Receiver field is required.")
    private String receiver;

    @MessageConstrain(message = "English version title is required.")
    private HashMap<String, String> title;

    @MessageConstrain(message = "English version content is required.")
    private HashMap<String, String> content;

    private String picture = "";

    private String contract = "";

    private String data = "";

}
