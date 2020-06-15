package com.gfashion.api.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message response class for the RestController response body.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private String id;

    private String action;

    private String status;

}
