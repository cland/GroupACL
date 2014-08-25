import com.acl.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder as SCH

class BootStrap {

    def init = { servletContext ->
	//	initRequestmap()
	//	createUsers()
	//	loginAsAdmin()
		
		// logout
	//	SCH.clearContext()
    }
    def destroy = {
    }
	
	private void loginAsAdmin() {
		// have to be authenticated as an admin to create ACLs
		SCH.context.authentication = new UsernamePasswordAuthenticationToken( 'admin', 'admin123', AuthorityUtils.createAuthorityList('ROLE_ADMIN'))
	}
	
	private void initRequestmap(){
//		'/':                              ['permitAll'],
//		'/index':                         ['permitAll'],
//		'/index.gsp':                     ['permitAll'],
//		'/assets/**':                     ['permitAll'],
//		'/**/js/**':                      ['permitAll'],
//		'/**/css/**':                     ['permitAll'],
//		'/**/images/**':                  ['permitAll'],
//		'/**/favicon.ico':                ['permitAll']			
		
		for (String url in [ '/',
			 '/index',
			 '/index.gsp', 
			 '/**/favicon.ico', 
			 '/**/js/**', 
			 '/**/css/**', 
			 '/**/images/**', 
			 '/login', 
			 '/login.*', 
			 '/login/*', 
			 '/logout', 
			 '/logout.*', 
			 '/logout/*']) {
			 new Requestmap( url: url, configAttribute: 'permitAll').save() 
		}
	} //end method
	
	private void createUsers() {
			def adminRole = new Role(authority:"ROLE_ADMIN").save()
			def userRole = new Role(authority:"ROLE_USER").save()
			
			def adminGroup = new RoleGroup(authority:"GROUP_ADMIN").save()
			def userGroup = new RoleGroup(authority:"GROUP_USER").save()
			
			RoleGroupRole.create adminGroup, adminRole
			RoleGroupRole.create userGroup, userRole
			
			3.times {
				long id = it + 1
				def user = new User(username: "user$id", enabled: true, password: "password$id").save()
				UserRoleGroup.create user, userGroup
			}
		
			def admin = new User(username: 'admin', enabled: true, password: 'admin123').save()
		
			UserRoleGroup.create admin, userGroup
			UserRoleGroup.create admin, adminGroup, true
		}
	
} //
