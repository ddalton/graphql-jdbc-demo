package tools.xor;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.xor.service.AggregateManager;
import tools.xor.service.GraphQLService;
import tools.xor.service.Shape;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@RestController
@RequestMapping("/graphql")
public class GraphQLController {

    @Resource(name = "aggregateManager")
    protected AggregateManager aggregateService;

    private Shape dynamicShape;

    @PostConstruct
    public void createShape() {
        dynamicShape = aggregateService.getDataModel().createShape("DOMAIN_SHAPE");
        GraphQLTypeMapper typeMapper = (GraphQLTypeMapper)aggregateService.getTypeMapper();
        this.dynamicShape = typeMapper.getDynamicShape();
    }

    /**
     * When receiving an HTTP GET request, the GraphQL query should be specified in
     * the "query" query string
     * Query variables can be sent as a JSON-encoded string in an additional query parameter
     * called variables. If the query contains several named operations,
     * an operationName query parameter can be used to control which one should be executed.
     *
     * If the query parameter is not provided teturn the GraphQL schema
     * in Schema Definition Language (SDL) format
     *
     * @return schema in SDL format
     */
    @GetMapping
    public String getSchema(@RequestParam String query,
                            @RequestParam String variables,
                            @RequestParam String operationName) {
        GraphQLService graphQLService = new GraphQLService(this.dynamicShape);

        return graphQLService.getSchema();
    }

    /**
     * There are 2 ways a POST request is sent
     * 1. using Content-Type application/json
     *    In this method the JSON-encoded body should be of the following form
     *    {
     *       "query": "...",
     *       "operationName": "...",
     *       "variables": { "myVariable": "someValue", ... }
     *    }
     *
     * 2. using Content-Type application/graphql
     *    The HHTP POST body should contain the query string
     *
     * @param postbody representing the post body
     * @return the result
     */
    @PostMapping(headers = "content-type=application/json")
    public Object executeRequest(@RequestBody String postbody) {
        GraphQLService graphQLService = new GraphQLService(dynamicShape);

        JSONObject json = new JSONObject(postbody);
        if(json.has("query")) {
            return graphQLService.executeRequest(json.getString("query"));
        }

        return "Not a valid GraphQL request";
    }

    // TODO: create another POST method handler to support application/graphql header
}
