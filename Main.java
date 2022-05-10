import java.util.*;

public class Main {
    public enum ProcessState {READY, BLOCKED, SUSPENDED_READY, SUSPENDED_BLOCKED, FINISHED} // SUSPENDED_READY and SUSPENDED_BLOCKED aren't used, but I kept them here if I ever want to expand this simulation

    ;

    public static void main(String[] args) {
        final int QUANTUM = 5; //set arbitrarily, can be any number
        SimProcessor simProcessor = new SimProcessor();
        ProcessControlBlock pcbOne = new ProcessControlBlock(new SimProcess(1, "one", 356));
        ProcessControlBlock pcbTwo = new ProcessControlBlock(new SimProcess(2, "two", 121));
        ProcessControlBlock pcbThree = new ProcessControlBlock(new SimProcess(3, "three", 257));
        ProcessControlBlock pcbFour = new ProcessControlBlock(new SimProcess(4, "four", 246));
        ProcessControlBlock pcbFive = new ProcessControlBlock(new SimProcess(5, "five", 199));
        ProcessControlBlock pcbSix = new ProcessControlBlock(new SimProcess(6, "six", 357));
        ProcessControlBlock pcbSeven = new ProcessControlBlock(new SimProcess(7, "seven", 157));
        ProcessControlBlock pcbEight = new ProcessControlBlock(new SimProcess(8, "eight", 119));
        ProcessControlBlock pcbNine = new ProcessControlBlock(new SimProcess(9, "nine", 267));
        ProcessControlBlock pcbTen = new ProcessControlBlock(new SimProcess(10, "ten", 242));
        ArrayList<ProcessControlBlock> readyProcesses = new ArrayList<>();
        ArrayList<ProcessControlBlock> blockedProcesses = new ArrayList<>();
        Collections.addAll(readyProcesses, pcbOne, pcbTwo, pcbThree, pcbFour, pcbFive, pcbSix, pcbSeven, pcbEight, pcbNine, pcbTen);
        ProcessControlBlock currentPCB = readyProcesses.get(0); //start off with the first process
        ProcessState ps = ProcessState.READY; //This represents the process state. In a real system this isn't really a thing, you know a process's state based on where it is, but in this simulation I'm representing the process state with a variable. I initialized it because otherwise the project can't build
        int counter = 0;
        for (int i = 0; i <= 3000; i++) {
            System.out.print("\nStep " + i + " ");
            for (int k = 0; k < blockedProcesses.size(); k++) {
                if (Math.random() < .3) {  //Randomly set the processes to a ready state 30% of the time, because since this is a simulation, they're not going to be ready by themselves (as opposed to an actual process)
                    readyProcesses.add(blockedProcesses.get(k));
                    blockedProcesses.remove(k);
                }
            }
            if (simProcessor.getCurrentProcess() == null) { //if there's no process running, put a process on the processor
                if (readyProcesses.size() == 0) {
                    System.out.println("*** No Processes Ready, idling... ***");
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
            } else { //if there is a process running...
                if (counter++ < QUANTUM) { //if the quantum didn't expire...
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
                    //if the process isn't blocked or finished...
                    ps = simProcessor.executeNextInstruction();
                } else { //if the quantum did expire...
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
                }
            }
        }
    }
}

