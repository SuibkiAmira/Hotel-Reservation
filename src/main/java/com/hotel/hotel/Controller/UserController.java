package com.hotel.hotel.Controller;

import com.hotel.hotel.Model.Booking;
import com.hotel.hotel.Model.Chambre;
import com.hotel.hotel.Service.BookingService;
import com.hotel.hotel.Service.ChambreService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;


@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private ChambreService chambreService;
    @Autowired
    private BookingService bookingService;


    @GetMapping("/")
    public ModelAndView GetHomeView(){
        ModelAndView modelAndView = new ModelAndView("index");
        logger.info("Someone in");
        return modelAndView;
    }

    @GetMapping("/rooms")
    public ModelAndView GetAllChambres() {
        ModelAndView modelAndView = new ModelAndView("rooms");
        List<Chambre> chambreList = chambreService.findAllByChambreStatusTrue();
        modelAndView.addObject("chambreList", chambreList);
        logger.info("User viewing available chambres");
        return modelAndView;
    }

    @GetMapping("/reservation")
    public ModelAndView GetMyOrders(@RequestParam Integer customerId){
        ModelAndView modelAndView = new ModelAndView("reservation");
        List<Booking> bookingList = bookingService.FindAllByCustomerId(1);
        modelAndView.addObject("bookingList",bookingList);
        logger.info("User viewing orders");
        return modelAndView;
    }

    @GetMapping("/bookingform")
    public ModelAndView BookingChambre(@RequestParam Integer chambreId){
        ModelAndView modelAndView = new ModelAndView("bookingform");
        Chambre chambre = chambreService.findChambreById(chambreId);
        Booking booking = new Booking();
        booking.setChambreId(chambre.getChambreId());
        booking.setCustomerId(1);
        modelAndView.addObject("booking",booking);
        logger.info("Some user trying to rent a chambre");
        return modelAndView;
    }

    @PostMapping("/save-booking")
    public String SaveBooking(@ModelAttribute Booking booking, @RequestParam Integer chambreId, RedirectAttributes redirectAttributes){
        Chambre chambre = chambreService.findChambreById(chambreId);
        boolean isAvailable = bookingService.isRoomAvailable(
                chambreId, booking.getBookingDateFrom(), booking.getBookingDateTo());

        if (!isAvailable) {
            redirectAttributes.addFlashAttribute("errorMessage", "Room not available on these dates. Please select different dates.");
            return "redirect:/bookingform?chambreId=" + chambreId;
        }
        LocalDate dateBefore = LocalDate.parse(booking.getBookingDateFrom());
        LocalDate dateAfter = LocalDate.parse(booking.getBookingDateTo());
        long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        if(noOfDaysBetween <1){
            booking.setTotalPrice(chambre.getChambrePrice() * (noOfDaysBetween +1));
        }
        else{
            booking.setTotalPrice(chambre.getChambrePrice() * noOfDaysBetween);
        }

        booking.setCustomerId(1);
        booking.setChambreId(chambreId);
        booking.setChambre(chambre);
        booking.setImage(chambre.getChambreImage());
        booking.setPriceDay(chambre.getChambrePrice());
        chambreService.SaveChambre(chambre);
        bookingService.BookingSave(booking);
        logger.info("User rented a chambre");
        return "redirect:/reservation?customerId=1";
    }


    @PostMapping("/update-booking")
    public String UpdateBooking(@ModelAttribute Booking updatedBooking, @RequestParam Integer chambreId, @RequestParam Integer bookingId, RedirectAttributes redirectAttributes){
        Booking booking = bookingService.FindBooking(bookingId);
        Chambre chambre = chambreService.findChambreById(chambreId);
        boolean isAvailable = bookingService.isRoomAvailable(
                chambreId, updatedBooking.getBookingDateFrom(), updatedBooking.getBookingDateTo());

        if (!isAvailable) {
            redirectAttributes.addFlashAttribute("errorMessage", "Room not available on these dates. Please select different dates.");
            return "redirect:/update-booking?bookingId=" + booking.getBookingId();
        }
        LocalDate dateBefore = LocalDate.parse(updatedBooking.getBookingDateFrom());
        LocalDate dateAfter = LocalDate.parse(updatedBooking.getBookingDateTo());
        long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        if(noOfDaysBetween <1){
            booking.setTotalPrice(chambre.getChambrePrice() * (noOfDaysBetween +1));
        }
        else{
            booking.setTotalPrice(chambre.getChambrePrice() * noOfDaysBetween);
        }
        booking.setBookingDateFrom(updatedBooking.getBookingDateFrom());
        booking.setBookingDateTo(updatedBooking.getBookingDateTo());
        booking.setPriceDay(chambre.getChambrePrice());
        chambreService.SaveChambre(chambre);
        bookingService.BookingSave(booking);
        logger.info("User rented a chambre");
        return "redirect:/reservation?customerId=1";
    }


    @GetMapping("/update-booking")
    public ModelAndView UpdateBooking(@RequestParam Integer bookingId){
        ModelAndView modelAndView = new ModelAndView("editbookingform");
        Booking booking = bookingService.FindBooking(bookingId);
        System.out.println(booking.getBookingId());
        modelAndView.addObject("booking",booking);
        logger.info("User trying to update booking");
        return modelAndView;
    }

    @GetMapping("/return-room")
    public String ReturnChambre(@RequestParam Integer bookingId, @RequestParam Integer chambreId) {
        Booking booking1 = bookingService.FindBooking(bookingId);
        Chambre chambre = chambreService.findChambreById(chambreId);
        chambre.setChambreStatus(true);
        bookingService.DeleteBooking(booking1.getBookingId());
        chambreService.SaveChambre(chambre);
        logger.info("User returned a chambre");
        return "redirect:/reservation?customerId=1";
    }

    @GetMapping("/search-rooms")
    public ModelAndView searchAvailableRooms(
            @RequestParam("checkInDate") String checkInDate,
            @RequestParam("checkOutDate") String checkOutDate) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.ENGLISH);

        try {
            LocalDate parsedCheckInDate = LocalDate.parse(checkInDate.trim(), inputFormatter);
            LocalDate parsedCheckOutDate = LocalDate.parse(checkOutDate.trim(), inputFormatter);

            List<Chambre> availableRooms = chambreService.findAvailableRooms(parsedCheckInDate, parsedCheckOutDate);

            ModelAndView modelAndView = new ModelAndView("rooms");
            modelAndView.addObject("chambreList", availableRooms);
            return modelAndView;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'd MMMM, yyyy'.");
        }
    }



    @PostMapping("/save-update")
    public String SaveUpdate(@ModelAttribute Booking booking, @RequestParam Integer bookingId) {
        Booking booking1 = bookingService.FindBooking(bookingId);
        LocalDate dateBefore = LocalDate.parse(booking.getBookingDateFrom());
        LocalDate dateAfter = LocalDate.parse(booking.getBookingDateTo());
        long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        if(noOfDaysBetween < 1) {
            booking1.setTotalPrice(booking1.getPriceDay());
        }
        else{
            booking1.setTotalPrice(booking1.getPriceDay() * noOfDaysBetween);
        }
        booking1.setBookingDateFrom(booking.getBookingDateFrom());
        booking1.setBookingDateTo(booking.getBookingDateTo());
        bookingService.BookingSave(booking1);
        logger.info("User updated booking");
        return "redirect:/rooms";
    }




}
