package com.vgorash.lab2.config;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.health.ServiceHealth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceDiscoveryClient {

    @Value("${service1.url}")
    private String backupUrl;

    private Consul client;

    @PostConstruct
    private void init(){
        try {
            client = Consul.builder().build();
        } catch (Exception e) {
            System.err.println("Consul is unavailable");
        }
    }

    public String getUriFromConsul(String serviceName){
        if (client != null) {
            HealthClient healthClient = client.healthClient();
            List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances(serviceName).getResponse();
            if (nodes.size() > 0) {
                ServiceHealth service = nodes.get(0);
                String address = service.getNode().getAddress();
                int port = service.getService().getPort();
                String app = service.getService().getMeta().get("api_address");
                return String.format("https://%s:%d/%s", address, port, app);
            }
        }
        System.err.println("Service is not available from consul - using fallback resource");
        return backupUrl;
    }
}
