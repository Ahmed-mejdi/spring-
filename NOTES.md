Annotations used:

@SpringBootApplication: Combines @Configuration, @EnableAutoConfiguration, and @ComponentScan
@RestController: Marks a class as a controller that returns domain objects, not views
@GetMapping: Maps HTTP GET requests to specific handler methods
@RequestParam: Binds request parameters to method parameters


Spring Boot converts Java objects to JSON using its auto-configuration of Jackson libraries.
@SpringBootApplication significance: It enables auto-configuration, component scanning, and defines a configuration class.
Port change: Add server.port=xxxx to application.properties.
Spring Boot advantages:

Simplified configuration
Embedded server
Starter dependencies
Production-ready features
Microservice support
Reduced development time