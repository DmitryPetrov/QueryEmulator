package com.emulator.domain.login;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.prelogin.PreLoginResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientAuthDataBuilder {

    private final String USE_2048_BIT_SRP = "false";

    public ClientAuthData build(AppUser user, PreLoginResult preLoginResult) throws IOException {
        List<String> command = buildCommand(user, preLoginResult);

        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();

        ClientAuthData result = getResult(process);

        return result;
    }

    private ClientAuthData getResult(Process process) throws IOException {
        ClientAuthData authData = new ClientAuthData();

        // clean up if any output in stderr
        InputStream stderr = process.getErrorStream ();
        BufferedReader stderrReader = new BufferedReader (new InputStreamReader (stderr));
        String line;
        while ((line = stderrReader.readLine ()) != null) {
            System.out.println ("[Stderr] " + line);
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
        command.add(user.getUserName());
        command.add(user.getPassword());
        command.add(preLoginResult.getSalt().toString());
        command.add(preLoginResult.getBytesFromServer().toString());
        command.add(USE_2048_BIT_SRP);

        return command;
    }
}
