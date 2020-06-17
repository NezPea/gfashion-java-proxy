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
@Table(name = "gf_contact_us")
public class GfContactUsEntity {

    /**
     *  ID GF_CONTACT_US_ID
     */
    @Id
    @GenericGenerator(name = "contactGenerator", strategy = "uuid")
    @GeneratedValue(generator = "contactGenerator")
    @Column(name = "gf_contact_us_id")
    private String  gfContactUsId;
    /**
     *  用户ID GF_CUSTOMER_ID
     */
    @Column(name = "gf_customer_id")
    private String gfCustomerId ;
    /**
     * 订单ID  GF_ORDER_ID  spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
     */
    @Column(name =  "gf_order_id")
    private String  gfOrderId;

    /**
     * 客服Id serviceId
     */
    @Column(name = "service_id")
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
    @Column(name = "content_of_inquiry")
    private String  contentOfInquiry;
    /**
     *   状态 STATUS  1新增数据/ 2客服处理中 /3 已解决（答复客户）/ 4客户处理中
     */
    @Column(name = "status")
    private Integer  status;
    /**
     * 生成时间。（第一次联络时间） CREATE_TIME
     */
    @Column(name = "create_time")
    private Long   createTime;
    /**
     * 更新时间 UPDATE_TIME
     */
    @Column(name = "update_time")
     private Long updateTime;

    /**
     * 更新时间 UPDATE_TIME
     */
    @Column(name = "custom_flush_time")
    private Long customFlushTime;

    /**
     * 用户昵称
     */
    @Column(name = "custom_name")
    private String customName;

    /**
     * 品牌编号
     */
    @Column(name = "brand_id")
    private String brandId;
    /**
     * 品牌名
     */
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 读状态  1 未读  2 已读
     */
    @Column(name = "is_read")
    private Integer isRead;

    /**
     * 客服名称 不映射到数据库
     */
    @Transient
    private String serviceName;


}
