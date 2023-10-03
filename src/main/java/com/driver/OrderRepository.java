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
        DeliveryPartnerDb.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void addOrderToDelPartnerInDb(String orderId, String partnerId) {
        if(DeliveryPartnerDb.containsKey(partnerId) && OrdersDb.containsKey(orderId)) {

            //adding to Deliver Partner
            ArrayList<String> currOrderList = DeliveryPartnerDb.get(partnerId).getAssignedOrdersList();
            currOrderList.add(orderId);

            DeliveryPartner currDelPartner = DeliveryPartnerDb.get(partnerId);
            currDelPartner.setAssignedOrdersList(currOrderList);

            DeliveryPartnerDb.put(partnerId,currDelPartner);

            //adding to orderId
            Order currOrder = OrdersDb.get(orderId);

            currOrder.setDriverAssigned(true);
            currOrder.setAssignedDeliveryPartner(partnerId);

            OrdersDb.put(orderId, currOrder);


        }
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

            DeliveryPartnerDb.remove(partnerId); //removing to Deliver Partner

            //removing to orderId
            for(String orderId:currOrderList) {
                Order currOrder = OrdersDb.get(orderId);
                currOrder.setDriverAssigned(false);
                currOrder.setAssignedDeliveryPartner("");
                OrdersDb.put(orderId, currOrder);
            }
        }
    }


    public void removeOrderIdFromDb(String orderId) {
        if(OrdersDb.containsKey(orderId)) {

            Order currOrder = OrdersDb.get(orderId);

            String partnerId = currOrder.getAssignedDeliveryPartner();

            if(DeliveryPartnerDb.containsKey(partnerId)) {
                DeliveryPartner currDelPartner = DeliveryPartnerDb.get(partnerId);

                ArrayList<String> currOrderList = DeliveryPartnerDb.get(partnerId).getAssignedOrdersList();

                currOrderList.remove(orderId);
            }
            OrdersDb.remove(orderId);
        }
    }
}
