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
			 '/secure/**', 
			 '/logout/*']) {
			 new Requestmap( url: url, configAttribute: 'permitAll').save() 
		}
		
			 //admin
			 for (String url in [ '/requestmap/**',
				 '/role/**',
				 '/roleGroup/**',
				 '/user/**']) {
				 new Requestmap( url: url, configAttribute: 'ROLE_ADMIN,isFullyAuthenticated()').save()
			}
	} //end method
	
	private void createUsers() {
			def adminRole = new Role(authority:"ROLE_ADMIN").save(flush:true)
			def userRole = new Role(authority:"ROLE_USER").save(flush:true)
			
			def adminGroup = new RoleGroup(name:"GROUP_ADMIN").save(flush:true)
			def userGroup = new RoleGroup(name:"GROUP_USER").save(flush:true)
			
			RoleGroupRole.create adminGroup, adminRole
			RoleGroupRole.create userGroup, userRole
			
			3.times {
				long id = it + 1
				def user = new User(username: "user$id", enabled: true, password: "password$id").save(flush:true)
				UserRoleGroup.create user, userGroup
			}
		
			def admin = new User(username: 'admin', enabled: true, password: 'admin123').save(flush:true)
		
			UserRoleGroup.create admin, userGroup
			UserRoleGroup.create admin, adminGroup, true
		}
	
} //
