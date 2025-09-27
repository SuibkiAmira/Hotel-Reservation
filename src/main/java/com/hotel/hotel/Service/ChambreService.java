package com.hotel.hotel.Service;

import com.hotel.hotel.Model.Chambre;
import com.hotel.hotel.Repository.ChambreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChambreService implements ChambreServices {

    @Autowired
    private ChambreRepo ChambreRepo;

    @Override
    public List<Chambre> findAllByChambreStatusTrue() {
        return ChambreRepo.findAllByChambreStatusTrue();
    }

    @Override
    public List<Chambre> GetAllChambres() {
        return ChambreRepo.findAll();
    }

    @Override
    public List<Chambre> findAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        return ChambreRepo.findAvailableRooms(checkInDate, checkOutDate);
    }

    @Override
    public Chambre findChambreById(int chambreId) {
        Optional<Chambre> optional = ChambreRepo.findById(chambreId);
        Chambre chambre;
        if (optional.isPresent()) {
            chambre = optional.get();
        }
        else {
            throw new RuntimeException(
                    "Chambre not found for id : " + chambreId);
        }
        return chambre;

    }

    @Override
    public void SaveChambre(Chambre chambre) {
        ChambreRepo.save(chambre);
    }

    @Override
    public void DeleteChambre(int chambreId) {
        ChambreRepo.deleteById(chambreId);
    }


}
