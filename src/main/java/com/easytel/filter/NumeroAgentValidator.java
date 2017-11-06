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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.easytel.filter.NumeroAgentValidator")
public class NumeroAgentValidator implements Validator{

	private static final String NUMERO_PATTERN = "[0-9]{10}";

	private Pattern pattern;
	private Matcher matcher;

	public NumeroAgentValidator(){
		  pattern = Pattern.compile(NUMERO_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		matcher = pattern.matcher(value.toString());
                boolean exist = AgentDAO.check("numero", value.toString());
		if(!matcher.matches()){
			FacesMessage msg =
				new FacesMessage("Echec de la validation du numéro",
						"Format du numéro téléphone invalide.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		} else if(exist) {
                    FacesMessage msg =
				new FacesMessage("Vérification du numéro",
						"Ce numéro est déjà utilisé par un agent.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
                }

	}
}
