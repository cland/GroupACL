package com.acl

public enum SystemRoles {
	//SUPER ADMIN
	ROLE_ADMIN("ROLE_ADMIN","System Admin"),
	ROLE_DEVELOPER("ROLE_DEVELOPER","System Developer"),	
	
	//NATIONAL LEVEL
	ROLE_NCO("ROLE_NCO","National Co-Ordinator"),
	ROLE_MEO("ROLE_MEO","Monitoring and Evaluation"),
	
	//REGIONAL LEVEL
	ROLE_PCO("ROLE_PCO","Provincial Co-Ordinator"),
	
	//OFFICE LEVEL
	ROLE_OCO("ROLE_OCO","Office Co-Ordinator"),	
	ROLE_CWO("ROLE_CWO","Case Worker Officer"),
	ROLE_SPO("ROLE_SPO","Special Case Worker"),
	ROLE_REVIEWER("ROLE_REVIEWER","View Only")
	
	final String value;
	final String description;
	SystemRoles(String value) {
		this.value = value;
		this.description = "";
	}
	SystemRoles(String value,String desc) {
		this.value = value;
		this.description = desc;
	}
	String toString(){
		value;
	}
	String getDescription(){
		return description
	}
	String getKey(){
		name()
	}

	static list() {
		[ROLE_REVIEWER,ROLE_ADMIN,ROLE_DEVELOPER,
			ROLE_NCO,
			ROLE_PCO,
			ROLE_OCO,
			ROLE_CWO,
			ROLE_SPO,
			ROLE_MEO
			]
	}
}
