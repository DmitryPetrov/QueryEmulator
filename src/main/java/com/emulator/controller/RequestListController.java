package com.emulator.controller;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RequestListController extends AbstractController{

    private static Logger log = LoggerFactory.getLogger(RequestListController.class);

    private static final String URI = "/request/list";

    @Autowired
    private RequestChainPool chainPool;

    @GetMapping(URI)
    @ResponseBody
    public ResponseBodyData getRequestChainList(HttpSession httpSession) {
        log.info("Request uri='" + URI + "'");
        try {
            AppUser user = getUser(httpSession);
            List<RequestChain> chainList = chainPool.getChainList(user);
            return getSuccessResponse(chainList);
        } catch (UserIsNotAuthorizedException e) {
            return getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return getServerFailResponse(e);
        }
    }

    private ResponseBodyData getSuccessResponse(List<RequestChain> chainList) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Request list");
        result.setRequestChainList(chainList);

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain) {
        return null;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception, RequestChain
            chain) {
        return null;
    }

}
