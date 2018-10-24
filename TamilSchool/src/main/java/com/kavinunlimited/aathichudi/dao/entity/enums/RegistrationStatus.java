package com.kavinunlimited.aathichudi.dao.entity.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RegistrationStatus {
	@JsonProperty("User Input pending")
	PENDING_USER,
	@JsonProperty("Admin Review pending")
	PENDING_ADMIN,
	@JsonProperty("Complete")
	COMPLETE

}
