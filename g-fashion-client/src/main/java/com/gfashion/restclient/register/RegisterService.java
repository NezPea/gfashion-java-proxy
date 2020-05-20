package com.gfashion.restclient.register;

import com.gfashion.data.CustomerEntity;
import com.gfashion.data.User;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName FeginRegisterService
 * @Description TODO
 * @Author zhangyuanbo
 * @Date 2020/05/18  23:17
 * @Version 1.0
 */
public interface RegisterService {
    String getAdminToken();
    String getAdminToken(User user);
    String getCustomerToken(User user);
    String registerCustomer(CustomerEntity customer) throws UnsupportedEncodingException;
}
