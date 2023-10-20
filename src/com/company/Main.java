package com.company;

import java.sql.SQLOutput;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        Random random = new Random();
        Queue<ProcessControlBlock> readyProcess = startProcessor();
        ArrayList<ProcessControlBlock> blockedProcess = new ArrayList<>();
        SimProcessor processor = new SimProcessor();
        final int QUANTUM = 5;
        int quantumCount = 0;

        for (int i = 0; i < 3000; i ++){

            if (!readyProcess.isEmpty()){
                ProcessControlBlock current = readyProcess.peek();
                setPCB(processor, current);

                System.out.println("Restoring " + current.getSimProcess().procName + " Instruction:" + processor.getCurrInstruction() + " R1: " + processor.getRegister1() + " R2: "
                        + processor.getRegister2() + " R3: " + processor.getRegister3() + " R4: " + processor.getRegister4());

                ProcessState currentState = processor.executeNextInstruction();
                quantumCount++;


                if (currentState == ProcessState.FINISHED){
                    System.out.println("***PROCESS FINISHED***");
                    System.out.println("CONTEXT SWITCH: Saving Process " + current.getSimProcess().procName);
                    System.out.println("Instruction: " + processor.getCurrInstruction() + " R1: " + processor.getRegister1() + " R2: "
                            + processor.getRegister2() + " R3: " + processor.getRegister3() + " R4: " + processor.getRegister4());
                    savePCB(processor, current);
                    readyProcess.poll();
                    quantumCount = 0;
                }
                if (currentState == ProcessState.BLOCKED){
                    System.out.println("***PROCESS BLOCKED***");
                    System.out.println("CONTEXT SWITCH: Saving Process " + current.getSimProcess().procName);
                    System.out.println("Instruction: " + processor.getCurrInstruction() + " R1: " + processor.getRegister1() + " R2: "
                            + processor.getRegister2() + " R3: " + processor.getRegister3() + " R4: " + processor.getRegister4());
                    savePCB(processor, current);
                    readyProcess.poll();
                    quantumCount = 0;
                    blockedProcess.add(current);
                }
                if (quantumCount >= QUANTUM) {
                    System.out.println("***PROCESS HIT QUANTUM***");
                    System.out.println("CONTEXT SWITCH: Saving Process " + current.getSimProcess().procName);
                    System.out.println("Instruction: " + processor.getCurrInstruction() + " R1: " + processor.getRegister1() + " R2: "
                            + processor.getRegister2() + " R3: " + processor.getRegister3() + " R4: " + processor.getRegister4());
                    savePCB(processor, current);
                    readyProcess.poll();
                    readyProcess.add(current);
                    quantumCount = 0;

                }

                blockToReady(random, readyProcess, blockedProcess);
            }
            else {
                System.out.println("Idling...");
                blockToReady(random, readyProcess, blockedProcess);
            }

        }

    }

    private static void blockToReady(Random random, Queue<ProcessControlBlock> readyProcess, ArrayList<ProcessControlBlock> blockedProcess) {
        for (int j = 0; j < blockedProcess.size(); j ++){
            if (random.nextInt(100) < 30){
                ProcessControlBlock pcb = blockedProcess.remove(j);
                readyProcess.add(pcb);
                System.out.println(pcb.getSimProcess().procName + " Pulled off the blocked list");

            }

        }
    }

    private static void setPCB(SimProcessor processor, ProcessControlBlock current) {
        processor.setSimProcess(current.getSimProcess());
        processor.setRegister1(current.getRegister1());
        processor.setRegister2(current.getRegister2());
        processor.setRegister3(current.getRegister3());
        processor.setRegister4(current.getRegister4());
        processor.setCurrInstruction(current.getSave_currentInstruction());
    }

    private static void savePCB(SimProcessor processor, ProcessControlBlock current) {
        current.setRegister1(processor.getRegister1());
        current.setRegister2(processor.getRegister2());
        current.setRegister3(processor.getRegister3());
        current.setRegister4(processor.getRegister4());
        current.setSave_currentInstruction(processor.getCurrInstruction());
    }

    private static Queue<ProcessControlBlock> startProcessor() {
        Queue<ProcessControlBlock> queue = new LinkedList<>();
        Random random = new Random();
        String[] processNames = {"Google Chrome", "Crossy Road", "Calculator", "IntellaJ", "VS",
                "MS PowerPoint", "MS Word", "MS Excel", "MY SQL", "Andriod Studio"};
        int[] processInstrucions = {100, 200, 300, 400, 150, 250, 350, 175, 275, 375};

        for (int i = 0; i < processNames.length; i ++){
            SimProcess simProcess = new SimProcess(random.nextInt(20), processNames[i], processInstrucions[i] );
            ProcessControlBlock pcb = new ProcessControlBlock(simProcess);
            queue.add(pcb);
        }
        return queue;

    }
}
