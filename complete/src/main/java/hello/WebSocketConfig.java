package hello;

import java.security.Principal;
import java.util.Map;

import javax.annotation.sql.DataSourceDefinitions;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		//config.enableSimpleBroker("/topic");
		
		
		config.enableStompBrokerRelay("/topic", "/queue")
	    .setSystemLogin("guest")
	    .setSystemPasscode("guest")
	    .setRelayHost("localhost")
	    .setRelayPort(61613);//
	    //.setUseSSL(true)
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/hello")
		.setHandshakeHandler(new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
            	System.err.println(attributes.get("login"));
            	
            	// Principal principal = request.getPrincipal();
                /*if (principal == null) {
                	
                    
                    principal = new UsernamePasswordAuthenticationToken(new java.util.Date().getTime()+"", null);
                }*/
                return new UsernamePasswordAuthenticationToken(new java.util.Date().getTime()+"", null);
            }
        })
        .
		withSockJS();
	}

}