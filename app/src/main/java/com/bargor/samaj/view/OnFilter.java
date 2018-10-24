package com.bargor.samaj.view;

public interface OnFilter {
    void onApply(String main_id,String sub_id,String study_id);
    void onClear(String main_id,String sub_id,String study_id);
}
