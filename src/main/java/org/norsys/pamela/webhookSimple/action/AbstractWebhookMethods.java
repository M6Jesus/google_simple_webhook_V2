package org.norsys.pamela.webhookSimple.action;

import java.util.ArrayList;
import java.util.List;

import org.norsys.pamela.webhookSimple.model.demandePermission.Data;
import org.norsys.pamela.webhookSimple.model.demandePermission.DemandePermission;
import org.norsys.pamela.webhookSimple.model.demandePermission.GoogleDemandePermission;
import org.norsys.pamela.webhookSimple.model.demandePermission.PayloadDemandePermission;
import org.norsys.pamela.webhookSimple.model.demandePermission.SystemIntent;
import org.norsys.pamela.webhookSimple.model.request.OutputContexts;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.norsys.pamela.webhookSimple.model.response.Google;
import org.norsys.pamela.webhookSimple.model.response.Items;
import org.norsys.pamela.webhookSimple.model.response.OutputContextsReponse;
import org.norsys.pamela.webhookSimple.model.response.Payload;
import org.norsys.pamela.webhookSimple.model.response.Reponse;
import org.norsys.pamela.webhookSimple.model.response.RichResponse;
import org.norsys.pamela.webhookSimple.model.response.SimpleResponse;

public abstract class AbstractWebhookMethods implements WebhookAction {

	String adresse;
	String displayName;
	String prenom;
	String codeSecret = "1234";
	String prenomSecurite;

	/**
	 * 
	 * @param messageReponse:
	 *            la reponse audible a retourner à l'utilisateur
	 * @param outputContext:
	 *            la valeur du parametre "name" de l'outputContext
	 * @param isEndOfConversation:
	 *            booleen qui permet de creer une reponse qui est une fin de
	 *            conversation
	 * @return un objet reponse pour la plateforme dialogflow
	 */
	Reponse creationReponse(final String messageReponse, final String outputContext,
			final boolean isEndOfConversation) {

		Reponse reponse = new Reponse();
		SimpleResponse simpleResponse = new SimpleResponse();
		simpleResponse.setTextToSpeech(messageReponse);
		simpleResponse.setDisplayText(messageReponse);

		Items items = new Items();
		items.setSimpleResponse(simpleResponse);

		RichResponse richResponse = new RichResponse();
		List<Items> liste = new ArrayList<>();
		liste.add(items);
		richResponse.setItems(liste);
		Google google = new Google();
		google.setExpectUserResponse(true);
		if (isEndOfConversation == true) {
			// ** dans le cas ou je souhaite fermer la conversation***//
			google.setExpectUserResponse(false);
		}
		google.setRichResponse(richResponse);
		Payload payload = new Payload();
		payload.setGoogle(google);
		reponse.setPayload(payload);

		org.norsys.pamela.webhookSimple.model.response.FulfillmentMessages fulfillmentMessages = new org.norsys.pamela.webhookSimple.model.response.FulfillmentMessages();

		org.norsys.pamela.webhookSimple.model.response.Text texte = new org.norsys.pamela.webhookSimple.model.response.Text();

		List<String> listess = new ArrayList<>();
		listess.add(messageReponse);
		texte.setText(listess);
		fulfillmentMessages.setText(texte);

		List<org.norsys.pamela.webhookSimple.model.response.FulfillmentMessages> listes = new ArrayList<>();
		listes.add(fulfillmentMessages);
		reponse.setFulfillmentMessages(listes);
		if (outputContext != null) {
			OutputContextsReponse outputCont = new OutputContextsReponse();
			outputCont.setLifespanCount(1);
			if (outputContext.contains("connexion_oki")) {
				outputCont.setLifespanCount(5);
			}
			outputCont.setName(outputContext);
			List<OutputContextsReponse> leOutput = new ArrayList<>();
			leOutput.add(outputCont);
			reponse.setOutputContexts(leOutput);
		}
		return reponse;
	}

	/**
	 * 
	 * @param request
	 *            un objet requete provenant de la plateforme dialogflow
	 * @param nouveauOUtPutContext
	 *            le nom de l'ouputContexte vers lequel je veux faire pointé mon
	 *            intent
	 * @return la valeur du parametre "name" dans l'objet reponse
	 */
	public String RecupereEtRenvoieLeNouveauOutputContext(final Request request, final String nouveauOUtPutContext) {
		OutputContexts outputContexts = request.getQueryResult().getOutputContexts().get(0);
		// ici je recupere le nom de l'ouputcontext avec toutes données de projetId,
		// session... ce que je veux c'est changer la fin de cette String en mettant le
		// nouveau contexte souhaiter
		String name = outputContexts.getName();
		// enlever le contexte present
		int indexDernierSlash = name.lastIndexOf('/');
		String chaineSansContexte = name.substring(0, indexDernierSlash + 1);
		// je concatene le nouveau contexte
		String nouveauName = chaineSansContexte + nouveauOUtPutContext;
		return nouveauName;
	}

	/**
	 * 
	 * @return un objet reponse qui est une demande de permission a l'utilisateur
	 *         pour l'utilisation de ses données personelles
	 */
	public DemandePermission demandePermission() {
		Data data = new Data();
		data.setType("type.googleapis.com/google.actions.v2.PermissionValueSpec");
		data.setOptContext("connaitre ta position");
		String[] permissions = { "NAME", "DEVICE_PRECISE_LOCATION" };
		data.setPermissions(permissions);

		SystemIntent systemIntent = new SystemIntent();
		systemIntent.setData(data);
		systemIntent.setIntent("actions.intent.PERMISSION");

		GoogleDemandePermission googleDemandePermission = new GoogleDemandePermission();
		googleDemandePermission.setExpectUserResponse(true);
		googleDemandePermission.setSystemIntent(systemIntent);

		PayloadDemandePermission payloadDemandePermission = new PayloadDemandePermission();
		payloadDemandePermission.setGoogle(googleDemandePermission);

		DemandePermission demandePermission = new DemandePermission();
		demandePermission.setPayload(payloadDemandePermission);
		return demandePermission;
	}
	
	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getCodeSecret() {
		return codeSecret;
	}

	public void setCodeSecret(String codeSecret) {
		this.codeSecret = codeSecret;
	}

	public String getPrenomSecurite() {
		return prenomSecurite;
	}

	public void setPrenomSecurite(String prenomSecurite) {
		this.prenomSecurite = prenomSecurite;
	}

}
