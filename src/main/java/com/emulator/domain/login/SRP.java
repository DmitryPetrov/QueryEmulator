package com.emulator.domain.login;

import com.emulator.domain.prelogin.PreLoginResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SRP {

/*    private String login = null;
    private String password = null;
    private String salt = null;
    private String bytesFromServer = null;

    private AppUser user;
    private PreLoginResult preLoginResult;

    {
        login = "rtk";
        password = "N";
        salt = "ob+TXc2f9w7n7w==";
        bytesFromServer = "Sd3U3BCcMGD0Gkeq0Ksmx8EJVeON1zw6Ogvqzf6sQVQ=";
    }

    public SRP() {

    }

    public Map<String, String> run(AppUser user) throws IOException {
        List<String> command = buildCommand();

        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();

        Map<String, String> result = getResult(process);

        System.out.println("---->> " + result);

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
            System.out.println ("[Stdout] " + line);

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

    private List<String> buildCommand() {
        List<String> command = new ArrayList<String>();
        command.add("java");
        command.add("-jar");
        command.add("srp-1.0-SNAPSHOT-shaded.jar");
        command.add(login);
        command.add(password);
        command.add(salt);
        command.add(bytesFromServer);

        return command;
    }

    private String getLogin() {
        return user.getUserName();
    }

    private String getPassword() {
        return user.getPassword();
    }

    private String getSalt() {
        return preLoginResult.getSaltString();
    }

    private String getBytesFromServer() {
        return preLoginResult.getBytesFromServerString();
    }*/
}
