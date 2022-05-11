package dmit2015.youngjaelee.assignment07.restClient;

import dmit2015.youngjaelee.assignment07.entity.Bill;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Map;



@RegisterRestClient(baseUri = "https://dmit2015-1212-youngjaelee-default-rtdb.firebaseio.com/")
//@RegisterRestClient
public interface BillService {

    @POST
    @Path("/bills.json")
    JsonObject create(Bill newBill);

    @GET
    @Path("/bills.json")
    Map<String, Bill> list();

    @GET
    @Path("/bills/{key}.json")
    Bill findById(@PathParam("key") String id);

    @PUT
    @Path("/bills/{key}.json")
    Bill update(@PathParam("key") String id, Bill existingBill);

    @DELETE
    @Path("/bills/{key}.json")
    void delete(@PathParam("key") String id);
}
