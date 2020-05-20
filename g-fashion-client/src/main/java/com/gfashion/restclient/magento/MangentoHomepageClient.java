package com.gfashion.restclient.magento;

import com.gfashion.domain.homepage.CustomizedHomepage;
import com.gfashion.restclient.RestClient;
import com.gfashion.restclient.magento.exception.CustomerNotFoundException;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MangentoHomepageClient {
    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public CustomizedHomepage getCustomizedHomepage(Integer customerId) throws CustomerNotFoundException {

        return new CustomizedHomepage();
    }
}
