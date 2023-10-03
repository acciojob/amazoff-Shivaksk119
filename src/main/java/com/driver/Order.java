package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
    private boolean isDriverAssigned;
    private String AssignedDeliveryPartner;

    public Order(String id, String deliveryTime) {
        this.id = id;
        int HH = Integer.parseInt(deliveryTime.substring(0, 2));
        int MM = Integer.parseInt(deliveryTime.substring(deliveryTime.length()-2));
        this.deliveryTime = ((HH*60)+MM);
        this.isDriverAssigned = false;
        this.AssignedDeliveryPartner = "";
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
    public boolean isDriverAssigned() {
        return isDriverAssigned;
    }
    public void setDriverAssigned(boolean driverAssigned) {
        isDriverAssigned = driverAssigned;
    }

    public String getAssignedDeliveryPartner() {
        return AssignedDeliveryPartner;
    }

    public void setAssignedDeliveryPartner(String assignedDeliveryPartner) {
        AssignedDeliveryPartner = assignedDeliveryPartner;
    }
}
