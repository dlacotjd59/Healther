// Generated by view binder compiler. Do not edit!
package com.example.healtherlogin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.healtherlogin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMain1Binding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout container;

  @NonNull
  public final EditText inputEmail;

  @NonNull
  public final EditText inputPassword;

  @NonNull
  public final Button loginBut;

  @NonNull
  public final BottomNavigationView navView;

  @NonNull
  public final Button signupBut;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView2;

  private ActivityMain1Binding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout container, @NonNull EditText inputEmail,
      @NonNull EditText inputPassword, @NonNull Button loginBut,
      @NonNull BottomNavigationView navView, @NonNull Button signupBut, @NonNull TextView textView,
      @NonNull TextView textView2) {
    this.rootView = rootView;
    this.container = container;
    this.inputEmail = inputEmail;
    this.inputPassword = inputPassword;
    this.loginBut = loginBut;
    this.navView = navView;
    this.signupBut = signupBut;
    this.textView = textView;
    this.textView2 = textView2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMain1Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMain1Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main1, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMain1Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout container = (ConstraintLayout) rootView;

      id = R.id.input_email;
      EditText inputEmail = ViewBindings.findChildViewById(rootView, id);
      if (inputEmail == null) {
        break missingId;
      }

      id = R.id.input_password;
      EditText inputPassword = ViewBindings.findChildViewById(rootView, id);
      if (inputPassword == null) {
        break missingId;
      }

      id = R.id.login_but;
      Button loginBut = ViewBindings.findChildViewById(rootView, id);
      if (loginBut == null) {
        break missingId;
      }

      id = R.id.nav_view;
      BottomNavigationView navView = ViewBindings.findChildViewById(rootView, id);
      if (navView == null) {
        break missingId;
      }

      id = R.id.signup_but;
      Button signupBut = ViewBindings.findChildViewById(rootView, id);
      if (signupBut == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      return new ActivityMain1Binding((ConstraintLayout) rootView, container, inputEmail,
          inputPassword, loginBut, navView, signupBut, textView, textView2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
