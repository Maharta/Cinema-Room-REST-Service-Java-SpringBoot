class Starter {

    public static void startRunnables(Runnable[] runnables) {
        // implement the method
        for (Runnable runnable : runnables) {
            Thread newThread = new Thread(runnable);
            newThread.start();
        }
    }
}