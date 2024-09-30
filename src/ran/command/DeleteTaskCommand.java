package ran.command;

import main.java.Ui;
import main.java.TaskList;
import main.java.Storage;
import ran.task.Task;
import ran.command.CommandType;
import ran.exception.MissingArgumentException;
import ran.exception.OutOfListBoundsException;
import java.lang.NumberFormatException;
import java.io.IOException;

/**
 * Represents a user-prompted command to delete a specified task. A <code>DeleteTaskCommand</code> object 
 * corresponds to a command represented with its argument stored as one string, which is the index of
 * a task within a <code>TaskList</code> to be deleted.
 * Inherits from base <code>Command</code> class.
 */
public class DeleteTaskCommand extends Command {
    /**
     * Constructor of a <code>DeleteTaskCommand</code> object.
     *
     * @param commandArg String representing an index as the command argument
     */
    public DeleteTaskCommand(String commandArg) {
        super(commandArg);
    }

    /**
     * Executes the command to delete a specified task from a <code>TaskList</code>, 
     * display a message of what has been deleted, 
     * and update the data file accordingly to be in sync.
     *
     * @param tasks TaskList object representing a list of tasks to be acted upon
     * @param ui Ui object representing the user interface to display the message
     * @param storage Storage object representing the data file to be modified
     * @return Boolean value of false as terminating condition is not met
     * @throws IOException If encounter error interfacing
     * @throws OutOfListBoundsException If command argument is an invalid index in <code>tasks</code>
     * @throws MissingArgumentException If command argument cannot be converted to a valid integer
     */
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws OutOfListBoundsException,
           IOException, MissingArgumentException {
               int index;
               try {
                   int commandNumber = Integer.parseInt(commandArg);
                   index = commandNumber - 1;
               } catch (NumberFormatException e) {
                   throw new MissingArgumentException(CommandType.DELETE);
               }
               Task deletedTask = tasks.removeTask(index);
               storage.deleteFromDataFile(deletedTask.dataFileInput());
               ui.printDeletedTask(deletedTask.toString(), tasks.getTaskCount());
               return false;
    }
}
