package main;

import gfx.Window;
import persistency.Save;
import persistency.SaveHandler;
import sim.Particle;
import sim.Playback;
import sim.Simulation;

import java.util.Scanner;

public class Main {
    private static Window window;
    private static Simulation simulation;

    public static void main(String[] args){
        window = new Window(1200, 900, "Particle Simulation");

        boolean stop = false;
        Scanner reader = new Scanner(System.in);
        while(!stop){
            System.out.print("1. Start pre-loaded simulation;\n2. Start previous simulation playback;\n3. Load saved simulation;\n4. Exit.\n>> ");
            switch(reader.nextLine()){
                case "1":
                    System.out.print("\nFilename: ");
                    String filename1 = reader.nextLine();
                    simulation = new Simulation("Gravity Simulation");
                    simulation.startSimulation();
                    SaveHandler.save(filename1);
                    break;
                case "2":
                    if(Main.getSimulation() != null){
                        window.init();
                        window.start();
                    }else
                        System.out.println("No loaded simulation...\n");
                    break;
                case "3":
                    if(SaveHandler.listSaves() == 1){
                        System.out.print("\nFilename: ");
                        String filename2 = reader.nextLine();
                        Save save = SaveHandler.load(filename2);
                        if(save != null){
                            simulation = new Simulation(save.getTitle(), new Playback(save.getPlayback()));
                            window.init();
                            window.start();
                        }
                    }
                    break;
                case "4":
                    System.out.println("Exiting...");
                    stop = true;
                    break;
            }
        }
    }

        public static Window getWindow() {
            return window;
    }

    public static Simulation getSimulation() {
        return simulation;
    }
}
