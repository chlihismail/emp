package com.cxi.emp.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxi.emp.dto.ProductRequest;
import com.cxi.emp.entity.Event;
import com.cxi.emp.entity.User;
import com.cxi.emp.repository.EventRepository;
import com.cxi.emp.repository.UserRepository;


@RestController
@RequestMapping("/api/participant")
public class ParticipantRestController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    // @Autowired
    // private StripeService stripeService;

    @GetMapping("/book-event/{id}")
    public ResponseEntity<Map<String, Object>> showBookForm(@PathVariable Integer id, Principal principal) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        int remainingCapacity = event.getCapacity() - (event.getParticipants() != null ? event.getParticipants().size() : 0);

        String username = principal.getName();
        Optional<User> user = userRepository.findByEmail(username);

        Map<String, Object> response = new HashMap<>();
        response.put("event", event);
        response.put("remainingCapacity", remainingCapacity);
        response.put("userName", user.map(User::getName).orElse("Unknown User"));
        response.put("userEmail", user.map(User::getEmail).orElse("Unknown Email"));

        return ResponseEntity.ok(response);
    }


    @PostMapping("/checkout")
    public ResponseEntity<String> checkoutProducts(@RequestBody ProductRequest productRequest) {
        System.out.println(productRequest);
        // StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        // return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);

        return ResponseEntity.ok("Checkout logic will be implemented.");
    }
 
    @GetMapping("/success")
    public ResponseEntity<String> success() {
        return ResponseEntity.ok("Success!");
    }
}

