package com.agent;

import com.agent.service.AuthenticationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgentApplication {

    private static AuthenticationService authenticationService;

    public AgentApplication(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AgentApplication.class, args);

        String secretKey = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK";
        String lastCode = null;
        while (true) {
            String code = authenticationService.getTOTPCode(secretKey);
            if (!code.equals(lastCode)) {
                System.out.println(code);
            }
            lastCode = code;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            ;
        }
    }

}
