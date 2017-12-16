/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author odzhara-ongom
 */
@Configuration
public class BeansConfig {

    @Bean
    public RestTemplate buildTemplate() {
        return new RestTemplate();
    }
}
