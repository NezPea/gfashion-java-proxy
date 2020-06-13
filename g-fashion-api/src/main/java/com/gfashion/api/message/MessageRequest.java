package com.gfashion.api.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private HashMap<String, String> title;

    @NotNull
    private HashMap<String, String> content;

    private String picture = "";

    private String contract = "";

    private String data = "";

}
