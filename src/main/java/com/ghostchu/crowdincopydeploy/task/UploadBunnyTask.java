package com.ghostchu.crowdincopydeploy.task;

import com.ghostchu.crowdincopydeploy.bunnyapi.BCDNStorage;
import me.tongfei.progressbar.ProgressBar;

import java.io.File;

/**
 * UploadBunnyTask
 *
 * @author creatorfromhell
 * @since 2.0-SNAPSHOT
 */
public class UploadBunnyTask implements Runnable{

    private BCDNStorage bunnyStorage;
    private final File deployPath;
    private final String bunnyDest;

    public UploadBunnyTask(final BCDNStorage bunnyStorage, final File deployPath, final String bunnyDest) {
        this.bunnyStorage = bunnyStorage;
        this.deployPath = deployPath;
        this.bunnyDest = bunnyDest;
    }
    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {

            bunnyStorage.uploadFolder(deployPath.getPath(), bunnyDest);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}