package com.inlocal.restaurantapp.ui.homefragments.ui.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.CustomerDetails;

import java.io.Serializable;

public class ResponseCustomerDetails implements Serializable {
    @SerializedName("customerDetails")
    @Expose
    private CustomerDetails customerDetails;

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }
}
