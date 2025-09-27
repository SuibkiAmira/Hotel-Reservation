package com.hotel.hotel.Model;

import javax.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    private int chambreId;
    private int customerId;
    private String bookingDateFrom;
    private String bookingDateTo;
    private int priceDay;

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    private String image;
    private double totalPrice;

    @ManyToOne
    private Chambre chambre;
    public Booking(){

    }

    public Booking(int bookingId, int chambreId, int customerId, String bookingDateFrom, String bookingDateTo, int priceDay, String image, double totalPrice) {
        this.bookingId = bookingId;
        this.chambreId = chambreId;
        this.customerId = customerId;
        this.bookingDateFrom = bookingDateFrom;
        this.bookingDateTo = bookingDateTo;
        this.priceDay = priceDay;
        this.image = image;
        this.totalPrice = totalPrice;
    }

    public int getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(int priceDay) {
        this.priceDay = priceDay;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getChambreId() {
        return chambreId;
    }

    public void setChambreId(int chambreId) {
        this.chambreId = chambreId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getBookingDateFrom() {
        return bookingDateFrom;
    }

    public void setBookingDateFrom(String bookingDateFrom) {
        this.bookingDateFrom = bookingDateFrom;
    }

    public String getBookingDateTo() {
        return bookingDateTo;
    }

    public void setBookingDateTo(String bookingDataTo) {
        this.bookingDateTo = bookingDataTo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
