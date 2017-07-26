package com.blueicon.gpsblueicon.activities.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blueicon.gpsblueicon.R;
import com.blueicon.gpsblueicon.activities.tools.Constantes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.usuario) EditText usuario;
    @BindView(R.id.pwd) EditText password;
    @BindView(R.id.btnSend) Button btnEnter;

    @OnClick(R.id.btnSend) void eventEnter(){
        String user =  usuario.getText().toString();
        String pwd =  password.getText().toString();

        if(user.equals("blueicon") || pwd.equals("2468")) {
            Constantes.messageDialog("GPS", "Llena por favor los campos requeridos", this);
        }else {
            Intent intent = new Intent(this, LevantarCoordenadas.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
            // hideKeyboard(this);
            /** ::::::::: MANDO A LLAMAR EL METODO QU SE ENCUENTRA DENTRO DE COMTRANTES ::::::::***/
                Constantes.hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }
}
