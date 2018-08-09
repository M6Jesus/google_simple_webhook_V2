/**
 * 
 */
package org.norsys.pamela.webhookSimple.action;

import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.norsys.pamela.webhookSimple.model.response.Reponse;
import org.springframework.stereotype.Component;

/**
 * @author panou
 *
 */
@Component("DefaultAction")
public class DefaultAction extends AbstractWebhookMethods {

	@Override
	public DifferentTypeReponses getReponse(Request request) {
		Reponse reponse = creationReponse("nous n'avons pas d'action de ce genre", null, false);
		return reponse;
	}

	@Override
	public String getActionName() {
		return "DefaultAction";
	}

}
