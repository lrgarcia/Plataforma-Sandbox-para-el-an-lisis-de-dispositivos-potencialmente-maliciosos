/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.usb;

/**
 *
 * @author Luis
 */
import java.util.LinkedList;
import java.util.HashMap;
public class CommandManager {
    private LinkedList<Command> commandHistory = new LinkedList<Command>();
    private static CommandManager commandManager;
    //COMMANDS esta formado por su etiqueta string y la clase Comando que extiende de Command
    private static final HashMap<String, Class<? extends Command>> COMMANDS =
            new HashMap<String, Class<? extends Command>>();
    
    
    
    
    private CommandManager() {
        /*Al instanciar se dará de alta todos los comandos*/
        registCommand(ListAllDevicesCommand.COMMAND_NAME, ListAllDevicesCommand.class);
        registCommand(AnalizerDeviceCommand.COMMAND_NAME, AnalizerDeviceCommand.class);
    }


    public static synchronized CommandManager getIntance() {
            if (commandManager == null) {
                commandManager = new CommandManager();
            }
            return commandManager;
        }  
    public Command getCommand(String commandName) {
        if (COMMANDS.containsKey(commandName.toUpperCase())) {
            try {
                return COMMANDS.get(commandName.toUpperCase()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return new ErrorCommand();
            }
        } else {
            return new NotFoundCommand();
        }
    }

    public void registCommand(String commandName, 
                Class<? extends Command> command) {
            COMMANDS.put(commandName.toUpperCase(), command);
        }
    }