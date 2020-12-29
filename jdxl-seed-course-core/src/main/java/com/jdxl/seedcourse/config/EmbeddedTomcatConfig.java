package com.jdxl.seedcourse.config;

import lombok.Data;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.Servlet;

@Configuration
@ConditionalOnClass({ Servlet.class, Tomcat.class })
@EnableConfigurationProperties({EmbeddedTomcatConfig.TomcatKeepaliveProperties.class})
public class EmbeddedTomcatConfig {

    @Data
    @ConfigurationProperties(prefix = "server.tomcat")
    class TomcatKeepaliveProperties {
        private int keepAliveTimeout = 20000;
        private int maxKeepAliveRequests = 100;
    }

    /**
     * Nested configuration if Tomcat is being used.
     */
    @Component
    public class JdxlEmbeddedServletContainerCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

        private TomcatKeepaliveProperties tomcatKeepaliveProperties;

        @Autowired
        public void setTomcatKeepaliveProperties(TomcatKeepaliveProperties tomcatKeepaliveProperties) {
            this.tomcatKeepaliveProperties = tomcatKeepaliveProperties;
        }

        @Override
        public void customize(ConfigurableServletWebServerFactory factory) {
            ((TomcatServletWebServerFactory)factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
                @Override
                public void customize(Connector connector) {
                    Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
                    protocol.setKeepAliveTimeout(tomcatKeepaliveProperties.getKeepAliveTimeout());
                    protocol.setMaxKeepAliveRequests(tomcatKeepaliveProperties.getMaxKeepAliveRequests());
                }
            });
        }
    }


}
