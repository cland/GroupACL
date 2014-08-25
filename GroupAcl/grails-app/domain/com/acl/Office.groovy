package com.acl

class Office {
	String name
	
	static hasMany = [staff:Person]
    static constraints = {
    }
	
	String toString(){
		return name
	}
}
