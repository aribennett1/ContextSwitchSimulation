import java.util.Random;

public class ProcessControlBlock {
    Random r = new Random();
    private SimProcess simProcess;
    private int R1 = r.nextInt();
    private int R2 = r.nextInt();
    private int R3 = r.nextInt();
    private int R4 = r.nextInt();
    private int currInstruction = 0;

    public ProcessControlBlock(SimProcess simProcess) {
        this.simProcess = simProcess;
    }

    public SimProcess getSimProcess() {
        return simProcess;
    }

    public void setCurrInstruction(int currInstruction) {
        this.currInstruction = currInstruction;
    }

    public int getCurrInstruction() {
        return currInstruction;
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
}
