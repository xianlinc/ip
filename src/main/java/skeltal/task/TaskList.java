package skeltal.task;

import java.util.ArrayList;

import skeltal.SkeltalException;
import skeltal.Storage;

public class TaskList {

    private static ArrayList<Task> tasks = new ArrayList<>();

    /**
     * A method to add a Task object into the list.
     *
     * @param assignment A task object.
     * @return A reply from Skeltal for adding the task.
     * @throws SkeltalException
     */
    public static String addTask(Task assignment) throws SkeltalException {
        tasks.add(assignment);
        String reply = "Got it. I've added this task\n"
                + assignment + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
        Storage.write(Storage.SKELTAL_PATH, "task");
        return reply;
    }

    /**
     * A method to list the Task objects currently in the list.
     *
     * @return A String representation of the tasks in the TaskList.
     */
    public static String listReply() {
        String reply = "Here are the tasks in your list:\n";
        for (Task task : tasks) {
            reply += task + "\n";
        }
        return reply.trim();
    }

    /**
     * Returns the index position of the task in the list.
     *
     * @param task The Task object.
     * @return The index position of the task in the list.
     */
    public static int getIndex(Task task) {
        return tasks.indexOf(task);
    }

    /**
     * Sets the Task at the index (indexPlus1 - 1) in the TaskList to a "completed" state.
     *
     * @param indexPlus1 The index of the task in the list + 1.
     * @return A reply from Skeltal for setting the task to done.
     * @throws SkeltalException
     */
    public static String done(String indexPlus1) throws SkeltalException {
        String reply = "";
        int i = Integer.parseInt(indexPlus1) - 1;
        Task assignment = tasks.get(i);
        assignment.setComplete();
        reply += "Done! I've marked this task as done!\n";
        reply += assignment;
        Storage.write(Storage.SKELTAL_PATH, "task");
        return reply;
    }

    /**
     * Deletes the Task at the index index in the TaskList.
     *
     * @param index The index of the task in the list.
     * @return A reply from Skeltal for deleting a Task.
     * @throws SkeltalException
     */
    public static String delete(String index) throws SkeltalException {
        String reply = "Removed this task:\n";
        int i = Integer.parseInt(index) - 1;
        if (outOfRange(i)) {
            throw new SkeltalException("Choose a positive number that is within the range of the list!");
        }
        reply += tasks.get(i) + "\n";
        tasks.remove(i);
        reply += "Now you have " + tasks.size() + " tasks in the list.";
        Storage.write(Storage.SKELTAL_PATH, "task");
        return reply;
    }

    /**
     * A method to load tasks into the task list from an ArrayList object.
     *
     * @param arrayList An ArrayList object containing Task objects.
     */
    public static void loadTaskList(ArrayList<Task> arrayList) {
        tasks = arrayList;
    }

    /**
     * A method to find tasks in the TaskList that contain the String str.
     *
     * @param str The string you are searching for.
     * @return The matching tasks in the list.
     */
    public static String findMatchingTasks(String str) {
        String reply = "";
        reply += "Here are the matching tasks in your list.";
        for (Task task : tasks) {
            if (task.getTaskDescription().contains(str)) {
                reply += task + "\n";
            }
        }
        return reply.trim();
    }

    /**
     * Returns a String that represents the loadable form of the TaskList.
     *
     * @return A String representation of the TaskList.
     */
    public static String tasksToFileFormat() {
        String toWrite = "";
        for (Task task : tasks) {
            toWrite += task.store() + "\n";
        }
        return toWrite;
    }

    public static void resetList() {
        tasks.clear();
    }

    private static boolean outOfRange(int i) {
        if (tasks.size() <= 0 || tasks.size() < i) {
            return true;
        } else {
            return false;
        }
    }
}
