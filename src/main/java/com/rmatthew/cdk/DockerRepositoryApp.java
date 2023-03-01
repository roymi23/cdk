package com.rmatthew.cdk;

import dev.stratospheric.cdk.DockerRepository;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class DockerRepositoryApp {

    public static void main(String[] args) {

        App app = new App();

        String accountId = (String)app.getNode().tryGetContext("accountId");
        Validations.validateNotEmpty(accountId, "context variable 'accountId' must not be null");
        String region = (String)app.getNode().tryGetContext("region");
        Validations.validateNotEmpty(region, "context variable 'region' must not be null");
        String applicationName = (String)app.getNode().tryGetContext("applicationName");
        Validations.validateNotEmpty(applicationName, "context variable 'applicationName' must not be null");

        Environment awsEnvironment = makeEnv(accountId, region);

        Stack dockerRepositoryStack = new Stack(app, "DockerRepositoryStack", StackProps.builder()
                .stackName(applicationName + "-DockerRepository")
                .env(awsEnvironment)
                .build());
        DockerRepository dockerRepository = new DockerRepository(
                dockerRepositoryStack,
                "DockerRepository",
                awsEnvironment,
                new DockerRepository.DockerRepositoryInputParameters(applicationName, accountId)
        );

        app.synth();

    }

    static Environment makeEnv(String accountId, String region) {

        return Environment.builder()
                .account(accountId)
                .region(region)
                .build();
    }
}
