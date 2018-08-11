    package com.example.rashi.hw02;
    
    import android.app.Activity;
    import android.support.v7.app.ActionBar;
    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.ImageButton;
    import android.widget.TextView;
    
    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.Comparator;
    import java.util.LinkedList;
    import java.util.List;

    import static com.example.rashi.hw02.R.id.add;
    
    public class MainActivity extends AppCompatActivity {
        public static final String taskKey="TASK_KEY";
        public static final int reqCode=100;
        public static final int reqCode1=101;
        TextView priority,task,date,time,taskCount;
        CurrentTask currentTask;
        final LinkedList<CurrentTask> taskTotalList= new LinkedList<>();
        static int taskCounter;
    
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            //to add the app icon
            /* ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);*/
            
            setContentView(R.layout.activity_main);
            
            /// all the image buttons ////
            ImageButton previousButton = findViewById(R.id.previous);
            ImageButton nextButton= findViewById(R.id.next);
            ImageButton first= findViewById(R.id.first);
            ImageButton last= findViewById(R.id.last);
            ImageButton img = findViewById(add);
            ImageButton delete = findViewById(R.id.delete);
            
            //// text views ///
            priority = findViewById(R.id.taskPriority);
            task = findViewById(R.id.task_main);
            date = findViewById(R.id.taskDate);
            time = findViewById(R.id.taskTime);
            taskCount= findViewById(R.id.taskCount);

            if(currentTask==null) {
                taskCount.setText("Task 0 of 0");
            }

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                    startActivityForResult(intent, reqCode);
                }
            });

            first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CurrentTask first=  taskTotalList.getFirst();
                    task.setText(first.getTitle());
                    date.setText(first.getDate());
                    time.setText(first.getTime());
                    priority.setText(first.getPriority());
                    int count = CurrentTask.taskCount - 1;
                    taskCount.setText("Task 1 of " + taskTotalList.size());
                }
            });
            
            last.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CurrentTask last=  taskTotalList.getLast();
                    task.setText(last.getTitle());
                    date.setText(last.getDate());
                    time.setText(last.getTime());
                    priority.setText(last.getPriority());
                    taskCount.setText("Task " + taskTotalList.size() + " of " + taskTotalList.size());
                }
            });


            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskCounter+=1;
                    if(taskCounter<taskTotalList.size()) {
                        CurrentTask next = taskTotalList.get(taskCounter);
                        task.setText(next.getTitle());
                        date.setText(next.getDate());
                        time.setText(next.getTime());
                        priority.setText(next.getPriority());
                        int value=taskCounter + 1;
                        taskCount.setText("Task " + value
                                + " of " + taskTotalList.size());
                    }
                }
            });
    
            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskCounter-=1;
                    if(taskCounter>=0) {
                        CurrentTask t = taskTotalList.get(taskCounter);
                        task.setText(t.getTitle());
                        date.setText(t.getDate());
                        time.setText(t.getTime());
                        priority.setText(t.getPriority());
                        int value=taskCounter + 1;
                        taskCount.setText("Task " + value + " of " + taskTotalList.size());
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskTotalList.remove(taskCounter);
                    int value;
                    if(taskTotalList.size()!=0) {
                        if ((taskCounter - 1 >= 0)) {
                            value = taskCounter - 1;
                        } else {
                            value = taskCounter;
                        }
                        CurrentTask t = taskTotalList.get(value);
                        task.setText(t.getTitle());
                        date.setText(t.getDate());
                        time.setText(t.getTime());
                        priority.setText(t.getPriority());
                        int value1 = taskCounter ;
                        taskCount.setText("Task " + value1 + " of " + taskTotalList.size());
                    }
                    else
                    {
                        task.setText( getString(R.string.Task_title)  );
                        date.setText(getString(R.string.TaskDate) );
                        time.setText(getString(R.string.TaskTime) );
                        priority.setText(getString(R.string.TaskPriority) );
                        taskCount.setText("Task 0 of 0");
                    }


                }
            });
           findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(MainActivity.this, EditActivity.class);
                    i.putExtra(taskKey,taskTotalList.get(taskCounter));
                    startActivityForResult(i,reqCode1);
                }
            });



        }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                int value;
                if (requestCode == reqCode) {
    
                    if (resultCode == Activity.RESULT_OK) {
    

                        CurrentTask currentTask= (CurrentTask) data.getExtras().getSerializable(taskKey);
                        taskTotalList.add(currentTask);
                        Collections.sort(taskTotalList,new CurrentTask());
                         value=taskTotalList.indexOf(currentTask)+1;

                        Log.d("demo1", "size is "+taskTotalList.size());
                        for(CurrentTask c: taskTotalList)
                        {
                            //int value=
                            Log.d("demo1", "SOrted list"+c.getDate());
                        }

                        taskCounter=taskTotalList.size()-1;
                        task.setText(currentTask.getTitle());
                        date.setText(currentTask.getDate());
                        time.setText(currentTask.getTime());
                        priority.setText(currentTask.getPriority());
                        Log.d("demo", "count: "+CurrentTask.taskCount);
                            taskCount.setText("Task "+value +" of "+ taskTotalList.size());
    
    
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        // some stuff that will happen if there's no result
                    }
                }
                else if(requestCode==reqCode1){

                    CurrentTask currentTask= (CurrentTask) data.getExtras().getSerializable(taskKey);

                    taskTotalList.set(taskCounter,currentTask);
                    taskTotalList.get(taskCounter).setTitle(currentTask.getTitle());
                    taskCounter=taskTotalList.size()-1;
                    task.setText(currentTask.getTitle());
                    date.setText(currentTask.getDate());
                    time.setText(currentTask.getTime());
                    priority.setText(currentTask.getPriority());
                    Collections.sort(taskTotalList,new CurrentTask());
                    value=taskTotalList.indexOf(currentTask)+1;
                    Log.d("demo", "count: "+CurrentTask.taskCount);
                    taskCount.setText("Task "+taskCounter+ " of "+ taskTotalList.size());
                }
            }
    
    
    }
