package com.example.demo.services;

import com.example.demo.dao.CartRepository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entities.StatusType;
import com.example.demo.entities.dto.Purchase;
import com.example.demo.entities.dto.PurchaseResponse;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;
import java.util.UUID;

@Service
@CrossOrigin("http://localhost:4200")
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;
    private CartRepository cartRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository, CartRepository cartRepository){
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }
    // Will grab order info from the dto / / Generate a tracking #
    // Give an order items / / populate Address  // save to data base
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        try {
            Cart cart = purchase.getCart();
            Customer customer = purchase.getCustomer();

            if (cart == null){
                throw new Exception("Cart is Empty");
            }
            if (customer == null){
                throw new Exception("There's no customer");
            }
            String orderTrackingNumber = generateOrderTrackingNumber();
            cart.setOrderTrackingNumber(orderTrackingNumber);

            Set<CartItem> cartItems = purchase.getCartItems();
            cartItems.forEach(item -> cart.add(item));

            cart.setCartItem(purchase.getCartItems());
            cart.setCustomer(purchase.getCustomer());

            customer.add(cart);
            cart.setStatus(StatusType.ordered);

            //Saves to the Database
            customerRepository.save(customer);
            cartRepository.save(cart);


            System.out.println();
            System.out.println(orderTrackingNumber);
            System.out.println();
            return new PurchaseResponse(orderTrackingNumber);
        }catch (Exception e){
            return new PurchaseResponse("There was an issue processing your order! Please try again!");
        }
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number
        return UUID.randomUUID().toString();
    }
}
