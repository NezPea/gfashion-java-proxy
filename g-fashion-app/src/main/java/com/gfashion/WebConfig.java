@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${server.corsDomain}")
    private String corsDomain;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(corsDomain);
    }
}
