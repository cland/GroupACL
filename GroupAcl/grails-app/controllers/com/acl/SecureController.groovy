package com.acl
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class SecureController {

    def index() { render 'Secure access only' }
	
	def admins(){
		render 'Admins only'
	}
	
	def users(){
		render 'all users'
	}
}
