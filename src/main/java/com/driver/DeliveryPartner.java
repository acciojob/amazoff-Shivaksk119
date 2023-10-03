package com.driver;

import javax.print.attribute.standard.PrinterURI;
import java.security.PrivateKey;
import java.sql.Array;
import java.util.ArrayList;

public class DeliveryPartner {

    private String id;
    private int numberOfOrders;
    private boolean isOrderAssigned;
    private ArrayList<String> AssignedOrdersList;

    public DeliveryPartner(String id) {
        this.id = id;
        this.numberOfOrders = 0;
        this.isOrderAssigned = false;
        this.AssignedOrdersList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfOrders(){

        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public boolean isOrderAssigned() {
        return isOrderAssigned;
    }

    public void setOrderAssigned(boolean orderAssigned) {
        isOrderAssigned = orderAssigned;
    }

    public ArrayList<String> getAssignedOrdersList() {
        return AssignedOrdersList;
    }

    public void setAssignedOrdersList(ArrayList<String> assignedOrdersList) {
        AssignedOrdersList = assignedOrdersList;
    }
}