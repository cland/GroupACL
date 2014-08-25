package com.acl

class Person {
	String firstName
	String lastName
	static belongsTo = [office:Office]
	static hasMany = []
    static constraints = {
		
    }
	static mapping = {
		
	}
	
	String toString(){
		return name + " - " + office?.name
	}
}
