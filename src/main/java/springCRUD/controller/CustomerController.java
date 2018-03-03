package springCRUD.controller;


import org.springframework.web.bind.annotation.*;
import springCRUD.dao.CustomerDAO;
import springCRUD.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import springCRUD.service.CustomerService;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {


    //need to inject the customer dao

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model theModel) {


        //get customers from  the dao
        List<Customer> theCustomers = customerService.getCustomers();

        //add the customers to the model
        theModel.addAttribute("customers", theCustomers);


        return "list-customers";
    }


    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        Customer theCustomer = new Customer();

        theModel.addAttribute("customer", theCustomer);

        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {

        customerService.saveCustomer(theCustomer);

        return "redirect:/customer/list";
    }


    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {

        //get the customer from the service
        Customer theCustomer = customerService.getCustomer(theId);


        //set customer as a model attribute to pre-populate the form
        theModel.addAttribute("customer", theCustomer);

        //send over to our form
        return "customer-form";
    }

    @GetMapping("/deleteCustomer")
    public String deleteUser(@RequestParam("customerId") int theId) {


        customerService.deleteCustomer(theId);

        return "redirect:/customer/list";
    }

    @PostMapping("/search")
    public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel) {

        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);

        theModel.addAttribute("customers", theCustomers);

        return "list-customers";

    }


}
