package com.lesports.stadium.utils;

import java.util.ArrayList;
import java.util.Calendar;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lesports.stadium.R;
import com.lesports.stadium.activity.OnLineCreatingPayActivity;
import com.lesports.stadium.activity.OrderActivity;
import com.lesports.stadium.activity.PersionDataActivity;
import com.lesports.stadium.bean.SeatDetailInfo;
import com.lesports.stadium.view.PickerView;




public class CitySelector {
    public interface ResultHandler {
        void handle(String time);
    }

    public enum SCROLLTYPE {
        YEAR(1),
        MONTH(2),
        DAY(4),
        HOUR(8),
        MINUTE(16);

        private SCROLLTYPE(int value) {
            this.value = value;
        }

        public int value;

    }

    private int scrollUnits = SCROLLTYPE.YEAR.value + SCROLLTYPE.MONTH.value + SCROLLTYPE.DAY.value + SCROLLTYPE.HOUR.value + SCROLLTYPE.MINUTE.value;
    @SuppressWarnings("unused")
	private ResultHandler handler;
    private Context context;
    private final String FORMAT_STR = "yyyy-MM-dd HH:mm";

    private Dialog seletorDialog;
    private PickerView year_pv;
//    private PickerView month_pv;
//    private PickerView day_pv;
//    private PickerView hour_pv;


