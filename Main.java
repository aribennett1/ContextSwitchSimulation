import java.util.*;

public class Main {
    public enum ProcessState {READY, BLOCKED, SUSPENDED_READY, SUSPENDED_BLOCKED, FINISHED};

    public static void main(String[] args) {
        final int QUANTUM = 5;
        SimProcessor simProcessor = new SimProcessor();
        SimProcess one = new SimProcess(1, "one", 356);
        ProcessControlBlock pcbOne = new ProcessControlBlock(one);
        SimProcess two = new SimProcess(2, "two", 121);
        ProcessControlBlock pcbTwo = new ProcessControlBlock(two);
        SimProcess three = new SimProcess(3, "three", 257);
        ProcessControlBlock pcbThree = new ProcessControlBlock(three);
        SimProcess four = new SimProcess(4, "four", 246);
        ProcessControlBlock pcbFour = new ProcessControlBlock(four);
        SimProcess five = new SimProcess(5, "five", 199);
        ProcessControlBlock pcbFive = new ProcessControlBlock(five);
        SimProcess six = new SimProcess(6, "six", 357);
        ProcessControlBlock pcbSix = new ProcessControlBlock(six);
        SimProcess seven = new SimProcess(7, "seven", 157);
        ProcessControlBlock pcbSeven = new ProcessControlBlock(seven);
        SimProcess eight = new SimProcess(8, "eight", 119);
        ProcessControlBlock pcbEight = new ProcessControlBlock(eight);
        SimProcess nine = new SimProcess(9, "nine", 267);
        ProcessControlBlock pcbNine = new ProcessControlBlock(nine);
        SimProcess ten = new SimProcess(10, "ten", 242);
        ProcessControlBlock pcbTen = new ProcessControlBlock(ten);
        ArrayList<ProcessControlBlock> readyProcesses = new ArrayList<ProcessControlBlock>();
        ArrayList<ProcessControlBlock> blockedProcesses = new ArrayList<ProcessControlBlock>();
        readyProcesses.add(pcbOne);
        readyProcesses.add(pcbTwo);
        readyProcesses.add(pcbThree);
        readyProcesses.add(pcbFour);
        readyProcesses.add(pcbFive);
        readyProcesses.add(pcbSix);
        readyProcesses.add(pcbSeven);
        readyProcesses.add(pcbEight);
        readyProcesses.add(pcbNine);
        readyProcesses.add(pcbTen);
        ProcessControlBlock currentPCB = readyProcesses.get(0);
        ProcessState ps = ProcessState.READY;
        int counter = 0;
        for (int i = 0; i <= 3000; i++) {
            System.out.print("\nStep " + i + " ");
            if (simProcessor.getCurrentProcess() == null) {
                if (readyProcesses.size() == 0) {
                    System.out.println("*** No Processes Ready, idling... ***");
                    for (int k = 0; k < blockedProcesses.size(); k++) {
                        if (Math.random() < .3) {
                            readyProcesses.add(blockedProcesses.get(k));
                            blockedProcesses.remove(k);
                        }
                    }
                    continue;
                }
                currentPCB = readyProcesses.get(0);
                readyProcesses.remove(0);
                System.out.println("Context switch: restoring process: " + currentPCB.getSimProcess().getProcessName() + ", Instruction: " + currentPCB.getCurrInstruction() + " - R1: " + currentPCB.getRegisterValues(1) + ", R2: " + currentPCB.getRegisterValues(2) + ", R3: " + currentPCB.getRegisterValues(3) + ", R4: " + currentPCB.getRegisterValues(4));
                simProcessor.setCurrentProcess(currentPCB.getSimProcess());
                simProcessor.setCurrInstruction(currentPCB.getCurrInstruction());
                simProcessor.setRegisterValue(1, currentPCB.getRegisterValues(1));
                simProcessor.setRegisterValue(2, currentPCB.getRegisterValues(2));
                simProcessor.setRegisterValue(3, currentPCB.getRegisterValues(3));
                simProcessor.setRegisterValue(4, currentPCB.getRegisterValues(4));
                continue;
            } else {
                if (counter++ < QUANTUM) {
                    if (ps == ProcessState.BLOCKED) {
                        System.out.println("*** Process Blocked ***");
                        System.out.println("Context switch: saving process: " + currentPCB.getSimProcess().getProcessName() + ", Instruction: " + simProcessor.getCurrInstruction() + " - R1: " + simProcessor.getRegisterValues(1) + ", R2: " + simProcessor.getRegisterValues(2) + ", R3: " + simProcessor.getRegisterValues(3) + ", R4: " + simProcessor.getRegisterValues(4));
                        blockedProcesses.add(currentPCB);
                        currentPCB.setCurrInstruction(simProcessor.getCurrInstruction());
                        currentPCB.setRegisterValue(1, simProcessor.getRegisterValues(1));
                        currentPCB.setRegisterValue(2, simProcessor.getRegisterValues(2));
                        currentPCB.setRegisterValue(3, simProcessor.getRegisterValues(3));
                        currentPCB.setRegisterValue(4, simProcessor.getRegisterValues(4));
                        simProcessor.setCurrentProcess(null);
                        counter = 0;
                        ps = ProcessState.READY;
                        continue;
                    }
                    if (ps == ProcessState.FINISHED) {
                        System.out.println("*** Process Completed ***");
                        simProcessor.setCurrentProcess(null);
                        counter = 0;
                        ps = ProcessState.READY;
                        continue;
                    }
                    ps = simProcessor.executeNextInstruction();
                    for (int k = 0; k < blockedProcesses.size(); k++) {
                        if (Math.random() < .3) {
                            readyProcesses.add(blockedProcesses.get(k));
                            blockedProcesses.remove(k);
                        }
                    }
                } else {
                    System.out.println("*** Quantum Expired ***");
                    System.out.println("Context switch: saving process: " + currentPCB.getSimProcess().getProcessName() + ", Instruction: " + simProcessor.getCurrInstruction() + " - R1: " + simProcessor.getRegisterValues(1) + ", R2: " + simProcessor.getRegisterValues(2) + ", R3: " + simProcessor.getRegisterValues(3) + ", R4: " + simProcessor.getRegisterValues(4));
                    readyProcesses.add(currentPCB);
                    currentPCB.setCurrInstruction(simProcessor.getCurrInstruction());
                    currentPCB.setRegisterValue(1, simProcessor.getRegisterValues(1));
                    currentPCB.setRegisterValue(2, simProcessor.getRegisterValues(2));
                    currentPCB.setRegisterValue(3, simProcessor.getRegisterValues(3));
                    currentPCB.setRegisterValue(4, simProcessor.getRegisterValues(4));
                    simProcessor.setCurrentProcess(null);
                    counter = 0;
                    ps = ProcessState.READY;
                    for (int k = 0; k < blockedProcesses.size(); k++) {
                        if (Math.random() < .3) {
                            readyProcesses.add(blockedProcesses.get(k));
                            blockedProcesses.remove(k);
                        }
                    }
                }

            }
        }
    }
}

