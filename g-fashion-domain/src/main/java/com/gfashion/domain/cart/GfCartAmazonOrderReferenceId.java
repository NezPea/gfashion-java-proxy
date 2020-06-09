package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartAmazonOrderReferenceId {

    private String id;
    private String amazonOrderReferenceId;
    private Integer quoteId;
    private String sandboxSimulationReference;
    private Boolean confirmed;
}