    private ArrayList<String> year, month, day, hour, minute;
    private int  minute_workStart, minute_workEnd, hour_workStart, hour_workEnd;
    @SuppressWarnings("unused")
	private final long ANIMATORDELAY = 200L;
    @SuppressWarnings("unused")
	private final long CHANGEDELAY = 90L;
    private String workStart_str;
    private String workEnd_str;
    private Calendar startCalendar;
    private Calendar endCalendar;
	private SeatDetailInfo sd;
	private TextView tv_confirm;
	PersionDataActivity instance;
	OnLineCreatingPayActivity instances;
	OrderActivity instances_order;
	@SuppressWarnings("unused")
	private boolean isuse=false;
	/**
	 * 用来标记是从商品订单界面传递过来
	 */
	@SuppressWarnings("unused")
	private String tag="lwc";

 
    public CitySelector(Context context, ResultHandler resultHandler, String startDate, String endDate,PersionDataActivity instance) {
    	this.instance=instance;
        this.context = context;
        this.handler = resultHandler;
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtil.parse(startDate, FORMAT_STR));
        endCalendar.setTime(DateUtil.parse(endDate, FORMAT_STR));
        initDialog();
        initView();
    }
    public CitySelector(Context context, ResultHandler resultHandler, String startDate, String endDate,OnLineCreatingPayActivity instance,boolean name) {
    	this.instances=instance;
        this.context = context;
        this.handler = resultHandler;
        this.isuse=name;
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtil.parse(startDate, FORMAT_STR));
        endCalendar.setTime(DateUtil.parse(endDate, FORMAT_STR));
        initDialog();
        initView();
    }
    public CitySelector(Context context, ResultHandler resultHandler, String startDate, String endDate,OrderActivity instance,boolean name,String tags) {
    	this.instances_order=instance;
        this.context = context;
        this.handler = resultHandler;
        this.isuse=name;
        this.tag=tags;
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtil.parse(startDate, FORMAT_STR));
        endCalendar.setTime(DateUtil.parse(endDate, FORMAT_STR));
        initDialog();
        initView();
    }


    public CitySelector(Context context, ResultHandler resultHandler, String startDate, String endDate, String workStartTime, String workEndTime) {
        this(context, resultHandler, startDate, endDate,null);
        this.workStart_str = workStartTime;
        this.workEnd_str = workEndTime;
    }

    public void show() {
        if (startCalendar.getTime().getTime() >= endCalendar.getTime().getTime()) {
            Toast.makeText(context, "hahah", Toast.LENGTH_LONG).show();
            return;
        }

        if (!excuteWorkTime()) return;
        initTimer();
        addListener();
        seletorDialog.show();


    }

    private void initDialog() {
        if (seletorDialog == null) {
            seletorDialog = new Dialog(context, R.style.time_dialog);
            seletorDialog.setCancelable(false);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            seletorDialog.setContentView(R.layout.dialog_city_selector);
            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(context).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }
    }

    private void initView() {
        year_pv = (PickerView) seletorDialog.findViewById(R.id.year_pv);
//        month_pv = (PickerView) seletorDialog.findViewById(R.id.month_pv);
//        day_pv = (PickerView) seletorDialog.findViewById(R.id.day_pv);
//        hour_pv = (PickerView) seletorDialog.findViewById(R.id.hour_pv);
       tv_confirm = (TextView) seletorDialog.findViewById(R.id.tv_confirm);
       tv_confirm.setOnClickListener(new OnClickListener() {
		
		@SuppressWarnings("static-access")
		@Override
		public void onClick(View v) {
					instance.setCity(sd.getFloor());
					seletorDialog.dismiss();
		}
	});
        sd = new SeatDetailInfo();
       
    }



    private void initTimer() {
        initArrayList();
        	year.add("北京");
        	year.add("天津");
        	year.add("上海");
        	year.add("重庆");
        	year.add("河北");
        	year.add("山西");
        	year.add("辽宁");
        	year.add("吉林");
        	year.add("黑龙江");
        	year.add("江苏");
        	year.add("浙江");
        	year.add("安徽");
        	year.add("福建");
        	year.add("江西");
        	year.add("山东");
        	year.add("河南");
        	year.add("湖北");
        	year.add("湖南");
        	year.add("广东");
        	year.add("海南");
        	year.add("四川");
        	year.add("贵州");
        	year.add("云南");
        	year.add("陕西");
        	year.add("甘肃");
        	year.add("青海");
        	year.add("西藏");
        	year.add("广西");
        	year.add("内蒙古");
        	year.add("宁夏");
        	year.add("新疆");
        	year.add("香港");
        	year.add("澳门");
        	year.add("台湾");
        loadComponent();

    }

    private boolean excuteWorkTime() {
        boolean res = true;
        if (!TextUtil.isEmpty(workStart_str) && !TextUtil.isEmpty(workEnd_str)) {
            String[] start = workStart_str.split(":");
            String[] end = workEnd_str.split(":");
            hour_workStart = Integer.parseInt(start[0]);
            minute_workStart = Integer.parseInt(start[1]);
            hour_workEnd = Integer.parseInt(end[0]);
            minute_workEnd = Integer.parseInt(end[1]);
            Calendar workStartCalendar = Calendar.getInstance();
            Calendar workEndCalendar = Calendar.getInstance();
            workStartCalendar.setTime(startCalendar.getTime());
            workEndCalendar.setTime(endCalendar.getTime());
            workStartCalendar.set(Calendar.HOUR_OF_DAY, hour_workStart);
            workStartCalendar.set(Calendar.MINUTE, minute_workStart);
            workEndCalendar.set(Calendar.HOUR_OF_DAY, hour_workEnd);
            workEndCalendar.set(Calendar.MINUTE, minute_workEnd);


            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            Calendar startWorkTime = Calendar.getInstance();
            Calendar endWorkTime = Calendar.getInstance();

            startTime.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY));
            startTime.set(Calendar.MINUTE, startCalendar.get(Calendar.MINUTE));
            endTime.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY));
            endTime.set(Calendar.MINUTE, endCalendar.get(Calendar.MINUTE));

            startWorkTime.set(Calendar.HOUR_OF_DAY, workStartCalendar.get(Calendar.HOUR_OF_DAY));
            startWorkTime.set(Calendar.MINUTE, workStartCalendar.get(Calendar.MINUTE));
            endWorkTime.set(Calendar.HOUR_OF_DAY, workEndCalendar.get(Calendar.HOUR_OF_DAY));
            endWorkTime.set(Calendar.MINUTE, workEndCalendar.get(Calendar.MINUTE));


            if (startTime.getTime().getTime() == endTime.getTime().getTime() || (startWorkTime.getTime().getTime() < startTime.getTime().getTime() && endWorkTime.getTime().getTime() < startTime.getTime().getTime())) {
                Toast.makeText(context, "zheshi", Toast.LENGTH_LONG).show();
                return false;
            }
            startCalendar.setTime(startCalendar.getTime().getTime() < workStartCalendar.getTime().getTime() ? workStartCalendar.getTime() : startCalendar.getTime());
            endCalendar.setTime(endCalendar.getTime().getTime() > workEndCalendar.getTime().getTime() ? workEndCalendar.getTime() : endCalendar.getTime());

        }
        return res;


    }

    @SuppressWarnings("unused")
	private String fomatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    private void initArrayList() {
        if (year == null) year = new ArrayList<>();
        if (month == null) month = new ArrayList<>();
        if (day == null) day = new ArrayList<>();
        if (hour == null) hour = new ArrayList<>();
        if (minute == null) minute = new ArrayList<>();
        year.clear();
        month.clear();
        day.clear();
        hour.clear();
        minute.clear();
    }


    private void addListener() {
        year_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @SuppressWarnings("static-access")
			@Override
            public void onSelect(String text) {
            	sd.setFloor(text);
 //               selectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
//                monthChange();


            }
        });
   
    }

    private void loadComponent() {
        year_pv.setData(year);
//        month_pv.setData(month);
//        day_pv.setData(day);
//        hour_pv.setData(hour);
        year_pv.setSelected(0);
//        month_pv.setSelected(0);
//        day_pv.setSelected(0);
//        hour_pv.setSelected(0);
        excuteScroll();
    }

    private void excuteScroll() {
//        year_pv.setCanScroll(year.size() > 1 && (scrollUnits & SCROLLTYPE.YEAR.value) == SCROLLTYPE.YEAR.value);
    	 year_pv.setCanScroll(true);
//        month_pv.setCanScroll(month.size() > 1 && (scrollUnits & SCROLLTYPE.MONTH.value) == SCROLLTYPE.MONTH.value);
//    	 month_pv.setCanScroll(true);
// //       day_pv.setCanScroll(day.size() > 1 && (scrollUnits & SCROLLTYPE.DAY.value) == SCROLLTYPE.DAY.value);
//        day_pv.setCanScroll(true);
////        hour_pv.setCanScroll(hour.size() > 1 && (scrollUnits & SCROLLTYPE.HOUR.value) == SCROLLTYPE.HOUR.value);
//        hour_pv.setCanScroll(true);
 //       minute_pv.setCanScroll(minute.size() > 1 && (scrollUnits & SCROLLTYPE.MINUTE.value) == SCROLLTYPE.MINUTE.value);
    }

