package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.RemovalPolicy;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.cognito.UserPool;
import software.amazon.awscdk.services.cognito.UserPoolClient;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.deployment.BucketDeployment;
import software.amazon.awscdk.services.s3.deployment.Source;
import software.amazon.awscdk.services.ssm.StringParameter;

import java.util.Arrays;
import java.util.Random;

public class ServerlessWorkshopWildrydesStack extends Stack {
    public ServerlessWorkshopWildrydesStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public ServerlessWorkshopWildrydesStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        UserPool userPool = UserPool.Builder.create(this, "WildRydesUserPool")
                .userPoolName("WildRydes")
                .build();

        UserPoolClient userPoolClient = UserPoolClient.Builder.create(this,"WildRydesAppClient")
                .userPool(userPool)
                .userPoolClientName("WildRydesApp")
                .generateSecret(false)
                .build();

        Bucket websiteBucket = Bucket.Builder.create(this, "WildRydesWebsiteBucket")
                .bucketName("wildrydes-yeehah-"+randomChars())
                .publicReadAccess(true)
                .websiteIndexDocument("index.html")
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        BucketDeployment websiteContents = BucketDeployment.Builder.create(this, "WildRydesWebsiteDeployment")
                .sources(Arrays.asList(Source.asset("./assets/website")))
                .destinationBucket(websiteBucket)
                .build();
    }

    private String randomChars() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
