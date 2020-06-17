package com.gfashion.data;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "gfContactUsLog")
public class GfContactUsLogEntity {

    /**
     *  ID
     */
    @Column(name= "gfContactUsId")
    private String  gfContactUsId;

    /**
     *   AUTO INCREMENT
     */
    @Id
    @GenericGenerator(name = "contactLogGenerator", strategy = "uuid")
    @GeneratedValue(generator = "contactLogGenerator")
    @Column(name= "seq")
    private String seq;

    /**
     *  状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     *  1： 客服发给客户，2：客户发给客服 3：GF团队内部交互信息
     */
    @Column(name = "infoType")
    private Integer infoType;


    /**
     *  外部（客户）交互信息
     */
    @Column(name = "infoWithExternal")
    private String infoWithExternal;

    /**
     *  内部交互信息
     */
    @Column(name = "infoWithInternal")
    private String infoWithInternal;

    /**
     * 图片
     */
    @Column(name = "picture")
    private String picture;

    /**
     * 客服负责人
     */
    @Column(name = "csSpecialist")
    private String csSpecialist;

    /**
     * 客服负责人
     */
    @Column(name = "serviceId")
    private String serviceId;

    /**
     *  生成时间
     */
    @Column(name = "createTime")
    private Long createTime;

    /**
     *  排序时间
     */
    @Column(name = "orderTime")
    private Long orderTime;



}