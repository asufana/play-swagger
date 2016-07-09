package controllers.swagger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wordnik.swagger.models.Swagger;
import models.swagger.Swaggerton;
import play.mvc.*;
import play.mvc.results.*;

/**
 * @author Michael Ruf
 * @since 2015-05-03
 */
@SuppressWarnings("unused")
public class SwaggerController extends Controller {

    private static final ObjectMapper mapper;
    
    @Before
    static void checkAuthentification() {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.contentType = "application/json";
    }

    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static void json() throws RenderJson {
        Swagger swaggerObject = Swaggerton.get().getSwagger();

        if (swaggerObject.getHost() == null) {
            swaggerObject.setHost(request.host);
        }

        try {
            throw new RenderHtml(mapper.writeValueAsString(swaggerObject));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
