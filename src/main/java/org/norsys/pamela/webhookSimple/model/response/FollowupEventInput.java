package org.norsys.pamela.webhookSimple.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * cette classe represente l'objet FollowupEventInput d'une reponse
 * @author panou
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name", "languageCode", "parameters"})
public class FollowupEventInput {
	
	@JsonProperty("name")
	private String name;
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	
	@JsonProperty("languageCode")
	private String languageCode;
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(final String languageCode) {
		this.languageCode = languageCode;
	}

	
	@JsonProperty("parameters")
	private ParametersReponse parameters;
	public ParametersReponse getParameters() {
		return parameters;
	}
	public void setParameters(final ParametersReponse parameters) {
		this.parameters = parameters;
	}
	
}
