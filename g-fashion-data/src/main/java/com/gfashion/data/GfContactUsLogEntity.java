package com.gfashion.data;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Set;

/**
 * 客服日志表
 *
 * @author ：勉强生
 * @description：TODO
 * @date ：2020/6/7 15:59
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "gfContactUsLog")
//@Document(indexName="gfContactUsLog")
public class GfContactUsLogEntity {

    @DynamoDBHashKey(attributeName = "gfContactUsId")
    private String gfContactUsId; //ID

    @DynamoDBRangeKey(attributeName = "seq")
    private String seq; //流水号 AUTO INCREMENT

    @DynamoDBAttribute(attributeName = "status")
    private Integer status; //状态 1新增数据/ 2客服处理中 /3 已解决（答复客户）/ 4客户处理中

    @DynamoDBAttribute(attributeName = "infoType")
    private Integer infoType; //消息类型 1： 客服发给客户，2：客户发给客服 3：GF团队内部交互信息

    @DynamoDBAttribute(attributeName = "infoWithExternal")
    private String infoWithExternal; //外部（客户）交互信息

    @DynamoDBAttribute(attributeName = "infoWithInternal")
    private String infoWithInternal; //内部交互信息

    @DynamoDBAttribute(attributeName = "picture")
    private Set<String> picture; //图片

    @DynamoDBAttribute(attributeName = "csSpecialist")
    private String csSpecialist; //客服负责人

    @DynamoDBAttribute(attributeName = "createTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime; //生成时间


}
