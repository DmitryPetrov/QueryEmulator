package com.emulator.domain.requestchain;

import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.exception.ParameterIsNullException;
import com.emulator.exception.RequestChainIsNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RequestChainPool {

    private static Logger log = LoggerFactory.getLogger(RequestChainPool.class);

    private Map<AppUser, List<RequestChain>> pool = new HashMap<>();

    @Autowired
    SoapClient soapClient;

    public StatementRequestChain createStatementRequestChain(AppUser user) {
        log.debug("Created new StatementRequestChain for user." + user);
        return new StatementRequestChain(user, soapClient);
    }

    public PayRequestChain createPayRequestChain(AppUser user) {
        log.debug("Created new PayRequestChain for user." + user);
        return new PayRequestChain(user, soapClient);
    }

    public void addToPool(RequestChain chain) {
        if (chain == null) {
            throw new ParameterIsNullException("RequestChain must not be null");
        }
        AppUser user = chain.getUser();

        List<RequestChain> chainList = null;
        if (pool.containsKey(user)) {
            chainList = pool.get(user);
        } else {
            chainList = new ArrayList<>();
            pool.put(user, chainList);
        }

        log.debug("RequestChain(responseId=" + chain.getResponseId() + ") was added to RequestChainPool");
        chainList.add(chain);
    }

    public RequestChain getRequestChain(AppUser user, String responseId) {
        return getChainList(user).stream()
                .filter(requestChain -> responseId.equals(requestChain.getResponseId()))
                .findFirst()
                .orElseThrow(() -> new RequestChainIsNotExistException("RequestChain with responseId=" + responseId +
                        " and user=" + user + " not found"));
    }

    public RequestChain getRequestChain(AppUser user, IncomingData data) {
        return getRequestChain(user, data.getAttrRequestId());
    }

        public List<RequestChain> getChainList(AppUser user) {
        return pool.entrySet().stream()
                .filter(x -> user.equals(x.getKey()))
                .flatMap(x -> x.getValue().stream())
                .collect(Collectors.toList());
    }
}
