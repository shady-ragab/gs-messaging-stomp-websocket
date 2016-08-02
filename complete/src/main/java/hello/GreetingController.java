package hello;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
	
	@Autowired
	SimpMessagingTemplate simpleMessageTemplate;


    @MessageMapping("/hello")
    //@SendTo("/topic/greetings")
    public void greeting(HelloMessage message , Principal principal) throws Exception {
        Thread.sleep(1000); // simulated delay
        
        //simpleMessageTemplate.convertAndSend("/queue/greetings1", new Greeting("Hello, " + message.getName() + "!"));
        System.err.println(principal.getName());
        simpleMessageTemplate.convertAndSendToUser(principal.getName(),"/queue/greetings1", new Greeting("Hello, " + message.getName() + "!"));
        
        //return new Greeting("Hello, " + message.getName() + "!");
    }

}
