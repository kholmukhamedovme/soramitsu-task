package me.kholmukhamedov.soramitsutest.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;

import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.presentation.presenter.MainPresenter;
import me.kholmukhamedov.soramitsutest.presentation.utils.BaseActivity;

public class MainActivity extends BaseActivity implements MainView {

    public static final String TAG = "MainActivity";

    @InjectPresenter
    MainPresenter mMainPresenter;

    public static Intent getIntent(final Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
