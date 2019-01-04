package com.example.frameworkdemoproject;

import android.os.AsyncTask;
import android.support.annotation.WorkerThread;
import android.view.View;
import android.widget.ProgressBar;

public class MyAsyncTask extends AsyncTask<Object, Object, Object> {

    interface TaskInvoker{
        @WorkerThread
        Object doInBackground(int state);
        void onPostExecute(int state, Object object);
        ProgressBar getProgressBar();
    }

    private TaskInvoker taskInvoker;
    private int asyncTaskState;

    public MyAsyncTask(TaskInvoker taskInvoker, int asyncTaskState){
        this.taskInvoker = taskInvoker;
        this.asyncTaskState = asyncTaskState;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(taskInvoker.getProgressBar() != null) {
            taskInvoker.getProgressBar().setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return taskInvoker.doInBackground(asyncTaskState);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(taskInvoker.getProgressBar() != null) {
            taskInvoker.getProgressBar().setVisibility(View.GONE);
        }
        taskInvoker.onPostExecute(asyncTaskState, o);
    }
}
