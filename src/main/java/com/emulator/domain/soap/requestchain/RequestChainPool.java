package com.emulator.domain.soap.requestchain;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import com.emulator.exception.RequestChainIsNotExistException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RequestChainPool {

    private List<RequestChain> chains = new ArrayList<>();

    public RequestChain createRequestChain(AppUser user) {
        RequestChain requestChain = new RequestChain(user);
        chains.add(requestChain);
        return requestChain;
    }

    public RequestChain getRequestChain(AppUser user, String responseId) {
        for (RequestChain requestChain : chains) {
            if (!requestChain.getResponseId().equals(responseId)) {
                continue;
            }
            if (!requestChain.getUser().equals(user)) {
                continue;
            }
            return requestChain;
        }
        throw new RequestChainIsNotExistException("RequestChain with responseId=" + responseId + " and user=" + user
                + " not found");
    }

    public List<RequestChain> getChainList(AppUser user) {
        List<RequestChain> result = new ArrayList<>();
        for (RequestChain requestChain : chains) {
            if (requestChain.getUser().equals(user)) {
                result.add(requestChain);
            }
        }
        return result;
    }

}
