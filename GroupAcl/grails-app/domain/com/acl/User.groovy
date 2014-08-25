package com.acl

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Person person
	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
		person nullable:true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<RoleGroup> getAuthorities() {
		UserRoleGroup.findAllByUser(this).collect { it.roleGroup }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
