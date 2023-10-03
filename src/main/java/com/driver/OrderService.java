package com.driver;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    OrderRepository repoLayer = new OrderRepository();

    public OrderService() {
    }

    public void addAnOrder(Order order) {
        repoLayer.addOrderToDb(order);
    }

    public void addDelvPartner(String partnerId) {
        repoLayer.addDelvPartnerToDb(partnerId);
    }

    public void addOrderToDelPartner(String orderId, String partnerId) {
        repoLayer.addOrderToDelPartnerInDb(orderId, partnerId);
    }

    public Order getOrderByOrderId(String orderId) {
        return repoLayer.getOrderFromDb(orderId);
    }

    public DeliveryPartner getDelPartnerByPartnerId(String partnerId) {
        return repoLayer.getDelPartnerFromDb(partnerId);
    }

    public Integer getCountOfOrderByPartnerId(String partnerId) {
        if(getDelPartnerByPartnerId(partnerId)!=null) {
            ArrayList<String> OrderListofPartnerId = getDelPartnerByPartnerId(partnerId).getAssignedOrdersList();
            return OrderListofPartnerId.size();
        }
        return 0;
    }

    public List<String> getListOfOrdersByPartnerId(String partnerId) {
        if(getDelPartnerByPartnerId(partnerId)!=null) {
            return getDelPartnerByPartnerId(partnerId).getAssignedOrdersList();
        }
        return null;
    }

    public List<String> getListOfAllOrders() {
        return repoLayer.getListOfAllOrdersFromDb();
    }

    public Integer getCountOfUnassignedOrders() {
        List<String> allOrderList = getListOfAllOrders();
        int CountofUnassinged = 0;

        for(String CurrOrder:allOrderList) {
            if(!getOrderByOrderId(CurrOrder).isDriverAssigned()) {
                CountofUnassinged++;
            }
        }
        return CountofUnassinged;
    }

    public Integer getCountOfUndeliveredOrders(String time, String partnerId) {

        List<String> delPartnerOrderList = getListOfOrdersByPartnerId(partnerId);
        int CountOfUndelivered = 0;

        int HH = Integer.parseInt(time.substring(0, 2));
        int MM = Integer.parseInt(time.substring(time.length()-2));
        int currTime = ((HH*60)+MM);

        for(String currOrder:delPartnerOrderList) {
            if(getOrderByOrderId(currOrder).getDeliveryTime()>currTime) {
                CountOfUndelivered++;
            }
        }
        return CountOfUndelivered;
    }


    public String getLastDeliveryTime(String partnerId) {

        List<String> delPartnerOrderList = getListOfOrdersByPartnerId(partnerId);

        int MaxTime = Integer.MIN_VALUE;

        for(String currOrder:delPartnerOrderList) {
            MaxTime = Math.max(MaxTime, getOrderByOrderId(currOrder).getDeliveryTime());
        }
        if(MaxTime==Integer.MIN_VALUE) {
            MaxTime = 0;
        }
        int HH = MaxTime/60;
        int MM = MaxTime%60;

        return HH+":"+MM;
    }

    public void removePartnerId(String partnerId) {
        repoLayer.removePartnerIdFromDb(partnerId);
    }

    public void removeOrderId(String orderId) {
        repoLayer.removeOrderIdFromDb(orderId);
    }
}
