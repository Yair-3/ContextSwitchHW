package com.company;

import java.util.Random;

public class SimProcessor {
    private SimProcess simProcess;
    private int register1;
    private int register2;
    private int register3;
    private int register4;

    private int currInstruction;

    public void setSimProcess(SimProcess sP){
        this.simProcess = sP;
    }

    public SimProcess getSimProcess(){
        return this.simProcess;
    }

    public void setRegister1(int register1) {
        this.register1 = register1;
    }

    public void setRegister2(int register2) {
        this.register2 = register2;
    }

    public void setRegister3(int register3) {
        this.register3 = register3;
    }

    public void setRegister4(int register4) {
        this.register4 = register4;
    }

    public int getRegister1() {
        return register1;
    }

    public int getRegister2() {
        return register2;
    }

    public int getRegister3() {
        return register3;
    }

    public int getRegister4() {
        return register4;
    }

    public int getCurrInstruction() {
        return currInstruction;
    }

    public void setCurrInstruction(int currInstruction) {
        this.currInstruction = currInstruction;
    }

    public ProcessState executeNextInstruction(){
        Random random = new Random();
        ProcessState state = this.simProcess.execute(currInstruction++);

        setRegister1(random.nextInt(100));
        setRegister2(random.nextInt(100));
        setRegister3(random.nextInt(100));
        setRegister4(random.nextInt(100));

        return state;
    }
}
