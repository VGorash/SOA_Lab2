package com.vgorash.lab2.controller;

import com.vgorash.lab2.model.Ticket;
import com.vgorash.lab2.model.TicketType;
import com.vgorash.lab2.util.XStreamUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api/sell")
@RestController
public class MainController {

    private final RestTemplate restTemplate;

    public MainController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${lab2.main_url}")
    private String serviceUrl;

    @GetMapping("/test")
    public String test(){
        return "Success";
    }

    @PostMapping(value = "/vip/{ticket-id}", produces = "application/xml")
    public String vipTicket(@PathVariable(name = "ticket-id") Long ticketId, HttpServletResponse response){
        try {
            String result =  restTemplate.getForObject(serviceUrl + ticketId.toString(), String.class);
            Ticket ticket = XStreamUtil.fromXML(result);
            ticket.setType(TicketType.VIP);
            if(ticket.getPrice() != null){
                ticket.setPrice(ticket.getPrice() * 2);
            }
            result = restTemplate.postForObject(serviceUrl, XStreamUtil.toXML(ticket), String.class);
            return result;
        }
        catch (HttpClientErrorException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PostMapping(value = "/{ticket-id}/{price}", produces = "application/xml")
    public String sellTicket(@PathVariable(name = "ticket-id") Long ticketId,
                             @PathVariable(name = "price") Integer price){
        try {
            String result =  restTemplate.getForObject(serviceUrl + ticketId.toString(), String.class);
            Ticket ticket = XStreamUtil.fromXML(result);
            ticket.setPrice(price);
            result = restTemplate.postForObject(serviceUrl, XStreamUtil.toXML(ticket), String.class);
            return result;
        }
        catch (HttpClientErrorException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

}
