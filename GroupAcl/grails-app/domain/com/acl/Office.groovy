package com.acl

class Office {
	String name
	String code
	
	static hasMany = [staff:Person]
    static constraints = {
		name unique:true, blank:false
		code unique:true, blank:false
    }
	
	String toString(){
		return name
	}
}
