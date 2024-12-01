package com.cxi.emp.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxi.emp.dto.ProductRequest;
import com.cxi.emp.dto.StripeResponse;
import com.cxi.emp.entity.Event;
import com.cxi.emp.entity.User;
import com.cxi.emp.repository.EventRepository;
import com.cxi.emp.repository.UserRepository;
import com.cxi.emp.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Controller
@RequestMapping("/")
public class ParticipantController{

  @Autowired
  private EventRepository eventRepository;
  @Autowired
  private UserRepository userRepository;
  // @Autowired
  // private StripeService stripeService;

    @GetMapping("/book-event/{id}")
    public String showBookForm(@PathVariable Integer id, Model model, Principal principal) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    
    int remainingCapacity = event.getCapacity() - (event.getParticipants() != null ? event.getParticipants().size() : 0);
    model.addAttribute("event", event);
    model.addAttribute("remainingCapacity", remainingCapacity);

    String username = principal.getName();
    Optional<User> user = userRepository.findByEmail(username);

    if (user.isPresent()) {
        model.addAttribute("userName", user.get().getName());
        model.addAttribute("userEmail", user.get().getEmail());
    } else {
        model.addAttribute("userName", "Unknown User");
        model.addAttribute("userEmail", "Unknown Email");
    }
        model.addAttribute("event", event);
        return "book-event";
    }

   @PostMapping("/checkout")
    public void checkoutProducts(@RequestBody ProductRequest productRequest) {
    System.out.println(productRequest);
        // StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        // return ResponseEntity
        //         .status(HttpStatus.OK)
        //         .body(stripeResponse);
    }

    @GetMapping("/success")
    public ResponseEntity<String> success(){
        return ResponseEntity.ok("Success!");
    }
}
