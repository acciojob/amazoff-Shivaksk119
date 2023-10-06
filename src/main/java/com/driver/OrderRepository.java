package com.driver;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository {

    HashMap<String, Order> OrdersDb = new HashMap<>();
    HashMap<String, DeliveryPartner> DeliveryPartnerDb = new HashMap<>();
    HashMap<String, String> OrderToPartnerPair = new HashMap<>();
    HashMap<String, ArrayList<String>> PartnerToOrderPair = new HashMap<>();

    public OrderRepository() {//Constructor
    }

    public void addOrderToDb(Order order) {
        OrdersDb.put(order.getId(), order);
    }

    public void addDelvPartnerToDb(String partnerId) {
        DeliveryPartner newDeliveryPartner = new DeliveryPartner(partnerId);
        DeliveryPartnerDb.put(newDeliveryPartner.getId(), newDeliveryPartner);
    }

    public void addOrderToDelPartnerInDb(String orderId, String partnerId) {
        if(OrderToPartnerPair.containsKey(orderId)) {
            return;
        }
        ArrayList<String> OrderList = PartnerToOrderPair.getOrDefault(partnerId, new ArrayList<>());
        OrderList.add(orderId);

        PartnerToOrderPair.put(partnerId, OrderList);
        OrderToPartnerPair.put(orderId, partnerId);
        DeliveryPartner CurrpPartner = DeliveryPartnerDb.get(partnerId);
        CurrpPartner.setNumberOfOrders(OrderList.size());
        DeliveryPartnerDb.put(partnerId, CurrpPartner);
    }

    public Order getOrderFromDb(String orderId) {
        return OrdersDb.getOrDefault(orderId, null);
    }

    public DeliveryPartner getDelPartnerFromDb(String partnerId) {
        if(DeliveryPartnerDb.containsKey(partnerId)) {
            return DeliveryPartnerDb.get(partnerId);
        }
        return null;
    }

    public List<String> getListOfAllOrdersFromDb() {
        List<String> OrdersList = new ArrayList<>();

        for(String currOrder: OrdersDb.keySet()) {
            OrdersList.add(currOrder);
        }
        return OrdersList;
    }

    public void removePartnerIdFromDb(String partnerId) {
        DeliveryPartnerDb.remove(partnerId);
        ArrayList<String> OrderList = PartnerToOrderPair.getOrDefault(partnerId,new ArrayList<>());
        for(String Order: OrderList) {
            OrderToPartnerPair.remove(Order);
        }
        PartnerToOrderPair.remove(partnerId);
    }


    public void removeOrderIdFromDb(String orderId) {
        OrdersDb.remove(orderId);
        String partnerId =OrderToPartnerPair.get(orderId);
        OrderToPartnerPair.remove(orderId);
        ArrayList<String> OrderList =PartnerToOrderPair.get(partnerId);
        for(String Order: OrderList) {
            OrderList.remove(Order);
        }
        PartnerToOrderPair.put(partnerId,OrderList);
    }

    public Integer getCountOfUnassignedOrdersFromDB() {
        return OrdersDb.size()-OrderToPartnerPair.size();
    }

    public Integer getCountOfOrderByPartnerIdFromDB(String partnerId) {
        return PartnerToOrderPair.get(partnerId).size();
    }

    public List<String> getListOfOrdersByPartnerIdFromDB(String partnerId) {
        List<String> OrderList = new ArrayList<>();
        OrderList = PartnerToOrderPair.get(partnerId);
        return OrderList;
    }
}