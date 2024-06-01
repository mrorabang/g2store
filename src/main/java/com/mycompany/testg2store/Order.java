package com.mycompany.testg2store;

public class Order {
    private String orderId;
    private String customerPhone;
    private String employeeId;
    private String time;
    private float total;

    // Constructor
    public Order(String orderId, String customerPhone, String employeeId, String time, float total) {
        this.orderId = orderId;
        this.customerPhone = customerPhone;
        this.employeeId = employeeId;
        this.time = time;
        this.total = total;
    }

    // Getters and setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}

