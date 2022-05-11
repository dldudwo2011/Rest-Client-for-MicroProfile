package dmit2015.youngjaelee.assignment07.view;

import dmit2015.youngjaelee.assignment07.entity.Bill;
import dmit2015.youngjaelee.assignment07.restClient.BillService;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Optional;

@Named("currentBillDeleteController")
@ViewScoped
public class BillDeleteController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    @RestClient
    private BillService _billService;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private String editId;

    @Getter
    private Bill existingBill;

    @PostConstruct
    public void init() {
        if (!Faces.isPostback()) {
            if (editId != null) {
                existingBill = _billService.findById(editId);
                if (existingBill == null) {
                    Faces.redirect(Faces.getRequestURI().substring(0, Faces.getRequestURI().lastIndexOf("/")) + "/index");
                }
            } else {
                Faces.redirect(Faces.getRequestURI().substring(0, Faces.getRequestURI().lastIndexOf("/")) + "/index");
            }
        }
    }

    public String onDelete() {
        String nextPage = "";
        try {
            _billService.delete(editId);
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete was not successful.");
        }
        return nextPage;
    }
}