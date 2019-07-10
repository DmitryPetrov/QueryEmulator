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

    public Map<String, String> build(AppUser user, PreLoginResult preLoginResult) throws IOException {
        List<String> command = buildCommand(user, preLoginResult);

        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();

        Map<String, String> result = getResult(process);

        return result;
    }

    private Map<String, String> getResult(Process process) throws IOException {
        Map<String, String> result = new HashMap<>();

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
                result.put("passwordHash", passwordHash);
            }

            if(line.contains("extPasswordData")) {
                String[] array = line.split(" ");
                String extPasswordData = array[(array.length - 1)];
                result.put("extPasswordData", extPasswordData);
            }
        }
        stdoutReader.close();

        return result;
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
        command.add("true");

        return command;
    }
}
