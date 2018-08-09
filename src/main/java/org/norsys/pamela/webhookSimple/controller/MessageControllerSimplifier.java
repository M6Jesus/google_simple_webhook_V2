package org.norsys.pamela.webhookSimple.controller;

import java.util.ArrayList;
import java.util.List;

import org.norsys.pamela.webhookSimple.action.ConversationIntent;
import org.norsys.pamela.webhookSimple.action.DefaultAction;
import org.norsys.pamela.webhookSimple.action.EndOfConversation;
import org.norsys.pamela.webhookSimple.action.RequestPermissionAction;
import org.norsys.pamela.webhookSimple.action.SignIn;
import org.norsys.pamela.webhookSimple.action.UserInfos;
import org.norsys.pamela.webhookSimple.action.UserProvidesFirstName;
import org.norsys.pamela.webhookSimple.action.UserProvidesLastName;
import org.norsys.pamela.webhookSimple.action.UserProvidesSecretCode;
import org.norsys.pamela.webhookSimple.action.UserProvidesSecretCode2;
import org.norsys.pamela.webhookSimple.action.WebhookAction;
import org.norsys.pamela.webhookSimple.model.InterfaceReponses.DifferentTypeReponses;
import org.norsys.pamela.webhookSimple.model.request.QueryResult;
import org.norsys.pamela.webhookSimple.model.request.Request;
import org.norsys.pamela.webhookSimple.model.response.Reponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageControllerSimplifier {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageControllerSimplifier.class);
	@Autowired
	UserProvidesFirstName userProvidesFirstName;
	@Autowired
	UserProvidesLastName userProvidesLastName;
	@Autowired
	UserProvidesSecretCode userProvidesSecretCode;
	@Autowired
	UserProvidesSecretCode2 userProvidesSecretCode2;
	@Autowired
	EndOfConversation endOfConversation;
	@Autowired
	RequestPermissionAction requestPermissionAction;
	@Autowired
	UserInfos userInfos;
	@Autowired
	SignIn signIn;
	@Autowired
	ConversationIntent conversationIntent;
	@Autowired
	DefaultAction defaultAction;
	
	List<WebhookAction> webhookActions = new ArrayList<>();	
	private void addAllActions() {
		webhookActions.add(userProvidesFirstName);
		webhookActions.add(userProvidesLastName);
		webhookActions.add(userProvidesSecretCode);
		webhookActions.add(userProvidesSecretCode2);
		webhookActions.add(endOfConversation);
		webhookActions.add(requestPermissionAction);
		webhookActions.add(userInfos);
		webhookActions.add(signIn);
		webhookActions.add(conversationIntent);	
	}
	
	/**
	 * 
	 * @param request:
	 *            reprensente l'objet requete provenant de dialogflow
	 * @return ResponseEntity<DifferentTypeReponses> a la requette
	 */
	@PostMapping("/webhook2")
	public ResponseEntity<DifferentTypeReponses> posterUnMessage(@RequestBody final Request request) {	
		if(webhookActions.isEmpty()) {
			addAllActions();
			logger.info("chargement des actions");
		}
		
		QueryResult queryResult = request.getQueryResult();
		String actionToDo = queryResult.getAction();
		
		DifferentTypeReponses reponse = defaultAction.getReponse(request);
		for(WebhookAction action : webhookActions) {
			if(action.getActionName().equals(actionToDo)) {
				reponse = action.getReponse(request);
				logger.info("execution de l'action '{}' ", action.getActionName());
			}		
		}
		return ResponseEntity.status(HttpStatus.OK).body(reponse);
	}
}
