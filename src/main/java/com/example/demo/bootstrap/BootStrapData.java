package com.example.demo.bootstrap;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Country;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Not;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BootStrapData implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Transactional //This is required since this is a transactional!!!
    @Override
    public void run(String... args) throws Exception {
        Division division = new Division() ;
        division.setId(2L);
        division.getDivision_name();

        division.setCountry_id(1L);

        //Creates customer information, Gets it, and adds it!
        Customer Danny = new Customer("1234 Quarantania Dr", "Danny","Brown",
                                        "7412447895", "95214",  division);
        division.getCustomers().add(Danny);

        Customer Alan = new Customer("1234 Best Friend dr", "Alan", "Chemicant",
                                        "5962447124", "95214",  division);
        division.getCustomers().add(Alan);

        Customer Jessie = new Customer("684 Pleasure Dr", "Jessie", "Ware",
                                        "1589637124", "98752",  division);
        division.getCustomers().add(Jessie);

        Customer Cove = new Customer("4517 Come Close dr", "Cove", "Reber",
                                        "6587418521", "45682",  division);
        division.getCustomers().add(Cove);

        Customer George = new Customer("1234 Oh I Ya Dr", "George", "Clanton",
                                         "9852657454", "65427",  division);
        division.getCustomers().add(George);

        //Saves the Division Country
        divisionRepository.save(division);

        //Saves hardcoded in Customers
        customerRepository.save(Danny);
        customerRepository.save(Alan);
        customerRepository.save(Jessie);
        customerRepository.save(Cove);
        customerRepository.save(George);

    }
}
