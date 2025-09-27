package com.hotel.hotel.Service;

import com.hotel.hotel.Model.Booking;

import java.util.List;

public interface BookingServices {
    List<Booking> FindAllByCustomerId(int customerId);
    void BookingSave(Booking booking);
    Booking FindBooking(int bookingId);
    void DeleteBooking(int bookingId);
    public boolean isRoomAvailable(Integer chambreId, String bookingDateFrom, String bookingDateTo);

}
