package com.driver;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository {

    HashMap<String, Order> OrdersDb = new HashMap<>();
    HashMap<String, DeliveryPartner> DeliveryPartnerDb = new HashMap<>();


    public OrderRepository() {//Constructor
    }

    public void addOrderToDb(Order order) {
        OrdersDb.put(order.getId(), order);
    }

    public void addDelvPartnerToDb(String partnerId) {
        DeliveryPartner newDeliveryPartner = new DeliveryPartner(partnerId);
        DeliveryPartnerDb.put(partnerId, newDeliveryPartner);
    }

    public void addOrderToDelPartnerInDb(String orderId, String partnerId) {
        if(OrdersDb.get(orderId).isDriverAssigned()) {
            return;
        }
        //if(DeliveryPartnerDb.containsKey(partnerId) && OrdersDb.containsKey(orderId)) {

        //adding order to Deliver Partner
        DeliveryPartner currDelPartner = DeliveryPartnerDb.get(partnerId);
        ArrayList<String> currOrderList = currDelPartner.getAssignedOrdersList();
        currOrderList.add(orderId);

        currDelPartner.setAssignedOrdersList(currOrderList);
        currDelPartner.setOrderAssigned(true);

        DeliveryPartnerDb.put(partnerId,currDelPartner);

        //adding partner to orderId
        Order currOrder = OrdersDb.get(orderId);
        currOrder.setDriverAssigned(true);
        currOrder.setAssignedDeliveryPartner(partnerId);

        OrdersDb.put(orderId, currOrder);
    }

    public Order getOrderFromDb(String orderId) {
        return OrdersDb.getOrDefault(orderId, null);
    }

    public DeliveryPartner getDelPartnerFromDb(String partnerId) {
        return DeliveryPartnerDb.getOrDefault(partnerId, null);
    }

    public List<String> getListOfAllOrdersFromDb() {
        List<String> OrdersList = new ArrayList<>();

        for(String currOrder: OrdersDb.keySet()) {
            OrdersList.add(currOrder);
        }
        return OrdersList;
    }

    public void removePartnerIdFromDb(String partnerId) {
        if(DeliveryPartnerDb.containsKey(partnerId)) {

            ArrayList<String> currOrderList = DeliveryPartnerDb.get(partnerId).getAssignedOrdersList();

            //removing to orderId
            for(String orderId:currOrderList) {
                Order currOrder = OrdersDb.get(orderId);
                currOrder.setDriverAssigned(false);
                currOrder.setAssignedDeliveryPartner("");
                OrdersDb.put(orderId, currOrder);
            }

            DeliveryPartnerDb.remove(partnerId); //removing to Deliver Partner
        }
    }


    public void removeOrderIdFromDb(String orderId) {
        if(OrdersDb.containsKey(orderId)) {

            Order currOrder = OrdersDb.get(orderId);

            if(currOrder.isDriverAssigned()) {
                String partnerId = currOrder.getAssignedDeliveryPartner();

                if(DeliveryPartnerDb.containsKey(partnerId)) {
                    DeliveryPartner currDelPartner = DeliveryPartnerDb.get(partnerId);
                    ArrayList<String> currOrderList = DeliveryPartnerDb.get(partnerId).getAssignedOrdersList();
                    currOrderList.remove(orderId);
                    currDelPartner.setAssignedOrdersList(currOrderList);
                    DeliveryPartnerDb.put(partnerId, currDelPartner);
                }
            }

            OrdersDb.remove(orderId);
        }
    }

    public Integer getCountOfUnassignedOrdersFromDB() {
        int UnassignedCount = 0;
        for(Order currOrder : OrdersDb.values()) {
            if(!currOrder.isDriverAssigned()) {
                UnassignedCount++;
            }
        }
        return UnassignedCount;
    }
}
