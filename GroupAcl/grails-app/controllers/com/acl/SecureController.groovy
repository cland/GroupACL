package com.acl
import grails.plugin.springsecurity.annotation.Secured


class SecureController {
	def springSecurityService
		
    def index() {
		User curuser = springSecurityService.getCurrentUser()	
	      String username = curuser?.username //principal.username
	      Set<RoleGroup> authorities = UserRoleGroup.findAllByUser(curuser).collect { it.roleGroup } // principal.authorities // a Collection of GrantedAuthority
	     boolean enabled = curuser.enabled //principal.enabled
		  
		
		 authorities.each{grp ->			 
			 println ">> " + grp?.name
			 println "\n-> " + grp?.getAuthorities()?.authority
		 }
		// println(authorities*.getAuthorities()?.authority)
		  //check group
		  
		render 'Secure access only ' + username + " [" + authorities + "] - " + isLoggedIn() + " "
	}
	
	
	def everyone(){
		render 'For everyone'
	} 
	
	
	def users(){
		render 'all users'
	}
}
