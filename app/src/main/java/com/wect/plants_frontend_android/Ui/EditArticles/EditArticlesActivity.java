package com.wect.plants_frontend_android.Ui.EditArticles;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Ui.Based.BaseActivity;
import com.wect.plants_frontend_android.Utils.BarUtils;

public class EditArticlesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_edit_articles);
    }
}