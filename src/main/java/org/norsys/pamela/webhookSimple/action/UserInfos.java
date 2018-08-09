package org.norsys.pamela.webhookSimple.action;

import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.request.Arguments;
import org.norsys.pamela.webhookSimple.model.request.Inputs;
import org.norsys.pamela.webhookSimple.model.request.Location;
import org.norsys.pamela.webhookSimple.model.request.PayloadRequest;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.norsys.pamela.webhookSimple.model.response.Reponse;
import org.springframework.stereotype.Component;

@Component("user_info")
public class UserInfos extends AbstractWebhookMethods {

	@Override
	public String getActionName() {
		return "user_info";
	}

	@Override
	public DifferentTypeReponses getReponse(Request request) {
		// ici la requete a des information en plus sur l'utilisateur en fonction de sa
		// reponse, si elle est oui ou non
		PayloadRequest payload = request.getOriginalDetectIntentRequest().getPayload();
		Inputs input = payload.getInputs().get(0);
		Arguments arguments = input.getArguments().get(0);

		if (arguments.getTextValue().equals("true") && arguments.isBoolValue()) {
			// ici l'utilisateur a dit oui, je dois retourner son adresse
			Location location = payload.getDevice().getLocation();
			displayName = payload.getUser().getProfile().getDisplayName();
			prenom = payload.getUser().getProfile().getGivenName();
			adresse = location.getFormattedAddress();
			String ville = location.getCity();
			// je retourne un objet reponse avec l'adresse
			Reponse reponse = creationReponse("Vous vous trouvez actuellement au " + adresse + " votre ville est "
					+ ville + " et votre nom est  " + displayName, null, false);
			return reponse;
		} else {
			Reponse reponse = creationReponse(
					"Vous ne m'avez pas donner de permission pour acceder à vos informations personnelles", null,
					false);
			return reponse;
		}
	
	}

}
