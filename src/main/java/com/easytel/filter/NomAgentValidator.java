/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.filter;

/**
 *
 * @author andri
 */
import com.easytel.dao.AgentDAO;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.easytel.filter.NomAgentValidator")
public class NomAgentValidator implements Validator{
    
    public NomAgentValidator() {
        
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component,
                    Object value) throws ValidatorException {
        boolean exist = AgentDAO.check("nom", value.toString());
        if(exist) {
            FacesMessage msg =
                        new FacesMessage("Vérification du nom",
                                        "Ce nom existe déja.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
        }
    }
        
}
