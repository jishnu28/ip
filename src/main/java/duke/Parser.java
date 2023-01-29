package duke;

/**
 * Class that deals with making sense of the user command
 */
public class Parser {
    /**
     * Makes sense of the user command and calls corresponding methods
     *
     * @param userInput String of user input read from command line
     * @param taskList TaskList object to which tasks are added to or deleted from
     * @param fileManager Storage object that deals with loading tasks from file and saving tasks in file
     */
    public void parse(String userInput, TaskList taskList, Storage fileManager) {
        if (!userInput.equals("list")) {

            // If input = "find x" return all tasks that contain keyword: x
            if (userInput.startsWith("find ")) {
                String keyword = userInput.substring(5);
                TaskList tempTaskList = taskList.findTasks(keyword);
                System.out.println("Here are the matching tasks in your list:");
                tempTaskList.printList();
            }

            // If input = "mark x" set task x completed? to True
            else if (userInput.startsWith("mark ")) {
                int taskNum = Integer.parseInt(userInput.substring(5));
                taskList.getTask(taskNum - 1).setCompleted(true);
            }

            // If input = "unmark x" set task x completed? to False
            else if (userInput.startsWith("unmark ")) {
                int taskNum = Integer.parseInt(userInput.substring(7));
                taskList.getTask(taskNum - 1).setCompleted(false);
            }

            // If input is a deadline, create deadline and add to task list
            else if (userInput.startsWith("deadline ")) {
                if (userInput.contains("/by ")) {
                    Duke.addToList(new Deadline(false, userInput));
                } else {
                    try {
                        Duke.throwException("deadline");
                    } catch (DukeException de) {
                        System.out.println(de.toString());
                    }
                }
            }

            // If input is an event, create event and add to task list
            else if (userInput.startsWith("event ")) {
                if (userInput.contains("/from ") && userInput.contains("/to ")) {
                    Duke.addToList(new Event(false, userInput));
                } else {
                    try {
                        Duke.throwException("event");
                    } catch (DukeException de) {
                        System.out.println(de.toString());
                    }
                }
            }

            // If input is a ToDos item, create ToDos item and add to task list
            else if (userInput.startsWith("todo ")) {
                if (userInput.length() > 5) {
                    Duke.addToList(new Todo(false, userInput));
                } else {
                    try {
                        Duke.throwException("todo");
                    } catch (DukeException de) {
                        System.out.println(de.toString());
                    }
                }
            }

            // If command is delete, then remove from task list and return deleted task
            else if (userInput.startsWith("delete ")) {
                // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!___________handle non-int input
                try {
                    int num = Integer.parseInt(userInput.substring(7));
                    Duke.removeFromList(num);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }

            }

            // Else create and add task to list
            else {
                try {
                    Duke.addToList(new Task());
                }
                catch (DukeException de){
                    System.out.println(de.toString());
                }
            }
            // Insert call on method that writes curr version of taskList to data/duke.txt
            fileManager.writeToFile(taskList);
        }
        else {
            Duke.displayList();
        }
    }
}
