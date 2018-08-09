package org.norsys.pamela.webhookSimple.action;

import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.demandePermission.Data;
import org.norsys.pamela.webhookSimple.model.demandePermission.DemandePermission;
import org.norsys.pamela.webhookSimple.model.demandePermission.GoogleDemandePermission;
import org.norsys.pamela.webhookSimple.model.demandePermission.PayloadDemandePermission;
import org.norsys.pamela.webhookSimple.model.demandePermission.SystemIntent;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.springframework.stereotype.Component;

@Component("sign_in")
public class SignIn extends AbstractWebhookMethods {

	@Override
	public String getActionName() {
		return "sign_in";
	}

	@Override
	public DifferentTypeReponses getReponse(Request request) {
		// je retourne une reponse pour activer l'intent qui contient l'event
		GoogleDemandePermission googleDemandePermission = new GoogleDemandePermission();
		googleDemandePermission.setExpectUserResponse(true);

		Data data = new Data();
		SystemIntent systemIntent = new SystemIntent();
		systemIntent.setIntent("actions.intent.SIGN_IN");
		systemIntent.setData(data);

		googleDemandePermission.setSystemIntent(systemIntent);
		PayloadDemandePermission payloadDemandePermission = new PayloadDemandePermission();
		payloadDemandePermission.setGoogle(googleDemandePermission);

		DemandePermission demandePermission = new DemandePermission();
		demandePermission.setPayload(payloadDemandePermission);
		return demandePermission;
	}

}
