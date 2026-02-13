package com.ghostchu.crowdincopydeploy.uploader;
/*
 * IslandSurvival
 * Copyright (C) 2025 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.ghostchu.crowdincopydeploy.S3Uploader;
import com.ghostchu.crowdincopydeploy.bunnyapi.BCDNStorage;
import com.ghostchu.crowdincopydeploy.task.UploadBunnyTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * BunnyUploader
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class BunnyUploader {
    private static final Logger LOG = LoggerFactory.getLogger(BunnyUploader.class);

    private File deployPath;
    private String bunnyZone;
    private String bunnyAPIKey;
    private String bunnyMainZone;
    private String bunnyDest;
    private BCDNStorage bunnyStorage;

    public BunnyUploader() {
        acceptEnvironmentVariable();

        upload();
    }

    private void acceptEnvironmentVariable() {
        bunnyZone = System.getenv("BUNNY_ZONE");
        bunnyAPIKey = System.getenv("BUNNY_API_KEY");

        bunnyMainZone = System.getenv("BUNNY_MAIN_ZONE");
        if(StringUtils.isEmpty(bunnyMainZone)) {
            bunnyMainZone = "de";
        }

        bunnyDest = System.getenv("BUNNY_DEST");
        if(StringUtils.isEmpty(bunnyDest)) {
            bunnyDest = "";
        }

        String deployPath = System.getenv("DEPLOY_PATH");
        if(StringUtils.isEmpty(deployPath)) {
            deployPath = "./deploy-target";
        }

        this.deployPath = new File(deployPath);
        this.deployPath.mkdirs();

        bunnyStorage = new BCDNStorage(bunnyZone, bunnyAPIKey, bunnyMainZone);

        LOG.info("Deploy files from {} to BunnyStorage...", this.deployPath.getAbsolutePath());
    }

    private void upload() {
        new UploadBunnyTask(bunnyStorage, deployPath, bunnyDest).run();
    }
}