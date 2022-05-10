import java.util.Random;

public class SimProcessor {
    Random r = new Random();
    private SimProcess currentProcess;
    private int R1, R2, R3, R4, currInstruction;

    public void setCurrentProcess(SimProcess currentProcess) {
        this.currentProcess = currentProcess;
    }

    public SimProcess getCurrentProcess() {
        return currentProcess;
    }

    public void setRegisterValue(int register, int value) {
        if (register == 1) {
            R1 = value;
        }
        if (register == 2) {
            R2 = value;
        }
        if (register == 3) {
            R3 = value;
        }
        if (register == 4) {
            R4 = value;
        }
    }

    public int getRegisterValues(int register) {
        if (register == 1) {
            return R1;
        }
        if (register == 2) {
            return R2;
        }
        if (register == 3) {
            return R3;
        }
        return R4;
    }

    public void setCurrInstruction(int currInstruction) {
        this.currInstruction = currInstruction;
    }

    public int getCurrInstruction() {
        return currInstruction;
    }

    public Main.ProcessState executeNextInstruction() {
        Main.ProcessState currentProcessState = currentProcess.execute(currInstruction++);
        R1 = r.nextInt();
        R2 = r.nextInt();
        R3 = r.nextInt();
        R4 = r.nextInt();
        return currentProcessState;
    }
}
