package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipOrder implements Serializable {

    @NotEmpty
    private List<MagentoShipmentItemCreation> items;

    private boolean notify;

    private boolean appendComment;

    private MagentoShipmentCommentCreation comment;

    private List<MagentoShipmentTrackCreation> tracks;

    private List<MagentoShipmentPackageCreation> packages;

    private MagentoShipmentCreationArguments arguments;

}
