package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.order.GfOrder;
import com.gfashion.domain.sales.*;
import com.gfashion.domain.sales.response.GfShipmentResp;
import com.gfashion.restclient.magento.order.MagentoOrderItem;
import com.gfashion.restclient.magento.order.MagentoOrderItemOrderId;
import com.gfashion.restclient.magento.sales.*;
import com.gfashion.restclient.magento.sales.response.MagentoShipmentResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface GfMagentoOrderShipmentConverter {
    //--order

    @Mapping(source = "items", target = "order_id", qualifiedByName = "refactorMagentoOrderItem")
    GfOrder from(MagentoOrderItem orderItem);

    List<GfOrder> from(List<MagentoOrderItem> orderResp);

    @Named("refactorMagentoOrderItem")
    static String refactorMagentoOrderItem(List<MagentoOrderItemOrderId> items) {
        if (items != null && items.size() > 0) {
            if (items.get(0) != null) {
                return items.get(0).getOrder_id();
            }
        }
        return null;
    }

    //--order

    //--shipment
    MagentoShipment convertGfShipmentToMagentoShipment(GfShipment gfShipment);

    MagentoShipmentItem convertGfShipmentItemToMagentoShipmentItem(GfShipmentItem gfShipmentItem);

    MagentoShipmentTrack convertGfShipmentTrackToMagentoShipmentTrack(GfShipmentTrack gfShipmentTrack);

    MagentoShipmentComment convertGfShipmentCommentToMagentoShipmentComment(GfShipmentComment gfShipmentComment);

    MagentoShipOrder convertGfShipOrderToMagentoShipOrder(GfShipOrder gfShipOrder);

    MagentoShipmentItemCreation convertGfShipmentItemCreationToMagentoShipmentItemCreation(GfShipmentItemCreation gfShipmentItemCreation);

    MagentoShipmentCommentCreation convertGfGfShipmentCommentCreationToMagentoShipmentCommentCreation(GfShipmentCommentCreation gfShipmentCommentCreation);

    MagentoShipmentTrackCreation convertGfShipmentTrackCreationToMagentoShipmentTrackCreation(GfShipmentTrackCreation gfShipmentTrackCreation);

    MagentoShipmentPackageCreation convertGfShipmentPackageCreationToMagentoShipmentPackageCreation(GfShipmentPackageCreation gfShipmentPackageCreation);

    GfShipment convertMagentoShipmentToGfShipment(MagentoShipment magentoShipment);

    GfShipmentItem convertMagentoShipmentItemToGfShipmentItem(MagentoShipmentItem magentoShipmentItem);

    GfShipmentTrack convertMagentoShipmentTrackToGfShipmentTrack(MagentoShipmentTrack magentoShipmentTrack);

    GfShipmentComment convertMagentoShipmentCommentToGfShipmentComment(MagentoShipmentComment magentoShipmentComment);

    GfShipmentResp convertMagentoShipmentRespToGfShipmentResp(MagentoShipmentResp magentoShipmentResp);
    //--shipment
}
