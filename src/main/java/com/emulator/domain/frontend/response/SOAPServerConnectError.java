package com.emulator.domain.frontend.response;

import java.util.ArrayList;
import java.util.List;

public class SOAPServerConnectError {

    private String error;
    private List<String> massageTrace;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getMassageTrace() {
        return massageTrace;
    }

    public void setMassageTrace(List<String> massageTrace) {
        this.massageTrace = massageTrace;
    }

    public void addMessage(String message) {
        if (this.massageTrace == null) {
            this.massageTrace = new ArrayList<>();
        }

        this.massageTrace.add(message);
    }


    @Override
    public String toString() {
        return "SOAPServerConnectError{" +
                "error='" + error + '\'' +
                ", massageTrace=" + massageTrace +
                '}';
    }
}
