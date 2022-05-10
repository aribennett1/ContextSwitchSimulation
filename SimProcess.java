public class SimProcess {
    private int pid, totalInstructions;
    private String procName;

    public SimProcess(int pid, String procName, int totalInstructions) {
        this.pid = pid;
        this.procName = procName;
        this.totalInstructions = totalInstructions;
    }

    public String getProcessName() {
        return procName;
    }

    public int getProcessPid() {
        return pid;
    }

    public Main.ProcessState execute(int i) {
        System.out.print("  PID: " + pid + ", Process Name: " + procName + ", executing instruction: " + i);
        if (i >= totalInstructions) {
            return Main.ProcessState.FINISHED;
        } else {
            double r = Math.random();
            if (r < 0.15) {
                return Main.ProcessState.BLOCKED;
            }
        }
        return Main.ProcessState.READY;
    }
}
