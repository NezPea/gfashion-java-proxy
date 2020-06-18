package com.gfashion.domain.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfShipOrder implements Serializable {

    @NotEmpty
    private List<GfShipmentItemCreation> items;

    private Boolean notify;

    private Boolean appendComment;

    private GfShipmentCommentCreation comment;

    private List<GfShipmentTrackCreation> tracks;

    private List<GfShipmentPackageCreation> packages;

    private GfShipmentCreationArguments arguments;

}
