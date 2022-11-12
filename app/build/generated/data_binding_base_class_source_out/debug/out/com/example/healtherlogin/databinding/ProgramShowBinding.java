// Generated by view binder compiler. Do not edit!
package com.example.healtherlogin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.healtherlogin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ProgramShowBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView week1;

  @NonNull
  public final TextView week1txt1;

  @NonNull
  public final TextView week1txt2;

  @NonNull
  public final TextView week1txt3;

  @NonNull
  public final TextView week2;

  @NonNull
  public final TextView week3;

  @NonNull
  public final TextView week3txt1;

  @NonNull
  public final TextView week3txt2;

  @NonNull
  public final TextView week3txt3;

  @NonNull
  public final TextView week4;

  @NonNull
  public final TextView week5;

  @NonNull
  public final TextView week5txt1;

  @NonNull
  public final TextView week5txt2;

  @NonNull
  public final TextView week5txt3;

  private ProgramShowBinding(@NonNull LinearLayout rootView, @NonNull TextView week1,
      @NonNull TextView week1txt1, @NonNull TextView week1txt2, @NonNull TextView week1txt3,
      @NonNull TextView week2, @NonNull TextView week3, @NonNull TextView week3txt1,
      @NonNull TextView week3txt2, @NonNull TextView week3txt3, @NonNull TextView week4,
      @NonNull TextView week5, @NonNull TextView week5txt1, @NonNull TextView week5txt2,
      @NonNull TextView week5txt3) {
    this.rootView = rootView;
    this.week1 = week1;
    this.week1txt1 = week1txt1;
    this.week1txt2 = week1txt2;
    this.week1txt3 = week1txt3;
    this.week2 = week2;
    this.week3 = week3;
    this.week3txt1 = week3txt1;
    this.week3txt2 = week3txt2;
    this.week3txt3 = week3txt3;
    this.week4 = week4;
    this.week5 = week5;
    this.week5txt1 = week5txt1;
    this.week5txt2 = week5txt2;
    this.week5txt3 = week5txt3;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ProgramShowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ProgramShowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.program_show, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ProgramShowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.week1;
      TextView week1 = ViewBindings.findChildViewById(rootView, id);
      if (week1 == null) {
        break missingId;
      }

      id = R.id.week1txt1;
      TextView week1txt1 = ViewBindings.findChildViewById(rootView, id);
      if (week1txt1 == null) {
        break missingId;
      }

      id = R.id.week1txt2;
      TextView week1txt2 = ViewBindings.findChildViewById(rootView, id);
      if (week1txt2 == null) {
        break missingId;
      }

      id = R.id.week1txt3;
      TextView week1txt3 = ViewBindings.findChildViewById(rootView, id);
      if (week1txt3 == null) {
        break missingId;
      }

      id = R.id.week2;
      TextView week2 = ViewBindings.findChildViewById(rootView, id);
      if (week2 == null) {
        break missingId;
      }

      id = R.id.week3;
      TextView week3 = ViewBindings.findChildViewById(rootView, id);
      if (week3 == null) {
        break missingId;
      }

      id = R.id.week3txt1;
      TextView week3txt1 = ViewBindings.findChildViewById(rootView, id);
      if (week3txt1 == null) {
        break missingId;
      }

      id = R.id.week3txt2;
      TextView week3txt2 = ViewBindings.findChildViewById(rootView, id);
      if (week3txt2 == null) {
        break missingId;
      }

      id = R.id.week3txt3;
      TextView week3txt3 = ViewBindings.findChildViewById(rootView, id);
      if (week3txt3 == null) {
        break missingId;
      }

      id = R.id.week4;
      TextView week4 = ViewBindings.findChildViewById(rootView, id);
      if (week4 == null) {
        break missingId;
      }

      id = R.id.week5;
      TextView week5 = ViewBindings.findChildViewById(rootView, id);
      if (week5 == null) {
        break missingId;
      }

      id = R.id.week5txt1;
      TextView week5txt1 = ViewBindings.findChildViewById(rootView, id);
      if (week5txt1 == null) {
        break missingId;
      }

      id = R.id.week5txt2;
      TextView week5txt2 = ViewBindings.findChildViewById(rootView, id);
      if (week5txt2 == null) {
        break missingId;
      }

      id = R.id.week5txt3;
      TextView week5txt3 = ViewBindings.findChildViewById(rootView, id);
      if (week5txt3 == null) {
        break missingId;
      }

      return new ProgramShowBinding((LinearLayout) rootView, week1, week1txt1, week1txt2, week1txt3,
          week2, week3, week3txt1, week3txt2, week3txt3, week4, week5, week5txt1, week5txt2,
          week5txt3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}