package bg.softuni.aquagate.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        //TODO configuration to encode and decode passwords!!!!!!!
        return new ModelMapper();
    }

}
