package com.hotel.hotel.Controller;

import com.hotel.hotel.Model.Chambre;
import com.hotel.hotel.Model.Customer;
import com.hotel.hotel.Service.ChambreService;
import com.hotel.hotel.Service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminContoller {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private ChambreService chambreService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/admin/rooms")
    public ModelAndView GetAllChambresForAdmin(){
        ModelAndView modelAndView = new ModelAndView("rooms");
        List<Chambre> ChambreList = chambreService.GetAllChambres();
        modelAndView.addObject("chambreList",ChambreList);
        logger.info("Admin viewing Chambres");
        return modelAndView;
    }


    @GetMapping("/admin/add-room")
    public ModelAndView AddChambre(){
        ModelAndView modelAndView = new ModelAndView("new-room");
        Chambre chambre = new Chambre();
        modelAndView.addObject("chambre",chambre);
        logger.info("Admin trying to add a new Chambre");
        return modelAndView;
    }

    @GetMapping("/admin/room")
    public ModelAndView UpdateChambreInfo(@RequestParam Integer chambreId){
        ModelAndView modelAndView = new ModelAndView("new-room");
        Chambre chambre = chambreService.findChambreById(chambreId);
        modelAndView.addObject("chambre",chambre);
        logger.info("Admin trying to update a Chambre");
        return modelAndView;
    }


    @GetMapping("error-delete")
    public ModelAndView DeleteError(){
        logger.info("Admin couldn't delete a chambre");
        return new ModelAndView("error-delete");
    }

    @GetMapping("delete-room")
    public String DeleteChambre(@RequestParam Integer chambreId){
        Chambre Chambre = chambreService.findChambreById(chambreId);
        if(Chambre.isChambreStatus()){
            chambreService.DeleteChambre(chambreId);
            logger.info("Admin deleted a Chambre with info " + " " + Chambre.getChambreName() + " " + Chambre.getChambreModel());
            return "redirect:/rooms";
        }
        else {
            return "redirect:/error-delete";
        }

    }
    @GetMapping("/admin/customers")
    public ModelAndView GetAllCustomers(){
        ModelAndView modelAndView = new ModelAndView("customers");
        List<Customer> customerList = customerService.GetAllCustomer();
        modelAndView.addObject("customerList",customerList);
        logger.info("Admin viewing customers");
        return modelAndView;
    }

    @GetMapping("/admin/customer/customer-id")
    public ModelAndView UpdateCustomer(@RequestParam int customerId){
        ModelAndView modelAndView = new ModelAndView("update-customer");
        Customer customer = customerService.FindCustomerById(customerId);
        modelAndView.addObject("customer",customer);
        logger.info("Admin trying to update a customer info");
        return modelAndView;
    }

    @GetMapping("delete/customer")
    public String DeleteCustomer(@RequestParam Integer customerId){
        Customer customer = customerService.FindCustomerById(customerId);
        customerService.DeleteCustomer(customer);
        logger.info("Admin deleted a customer");
        return "redirect:/admin/customers";
    }

    @GetMapping("/admin/new/customer")
    public ModelAndView AddCustomer(){
        ModelAndView modelAndView = new ModelAndView("new-customer");
        Customer customer = new Customer();
        modelAndView.addObject("customer",customer);
        logger.info("Admin trying to add  a new customer");
        return modelAndView;
    }


    @PostMapping("/save-customer")
    public String SaveCustomer(@ModelAttribute Customer customer){
        customerService.SaveCustomer(customer);
        logger.info("Admin saved some process about customers");
        return "redirect:/admin/customers";
    }

    @PostMapping("/save-room")
    public String SaveChambre(@ModelAttribute Chambre Chambre){
        chambreService.SaveChambre(Chambre);
        logger.info("Admin saved some process about Chambres");
        return "redirect:/rooms";
    }


}
