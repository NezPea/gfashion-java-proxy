package com.gfashion.restclient.magento;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerRegistration;
import org.springframework.stereotype.Component;

@Component
public class MagentoObjectConverter {

    public GfCustomerRegistration ConvertDtoToGfCustomerRegistration(MagentoCustomer dto){
        GfCustomerRegistration res = new GfCustomerRegistration();
        GfCustomer customer = new GfCustomer();
        customer.setEmail(dto.getEmail());
        customer.setFirstname(dto.getFirstname());
        customer.setLastname(dto.getLastname());
        customer.setId(dto.getId());
        res.setCustomer(customer);
        return res;
    }

    public MagentoCustomer ConvertGfCustomerRegistrationToDto(GfCustomerRegistration customerRegistration){
        MagentoCustomer dto = new MagentoCustomer();
        GfCustomer customer = customerRegistration.getCustomer();
        dto.setEmail(customer.getEmail());
        dto.setFirstname(customer.getFirstname());
        dto.setLastname(customer.getLastname());
        return dto;
    }
}
