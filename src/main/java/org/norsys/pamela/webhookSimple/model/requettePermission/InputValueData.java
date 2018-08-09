package org.norsys.pamela.webhookSimple.model.requettePermission;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
@JsonPropertyOrder({"@type", "optContext", "permissions"})
public class InputValueData {
	
	@JsonTypeInfo(
			use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type",
            visible = true
			) 
	@JsonProperty("@type")
	private final String type = "type.googleapis.com/google.actions.v2.PermissionValueSpec";
	public String getType() {
		return type;
	}
	
	
	
	@JsonProperty("optContext")
	private String optContext;
	public String getOptContext() {
		return optContext;
	}
	public void setOptContext(String optContext) {
		this.optContext = optContext;
	}
	
	
	@JsonProperty("permissions")
	private String permissions[];
	public String[] getPermissions() {
		return permissions;
	}
	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}
	
	
	
	

}
