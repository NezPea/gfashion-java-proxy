package com.gfashion.api;

import com.gfashion.data.CustomerEntity;
import com.gfashion.data.User;
import com.gfashion.restclient.register.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName GfashionRegister
 * @Description TODO
 * @Author IT Miles Chuang
 * @Date 2020/05/18  23:04
 * @Version 1.0
 */
@RestController
@RequestMapping(path = "/gfashion", produces = "application/json")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionRegister {

    @Autowired
    RegisterService registerService;

    @GetMapping("/getAdminToken")
    public String getAdminToken() {
        String token = registerService.getAdminToken();
        System.out.println(token);
        return token;
    }

    @GetMapping("/getCustomerToken/{userName}/{password}")
    public String getCustomerToken(@PathVariable(name = "userName") String userName, @PathVariable(name="password") String password) {
        User customerUser = new User(userName, password);
        String token = registerService.getCustomerToken(customerUser);
        System.out.println(token);
        return token;
    }

    @GetMapping("/login")
    public String login(@RequestBody User user) {
        String token = registerService.login(user);
        System.out.println(token);
        return token;
    }

    @PostMapping("/customer")
    public String registerCustomer(@RequestBody CustomerEntity customer) throws UnsupportedEncodingException {
        String response=registerService.registerCustomer(customer);
        return response;
    }
}
