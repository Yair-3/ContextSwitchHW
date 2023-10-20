package com.company;


import java.util.Random;

public class SimProcess {
    public int pid;
    public String procName;
    public int totalINstructions;

    public SimProcess(int pid_num, String name, int instruct) {
        this.pid = pid_num;
        this.procName = name;
        this.totalINstructions = instruct;

    }

    public ProcessState execute(int i){
        System.out.println("PID: " + pid +  "  Name: " + procName + " Instruction Number: " + i);

        if (i >= totalINstructions){
            return ProcessState.FINISHED;
        }
        Random random = new Random();
        int randomNum = random.nextInt(100);
        if (randomNum > 15){
            return ProcessState.BLOCKED;
        }
        return ProcessState.READY;

    }

}
