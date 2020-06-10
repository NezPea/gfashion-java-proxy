package com.gfashion.api.log.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogEntity {
    private String id;
    private String operationDate;
    private String requestType; //HTTP_METHOD
    private String operationUrl;
    private String operationClass;
    private String magentoProductId;
    private String requestRawInfo; //request.getinputStream
    private String requestObjectInfo; //joinPoint.getArgs
    private String responseInfo;
    private String exceptionInfo;

    //for custom tag.
    private String operationType;
    private String operationEvent;
}
