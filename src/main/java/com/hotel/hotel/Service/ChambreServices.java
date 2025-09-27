package com.hotel.hotel.Service;

import com.hotel.hotel.Model.Chambre;

import java.time.LocalDate;
import java.util.List;

public interface ChambreServices {
    List<Chambre> findAllByChambreStatusTrue();
    List<Chambre> GetAllChambres();
    Chambre findChambreById(int chambreId);
    void SaveChambre(Chambre chambre);
    void DeleteChambre(int chambreId);
    public List<Chambre> findAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate);
}
