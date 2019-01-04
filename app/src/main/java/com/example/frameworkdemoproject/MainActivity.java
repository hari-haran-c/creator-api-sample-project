package com.example.frameworkdemoproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zoho.creator.framework.apiutil.FormAPIUtil;
import com.zoho.creator.framework.apiutil.ReportAPIUtil;
import com.zoho.creator.framework.apiutil.ZOHOCreatorAPIUtil;
import com.zoho.creator.framework.exception.ZCException;
import com.zoho.creator.framework.model.ZCApplication;
import com.zoho.creator.framework.model.components.ZCComponent;
import com.zoho.creator.framework.model.components.ZCComponentType;

import java.util.List;

public class MainActivity extends ZohoCreatorBaseActivity implements MyAsyncTask.TaskInvoker{

    private ListView listView;
    private TextView noAppsTextView;
    private ProgressBar progressBar;

    private ZCComponent zcComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.applicationsListView);
        noAppsTextView = findViewById(R.id.noAppsTextView);
        progressBar = findViewById(R.id.progressBar);

        MyAsyncTask asyncTask = new MyAsyncTask(this, 0);
        asyncTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_signout){
            AuthorizationUtil.logOutFromApp(MainActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Object doInBackground(int state) {
        if(state == 0) {
            try {
                return ZOHOCreatorAPIUtil.getApplicationList();
            } catch (ZCException e) {
                e.printStackTrace();
                return null;
            }
        } else if(state == 1){
            try {
                return FormAPIUtil.getForm(zcComponent);
            } catch (ZCException e) {
                e.printStackTrace();
            }
        }else if(state == 2){
            try {
                return ReportAPIUtil.getReport(zcComponent);
            } catch (ZCException e) {
                e.printStackTrace();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void onPostExecute(int state, Object object) {
        if(object instanceof List && !((List) object).isEmpty() && ((List) object).get(0) instanceof ZCApplication){
            listView.setAdapter(new ListAdapter(this, (List<ZCApplication>)object));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    if(position == 0){
//                        zcComponent = new ZCComponent("omnamonarayana", "test-push-notification-schedule", ZCComponentType.FORM, "Form_1", "Form_1", -1);
//                        MyAsyncTask asyncTask = new MyAsyncTask(MainActivity.this, 1);
//                        asyncTask.execute();
//                    }else if(position == 1){
//                        zcComponent = new ZCComponent("omnamonarayana", "test-push-notification-schedule", ZCComponentType.REPORT, "Form_1_Report", "Form_1_Report", -1);
//                        MyAsyncTask asyncTask = new MyAsyncTask(MainActivity.this, 2);
//                        asyncTask.execute();
//                    }
                }
            });
        }else{
            noAppsTextView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    private static class ListAdapter extends ArrayAdapter<ZCApplication>{

        private List<ZCApplication> zcApplicationList;
        private LayoutInflater layoutInflater;

        ListAdapter(Context context, List<ZCApplication> zcApplicationList) {
            super(context, 0, zcApplicationList);
            this.zcApplicationList = zcApplicationList;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return zcApplicationList.size();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.layout_for_app_list, null);
            }
            TextView primaryTextView = convertView.findViewById(R.id.primaryTextView);
            TextView secondaryTextView = convertView.findViewById(R.id.secondaryTextView);

            primaryTextView.setText(zcApplicationList.get(position).getAppName());
            secondaryTextView.setText(zcApplicationList.get(position).getAppOwner());
            return convertView;
        }
    }
}
