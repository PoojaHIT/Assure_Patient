package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.NOTIFICATION;
import static com.hit.assure.Retrofit.ServerCode.NOTIFICATION_READ;
import static com.hit.assure.Retrofit.ServerCode.VCSLOTDATA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hit.assure.Adapter.SearchAdapter;
import com.hit.assure.Model.Notification.NotificationData;
import com.hit.assure.Model.Notification.NotificationResponse;
import com.hit.assure.Model.NotificationReadResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.PaginationScrollListener;
import com.hit.assure.Util.PreferenceServices;

import java.util.List;
import java.util.Objects;

public class NotificationActivity extends AppCompatActivity implements ServerResponse {

    private static final int PAGE_START = 0;
    private int TOTAL_PAGE, CURRENT_PAGE = PAGE_START;
    private boolean isLoading = false, isLastPage = false;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerview_notifications;
    private NotificationlistAdapter notificationlistAdapter;
    private LinearLayout ll_nodata;
    private ImageView Goback;
    private List<NotificationData> notificationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        inita();
        showProgressDialog();
        new Requestor(NOTIFICATION, NotificationActivity.this).getNotificationlist(PreferenceServices.getInstance().getUser_id(),
                String.valueOf(CURRENT_PAGE));
    }

    private void inita() {

        Goback = findViewById(R.id.Goback);
        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_nodata = findViewById(R.id.ll_nodata);

        recyclerview_notifications = findViewById(R.id.recyclerview_notifications);
        LinearLayoutManager rxpatientlistlayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview_notifications.setLayoutManager(rxpatientlistlayout);
        recyclerview_notifications.setHasFixedSize(true);
        recyclerview_notifications.setNestedScrollingEnabled(false);
        recyclerview_notifications.setAdapter(notificationlistAdapter);


        recyclerview_notifications.addOnScrollListener(new PaginationScrollListener(rxpatientlistlayout) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                CURRENT_PAGE += 1;
                if (CURRENT_PAGE <= TOTAL_PAGE) {
                    notificationlistAdapter.addLoadingFooterSubCat();
                    // mocking network delay for API call
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new Requestor(NOTIFICATION, NotificationActivity.this).getNotificationlist(PreferenceServices.getInstance().getUser_id(),
                                    String.valueOf(CURRENT_PAGE));
                        }
                    }, 1000);
                } else {
                    isLastPage = true;
                }
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }


    @Override
    public void success(Object o, int code) {
        switch (code) {
            case NOTIFICATION:
                NotificationResponse response = (NotificationResponse) o;
                if (response.getResponseCode() == 200) {
                    notificationData = response.getNotificationData();

                    TOTAL_PAGE = response.getTotal_page();
                    if (notificationData != null && notificationData.size() > 0) {
                        hideProgressDialog();
                        if (CURRENT_PAGE == 0) {
                            notificationlistAdapter = new NotificationlistAdapter(this, notificationData);
                            recyclerview_notifications.setAdapter(notificationlistAdapter);
                            recyclerview_notifications.setVisibility(View.VISIBLE);

                            if (CURRENT_PAGE != TOTAL_PAGE) {

                            } else {
                                isLastPage = true;
                            }
                        } else {
                            notificationlistAdapter.removeLoadingFooterFilter();
                            isLoading = false;
                            notificationlistAdapter.addShopFilterProductList(notificationData);
                            if (CURRENT_PAGE != TOTAL_PAGE) {

                            } else {
                                isLastPage = true;
                            }
                        }


                    } else {
                        notificationlistAdapter.removeLoadingFooterFilter();
                        hideProgressDialog();
                    }
                } else {
                    ll_nodata.setVisibility(View.VISIBLE);
                    recyclerview_notifications.setVisibility(View.GONE);
                    hideProgressDialog();
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }

    public class NotificationlistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ServerResponse {

        private Context mContext;
        private List<NotificationData> notificationData;

        private static final int ITEM = 0;
        private static final int LOADING = 1;
        private boolean isLoadingAdded = false;

        public NotificationlistAdapter(Context mContext, List<NotificationData> notificationData) {
            this.mContext = mContext;
            this.notificationData = notificationData;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            switch (viewType) {
                case ITEM:
                    viewHolder = getViewHolder(viewGroup, layoutInflater);
                    break;
                case LOADING:
                    View viewLoading = layoutInflater.inflate(R.layout.item_progress_pagination, viewGroup, false);
                    viewHolder = new LoadingViewH(viewLoading);
                    break;
            }

            return viewHolder;
        }

        private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater layoutInflater) {
            RecyclerView.ViewHolder viewHolder;

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notificationlist, parent, false);
            MyViewHolder sellersViewholder = new MyViewHolder(view);
            return sellersViewholder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder myViewHolder, @SuppressLint("RecyclerView") int position) {

            switch (getItemViewType(position)) {
                case ITEM:
                    MyViewHolder holder = (MyViewHolder) myViewHolder;

                    holder.txtTitle.setText(notificationData.get(position).getType());
                    holder.txtmsg.setText(notificationData.get(position).getDescription());
                    holder.txt_date.setText(notificationData.get(position).getCreated_at());

//                    if (notificationData.get(position).getIs_read().equalsIgnoreCase("0")) {
//                        holder.ll_parentlayout.setBackgroundColor(getResources().getColor(R.color.lite_grey));
//                    } else {
//                        holder.ll_parentlayout.setBackgroundColor(getResources().getColor(R.color.white));
//                    }

//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (notificationData.get(position).getIs_read().equalsIgnoreCase("0")) {
//                                showProgressDialog();
//                                new Requestor(NOTIFICATION_READ, NotificationlistAdapter.this).getNotificationRead(notificationData.get(position).getNotification_id());
//                            }
//                        }
//                    });

                    break;
                case LOADING:
                    //Do nothing
                    break;
            }


        }


        public void addLoadingFooterSubCat() {
            isLoadingAdded = true;
            add(new NotificationData());
        }

        public void add(NotificationData prodlist) {
            notificationData.add(prodlist);
            notifyItemInserted(notificationData.size() - 1);
        }

        public void removeLoadingFooterFilter() {
            isLoadingAdded = false;

            int position = notificationData.size() - 1;
            NotificationData prodlist = getItem(position);

            if (prodlist != null) {
                notificationData.remove(position);
                notifyItemRemoved(position);
            }
        }

        private NotificationData getItem(int position) {
            return notificationData.get(position);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == notificationData.size() - 1 && isLoadingAdded) return LOADING;
            else return ITEM;
        }

        public void addShopFilterProductList
                (List<NotificationData> prodlist) {
            if (prodlist != null) {
                for (NotificationData model : prodlist)
                    this.notificationData.add(model);
                notifyDataSetChanged();
            }
        }

        @Override
        public void success(Object o, int code) {
            switch (code) {
                case NOTIFICATION_READ:
                    NotificationReadResponse notificationReadResponse = (NotificationReadResponse) o;
                    if (notificationReadResponse.getResult().equalsIgnoreCase("true")) {
                        hideProgressDialog();
                        notifyDataSetChanged();
//                        showProgressDialog();
//                        new Requestor(NOTIFICATION, NotificationActivity.this).getNotificationlist(PreferenceServices.getInstance().getUser_id(),
//                                "0");
                    } else {
                        hideProgressDialog();
                    }
                    break;
            }
        }

        @Override
        public void error(Object o, int code) {

        }

        private class LoadingViewH extends RecyclerView.ViewHolder {
            public LoadingViewH(View viewLoading) {
                super(viewLoading);
            }
        }

        @Override
        public int getItemCount() {
            return notificationData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txtTitle, txtmsg, txt_date;
            public LinearLayout ll_parentlayout;

            public MyViewHolder(View view) {
                super(view);
                txtTitle = view.findViewById(R.id.txtTitle);
                txtmsg = (TextView) view.findViewById(R.id.txtmsg);
                txt_date = (TextView) view.findViewById(R.id.txt_date);
                ll_parentlayout = (LinearLayout) view.findViewById(R.id.ll_parentlayout);
            }
        }

    }


    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setContentView(R.layout.item_loader);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}