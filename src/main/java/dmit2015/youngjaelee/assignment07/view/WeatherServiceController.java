package dmit2015.youngjaelee.assignment07.view;

import dmit2015.youngjaelee.assignment07.model.OpenWeatherData;
import dmit2015.youngjaelee.assignment07.model.WeatherService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import dmit2015.youngjaelee.assignment07.view.BillListController.*;

@Named
public class WeatherServiceController {



    @Inject
    @RestClient
    private WeatherService _weatherService;

    @Inject
//    @ConfigProperty(name="api.openweathermap.org.ApiKey")
    @ConfigProperty(name="OPENWEATHERMAP_API")   // read value from Environment variable
    // You can export a environment variable in linux:
    // export OPENWEATHERMAP_APIKEY=yourOwnApiKey
    private String _weatherApiKey;

    @Getter
    private OpenWeatherData weatherData;

    private BillListController listController;

    public WeatherServiceController() {
        listController = new BillListController();
    }


    @PostConstruct
    void init() {
        try {

            if(listController.getCurrentGeoLocation() != null)
            {
                Double latitude = Double.parseDouble(listController.getCurrentGeoLocation().getLatitude());
                Double longitude = Double.parseDouble(listController.getCurrentGeoLocation().getLongitude());
                weatherData = _weatherService.findByGpsLocation(
                        latitude, longitude, _weatherApiKey, "metric");
            }
            else{
                weatherData = _weatherService.findByCityName(
                        "Edmonton", _weatherApiKey, "metric");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            Messages.addGlobalError("Error fetching weather data with exception {0}", ex.getMessage());
        }
    }
}
