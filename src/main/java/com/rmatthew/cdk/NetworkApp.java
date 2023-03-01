package com.rmatthew.cdk;

import dev.stratospheric.cdk.Network;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import static com.rmatthew.cdk.Validations.validateNotEmpty;

public class NetworkApp {

  public static void main(final String[] args) {
    App app = new App();

    String environmentName = (String) app.getNode().tryGetContext("environmentName");
    validateNotEmpty(environmentName, "context variable 'environmentName' must not be null");

    String accountId = (String) app.getNode().tryGetContext("accountId");
    validateNotEmpty(accountId, "context variable 'accountId' must not be null");

    String region = (String) app.getNode().tryGetContext("region");
    validateNotEmpty(region, "context variable 'region' must not be null");

    String sslCertificateArn = (String) app.getNode().tryGetContext("sslCertificateArn");
//    requireNonEmpty(sslCertificateArn, "context variable 'sslCertificateArn' must not be null");

    Environment awsEnvironment = makeEnv(accountId, region);

    Stack networkStack = new Stack(app, "NetworkStack", StackProps.builder()
      .stackName(environmentName + "-Network")
      .env(awsEnvironment)
      .build());

    Network network = new Network(
      networkStack,
      "Network",
      awsEnvironment,
      environmentName,
      new Network.NetworkInputParameters(sslCertificateArn));

    app.synth();
  }

  static Environment makeEnv(String account, String region) {
    return Environment.builder()
      .account(account)
      .region(region)
      .build();
  }

}
