package com.hotel.hotel.Repository;

import com.hotel.hotel.Model.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChambreRepo extends JpaRepository<Chambre,Integer> {

    List<Chambre> findAllByChambreStatusTrue();
    @Query("SELECT c FROM Chambre c WHERE c.chambreId NOT IN " +
            "(SELECT b.chambre.chambreId FROM Booking b WHERE " +
            "b.bookingDateFrom < :checkOutDate AND b.bookingDateTo > :checkInDate)")
    List<Chambre> findAvailableRooms(@Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);
}
