package com.chniccs.study.demos.sign;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.app.BaseApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by chniccs on 16/8/17.
 * 封装了日历的控件
 */
public class SignCalendarView extends LinearLayout {
    private Context mContext;

    private List<String> list = new ArrayList<String>();
    private ArrayList<HashMap<String, Object>> sinalist, alisttmp;

    private int dayMaxNum;
    private int year, month, day;
    private int space;
    SinInDao sdao;

    Calendar mCalendar;
    GridView gdDate;
    TextView mTxtNowDate;

    public SignCalendarView(Context context) {
        this(context, null, 0);
    }

    public SignCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.sign_view, this);
        mTxtNowDate = (TextView) view.findViewById(R.id.txtNowDate);
        gdDate = (GridView) view.findViewById(R.id.gdDate);

        sdao = new SinInDao(mContext);//初始化数据库操作类
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date m = null;
        //这里可以用来自定义传入的日期
        try {
            m = dateFormat.parse("2016-08-02");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        m=new Date();
        //设置标题
        String date = dateFormat.format(m);
        mTxtNowDate.setText(date);
        //获得日历对象
        mCalendar = dateFormat.getCalendar();
        mCalendar.setTime(m);
        year = mCalendar.get(Calendar.YEAR);
        month = mCalendar.get(Calendar.MONTH) + 1;//因为月份是从0开始计算的，所以要加1
        day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int weekOfMonth = mCalendar.get(Calendar.WEEK_OF_MONTH);
        //根据当前日期在本月第几周，以及当天在本周是第几天来推算出本月第1号是周几,需要几个空格
        int i = 0;
        int week = mCalendar.get(Calendar.DAY_OF_WEEK);//取的是今天是星期几，以周日开始，周日为1周六为7
        i += (week == 1 ? week + 6 : week - 1);
        weekOfMonth--;//先除去本周，看距离第一周还有几周
        if (weekOfMonth >= 1) {
            i += weekOfMonth * 7;
        }
        if (i > 0) {
            space = Math.abs(day - i);
        }

        sdao.open();
        getData();

        gdDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //判断是否已经签到 从服务器获取签到信息
                //模拟从本地数据库获取信息
                if (day == arg2 + 1)//只能当天签到
                {
                    sinalist = sdao.findSinInfo("zhangsan", year + "-" + month + "-" + (arg2 + 1), "0");
                    if (sinalist.size() > 0) {
                        Toast.makeText(mContext, "已经签过到不能重复签到", Toast.LENGTH_SHORT).show();
                        Log.d("", "已签到");
                    } else {
                        //在数据库插入一条数据
                        sdao.insertSinInfo("zhangsan", "张三", year + "-" + month + "-" + (arg2 + 1), year + "" + month);
                        getData();
                    }
                }
            }
        });
    }

    void getData() {
        sinalist = sdao.findSinInfo("zhangsan", "", year + "" + month);//查询当月已签到的日期
        list.clear();
        //此处是用处：每个月1号如果不是周一的话，若其假设其为周三，就用2个元素占下集合中的前两位，让1号对应到相应周数。
        for (int i = 0; i < space; i++) {
            list.add(1 + i + "");
        }
        dayMaxNum = getCurrentMonthDay();
        for (int i = 0; i < dayMaxNum; i++) {
            list.add(i + 1 + "");
        }
        gdDate.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gdDate.setAdapter(new getDayNumAdapter(BaseApplication.getContext()));
    }

    class getDayNumAdapter extends BaseAdapter {
        Context c;

        public getDayNumAdapter(Context c) {
            this.c = c;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LinearLayout.inflate(c, R.layout.date_mb, null);
            TextView txtWeek = (TextView) v.findViewById(R.id.txtWeekDateMB);
            TextView txtDay = (TextView) v.findViewById(R.id.txtDayDateMB);

            switch (position) {
                case 0:
                    txtWeek.setText("一");
                    break;
                case 1:
                    txtWeek.setText("二");
                    break;
                case 2:
                    txtWeek.setText("三");
                    break;
                case 3:
                    txtWeek.setText("四");
                    break;
                case 4:
                    txtWeek.setText("五");
                    break;
                case 5:
                    txtWeek.setText("六");
                    break;
                case 6:
                    txtWeek.setText("日");
                    break;
            }
            if (position < 7) {
                txtWeek.setVisibility(View.VISIBLE);
            }


            if (position < space) {
                return v;
            }
            int lstDay = Integer.parseInt(list.get(position));

            //标记当前日期
            if (day == lstDay) {
                txtDay.setText(list.get(position).toString());
                txtDay.setTextColor(Color.BLUE);
//				txtDay.setTextSize(18);
                txtDay.setTypeface(Typeface.DEFAULT_BOLD);
            } else
                txtDay.setText(list.get(position).toString());

            //标记已签到后的背景
            for (int i = 0; i < sinalist.size(); i++) {
                String nowdate = sinalist.get(i).get("sindate").toString();
                String[] nowdatearr = nowdate.split("-");
                if (lstDay == Integer.parseInt(nowdatearr[2])) {
                    txtDay.setBackgroundColor(Color.GREEN);
                    txtDay.setTextColor(Color.YELLOW);
                }
            }
            return v;
        }

    }

    //获取当月的 天数
    public int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
