/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web;

import java.util.NoSuchElementException;
import javax.annotation.Priority;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;

/**
 *
 * @author michal
 */
@Provider
@Priority(1)
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {
    @Override
    public Response toResponse(NoSuchElementException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", "no_json_body_exception"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
    }
}