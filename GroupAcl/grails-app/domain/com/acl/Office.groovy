package com.acl

class Office {
	String name
	String code
	def groupManagerService
	static transients = ["officeGroups"]
	static hasMany = [staff:Person]
    static constraints = {
		name unique:true, blank:false
		code unique:true, blank:false
    }
	
	List<RoleGroup> getOfficeGroups(){
		return groupManagerService.getOfficeGroups(this)
	}
	String toString(){
		return name
	}
}
