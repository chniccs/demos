package com.chniccs.study.demos.greendao;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.app.BaseApplication;
import com.chniccs.study.demos.greendao.entity.User;
import com.chniccs.study.demos.greendao.gen.DaoMaster;
import com.chniccs.study.demos.greendao.gen.DaoSession;
import com.chniccs.study.demos.greendao.gen.UserDao;
import com.chniccs.study.demos.rxjava.RxSchedulersHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by chniccs on 16/8/9.
 * 参考：http://blog.csdn.net/njweiyukun/article/details/51893092
 * 将功能都写在这一个类中，只为了更加方便的观察整个流程，仅作为功能参考
 */
public class GreenDaoFragment extends Fragment {
    @BindView(R.id.greendao_tv)
    TextView mTv;
    @BindView(R.id.greendao_et_add)
    EditText mEtAdd;
    @BindView(R.id.greendao_et_delete)
    EditText mEtDel;
    @BindView(R.id.greendao_rcyclerview)
    RecyclerView mRecyclerView;
    private UserDao mUserDao;
    private String mName;
    private User mUser;
    private RecyclerView.Adapter mAdapter;
    private List<User> mUserList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(BaseApplication.getContext(), "demos-db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        mUserDao = daoSession.getUserDao();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_greendao, container, false);
        ButterKnife.bind(this, view);
        init();
//        mUserDao.deleteAll();

        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        Observable.just("")//拿到全部数据
                .map(new Func1<String, List>() {
                    @Override
                    public List call(String s) {
                        return mUserDao.queryBuilder()
                                .orderAsc(UserDao.Properties.Id)
                                .build().list();
                    }
                })
                .compose(RxSchedulersHelper.<List>io_main())
                .subscribe(new Action1<List>() {
                    @Override
                    public void call(List list) {
                        mUserList = list;
                        mAdapter.notifyDataSetChanged();
                    }
                });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RecyclerView.Adapter() {
            @Override
            public GreenDaoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new GreenDaoHolder(LayoutInflater.from(
                        getActivity()).inflate(R.layout.item, parent,
                        false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((GreenDaoHolder) holder).tv.setText(mUserList.get(position).getName());
            }


            @Override
            public int getItemCount() {
                if (mUserList == null || mUserList.size() <= 0) {
                    return 0;
                }
                return mUserList.size();
            }
        };
        mRecyclerView.setAdapter(mAdapter);

    }

    class GreenDaoHolder extends RecyclerView.ViewHolder {

        public GreenDaoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);//注解绑定
        }

        @BindView(R.id.tv)
        TextView tv;

    }

    @OnClick(R.id.greendao_btn_add)
    public void add(View v) {

        mName = mEtAdd.getText().toString();
        if (mName != null && !mName.isEmpty()) {
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    mUser = new User(null, mName);
                    mUserDao.insert(mUser);
                    subscriber.onCompleted();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            addToList(mUser);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {

                        }


                    });
        } else {
            Toast.makeText(getActivity(), "名字不要为空啦！", Toast.LENGTH_SHORT).show();
        }
    }

    private User mFindUser;

    @OnClick(R.id.greendao_btn_delete)
    public void delete(View v) {//删除
        mName = mEtDel.getText().toString();
        if (mName != null && !mName.isEmpty()) {

            Observable.create(new Observable.OnSubscribe<String>() {


                @Override
                public void call(Subscriber<? super String> subscriber) {
                    //查询到
                    mFindUser = mUserDao.queryBuilder().where(UserDao.Properties.Name.eq(mName)).build().unique();
                    try {
                        mUserDao.deleteByKey(mFindUser.getId());
                        subscriber.onNext(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            update(mFindUser);
                        }
                    });


        } else {
            Toast.makeText(getActivity(), "名字不要为空啦！", Toast.LENGTH_SHORT).show();
        }
    }

    void addToList(User user) {
        if (mUserList == null) {
            mUserList = new ArrayList<>();
        }
        mUserList.add(user);
        mAdapter.notifyDataSetChanged();
    }

    void update(User user) {
        Iterator<User> iterator = mUserList.iterator();
        int i = -1;
        while (iterator.hasNext()) {
            i++;
            if (iterator.next().getId() == user.getId()) {
                iterator.remove();
                mAdapter.notifyItemRemoved(i);
                break;
            }
        }
    }
}
