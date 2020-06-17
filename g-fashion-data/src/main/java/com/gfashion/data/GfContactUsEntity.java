package com.gfashion.data;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gfContactUs")
public class GfContactUsEntity {

    /**
     *  ID GF_CONTACT_US_ID
     */
    @Id
    @GenericGenerator(name = "contactGenerator", strategy = "uuid")
    @GeneratedValue(generator = "contactGenerator")
    @Column(name = "gfContactUsId")
    private String  gfContactUsId;
    /**
     *  用户ID GF_CUSTOMER_ID
     */
    @Column(name = "gfCustomerId")
    private String gfCustomerId ;
    /**
     * 订单ID  GF_ORDER_ID  spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
     */
    @Column(name =  "gfOrderId")
    private String  gfOrderId;

    /**
     * 客服Id serviceId
     */
    @Column(name = "serviceId")
    private String serviceId;

    /**
     * 语言种类
     */
    @Column(name = "language")
    private String language;

    /**
     * 咨询种类 TYPE
     */
    @Column(name = "type")
    private Integer type;
    /**
     *  标题 TITLE
     */
    @Column(name = "title")
    private String title;
    /**
     *  咨询内容  CONTENT_OF_INQUIRY
     */
    @Column(name = "contentOfInquiry")
    private String  contentOfInquiry;
    /**
     *   状态 STATUS  1新增数据/ 2客服处理中 /3 已解决（答复客户）/ 4客户处理中
     */
    @Column(name = "status")
    private Integer  status;
    /**
     * 生成时间。（第一次联络时间） CREATE_TIME
     */
    @Column(name = "createTime")
    private Long   createTime;
    /**
     * 更新时间 UPDATE_TIME
     */
    @Column(name = "updateTime")
     private Long updateTime;

    /**
     * 更新时间 UPDATE_TIME
     */
    @Column(name = "customFlushTime")
    private Long customFlushTime;

    /**
     * 用户昵称
     */
    @Column(name = "customName")
    private String customName;

    /**
     * 品牌编号
     */
    @Column(name = "brandId")
    private String brandId;
    /**
     * 品牌名
     */
    @Column(name = "brandName")
    private String brandName;

    /**
     * 读状态  1 未读  2 已读
     */
    @Column(name = "isRead")
    private Integer isRead;

    /**
     * 客服名称 不映射到数据库
     */
    @Transient
    private String serviceName;


}
