package com.vgorash.lab2.controller;

import com.vgorash.lab2.config.ServiceDiscoveryClient;
import com.vgorash.lab2.model.Ticket;
import com.vgorash.lab2.model.TicketType;
import com.vgorash.lab2.util.XStreamUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/sell")
@RestController
public class MainController {

    private final RestTemplate restTemplate;
    private final ServiceDiscoveryClient serviceDiscoveryClient;

    public MainController(RestTemplate restTemplate, ServiceDiscoveryClient serviceDiscoveryClient){
        this.restTemplate = restTemplate;
        this.serviceDiscoveryClient = serviceDiscoveryClient;
    }

    @Value("${service1.name}")
    private String serviceName;

    @GetMapping("/test")
    public String test(){
        System.out.println("Request");
        String serviceUrl = serviceDiscoveryClient.getUriFromConsul(serviceName);
        return restTemplate.getForObject(serviceUrl, String.class);
    }

    @PostMapping(value = "/vip/{ticket-id}", produces = "application/xml")
    public String vipTicket(@PathVariable(name = "ticket-id") Long ticketId){
        String serviceUrl = serviceDiscoveryClient.getUriFromConsul(serviceName);
        try {
            String result =  restTemplate.getForObject(serviceUrl + ticketId.toString(), String.class);
            Ticket ticket = XStreamUtil.fromXML(result);
            ticket.setType(TicketType.VIP);
            if(ticket.getPrice() != null){
                ticket.setPrice(ticket.getPrice() * 2);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> entity = new HttpEntity<>(XStreamUtil.toXML(ticket), headers);
            return restTemplate.postForObject(serviceUrl, entity, String.class);
        }
        catch (HttpClientErrorException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    @PostMapping(value = "/{ticket-id}/{price}", produces = "application/xml")
    public String sellTicket(@PathVariable(name = "ticket-id") Long ticketId,
                             @PathVariable(name = "price") Integer price){
        String serviceUrl = serviceDiscoveryClient.getUriFromConsul(serviceName);
        try {
            String result =  restTemplate.getForObject(serviceUrl + ticketId.toString(), String.class);
            Ticket ticket = XStreamUtil.fromXML(result);
            ticket.setPrice(price);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> entity = new HttpEntity<>(XStreamUtil.toXML(ticket), headers);
            return restTemplate.postForObject(serviceUrl, entity, String.class);
        }
        catch (HttpClientErrorException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

}
