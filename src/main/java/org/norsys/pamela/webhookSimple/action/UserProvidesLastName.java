package org.norsys.pamela.webhookSimple.action;

import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.request.QueryResult;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.norsys.pamela.webhookSimple.model.response.Reponse;
import org.springframework.stereotype.Component;


@Component("UserProvides_LastName")
public class UserProvidesLastName extends AbstractWebhookMethods{

	@Override
	public String getActionName() {
		return "UserProvides_LastName";
	}

	@Override
	public DifferentTypeReponses getReponse(Request request) {
		QueryResult queryResult = request.getQueryResult();
		String valeurQuestion = queryResult.getQueryText();
		Reponse reponse = new Reponse();
		String nouveauContexte = RecupereEtRenvoieLeNouveauOutputContext(request, "awaiting_codeSecret");
		reponse = creationReponse("nous avons rencontrer un probleme interne. Merci de réessayer plus tard",
				null, true);
		String noms = valeurQuestion.toLowerCase();
		if (noms.equals("anou")) {
			reponse = creationReponse("vous m'avez dit " + noms + ". j'enregistre Ce nom. Merci de me donner le code secret associé à ce nom pour que j'établisse une connexion sécurisé", nouveauContexte, false);
			prenomSecurite = noms;
		}
		else {
			//***si le nom n'est pas reconnu, je le notifie et je ferme la connexion ****//
			reponse = creationReponse("Desoler. vous m'avez dit " + prenomSecurite + " "+ noms + ". ce prénom n'est pas connu dans l'application", null, true);
			
		}
		return reponse;
	}

}
