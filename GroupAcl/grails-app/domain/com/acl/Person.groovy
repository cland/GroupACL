package com.acl

class Person {
	String name

	static belongsTo = [office:Office]
	static hasMany = []
    static constraints = {
		
    }
	static mapping = {
		phones cascade:"all-delete-orphan"
	}
	
	String toString(){
		return name + " - " + office?.name
	}
}
