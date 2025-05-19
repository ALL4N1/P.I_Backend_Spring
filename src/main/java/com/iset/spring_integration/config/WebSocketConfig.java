package com.iset.spring_integration.config;

import com.iset.spring_integration.services.AuthChannelInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private AuthChannelInterceptor authChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:3000") // Limité au dev
                .withSockJS()
                .setSuppressCors(false); // ✅ Correction de la méthode
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // Activer /topic
        config.enableSimpleBroker("/queue"); // Uniquement pour les files d'attente privées
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor)
                .taskExecutor()
                .corePoolSize(20) // Augmenter la capacité du pool
                .maxPoolSize(50)
                .queueCapacity(100);
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        // Augmenter les limites de messages
        registry.setMessageSizeLimit(512 * 1024); // 512KB
        registry.setSendTimeLimit(20 * 1000); // 20 secondes
        registry.setSendBufferSizeLimit(1024 * 1024); // 1MB
    }
}
