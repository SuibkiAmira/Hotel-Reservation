package com.hotel.hotel.Model;
import javax.persistence.*;
import java.util.List;

@Entity
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chambreId;
    private String chambreName;
    private String chambreModel;
    private String chambreImage;
    private int chambrePrice;
    private boolean chambreStatus = true; // available or not
    @OneToMany(mappedBy="chambre")
    private List<Booking> bookingList;
    public Chambre(){

    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public Chambre(int chambreId, String chambreName, String chambreModel, String chambreImage, int chambrePrice, boolean chambreStatus) {
        this.chambreId = chambreId;
        this.chambreName = chambreName;
        this.chambreModel = chambreModel;
        this.chambreImage = chambreImage;
        this.chambrePrice = chambrePrice;
        this.chambreStatus = chambreStatus;
    }

    public int getChambreId() {
        return chambreId;
    }

    public void setChambreId(int chambreId) {
        this.chambreId = chambreId;
    }

    public String getChambreName() {
        return chambreName;
    }

    public void setChambreName(String chambreName) {
        this.chambreName = chambreName;
    }

    public String getChambreModel() {
        return chambreModel;
    }

    public void setChambreModel(String chambreModel) {
        this.chambreModel = chambreModel;
    }

    public String getChambreImage() {
        return chambreImage;
    }

    public void setChambreImage(String chambreImage) {
        this.chambreImage = chambreImage;
    }

    public int getChambrePrice() {
        return chambrePrice;
    }

    public void setChambrePrice(int chambrePrice) {
        this.chambrePrice = chambrePrice;
    }

    public boolean isChambreStatus() {
        return chambreStatus;
    }

    public void setChambreStatus(boolean chambreStatus) {
        this.chambreStatus = chambreStatus;
    }
}
