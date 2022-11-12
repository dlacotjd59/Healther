// Generated by view binder compiler. Do not edit!
package com.example.healtherlogin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.healtherlogin.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SignUpBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextInputEditText saveAGE;

  @NonNull
  public final TextInputEditText saveHEI;

  @NonNull
  public final TextInputEditText saveID;

  @NonNull
  public final TextInputEditText savePW;

  @NonNull
  public final TextInputEditText saveWEI;

  @NonNull
  public final Button signupBut2;

  private SignUpBinding(@NonNull ConstraintLayout rootView, @NonNull TextInputEditText saveAGE,
      @NonNull TextInputEditText saveHEI, @NonNull TextInputEditText saveID,
      @NonNull TextInputEditText savePW, @NonNull TextInputEditText saveWEI,
      @NonNull Button signupBut2) {
    this.rootView = rootView;
    this.saveAGE = saveAGE;
    this.saveHEI = saveHEI;
    this.saveID = saveID;
    this.savePW = savePW;
    this.saveWEI = saveWEI;
    this.signupBut2 = signupBut2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SignUpBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.save_AGE;
      TextInputEditText saveAGE = ViewBindings.findChildViewById(rootView, id);
      if (saveAGE == null) {
        break missingId;
      }

      id = R.id.save_HEI;
      TextInputEditText saveHEI = ViewBindings.findChildViewById(rootView, id);
      if (saveHEI == null) {
        break missingId;
      }

      id = R.id.save_ID;
      TextInputEditText saveID = ViewBindings.findChildViewById(rootView, id);
      if (saveID == null) {
        break missingId;
      }

      id = R.id.save_PW;
      TextInputEditText savePW = ViewBindings.findChildViewById(rootView, id);
      if (savePW == null) {
        break missingId;
      }

      id = R.id.save_WEI;
      TextInputEditText saveWEI = ViewBindings.findChildViewById(rootView, id);
      if (saveWEI == null) {
        break missingId;
      }

      id = R.id.signup_but2;
      Button signupBut2 = ViewBindings.findChildViewById(rootView, id);
      if (signupBut2 == null) {
        break missingId;
      }

      return new SignUpBinding((ConstraintLayout) rootView, saveAGE, saveHEI, saveID, savePW,
          saveWEI, signupBut2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}