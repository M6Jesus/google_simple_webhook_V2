package org.norsys.pamela.webhookSimple.action;

import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.request.QueryResult;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.norsys.pamela.webhookSimple.model.response.Reponse;
import org.springframework.stereotype.Component;


@Component("UserProvides_firstName")
public class UserProvidesFirstName extends AbstractWebhookMethods{

	@Override
	public String getActionName() {
		
		return "UserProvides_firstName";
	}

	@Override
	public DifferentTypeReponses getReponse(Request request) {
		QueryResult queryResult = request.getQueryResult();
		String valeurQuestion = queryResult.getQueryText();
		Reponse reponse = new Reponse();
		String nouveauContexte = RecupereEtRenvoieLeNouveauOutputContext(request, "awaiting_LastName");
		reponse = creationReponse("nous avons rencontrer un probleme interne. Merci de réessayer plus tard",
				null, true);
		String prenoms = valeurQuestion.toLowerCase();
		if (prenoms.equals("pamela")) {
			reponse = creationReponse("Merci. vous m'avez dit " + prenoms + ". Pourriez vous me donner votre nom s'il vous plait?", nouveauContexte, false);
			prenomSecurite = prenoms;
		}
		else {
			//***si le prenom n'est pas reconnu je le notifie et ferme la conversation ****///
			reponse = creationReponse("Desoler. vous m'avez dit " + prenoms + ". ce prénom n'est pas connu dans l'application", null, true);
		}
		return reponse;
	}

}
