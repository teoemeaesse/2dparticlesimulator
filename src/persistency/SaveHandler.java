package persistency;

import main.Main;
import sim.Particle;

import java.io.*;

/**
 * Created by tomas on 8/4/2019.
 */
public class SaveHandler {
    public static void save(String filename){
        try{
            File configHome = new File(System.getenv("APPDATA"));
            if(!configHome.exists())
                configHome.createNewFile();
            File directory = new File(configHome.getAbsolutePath() + "/2dparticlesimulation");
            if(!directory.exists())
                directory.mkdir();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(configHome + "/2dparticlesimulation/" + filename));
            oos.writeObject(new Save(Main.getSimulation().getPlayback().getPlayback(), Main.getSimulation().getTitle()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static Save load(String filename){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(System.getenv("APPDATA") + "/2dparticlesimulation/" + filename));
            System.out.println("Loading...\n");
            return (Save) ois.readObject();
        }catch(IOException | ClassNotFoundException e){
            System.out.println("File not found...\n");
        }
        return null;
    }

    public static int listSaves(){
        try{
            File configHome = new File(System.getenv("APPDATA"));
            if(!configHome.exists())
                configHome.createNewFile();
            File directory = new File(configHome.getAbsolutePath() + "/2dparticlesimulation");
            if(!directory.exists())
                directory.mkdir();
            File[] saves = directory.listFiles();
            if(saves.length != 0){
                for(File f : directory.listFiles())
                    System.out.println(f.getName());
                return 1;
            }else{
                System.out.println("No saved simulations found...\n");
                return 0;
            }
        }catch(IOException e){
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean saveExists(String filename){
        return new File(System.getenv("APPDATA") + "/2dparticlesimulation/" + filename).exists();
    }
}
