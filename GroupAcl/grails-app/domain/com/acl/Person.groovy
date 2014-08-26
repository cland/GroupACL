package com.acl

import java.util.Set;

class Person {
	def groupManagerService
	String firstName
	String lastName
	static belongsTo = [office:Office]
	static hasMany = []
    static constraints = {
		
    }
	static mapping = {
		
	}
	static transients = [ 
		'hasLoginDetails',
		'primaryOffice',
		'authorities',
		'loginDetails' ]
	public boolean hasLoginDetails(){
		//work out if this person has a user account
		return (getLoginDetails() != null?true:false)
	}
	public User getLoginDetails(){
		return User.findByPerson(this)
	}
	public Office getPrimaryOffice(){
		//The office that this person belongs to
		def list = Office.createCriteria().list(){
		createAlias('staff',"s")
		eq('s.id',id)
	}
	Office o = null
		if(list.size() > 0) office = list.get(0)
		return o
	}

	public Set<RoleGroup> getAuthorities() {
		User u = getLoginDetails()
		if(!u) return []
		return u.authorities
	}
	
	String toString(){
		return firstName + " " + lastName +  " - " + office?.name
	}
}