//    private void monthChange() {
//        month.clear();
//
//        if("首层".equals(sd.getFloor())){
//        	for(int i=101;i<=122;i++){
//        		month.add(i+"");
//        	}
//        }else if("包厢层".equals(sd.getFloor())){
//        	
//        	for(int i=1;i<=53;i++){
//        		month.add("V"+i);
//        	}
//        }else{
//        	for(int i=301;i<=322;i++){
//        		month.add(i+"");
//        	}
 //       }
//        month_pv.setData(month);
//        month_pv.setSelected(0);
//        excuteAnimator(ANIMATORDELAY, month_pv);
//        month_pv.postDelayed(new Runnable() {
//            @Override
//            public void run() {
// //               dayChange();
//            }
//        }, CHANGEDELAY);

//    }
//
//    private void dayChange() {
//        day_pv.setSelected(0);
//        excuteAnimator(ANIMATORDELAY, day_pv);
//        day_pv.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                hourChange();
//            }
//        }, CHANGEDELAY);
//    }

//    private void hourChange() {
//        hour_pv.setSelected(0);
//        excuteAnimator(ANIMATORDELAY, hour_pv);
//        hour_pv.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                minuteChange();
//            }
//        }, CHANGEDELAY);
//
//    }

    @SuppressWarnings("unused")
	private void minuteChange() {

        excuteScroll();


    }

    @SuppressWarnings("unused")
	private void excuteAnimator(long ANIMATORDELAY, View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(ANIMATORDELAY).start();
    }


    public void setNextBtTip(String str) {

    }

    public int setScrollUnit(SCROLLTYPE... scrolltypes) {
        scrollUnits = 0;
        for (SCROLLTYPE scrolltype : scrolltypes) {
            scrollUnits ^= scrolltype.value;
        }
        return scrollUnits;
    }


}
