package com.emulator.domain.soap.requestchain;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import com.emulator.exception.RequestChainIsNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RequestChainPool {

    private Map<AppUser, List<RequestChain>> pool = new HashMap<>();

    @Autowired
    SoapClient soapClient;

    public RequestChain createRequestChain(AppUser user) {
        List<RequestChain> chainList = null;

        if (pool.containsKey(user)) {
            chainList = pool.get(user);
        } else {
            chainList = new ArrayList<>();
            pool.put(user, chainList);
        }

        RequestChain requestChain = new RequestChain(user, soapClient);
        chainList.add(requestChain);
        return requestChain;
    }

    public RequestChain getRequestChain(AppUser user, String responseId) {
        return getChainList(user).stream()
                .filter(requestChain -> responseId.equals(requestChain.getResponseId()))
                .findFirst()
                .orElseThrow(() -> new RequestChainIsNotExistException("RequestChain with responseId=" + responseId +
                        " and user=" + user + " not found"));
    }

    public List<RequestChain> getChainList(AppUser user) {
        return pool.entrySet().stream()
                .filter(x -> user.equals(x.getKey()))
                .flatMap(x -> x.getValue().stream())
                .collect(Collectors.toList());
    }

}
