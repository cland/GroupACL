package com.acl

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

	String toString(){
		return firstName + " " + lastName +  " - " + office?.name
	}
}
