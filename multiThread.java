import java.util.*;

class Task {
    int id, time;

    Task(int id, int time) {
        this.id = id;
        this.time = time;
    }
}

class Server extends Thread {
   
    String name;
    int maxTime;
    Queue<Task> taskQueue = new LinkedList<>();
    Task currentTask = null, lastCompletedTask = null;
    volatile boolean running = true;

    Server(String name, int maxTime) {
        this.name = name;
        this.maxTime = maxTime;
    }

    public void run() {
        try {
            while (running || !taskQueue.isEmpty()) {
                Task task;
                synchronized (taskQueue) {
                    task = taskQueue.poll();
                }
                if (task != null) {
                    currentTask = task;
                    System.out.println("Server " + name + " starts task " + task.id + " (time: " + task.time + "ms)");
                    Thread.sleep(task.time);
                    lastCompletedTask = task;
                    currentTask = null;
                    System.out.println("Server " + name + " completed task " + task.id);
                } else {
                    Thread.sleep(100); 
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    void shutdown() {
        running = false;
    }

    String getStatus() {
        if (currentTask != null) {
            return "Server " + name + " is currently processing task: " + currentTask.id;
        } else if (lastCompletedTask != null) {
            return "Server " + name + " last completed task: " + lastCompletedTask.id;
        } else {
            return "Server " + name + " is idle";
        }
    }

    void addTask(Task task) {
        synchronized (taskQueue) {
            taskQueue.offer(task);
        }
    }
}

public class multiThread{
    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);

        int k = sc.nextInt();
        List<Server> servers = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            servers.add(new Server(sc.next(), sc.nextInt()));
        }

        int x = sc.nextInt();
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < x; i++) {
            tasks.add(new Task(sc.nextInt(), sc.nextInt()));
        }

        for (Server server : servers) {
            server.start();
        }

        for (Task task : tasks) {
            boolean assigned = false;
            for (Server server : servers) {
                if (task.time <= server.maxTime) {
                    server.addTask(task);
                    assigned = true;
                    break;
                }
            }
            if (!assigned) {
                System.out.println("Task " + task.id + " could not be assigned to any server.");
            }
        }

        Thread statusUpdater = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Status Update:");
                    for (Server server : servers) {
                        System.out.println(server.getStatus());
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        statusUpdater.setDaemon(true);
        statusUpdater.start();

        Thread.sleep(5000);

        for (Server server : servers) {
            server.shutdown();
        }

        for (Server server : servers) {
            server.join();
        }

        sc.close();
    }
}