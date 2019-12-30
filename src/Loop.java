import java.util.concurrent.Phaser;

public class Loop {

    public void apply() {
        int threads = 2;
        Phaser phaser = new Phaser();
        for (int i = 0; i < 100; i++) {
            int phaseCount = phaser.getPhase();
            phaser.register();

            System.out.println("Phasecount: " + phaseCount);
            for(int t = 0; t < threads; t++) {
                phaser.register();
            }
            System.out.println("Phaser unarrived party size is now: " + phaser.getUnarrivedParties());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int t = 0; t < threads; t++) {
//                phaser.arriveAndDeregister(); results in Phasecount: -2147483646
                phaser.arrive(); //does not register party as arrived?
            }

            phaser.arriveAndAwaitAdvance();
//            phaser.arriveAndDeregister(); results in Phasecount: -2147483646

            System.out.println("--Done with current iteration--");
        }
    }
}
