package dmit2015.youngjaelee.assignment07.view;

import dmit2015.youngjaelee.assignment07.entity.Bill;
import dmit2015.youngjaelee.assignment07.restClient.BillService;
import dmit2015.youngjaelee.assignment07.restClient.GeoLocation;
import dmit2015.youngjaelee.assignment07.restClient.GeoLocationService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import java.io.Serializable;
import java.util.Map;

@Named("currentBillListController")
@ViewScoped
public class BillListController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    @RestClient
    private BillService _billService;

    @Inject
    @ConfigProperty(name="IPGEOLOCATION_API")
    private String ipGeoLocationApiKey;

    @Inject
    @RestClient
    private GeoLocationService _geoLocationService;

    @Getter
    private GeoLocation currentGeoLocation;

    @Getter
    private Map<String, Bill> billMap;



    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            String remoteIP = Faces.getRemoteAddr();
            if (remoteIP.equals("127.0.0.1")) {
                // Call location API without IP address

                currentGeoLocation = _geoLocationService.withoutIPAddress(ipGeoLocationApiKey);
            } else {
                // Call location API with IP address
                currentGeoLocation = _geoLocationService.withIPAddress(ipGeoLocationApiKey,remoteIP);
            }

            billMap = _billService.list();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}