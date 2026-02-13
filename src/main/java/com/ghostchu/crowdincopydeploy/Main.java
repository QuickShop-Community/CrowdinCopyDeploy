package com.ghostchu.crowdincopydeploy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        LOG.info("CrowdinCopyDeploy v1.0.0");
        LOG.info("Starting up...");
        new CrowdinDeployer(new CrowdinAPI());
        String storageHandler = System.getenv("STORAGE_HANDLER");

        if(StringUtils.isEmpty(storageHandler)){
            storageHandler = "bunny";
        }

        if(storageHandler.equalsIgnoreCase("aws")
                && !StringUtils.isEmpty(System.getenv("AWS_S3_BUCKET"))){
            LOG.info("S3 uploading...");
            new S3Uploader();
        }

        if(storageHandler.equalsIgnoreCase("bunny")
                && !StringUtils.isEmpty(System.getenv("BUNNY_STORAGE_BUCKET"))){
            LOG.info("BunnyCDN uploading...");
        }

        LOG.info("All completed");
        System.exit(0);
    }
}