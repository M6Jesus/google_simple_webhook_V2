package org.norsys.pamela.webhookSimple.action;

import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.request.Request;

public interface WebhookAction {
	
	public String getActionName();

	public DifferentTypeReponses getReponse(Request request);
	
	
}
