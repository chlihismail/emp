package com.cxi.emp.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxi.emp.dto.ProductRequest;
import com.cxi.emp.dto.StripeResponse;
import com.cxi.emp.entity.Event;
import com.cxi.emp.entity.User;
import com.cxi.emp.repository.EventRepository;
import com.cxi.emp.repository.UserRepository;
import com.cxi.emp.service.EventService;
import com.cxi.emp.service.StripeService;

@Controller
@RequestMapping("/")
public class ParticipantController{

  @Autowired
  private EventRepository eventRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private EventService eventService;
  @Autowired
  private StripeService stripeService;

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

  @GetMapping("/success")
  public ResponseEntity<String> success(){
    return ResponseEntity.ok("Success!");
  }

  @GetMapping("/cancel")
  public ResponseEntity<String> cancel(){
    return ResponseEntity.ok("Canceled operation!");
  }

  @GetMapping("/checkout/{id}")
  public String buyTicket(@PathVariable Integer id) {
    Optional<Event> event = eventService.getEventById(id);

    ProductRequest productRequest = ProductRequest.builder()
    .amount(event.get().getPrice().longValue()*100)
    .quantity(1L)
    .name(event.get().getTitle())
    .currency("USD")
    .build();
    StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
    String redirectUrl = stripeResponse.sessionUrl();

    //setting up the participant to the event and the event to the participant
    eventService.addParticipantToEvent(id);
    return "redirect:" + redirectUrl;
  }

}
