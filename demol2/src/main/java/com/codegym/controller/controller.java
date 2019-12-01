package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class controller {
    @Autowired
    private CustomerService customerService;
   @GetMapping("/home")
   public ModelAndView home(@RequestParam("seach")Optional<String>seach, @PageableDefault(size = 4, direction = Sort.Direction.DESC ) Pageable pageable) {

       Page<Customer> customers;
       if(seach.isPresent()){
           customers = customerService.findAllByName(seach.get(),pageable);
       }else{
           customers = customerService.findAll(pageable);
       }
       ModelAndView modelAndView = new ModelAndView("home");
       modelAndView.addObject("customers", customers);
       return modelAndView;
   }


   @GetMapping("/customer/create")
    public ModelAndView showCreate(){
       ModelAndView modelAndView = new ModelAndView("create");
       modelAndView.addObject("customer", new  Customer());
       return modelAndView;
   }
   @PostMapping("/create/Function")
    public ModelAndView createFunction(@ModelAttribute Customer customer){
       customerService.save(customer);
       ModelAndView modelAndView = new ModelAndView("redirect:/home");
       modelAndView.addObject("customer", new Customer());
       modelAndView.addObject("mess","successfull");
       return modelAndView;
   }

   @GetMapping("/customer/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id){
       Customer customer = customerService.findById(id);
       ModelAndView modelAndView = new ModelAndView("edit");
       modelAndView.addObject("customer",customer);
       return modelAndView;
   }

   @PostMapping("/edit/function")
   public ModelAndView editFunction(@ModelAttribute Customer customer){
       customerService.save(customer);
       ModelAndView modelAndView = new ModelAndView("redirect:/home");
       modelAndView.addObject("customers",customer);
       modelAndView.addObject("mess","succsessfull");
       return modelAndView;
   }

   @GetMapping("/customer/delete/{id}")
    public ModelAndView showDelete(@PathVariable Long id){
       Customer customer = customerService.findById(id);
       ModelAndView modelAndView = new ModelAndView("delete");
       modelAndView.addObject("customer",customer);
       return modelAndView;
   }
   @PostMapping("/delete/function")
    public ModelAndView deleteFunction(@RequestParam Long id){
       customerService.remove(id);
       ModelAndView modelAndView = new ModelAndView("redirect:/home");
       return modelAndView;
   }

}
