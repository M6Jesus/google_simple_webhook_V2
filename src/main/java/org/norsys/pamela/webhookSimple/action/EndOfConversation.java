package org.norsys.pamela.webhookSimple.action;

import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.request.QueryResult;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.norsys.pamela.webhookSimple.model.response.Reponse;
import org.springframework.stereotype.Component;

@Component("EndOf_conversation")
public class EndOfConversation extends AbstractWebhookMethods {

	@Override
	public String getActionName() {
		return "EndOf_conversation";
	}

	@Override
	public DifferentTypeReponses getReponse(Request request) {
		QueryResult queryResult = request.getQueryResult();
		String valeurQuestion = queryResult.getQueryText();
		Reponse reponse = new Reponse();
		reponse = creationReponse("nous avons rencontrer un probleme interne. Merci de réessayer plus tard", null,
				false);
		if (valeurQuestion.equals(codeSecret)) {
			String nouveauContexte = RecupereEtRenvoieLeNouveauOutputContext(request, "connexion_oki");
			reponse = creationReponse(
					"Vous avez rentrer le bon code. la connexion viens d'être établi avec l'application ",
					nouveauContexte, false);
		} else {
			reponse = creationReponse(
					"Désoler vous n'avez pas donner le bon code. vous n'êtes pas autoriser à vous connecter. Aurevoir!",
					null, true);
		}
		return reponse;
	}

}
