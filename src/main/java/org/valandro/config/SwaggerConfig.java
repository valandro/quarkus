package org.valandro.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        tags = {
                @Tag(name = "Access Log API", description = "API to manage your applications access logs."),
        },
        info = @Info(
                title = "User API with Quarkus",
                version = "1.0.0",
                contact = @Contact(
                        name = "Lucas Valandro",
                        url = "http://github.com/valandro"),
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/licenses/MIT")))
public class SwaggerConfig extends Application {
}
