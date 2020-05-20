package com.gfashion.restclient.register;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName FeginService
 * @Description TODO
 * @Author zhangyuanbo
 * @Date 2020/05/18  23:15
 * @Version 1.0
 */
@FeignClient(name = "registerCustomer", url = "https://www.gfashion2020.tk/index.php/rest/V1")
public interface FeginRegisterService {
    @RequestMapping(method = RequestMethod.POST, value = "/integration/admin/token",
            headers = {"Content-Type=application/json"},consumes = MediaType.APPLICATION_JSON_VALUE)
    String getAdminToken(@RequestBody String user);

    @RequestMapping(method = RequestMethod.POST, value = "integration/customer/token",
            headers = {"Content-Type=application/json"},consumes = MediaType.APPLICATION_JSON_VALUE)
    String getCustomerToken(@RequestBody String user);

    @RequestMapping(method = RequestMethod.POST, value = "integration/customer/token",
            headers = {"Content-Type=application/json"},consumes = MediaType.APPLICATION_JSON_VALUE)
    String login(@RequestBody String user);

    @RequestMapping(method = RequestMethod.GET, value = "/customers/1",
            headers =    {"Authorization=Bearer ${adminToken}","Content-Type=application/json"},
//            headers =    {"Content-Type=application/json"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    String getCustomer();

    @RequestMapping(method = RequestMethod.POST, value = "/customers",
//            headers =    {"Authorization=Bearer 2c87mwajqm7y7dzgl89age8bhykvbxcd"
//                    ,"Content-Type=application/json"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    String registerCustomer(@RequestBody String customer);
}
