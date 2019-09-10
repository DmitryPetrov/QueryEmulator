package com.emulator.domain.soap.requests.authorization.login;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.authorization.AuthorizationManager;
import com.emulator.domain.soap.requests.authorization.prelogin.PreLoginResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClientAuthDataBuilder {

    private static Logger log = LoggerFactory.getLogger(ClientAuthDataBuilder.class);

    private final String USE_2048_BIT_SRP = "true";

    public ClientAuthData build(AppUser user, PreLoginResult preLoginResult) throws IOException {
        List<String> command = buildCommand(user, preLoginResult);

        log.debug("Build client auth data with parameters=" + command);
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();

        ClientAuthData authData = getResult(process);

        log.info("Client auth data was built " + authData);
        return authData;
    }

    private ClientAuthData getResult(Process process) throws IOException {
        ClientAuthData authData = new ClientAuthData();

        // clean up if any output in stderr
        InputStream stderr = process.getErrorStream ();
        BufferedReader stderrReader = new BufferedReader (new InputStreamReader (stderr));
        String line;
        while ((line = stderrReader.readLine ()) != null) {
            log.error("Error in building client auth data " + line);
        }
        stderrReader.close();

        // clean up if any output in stdout
        InputStream stdout = process.getInputStream ();
        BufferedReader stdoutReader = new BufferedReader (new InputStreamReader(stdout));
        while ((line = stdoutReader.readLine ()) != null) {
            if(line.contains("passwordHash")) {
                String[] array = line.split(" ");
                String passwordHash = array[(array.length - 1)];
                authData.setPasswordHash(passwordHash);
            }

            if(line.contains("extPasswordData")) {
                String[] array = line.split(" ");
                String extPasswordData = array[(array.length - 1)];
                authData.setExtPasswordData(extPasswordData);
            }
        }
        stdoutReader.close();

        return authData;
    }

    private List<String> buildCommand(AppUser user, PreLoginResult preLoginResult) {
        List<String> command = new ArrayList<String>();
        command.add("java");
        command.add("-jar");
        command.add("srp-1.1-SNAPSHOT-shaded.jar");
        command.add(preLoginResult.getUserHashString());
        command.add(user.getPassword());
        command.add(preLoginResult.getSaltString());
        command.add(preLoginResult.getBytesFromServerString());
        command.add(USE_2048_BIT_SRP);

        return command;
    }
}
