package com.inlocal.restaurantapp.ui.myorders.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityOrderListBinding;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivity;
import com.inlocal.restaurantapp.ui.bookingdetails.view.BookingDetailsActivity;
import com.inlocal.restaurantapp.ui.bookingdetails.viewmodel.BookingDetailsViewModel;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.ui.myorders.model.DateRange;
import com.inlocal.restaurantapp.ui.myorders.model.OrderHistoryItem;
import com.inlocal.restaurantapp.ui.myorders.viewmodel.OrderHistoryViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.DateConveter;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class MyOrdersActivity extends BaseActivity<ActivityOrderListBinding> implements OrderListAdapter.HistoryItemClickListener {
    @Inject
    ViewModelFactory viewModelFactory;
    private OrderHistoryViewModel viewModel;
    private OrderListAdapter adapter;
    private int mPage = 0;
    private DateRange dateRange;
    private DownloadManager downloadManager;
    private String downloadUrl = "", downloadFileName = "";
    private List<OrderHistoryItem> orderHistoryItemList;
    private String startFormateDate = "", endFormateDate = "", startDate = "", endDate = "";


    @Override
    protected int layoutRes() {
        return R.layout.activity_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(OrderHistoryViewModel.class);
        binding.setLifecycleOwner(this);
        initVar();
        binding.flCal.setOnClickListener(v -> {
            showcalendarDialog();
        });
        binding.flDoc.setOnClickListener(v -> {
            viewModel.getExportResponse();
        });
        binding.imgBack.setOnClickListener(v -> onBackPressed());


        viewModel.exportResponse.observe(this, response -> {
            if (response != null) {
                downloadUrl = response.getFilePath();
                downloadFileName = response.getFilePath().substring(response.getFilePath().lastIndexOf("/") + 1);
                Log.e("fileName", downloadFileName);
                new DownloadingTask().execute();
                /*downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(response.getFilePath());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                long reference = downloadManager.enqueue(request);*/
            }
        });
        viewModel.orderHistoryListResponse.observe(this, response -> {
            if (response != null) {
                if (mPage == 0) {
                    orderHistoryItemList.clear();
                    orderHistoryItemList = response.getOrderHistory();
                    adapter.setList(orderHistoryItemList);
                } else {
                    for (int i = 0; i < response.getOrderHistory().size(); i++) {
                        adapter.addRow(response.getOrderHistory().get(i));
                    }
                    //orderHistoryItemList.addAll(response.getOrderHistory());
                }
            }
        });

        binding.recyclerItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (orderHistoryItemList.size() < viewModel.totalListRecord.getValue()) {
                        mPage = mPage + 1;
                        getData();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        viewModel.recodFound.observe(this, response -> {
            binding.recyclerItem.setVisibility(viewModel.recodFound.getValue() ? View.VISIBLE : View.GONE);
            binding.tvNoRecord.setVisibility(viewModel.recodFound.getValue() ? View.GONE : View.VISIBLE);
        });

        viewModel.errorFromServer.observe(this, response -> {
            showSnackbar(response);
        });

        viewModel.isProgressEnabled.observe(this, booleanEvent -> {
                    if (booleanEvent.getContentIfNotHandled()) {
                        showLoading();
                    } else {
                        hideLoading();
                    }
                }
        );
    }

    private void initVar() {
        orderHistoryItemList = new ArrayList<>();
        adapter = new OrderListAdapter(this);
        binding.recyclerItem.setAdapter(adapter);
        //startDate = DateConveter.getTodayDate();
        startDate = DateConveter.getTwoMonthOldDate();
        endDate = DateConveter.getTodayDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 0;
        getData();
    }


    private void showcalendarDialog() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_calendar, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 50);
        alertDialog.getWindow().setBackgroundDrawable(inset);
        alertDialog.setCancelable(false);
        alertDialog.show();


        TextView tvCalCancel = dialogView.findViewById(R.id.tvCalCancel);
        TextView tvCalTitle = dialogView.findViewById(R.id.tvCalTitle);
        TextView tvCalDone = dialogView.findViewById(R.id.tvCalDone);
        CalendarPickerView calendar_view = dialogView.findViewById(R.id.calendar_view);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 0);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);

        calendar_view.scrollToPosition(11);
        calendar_view.deactivateDates(list);
        calendar_view.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.ENGLISH)) //
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withDeactivateDates(list);
        // .withSelectedDates(selectedEndDate);

        tvCalDone.setEnabled(false);
        tvCalDone.setTextColor(getResources().getColor(R.color.gray5));

        calendar_view.setCellClickInterceptor(new CalendarPickerView.CellClickInterceptor() {
            @Override
            public boolean onCellClicked(Date date) {
                tvCalDone.setTextColor(getResources().getColor(R.color.black));
                tvCalDone.setEnabled(true);
                return false;
            }
        });

        tvCalDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (calendar_view.getSelectedDates().size() == 0) {
                    startDate = DateConveter.getTodayDate();
                    endDate = DateConveter.getTodayDate();
                    // startFormateDate = DateConveter.getTodayDateNewFormat();
                    // endFormateDate = DateConveter.getTodayDateNewFormat();
                } else {
                    Date statDate = calendar_view.getSelectedDates().get(0);
                    Date enDate = calendar_view.getSelectedDates().get(calendar_view.getSelectedDates().size() - 1);


                    //startFormateDate = new SimpleDateFormat("dd-MMM-yyyy").format(statDate);
                    //endFormateDate = new SimpleDateFormat("dd-MMM-yyyy").format(enDate);

                    startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(statDate);
                    endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(enDate);
                }
                Log.v("start date ", startFormateDate);
                Log.v("end date ", endFormateDate);

                alertDialog.dismiss();
                mPage = 0;
                getData();
            }
        });

        tvCalCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public void getData() {
        DateRange dateRange = new DateRange(startDate, endDate);
        viewModel.getOrderHistoryList(mPage, dateRange);
    }

    @Override
    public void onHistoryItemClick(OrderHistoryItem item, int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.IntentData.ORDER_ID, item.getId());
        NavUtil.ForActivity.navTo(this, BookingDetailsActivity.class, false, bundle);
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    //If Download completed then change button text
                    Toast.makeText(MyOrdersActivity.this, "File Downloaded. Location=> " + outputFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    hideLoading();
                } else {
                    Toast.makeText(MyOrdersActivity.this, "File Downloaded Failed", Toast.LENGTH_SHORT).show();
                    //If download failed change button text
                    hideLoading();

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs
                hideLoading();
                Log.e("MyOrdersActivity", "Download Failed with Exception - " + e.getLocalizedMessage());

            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e("MyOrderActivity", "Server returned HTTP " + c.getResponseCode() + " " + c.getResponseMessage());
                }


                //Get File if SD card is present

                apkStorage = new File(Environment.getExternalStorageDirectory() + "/restaurant");

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e("File", "Directory Created.");
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e("File", "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {
                hideLoading();
                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e("File", "Download Error Exception " + e.getMessage());
            }

            return null;
        }
    }
}