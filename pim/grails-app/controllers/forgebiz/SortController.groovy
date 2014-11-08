package forgebiz

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class SortController {

 static scaffold = true
}
