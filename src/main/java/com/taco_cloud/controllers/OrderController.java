package com.taco_cloud.controllers;

import com.taco_cloud.data.TacoOrder;
import com.taco_cloud.data.User;
import com.taco_cloud.repository.OrderRepository;
import com.taco_cloud.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);

        log.info("Order submitted: {}", order);
        orderRepository.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder putOrder(@PathVariable Long orderId, @RequestBody TacoOrder order) {
        order.setId(orderId);
        return orderRepository.save(order);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder patchOrder(@PathVariable Long orderId, @RequestBody TacoOrder order) {
        TacoOrder old = orderRepository.findById(orderId).get();

        if (Objects.nonNull(order.getDeliveryName())) {
            old.setDeliveryName(order.getDeliveryName());
        }

        if (Objects.nonNull(order.getDeliveryStreet())) {
            old.setDeliveryStreet(order.getDeliveryStreet());
        }

        if (Objects.nonNull(order.getDeliveryCity())) {
            old.setDeliveryCity(order.getDeliveryCity());
        }

        if (Objects.nonNull(order.getDeliveryState())) {
            old.setDeliveryState(order.getDeliveryState());
        }

        if (Objects.nonNull(order.getDeliveryZip())) {
            old.setDeliveryZip(order.getDeliveryZip());
        }

        if (Objects.nonNull(order.getCcNumber())) {
            old.setCcNumber(order.getCcNumber());
        }

        if (Objects.nonNull(order.getCcExpiration())) {
            old.setCcExpiration(order.getCcExpiration());
        }

        if (Objects.nonNull(order.getCcCVV())) {
            old.setCcCVV(order.getCcCVV());
        }

        if (!CollectionUtils.isEmpty(order.getTacos())) {
            old.setTacos(order.getTacos());
        }

        return orderRepository.save(old);
    }

    @DeleteMapping(path = "/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {
            //ignore
        }
    }
}
