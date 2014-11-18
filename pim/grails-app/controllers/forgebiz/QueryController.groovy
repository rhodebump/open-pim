package forgebiz

import grails.plugin.springsecurity.annotation.Secured


@Secured(['ROLE_ADMIN'])
class QueryController {

  static scaffold = true
}
