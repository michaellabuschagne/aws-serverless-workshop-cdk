package com.myorg;

import software.amazon.awscdk.core.App;

import java.util.Arrays;

public class ServerlessWorkshopWildrydesApp {
    public static void main(final String[] args) {
        App app = new App();

        new ServerlessWorkshopWildrydesStack(app, "ServerlessWorkshopWildrydesStack");

        app.synth();
    }
}
