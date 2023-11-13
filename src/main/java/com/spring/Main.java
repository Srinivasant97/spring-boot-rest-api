package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers/")
public class Main {

    private CustomerRepository customerRepository;
    public Main(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/hello")
    public GreetMethod greet(){
        GreetMethod response = new GreetMethod("Hello World!", List.of("Java", "Python", "ROR"), new Person("Ram",20,20_000));
        return response;
    }

    record GreetMethod(String greet, List<String> known_language, Person user){}

    record  Person(String name, int age, double salary){}

    record NewCustomerAdd(String name, String email, Integer age){}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerAdd request){
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setAge(request.age);
        customer.setEmail(request.email);
        customerRepository.save(customer);
    }
    @GetMapping
    public List<Customer> getCustomers() {
        return this.customerRepository.findAll();
    }

    @DeleteMapping("{customerId}")
    public void  deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }
}
