<g:select name="queryId"
          from="${forgebiz.Query.list()}"
          value="${params.queryId}"
          optionValue="name"
          optionKey="id" />