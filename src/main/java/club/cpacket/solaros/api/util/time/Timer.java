package club.cpacket.solaros.api.util.time;

/**
 * @author zzurio
 */

public class Timer {

    public long time;

    public Timer() {
        this.time = System.currentTimeMillis();
    }

    public Timer reset(long time) {
        this.time = time;
        return this;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getMS(long time) {
        return time / 1000000L;
    }

    public boolean hasPassed(int millis) {
        return (System.currentTimeMillis() - this.time) >= millis;
    }

    public boolean passAndReset(int millis) {
        if (System.currentTimeMillis() - this.time >= millis) {
            reset();
            return true;
        } else return false;
    }

    public void setTimer(long time) {
        if (time > 0.0f) {
            this.time = time;
        }
    }

    public boolean sleep(long time) {
        if ((System.nanoTime() / 1000000L - time) >= time) {
            reset();
            return true;
        }

        return false;
    }

    public void reset() {
        this.time = System.nanoTime();
    }
}
