package com.acl

class RoleGroup {

	String name
	String description
	static mapping = {
		cache true
	}

	Set<Role> getAuthorities() {
		RoleGroupRole.findAllByRoleGroup(this).collect { it.role }
	}

	static constraints = {
		name blank: false, unique: true
		description nullable:true
	}
	
	String toString(){
		(!description?.equals("")? description : name)
	}
}
