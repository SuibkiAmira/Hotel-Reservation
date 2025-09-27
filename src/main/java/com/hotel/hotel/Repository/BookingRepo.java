package com.hotel.hotel.Repository;

import com.hotel.hotel.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Integer> {

    List<Booking> findAllByCustomerId(int customerId);
    @Query("SELECT b FROM Booking b WHERE b.chambreId = :chambreId AND " +
            "((b.bookingDateFrom <= :bookingDateTo AND b.bookingDateTo >= :bookingDateFrom))")
    List<Booking> findOverlappingBookings(@Param("chambreId") Integer chambreId,
                                          @Param("bookingDateFrom") String bookingDateFrom,
                                          @Param("bookingDateTo") String bookingDateTo);

}
