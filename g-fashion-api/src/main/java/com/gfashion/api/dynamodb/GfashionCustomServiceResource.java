package com.gfashion.api.dynamodb;


import com.gfashion.data.GfContactUsEntity;
import com.gfashion.data.service.GfContactUsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionCustomServiceResource {

    private GfContactUsService gfContactUsService;






    /**
     *
     * 获取咨询状态
     */
    @GetMapping(value = "/service/getContactStatus", produces = "application/json;charset=utf-8")
    public ResponseEntity<Map> getContactStatus() {

        return ResponseEntity.status(HttpStatus.OK).body(gfContactUsService.getContactStatus());
    }

    /**
     *
     *获取咨询类型全部
     */
    @GetMapping(value = "/service/getContactType", produces = "application/json;charset=utf-8")
    public ResponseEntity<Map> getContactType() {

        return ResponseEntity.status(HttpStatus.OK).body(gfContactUsService.getContactType());
    }


    /**
     *
     * 手动修改 是否关闭
     */
    @PostMapping(value = "/service/updateContactStatus", produces = "application/json;charset=utf-8")
    public ResponseEntity updateContactStatus(@RequestParam String contactUsId, @RequestParam Integer status, @RequestParam String serviceId) {
        if(!("2".equals(status) || "3".equals(status) || "4".equals(status))){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        gfContactUsService.updateContactStatus(contactUsId, serviceId, status);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }

    /**
     *
     * 修改已读状态
     */
    @PostMapping(value = "/service/readStatus", produces = "application/json;charset=utf-8")
    public ResponseEntity readStatus(@RequestParam String contactUsId, @RequestParam String serviceId) {
        gfContactUsService.updateReadStatus(contactUsId);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }

    /**
     *
     * 获取咨询的记录
     */
    @PostMapping(value = "/service/getContactList", produces = "application/json;charset=utf-8")
    public ResponseEntity<List> getContactList(@RequestBody Map<String,Object> map ){
        List<GfContactUsEntity> resultList = gfContactUsService.getContactList( map);

        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    /**
     *
     *客服发送消息
     */
    @PostMapping(value = "/service/sendContactMessage", produces = "application/json;charset=utf-8")
    public ResponseEntity<Map<String, Object>> sendContactMessage(@RequestBody Map<String,Object> map ){


        Map<String, Object> resultJson = gfContactUsService.serviceSendMessage(map);
        return ResponseEntity.status(HttpStatus.OK).body(resultJson);
    }


    /**
     *
     *  获取当前的记录
     */
    @PostMapping(value = "/service/getLogList", produces = "application/json;charset=utf-8")
    public ResponseEntity<Map<String, Object>> getLogList(@RequestBody Map<String,Object> map){
        Map<String, Object> resultJson = gfContactUsService.cycleToService(map);
        return ResponseEntity.status(HttpStatus.OK).body(resultJson);
    }

    /**
     *
     *  模拟用户 测试用接口
     */
    @PostMapping(value = "/service/testCustom", produces = "application/json;charset=utf-8")
    public ResponseEntity<Map<String, Object>> testCustom(@RequestBody Map<String,Object> map ){
        Map<String, Object> resultJson = gfContactUsService.siteMailToContact(map);
        return ResponseEntity.status(HttpStatus.OK).body(resultJson);
    }

}
