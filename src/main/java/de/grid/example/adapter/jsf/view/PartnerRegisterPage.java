package de.grid.example.adapter.jsf.view;

import de.grid.example.application.PartnerService;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;

@Component
@ViewScoped
public class PartnerRegisterPage implements Serializable
{
    private static final long serialVersionUID = 1L;

    //todo Needs to be solved in other ways, but works for now
    private final PartnerService partnerService;

    private String firstName = "Justin";
    private String lastName = "Time";

    @Inject
    public PartnerRegisterPage(PartnerService partnerService)
    {
        this.partnerService = partnerService;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void registerPartner()
    {
        this.partnerService.registerNewPartner(firstName, lastName);
        addMessage(String.format("%s, %s registered!", lastName, firstName));
    }

    private void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}