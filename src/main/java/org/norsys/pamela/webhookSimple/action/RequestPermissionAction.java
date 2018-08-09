package org.norsys.pamela.webhookSimple.action;

import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.demandePermission.DemandePermission;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.springframework.stereotype.Component;

@Component("request_permission")
public class RequestPermissionAction extends AbstractWebhookMethods {


	@Override
	public String getActionName() {
		return "request_permission";
	}

	@Override
	public DifferentTypeReponses getReponse(Request request) {
		DemandePermission demandePermission = demandePermission();
		return demandePermission;
	}
}
