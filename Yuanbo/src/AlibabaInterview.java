//评测题目: //评测题目:+编写代码题：
/*有一张任务表demo_task。有4个字段，分别是
id（任务ID）、
name（任务名）、
status（任务状态，分别是未开始、进行中、已完成、失败）、
content（任务内容）。

业务逻辑是，每秒会至少新增10个任务，状态是末开始，进行中的任务一般要处理1分钟，有些任务会失败，因此需要重试3次。
请用java多线程机制，实现一个任务处理的流程。
任务处理部分请用伪代码即可。
重点是从数据表读取任务、多线程处理任务、更新状态这三个部分。
其余可省略。请随意发挥。

1、	请编写伪代码，将任务处理的整个流程体现出来？
2、	你觉得这个任务处理的核心问题是什么？为什么？
3、  如何提升开发者的开发效率，请举一个你实践过的案例。*/

/*
1. 伪代码
// Task entity 
  class Task: 
	id, name, status, content

// DB operations 
  class TaskDao:
  	public List<Task> readTasks(){
    	// fetch only the unprocessed/ new added tasks
    }
    public void updateStatus(Task task){
    	// update task status
    }
//Task processing
  @EnableScheduling
  class TaskService: 
	volatile List<Task> taskList = new ArrayList<Task>();
    static ExecutorService executor = Executors.newFixedThreadPool(600);

	@Scheduled(fixedDelay=1000)
	public void process(){
    	taskList.add(TaskDao.readTasks());
      	processTasks(taskList);
    }
	public void processTasks(List<Task> taskList){
		for task : taskList
          executor.execute(new Handler(task));
      	  taskList.remove(task);
    }

// handler for task
  class Handler implements Runnable {
     private final Task task;
     private final volatile int count;
     Handler(Task task) { this.task = task; }
     public void run() {
       while(count < 3){
         count++;
         try{
			// run the task 
           
           	//update the task status if run success 
           	taskDao.updateTask(task);
   			// get out of the loop
           	break; 	                 	
         }catch(Exception e){
			// exception handling 
         }
       }
   }
  	




2. 你觉得这个任务处理的核心问题是什么？为什么？
  
  多线程管理： 
  每秒至少10条新增任务，每个任务处理一分钟。这里采用大小为600的线程池ExecutorService 10*60 = 600 
  
  任务列表的维护：
  采用volatile关键字， 同时维护list的加减。
  
  任务执行：
  每条任务可能出错，至少执行三次，这里采用单独的处理线程handler并加上计数器控制
  

3. 如何提升开发者的开发效率，请举一个你实践过的案例。
 
  a. 工具 
	-- git 代码同步和版本控制
    -- IDE 方便开发 eclipse等
    -- 快捷键 
   b.开发方法 Scrum framework，agile，增量开发, 结伴编程
   c.管理，将事情记录下来，先做重要的事，专注。

*/