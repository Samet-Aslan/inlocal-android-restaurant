package com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.ui.bookingdetails.model.OrderItems;

import java.io.Serializable;
import java.util.List;

public class DeliveryOrderItem implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("table_no")
    @Expose
    private Integer tableNo;
    @SerializedName("order_items_count")
    @Expose
    private Integer orderItemsCount;
    @SerializedName("no_of_guest")
    @Expose
    private Integer noOfGuest;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("order_status_id")
    @Expose
    private Integer orderStatusId;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("percentage_tip_value")
    @Expose
    private Double percentageTipValue;
    @SerializedName("tip_amt")
    @Expose
    private Double tipAmt;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("order_subtotal")
    @Expose
    private Double orderSubtotal;
    @SerializedName("delivery_charge")
    @Expose
    private Double deliveryCharge;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("final_order_amount")
    @Expose
    private Double finalOrderAmount;
    @SerializedName("order_commission")
    @Expose
    private Integer orderCommission;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("created_by")
    @Expose
    private Object createdBy;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("deleted_by")
    @Expose
    private Object deletedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("booking_id")
    @Expose
    private Integer bookingId;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("order_items")
    @Expose
    private List<OrderItems> orderItems = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Integer orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Double getPercentageTipValue() {
        return percentageTipValue;
    }

    public void setPercentageTipValue(Double percentageTipValue) {
        this.percentageTipValue = percentageTipValue;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Double getOrderSubtotal() {
        return orderSubtotal;
    }

    public void setOrderSubtotal(Double orderSubtotal) {
        this.orderSubtotal = orderSubtotal;
    }

    public Double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getFinalOrderAmount() {
        return finalOrderAmount;
    }

    public void setFinalOrderAmount(Double finalOrderAmount) {
        this.finalOrderAmount = finalOrderAmount;
    }

    public Integer getOrderCommission() {
        return orderCommission;
    }

    public void setOrderCommission(Integer orderCommission) {
        this.orderCommission = orderCommission;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public Integer getNoOfGuest() {
        return noOfGuest;
    }

    public void setNoOfGuest(Integer noOfGuest) {
        this.noOfGuest = noOfGuest;
    }

    public Integer getOrderItemsCount() {
        return orderItemsCount;
    }

    public void setOrderItemsCount(Integer orderItemsCount) {
        this.orderItemsCount = orderItemsCount;
    }

    public Double getTipAmt() {
        return tipAmt;
    }

    public void setTipAmt(Double tipAmt) {
        this.tipAmt = tipAmt;
    }
}
