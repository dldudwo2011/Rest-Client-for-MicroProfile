package dmit2015.youngjaelee.assignment07.view;

import dmit2015.youngjaelee.assignment07.entity.Bill;
import dmit2015.youngjaelee.assignment07.restClient.BillService;
import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.math.BigDecimal;

@Named("currentBillCreateController")
@RequestScoped
public class BillCreateController {

    @Inject
    @RestClient
    private BillService _billService;

    @Getter
    private Bill newBill = new Bill();

    public String onCreateNew() {
        String nextPage = "";
        try {
            newBill.setAmountBalance(newBill.getAmountDue());
            _billService.create(newBill);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

}