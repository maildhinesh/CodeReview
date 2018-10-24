package com.kavinunlimited.aathichudi.dao.entity.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserStatus {
	@JsonProperty("Inactive")
	INACTIVE,
	@JsonProperty("Active")
	ACTIVE,
	@JsonProperty("Locked")
	LOCKED
	
}
