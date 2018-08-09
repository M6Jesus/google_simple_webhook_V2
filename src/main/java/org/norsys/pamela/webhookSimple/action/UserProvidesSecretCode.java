package org.norsys.pamela.webhookSimple.action;

import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.request.QueryResult;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.norsys.pamela.webhookSimple.model.response.Reponse;
import org.springframework.stereotype.Component;


@Component("UserProvides_secretCode")
public class UserProvidesSecretCode extends AbstractWebhookMethods{

	@Override
	public String getActionName() {
		return "UserProvides_secretCode";
	}

	@Override
	public DifferentTypeReponses getReponse(Request request) {
		QueryResult queryResult = request.getQueryResult();
		String valeurQuestion = queryResult.getQueryText();
		Reponse reponse = new Reponse();
		// String codeRentrer = arguments.getTextValue();

		reponse = creationReponse("nous avons rencontrer un probleme interne. Merci de réessayer plus tard",
				null, false);
		if (valeurQuestion.equals(codeSecret)) {
			String nouveauContexte = RecupereEtRenvoieLeNouveauOutputContext(request, "connexion_oki");
			reponse = creationReponse(
					"Vous avez rentrer le bon code. la connexion viens d'être établi avec l'application ",
					nouveauContexte, false);
		} else {
			String nouveauContexte = RecupereEtRenvoieLeNouveauOutputContext(request, "awaiting_codeSecret2");
			reponse = creationReponse("vous avez dit " + valeurQuestion
					+ " ceci n'est malhereusement pas le code associé à ce compte. Veuillez me donner un nouveau code. il vous reste "
					+ "deux" + " tentatives", nouveauContexte, false);
		}
		return reponse;
	}

}
