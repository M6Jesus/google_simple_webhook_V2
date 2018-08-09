package org.norsys.pamela.webhookSimple.action;

import org.norsys.pamela.webhookSimple.event.ArriverMessage;
import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.demandePermission.DemandePermission;
import org.norsys.pamela.webhookSimple.model.request.QueryResult;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.norsys.pamela.webhookSimple.model.response.Reponse;
import org.norsys.pamela.webhookSimple.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component("conversation-intent")
public class ConversationIntent extends AbstractWebhookMethods {
	
	@Autowired
	public MessageService messageService;
	
	@Override
	public String getActionName() {
		return "conversation-intent";
	}

	@Override
	public DifferentTypeReponses getReponse(Request request) {
		QueryResult queryResult = request.getQueryResult();
		String valeurQuestion = queryResult.getQueryText();
		// retourner le nom de l'utilisateur s'il le demande
		if (valeurQuestion.contains("nom") || valeurQuestion.contains("prénom")
				|| valeurQuestion.contains("m’appelle")) {
			// si le nom et le prenom sont deja enregistrer
			if (displayName != null) {
				Reponse reponse = creationReponse("Vous vous appelez " + displayName, null, false);
				if (valeurQuestion.contains("prénom")) {
					reponse = creationReponse("Votre prénom est " + prenom, null, false);
					return reponse;
				}
				return reponse;
			} else {
				// si le nom et prenom n'est pas encore enregistrer, je fais la demande de
				// permission pour reuperer
				DemandePermission demandePermission = demandePermission();
				return demandePermission;
			}

		}

		// *************************** requettes effectives a la base de donnée
		// *****************************************//

		// message de fin au cas ou je recois "merci google"
		if (valeurQuestion.contains("merci")) {
			Reponse reponse = creationReponse("je t'en prie pamela. Qu'est ce que je peux faire pour toi", null, false);
			return reponse;

		} else {
			ArriverMessage arriverMessage = new ArriverMessage(this, valeurQuestion);
			ResponseEntity<String> reponseApi = messageService.envoiMessage(arriverMessage);
			if (reponseApi != null) {
				String reponseApiString = reponseApi.getBody();

				// au cas ou j'ai une reponse en anglais true ou false
				if (reponseApiString.contains("true")) {
					reponseApiString = reponseApiString.replace("true", "oui, effectivement");
				} else if (reponseApiString.contains("false")) {
					reponseApiString = reponseApiString.replace("false", "non, pas du tout");
				}
				Reponse reponse = creationReponse(reponseApiString, null, false);
				return reponse;
			} else {
				Reponse reponse = creationReponse("desoler je n'ai pas cette information", null, false);

				return reponse;
			}
		}
	}

}
