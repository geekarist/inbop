package me.cpele.inbop;

import java.util.concurrent.Executor;

public class AppExecutors {

    private Executor mDisk;

    public AppExecutors(Executor disk) {
        mDisk = disk;
    }

    public Executor disk() {
        return mDisk;
    }
}
